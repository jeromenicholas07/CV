/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Database.CricDB;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.*;

public class getData extends HttpServlet {

    CricDB db = new CricDB();

    private Object backTest5_Test(List<Inning> selects, int fiveWktIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public interface MatchProcessor {

        List<Match> getMatches(String teamName);

        List<Match> getBtMatches();

        List<Match> getGMatches(String groundName);

        List<Match> process(List<Match> matches);
    }

    public interface TestProcessor {

        List<testMatch> getMatches(String teamName, Location xType);

        List<testMatch> getGMatches(String groundName);

        List<testMatch> process(List<testMatch> matches);
    }

    public interface InningCaller {

        Inning call(testMatch match);
    }

    public int toInt(double d) {
        return (int) d;
    }

    private Map<String, String> backTest5(MatchProcessor type, boolean isFirstInning, int pIndex, boolean isBatting) {
        Map<String, String> btMap = generateBt5Map();

        List<Match> btMatches = type.getBtMatches();

        for (int i = 0; i < btMatches.size(); i++) {

            Match currMatch = btMatches.get(i);
            int curr = isFirstInning ? parseInt(currMatch.getInningOne().getParams().get(pIndex)) : parseInt(currMatch.getInningTwo().getParams().get(pIndex));
            List<Match> matches = isBatting ? type.getMatches(currMatch.getHomeTeam()) : type.getMatches(currMatch.getHomeTeam());
            Timestamp currDate = currMatch.getMatchDate();
            List<Match> temp = new ArrayList<>(matches);
            temp.removeIf(m -> (m.getMatchDate().after(currDate) || m.getMatchDate().equals(currDate)));
            List<Inning> tempInns = isFirstInning ? temp.stream().map(m -> m.getInningOne()).collect(Collectors.toList()) : temp.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());
            if (tempInns.size() < 5) {
                continue;
            }

            OHL ohl = db.getOHL(currMatch.getMatchId());
            if (ohl == null) {
                continue;
            }
            boolean noOhlHeader = false;
            Header currHeader = ohl.getHeader(isFirstInning, pIndex);
            if (currHeader != null && currHeader.isEmpty()) {
                continue;
            } else if (currHeader == null) {
                noOhlHeader = true;
            }

            if (!noOhlHeader && pIndex == 2) {
                currHeader.setOpen(curr - currHeader.getOpen());
                currHeader.setHigh((int) (curr - currHeader.getHigh()));
                currHeader.setLow(curr - currHeader.getLow());
            }

            List<Inning> sub = new ArrayList<>(tempInns.subList(0, 5));
            Collections.sort(sub, new Comparator<Inning>() {
                @Override
                public int compare(Inning o1, Inning o2) {
                    return parseInt(o1.getParams().get(pIndex))
                            - parseInt(o2.getParams().get(pIndex));
                }
            });

            int[] p = new int[5];
            for (int j = 0; j < 5; j++) {
                p[j] = parseInt(sub.get(j).getParams().get(pIndex));
            }

            incrementBt(btMap, "N");

            if (!noOhlHeader) {
                String OO_Header = "";
                if (currHeader.getOpen() < p[0]) {
                    OO_Header = "0/5";
                } else if (currHeader.getOpen() >= p[4]) {
                    OO_Header = "5/0";
                } else {
                    for (int j = 0; j <= 3; j++) {
                        if (currHeader.getOpen() >= p[j] && currHeader.getOpen() < p[j + 1]) {
                            OO_Header = (j + 1) + "/" + (4 - j);
                        }
                    }
                }

                belowAboveIncrement(curr, toInt(currHeader.getOpen()), OO_BT + OO_Header, btMap);
            }

            if (noOhlHeader || OH_Condition(p[0], currHeader)) {
                belowAboveIncrement(curr, p[0], OH_BT + "0/5", btMap);
            }

            if (noOhlHeader || OL_Condition(p[0], currHeader)) {
                belowAboveIncrement(curr, p[0], OL_BT + "0/5", btMap);
            }

            if (noOhlHeader || OH_Condition(p[1], currHeader)) {
                belowAboveIncrement(curr, p[1], OH_BT + "1/4", btMap);
            }

            if (noOhlHeader || OL_Condition(p[1], currHeader)) {
                belowAboveIncrement(curr, p[1], OL_BT + "1/4", btMap);
            }

            if (noOhlHeader || OL_Condition(p[2], currHeader)) {
                belowAboveIncrement(curr, p[2], OL_BT + "2/3", btMap);
            }

            if (noOhlHeader || OH_Condition(p[2], currHeader)) {
                belowAboveIncrement(curr, p[2], OH_BT + "3/2", btMap);
            }

            if (noOhlHeader || OH_Condition(p[3], currHeader)) {
                belowAboveIncrement(curr, p[3], OH_BT + "4/1", btMap);
            }

            if (noOhlHeader || OL_Condition(p[3], currHeader)) {
                belowAboveIncrement(curr, p[3], OL_BT + "4/1", btMap);
            }

            if (noOhlHeader || OH_Condition(p[4], currHeader)) {
                belowAboveIncrement(curr, p[4], OH_BT + "5/0", btMap);
            }

            if (noOhlHeader || OL_Condition(p[4], currHeader)) {
                belowAboveIncrement(curr, p[4], OL_BT + "5/0", btMap);
            }

        }
        return btMap;
    }

    private Map<String, String> generateBt5Map() {
        Map<String, String> btMap = new LinkedHashMap<>();
        btMap.put("N", "0");
        initBt5WithPrefix(OO_BT, btMap);
        initBt5WithPrefix(OH_BT, btMap);
        initBt5WithPrefix(OL_BT, btMap);
        return btMap;
    }

    private void initBt5WithPrefix(String prefix, Map<String, String> btMap) {
        btMap.put(prefix + "0/5", BT_INIT);
        btMap.put(prefix + "1/4", BT_INIT);
        if (prefix.contains(OH_BT)) {
            btMap.put(prefix + "2/3", "");
            btMap.put(prefix + "3/2", BT_INIT);
        } else if (prefix.contains(OL_BT)) {
            btMap.put(prefix + "2/3", BT_INIT);
            btMap.put(prefix + "3/2", "");
        } else {
            btMap.put(prefix + "2/3", BT_INIT);
            btMap.put(prefix + "3/2", BT_INIT);
        }
        btMap.put(prefix + "4/1", BT_INIT);
        btMap.put(prefix + "5/0", BT_INIT);
    }

