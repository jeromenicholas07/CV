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
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.*;

public class getData extends HttpServlet {

    public interface MatchProcessor {

        List<Match> getMatches(String teamName);

        List<Match> getGMatches(String groundName);

        List<Match> process(List<Match> matches);
    }

    public interface TestProcessor {

        List<testMatch> getMatches(String teamName, TestType xType);

        List<testMatch> getGMatches(String groundName);

        List<testMatch> process(List<testMatch> matches);
    }

    public interface InningCaller {

        Inning call(testMatch match);
    }

    private Map<String, Integer> backTest5(List<Inning> inns, int pIndex) {
        Map<String, Integer> btMap = new LinkedHashMap<>();
        btMap.put("N", 0);
        btMap.put("<1", 0);
        btMap.put("1<2", 0);
        btMap.put("1/4", 0);
        btMap.put("2/3", 0);
        btMap.put("3/2", 0);
        btMap.put("4/1", 0);
        btMap.put("4<5", 0);
        btMap.put("5<", 0);

        if (inns.size() > 5) {
            for (int i = 0; i < inns.size() - 6; i++) {
                int curr = parseInt(inns.get(i).getParams().get(pIndex));

                List<Inning> sub = new ArrayList<>(inns.subList(i + 1, i + 6));
                Collections.sort(sub, new Comparator<Inning>() {
                    @Override
                    public int compare(Inning o1, Inning o2) {
                        return parseInt(o1.getParams().get(pIndex))
                                - parseInt(o2.getParams().get(pIndex));
                    }
                });

                btMap.put("N", btMap.get("N") + 1);

                if (curr < parseInt(sub.get(0).getParams().get(pIndex))) {
                    btMap.put("<1", btMap.get("<1") + 1);
                }
                if (curr >= parseInt(sub.get(0).getParams().get(pIndex))
                        && curr < parseInt(sub.get(1).getParams().get(pIndex))) {
                    btMap.put("1<2", btMap.get("1<2") + 1);
                }
                if (curr >= parseInt(sub.get(1).getParams().get(pIndex))) {
                    btMap.put("1/4", btMap.get("1/4") + 1);
                }
                if (curr >= parseInt(sub.get(2).getParams().get(pIndex))) {
                    btMap.put("2/3", btMap.get("2/3") + 1);
                }
                if (curr <= parseInt(sub.get(2).getParams().get(pIndex))) {
                    btMap.put("3/2", btMap.get("3/2") + 1);
                }
                if (curr <= parseInt(sub.get(3).getParams().get(pIndex))) {
                    btMap.put("4/1", btMap.get("4/1") + 1);
                }
                if (curr > parseInt(sub.get(3).getParams().get(pIndex))
                        && curr < parseInt(sub.get(4).getParams().get(pIndex))) {
                    btMap.put("4<5", btMap.get("4<5") + 1);
                }
                if (curr >= parseInt(sub.get(4).getParams().get(pIndex))) {
                    btMap.put("5<", btMap.get("5<") + 1);
                }
            }
        }
        return btMap;
    }

