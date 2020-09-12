/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Database.CricDB;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author DELL n
 */
public class getData extends HttpServlet {
    public interface ExtraTestProcessing{
        List<testMatch> process(List<testMatch> matches);
    }
    public interface MatchProcessor{
        List<Match> getMatches(String teamName);
        List<Match> getGMatches(String groundName);
        List<Match> process(List<Match> matches);
    }

    public interface TestProcessor{
        List<testMatch> getMatches(String teamName, TestType xType);
        List<testMatch> getGMatches(String groundName);
        List<testMatch> process(List<testMatch> matches);
    }
    
    public interface InningCaller{
        Inning call(testMatch match);
    }
    
    private Map<String, Integer> backTest5(List<Inning> inns, int pIndex){
        Map<String, Integer> btMap = new LinkedHashMap<>();
        btMap.put("< 1/5", 0);
        btMap.put("1/5 - 2/5", 0);
        btMap.put("2/5 - 3/5", 0);
        btMap.put("3/5 - 4/5", 0);
        btMap.put("4/5 - 5/5", 0);
        btMap.put("5/5 <", 0);

        if (inns.size() > 5) {
            for (int i = 0; i < inns.size() - 6; i++) {
                int curr = Integer.parseInt(inns.get(i).getParams().get(pIndex));

                List<Inning> sub = new ArrayList<>(inns.subList(i + 1, i + 6));
                Collections.sort(sub, new Comparator<Inning>() {
                    @Override
                    public int compare(Inning o1, Inning o2) {
                        return Integer.parseInt(o1.getParams().get(pIndex))
                                - Integer.parseInt(o2.getParams().get(pIndex));
                    }
                });

                if (curr < Integer.parseInt(sub.get(0).getParams().get(pIndex))) {
                    btMap.put("< 1/5", btMap.get("< 1/5") + 1);
                } else if (curr >= Integer.parseInt(sub.get(0).getParams().get(pIndex))
                        && curr < Integer.parseInt(sub.get(1).getParams().get(pIndex))) {
                    btMap.put("1/5 - 2/5", btMap.get("1/5 - 2/5") + 1);
                } else if (curr >= Integer.parseInt(sub.get(1).getParams().get(pIndex))
                        && curr < Integer.parseInt(sub.get(2).getParams().get(pIndex))) {
                    btMap.put("2/5 - 3/5", btMap.get("2/5 - 3/5") + 1);
                } else if (curr >= Integer.parseInt(sub.get(2).getParams().get(pIndex))
                        && curr < Integer.parseInt(sub.get(3).getParams().get(pIndex))) {
                    btMap.put("3/5 - 4/5", btMap.get("3/5 - 4/5") + 1);
                } else if (curr >= Integer.parseInt(sub.get(3).getParams().get(pIndex))
                        && curr < Integer.parseInt(sub.get(4).getParams().get(pIndex))) {
                    btMap.put("4/5 - 5/5", btMap.get("4/5 - 5/5") + 1);
                } else {
                    btMap.put("5/5 <", btMap.get("5/5 <") + 1);
                }
            }
        }
        return btMap;
    }

    private Map<String, Integer> backTest10(String teamName, List<Match> oneMatch, int pIndex, MatchProcessor matchProcessor){
        Map<String, Integer> btMap = new LinkedHashMap<>();
        btMap.put("N", 0);
        btMap.put("< 1/10", 0);
        btMap.put("1/10 - 2/10", 0);
        btMap.put("2/10 - 3/10", 0);
        btMap.put("3/10 - 4/10", 0);
        btMap.put("4/10 - 5/10", 0);
        btMap.put("5/10 - 6/10", 0);
        btMap.put("6/10 - 7/10", 0);
        btMap.put("7/10 - 8/10", 0);
        btMap.put("8/10 - 9/10", 0);
        btMap.put("9/10 - 10/10", 0);
        btMap.put("10/10 <", 0);
        btMap.put("2-2-2-3 above", 0);
        btMap.put("4-4-4-7 below", 0);
        btMap.put("3-3-3-4 above", 0);
        btMap.put("3-3-3-6 below", 0);
        btMap.put("2-2gr above", 0);
        btMap.put("3-2gr above", 0);
        btMap.put("4-3gr above", 0);
        btMap.put("6-3gr below", 0);
        btMap.put("7-4gr below", 0);
        btMap.put("8-4gr below", 0);
        
//        Collections.sort(oneMatch, new Comparator<Match>(){
//            @Override
//            public int compare(Match o1, Match o2) {
//                return o1.getMatchDate().compareTo(o2.getMatchDate());
//            }
//        });
        Map<String, List<Match>> cache = new LinkedHashMap<>();

        for (int i = 0; i < oneMatch.size() - 6; i++) {
            Match currMatch = oneMatch.get(i);
            int curr = Integer.parseInt(currMatch.getInningOne().getParams().get(pIndex));
            Timestamp currDate = currMatch.getMatchDate();
            String oppTeam = currMatch.getHomeTeam().equals(teamName) ? currMatch.getAwayTeam() : currMatch.getHomeTeam();
            String groundName = currMatch.getGroundName();
            
            if(!cache.keySet().contains(oppTeam)){
                cache.put(oppTeam, matchProcessor.getMatches(oppTeam));
            }
            List<Match> twoMatch = new ArrayList<>(cache.get(oppTeam));
            List<Match> grMatch = matchProcessor.getGMatches(groundName);

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
                break;
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
                    return Integer.parseInt(o1.getParams().get(pIndex))
                            - Integer.parseInt(o2.getParams().get(pIndex));
                }
            };

            Collections.sort(sub, innComp);
            Collections.sort(subA, innComp);
            Collections.sort(subB, innComp);
            Collections.sort(subG, innComp);

            btMap.put("N", btMap.get("N") + 1);