    private Map<String, String> backTest5_Gr(MatchProcessor type, boolean isFirstInning, int pIndex) {
        Map<String, String> btMap = generateBt5Map();

        List<Match> btMatches = type.getBtMatches();

        for (int i = 0; i < btMatches.size(); i++) {
            Match currMatch = btMatches.get(i);
            List<Match> matches = type.getGMatches(currMatch.getGroundName());
            int curr = isFirstInning ? parseInt(currMatch.getInningOne().getParams().get(pIndex)) : parseInt(currMatch.getInningTwo().getParams().get(pIndex));
            Timestamp currDate = currMatch.getMatchDate();
            List<Match> temp = new ArrayList<>(matches);
            temp.removeIf(m -> (m.getMatchDate().after(currDate)));
            List<Inning> tempInns = isFirstInning ? temp.stream().map(m -> m.getInningOne()).collect(Collectors.toList()) : temp.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());

            if (tempInns.size() < 5) {
                continue;
            }

            OHL ohl = db.getOHL(currMatch.getMatchId());
            if (ohl == null) {
                continue;
            }
            boolean noOhlHeader = false;
            Header currHeader = ohl.getHeader(isFirstInning, pIndex);
            if (currHeader != null && currHeader.isEmpty()) {
                continue;
            } else if (currHeader == null) {
                noOhlHeader = true;
            }

            if (!noOhlHeader && pIndex == 2) {
                currHeader.setOpen(curr - currHeader.getOpen());
                currHeader.setHigh((int) (curr - currHeader.getHigh()));
                currHeader.setLow(curr - currHeader.getLow());
            }

            List<Inning> sub = new ArrayList<>(tempInns.subList(0, 5));
            Collections.sort(sub, new Comparator<Inning>() {
                @Override
                public int compare(Inning o1, Inning o2) {
                    return parseInt(o1.getParams().get(pIndex))
                            - parseInt(o2.getParams().get(pIndex));
                }
            });

            int[] p = new int[5];
            for (int j = 0; j < 5; j++) {
                p[j] = parseInt(sub.get(j).getParams().get(pIndex));
            }

            incrementBt(btMap, "N");

            if (!noOhlHeader) {
                String OO_Header = "";
                if (currHeader.getOpen() < p[0]) {
                    OO_Header = "0/5";
                } else if (currHeader.getOpen() >= p[4]) {
                    OO_Header = "5/0";
                } else {
                    for (int j = 0; j <= 3; j++) {
                        if (currHeader.getOpen() >= p[j] && currHeader.getOpen() < p[j + 1]) {
                            OO_Header = (j + 1) + "/" + (4 - j);
                        }
                    }
                }
                belowAboveIncrement(curr, toInt(currHeader.getOpen()), OO_BT + OO_Header, btMap);
            }

            if (noOhlHeader || OH_Condition(p[0], currHeader)) {
                belowAboveIncrement(curr, p[0], OH_BT + "0/5", btMap);
            }

            if (noOhlHeader || OL_Condition(p[0], currHeader)) {
                belowAboveIncrement(curr, p[0], OL_BT + "0/5", btMap);
            }

            if (noOhlHeader || OH_Condition(p[1], currHeader)) {
                belowAboveIncrement(curr, p[1], OH_BT + "1/4", btMap);
            }

            if (noOhlHeader || OL_Condition(p[1], currHeader)) {
                belowAboveIncrement(curr, p[1], OL_BT + "1/4", btMap);
            }

            if (noOhlHeader || OL_Condition(p[2], currHeader)) {
                belowAboveIncrement(curr, p[2], OL_BT + "2/3", btMap);
            }

            if (noOhlHeader || OH_Condition(p[2], currHeader)) {
                belowAboveIncrement(curr, p[2], OH_BT + "3/2", btMap);
            }

            if (noOhlHeader || OH_Condition(p[3], currHeader)) {
                belowAboveIncrement(curr, p[3], OH_BT + "4/1", btMap);
            }

            if (noOhlHeader || OL_Condition(p[3], currHeader)) {
                belowAboveIncrement(curr, p[3], OL_BT + "4/1", btMap);
            }

            if (noOhlHeader || OH_Condition(p[4], currHeader)) {
                belowAboveIncrement(curr, p[4], OH_BT + "5/0", btMap);
            }

            if (noOhlHeader || OL_Condition(p[4], currHeader)) {
                belowAboveIncrement(curr, p[4], OL_BT + "5/0", btMap);
            }

        }
        return btMap;
    }

    private final String GR_0 = "Gr 0/5 | ";
    private final String GR_1 = "Gr 1/4 | ";
    private final String GR_2 = "Gr 2/3 | ";
    private final String GR_3 = "Gr 3/2 | ";
    private final String GR_4 = "Gr 4/1 | ";
    private final String GR_5 = "Gr 5/0 | ";

    private final String OO_BT = "open---";
    private final String OH_BT = "openhigh---";
    private final String OL_BT = "openlow---";

    private Map<String, String> generateBtMap() {
        Map<String, String> btMap = new LinkedHashMap<>();
        btMap.put("N", "0");
        generateWithPrefix(OO_BT, btMap);
        generateWithPrefix(OH_BT, btMap);
        generateWithPrefix(OL_BT, btMap);
        btMap.put("NG", "0");
        generateWithPrefix(GR_0, btMap);
        generateWithPrefix(GR_1, btMap);
        generateWithPrefix(GR_2, btMap);
        generateWithPrefix(GR_3, btMap);
        generateWithPrefix(GR_4, btMap);
        generateWithPrefix(GR_5, btMap);

        return btMap;
    }

    private void generateWithPrefix(String prefix, Map<String, String> btMap) {
        String NO_GR_INIT = BT_INIT;
        String XTRA_BT_INIT = "0";
        if (!prefix.startsWith("open")) {
            NO_GR_INIT = "";
        }
        if (!prefix.startsWith(OO_BT)) {
            XTRA_BT_INIT = "";
        }
        String OO_INIT = "";
        if (prefix.equals(OO_BT)) {
            OO_INIT = BT_INIT;
        }
        btMap.put(prefix + "<1", NO_GR_INIT);
        btMap.put(prefix + "1/9", BT_INIT);
        btMap.put(prefix + "2/8", BT_INIT);
        btMap.put(prefix + "3/7", BT_INIT);
        btMap.put(prefix + "4/6", BT_INIT);
        btMap.put(prefix + "5/5", OO_INIT);
        btMap.put(prefix + "6/4", BT_INIT);
        btMap.put(prefix + "7/3", BT_INIT);
        btMap.put(prefix + "8/2", BT_INIT);
        btMap.put(prefix + "9/1", BT_INIT);
        btMap.put(prefix + "10<", NO_GR_INIT);
        btMap.put(prefix + "3-3 above(6<)", XTRA_BT_INIT);
        btMap.put(prefix + "4-4 above(8<)", XTRA_BT_INIT);
        btMap.put(prefix + "3-3 below(<6)", XTRA_BT_INIT);
        btMap.put(prefix + "4-4 below(<8)", XTRA_BT_INIT);
    }

    private final String BT_INIT = "0/0/0";

    private boolean OL_Condition(int p, Header currHeader) {
        return p >= currHeader.getLow() && p < currHeader.getOpen();
    }

    private boolean OH_Condition(int p, Header currHeader) {
        return p <= currHeader.getHigh() && p > currHeader.getOpen();
    }

    private void belowAboveIncrement(int curr, int p, String header, Map<String, String> btMap) {
        if (curr < p) {
            incrementBt(btMap, header, pos.BELOW);
        } else if (curr == p) {
            incrementBt(btMap, header, pos.EQUAL);
        } else {
            incrementBt(btMap, header, pos.ABOVE);
        }
    }

    private void fillMapBt10(Map<String, String> btMap, int curr, int pIndex, List<Inning> sub, List<Inning> subA, List<Inning> subB, List<Inning> subG, Header currHeader) {
        boolean noOhlHeader = false;
        if (currHeader == null) {
            noOhlHeader = true;
        }

        if (!noOhlHeader && pIndex == 2) {
            currHeader.setOpen(curr - currHeader.getOpen());
            currHeader.setHigh((int) (curr - currHeader.getHigh()));
            currHeader.setLow(curr - currHeader.getLow());
        }

        incrementBt(btMap, "N");

        if (subG.size() == 5) {
            incrementBt(btMap, "NG");
        }
        int[] p = new int[10];
        for (int i = 0; i < 10; i++) {
            p[i] = parseInt(sub.get(i).getParams().get(pIndex));
        }

        if (!noOhlHeader) {
            String OO_Header = "";
            if (currHeader.getOpen() < p[0]) {
                OO_Header = "<1";
            } else if (currHeader.getOpen() >= p[9]) {
                OO_Header = "10<";
            } else {
                for (int i = 0; i <= 8; i++) {
                    if (currHeader.getOpen() >= p[i] && currHeader.getOpen() < p[i + 1]) {
                        OO_Header = (i + 1) + "/" + (9 - i);
                    }
                }
            }
            System.out.println("header ----->" + OO_BT + OO_Header);
            belowAboveIncrement(curr, toInt(currHeader.getOpen()), OO_BT + OO_Header, btMap);
        }

        if (noOhlHeader || OH_Condition(p[0], currHeader)) {
            belowAboveIncrement(curr, p[0], OH_BT + "<1", btMap);
        }

        if (noOhlHeader || OL_Condition(p[0], currHeader)) {
            belowAboveIncrement(curr, p[0], OL_BT + "<1", btMap);
        }

        if (noOhlHeader || OH_Condition(p[1], currHeader)) {
            belowAboveIncrement(curr, p[1], OH_BT + "1/9", btMap);
        }

        if (noOhlHeader || OL_Condition(p[1], currHeader)) {
            belowAboveIncrement(curr, p[1], OL_BT + "1/9", btMap);
        }

        if (noOhlHeader || OH_Condition(p[2], currHeader)) {
            belowAboveIncrement(curr, p[2], OH_BT + "2/8", btMap);
        }

        if (noOhlHeader || OL_Condition(p[2], currHeader)) {
            belowAboveIncrement(curr, p[2], OL_BT + "2/8", btMap);
        }

        if (noOhlHeader || OH_Condition(p[3], currHeader)) {
            belowAboveIncrement(curr, p[3], OH_BT + "3/7", btMap);
        }

        if (noOhlHeader || OL_Condition(p[3], currHeader)) {
            belowAboveIncrement(curr, p[3], OL_BT + "3/7", btMap);
        }

        if (noOhlHeader || OH_Condition(p[4], currHeader)) {
            belowAboveIncrement(curr, p[4], OH_BT + "4/6", btMap);
        }

        if (noOhlHeader || OL_Condition(p[4], currHeader)) {
            belowAboveIncrement(curr, p[4], OL_BT + "4/6", btMap);
        }

        if (noOhlHeader || OH_Condition(p[5], currHeader)) {
            belowAboveIncrement(curr, p[5], OH_BT + "6/4", btMap);
        }

        if (noOhlHeader || OL_Condition(p[5], currHeader)) {
            belowAboveIncrement(curr, p[5], OL_BT + "6/4", btMap);
        }

        if (noOhlHeader || OH_Condition(p[6], currHeader)) {
            belowAboveIncrement(curr, p[6], OH_BT + "7/3", btMap);
        }

        if (noOhlHeader || OL_Condition(p[6], currHeader)) {
            belowAboveIncrement(curr, p[6], OL_BT + "7/3", btMap);
        }

        if (noOhlHeader || OH_Condition(p[7], currHeader)) {
            belowAboveIncrement(curr, p[7], OH_BT + "8/2", btMap);
        }

        if (noOhlHeader || OL_Condition(p[7], currHeader)) {
            belowAboveIncrement(curr, p[7], OL_BT + "8/2", btMap);
        }

        if (noOhlHeader || OH_Condition(p[8], currHeader)) {
            belowAboveIncrement(curr, p[8], OH_BT + "9/1", btMap);
        }

        if (noOhlHeader || OL_Condition(p[8], currHeader)) {
            belowAboveIncrement(curr, p[8], OL_BT + "9/1", btMap);
        }

        if (noOhlHeader || OH_Condition(p[9], currHeader)) {
            belowAboveIncrement(curr, p[9], OH_BT + "10<", btMap);
        }

        if (noOhlHeader || OL_Condition(p[9], currHeader)) {
            belowAboveIncrement(curr, p[9], OL_BT + "10<", btMap);
        }

        if (curr >= parseInt(subA.get(2).getParams().get(pIndex))
                && curr >= parseInt(subB.get(2).getParams().get(pIndex))
                && curr >= parseInt(sub.get(5).getParams().get(pIndex))) {
            incrementBt(btMap, OO_BT + "3-3 above(6<)");
        }
        if (curr >= parseInt(subA.get(3).getParams().get(pIndex))
                && curr >= parseInt(subB.get(3).getParams().get(pIndex))
                && curr >= parseInt(sub.get(7).getParams().get(pIndex))) {
            incrementBt(btMap, OO_BT + "4-4 above(8<)");
        }
        if (curr <= parseInt(subA.get(2).getParams().get(pIndex))
                && curr <= parseInt(subB.get(2).getParams().get(pIndex))
                && curr <= parseInt(sub.get(5).getParams().get(pIndex))) {
            incrementBt(btMap, OO_BT + "3-3 below(<6)");
        }
        if (curr <= parseInt(subA.get(3).getParams().get(pIndex))
                && curr <= parseInt(subB.get(3).getParams().get(pIndex))
                && curr <= parseInt(sub.get(7).getParams().get(pIndex))) {
            incrementBt(btMap, OO_BT + "4-4 below(<8)");
        }

        fillGroundBT10(curr, btMap, pIndex, sub, subG, currHeader);
    }

    private void fillGroundBT10(int curr, Map<String, String> btMap, int pIndex, List<Inning> sub, List<Inning> subG, Header currHeader) {
        boolean noOhlHeader = false;
        if (currHeader == null) {
            noOhlHeader = true;
        }

        if (subG.size() == 5) {

            for (int i = 1; i < 9; i++) {
                int val = parseInt(sub.get(i).getParams().get(pIndex));
                String header;
//                boolean isNumerator;
                if (i < 5) {
                    header = i + "/" + (10 - i);
//                    isNumerator = curr >= val;
                } else {
                    header = (i + 1) + "/" + (10 - (i + 1));
//                    isNumerator = curr <= val;
                }

                if (noOhlHeader || parseInt(subG.get(0).getParams().get(pIndex)) > currHeader.getLow()) {
                    if (val <= parseInt(subG.get(0).getParams().get(pIndex))) {
//                        incrementBt(btMap, GR_0 + header, isNumerator);
                        belowAboveIncrement(curr, val, GR_0 + header, btMap);
                    }
                }

                if (noOhlHeader || parseInt(subG.get(0).getParams().get(pIndex)) > currHeader.getLow()) {
                    if (val > parseInt(subG.get(0).getParams().get(pIndex))
                            && val <= parseInt(subG.get(1).getParams().get(pIndex))) {
//                        incrementBt(btMap, GR_1 + header, isNumerator);
                        belowAboveIncrement(curr, val, GR_1 + header, btMap);
                    }
                }

                if (noOhlHeader || parseInt(subG.get(1).getParams().get(pIndex)) > currHeader.getLow()) {
                    if (val > parseInt(subG.get(1).getParams().get(pIndex))
                            && val <= parseInt(subG.get(2).getParams().get(pIndex))) {
//                        incrementBt(btMap, GR_2 + header, isNumerator);
                        belowAboveIncrement(curr, val, GR_2 + header, btMap);
                    }
                }

                if (noOhlHeader || parseInt(subG.get(3).getParams().get(pIndex)) <= currHeader.getHigh()) {
                    if (val > parseInt(subG.get(2).getParams().get(pIndex))
                            && val <= parseInt(subG.get(3).getParams().get(pIndex))) {
//                        incrementBt(btMap, GR_3 + header, isNumerator);
                        belowAboveIncrement(curr, val, GR_3 + header, btMap);
                    }
                }

                if (noOhlHeader || parseInt(subG.get(4).getParams().get(pIndex)) <= currHeader.getHigh()) {
                    if (val > parseInt(subG.get(3).getParams().get(pIndex))
                            && val <= parseInt(subG.get(4).getParams().get(pIndex))) {
//                        incrementBt(btMap, GR_4 + header, isNumerator);
                        belowAboveIncrement(curr, val, GR_4 + header, btMap);
                    }
                }

                if (noOhlHeader || parseInt(subG.get(4).getParams().get(pIndex)) <= currHeader.getHigh()) {
                    if (val > parseInt(subG.get(4).getParams().get(pIndex))) {
//                        incrementBt(btMap, GR_5 + header, isNumerator);
                        belowAboveIncrement(curr, val, GR_5 + header, btMap);
                    }
                }
            }

        }

//        0 <=1 
//        1 1 - <= 2
//        2 2 - <= 3
//        3 3 - <= 4
//        4 4 - <= 5
//        5 >5
    }

    private void incrementBt(Map<String, String> btMap, String header) {
        System.out.println(header);
        btMap.put(header, String.valueOf(parseInt(btMap.get(header)) + 1));
    }