    private Map<String, Integer> backTest5_Gr(List<Inning> inns, int pIndex) {
        Map<String, Integer> btMap = new LinkedHashMap<>();
        btMap.put("N", 0);
        btMap.put("0/5", 0);
        btMap.put("1/4", 0);
        btMap.put("2/3", 0);
        btMap.put("3/2", 0);
        btMap.put("4/1", 0);
        btMap.put("5/0", 0);

        if (inns.size() > 5) {
            for (int i = 0; i < inns.size() - 6; i++) {
                int curr = parseInt(inns.get(i).getParams().get(pIndex));

                List<Inning> sub = new ArrayList<>(inns.subList(i + 1, i + 6));
                Collections.sort(sub, new Comparator<Inning>() {
                    @Override
                    public int compare(Inning o1, Inning o2) {
                        return parseInt(o1.getParams().get(pIndex))
                                - parseInt(o2.getParams().get(pIndex));
                    }
                });

                btMap.put("N", btMap.get("N") + 1);

                if (curr >= parseInt(sub.get(0).getParams().get(pIndex))) {
                    btMap.put("0/5", btMap.get("0/5") + 1);
                }
                if (curr >= parseInt(sub.get(1).getParams().get(pIndex))) {
                    btMap.put("1/4", btMap.get("1/4") + 1);
                }
                if (curr >= parseInt(sub.get(2).getParams().get(pIndex))) {
                    btMap.put("2/3", btMap.get("2/3") + 1);
                }
                if (curr <= parseInt(sub.get(2).getParams().get(pIndex))) {
                    btMap.put("3/2", btMap.get("3/2") + 1);
                }
                if (curr <= parseInt(sub.get(3).getParams().get(pIndex))) {
                    btMap.put("4/1", btMap.get("4/1") + 1);
                }
                if (curr <= parseInt(sub.get(4).getParams().get(pIndex))) {
                    btMap.put("5/0", btMap.get("5/0") + 1);
                }
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
    
    private Map<String, Integer> generateBtMap() {
        Map<String, Integer> btMap = new LinkedHashMap<>();
        btMap.put("N", 0);
        generateWithPrefix("", btMap);
        btMap.put("NG", 0);
        generateWithPrefix(GR_0, btMap);
        generateWithPrefix(GR_1, btMap);
        generateWithPrefix(GR_2, btMap);
        generateWithPrefix(GR_3, btMap);
        generateWithPrefix(GR_4, btMap);
        generateWithPrefix(GR_5, btMap);

        return btMap;
    }
    
    private void generateWithPrefix(String prefix, Map<String, Integer> btMap){
        int NO_GR_INIT = 0;
        if(!prefix.equals("")){
            NO_GR_INIT = NO_GR_BT;
        }
        btMap.put(prefix+"<1", NO_GR_INIT);
        btMap.put(prefix+"1<2", NO_GR_INIT);
        btMap.put(prefix+"1/9", 0);
        btMap.put(prefix+"2/8", 0);
        btMap.put(prefix+"3/7", 0);
        btMap.put(prefix+"4/6", 0);
        btMap.put(prefix+"6/4", 0);
        btMap.put(prefix+"7/3", 0);
        btMap.put(prefix+"8/2", 0);
        btMap.put(prefix+"9/1", 0);
        btMap.put(prefix+"9<10", NO_GR_INIT);
        btMap.put(prefix+"10<", NO_GR_INIT);
        btMap.put(prefix+"3-3 above(6<)", NO_GR_INIT);
        btMap.put(prefix+"4-4 above(8<)", NO_GR_INIT);
        btMap.put(prefix+"3-3 below(<6)", NO_GR_INIT);
        btMap.put(prefix+"4-4 below(<8)", NO_GR_INIT);
    }

    private final int NO_GR_BT = -42;
    private void fillMapBt10(Map<String, Integer> btMap, int curr, int pIndex, List<Inning> sub, List<Inning> subA, List<Inning> subB, List<Inning> subG) {
        
        btMap.put("N", btMap.get("N") + 1);
        
        if (subG.size() == 5) {
            btMap.put("NG", btMap.get("NG") + 1);
        }

        if (curr < parseInt(sub.get(0).getParams().get(pIndex))) {
            incrementBt(btMap, "<1");
        }
        if (curr >= parseInt(sub.get(0).getParams().get(pIndex))
                && curr < parseInt(sub.get(1).getParams().get(pIndex))) {
            incrementBt(btMap, "1<2");
        }
        if (curr >= parseInt(sub.get(1).getParams().get(pIndex))) {
            incrementBt(btMap, "1/9");
        }
        if (curr >= parseInt(sub.get(2).getParams().get(pIndex))) {
            incrementBt(btMap, "2/8");
        }
        if (curr >= parseInt(sub.get(3).getParams().get(pIndex))) {
            incrementBt(btMap, "3/7");
        }
        if (curr >= parseInt(sub.get(4).getParams().get(pIndex))) {
            incrementBt(btMap, "4/6");
        }
        if (curr <= parseInt(sub.get(5).getParams().get(pIndex))) {
            incrementBt(btMap, "6/4");
        }
        if (curr <= parseInt(sub.get(6).getParams().get(pIndex))) {
            incrementBt(btMap, "7/3");
        }
        if (curr <= parseInt(sub.get(7).getParams().get(pIndex))) {
            incrementBt(btMap, "8/2");
        }
        if (curr <= parseInt(sub.get(8).getParams().get(pIndex))) {
            incrementBt(btMap, "9/1");
        }
        if (curr > parseInt(sub.get(8).getParams().get(pIndex))
                && curr < parseInt(sub.get(9).getParams().get(pIndex))) {
            incrementBt(btMap, "9<10");
        }
        if (curr >= parseInt(sub.get(9).getParams().get(pIndex))) {
            incrementBt(btMap, "10<");
        }
        if (curr >= parseInt(subA.get(2).getParams().get(pIndex))
                && curr >= parseInt(subB.get(2).getParams().get(pIndex))
                && curr >= parseInt(sub.get(5).getParams().get(pIndex))) {
            incrementBt(btMap, "3-3 above(6<)");
        }
        if (curr >= parseInt(subA.get(3).getParams().get(pIndex))
                && curr >= parseInt(subB.get(3).getParams().get(pIndex))
                && curr >= parseInt(sub.get(7).getParams().get(pIndex))) {
            incrementBt(btMap, "4-4 above(8<)");
        }
        if (curr <= parseInt(subA.get(2).getParams().get(pIndex))
                && curr <= parseInt(subB.get(2).getParams().get(pIndex))
                && curr <= parseInt(sub.get(5).getParams().get(pIndex))) {
            incrementBt(btMap, "3-3 below(<6)");
        }
        if (curr <= parseInt(subA.get(3).getParams().get(pIndex))
                && curr <= parseInt(subB.get(3).getParams().get(pIndex))
                && curr <= parseInt(sub.get(7).getParams().get(pIndex))) {
            incrementBt(btMap, "4-4 below(<8)");
        }

        fillGroundBT10(curr, btMap, pIndex, sub, subG);
    }
    
    private void fillGroundBT10(int curr, Map<String, Integer> btMap, int pIndex, List<Inning> sub, List<Inning> subG){
    
        if(subG.size() == 5){
            
            for(int i = 1; i < 9; i++){
                int val = parseInt(sub.get(i).getParams().get(pIndex));
                String header;
                boolean isNumerator;
                if(i<5){
                    header = i + "/" + (10-i);
                    isNumerator = curr >= val;
                }
                else{
                    header = (i+1) + "/" + (10-(i+1));
                    isNumerator = curr <= val;
                }
                
                if(val <= parseInt(subG.get(0).getParams().get(pIndex))){
                    incrementBt(btMap, GR_0 + header, isNumerator);
                }
                if(val > parseInt(subG.get(0).getParams().get(pIndex))
                        && val <= parseInt(subG.get(1).getParams().get(pIndex))){
                    incrementBt(btMap, GR_1 + header, isNumerator);
                }
                if(val > parseInt(subG.get(1).getParams().get(pIndex))
                        && val <= parseInt(subG.get(2).getParams().get(pIndex))){
                    incrementBt(btMap, GR_2 + header, isNumerator);
                }
                if(val > parseInt(subG.get(2).getParams().get(pIndex))
                        && val <= parseInt(subG.get(3).getParams().get(pIndex))){
                    incrementBt(btMap, GR_3 + header, isNumerator);
                }
                if(val > parseInt(subG.get(3).getParams().get(pIndex))
                        && val <= parseInt(subG.get(4).getParams().get(pIndex))){
                    incrementBt(btMap, GR_4 + header, isNumerator);
                }
                if(val > parseInt(subG.get(4).getParams().get(pIndex))){
                    incrementBt(btMap, GR_5 + header, isNumerator);
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
    
    private void incrementBt(Map<String, Integer> btMap, String header){
        btMap.put(header, btMap.get(header) + 1);
    }
    
    private void incrementBt(Map<String, Integer> btMap, String header, boolean isNumerator){
        int val = btMap.get(header);
        
        int num = val & 0xFFFF;
        int den = (val >> 16) & 0xFFFF;
        
        if(isNumerator){
            num++;
        }
        else{
            den++;
        }
        
        int newVal = num | (den << 16);
        
        btMap.put(header, newVal);
    }

    private Map<String, Integer> backTest10(String teamName, List<Match> oneMatch, int pIndex, MatchProcessor matchProcessor) {
        Map<String, Integer> btMap = generateBtMap();

//        Collections.sort(oneMatch, new Comparator<Match>(){
//            @Override
//            public int compare(Match o1, Match o2) {
//                return o1.getMatchDate().compareTo(o2.getMatchDate());
//            }
//        });

        Map<String, List<Match>> cache = new LinkedHashMap<>();
        Map<String, List<Match>> grCache = new LinkedHashMap<>();
        
        for (int i = 0; i < oneMatch.size() - 6; i++) {
            Match currMatch = oneMatch.get(i);
            int curr = parseInt(currMatch.getInningOne().getParams().get(pIndex));
            Timestamp currDate = currMatch.getMatchDate();
            String oppTeam = currMatch.getHomeTeam().equals(teamName) ? currMatch.getAwayTeam() : currMatch.getHomeTeam();
            String groundName = currMatch.getGroundName();

            if(!cache.keySet().contains(oppTeam)){
                cache.put(oppTeam, matchProcessor.getMatches(oppTeam));
            }
            if(!grCache.keySet().contains(groundName)){
                grCache.put(groundName, matchProcessor.getGMatches(groundName));
            }
            List<Match> twoMatch = new ArrayList<>(cache.get(oppTeam));
            twoMatch.removeIf(m -> m.getInningOne().getParams().get(pIndex).contains("-1"));
            List<Match> grMatch = new ArrayList<>(grCache.get(groundName));
            grMatch.removeIf(m -> m.getInningOne().getParams().get(pIndex).contains("-1"));

            List<Inning> sub = new ArrayList<>();
            List<Inning> subA = new ArrayList<>();
            List<Inning> subB = new ArrayList<>();
            List<Inning> subG = new ArrayList<>();

            for (int j = i + 1; j < i + 6; j++) {
                sub.add(oneMatch.get(j).getInningOne());
                subA.add(oneMatch.get(j).getInningOne());
            }

            twoMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
            if (twoMatch.size() < 5) {
                continue;
            }
            for (int j = 0; j < 5; j++) {
                sub.add(twoMatch.get(j).getInningOne());
                subB.add(twoMatch.get(j).getInningOne());
            }

            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
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

            fillMapBt10(btMap, curr, pIndex, sub, subA, subB, subG);

        }

        return btMap;
    }

    private Map<String, Integer> backTest10_Second(String teamName, List<Match> oneMatch, int pIndex, MatchProcessor matchProcessor) {
        Map<String, Integer> btMap = generateBtMap();

//        Collections.sort(oneMatch, new Comparator<Match>(){
//            @Override
//            public int compare(Match o1, Match o2) {
//                return o1.getMatchDate().compareTo(o2.getMatchDate());
//            }
//        });
        for (int i = 0; i < oneMatch.size() - 6; i++) {
            Match currMatch = oneMatch.get(i);
            int curr = parseInt(currMatch.getInningTwo().getParams().get(pIndex));
            Timestamp currDate = currMatch.getMatchDate();
            String oppTeam = currMatch.getHomeTeam().equals(teamName) ? currMatch.getAwayTeam() : currMatch.getHomeTeam();
            String groundName = currMatch.getGroundName();

            List<Match> twoMatch = matchProcessor.getMatches(oppTeam);
            twoMatch.removeIf(m -> m.getInningTwo().getParams().get(pIndex).contains("-1"));
            List<Match> grMatch = matchProcessor.getGMatches(groundName);
            grMatch.removeIf(m -> m.getInningTwo().getParams().get(pIndex).contains("-1"));

            List<Inning> sub = new ArrayList<>();
            List<Inning> subA = new ArrayList<>();
            List<Inning> subB = new ArrayList<>();
            List<Inning> subG = new ArrayList<>();

            for (int j = i + 1; j < i + 6; j++) {
                sub.add(oneMatch.get(j).getInningTwo());
                subA.add(oneMatch.get(j).getInningTwo());
            }

            twoMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
            if (twoMatch.size() < 5) {
                continue;
            }
            for (int j = 0; j < 5; j++) {
                sub.add(twoMatch.get(j).getInningTwo());
                subB.add(twoMatch.get(j).getInningTwo());
            }

            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
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

            fillMapBt10(btMap, curr, pIndex, sub, subA, subB, subG);

        }

        return btMap;
    }

    private Map<String, Integer> backTest10_Test(String teamName, List<testMatch> oneMatch, int pIndex, TestProcessor testProcessor, InningCaller inningCaller, TestType oppType) {
        Map<String, Integer> btMap = generateBtMap();

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

            fillMapBt10(btMap, curr, pIndex, sub, subA, subB, subG);
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

            String hometeam = db.checkhomeoraway(teamOne, teamTwo, groundName);

            TestType oneSide = hometeam.equalsIgnoreCase(teamOne) ? TestType.HOME : TestType.AWAY;
            TestType twoSide = hometeam.equalsIgnoreCase(teamTwo) ? TestType.HOME : TestType.AWAY;

            TestProcessor type0 = new TestProcessor() {
                @Override
                public List<testMatch> getMatches(String teamName, TestType xType) {
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
                public List<testMatch> getMatches(String teamName, TestType xType) {
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
                public List<testMatch> getMatches(String teamName, TestType xType) {
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

                    request.setAttribute("F_A_bt", backTest5(selects, foursIndex));
                    request.setAttribute("F_TA_bt", backTest10_Test(teamOne, matches, foursIndex, type0, One1Inning, twoSide));

                    request.setAttribute("S_A_bt", backTest5(selects, sixesIndex));
                    request.setAttribute("S_TA_bt", backTest10_Test(teamOne, matches, sixesIndex, type0, One1Inning, twoSide));

                    request.setAttribute("T_A_bt", backTest5(selects, totBoundariesIndex));
                    request.setAttribute("T_TA_bt", backTest10_Test(teamOne, matches, totBoundariesIndex, type0, One1Inning, twoSide));

                }
                B:
                {
                    List<testMatch> matches = type0.getMatches(teamTwo, twoSide);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                    request.setAttribute("FST_B", selects);

                    request.setAttribute("F_B_bt", backTest5(selects, foursIndex));
                    request.setAttribute("F_TB_bt", backTest10_Test(teamTwo, matches, foursIndex, type0, One1Inning, oneSide));

                    request.setAttribute("S_B_bt", backTest5(selects, sixesIndex));
                    request.setAttribute("S_TB_bt", backTest10_Test(teamTwo, matches, sixesIndex, type0, One1Inning, oneSide));

                    request.setAttribute("T_B_bt", backTest5(selects, totBoundariesIndex));
                    request.setAttribute("T_TB_bt", backTest10_Test(teamTwo, matches, totBoundariesIndex, type0, One1Inning, oneSide));
                }
                G:
                {
                    List<testMatch> matches = type0.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                    request.setAttribute("FST_G", selects);

                    request.setAttribute("F_G_bt", backTest5_Gr(selects, foursIndex));

                    request.setAttribute("S_G_bt", backTest5_Gr(selects, sixesIndex));

                    request.setAttribute("T_G_bt", backTest5_Gr(selects, totBoundariesIndex));
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

                        request.setAttribute("FTR_A_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("FTR_TA_bt", backTest10_Test(teamOne, matches, totalRunsIndex, type2, One1Inning, twoSide));

                        request.setAttribute("FFW_A_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("FFW_TA_bt", backTest10_Test(teamOne, matches, firstWktIndex, type2, One1Inning, twoSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type2.getMatches(teamTwo, twoSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                        request.setAttribute("FX_B", selects);

                        request.setAttribute("FTR_B_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("FTR_TB_bt", backTest10_Test(teamTwo, matches, totalRunsIndex, type1, One1Inning, oneSide));

                        request.setAttribute("FFW_B_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("FFW_TB_bt", backTest10_Test(teamTwo, matches, firstWktIndex, type1, One1Inning, oneSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                        request.setAttribute("FX_G", selects);

                        request.setAttribute("FTR_G_bt", backTest5_Gr(selects, totalRunsIndex));
                        request.setAttribute("FFW_G_bt", backTest5_Gr(selects, firstWktIndex));
                    }
                }
                After_five_wickets:
                {
                    TestProcessor type1_N15 = new TestProcessor() {
                        @Override
                        public List<testMatch> getMatches(String teamName, TestType xType) {
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
                        public List<testMatch> getMatches(String teamName, TestType xType) {
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

                        request.setAttribute("F5_A_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("F5_TA_bt", backTest10_Test(teamOne, matches, fiveWktIndex, type2, One1Inning, twoSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type2.getMatches(teamTwo, twoSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                        request.setAttribute("F5_B", selects);

                        request.setAttribute("F5_B_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("F5_TB_bt", backTest10_Test(teamTwo, matches, fiveWktIndex, type1, One1Inning, oneSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());

                        request.setAttribute("F5_G", selects);

                        request.setAttribute("F5_G_bt", backTest5_Gr(selects, fiveWktIndex));
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

                        request.setAttribute("STR_A_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("STR_TA_bt", backTest10_Test(teamTwo, matches, totalRunsIndex, type1, Two1Inning, oneSide));

                        request.setAttribute("SFW_A_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("SFW_TA_bt", backTest10_Test(teamTwo, matches, firstWktIndex, type1, Two1Inning, oneSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type1.getMatches(teamOne, oneSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());

                        request.setAttribute("SX_B", selects);

                        request.setAttribute("STR_B_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("STR_TB_bt", backTest10_Test(teamOne, matches, totalRunsIndex, type2, Two1Inning, twoSide));

                        request.setAttribute("SFW_B_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("SFW_TB_bt", backTest10_Test(teamOne, matches, firstWktIndex, type2, Two1Inning, twoSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());

                        request.setAttribute("SX_G", selects);

                        request.setAttribute("STR_G_bt", backTest5_Gr(selects, totalRunsIndex));
                        request.setAttribute("SFW_G_bt", backTest5_Gr(selects, firstWktIndex));
                    }
                }
                After_five_wickets:
                {
                    TestProcessor type1_N25 = new TestProcessor() {
                        @Override
                        public List<testMatch> getMatches(String teamName, TestType xType) {
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
                        public List<testMatch> getMatches(String teamName, TestType xType) {
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

                        request.setAttribute("S5_A_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("S5_TA_bt", backTest10_Test(teamTwo, matches, fiveWktIndex, type1_N25, Two1Inning, oneSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type1_N25.getMatches(teamOne, oneSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());

                        request.setAttribute("S5_B", selects);

                        request.setAttribute("S5_B_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("S5_TB_bt", backTest10_Test(teamOne, matches, fiveWktIndex, type2_N25, Two1Inning, twoSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1_N25.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());

                        request.setAttribute("S5_G", selects);

                        request.setAttribute("S5_G_bt", backTest5_Gr(selects, fiveWktIndex));
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

                        request.setAttribute("TTR_A_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("TTR_TA_bt", backTest10_Test(teamOne, matches, totalRunsIndex, type2, One2Inning, twoSide));

                        request.setAttribute("TFW_A_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("TFW_TA_bt", backTest10_Test(teamOne, matches, firstWktIndex, type2, One2Inning, twoSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type2.getMatches(teamTwo, twoSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());

                        request.setAttribute("TX_B", selects);

                        request.setAttribute("TTR_B_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("TTR_TB_bt", backTest10_Test(teamTwo, matches, totalRunsIndex, type1, One2Inning, oneSide));

                        request.setAttribute("TFW_B_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("TFW_TB_bt", backTest10_Test(teamTwo, matches, firstWktIndex, type1, One2Inning, oneSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());

                        request.setAttribute("TX_G", selects);

                        request.setAttribute("TTR_G_bt", backTest5_Gr(selects, totalRunsIndex));
                        request.setAttribute("TFW_G_bt", backTest5_Gr(selects, firstWktIndex));
                    }
                }
                After_five_wickets:
                {
                    TestProcessor type1_N35 = new TestProcessor() {
                        @Override
                        public List<testMatch> getMatches(String teamName, TestType xType) {
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
                        public List<testMatch> getMatches(String teamName, TestType xType) {
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

                        request.setAttribute("T5_A_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("T5_TA_bt", backTest10_Test(teamOne, matches, fiveWktIndex, type2_N35, One2Inning, twoSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type2_N35.getMatches(teamTwo, twoSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());

                        request.setAttribute("T5_B", selects);

                        request.setAttribute("T5_B_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("T5_TB_bt", backTest10_Test(teamTwo, matches, fiveWktIndex, type1_N35, One2Inning, oneSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1_N35.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());

                        request.setAttribute("T5_G", selects);

                        request.setAttribute("T5_G_bt", backTest5_Gr(selects, fiveWktIndex));
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

                        request.setAttribute("QTR_A_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("QTR_TA_bt", backTest10_Test(teamTwo, matches, totalRunsIndex, type1, Two2Inning, oneSide));

                        request.setAttribute("QFW_A_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("QFW_TA_bt", backTest10_Test(teamTwo, matches, firstWktIndex, type1, Two2Inning, oneSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type1.getMatches(teamOne, oneSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());

                        request.setAttribute("QX_B", selects);

                        request.setAttribute("QTR_B_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("QTR_TB_bt", backTest10_Test(teamOne, matches, totalRunsIndex, type2, Two2Inning, twoSide));

                        request.setAttribute("QFW_B_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("QFW_TB_bt", backTest10_Test(teamOne, matches, firstWktIndex, type2, Two2Inning, twoSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());

                        request.setAttribute("QX_G", selects);

                        request.setAttribute("QTR_G_bt", backTest5_Gr(selects, totalRunsIndex));
                        request.setAttribute("QFW_G_bt", backTest5_Gr(selects, firstWktIndex));
                    }
                }
                After_five_wickets:
                {
                    TestProcessor type1_N25 = new TestProcessor() {
                        @Override
                        public List<testMatch> getMatches(String teamName, TestType xType) {
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
                        public List<testMatch> getMatches(String teamName, TestType xType) {
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

                        request.setAttribute("Q5_A_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("Q5_TA_bt", backTest10_Test(teamTwo, matches, fiveWktIndex, type1_N25, Two2Inning, oneSide));
                    }
                    B:
                    {
                        List<testMatch> matches = type1_N25.getMatches(teamOne, oneSide);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());

                        request.setAttribute("Q5_B", selects);

                        request.setAttribute("Q5_B_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("Q5_TB_bt", backTest10_Test(teamOne, matches, fiveWktIndex, type2_N25, Two2Inning, twoSide));
                    }
                    G:
                    {
                        List<testMatch> matches = type1_N25.getGMatches(groundName);
                        List<Inning> selects = matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());

                        request.setAttribute("Q5_G", selects);

                        request.setAttribute("Q5_G_bt", backTest5_Gr(selects, fiveWktIndex));
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
                    List<Match> matches = db.getMatches(teamName, matchType, 0);
                    return process(matches);
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
                public List<Match> getGMatches(String groundName) {
                    List<Match> matches = db.getGroundInfo(groundName, matchType);
                    return process(matches);
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
                    selects = selects;
                    request.setAttribute("FormGuide_A", selects.subList(0, Math.min(5, selects.size())));
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
                    selects = selects;
                    request.setAttribute("FormGuide_B", selects.subList(0, Math.min(5, selects.size())));
                }
                // </editor-fold>
            }

            boundaries:
            {
                // <editor-fold defaultstate="collapsed">
                A:
                {
                    List<Match> matches = type0.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("FST_A", selects);
                    request.setAttribute("foursA_bt", backTest5(selects, 4));
                    request.setAttribute("sixesA_bt", backTest5(selects, 5));
                    request.setAttribute("boundariesA_bt", backTest5(selects, 8));
                    request.setAttribute("foursTA_bt", backTest10(teamOne, matches, 4, type0));
                    request.setAttribute("sixesTA_bt", backTest10(teamOne, matches, 5, type0));
                    request.setAttribute("boundariesTA_bt", backTest10(teamOne, matches, 6, type0));
                }
                B:
                {
                    List<Match> matches = type0.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("FST_B", selects);
                    request.setAttribute("foursB_bt", backTest5(selects, 4));
                    request.setAttribute("sixesB_bt", backTest5(selects, 5));
                    request.setAttribute("boundariesB_bt", backTest5(selects, 8));
                    request.setAttribute("foursTB_bt", backTest10(teamTwo, matches, 4, type0));
                    request.setAttribute("sixesTB_bt", backTest10(teamTwo, matches, 5, type0));
                    request.setAttribute("boundariesTB_bt", backTest10(teamTwo, matches, 6, type0));
                }
                G:
                {
                    List<Match> matches = type0.getGMatches(groundName);
                    List<Inning> selects1 = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("Gr_First", selects1.subList(0, Math.min(5, selects1.size())));
                    request.setAttribute("foursG_bt", backTest5_Gr(selects1, 4));
                    request.setAttribute("sixesG_bt", backTest5_Gr(selects1, 5));
                    request.setAttribute("boundariesG_bt", backTest5_Gr(selects1, 6));
                }
                // </editor-fold>
            }

            BCW_total:
            {
                // <editor-fold defaultstate="collapsed">
                A:
                {
                    List<Match> matches = type1.getMatches(teamOne);
                    matches.removeIf(m -> m.getBCW().contains("--"));
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("BCW_A", selects.subList(0, Math.min(5, selects.size())));
                }
                B:
                {
                    List<Match> matches = type2.getMatches(teamTwo);
                    matches.removeIf(m -> m.getBCW().contains("--"));
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("BCW_B", selects.subList(0, Math.min(5, selects.size())));
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

            First_Over:
            {
                // <editor-fold defaultstate="collapsed">
                A:
                {
                    List<Match> matches = type1.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("FO_A", selects);
                }
                B:
                {
                    List<Match> matches = type2.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("FO_B", selects);
                }
                // </editor-fold>
            }

            Last_X_Overs:
            {
                // <editor-fold defaultstate="collapsed">
                int pIndex = 2;
                MatchProcessor LO1 = new MatchProcessor() {
                    @Override
                    public List<Match> getMatches(String teamName) {
                        return process(type1_NoMajQuit.getMatches(teamName));
                    }

                    @Override
                    public List<Match> getGMatches(String groundName) {
                        return process(type1_NoMajQuit.getGMatches(groundName));
                    }

                    @Override
                    public List<Match> process(List<Match> matches) {
                        matches.removeIf(m -> m.getInningOne().getParams().get(2).contains("-1"));
                        return matches;
                    }
                };

                MatchProcessor LO2 = new MatchProcessor() {
                    @Override
                    public List<Match> getMatches(String teamName) {
                        return process(type2_NoMajQuit.getMatches(teamName));
                    }

                    @Override
                    public List<Match> getGMatches(String groundName) {
                        return LO1.getGMatches(groundName);
                    }

                    @Override
                    public List<Match> process(List<Match> matches) {
                        return LO1.process(matches);
                    }
                };

                A:
                {
                    List<Match> matches = type1_NoMajQuit.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("LO_A", selects);
                    request.setAttribute("LO_A_bt", backTest5(selects, pIndex));
                    request.setAttribute("LO_TA_bt", backTest10(teamOne, matches, pIndex, type2_NoMajQuit));
                }
                B:
                {
                    List<Match> matches = type2_NoMajQuit.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("LO_B", selects);
                    request.setAttribute("LO_B_bt", backTest5(selects, pIndex));
                    request.setAttribute("LO_TB_bt", backTest10(teamTwo, matches, pIndex, type1_NoMajQuit));
                }
                G:
                {
                    List<Match> matches = type1_NoMajQuit.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("LO_G", selects);
                    request.setAttribute("LO_G_bt", backTest5_Gr(selects, pIndex));
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
                    request.setAttribute("FW_A_bt", backTest5(selects, pIndex));
                    request.setAttribute("FW_TA_bt", backTest10(teamOne, matches, pIndex, type2));
                }
                B:
                {
                    List<Match> matches = type2.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("FW_B", selects);
                    request.setAttribute("FW_B_bt", backTest5(selects, pIndex));
                    request.setAttribute("FW_TB_bt", backTest10(teamTwo, matches, pIndex, type1));
                }
                G:
                {
                    List<Match> matches = type1.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("FW_G_bt", backTest5_Gr(selects, pIndex));
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
                    request.setAttribute("TR_A_bt", backTest5(selects, pIndex));
                    request.setAttribute("TR_TA_bt", backTest10(teamOne, matches, pIndex, type2_NoMajQuit));
                }
                B:
                {
                    List<Match> matches = type2_NoMajQuit.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("TR_B", selects);
                    request.setAttribute("TR_B_bt", backTest5(selects, pIndex));
                    request.setAttribute("TR_TB_bt", backTest10(teamTwo, matches, pIndex, type1_NoMajQuit));
                }
                G:
                {
                    List<Match> matches = type1_NoMajQuit.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("TR_G", selects);
                    request.setAttribute("TR_G_bt", backTest5_Gr(selects, pIndex));
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
                    request.setAttribute("FX_A_bt", backTest5(selects, pIndex));
                    request.setAttribute("FX_TA_bt", backTest10(teamOne, matches, pIndex, type2_NoMajQuit));
                }
                B:
                {
                    List<Match> matches = type2_NoMajQuit.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("FX_B", selects);
                    request.setAttribute("FX_B_bt", backTest5(selects, pIndex));
                    request.setAttribute("FX_TB_bt", backTest10(teamTwo, matches, pIndex, type1_NoMajQuit));
                }
                G:
                {
                    List<Match> matches = type1_NoMajQuit.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                    request.setAttribute("FX_G_bt", backTest5_Gr(selects, pIndex));
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
                    request.setAttribute("FXS_A_bt", backTest5(selects, pIndex));
                    request.setAttribute("FXS_TA_bt", backTest10_Second(teamTwo, matches, pIndex, type1_NoMajQuit));
                }
                B:
                {
                    List<Match> matches = type1_NoMajQuit.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());

                    request.setAttribute("FXS_B", selects);
                    request.setAttribute("FXS_B_bt", backTest5(selects, pIndex));
                    request.setAttribute("FXS_TB_bt", backTest10_Second(teamOne, matches, pIndex, type2_NoMajQuit));
                }
                G:
                {
                    List<Match> matches = type1_NoMajQuit.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());

                    request.setAttribute("FXS_G", selects);
                    request.setAttribute("FXS_G_bt", backTest5_Gr(selects, pIndex));
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
                    request.setAttribute("FWS_A_bt", backTest5(selects, pIndex));
                    request.setAttribute("FWS_TA_bt", backTest10_Second(teamTwo, matches, pIndex, type1));
                }
                B:
                {
                    List<Match> matches = type1.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());

                    request.setAttribute("FWS_B", selects);
                    request.setAttribute("FWS_B_bt", backTest5(selects, pIndex));
                    request.setAttribute("FWS_TB_bt", backTest10_Second(teamOne, matches, pIndex, type2));
                }
                G:
                {
                    List<Match> matches = type1.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());

                    request.setAttribute("FWS_G", selects);
                    request.setAttribute("FWS_G_bt", backTest5_Gr(selects, pIndex));
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