            if (curr < Integer.parseInt(sub.get(0).getParams().get(pIndex))) {
                btMap.put("< 1/10", btMap.get("< 1/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(0).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(1).getParams().get(pIndex))) {
                btMap.put("1/10 - 2/10", btMap.get("1/10 - 2/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(1).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(2).getParams().get(pIndex))) {
                btMap.put("2/10 - 3/10", btMap.get("2/10 - 3/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(2).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(3).getParams().get(pIndex))) {
                btMap.put("3/10 - 4/10", btMap.get("3/10 - 4/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(3).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(4).getParams().get(pIndex))) {
                btMap.put("4/10 - 5/10", btMap.get("4/10 - 5/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(4).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(5).getParams().get(pIndex))) {
                btMap.put("5/10 - 6/10", btMap.get("5/10 - 6/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(5).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(6).getParams().get(pIndex))) {
                btMap.put("6/10 - 7/10", btMap.get("6/10 - 7/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(6).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(7).getParams().get(pIndex))) {
                btMap.put("7/10 - 8/10", btMap.get("7/10 - 8/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(7).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(8).getParams().get(pIndex))) {
                btMap.put("8/10 - 9/10", btMap.get("8/10 - 9/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(8).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(9).getParams().get(pIndex))) {
                btMap.put("9/10 - 10/10", btMap.get("9/10 - 10/10") + 1);
            } else {
                btMap.put("10/10 <", btMap.get("10/10 <") + 1);
            }

            if (curr >= Integer.parseInt(sub.get(2).getParams().get(pIndex))
                    && curr >= Integer.parseInt(subA.get(1).getParams().get(pIndex))
                    && curr >= Integer.parseInt(subB.get(1).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(1).getParams().get(pIndex))) {
                        btMap.put("2-2-2-3 above", btMap.get("2-2-2-3 above") + 1);
                    }
                } else {
                    btMap.put("2-2-2-3 above", btMap.get("2-2-2-3 above") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(6).getParams().get(pIndex))
                    && curr <= Integer.parseInt(subA.get(3).getParams().get(pIndex))
                    && curr <= Integer.parseInt(subB.get(3).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(3).getParams().get(pIndex))) {
                        btMap.put("4-4-4-7 below", btMap.get("4-4-4-7 below") + 1);
                    }
                } else {
                    btMap.put("4-4-4-7 below", btMap.get("4-4-4-7 below") + 1);
                }
            }

            if (curr >= Integer.parseInt(sub.get(3).getParams().get(pIndex))
                    && curr >= Integer.parseInt(subA.get(2).getParams().get(pIndex))
                    && curr >= Integer.parseInt(subB.get(2).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(2).getParams().get(pIndex))) {
                        btMap.put("3-3-3-4 above", btMap.get("3-3-3-4 above") + 1);
                    }
                } else {
                    btMap.put("3-3-3-4 above", btMap.get("3-3-3-4 above") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(5).getParams().get(pIndex))
                    && curr <= Integer.parseInt(subA.get(2).getParams().get(pIndex))
                    && curr <= Integer.parseInt(subB.get(2).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(2).getParams().get(pIndex))) {
                        btMap.put("3-3-3-6 below", btMap.get("3-3-3-6 below") + 1);
                    }
                } else {
                    btMap.put("3-3-3-6 below", btMap.get("3-3-3-6 below") + 1);
                }
            }

            if (curr >= Integer.parseInt(sub.get(1).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(1).getParams().get(pIndex))) {
                        btMap.put("2-2gr above", btMap.get("2-2gr above") + 1);
                    }
                } else {
                    btMap.put("2-2gr above", btMap.get("2-2gr above") + 1);
                }
            }

            if (curr >= Integer.parseInt(sub.get(2).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(1).getParams().get(pIndex))) {
                        btMap.put("3-2gr above", btMap.get("3-2gr above") + 1);
                    }
                } else {
                    btMap.put("3-2gr above", btMap.get("3-2gr above") + 1);
                }
            }

            if (curr >= Integer.parseInt(sub.get(3).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(2).getParams().get(pIndex))) {
                        btMap.put("4-3gr above", btMap.get("4-3gr above") + 1);
                    }
                } else {
                    btMap.put("4-3gr above", btMap.get("4-3gr above") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(5).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(2).getParams().get(pIndex))) {
                        btMap.put("6-3gr below", btMap.get("6-3gr below") + 1);
                    }
                } else {
                    btMap.put("6-3gr below", btMap.get("6-3gr below") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(6).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(3).getParams().get(pIndex))) {
                        btMap.put("7-4gr below", btMap.get("7-4gr below") + 1);
                    }
                } else {
                    btMap.put("7-4gr below", btMap.get("7-4gr below") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(7).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(3).getParams().get(pIndex))) {
                        btMap.put("8-4gr below", btMap.get("8-4gr below") + 1);
                    }
                } else {
                    btMap.put("8-4gr below", btMap.get("8-4gr below") + 1);
                }
            }
        }

        return btMap;
    }
    
    private Map<String, Integer> backTest10_Second(String teamName, List<Match> oneMatch, int pIndex, MatchProcessor matchProcessor){
        Map<String, Integer> btMap = new LinkedHashMap<>();
        btMap.put("N", 0);
        btMap.put("< 1/10", 0);
        btMap.put("1/10 - 2/10", 0);
        btMap.put("2/10 - 3/10", 0);
        btMap.put("3/10 - 4/10", 0);
        btMap.put("4/10 - 5/10", 0);
        btMap.put("5/10 - 6/10", 0);
        btMap.put("6/10 - 7/10", 0);
        btMap.put("7/10 - 8/10", 0);
        btMap.put("8/10 - 9/10", 0);
        btMap.put("9/10 - 10/10", 0);
        btMap.put("10/10 <", 0);
        btMap.put("2-2-2-3 above", 0);
        btMap.put("4-4-4-7 below", 0);
        btMap.put("3-3-3-4 above", 0);
        btMap.put("3-3-3-6 below", 0);
        btMap.put("2-2gr above", 0);
        btMap.put("3-2gr above", 0);
        btMap.put("4-3gr above", 0);
        btMap.put("6-3gr below", 0);
        btMap.put("7-4gr below", 0);
        btMap.put("8-4gr below", 0);
        
//        Collections.sort(oneMatch, new Comparator<Match>(){
//            @Override
//            public int compare(Match o1, Match o2) {
//                return o1.getMatchDate().compareTo(o2.getMatchDate());
//            }
//        });

        for (int i = 0; i < oneMatch.size() - 6; i++) {
            Match currMatch = oneMatch.get(i);
            int curr = Integer.parseInt(currMatch.getInningTwo().getParams().get(pIndex));
            Timestamp currDate = currMatch.getMatchDate();
            String oppTeam = currMatch.getHomeTeam().equals(teamName) ? currMatch.getAwayTeam() : currMatch.getHomeTeam();
            String groundName = currMatch.getGroundName();
            
            List<Match> twoMatch = matchProcessor.getMatches(oppTeam);
            List<Match> grMatch = matchProcessor.getGMatches(groundName);

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
                break;
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
                    return Integer.parseInt(o1.getParams().get(pIndex))
                            - Integer.parseInt(o2.getParams().get(pIndex));
                }
            };

            Collections.sort(sub, innComp);
            Collections.sort(subA, innComp);
            Collections.sort(subB, innComp);
            Collections.sort(subG, innComp);

            btMap.put("N", btMap.get("N") + 1);

            if (curr < Integer.parseInt(sub.get(0).getParams().get(pIndex))) {
                btMap.put("< 1/10", btMap.get("< 1/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(0).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(1).getParams().get(pIndex))) {
                btMap.put("1/10 - 2/10", btMap.get("1/10 - 2/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(1).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(2).getParams().get(pIndex))) {
                btMap.put("2/10 - 3/10", btMap.get("2/10 - 3/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(2).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(3).getParams().get(pIndex))) {
                btMap.put("3/10 - 4/10", btMap.get("3/10 - 4/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(3).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(4).getParams().get(pIndex))) {
                btMap.put("4/10 - 5/10", btMap.get("4/10 - 5/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(4).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(5).getParams().get(pIndex))) {
                btMap.put("5/10 - 6/10", btMap.get("5/10 - 6/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(5).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(6).getParams().get(pIndex))) {
                btMap.put("6/10 - 7/10", btMap.get("6/10 - 7/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(6).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(7).getParams().get(pIndex))) {
                btMap.put("7/10 - 8/10", btMap.get("7/10 - 8/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(7).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(8).getParams().get(pIndex))) {
                btMap.put("8/10 - 9/10", btMap.get("8/10 - 9/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(8).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(9).getParams().get(pIndex))) {
                btMap.put("9/10 - 10/10", btMap.get("9/10 - 10/10") + 1);
            } else {
                btMap.put("10/10 <", btMap.get("10/10 <") + 1);
            }

            if (curr >= Integer.parseInt(sub.get(2).getParams().get(pIndex))
                    && curr >= Integer.parseInt(subA.get(1).getParams().get(pIndex))
                    && curr >= Integer.parseInt(subB.get(1).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(1).getParams().get(pIndex))) {
                        btMap.put("2-2-2-3 above", btMap.get("2-2-2-3 above") + 1);
                    }
                } else {
                    btMap.put("2-2-2-3 above", btMap.get("2-2-2-3 above") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(6).getParams().get(pIndex))
                    && curr <= Integer.parseInt(subA.get(3).getParams().get(pIndex))
                    && curr <= Integer.parseInt(subB.get(3).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(3).getParams().get(pIndex))) {
                        btMap.put("4-4-4-7 below", btMap.get("4-4-4-7 below") + 1);
                    }
                } else {
                    btMap.put("4-4-4-7 below", btMap.get("4-4-4-7 below") + 1);
                }
            }

            if (curr >= Integer.parseInt(sub.get(3).getParams().get(pIndex))
                    && curr >= Integer.parseInt(subA.get(2).getParams().get(pIndex))
                    && curr >= Integer.parseInt(subB.get(2).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(2).getParams().get(pIndex))) {
                        btMap.put("3-3-3-4 above", btMap.get("3-3-3-4 above") + 1);
                    }
                } else {
                    btMap.put("3-3-3-4 above", btMap.get("3-3-3-4 above") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(5).getParams().get(pIndex))
                    && curr <= Integer.parseInt(subA.get(2).getParams().get(pIndex))
                    && curr <= Integer.parseInt(subB.get(2).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(2).getParams().get(pIndex))) {
                        btMap.put("3-3-3-6 below", btMap.get("3-3-3-6 below") + 1);
                    }
                } else {
                    btMap.put("3-3-3-6 below", btMap.get("3-3-3-6 below") + 1);
                }
            }

            if (curr >= Integer.parseInt(sub.get(1).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(1).getParams().get(pIndex))) {
                        btMap.put("2-2gr above", btMap.get("2-2gr above") + 1);
                    }
                } else {
                    btMap.put("2-2gr above", btMap.get("2-2gr above") + 1);
                }
            }

            if (curr >= Integer.parseInt(sub.get(2).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(1).getParams().get(pIndex))) {
                        btMap.put("3-2gr above", btMap.get("3-2gr above") + 1);
                    }
                } else {
                    btMap.put("3-2gr above", btMap.get("3-2gr above") + 1);
                }
            }

            if (curr >= Integer.parseInt(sub.get(3).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(2).getParams().get(pIndex))) {
                        btMap.put("4-3gr above", btMap.get("4-3gr above") + 1);
                    }
                } else {
                    btMap.put("4-3gr above", btMap.get("4-3gr above") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(5).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(2).getParams().get(pIndex))) {
                        btMap.put("6-3gr below", btMap.get("6-3gr below") + 1);
                    }
                } else {
                    btMap.put("6-3gr below", btMap.get("6-3gr below") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(6).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(3).getParams().get(pIndex))) {
                        btMap.put("7-4gr below", btMap.get("7-4gr below") + 1);
                    }
                } else {
                    btMap.put("7-4gr below", btMap.get("7-4gr below") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(7).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(3).getParams().get(pIndex))) {
                        btMap.put("8-4gr below", btMap.get("8-4gr below") + 1);
                    }
                } else {
                    btMap.put("8-4gr below", btMap.get("8-4gr below") + 1);
                }
            }
        }

        return btMap;
    }
    
    private Map<String, Integer> backTest10_Test(String teamName, List<testMatch> oneMatch, int pIndex, TestProcessor testProcessor, InningCaller inningCaller, TestType oppType){
        Map<String, Integer> btMap = new LinkedHashMap<>();
        btMap.put("N", 0);
        btMap.put("< 1/10", 0);
        btMap.put("1/10 - 2/10", 0);
        btMap.put("2/10 - 3/10", 0);
        btMap.put("3/10 - 4/10", 0);
        btMap.put("4/10 - 5/10", 0);
        btMap.put("5/10 - 6/10", 0);
        btMap.put("6/10 - 7/10", 0);
        btMap.put("7/10 - 8/10", 0);
        btMap.put("8/10 - 9/10", 0);
        btMap.put("9/10 - 10/10", 0);
        btMap.put("10/10 <", 0);
        btMap.put("2-2-2-3 above", 0);
        btMap.put("4-4-4-7 below", 0);
        btMap.put("3-3-3-4 above", 0);
        btMap.put("3-3-3-6 below", 0);
        btMap.put("2-2gr above", 0);
        btMap.put("3-2gr above", 0);
        btMap.put("4-3gr above", 0);
        btMap.put("6-3gr below", 0);
        btMap.put("7-4gr below", 0);
        btMap.put("8-4gr below", 0);
        
//        Collections.sort(oneMatch, new Comparator<Match>(){
//            @Override
//            public int compare(Match o1, Match o2) {
//                return o1.getMatchDate().compareTo(o2.getMatchDate());
//            }
//        });

        for (int i = 0; i < oneMatch.size() - 6; i++) {
            testMatch currMatch = oneMatch.get(i);
            int curr = Integer.parseInt(inningCaller.call(currMatch).getParams().get(pIndex));
            Date currDate = currMatch.getMatchDate();
            String oppTeam = currMatch.getHomeTeam().equals(teamName) ? currMatch.getAwayTeam() : currMatch.getHomeTeam();
            String groundName = currMatch.getGroundName();
            
            List<testMatch> twoMatch = testProcessor.getMatches(oppTeam, oppType);
            List<testMatch> grMatch = testProcessor.getGMatches(groundName);

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
                break;
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
                    return Integer.parseInt(o1.getParams().get(pIndex))
                            - Integer.parseInt(o2.getParams().get(pIndex));
                }
            };

            Collections.sort(sub, innComp);
            Collections.sort(subA, innComp);
            Collections.sort(subB, innComp);
            Collections.sort(subG, innComp);

            btMap.put("N", btMap.get("N") + 1);

            if (curr < Integer.parseInt(sub.get(0).getParams().get(pIndex))) {
                btMap.put("< 1/10", btMap.get("< 1/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(0).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(1).getParams().get(pIndex))) {
                btMap.put("1/10 - 2/10", btMap.get("1/10 - 2/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(1).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(2).getParams().get(pIndex))) {
                btMap.put("2/10 - 3/10", btMap.get("2/10 - 3/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(2).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(3).getParams().get(pIndex))) {
                btMap.put("3/10 - 4/10", btMap.get("3/10 - 4/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(3).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(4).getParams().get(pIndex))) {
                btMap.put("4/10 - 5/10", btMap.get("4/10 - 5/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(4).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(5).getParams().get(pIndex))) {
                btMap.put("5/10 - 6/10", btMap.get("5/10 - 6/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(5).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(6).getParams().get(pIndex))) {
                btMap.put("6/10 - 7/10", btMap.get("6/10 - 7/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(6).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(7).getParams().get(pIndex))) {
                btMap.put("7/10 - 8/10", btMap.get("7/10 - 8/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(7).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(8).getParams().get(pIndex))) {
                btMap.put("8/10 - 9/10", btMap.get("8/10 - 9/10") + 1);
            } else if (curr >= Integer.parseInt(sub.get(8).getParams().get(pIndex))
                    && curr < Integer.parseInt(sub.get(9).getParams().get(pIndex))) {
                btMap.put("9/10 - 10/10", btMap.get("9/10 - 10/10") + 1);
            } else {
                btMap.put("10/10 <", btMap.get("10/10 <") + 1);
            }

            if (curr >= Integer.parseInt(sub.get(2).getParams().get(pIndex))
                    && curr >= Integer.parseInt(subA.get(1).getParams().get(pIndex))
                    && curr >= Integer.parseInt(subB.get(1).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(1).getParams().get(pIndex))) {
                        btMap.put("2-2-2-3 above", btMap.get("2-2-2-3 above") + 1);
                    }
                } else {
                    btMap.put("2-2-2-3 above", btMap.get("2-2-2-3 above") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(6).getParams().get(pIndex))
                    && curr <= Integer.parseInt(subA.get(3).getParams().get(pIndex))
                    && curr <= Integer.parseInt(subB.get(3).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(3).getParams().get(pIndex))) {
                        btMap.put("4-4-4-7 below", btMap.get("4-4-4-7 below") + 1);
                    }
                } else {
                    btMap.put("4-4-4-7 below", btMap.get("4-4-4-7 below") + 1);
                }
            }

            if (curr >= Integer.parseInt(sub.get(3).getParams().get(pIndex))
                    && curr >= Integer.parseInt(subA.get(2).getParams().get(pIndex))
                    && curr >= Integer.parseInt(subB.get(2).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(2).getParams().get(pIndex))) {
                        btMap.put("3-3-3-4 above", btMap.get("3-3-3-4 above") + 1);
                    }
                } else {
                    btMap.put("3-3-3-4 above", btMap.get("3-3-3-4 above") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(5).getParams().get(pIndex))
                    && curr <= Integer.parseInt(subA.get(2).getParams().get(pIndex))
                    && curr <= Integer.parseInt(subB.get(2).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(2).getParams().get(pIndex))) {
                        btMap.put("3-3-3-6 below", btMap.get("3-3-3-6 below") + 1);
                    }
                } else {
                    btMap.put("3-3-3-6 below", btMap.get("3-3-3-6 below") + 1);
                }
            }

            if (curr >= Integer.parseInt(sub.get(1).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(1).getParams().get(pIndex))) {
                        btMap.put("2-2gr above", btMap.get("2-2gr above") + 1);
                    }
                } else {
                    btMap.put("2-2gr above", btMap.get("2-2gr above") + 1);
                }
            }

            if (curr >= Integer.parseInt(sub.get(2).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(1).getParams().get(pIndex))) {
                        btMap.put("3-2gr above", btMap.get("3-2gr above") + 1);
                    }
                } else {
                    btMap.put("3-2gr above", btMap.get("3-2gr above") + 1);
                }
            }

            if (curr >= Integer.parseInt(sub.get(3).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr >= Integer.parseInt(subG.get(2).getParams().get(pIndex))) {
                        btMap.put("4-3gr above", btMap.get("4-3gr above") + 1);
                    }
                } else {
                    btMap.put("4-3gr above", btMap.get("4-3gr above") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(5).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(2).getParams().get(pIndex))) {
                        btMap.put("6-3gr below", btMap.get("6-3gr below") + 1);
                    }
                } else {
                    btMap.put("6-3gr below", btMap.get("6-3gr below") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(6).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(3).getParams().get(pIndex))) {
                        btMap.put("7-4gr below", btMap.get("7-4gr below") + 1);
                    }
                } else {
                    btMap.put("7-4gr below", btMap.get("7-4gr below") + 1);
                }
            }

            if (curr <= Integer.parseInt(sub.get(7).getParams().get(pIndex))) {
                if (subG.size() == 5) {
                    if (curr <= Integer.parseInt(subG.get(3).getParams().get(pIndex))) {
                        btMap.put("8-4gr below", btMap.get("8-4gr below") + 1);
                    }
                } else {
                    btMap.put("8-4gr below", btMap.get("8-4gr below") + 1);
                }
            }
        }

        return btMap;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        CricDB db = new CricDB();
        final Date backDate;
        java.util.Date tempDate = new java.util.Date();

        int matchType = Integer.parseInt(request.getParameter("tournament"));
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
            
            TestType oneType = hometeam.equalsIgnoreCase(teamOne) ? TestType.HOME : TestType.AWAY;
            TestType twoType = hometeam.equalsIgnoreCase(teamTwo) ? TestType.HOME : TestType.AWAY;
            
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

                        int fours = Integer.parseInt(q.getInningOne1().getParams().get(1))
                                + Integer.parseInt(q.getInningOne2().getParams().get(1)) 
                                + Integer.parseInt(q.getInningTwo1().getParams().get(1))
                                + Integer.parseInt(q.getInningTwo2().getParams().get(1));

                        int sixes = Integer.parseInt(q.getInningOne1().getParams().get(2))
                                + Integer.parseInt(q.getInningOne2().getParams().get(2))
                                + Integer.parseInt(q.getInningTwo1().getParams().get(2)) 
                                + Integer.parseInt(q.getInningTwo2().getParams().get(2));
                        
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
            Head_to_head :{
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
                    } else {
                        worl = BCW;
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
            
            Form_guide :{
                // <editor-fold defaultstate="collapsed">
                A:{
                    List<testMatch> matches = db.getTestMatches(teamOne, 0, oneType);
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
                        } else {
                            worl = BCW;
                        }
                        String bcwl = BorC + "/" + worl;

                        Inning m = q.getInningOne1();
                        List<String> params = m.getParams();
                        params.set(5, bcwl);
                        m.setParams(params);
                        selects.add(m);
                    }
                    request.setAttribute("FG_A", selects);
                }
                B:{
                    List<testMatch> matches = db.getTestMatches(teamTwo, 0, twoType);
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
                        } else {
                            worl = BCW;
                        }
                        String bcwl = BorC + "/" + worl;

                        Inning m = q.getInningOne1();
                        List<String> params = m.getParams();
                        params.set(5, bcwl);
                        m.setParams(params);
                        selects.add(m);
                    }
                    request.setAttribute("FG_B", selects);
                }
                // </editor-fold>
            }
            
            Fours_Sixes_Total_boundaries :{
                // <editor-fold defaultstate="collapsed">
                A :{
                    List<testMatch> matches = type0.getMatches(teamOne, oneType);
                    List<Inning> selects =  matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FST_A", selects.subList(0, Math.min(5, selects.size())));
                    
                    request.setAttribute("F_A_bt", backTest5(selects, foursIndex));
                    request.setAttribute("F_TA_bt", backTest10_Test(teamOne, matches, foursIndex, type0, One1Inning, twoType));
                    
                    request.setAttribute("S_A_bt", backTest5(selects, sixesIndex));
                    request.setAttribute("S_TA_bt", backTest10_Test(teamOne, matches, sixesIndex, type0, One1Inning, twoType));
                    
                    request.setAttribute("T_A_bt", backTest5(selects, totBoundariesIndex));
                    request.setAttribute("T_TA_bt", backTest10_Test(teamOne, matches, totBoundariesIndex, type0, One1Inning, twoType));
                    
                }
                B :{
                    List<testMatch> matches = type0.getMatches(teamTwo, twoType);
                    List<Inning> selects =  matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FST_B", selects.subList(0, Math.min(5, selects.size())));
                    
                    request.setAttribute("F_B_bt", backTest5(selects, foursIndex));
                    request.setAttribute("F_TB_bt", backTest10_Test(teamTwo, matches, foursIndex, type0, One1Inning, oneType));
                    
                    request.setAttribute("S_B_bt", backTest5(selects, sixesIndex));
                    request.setAttribute("S_TB_bt", backTest10_Test(teamTwo, matches, sixesIndex, type0, One1Inning, oneType));
                    
                    request.setAttribute("T_B_bt", backTest5(selects, totBoundariesIndex));
                    request.setAttribute("T_TB_bt", backTest10_Test(teamTwo, matches, totBoundariesIndex, type0, One1Inning, oneType));
                }
                G :{
                    List<testMatch> matches = type0.getGMatches(groundName);
                    List<Inning> selects =  matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FST_G", selects.subList(0, Math.min(5, selects.size())));
                    
                    request.setAttribute("F_G_bt", backTest5(selects, foursIndex));
                    
                    request.setAttribute("S_G_bt", backTest5(selects, sixesIndex));
                    
                    request.setAttribute("T_G_bt", backTest5(selects, totBoundariesIndex));
                }
                // </editor-fold>
            }
            
            First_Inning:{
                // <editor-fold defaultstate="collapsed">
                Total_and_first_wicket:{
                    A:{
                        List<testMatch> matches = type1.getMatches(teamOne, oneType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());


                        request.setAttribute("FX_A", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("FTR_A_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("FTR_TA_bt", backTest10_Test(teamOne, matches, totalRunsIndex, type2, One1Inning, twoType));
                        
                        request.setAttribute("FFW_A_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("FFW_TA_bt", backTest10_Test(teamOne, matches, firstWktIndex, type2, One1Inning, twoType));
                    }
                    B:{
                        List<testMatch> matches = type2.getMatches(teamTwo, twoType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());


                        request.setAttribute("FX_B", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("FTR_B_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("FTR_TB_bt", backTest10_Test(teamTwo, matches, totalRunsIndex, type1, One1Inning, oneType));
                        
                        request.setAttribute("FFW_B_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("FFW_TB_bt", backTest10_Test(teamTwo, matches, firstWktIndex, type1, One1Inning, oneType));
                    }
                    G:{
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());


                        request.setAttribute("FX_G", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("FTR_G_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("FFW_G_bt", backTest5(selects, firstWktIndex));
                    }
                }
                After_five_wickets:{
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
                    A:{
                        List<testMatch> matches = type1_N15.getMatches(teamOne, oneType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());


                        request.setAttribute("F5_A", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("F5_A_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("F5_TA_bt", backTest10_Test(teamOne, matches, fiveWktIndex, type2_N15, One1Inning, twoType));
                    }
                    B:{
                        List<testMatch> matches = type2_N15.getMatches(teamTwo, twoType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());


                        request.setAttribute("F5_B", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("F5_B_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("F5_TB_bt", backTest10_Test(teamTwo, matches, fiveWktIndex, type1_N15, One1Inning, oneType));
                    }
                    G:{
                        List<testMatch> matches = type1_N15.getGMatches(groundName);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningOne1()).collect(Collectors.toList());


                        request.setAttribute("F5_G", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("F5_G_bt", backTest5(selects, fiveWktIndex));
                    }
                }
                
                // </editor-fold>
            }
            
            Second_Inning:{
                // <editor-fold defaultstate="collapsed">
                Total_and_first_wicket:{
                    A:{
                        List<testMatch> matches = type2.getMatches(teamTwo, twoType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());


                        request.setAttribute("SX_A", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("STR_A_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("STR_TA_bt", backTest10_Test(teamTwo, matches, totalRunsIndex, type1, Two1Inning, oneType));
                        
                        request.setAttribute("SFW_A_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("SFW_TA_bt", backTest10_Test(teamTwo, matches, firstWktIndex, type1, Two1Inning, oneType));
                    }
                    B:{
                        List<testMatch> matches = type1.getMatches(teamOne, oneType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());


                        request.setAttribute("SX_B", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("STR_B_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("STR_TB_bt", backTest10_Test(teamOne, matches, totalRunsIndex, type2, Two1Inning, twoType));
                        
                        request.setAttribute("SFW_B_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("SFW_TB_bt", backTest10_Test(teamOne, matches, firstWktIndex, type2, Two1Inning, twoType));
                    }
                    G:{
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());


                        request.setAttribute("SX_G", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("STR_G_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("SFW_G_bt", backTest5(selects, firstWktIndex));
                    }
                }
                After_five_wickets:{
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
                    A:{
                        List<testMatch> matches = type2_N25.getMatches(teamTwo, twoType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());


                        request.setAttribute("S5_A", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("S5_A_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("S5_TA_bt", backTest10_Test(teamTwo, matches, fiveWktIndex, type1_N25, Two1Inning, oneType));
                    }
                    B:{
                        List<testMatch> matches = type1_N25.getMatches(teamOne, oneType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());


                        request.setAttribute("S5_B", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("S5_B_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("S5_TB_bt", backTest10_Test(teamOne, matches, fiveWktIndex, type2_N25, Two1Inning, twoType));
                    }
                    G:{
                        List<testMatch> matches = type1_N25.getGMatches(groundName);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningTwo1()).collect(Collectors.toList());


                        request.setAttribute("S5_G", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("S5_G_bt", backTest5(selects, fiveWktIndex));
                    }
                }
                
                // </editor-fold>
            }
            
            Third_Inning:{
                // <editor-fold defaultstate="collapsed">
                Total_and_first_wicket:{
                    A:{
                        List<testMatch> matches = type1.getMatches(teamOne, oneType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());


                        request.setAttribute("TX_A", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("TTR_A_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("TTR_TA_bt", backTest10_Test(teamOne, matches, totalRunsIndex, type2, One2Inning, twoType));
                        
                        request.setAttribute("TFW_A_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("TFW_TA_bt", backTest10_Test(teamOne, matches, firstWktIndex, type2, One2Inning, twoType));
                    }
                    B:{
                        List<testMatch> matches = type2.getMatches(teamTwo, twoType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());


                        request.setAttribute("TX_B", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("TTR_B_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("TTR_TB_bt", backTest10_Test(teamTwo, matches, totalRunsIndex, type1, One2Inning, oneType));
                        
                        request.setAttribute("TFW_B_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("TFW_TB_bt", backTest10_Test(teamTwo, matches, firstWktIndex, type1, One2Inning, oneType));
                    }
                    G:{
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());


                        request.setAttribute("TX_G", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("TTR_G_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("TFW_G_bt", backTest5(selects, firstWktIndex));
                    }
                }
                After_five_wickets:{
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
                    A:{
                        List<testMatch> matches = type1_N35.getMatches(teamOne, oneType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());


                        request.setAttribute("T5_A", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("T5_A_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("T5_TA_bt", backTest10_Test(teamOne, matches, fiveWktIndex, type2_N35, One2Inning, twoType));
                    }
                    B:{
                        List<testMatch> matches = type2_N35.getMatches(teamTwo, twoType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());


                        request.setAttribute("T5_B", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("T5_B_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("T5_TB_bt", backTest10_Test(teamTwo, matches, fiveWktIndex, type1_N35, One2Inning, oneType));
                    }
                    G:{
                        List<testMatch> matches = type1_N35.getGMatches(groundName);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningOne2()).collect(Collectors.toList());


                        request.setAttribute("T5_G", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("T5_G_bt", backTest5(selects, fiveWktIndex));
                    }
                }
                
                // </editor-fold>
            }
            
            Fourth_Inning:{
                // <editor-fold defaultstate="collapsed">
                Total_and_first_wicket:{
                    A:{
                        List<testMatch> matches = type2.getMatches(teamTwo, twoType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());


                        request.setAttribute("QX_A", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("QTR_A_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("QTR_TA_bt", backTest10_Test(teamTwo, matches, totalRunsIndex, type1, Two2Inning, oneType));
                        
                        request.setAttribute("QFW_A_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("QFW_TA_bt", backTest10_Test(teamTwo, matches, firstWktIndex, type1, Two2Inning, oneType));
                    }
                    B:{
                        List<testMatch> matches = type1.getMatches(teamOne, oneType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());


                        request.setAttribute("QX_B", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("QTR_B_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("QTR_TB_bt", backTest10_Test(teamOne, matches, totalRunsIndex, type2, Two2Inning, twoType));
                        
                        request.setAttribute("QFW_B_bt", backTest5(selects, firstWktIndex));
                        request.setAttribute("QFW_TB_bt", backTest10_Test(teamOne, matches, firstWktIndex, type2, Two2Inning, twoType));
                    }
                    G:{
                        List<testMatch> matches = type1.getGMatches(groundName);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());


                        request.setAttribute("QX_G", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("QTR_G_bt", backTest5(selects, totalRunsIndex));
                        request.setAttribute("QFW_G_bt", backTest5(selects, firstWktIndex));
                    }
                }
                After_five_wickets:{
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
                    A:{
                        List<testMatch> matches = type2_N25.getMatches(teamTwo, twoType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());


                        request.setAttribute("Q5_A", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("Q5_A_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("Q5_TA_bt", backTest10_Test(teamTwo, matches, fiveWktIndex, type1_N25, Two2Inning, oneType));
                    }
                    B:{
                        List<testMatch> matches = type1_N25.getMatches(teamOne, oneType);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());


                        request.setAttribute("Q5_B", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("Q5_B_bt", backTest5(selects, fiveWktIndex));
                        request.setAttribute("Q5_TB_bt", backTest10_Test(teamOne, matches, fiveWktIndex, type2_N25, Two2Inning, twoType));
                    }
                    G:{
                        List<testMatch> matches = type1_N25.getGMatches(groundName);
                        List<Inning> selects =  matches.stream().map(m -> m.getInningTwo2()).collect(Collectors.toList());


                        request.setAttribute("Q5_G", selects.subList(0, Math.min(5, selects.size())));
                        
                        request.setAttribute("Q5_G_bt", backTest5(selects, fiveWktIndex));
                    }
                }
                
                // </editor-fold>
            }
                        
            List<String> headers = new ArrayList();
            headers = db.getHeaders(matchType);
            request.setAttribute("headers", headers);
            request.setAttribute("teamOne", teamOne);
            request.setAttribute("teamTwo", teamTwo);
            request.setAttribute("groundName", groundName);

            request.setAttribute("hometeam", hometeam);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/testresults.jsp");
            dispatcher.forward(request, response);
            
        } else {
            
            MatchProcessor type0 = new MatchProcessor(){
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

                public List<Match> process(List<Match> matches){
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    matches.removeIf(m -> (m.getResult().contains("D/L")));
                    for (int i = 0; i < matches.size(); i++) {
                        Inning temp = matches.get(i).getInningOne();
                        int fours = Integer.parseInt(matches.get(i).getInningOne().getParams().get(4))
                                + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(4));
                        int sixes = Integer.parseInt(matches.get(i).getInningOne().getParams().get(5))
                                + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(5));
                        int totalB = fours + sixes;
                        List<String> ps = temp.getParams();
                        ps.set(4, String.valueOf(fours));
                        ps.set(5, String.valueOf(sixes));
                        ps.add(8, String.valueOf(totalB));
                        temp.setParams(ps);
                        matches.get(i).setInningOne(temp);
                        
                        Inning temp2 = matches.get(i).getInningTwo();
                        fours = Integer.parseInt(matches.get(i).getInningOne().getParams().get(4))
                                + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(4));
                        sixes = Integer.parseInt(matches.get(i).getInningOne().getParams().get(5))
                                + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(5));
                        totalB = fours + sixes;
                        List<String> ps2 = temp2.getParams();
                        ps2.set(4, String.valueOf(fours));
                        ps2.set(5, String.valueOf(sixes));
                        ps.add(8, String.valueOf(totalB));
                        temp2.setParams(ps2);
                        matches.get(i).setInningTwo(temp2);
                    }
                    return matches;
                }
                // </editor-fold>
            };

            MatchProcessor type1 = new MatchProcessor(){
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
                public List<Match> process(List<Match> matches){
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
            
            MatchProcessor type1_NoDLS = new MatchProcessor(){
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
                public List<Match> process(List<Match> matches){
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    matches.removeIf(m -> (m.getResult().contains("D/L")));
                    return matches;
                }
                // </editor-fold>
            };
            
            MatchProcessor type1_NoMajQuit = new MatchProcessor(){
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
                public List<Match> process(List<Match> matches){
                    matches.removeIf(m -> m.getResult().contains("MAJ_QUIT"));
                    return matches;
                }
                // </editor-fold>
            };
            
            MatchProcessor type2 = new MatchProcessor(){
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
                public List<Match> process(List<Match> matches){
                    return type1.process(matches);
                }
                // </editor-fold>
            };
            
            MatchProcessor type2_NoDLS = new MatchProcessor(){
                // <editor-fold defaultstate="collapsed">
                @Override
                public List<Match> getMatches(String teamName) {
                    List<Match> matches = db.getMatches(teamName, matchType, 2);
                    return process(matches);
                }

                @Override
                public List<Match> getGMatches(String groundName) {
                    return type1_NoDLS.getGMatches(groundName);
                }

                @Override
                public List<Match> process(List<Match> matches){
                    return type1_NoDLS.process(matches);
                }
                // </editor-fold>
            };
            
            MatchProcessor type2_NoMajQuit = new MatchProcessor(){
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
                public List<Match> process(List<Match> matches){
                    return type1_NoMajQuit.process(matches);
                }
                // </editor-fold>
            };
            
            Head_to_head:{
                // <editor-fold defaultstate="collapsed">
                List<Inning> hth = new ArrayList<>();

                List<Match> matches = db.getHth(matchType, teamOne, teamTwo);
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                for (int i = 0; i < Math.min(5, matches.size()); i++) {
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
                    } else {
                        worl = BCW;
                    }

                    if (res.contains("D/L")) {
                        worl = worl + "(D/L)";
                    }

                    params.set(7, BorC + "/" + worl);
                    m.setParams(params);
                    hth.add(m);
                }
                request.setAttribute("hth", hth);
                // </editor-fold>
            }

            Form_guide: {
                // <editor-fold defaultstate="collapsed">
                A:{
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
                        } else {
                            worl = BCW;
                        }

                        if (res.contains("D/L")) {
                            worl = worl + "(D/L)";
                        }

                        params.set(7, BorC + "/" + worl);
                        m.setParams(params);
                        selects.add(m);
                    }
                    selects = selects.subList(0, Math.min(5, selects.size()));
                    request.setAttribute("FormGuide_A", selects);
                }

                B:{
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
                        } else {
                            worl = BCW;
                        }

                        if (res.contains("D/L")) {
                            worl = worl + "(D/L)";
                        }

                        params.set(7, BorC + "/" + worl);
                        m.setParams(params);
                        selects.add(m);
                    }
                    selects = selects.subList(0, Math.min(5, selects.size()));
                    request.setAttribute("FormGuide_B", selects);
                }
                // </editor-fold>
            }
            
            boundaries:{
                // <editor-fold defaultstate="collapsed">
                A:{
                    List<Match> matches = type0.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FST_A", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("foursA_bt", backTest5(selects, 4));
                    request.setAttribute("sixesA_bt", backTest5(selects, 5));
                    request.setAttribute("boundariesA_bt", backTest5(selects, 8));
                    request.setAttribute("foursTA_bt", backTest10(teamOne, matches, 4, type0));
                    request.setAttribute("sixesTA_bt", backTest10(teamOne, matches, 5, type0));
                    request.setAttribute("boundariesTA_bt", backTest10(teamOne, matches, 6, type0));
                }
                B:{
                    List<Match> matches = type0.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                            
                    
                    request.setAttribute("FST_B", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("foursB_bt", backTest5(selects, 4));
                    request.setAttribute("sixesB_bt", backTest5(selects, 5));
                    request.setAttribute("boundariesB_bt", backTest5(selects, 8));
                    request.setAttribute("foursTB_bt", backTest10(teamTwo, matches, 4, type0));
                    request.setAttribute("sixesTB_bt", backTest10(teamTwo, matches, 5, type0));
                    request.setAttribute("boundariesTB_bt", backTest10(teamTwo, matches, 6, type0));
                }
                G:{
                    List<Match> matches = type0.getGMatches(groundName);
                    List<Inning> selects1 = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());

                            
                    request.setAttribute("Gr_First", selects1.subList(0, Math.min(5, selects1.size())));
                    request.setAttribute("foursG_bt", backTest5(selects1, 4));
                    request.setAttribute("sixesG_bt", backTest5(selects1, 5));
                    request.setAttribute("boundariesG_bt", backTest5(selects1, 6));
                }
                // </editor-fold>
            }
            
            BCW_total:{
                // <editor-fold defaultstate="collapsed">
                A:{
                    List<Match> matches = type1.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());


                    request.setAttribute("BCW_A", selects.subList(0, Math.min(5, selects.size())));
                }
                B:{
                    List<Match> matches = type2.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());


                    request.setAttribute("BCW_B", selects.subList(0, Math.min(5, selects.size())));
                }
                G:{
                    List<Match> matches = type1.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());


                    request.setAttribute("BCW_G", selects.subList(0, Math.min(5, selects.size())));
                }
                // </editor-fold>
            }
            
            First_Over:{
                // <editor-fold defaultstate="collapsed">
                A:{
                    List<Match> matches = type1.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FO_A", selects.subList(0, Math.min(5, selects.size())));
                }
                B:{
                    List<Match> matches = type2.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FO_B", selects.subList(0, Math.min(5, selects.size())));
                }
                // </editor-fold>
            }
            
            Last_X_Overs:{
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
                
                A:{
                    List<Match> matches = LO1.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("LO_A", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("LO_A_bt", backTest5(selects, pIndex));
                    request.setAttribute("LO_TA_bt", backTest10(teamOne, matches, pIndex, LO2));
                }
                B:{
                    List<Match> matches = LO2.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("LO_B", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("LO_B_bt", backTest5(selects, pIndex));
                    request.setAttribute("LO_TB_bt", backTest10(teamTwo, matches, pIndex, LO1));
                }
                G:{
                    List<Match> matches = LO1.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("LO_G", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("LO_G_bt", backTest5(selects, pIndex));
                }
                // </editor-fold>
            }
            
            First_Wicket:{
                // <editor-fold defaultstate="collapsed">
                int pIndex = 3;
                A:{
                    List<Match> matches = type1.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FW_A", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("FW_A_bt", backTest5(selects, pIndex));
                    request.setAttribute("FW_TA_bt", backTest10(teamOne, matches, pIndex, type2));
                }
                B:{
                    List<Match> matches = type2.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FW_B", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("FW_B_bt", backTest5(selects, pIndex));
                    request.setAttribute("FW_TB_bt", backTest10(teamTwo, matches, pIndex, type1));
                }
                G:{
                    List<Match> matches = type1.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FW_G_bt", backTest5(selects, pIndex));
                }
                // </editor-fold>
            }
            
            Total_runs:{
                // <editor-fold defaultstate="collapsed">
                int pIndex = 6;
                A:{
                    List<Match> matches = type1_NoMajQuit.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("TR_A", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("TR_A_bt", backTest5(selects, pIndex));
                    request.setAttribute("TR_TA_bt", backTest10(teamOne, matches, pIndex, type2_NoMajQuit));
                }
                B:{
                    List<Match> matches = type2_NoMajQuit.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("TR_B", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("TR_B_bt", backTest5(selects, pIndex));
                    request.setAttribute("TR_TB_bt", backTest10(teamTwo, matches, pIndex, type1_NoMajQuit));
                }
                G:{
                    List<Match> matches = type1_NoMajQuit.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("TR_G", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("TR_G_bt", backTest5(selects, pIndex));
                }
                // </editor-fold>
            }
            
            First_X_Overs:{
                // <editor-fold defaultstate="collapsed">
                int pIndex = 1;
                A:{
                    List<Match> matches = type1_NoMajQuit.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FX_A", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("FX_A_bt", backTest5(selects, pIndex));
                    request.setAttribute("FX_TA_bt", backTest10(teamOne, matches, pIndex, type2_NoMajQuit));
                }
                B:{
                    List<Match> matches = type2_NoMajQuit.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FX_B", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("FX_B_bt", backTest5(selects, pIndex));
                    request.setAttribute("FX_TB_bt", backTest10(teamTwo, matches, pIndex, type1_NoMajQuit));
                }
                G:{
                    List<Match> matches = type1_NoMajQuit.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningOne()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FX_G_bt", backTest5(selects, pIndex));
                }
                // </editor-fold>
            }
            
            First_X_Overs_Second:{
                // <editor-fold defaultstate="collapsed">
                int pIndex = 1;
                A:{
                    List<Match> matches = type2_NoMajQuit.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FXS_A", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("FXS_A_bt", backTest5(selects, pIndex));
                    request.setAttribute("FXS_TA_bt", backTest10_Second(teamTwo, matches, pIndex, type1_NoMajQuit));
                }
                B:{
                    List<Match> matches = type1_NoMajQuit.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FXS_B", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("FXS_B_bt", backTest5(selects, pIndex));
                    request.setAttribute("FXS_TB_bt", backTest10_Second(teamOne, matches, pIndex, type2_NoMajQuit));
                }
                G:{
                    List<Match> matches = type1_NoMajQuit.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FXS_G", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("FXS_G_bt", backTest5(selects, pIndex));
                }
                // </editor-fold>
            }
            
            First_Wicket_Second:{
                // <editor-fold defaultstate="collapsed">
                int pIndex = 3;
                A:{
                    List<Match> matches = type2.getMatches(teamTwo);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FWS_A", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("FWS_A_bt", backTest5(selects, pIndex));
                    request.setAttribute("FWS_TA_bt", backTest10_Second(teamTwo, matches, pIndex, type1));
                }
                B:{
                    List<Match> matches = type1.getMatches(teamOne);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FWS_B", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("FWS_B_bt", backTest5(selects, pIndex));
                    request.setAttribute("FWS_TB_bt", backTest10_Second(teamOne, matches, pIndex, type2));
                }
                G:{
                    List<Match> matches = type1.getGMatches(groundName);
                    List<Inning> selects = matches.stream().map(m -> m.getInningTwo()).collect(Collectors.toList());
                    
                    
                    request.setAttribute("FWS_G", selects.subList(0, Math.min(5, selects.size())));
                    request.setAttribute("FWS_G_bt", backTest5(selects, pIndex));
                }
                // </editor-fold>
            }

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