//    private void incrementBt(Map<String, Integer> btMap, String header, boolean isNumerator) {
//        int val = btMap.get(header);
//
//        int num = val & 0xFFFF;
//        int den = (val >> 16) & 0xFFFF;
//
//        if (isNumerator) {
//            num++;
//        } else {
//            den++;
//        }
//
//        int newVal = num | (den << 16);
//
//        btMap.put(header, newVal);
//    }
    enum pos {
        BELOW,
        EQUAL,
        ABOVE
    }

    private void incrementBt(Map<String, String> btMap, String header, pos position) {
        String val = btMap.get(header);
        if (val == null) {
            throw new Error("Invalid header : " + header);
        }
        String[] tempValues = val.split("/");
        int[] value = new int[3];

        if (tempValues.length != 3) {
            throw new Error("BT legth should be 3 like : 0/0/0");
        }
        for (int i = 0; i < 3; i++) {
            value[i] = parseInt(tempValues[i]);
        }

        switch (position) {
            case BELOW:
                value[0]++;
                break;
            case EQUAL:
                value[1]++;
                break;
            case ABOVE:
                value[2]++;
                break;
        }

        String newVal = value[0] + "/" + value[1] + "/" + value[2];

        btMap.put(header, newVal);
    }

    private Map<String, String> backTest10(int pIndex, MatchProcessor matchProcessor1, MatchProcessor matchProcessor2) {

        List<Match> btMatches = matchProcessor1.getBtMatches();
        Map<String, String> btMap = generateBtMap();
        boolean isFirstInning = true;
//        Collections.sort(oneMatch, new Comparator<Match>(){
//            @Override
//            public int compare(Match o1, Match o2) {
//                return o1.getMatchDate().compareTo(o2.getMatchDate());
//            }
//        });
        Map<String, List<Match>> cache1 = new LinkedHashMap<>();
        Map<String, List<Match>> cache2 = new LinkedHashMap<>();
        Map<String, List<Match>> grCache = new LinkedHashMap<>();

        for (int i = 0; i < btMatches.size(); i++) {
            Match currMatch = btMatches.get(i);
            int curr = parseInt(currMatch.getInningOne().getParams().get(pIndex));
            Timestamp currDate = currMatch.getMatchDate();
            Predicate<Match> dateFilter = m -> (m.getMatchDate().after(currDate) || m.getMatchDate().equals(currDate));
            OHL currOhl = db.getOHL(currMatch.getMatchId());
            if (currOhl == null) {
                continue;
            }

            Header currHeader = currOhl.getHeader(isFirstInning, pIndex);
            if (currHeader != null && currHeader.isEmpty()) {
                continue;
            }
            String home = currMatch.getHomeTeam();
            String away = currMatch.getAwayTeam();
            String groundName = currMatch.getGroundName();

            if (!cache1.keySet().contains(home)) {
                cache1.put(home, matchProcessor1.getMatches(home));
            }
            if (!cache2.keySet().contains(away)) {
                cache2.put(away, matchProcessor2.getMatches(away));
            }
            if (!grCache.keySet().contains(groundName)) {
                grCache.put(groundName, matchProcessor1.getGMatches(groundName));
            }
            List<Match> oneMatch = new ArrayList<>(cache1.get(home));
            oneMatch.removeIf(m -> m.getInningOne().getParams().get(pIndex).contains("-1"));
            List<Match> twoMatch = new ArrayList<>(cache2.get(away));
            twoMatch.removeIf(m -> m.getInningOne().getParams().get(pIndex).contains("-1"));
            List<Match> grMatch = new ArrayList<>(grCache.get(groundName));
            grMatch.removeIf(m -> m.getInningOne().getParams().get(pIndex).contains("-1"));

            List<Inning> sub = new ArrayList<>();
            List<Inning> subA = new ArrayList<>();
            List<Inning> subB = new ArrayList<>();
            List<Inning> subG = new ArrayList<>();

            oneMatch.removeIf(dateFilter);
            if (oneMatch.size() < 5) {
                continue;
            }
            for (int j = 0; j < 5; j++) {
                sub.add(oneMatch.get(j).getInningOne());
                subA.add(oneMatch.get(j).getInningOne());
            }

            twoMatch.removeIf(dateFilter);
            if (twoMatch.size() < 5) {
                continue;
            }
            for (int j = 0; j < 5; j++) {
                sub.add(twoMatch.get(j).getInningOne());
                subB.add(twoMatch.get(j).getInningOne());
            }

            grMatch.removeIf(dateFilter);
            if (grMatch.size() >= 5) {
                for (int j = 0; j < 5; j++) {
                    subG.add(grMatch.get(j).getInningOne());
                }
            }

            Comparator innComp = new Comparator<Inning>() {
                @Override
                public int compare(Inning o1, Inning o2) {
                    return parseInt(o1.getParams().get(pIndex))
                            - parseInt(o2.getParams().get(pIndex));
                }
            };

            Collections.sort(sub, innComp);
            Collections.sort(subA, innComp);
            Collections.sort(subB, innComp);
            Collections.sort(subG, innComp);

            fillMapBt10(btMap, curr, pIndex, sub, subA, subB, subG, currHeader);

        }

        return btMap;
    }

    private Map<String, String> backTest10_Second(int pIndex, MatchProcessor matchProcessor1, MatchProcessor matchProcessor2) {
        Map<String, String> btMap = generateBtMap();
        List<Match> btMatches = matchProcessor1.getBtMatches();
        boolean isFirstInning = false;
//        Collections.sort(oneMatch, new Comparator<Match>(){
//            @Override
//            public int compare(Match o1, Match o2) {
//                return o1.getMatchDate().compareTo(o2.getMatchDate());
//            }
//        });
        Map<String, List<Match>> cache1 = new LinkedHashMap<>();
        Map<String, List<Match>> cache2 = new LinkedHashMap<>();
        Map<String, List<Match>> grCache = new LinkedHashMap<>();

        for (int i = 0; i < btMatches.size() - 6; i++) {
            Match currMatch = btMatches.get(i);
            int curr = parseInt(currMatch.getInningTwo().getParams().get(pIndex));
            Timestamp currDate = currMatch.getMatchDate();
            Predicate<Match> dateFilter = m -> (m.getMatchDate().after(currDate) || m.getMatchDate().equals(currDate));
            OHL currOhl = db.getOHL(currMatch.getMatchId());
            if (currOhl == null) {
                continue;
            }

            Header currHeader = currOhl.getHeader(isFirstInning, pIndex);
            if (currHeader != null && currHeader.isEmpty()) {
                continue;
            }

            String home = currMatch.getHomeTeam();
            String away = currMatch.getAwayTeam();
            String groundName = currMatch.getGroundName();

            if (!cache1.keySet().contains(home)) {
                cache1.put(home, matchProcessor1.getMatches(home));
            }
            if (!cache2.keySet().contains(away)) {
                cache2.put(away, matchProcessor2.getMatches(away));
            }
            if (!grCache.keySet().contains(groundName)) {
                grCache.put(groundName, matchProcessor1.getGMatches(groundName));
            }
            List<Match> oneMatch = new ArrayList<>(cache1.get(home));
            oneMatch.removeIf(m -> m.getInningTwo().getParams().get(pIndex).contains("-1"));
            List<Match> twoMatch = new ArrayList<>(cache2.get(away));
            twoMatch.removeIf(m -> m.getInningTwo().getParams().get(pIndex).contains("-1"));
            List<Match> grMatch = new ArrayList<>(grCache.get(groundName));
            grMatch.removeIf(m -> m.getInningTwo().getParams().get(pIndex).contains("-1"));

            List<Inning> sub = new ArrayList<>();
            List<Inning> subA = new ArrayList<>();
            List<Inning> subB = new ArrayList<>();
            List<Inning> subG = new ArrayList<>();

            oneMatch.removeIf(dateFilter);
            if (oneMatch.size() < 5) {
                continue;
            }
            for (int j = 0; j < 5; j++) {
                sub.add(oneMatch.get(j).getInningTwo());
                subA.add(oneMatch.get(j).getInningTwo());
            }

            twoMatch.removeIf(dateFilter);
            if (twoMatch.size() < 5) {
                continue;
            }
            for (int j = 0; j < 5; j++) {
                sub.add(twoMatch.get(j).getInningTwo());
                subB.add(twoMatch.get(j).getInningTwo());
            }

            grMatch.removeIf(dateFilter);
            if (grMatch.size() >= 5) {
                for (int j = 0; j < 5; j++) {
                    subG.add(grMatch.get(j).getInningTwo());
                }
            }

            Comparator innComp = new Comparator<Inning>() {
                @Override
                public int compare(Inning o1, Inning o2) {
                    return parseInt(o1.getParams().get(pIndex))
                            - parseInt(o2.getParams().get(pIndex));
                }
            };

            Collections.sort(sub, innComp);
            Collections.sort(subA, innComp);
            Collections.sort(subB, innComp);
            Collections.sort(subG, innComp);

            fillMapBt10(btMap, curr, pIndex, sub, subA, subB, subG, currHeader);

        }

        return btMap;
    }

    private Map<String, String> backTest10_Test(String teamName, List<testMatch> oneMatch, int pIndex, TestProcessor testProcessor, InningCaller inningCaller, Location oppType) {
        Map<String, String> btMap = generateBtMap();

//        Collections.sort(oneMatch, new Comparator<Match>(){
//            @Override
//            public int compare(Match o1, Match o2) {
//                return o1.getMatchDate().compareTo(o2.getMatchDate());
//            }
//        });
        for (int i = 0; i < oneMatch.size() - 6; i++) {
            testMatch currMatch = oneMatch.get(i);
            int curr = parseInt(inningCaller.call(currMatch).getParams().get(pIndex));
            Date currDate = currMatch.getMatchDate();
            String oppTeam = currMatch.getHomeTeam().equals(teamName) ? currMatch.getAwayTeam() : currMatch.getHomeTeam();
            String groundName = currMatch.getGroundName();

            List<testMatch> twoMatch = testProcessor.getMatches(oppTeam, oppType);
            twoMatch.removeIf(m -> inningCaller.call(m).getParams().get(pIndex).contains("-1"));
            List<testMatch> grMatch = testProcessor.getGMatches(groundName);
            grMatch.removeIf(m -> inningCaller.call(m).getParams().get(pIndex).contains("-1"));

            List<Inning> sub = new ArrayList<>();
            List<Inning> subA = new ArrayList<>();
            List<Inning> subB = new ArrayList<>();
            List<Inning> subG = new ArrayList<>();

            for (int j = i + 1; j < i + 6; j++) {
                sub.add(inningCaller.call(oneMatch.get(j)));
                subA.add(inningCaller.call(oneMatch.get(j)));
            }

            twoMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
            if (twoMatch.size() < 5) {
                continue;
            }
            for (int j = 0; j < 5; j++) {
                sub.add(inningCaller.call(twoMatch.get(j)));
                subB.add(inningCaller.call(twoMatch.get(j)));
            }

            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
            if (grMatch.size() >= 5) {
                for (int j = 0; j < 5; j++) {
                    subG.add(inningCaller.call(grMatch.get(j)));
                }
            }

            Comparator innComp = new Comparator<Inning>() {
                @Override
                public int compare(Inning o1, Inning o2) {
                    return parseInt(o1.getParams().get(pIndex))
                            - parseInt(o2.getParams().get(pIndex));
                }
            };

            Collections.sort(sub, innComp);
            Collections.sort(subA, innComp);
            Collections.sort(subB, innComp);
            Collections.sort(subG, innComp);

//            fillMapBt10(btMap, curr, pIndex, sub, subA, subB, subG);
        }

        return btMap;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        CricDB db = new CricDB();
        final Date backDate;
        java.util.Date tempDate = new java.util.Date();

        int matchType = parseInt(request.getParameter("tournament"));

        String teamOne = request.getParameter("teamName1");
        String teamTwo = request.getParameter("teamName2");
        String favInput = request.getParameter("favSelect");
        String biasInput = request.getParameter("biasSelect");

        FavSide favSide;
        String favTeamName;
        switch (favInput) {
            case "B":
                favSide = FavSide.Batting;
                favTeamName = teamOne;
                break;
            case "C":
                favSide = FavSide.Chasing;
                favTeamName = teamTwo;
                break;
            default:
                throw new ServletException("Select fav side");
        }

        request.setAttribute("favTeamName", favTeamName);
        request.setAttribute("bias", biasInput);

        boolean isFavBatting = favSide.equals(FavSide.Batting);
        request.setAttribute("isFavBatting", isFavBatting);
        boolean isFavChasing = favSide.equals(FavSide.Chasing);
        request.setAttribute("isFavChasing", isFavChasing);

        String groundName = request.getParameter("groundName");
        String bdate = request.getParameter("backDate");
        if (bdate != null && !bdate.isEmpty()) {
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                tempDate = df.parse(bdate);
            } catch (Exception ex) {
                System.out.println("Date parse error =======================================");
                ex.printStackTrace();
            }
        }
        backDate = new Date(tempDate.getTime());

        if (matchType == 1) {
//            test(request, response);

            Location homeLocation = db.checkLocationOf(teamOne, teamTwo, groundName, matchType);

            if (homeLocation.equals(Location.NONE)) {
                throw new IOException("Test Ground not defined properly!!!!");
            }

            String hometeam = homeLocation.equals(Location.HOME) ? teamOne : teamTwo;

            Location oneSide = homeLocation;
            Location twoSide = homeLocation.equals(Location.AWAY) ? Location.HOME : Location.AWAY;

            TestProcessor type0 = new TestProcessor() {
                @Override
                public List<testMatch> getMatches(String teamName, Location xType) {
                    return process(db.getTestMatches(teamName, 0, xType));
                }

                @Override
                public List<testMatch> getGMatches(String groundName) {
                    return process(db.getTestGroundInfo(groundName));
                }

                @Override
                public List<testMatch> process(List<testMatch> matches) {
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));

                    for (testMatch q : matches) {

                        int fours = parseInt(q.getInningOne1().getParams().get(1))
                                + parseInt(q.getInningOne2().getParams().get(1))
                                + parseInt(q.getInningTwo1().getParams().get(1))
                                + parseInt(q.getInningTwo2().getParams().get(1));

                        int sixes = parseInt(q.getInningOne1().getParams().get(2))
                                + parseInt(q.getInningOne2().getParams().get(2))
                                + parseInt(q.getInningTwo1().getParams().get(2))
                                + parseInt(q.getInningTwo2().getParams().get(2));

                        int total = fours + sixes;

                        Inning m = q.getInningOne1();
                        List<String> temp = m.getParams();
                        temp.set(1, String.valueOf(fours));
                        temp.set(2, String.valueOf(sixes));
                        temp.add(6, String.valueOf(total));
                        m.setParams(temp);
                        q.setInningOne1(m);
                    }
                    return matches;
                }
            };

            TestProcessor type1 = new TestProcessor() {
                @Override
                public List<testMatch> getMatches(String teamName, Location xType) {
                    return process(db.getTestMatches(teamName, 1, xType));
                }

                @Override
                public List<testMatch> getGMatches(String groundName) {
                    return process(db.getTestGroundInfo(groundName));
                }

                @Override
                public List<testMatch> process(List<testMatch> matches) {
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    return matches;
                }
            };

            TestProcessor type2 = new TestProcessor() {
                @Override
                public List<testMatch> getMatches(String teamName, Location xType) {
                    return process(db.getTestMatches(teamName, 2, xType));
                }

                @Override
                public List<testMatch> getGMatches(String groundName) {
                    return process(db.getTestGroundInfo(groundName));
                }

                @Override
                public List<testMatch> process(List<testMatch> matches) {
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    return matches;
                }
            };

            InningCaller One1Inning = (testMatch match) -> match.getInningOne1();
            InningCaller One2Inning = (testMatch match) -> match.getInningOne2();
            InningCaller Two1Inning = (testMatch match) -> match.getInningTwo1();
            InningCaller Two2Inning = (testMatch match) -> match.getInningTwo2();

            int totalRunsIndex = 0;
            int foursIndex = 1;
            int sixesIndex = 2;
            int firstWktIndex = 3;
            int fiveWktIndex = 4;
            int totBoundariesIndex = 6;
            Head_to_head:
            {
                // <editor-fold defaultstate="collapsed">
                List<testMatch> matches = db.getTestHth(teamOne, teamTwo);
                List<Inning> hth = new ArrayList<>();
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                for (int i = 0; i < Math.min(5, matches.size()); i++) {
                    testMatch q = matches.get(i);

                    String worl = "";
                    String BorC = "";
                    if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                        BorC = "B";
                    } else {
                        BorC = "C";
                    }

                    String BCW = q.getBCW();
                    if (BCW.equals("B") || BCW.equals("C")) {
                        if (BCW.equals(BorC)) {
                            worl = "W";
                        } else {
                            worl = "L";
                        }
                    } else if (BCW.equals("T")) {
                        worl = "T";
                    } else {
                        continue;
                    }
                    String bcwl = BorC + "/" + worl;

                    Inning m = q.getInningOne1();
                    List<String> params = m.getParams();
                    params.set(5, bcwl);
                    m.setParams(params);
                    hth.add(m);
                }
                request.setAttribute("hth", hth);
                // </editor-fold>
            }

            Form_guide:
            {
                // <editor-fold defaultstate="collapsed">
                A:
                {
                    List<testMatch> matches = db.getTestMatches(teamOne, 0, oneSide);
                    List<Inning> selects = new ArrayList<>();
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(5, matches.size()); i++) {
                        testMatch q = matches.get(i);

                        String worl = "";
                        String BorC = "";
                        if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                            BorC = "B";
                        } else {
                            BorC = "C";
                        }

                        String BCW = q.getBCW();
                        if (BCW.equals("B") || BCW.equals("C")) {
                            if (BCW.equals(BorC)) {
                                worl = "W";
                            } else {
                                worl = "L";
                            }
                        } else if (BCW.equals("T")) {
                            worl = "T";
                        } else {
                            continue;
                        }
                        String bcwl = BorC + "/" + worl;

                        Inning m = q.getInningOne1();
                        List<String> params = m.getParams();
                        params.set(5, bcwl);
                        m.setParams(params);
                        selects.add(m);
                    }
                    request.setAttribute("FG_A", selects.subList(0, Math.min(5, selects.size())));
                }
                B:
                {
                    List<testMatch> matches = db.getTestMatches(teamTwo, 0, twoSide);
                    List<Inning> selects = new ArrayList<>();
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(5, matches.size()); i++) {
                        testMatch q = matches.get(i);

                        String worl = "";
                        String BorC = "";
                        if (q.getHomeTeam().equalsIgnoreCase(teamTwo)) {
                            BorC = "B";
                        } else {
                            BorC = "C";
                        }

                        String BCW = q.getBCW();
                        if (BCW.equals("B") || BCW.equals("C")) {
                            if (BCW.equals(BorC)) {
                                worl = "W";
                            } else {
                                worl = "L";
                            }
                        } else if (BCW.equals("T")) {
                            worl = "T";
                        } else {
                            continue;
                        }
                        String bcwl = BorC + "/" + worl;

                        Inning m = q.getInningOne1();
                        List<String> params = m.getParams();
                        params.set(5, bcwl);
                        m.setParams(params);
                        selects.add(m);
                    }
                    request.setAttribute("FG_B", selects.subList(0, Math.min(5, selects.size())));
                }
                // </editor-fold>
            }

            Fours_Sixes_Total_boundaries:
            {
                // <editor-fold defaultstate="collapsed">
                A:
                {
                    List<testMatch> matches = type0.getMatches(teamOne, oneSide);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                    request.setAttribute("FST_A", selects);

                    request.setAttribute("F_A_bt", backTest5_Test(selects, foursIndex));
                    request.setAttribute("F_TA_bt", backTest10_Test(teamOne, matches, foursIndex, type0, One1Inning, twoSide));

                    request.setAttribute("S_A_bt", backTest5_Test(selects, sixesIndex));
                    request.setAttribute("S_TA_bt", backTest10_Test(teamOne, matches, sixesIndex, type0, One1Inning, twoSide));

                    request.setAttribute("T_A_bt", backTest5_Test(selects, totBoundariesIndex));
                    request.setAttribute("T_TA_bt", backTest10_Test(teamOne, matches, totBoundariesIndex, type0, One1Inning, twoSide));

                }
                B:
                {
                    List<testMatch> matches = type0.getMatches(teamTwo, twoSide);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                    request.setAttribute("FST_B", selects);

                    request.setAttribute("F_B_bt", backTest5_Test(selects, foursIndex));
                    request.setAttribute("F_TB_bt", backTest10_Test(teamTwo, matches, foursIndex, type0, One1Inning, oneSide));

                    request.setAttribute("S_B_bt", backTest5_Test(selects, sixesIndex));
                    request.setAttribute("S_TB_bt", backTest10_Test(teamTwo, matches, sixesIndex, type0, One1Inning, oneSide));

                    request.setAttribute("T_B_bt", backTest5_Test(selects, totBoundariesIndex));
                    request.setAttribute("T_TB_bt", backTest10_Test(teamTwo, matches, totBoundariesIndex, type0, One1Inning, oneSide));
                }
                G:
                {
                    List<testMatch> matches = type0.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                    request.setAttribute("FST_G", selects);

//                    request.setAttribute("F_G_bt", backTest5_Gr(selects, foursIndex));
//
//                    request.setAttribute("S_G_bt", backTest5_Gr(selects, sixesIndex));
//
//                    request.setAttribute("T_G_bt", backTest5_Gr(selects, totBoundariesIndex));
                }
                // </editor-fold>
            }

            First_Inning:
            {
                // <editor-fold defaultstate="collapsed">
                Total_and_first_wicket:
                {
                    A:
                    {
                        List<testMatch> matches = type1.getMatches(teamOne, oneSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                        request.setAttribute("FX_A", selects);

                        request.setAttribute("FTR_A_bt", backTest5_Test(selects, totalRunsIndex));
                        request.setAttribute("FTR_TA_bt", backTest10_Test(teamOne, matches, totalRunsIndex, type2, One1Inning, twoSide));

                        request.setAttribute("FFW_A_bt", backTest5_Test(selects, firstWktIndex));
                        request.setAttribute("FFW_TA_bt", backTest10_Test(teamOne, matches, firstWktIndex, type2, One1Inning, twoSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type2.getMatches(teamTwo, twoSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                        request.setAttribute("FX_B", selects);

                        request.setAttribute("FTR_B_bt", backTest5_Test(selects, totalRunsIndex));
                        request.setAttribute("FTR_TB_bt", backTest10_Test(teamTwo, matches, totalRunsIndex, type1, One1Inning, oneSide));

                        request.setAttribute("FFW_B_bt", backTest5_Test(selects, firstWktIndex));
                        request.setAttribute("FFW_TB_bt", backTest10_Test(teamTwo, matches, firstWktIndex, type1, One1Inning, oneSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                        request.setAttribute("FX_G", selects);

//                        request.setAttribute("FTR_G_bt", backTest5_Gr(selects, totalRunsIndex));
//                        request.setAttribute("FFW_G_bt", backTest5_Gr(selects, firstWktIndex));
                    }
                }
                After_five_wickets:
                {
                    TestProcessor type1_N15 = new TestProcessor() {
                        @Override
                        public List<testMatch> getMatches(String teamName, Location xType) {
                            return process(type1.getMatches(teamName, xType));
                        }

                        @Override
                        public List<testMatch> getGMatches(String groundName) {
                            return process(type1.getGMatches(groundName));
                        }

                        @Override
                        public List<testMatch> process(List<testMatch> matches) {
                            matches.removeIf(m -> m.getInningOne1().getParams().get(fiveWktIndex).equals("-1"));
                            return matches;
                        }
                    };
                    TestProcessor type2_N15 = new TestProcessor() {
                        @Override
                        public List<testMatch> getMatches(String teamName, Location xType) {
                            return process(type2.getMatches(teamName, xType));
                        }

                        @Override
                        public List<testMatch> getGMatches(String groundName) {
                            return process(type2.getGMatches(groundName));
                        }

                        @Override
                        public List<testMatch> process(List<testMatch> matches) {
                            matches.removeIf(m -> m.getInningOne1().getParams().get(fiveWktIndex).equals("-1"));
                            return matches;
                        }
                    };
                    A:
                    {
                        List<testMatch> matches = type1.getMatches(teamOne, oneSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                        request.setAttribute("F5_A", selects);

                        request.setAttribute("F5_A_bt", backTest5_Test(selects, fiveWktIndex));
                        request.setAttribute("F5_TA_bt", backTest10_Test(teamOne, matches, fiveWktIndex, type2, One1Inning, twoSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type2.getMatches(teamTwo, twoSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                        request.setAttribute("F5_B", selects);

                        request.setAttribute("F5_B_bt", backTest5_Test(selects, fiveWktIndex));
                        request.setAttribute("F5_TB_bt", backTest10_Test(teamTwo, matches, fiveWktIndex, type1, One1Inning, oneSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                        request.setAttribute("F5_G", selects);

//                        request.setAttribute("F5_G_bt", backTest5_Gr(selects, fiveWktIndex));
                    }
                }

                // </editor-fold>
            }

            Second_Inning:
            {
                // <editor-fold defaultstate="collapsed">
                Total_and_first_wicket:
                {
                    A:
                    {
                        List<testMatch> matches = type2.getMatches(teamTwo, twoSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());

                        request.setAttribute("SX_A", selects);

                        request.setAttribute("STR_A_bt", backTest5_Test(selects, totalRunsIndex));
                        request.setAttribute("STR_TA_bt", backTest10_Test(teamTwo, matches, totalRunsIndex, type1, Two1Inning, oneSide));

                        request.setAttribute("SFW_A_bt", backTest5_Test(selects, firstWktIndex));
                        request.setAttribute("SFW_TA_bt", backTest10_Test(teamTwo, matches, firstWktIndex, type1, Two1Inning, oneSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type1.getMatches(teamOne, oneSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());

                        request.setAttribute("SX_B", selects);

                        request.setAttribute("STR_B_bt", backTest5_Test(selects, totalRunsIndex));
                        request.setAttribute("STR_TB_bt", backTest10_Test(teamOne, matches, totalRunsIndex, type2, Two1Inning, twoSide));

                        request.setAttribute("SFW_B_bt", backTest5_Test(selects, firstWktIndex));
                        request.setAttribute("SFW_TB_bt", backTest10_Test(teamOne, matches, firstWktIndex, type2, Two1Inning, twoSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());

                        request.setAttribute("SX_G", selects);

//                        request.setAttribute("STR_G_bt", backTest5_Gr(selects, totalRunsIndex));
//                        request.setAttribute("SFW_G_bt", backTest5_Gr(selects, firstWktIndex));
                    }
                }
                After_five_wickets:
                {
                    TestProcessor type1_N25 = new TestProcessor() {
                        @Override
                        public List<testMatch> getMatches(String teamName, Location xType) {
                            return process(type1.getMatches(teamName, xType));
                        }

                        @Override
                        public List<testMatch> getGMatches(String groundName) {
                            return process(type1.getGMatches(groundName));
                        }

                        @Override
                        public List<testMatch> process(List<testMatch> matches) {
                            matches.removeIf(m -> m.getInningTwo1().getParams().get(fiveWktIndex).equals("-1"));
                            return matches;
                        }
                    };
                    TestProcessor type2_N25 = new TestProcessor() {
                        @Override
                        public List<testMatch> getMatches(String teamName, Location xType) {
                            return process(type2.getMatches(teamName, xType));
                        }

                        @Override
                        public List<testMatch> getGMatches(String groundName) {
                            return process(type2.getGMatches(groundName));
                        }

                        @Override
                        public List<testMatch> process(List<testMatch> matches) {
                            matches.removeIf(m -> m.getInningTwo1().getParams().get(fiveWktIndex).equals("-1"));
                            return matches;
                        }
                    };
                    A:
                    {
                        List<testMatch> matches = type2_N25.getMatches(teamTwo, twoSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());

                        request.setAttribute("S5_A", selects);

                        request.setAttribute("S5_A_bt", backTest5_Test(selects, fiveWktIndex));
                        request.setAttribute("S5_TA_bt", backTest10_Test(teamTwo, matches, fiveWktIndex, type1_N25, Two1Inning, oneSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type1_N25.getMatches(teamOne, oneSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());

                        request.setAttribute("S5_B", selects);

                        request.setAttribute("S5_B_bt", backTest5_Test(selects, fiveWktIndex));
                        request.setAttribute("S5_TB_bt", backTest10_Test(teamOne, matches, fiveWktIndex, type2_N25, Two1Inning, twoSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1_N25.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());

                        request.setAttribute("S5_G", selects);

//                        request.setAttribute("S5_G_bt", backTest5_Gr(selects, fiveWktIndex));
                    }
                }

                // </editor-fold>
            }

            Third_Inning:
            {
                // <editor-fold defaultstate="collapsed">
                Total_and_first_wicket:
                {
                    A:
                    {
                        List<testMatch> matches = type1.getMatches(teamOne, oneSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());

                        request.setAttribute("TX_A", selects);

                        request.setAttribute("TTR_A_bt", backTest5_Test(selects, totalRunsIndex));
                        request.setAttribute("TTR_TA_bt", backTest10_Test(teamOne, matches, totalRunsIndex, type2, One2Inning, twoSide));

                        request.setAttribute("TFW_A_bt", backTest5_Test(selects, firstWktIndex));
                        request.setAttribute("TFW_TA_bt", backTest10_Test(teamOne, matches, firstWktIndex, type2, One2Inning, twoSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type2.getMatches(teamTwo, twoSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());

                        request.setAttribute("TX_B", selects);

                        request.setAttribute("TTR_B_bt", backTest5_Test(selects, totalRunsIndex));
                        request.setAttribute("TTR_TB_bt", backTest10_Test(teamTwo, matches, totalRunsIndex, type1, One2Inning, oneSide));

                        request.setAttribute("TFW_B_bt", backTest5_Test(selects, firstWktIndex));
                        request.setAttribute("TFW_TB_bt", backTest10_Test(teamTwo, matches, firstWktIndex, type1, One2Inning, oneSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());

                        request.setAttribute("TX_G", selects);

//                        request.setAttribute("TTR_G_bt", backTest5_Gr(selects, totalRunsIndex));
//                        request.setAttribute("TFW_G_bt", backTest5_Gr(selects, firstWktIndex));
                    }
                }
                After_five_wickets:
                {
                    TestProcessor type1_N35 = new TestProcessor() {
                        @Override
                        public List<testMatch> getMatches(String teamName, Location xType) {
                            return process(type1.getMatches(teamName, xType));
                        }

                        @Override
                        public List<testMatch> getGMatches(String groundName) {
                            return process(type1.getGMatches(groundName));
                        }

                        @Override
                        public List<testMatch> process(List<testMatch> matches) {
                            matches.removeIf(m -> m.getInningOne2().getParams().get(fiveWktIndex).equals("-1"));
                            return matches;
                        }
                    };
                    TestProcessor type2_N35 = new TestProcessor() {
                        @Override
                        public List<testMatch> getMatches(String teamName, Location xType) {
                            return process(type2.getMatches(teamName, xType));
                        }

                        @Override
                        public List<testMatch> getGMatches(String groundName) {
                            return process(type2.getGMatches(groundName));
                        }

                        @Override
                        public List<testMatch> process(List<testMatch> matches) {
                            matches.removeIf(m -> m.getInningOne2().getParams().get(fiveWktIndex).equals("-1"));
                            return matches;
                        }
                    };
                    A:
                    {
                        List<testMatch> matches = type1_N35.getMatches(teamOne, oneSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());

                        request.setAttribute("T5_A", selects);

                        request.setAttribute("T5_A_bt", backTest5_Test(selects, fiveWktIndex));
                        request.setAttribute("T5_TA_bt", backTest10_Test(teamOne, matches, fiveWktIndex, type2_N35, One2Inning, twoSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type2_N35.getMatches(teamTwo, twoSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());

                        request.setAttribute("T5_B", selects);

                        request.setAttribute("T5_B_bt", backTest5_Test(selects, fiveWktIndex));
                        request.setAttribute("T5_TB_bt", backTest10_Test(teamTwo, matches, fiveWktIndex, type1_N35, One2Inning, oneSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1_N35.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());

                        request.setAttribute("T5_G", selects);

//                        request.setAttribute("T5_G_bt", backTest5_Gr(selects, fiveWktIndex));
                    }
                }

                // </editor-fold>
            }

            Fourth_Inning:
            {
                // <editor-fold defaultstate="collapsed">
                Total_and_first_wicket:
                {
                    A:
                    {
                        List<testMatch> matches = type2.getMatches(teamTwo, twoSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());

                        request.setAttribute("QX_A", selects);

                        request.setAttribute("QTR_A_bt", backTest5_Test(selects, totalRunsIndex));
                        request.setAttribute("QTR_TA_bt", backTest10_Test(teamTwo, matches, totalRunsIndex, type1, Two2Inning, oneSide));

                        request.setAttribute("QFW_A_bt", backTest5_Test(selects, firstWktIndex));
                        request.setAttribute("QFW_TA_bt", backTest10_Test(teamTwo, matches, firstWktIndex, type1, Two2Inning, oneSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type1.getMatches(teamOne, oneSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());

                        request.setAttribute("QX_B", selects);

                        request.setAttribute("QTR_B_bt", backTest5_Test(selects, totalRunsIndex));
                        request.setAttribute("QTR_TB_bt", backTest10_Test(teamOne, matches, totalRunsIndex, type2, Two2Inning, twoSide));

                        request.setAttribute("QFW_B_bt", backTest5_Test(selects, firstWktIndex));
                        request.setAttribute("QFW_TB_bt", backTest10_Test(teamOne, matches, firstWktIndex, type2, Two2Inning, twoSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());

                        request.setAttribute("QX_G", selects);

//                        request.setAttribute("QTR_G_bt", backTest5_Gr(selects, totalRunsIndex));
//                        request.setAttribute("QFW_G_bt", backTest5_Gr(selects, firstWktIndex));
                    }
                }
                After_five_wickets:
                {
                    TestProcessor type1_N25 = new TestProcessor() {
                        @Override
                        public List<testMatch> getMatches(String teamName, Location xType) {
                            return process(type1.getMatches(teamName, xType));
                        }

                        @Override
                        public List<testMatch> getGMatches(String groundName) {
                            return process(type1.getGMatches(groundName));
                        }

                        @Override
                        public List<testMatch> process(List<testMatch> matches) {
                            matches.removeIf(m -> m.getInningTwo2().getParams().get(fiveWktIndex).equals("-1"));
                            return matches;
                        }
                    };
                    TestProcessor type2_N25 = new TestProcessor() {
                        @Override
                        public List<testMatch> getMatches(String teamName, Location xType) {
                            return process(type2.getMatches(teamName, xType));
                        }

                        @Override
                        public List<testMatch> getGMatches(String groundName) {
                            return process(type2.getGMatches(groundName));
                        }

                        @Override
                        public List<testMatch> process(List<testMatch> matches) {
                            matches.removeIf(m -> m.getInningTwo2().getParams().get(fiveWktIndex).equals("-1"));
                            return matches;
                        }
                    };
                    A:
                    {
                        List<testMatch> matches = type2_N25.getMatches(teamTwo, twoSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());

                        request.setAttribute("Q5_A", selects);

                        request.setAttribute("Q5_A_bt", backTest5_Test(selects, fiveWktIndex));
                        request.setAttribute("Q5_TA_bt", backTest10_Test(teamTwo, matches, fiveWktIndex, type1_N25, Two2Inning, oneSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type1_N25.getMatches(teamOne, oneSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());

                        request.setAttribute("Q5_B", selects);

                        request.setAttribute("Q5_B_bt", backTest5_Test(selects, fiveWktIndex));
                        request.setAttribute("Q5_TB_bt", backTest10_Test(teamOne, matches, fiveWktIndex, type2_N25, Two2Inning, twoSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1_N25.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());

                        request.setAttribute("Q5_G", selects);

//                        request.setAttribute("Q5_G_bt", backTest5_Gr(selects, fiveWktIndex));
                    }
                }

                // </editor-fold>
            }

            List<String> headers = new ArrayList();
            headers = db.getHeaders(matchType);
            request.setAttribute("matchType", matchType);
            request.setAttribute("headers", headers);
            request.setAttribute("teamOne", teamOne);
            request.setAttribute("teamTwo", teamTwo);
            request.setAttribute("groundName", groundName);

            request.setAttribute("hometeam", hometeam);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/testresults.jsp");
            dispatcher.forward(request, response);

        } else {

            MatchProcessor type0 = new MatchProcessor() {
                // <editor-fold defaultstate="collapsed">
                @Override
                public List<Match> getMatches(String teamName) {
//                    List<Match> matches = db.getMatches(teamName, matchType, 0);
//                    return process(matches);
                    return null;
                }

                @Override
                public List<Match> getBtMatches() {
                    List<Match> matches = db.getAllFavMatches(matchType);
                    matches.removeIf(m -> {
                        String[] favDeets = db.getFavourites(m.getMatchId());
                        String chosenTeam = isFavBatting ? m.getHomeTeam() : m.getAwayTeam();
                        return (favDeets == null || (!favDeets[0].equals(chosenTeam) && !favDeets[1].equals(biasInput)));
                    });

                    return matches;
                }

                @Override
                public List<Match> getGMatches(String groundName) {
                    List<Match> matches = db.getGroundInfo(groundName, matchType);
                    return process(matches);
                }

                public List<Match> process(List<Match> matches) {
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    matches.removeIf(m -> (m.getResult().contains("D/L")));
                    for (int i = 0; i < matches.size(); i++) {
                        Inning temp = matches.get(i).getInningOne();
                        int fours = parseInt(matches.get(i).getInningOne().getParams().get(4))
                                + parseInt(matches.get(i).getInningTwo().getParams().get(4));
                        int sixes = parseInt(matches.get(i).getInningOne().getParams().get(5))
                                + parseInt(matches.get(i).getInningTwo().getParams().get(5));
                        int totalB = fours + sixes;
                        List<String> ps = temp.getParams();
                        ps.set(4, String.valueOf(fours));
                        ps.set(5, String.valueOf(sixes));
                        ps.add(8, String.valueOf(totalB));
                        temp.setParams(ps);
                        matches.get(i).setInningOne(temp);
                    }
                    return matches;
                }
                // </editor-fold>
            };

            MatchProcessor type1 = new MatchProcessor() {
                // <editor-fold defaultstate="collapsed">   
                @Override
                public List<Match> getMatches(String teamName) {
                    List<Match> matches = db.getMatches(teamName, matchType, 1);
                    return process(matches);
                }

                @Override
                public List<Match> getBtMatches() {
                    List<Match> matches = db.getAllFavMatches(matchType);
                    matches.removeIf(m -> {
                        String[] favDeets = db.getFavourites(m.getMatchId());
                        String chosenTeam = isFavBatting ? m.getHomeTeam() : m.getAwayTeam();
                        return (favDeets == null || (!favDeets[0].equals(chosenTeam) && !favDeets[1].equals(biasInput)));
                    });

                    return matches;
                }

                @Override

                public List<Match> getGMatches(String groundName) {
                    return type0.getGMatches(groundName);
                }

                @Override
                public List<Match> process(List<Match> matches) {
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < matches.size(); i++) {
                        Inning temp = matches.get(i).getInningOne();
                        List<String> ps = temp.getParams();
                        if (matches.get(i).getResult().contains("D/L")) {
                            ps.set(7, ps.get(7) + " (D/L)");
                        }
                        temp.setParams(ps);
                        matches.get(i).setInningOne(temp);

                        Inning temp2 = matches.get(i).getInningTwo();
                        List<String> ps2 = temp2.getParams();
                        if (matches.get(i).getResult().contains("D/L")) {
                            ps2.set(7, ps2.get(7) + " (D/L)");
                        }
                        temp2.setParams(ps2);
                        matches.get(i).setInningTwo(temp2);
                    }
                    return matches;
                }
                // </editor-fold>
            };

            MatchProcessor type1_NoMajQuit = new MatchProcessor() {
                // <editor-fold defaultstate="collapsed">
                @Override
                public List<Match> getMatches(String teamName) {
                    return process(type1.getMatches(teamName));
                }

                @Override
                public List<Match> getBtMatches() {
                    return process(type1.getBtMatches());
                }

                @Override
                public List<Match> getGMatches(String groundName) {
                    return process(type1.getGMatches(groundName));
                }

                @Override
                public List<Match> process(List<Match> matches) {
                    matches.removeIf(m -> m.getResult().contains("MAJ_QUIT"));
                    return matches;
                }
                // </editor-fold>
            };

            MatchProcessor type2 = new MatchProcessor() {
                // <editor-fold defaultstate="collapsed">
                @Override
                public List<Match> getMatches(String teamName) {
                    List<Match> matches = db.getMatches(teamName, matchType, 2);
                    return process(matches);
                }

                @Override
                public List<Match> getBtMatches() {
                    return type1.getBtMatches();
                }

                @Override
                public List<Match> getGMatches(String groundName) {
                    return type1.getGMatches(groundName);
                }

                @Override
                public List<Match> process(List<Match> matches) {
                    return type1.process(matches);
                }
                // </editor-fold>
            };

            MatchProcessor type2_NoMajQuit = new MatchProcessor() {
                // <editor-fold defaultstate="collapsed">
                @Override
                public List<Match> getMatches(String teamName) {
                    return process(type2.getMatches(teamName));
                }

                @Override
                public List<Match> getBtMatches() {
                    return process(type2.getBtMatches());
                }

                @Override
                public List<Match> getGMatches(String groundName) {
                    return type1_NoMajQuit.getGMatches(groundName);
                }

                @Override
                public List<Match> process(List<Match> matches) {
                    return type1_NoMajQuit.process(matches);
                }
                // </editor-fold>
            };

            Head_to_head:
            {
                // <editor-fold defaultstate="collapsed">
                List<Inning> hth = new ArrayList<>();

                List<Match> matches = db.getHth(matchType, teamOne, teamTwo);
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                for (int i = 0; i < matches.size(); i++) {
                    Match q = matches.get(i);
                    Inning m = q.getInningOne();
                    List<String> params = m.getParams();

                    String res = q.getResult();
                    String worl = "";

                    String BorC = "";

                    if (q.getHomeTeam().equalsIgnoreCase(favTeamName)) {
                        BorC = "B";
                    } else {
                        BorC = "C";
                    }

                    String BCW = q.getBCW();
                    if (BCW.equals("B") || BCW.equals("C")) {
                        if (BCW.equals(BorC)) {
                            worl = "W";
                        } else {
                            worl = "L";
                        }
                    } else if (BCW.equals("T")) {
                        worl = "T";
                    } else {
                        System.out.println("-------------------skipping h2h point for bcw=" + BCW);
                        continue;
                    }

                    if (res.contains("D/L")) {
                        worl = worl + "(D/L)";
                    }

                    params.set(7, BorC + "/" + worl);
                    m.setParams(params);
                    hth.add(m);
                }
                request.setAttribute("hth", hth.subList(0, Math.min(5, hth.size())));
                // </editor-fold>
            }

            Form_guide:
            {
                // <editor-fold defaultstate="collapsed">
                A:
                {
                    List<Inning> selects = new ArrayList<>();

                    List<Match> matches = db.getDB(matchType, teamOne);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (Match q : matches) {
                        Inning m = q.getInningOne();
                        List<String> params = m.getParams();

                        String res = q.getResult();
                        String worl = "";
                        String BorC = "";

                        if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                            BorC = "B";
                        } else {
                            BorC = "C";
                        }

                        String BCW = q.getBCW();
                        if (BCW.equals("B") || BCW.equals("C")) {
                            if (BCW.equals(BorC)) {
                                worl = "W";
                            } else {
                                worl = "L";
                            }
                        } else if (BCW.equals("T")) {
                            worl = "T";
                        } else {
                            continue;
                        }

                        if (res.contains("D/L")) {
                            worl = worl + "(D/L)";
                        }

                        params.set(7, BorC + "/" + worl);
                        m.setParams(params);
                        selects.add(m);
                    }

                    if (isFavChasing) {
                        request.setAttribute("FormGuide_B", selects.subList(0, Math.min(5, selects.size())));
                    } else {
                        request.setAttribute("FormGuide_A", selects.subList(0, Math.min(5, selects.size())));
                    }
                }

                B:
                {
                    List<Inning> selects = new ArrayList<>();

                    List<Match> matches = db.getDB(matchType, teamTwo);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));

                    for (Match q : matches) {
                        Inning m = q.getInningOne();
                        List<String> params = m.getParams();

                        String res = q.getResult();
                        String toss = q.getTossWinner();
                        String worl = "";
                        String BorC = "";
                        System.out.println("toss:" + toss);

                        if (q.getHomeTeam().equalsIgnoreCase(teamTwo)) {
                            BorC = "B";
                        } else {
                            BorC = "C";
                        }

                        String BCW = q.getBCW();
                        if (BCW.equals("B") || BCW.equals("C")) {
                            if (BCW.equals(BorC)) {
                                worl = "W";
                            } else {
                                worl = "L";
                            }
                        } else if (BCW.equals("T")) {
                            worl = "T";
                        } else {
                            continue;
                        }

                        if (res.contains("D/L")) {
                            worl = worl + "(D/L)";
                        }

                        params.set(7, BorC + "/" + worl);
                        m.setParams(params);
                        selects.add(m);
                    }

                    if (isFavChasing) {
                        request.setAttribute("FormGuide_A", selects.subList(0, Math.min(5, selects.size())));
                    } else {
                        request.setAttribute("FormGuide_B", selects.subList(0, Math.min(5, selects.size())));
                    }
                }
                // </editor-fold>
            }

            BCW_total:
            {
                // <editor-fold defaultstate="collapsed">
                A:
                {
                    List<Match> matches = db.getMatches(teamOne, matchType, 1);
                    matches = type1.process(matches);
                    matches.removeIf(m -> m.getBCW().contains("--"));
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("BCW_A", selects.subList(0, Math.min(5, selects.size())));
                }
                B:
                {
                    List<Match> matches = db.getMatches(teamTwo, matchType, 2);
                    matches = type2.process(matches);
                    matches.removeIf(m -> m.getBCW().contains("--"));
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("BCW_B", selects.subList(0, Math.min(5, selects.size())));
                }

                if (isFavChasing) {
                    List<Inning> selectA = (List<Inning>) request.getAttribute("BCW_A");
                    List<Inning> selectB = (List<Inning>) request.getAttribute("BCW_B");

                    request.setAttribute("BCW_A", selectB);
                    request.setAttribute("BCW_B", selectA);
                }

                G:
                {
                    List<Match> matches = type1.getGMatches(groundName);
                    matches.removeIf(m -> m.getBCW().contains("--"));
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("BCW_G", selects.subList(0, Math.min(5, selects.size())));
                }
                // </editor-fold>
            }

            G:
            {
                // <editor-fold defaultstate="collapsed">
                List<Match> matches = type0.getGMatches(groundName);
                List<Inning> selects1 = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                request.setAttribute("Gr_First", selects1.subList(0, Math.min(5, selects1.size())));

                request.setAttribute("foursG_bt", backTest5_Gr(type0, true, 4));
                request.setAttribute("sixesG_bt", backTest5_Gr(type0, true, 5));
                request.setAttribute("boundariesG_bt", backTest5_Gr(type0, true, 6));
                // </editor-fold>
            }

            Last_X_Overs:
            {
                // <editor-fold defaultstate="collapsed">
                int pIndex = 2;
                A:
                {
                    List<Match> matches = type1_NoMajQuit.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("LO_A", selects);

                    request.setAttribute("LO_A_bt", backTest5(type1_NoMajQuit, true, pIndex, true));
                    request.setAttribute("LO_T_bt", backTest10(pIndex, type1_NoMajQuit, type2_NoMajQuit));
                }
                B:
                {
                    List<Match> matches = type2_NoMajQuit.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("LO_B", selects);

                    request.setAttribute("LO_B_bt", backTest5(type2_NoMajQuit, true, pIndex, false));
                }
                G:
                {
                    List<Match> matches = type1_NoMajQuit.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("LO_G", selects);

                    request.setAttribute("LO_G_bt", backTest5_Gr(type1_NoMajQuit, true, pIndex));
                }
                // </editor-fold>
            }

            First_Wicket:
            {
                // <editor-fold defaultstate="collapsed">
                int pIndex = 3;
                A:
                {
                    List<Match> matches = type1.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("FW_A", selects);

                    request.setAttribute("FW_A_bt", backTest5(type1, true, pIndex, true));
                    request.setAttribute("FW_T_bt", backTest10(pIndex, type1, type2));
                }
                B:
                {
                    List<Match> matches = type2.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("FW_B", selects);

                    request.setAttribute("FW_B_bt", backTest5(type2, true, pIndex, false));
                }
                G:
                {
                    request.setAttribute("FW_G_bt", backTest5_Gr(type1, true, pIndex));
                }
                // </editor-fold>
            }

            Total_runs:
            {
                // <editor-fold defaultstate="collapsed">
                int pIndex = 6;
                A:
                {
                    List<Match> matches = type1_NoMajQuit.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("TR_A", selects);

                    request.setAttribute("TR_A_bt", backTest5(type1_NoMajQuit, true, pIndex, true));
                    request.setAttribute("TR_T_bt", backTest10(pIndex, type1_NoMajQuit, type2_NoMajQuit));
                }
                B:
                {
                    List<Match> matches = type2_NoMajQuit.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("TR_B", selects);

                    request.setAttribute("TR_B_bt", backTest5(type2_NoMajQuit, true, pIndex, false));
                }
                G:
                {
                    List<Match> matches = type1_NoMajQuit.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("TR_G", selects);

                    request.setAttribute("TR_G_bt", backTest5_Gr(type1_NoMajQuit, true, pIndex));
                }
                // </editor-fold>
            }

            First_X_Overs:
            {
                // <editor-fold defaultstate="collapsed">
                int pIndex = 1;
                A:
                {
                    List<Match> matches = type1_NoMajQuit.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("FX_A", selects);

                    request.setAttribute("FX_A_bt", backTest5(type1_NoMajQuit, true, pIndex, true));
                    request.setAttribute("FX_T_bt", backTest10(pIndex, type1_NoMajQuit, type2_NoMajQuit));
                }
                B:
                {
                    List<Match> matches = type2_NoMajQuit.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("FX_B", selects);

                    request.setAttribute("FX_B_bt", backTest5(type2_NoMajQuit, true, pIndex, false));
                }
                G:
                {
                    request.setAttribute("FX_G_bt", backTest5_Gr(type1_NoMajQuit, true, pIndex));
                }
                // </editor-fold>
            }

            First_X_Overs_Second:
            {
                // <editor-fold defaultstate="collapsed">
                int pIndex = 1;
                A:
                {
                    List<Match> matches = type2_NoMajQuit.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());

                    request.setAttribute("FXS_A", selects);

                    request.setAttribute("FXS_A_bt", backTest5(type2_NoMajQuit, false, pIndex, false));
                    request.setAttribute("FXS_T_bt", backTest10_Second(pIndex, type2_NoMajQuit, type1_NoMajQuit));
                }
                B:
                {
                    List<Match> matches = type1_NoMajQuit.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());

                    request.setAttribute("FXS_B", selects);

                    request.setAttribute("FXS_B_bt", backTest5(type1_NoMajQuit, false, pIndex, true));
                }
                G:
                {
                    List<Match> matches = type1_NoMajQuit.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());

                    request.setAttribute("FXS_G", selects);

                    request.setAttribute("FXS_G_bt", backTest5_Gr(type1_NoMajQuit, false, pIndex));
                }
                // </editor-fold>
            }

            First_Wicket_Second:
            {
                // <editor-fold defaultstate="collapsed">
                int pIndex = 3;
                A:
                {
                    List<Match> matches = type2.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());

                    request.setAttribute("FWS_A", selects);

                    request.setAttribute("FWS_A_bt", backTest5(type2, false, pIndex, false));
                    request.setAttribute("FWS_T_bt", backTest10_Second(pIndex, type2, type1));
                }
                B:
                {
                    List<Match> matches = type1.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());

                    request.setAttribute("FWS_B", selects);

                    request.setAttribute("FWS_B_bt", backTest5(type1, false, pIndex, true));
                }
                G:
                {
                    List<Match> matches = type1.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());

                    request.setAttribute("FWS_G", selects);

                    request.setAttribute("FWS_G_bt", backTest5_Gr(type1, false, pIndex));
                }
                // </editor-fold>
            }

            request.setAttribute("matchType", matchType);
            request.setAttribute("headers", db.getHeaders(matchType));
            request.setAttribute("teamOne", teamOne);
            request.setAttribute("teamTwo", teamTwo);
            request.setAttribute("groundName", groundName);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/results.jsp");
            dispatcher.forward(request, response);

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
