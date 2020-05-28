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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.*;

/**
 *
 * @author DELL
 */
public class getData extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private void test(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException{
        CricDB db = new CricDB();
            final Date backDate;
            java.util.Date tempDate = new java.util.Date();

            int matchType = Integer.parseInt(request.getParameter("tournament"));
            String teamOne = request.getParameter("teamName1");
            String teamTwo = request.getParameter("teamName2");
            String groundName = request.getParameter("groundName");
            String hometeam = db.checkhomeoraway(teamOne, teamTwo, groundName);
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
            
        if (teamOne.equals(hometeam)) {

                    System.out.println("home is : " + teamOne);

                    List<testInning> t_oneBatFirst = new ArrayList<>();
                    List<testInning> t_oneBatSecond = new ArrayList<>();
                    List<testInning> t_twoBatFirst = new ArrayList<>();
                    List<testInning> t_twoBatSecond = new ArrayList<>();

                    List<testInning> t_oneBowlFirst = new ArrayList<>();
                    List<testInning> t_oneBowlSecond = new ArrayList<>();
                    List<testInning> t_twoBowlFirst = new ArrayList<>();
                    List<testInning> t_twoBowlSecond = new ArrayList<>();

                    List<testInning> t_groundFirst1 = new ArrayList<>();
                    List<testInning> t_groundSecond1 = new ArrayList<>();
                    List<testInning> t_groundFirst2 = new ArrayList<>();
                    List<testInning> t_groundSecond2 = new ArrayList<>();

                    testInning temp;
                    List<testMatch> matches;
                    matches = db.gettestMatches(teamOne, matchType, 1);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    
                    // out.println(matches);
                    int k = 5;
                    for (int i = 0; i < Math.min(k, matches.size()); i++) {
                        temp = matches.get(i).getInningOne1();
                        /*out.println(temp.getInningId());
                out.println(temp.getFirstwicket());
                out.println(temp.getFours());
                out.println(temp.getRuns5wicket());
                out.println(temp.getSixes() );
                out.println(temp.getTotalruns());
                //out.println(temp);   
                         */

                        int fours = matches.get(i).getInningOne1().getFours()
                                + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours()
                                + matches.get(i).getInningTwo2().getFours();
                        //out.println(fours);
                        int sixes = matches.get(i).getInningOne1().getSixes()
                                + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                                + matches.get(i).getInningTwo2().getSixes();
                        //out.println(sixes);
                        temp.setFours(fours);
                        temp.setSixes(sixes);
                        t_oneBatFirst.add(temp);
                        if (temp.getRuns5wicket() == -1) {
                            k++;
                        }
                    }

                    matches.clear();
                    k = 5;
                    matches = db.gettestMatches(teamTwo, matchType, 2);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(k, matches.size()); i++) {
                        temp = matches.get(i).getInningTwo1();
                        int fours = matches.get(i).getInningOne1().getFours()
                                + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours()
                                + matches.get(i).getInningTwo2().getFours();
                        int sixes = matches.get(i).getInningOne1().getSixes()
                                + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                                + matches.get(i).getInningTwo2().getSixes();
                        temp.setFours(fours);
                        temp.setSixes(sixes);
                        t_twoBatSecond.add(temp);
                        if (temp.getRuns5wicket() == -1) {
                            k++;
                        }
                    }

                    matches.clear();
                    k = 5;
                    matches = db.gettestMatches(teamOne, matchType, 1);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(k, matches.size()); i++) {
                        temp = matches.get(i).getInningTwo1();
                        int fours = matches.get(i).getInningOne1().getFours()
                                + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours()
                                + matches.get(i).getInningTwo2().getFours();
                        int sixes = matches.get(i).getInningOne1().getSixes()
                                + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                                + matches.get(i).getInningTwo2().getSixes();
                        temp.setFours(fours);
                        temp.setSixes(sixes);
                        t_oneBowlSecond.add(temp);
                        if (temp.getRuns5wicket() == -1) {
                            k++;
                        }
                    }

                    matches.clear();
                    k = 5;
                    matches = db.gettestMatches(teamTwo, matchType, 2);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(k, matches.size()); i++) {
                        temp = matches.get(i).getInningOne1();
                        int fours = matches.get(i).getInningOne1().getFours()
                                + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours()
                                + matches.get(i).getInningTwo2().getFours();
                        int sixes = matches.get(i).getInningOne1().getSixes()
                                + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                                + matches.get(i).getInningTwo2().getSixes();
                        temp.setFours(fours);
                        temp.setSixes(sixes);
                        t_twoBowlFirst.add(temp);
                        if (temp.getRuns5wicket() == -1) {
                            k++;
                        }
                    }

                    List<testInning> one1 = new ArrayList<>();
                    List<testInning> two1 = new ArrayList<>();
                    List<testInning> one2 = new ArrayList<>();
                    List<testInning> two2 = new ArrayList<>();

                    matches.clear();

                    testInning temp11;
                    testInning temp12;
                    testInning temp21;
                    testInning temp22;

                    matches = db.gettestMatches(teamOne, matchType, 0);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(5, matches.size()); i++) {
                        if (matches.get(i).getBattingFirst() == teamOne) {
                            temp11 = matches.get(i).getInningOne1();
                            temp12 = matches.get(i).getInningOne2();
                        } else {
                            temp11 = matches.get(i).getInningTwo1();
                            temp12 = matches.get(i).getInningTwo2();

                        }
                        int fours = matches.get(i).getInningOne1().getFours()
                                + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours()
                                + matches.get(i).getInningTwo2().getFours();
                        int sixes = matches.get(i).getInningOne1().getSixes()
                                + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                                + matches.get(i).getInningTwo2().getSixes();
                        temp11.setFours(fours);
                        temp12.setFours(fours);
                        temp11.setSixes(sixes);
                        temp12.setSixes(sixes);

                        one1.add(temp11);
                        one2.add(temp12);
                    }
                    request.setAttribute("one1", one1);
                    request.setAttribute("one2", one2);

                    matches.clear();

                    matches = db.gettestMatches(teamTwo, matchType, 0);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(5, matches.size()); i++) {
                        if (matches.get(i).getBattingFirst() == teamTwo) {
                            temp21 = matches.get(i).getInningOne1();
                            temp22 = matches.get(i).getInningOne2();
                        } else {
                            temp21 = matches.get(i).getInningTwo1();
                            temp22 = matches.get(i).getInningTwo2();

                        }
                        int fours = matches.get(i).getInningOne1().getFours()
                                + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours()
                                + matches.get(i).getInningTwo2().getFours();
                        int sixes = matches.get(i).getInningOne1().getSixes()
                                + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                                + matches.get(i).getInningTwo2().getSixes();
                        temp21.setFours(fours);
                        temp22.setFours(fours);
                        temp21.setSixes(sixes);
                        temp22.setSixes(sixes);

                        two1.add(temp21);
                        two2.add(temp22);
                    }
                    request.setAttribute("two1", two1);
                    request.setAttribute("two2", two2);

                    matches.clear();

                    matches = db.gettestGroundInfo(groundName, matchType);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    k = 5;
                    for (int i = 0; i < Math.min(k, matches.size()); i++) {
                        testMatch q = matches.get(i);

                        t_groundFirst1.add(q.getInningOne1());
                        t_groundFirst2.add(q.getInningOne2());
                        t_groundSecond1.add(q.getInningTwo1());
                        t_groundSecond2.add(q.getInningTwo2());
                    }
                    matches.clear();

                    /*         
            matches = db.gettestGroundInfo(groundName, matchType);
            
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                temp11 = matches.get(i).getInningOne1();
                temp12 = matches.get(i).getInningOne2();
                temp21 = matches.get(i).getInningTwo1();
                temp22 = matches.get(i).getInningTwo2();
                
 /*               int fours = matches.get(i).getInningOne1().getFours()
                        + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours() 
                        + matches.get(i).getInningTwo2().getFours();
                int sixes = matches.get(i).getInningOne1().getSixes()
                        + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                        + matches.get(i).getInningTwo2().getSixes(); 
                
                temp21.setFours(fours);
                temp22.setFours(fours);
                temp21.setSixes(sixes);
                temp22.setSixes(sixes);
                
                t_groundFirst1.add(temp11);
                t_groundFirst2.add(temp12);
                t_groundSecond1.add(temp21);
                t_groundSecond2.add(temp22);               

 //               if (temp.getParams().get(2).equals("-1") || temp2.getParams().get(2).equals("-1")) {
 //                   k++;
 //               }

            }
            
                     */
                    List<testInning> t_oneBatFirstX = new ArrayList<>();
                    List<testInning> t_twoBowlFirstX = new ArrayList<>();
                    List<testInning> t_groundFirst1X = new ArrayList<>();
                    List<testInning> t_groundSecond1X = new ArrayList<>();
                    List<testInning> t_groundFirst2X = new ArrayList<>();
                    List<testInning> t_groundSecond2X = new ArrayList<>();

                    for (testInning q : t_oneBatFirst) {
                        if (q.getRuns5wicket() != -1) {
                            t_oneBatFirstX.add(q);
                        }
                    }

                    for (testInning q : t_twoBowlFirst) {
                        if (q.getRuns5wicket() != -1) {
                            t_twoBowlFirstX.add(q);
                        }
                    }

                    for (testInning q : t_groundFirst1) {
                        if (q.getRuns5wicket() != -1) {
                            t_groundFirst1X.add(q);
                        }
                    }
                    for (testInning q : t_groundSecond1) {
                        if (q.getRuns5wicket() != -1) {
                            t_groundSecond1X.add(q);
                        }
                    }
                    for (testInning q : t_groundFirst2) {
                        if (q.getRuns5wicket() != -1) {
                            t_groundFirst2X.add(q);
                        }
                    }

                    for (testInning q : t_groundSecond2) {
                        if (q.getRuns5wicket() != -1) {
                            t_groundSecond2X.add(q);
                        }
                    }

                    t_oneBatFirst = t_oneBatFirst.subList(0, Math.min(5, t_oneBatFirst.size()));
                    t_twoBowlFirst = t_twoBowlFirst.subList(0, Math.min(5, t_twoBowlFirst.size()));
                    /*            t_groundFirst1 = t_groundFirst1.subList(0, Math.min(5, t_groundFirst1.size()));
            t_groundSecond1 = t_groundSecond1.subList(0, Math.min(5, t_groundSecond1.size()));
            t_groundFirst2 = t_groundFirst2.subList(0, Math.min(5, t_groundFirst2.size()));
            t_groundSecond2 = t_groundSecond2.subList(0, Math.min(5, t_groundSecond2.size()));
                     */
                    request.setAttribute("t_oneBatFirstX", t_oneBatFirstX);
                    request.setAttribute("t_twoBowlFirstX", t_twoBowlFirstX);
                    request.setAttribute("t_groundFirst1X", t_groundFirst1);
                    request.setAttribute("t_groundSecond1X", t_groundSecond1);
                    request.setAttribute("t_groundFirst2X", t_groundFirst2);
                    request.setAttribute("t_groundSecond2X", t_groundSecond2);

                    List<testInning> t_twoBatSecondX = new ArrayList<>();
                    List<testInning> t_oneBowlSecondX = new ArrayList<>();

                    for (testInning q : t_twoBatSecond) {
                        if (q.getRuns5wicket() != -1) {
                            t_twoBatSecondX.add(q);
                        }
                    }

                    for (testInning q : t_oneBowlSecond) {
                        if (q.getRuns5wicket() != -1) {
                            t_oneBowlSecondX.add(q);
                        }
                    }

                    t_twoBatSecond = t_twoBatSecond.subList(0, Math.min(5, t_twoBatSecond.size()));
                    t_oneBowlSecond = t_oneBowlSecond.subList(0, Math.min(5, t_oneBowlSecond.size()));

                    request.setAttribute("t_twoBatSecondX", t_twoBatSecondX);
                    request.setAttribute("t_oneBowlSecondX", t_oneBowlSecondX);

//            for(int i = 0; i < matches.size(); i++){
//                out.print("<h1>"+matches.get(i));
//            }
                    List<testInning> t_oneBatFirstY = new ArrayList<>();
                    List<testInning> t_twoBowlFirstY = new ArrayList<>();
                    List<testInning> t_oneBatFirst1 = new ArrayList<>();
                    List<testInning> t_twoBowlFirst1 = new ArrayList<>();
                    List<testInning> t_oneBatFirst2 = new ArrayList<>();
                    List<testInning> t_twoBowlFirst2 = new ArrayList<>();
                    List<testInning> onetotal = new ArrayList<>();
                    List<testInning> twototal = new ArrayList<>();
                    
                    matches.clear();
                    matches = db.gettesthome(teamOne, 1, 0);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));

                    for (testMatch q : matches) {
                        int fours;
                        int sixes;

                        fours = q.getInningOne1().getFours()
                                + q.getInningOne2().getFours() + q.getInningTwo1().getFours() + q.getInningTwo2().getFours();

                        sixes = q.getInningOne1().getSixes()
                                + q.getInningOne2().getSixes() + q.getInningTwo1().getSixes() + q.getInningTwo2().getSixes();

                        testInning m = q.getInningOne1();
                        m.setFours(fours);
                        m.setSixes(sixes);
                        onetotal.add(m);

                    }

                    matches.clear();
                    matches = db.gettestaway(teamTwo, 1, 0);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (testMatch q : matches) {
                        int fours;
                        int sixes;
                        fours = q.getInningOne1().getFours()
                                + q.getInningOne2().getFours() + q.getInningTwo1().getFours() + q.getInningTwo2().getFours();

                        sixes = q.getInningOne1().getSixes()
                                + q.getInningOne2().getSixes() + q.getInningTwo1().getSixes() + q.getInningTwo2().getSixes();
                        testInning m = q.getInningOne1();
                        m.setFours(fours);
                        m.setSixes(sixes);
                        twototal.add(m);

                    }

                    matches.clear();
                    matches = db.gettesthome(teamOne, 1, 0);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (testMatch q : matches) {
                        String res = q.getResult();
                        String worl;
                        String borc;

                        if (res.contains(teamOne)) {
                            worl = "Win";
                        } else if (res.equals("Match drawn")) {
                            worl = "Draw";
                        } else {
                            worl = "Lose";
                        }

                        if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                            borc = "B";
                        } else {
                            borc = "C";
                        }

                        /* String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamOne)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(" wicket")){
                    if(q.getHomeTeam().equalsIgnoreCase(teamOne)){
                        worl = "L";
                    }
                    else{
                        worl = "W";
                    }
                }
                else if(res.contains(" run")){
                    if(q.getHomeTeam().equalsIgnoreCase(teamOne)){
                        worl = "W";
                    }
                    else{
                        worl = "L";
                    }
                }else{
                    worl = "-";
                }
                         */
                        testInning m = q.getInningOne1();

                        m.setWinner(borc + "/" + worl);
                        t_oneBatFirstY.add(m);
                    }

                    matches.clear();
                    matches = db.gettestaway(teamTwo, 1, 0);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (testMatch q : matches) {
                        String res = q.getResult();
                        String worl;
                        String borc;

                        if (res.contains(teamTwo)) {
                            worl = "Win";
                        } else if (res.equals("Match drawn")) {
                            worl = "Draw";
                        } else {
                            worl = "Lose";
                        }

                        if (q.getHomeTeam().equalsIgnoreCase(teamTwo)) {
                            borc = "B";
                        } else {
                            borc = "C";
                        }
                        /*(
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamOne)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(" wicket")){
                    if(q.getHomeTeam().equalsIgnoreCase(teamTwo)){
                        worl = "L";
                    }
                    else{
                        worl = "W";
                    }
                }
                else if(res.contains(" run")){
                    if(q.getHomeTeam().equalsIgnoreCase(teamTwo)){
                        worl = "W";
                    }
                    else{
                        worl = "L";
                    }
                }else{
                    worl = "-";
                }
                         */
                        testInning m = q.getInningOne1();

                        m.setWinner(borc + "/" + worl);
                        t_twoBowlFirstY.add(m);
                    }

                    for (testInning q : t_oneBatFirstY) {
                        if (q.getRuns5wicket() != -1) {
                            t_oneBatFirst1.add(q);
                        }
                    }

                    for (testInning q : t_twoBowlFirstY) {
                        if (q.getRuns5wicket() != -1) {
                            t_twoBowlFirst1.add(q);
                        }
                    }
                    t_oneBatFirst2 = t_oneBatFirst1.subList(0, Math.min(5, t_oneBatFirst1.size()));
                    t_twoBowlFirst2 = t_twoBowlFirst1.subList(0, Math.min(5, t_twoBowlFirst1.size()));
                    onetotal = onetotal.subList(0, Math.min(5, onetotal.size()));
                    twototal = twototal.subList(0, Math.min(5, twototal.size()));
                    request.setAttribute("t_oneBatFirstY", t_oneBatFirst2);
                    request.setAttribute("t_twoBowlFirstY", t_twoBowlFirst2);

                    List<testInning> hth = new ArrayList<>();
                    List<testMatch> hthmatch = new ArrayList<>();
                    
                    matches.clear();
                    matches = db.gettestHth(matchType, teamOne, teamTwo);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(5, matches.size()); i++) {
                        testMatch q = matches.get(i);

                        String res = q.getResult();
                        String worl = "";

                        String BorC = "";
                        if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                            BorC = "B";
                        } else {
                            BorC = "C";
                        }

                        if (res.contains(" wicket")) {
                            if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                                worl = "L";
                            } else {
                                worl = "W";
                            }
                        } else if (res.contains(" run")) {
                            if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                                worl = "W";
                            } else {
                                worl = "L";
                            }
                        } else {
                            worl = "-";
                        }
                        testInning m = q.getInningOne1();
                        String bcwl = BorC + "/" + worl;
                        m.setWinner(bcwl);
                        hth.add(m);
                        hthmatch.add(q);

                    }

                    List<testInning> t_teamoneBatFirst = new ArrayList<>();
                    List<testInning> t_teamoneBatThird = new ArrayList<>();
                    List<testInning> t_teamoneBowlSecond = new ArrayList<>();
                    List<testInning> t_teamoneBowlFourth = new ArrayList<>();

                    List<testInning> t_teamtwoBowlFirst = new ArrayList<>();
                    List<testInning> t_teamtwoBowlThird = new ArrayList<>();
                    List<testInning> t_teamtwoBatSecond = new ArrayList<>();
                    List<testInning> t_teamtwoBatFourth = new ArrayList<>();
                    List<testInning> t_teamoneBatFirst1 = new ArrayList<>();
                    List<testInning> t_teamoneBatThird1 = new ArrayList<>();
                    List<testInning> t_teamoneBowlSecond1 = new ArrayList<>();
                    List<testInning> t_teamoneBowlFourth1 = new ArrayList<>();

                    List<testInning> t_teamtwoBowlFirst1 = new ArrayList<>();
                    List<testInning> t_teamtwoBowlThird1 = new ArrayList<>();
                    List<testInning> t_teamtwoBatSecond1 = new ArrayList<>();
                    List<testInning> t_teamtwoBatFourth1 = new ArrayList<>();

//Team one Bat First Innings LAST 5      
                    matches = db.gettesthome(teamOne, 1, 1);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    k = 5;
                    for (int i = 0; i < matches.size(); i++) {
                        testMatch q = matches.get(i);
                        String res = q.getResult();
                        String worl = "";

                        String BorC = "";
                        if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                            BorC = "B";
                        } else {
                            BorC = "C";
                        }
                        if (res.contains(teamOne)) {
                            worl = "Win";
                        } else if (res.equals("Match drawn")) {
                            worl = "Draw";
                        } else {
                            worl = "Lose";
                        }

                        testInning m = q.getInningOne1();
                        m.setWinner(BorC + "/" + worl);

                        t_teamoneBatFirst.add(m);

                        testInning b = q.getInningOne2();
                        b.setWinner(BorC + "/" + worl);
                        t_teamoneBatThird.add(b);

                        testInning c = q.getInningTwo1();
                        c.setWinner(BorC + "/" + worl);
                        t_teamoneBowlSecond.add(c);

                        testInning d = q.getInningTwo2();
                        d.setWinner(BorC + "/" + worl);
                        t_teamoneBowlFourth.add(d);
                    }
                    //           matches.clear();

                    //           matches.clear();
//Team One Bats Fourth Innings LAST 5            
                    //          matches.clear();
//Team two Bowls First Innings LAST 5            
                    matches = db.gettestaway(teamTwo, 1, 2);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));

                    for (int i = 0; i < matches.size(); i++) {
                        testMatch q = matches.get(i);
                        String res = q.getResult();
                        String worl = "";

                        String BorC = "";
                        if (q.getHomeTeam().equalsIgnoreCase(teamTwo)) {
                            BorC = "B";
                        } else {
                            BorC = "C";
                        }

                        if (res.contains(teamTwo)) {
                            worl = "Win";
                        } else if (res.equals("Match drawn")) {
                            worl = "Draw";
                        } else {
                            worl = "Lose";
                        }

                        testInning m = q.getInningOne1();
                        m.setWinner(BorC + "/" + worl);

                        t_teamtwoBowlFirst.add(m);

                        testInning b = q.getInningOne2();
                        b.setWinner(BorC + "/" + worl);
                        t_teamtwoBowlThird.add(b);

                        testInning c = q.getInningTwo1();
                        c.setWinner(BorC + "/" + worl);
                        t_teamtwoBatSecond.add(c);

                        testInning d = q.getInningTwo2();
                        d.setWinner(BorC + "/" + worl);
                        t_teamtwoBatFourth.add(d);
                    }

                    for (testInning q : t_teamoneBatFirst) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamoneBatFirst1.add(q);
                        }
                    }
                    for (testInning q : t_teamoneBatThird) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamoneBatThird1.add(q);
                        }
                    }
                    for (testInning q : t_teamoneBowlSecond) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamoneBowlSecond1.add(q);
                        }
                    }
                    for (testInning q : t_teamoneBowlFourth) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamoneBowlFourth1.add(q);
                        }
                    }

                    for (testInning q : t_teamtwoBatSecond) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamtwoBatSecond1.add(q);
                        }
                    }
                    for (testInning q : t_teamtwoBatFourth) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamtwoBatFourth1.add(q);
                        }
                    }
                    for (testInning q : t_teamtwoBowlFirst) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamtwoBowlFirst1.add(q);
                        }
                    }
                    for (testInning q : t_teamtwoBowlThird) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamtwoBowlThird1.add(q);
                        }
                    }

                    t_teamoneBatFirst1 = t_teamoneBatFirst1.subList(0, Math.min(5, t_teamoneBatFirst1.size()));
                    t_teamoneBatThird1 = t_teamoneBatThird1.subList(0, Math.min(5, t_teamoneBatThird1.size()));
                    t_teamoneBowlSecond1 = t_teamoneBowlSecond1.subList(0, Math.min(5, t_teamoneBowlSecond1.size()));
                    t_teamoneBowlFourth1 = t_teamoneBowlFourth1.subList(0, Math.min(5, t_teamoneBowlFourth1.size()));

                    t_teamtwoBowlFirst1 = t_teamtwoBowlFirst1.subList(0, Math.min(5, t_teamtwoBowlFirst1.size()));
                    t_teamtwoBowlThird1 = t_teamtwoBowlThird1.subList(0, Math.min(5, t_teamtwoBowlThird1.size()));
                    t_teamtwoBatSecond1 = t_teamtwoBatSecond1.subList(0, Math.min(5, t_teamtwoBatSecond1.size()));
                    t_teamtwoBatFourth1 = t_teamtwoBatFourth1.subList(0, Math.min(5, t_teamtwoBatFourth1.size()));

                    if(true){
                        List<testMatch> totMatches = new ArrayList<>();
                        totMatches = db.gettesthome(teamOne, 1, 1);
                        totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                        totMatches.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                        totMatches.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));
                        
                        List<testInning> tZ = new ArrayList<>();
                        for(int i = 0; i < totMatches.size(); i++){
                            tZ.add(totMatches.get(i).getInningOne1());
                        }
                        
                        Map<String,Integer> bt = new LinkedHashMap<>();
                        bt.put("< 1/5", 0);
                        bt.put("1/5 - 2/5", 0);
                        bt.put("2/5 - 3/5", 0);
                        bt.put("3/5 - 4/5", 0);
                        bt.put("4/5 - 5/5", 0);
                        bt.put("5/5 <", 0);

                        if(tZ.size() > 5){
                            for(int i = 0; i < tZ.size()-6; i++){
                                int curr = tZ.get(i).getTotalruns();

                                List<testInning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                                Collections.sort(sub, new Comparator<testInning>() {
                                    @Override
                                    public int compare(testInning o1, testInning o2) {
                                        return o1.getTotalruns()
                                                - o2.getTotalruns();
                                    }
                                });

                                if(curr < sub.get(0).getTotalruns()){
                                    bt.put("< 1/5", bt.get("< 1/5")+1 );
                                }
                                else if(curr >= sub.get(0).getTotalruns()
                                        && curr < sub.get(1).getTotalruns() ){
                                    bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                                }
                                else if(curr >= sub.get(1).getTotalruns()
                                        && curr < sub.get(2).getTotalruns() ){
                                    bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                                }
                                else if(curr >= sub.get(2).getTotalruns() 
                                        && curr < sub.get(3).getTotalruns() ){
                                    bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                                }
                                else if(curr >= sub.get(3).getTotalruns() 
                                        && curr < sub.get(4).getTotalruns() ){
                                    bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                                }
                                else{
                                    bt.put("5/5 <", bt.get("5/5 <")+1 );
                                }
                            } 
                        }

                        request.setAttribute("t_teamoneBatFirst0_bt", bt);
                    }
                    
                    
                    if(true){
                        List<testMatch> totMatches = new ArrayList<>();
                        totMatches = db.gettestaway(teamTwo, 1, 2);
                        totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                        totMatches.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                        totMatches.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));
                        
                        List<testInning> tZ = new ArrayList<>();
                        for(int i = 0; i < totMatches.size(); i++){
                            tZ.add(totMatches.get(i).getInningOne1());
                        }
                        
                        Map<String,Integer> bt = new LinkedHashMap<>();
                        bt.put("< 1/5", 0);
                        bt.put("1/5 - 2/5", 0);
                        bt.put("2/5 - 3/5", 0);
                        bt.put("3/5 - 4/5", 0);
                        bt.put("4/5 - 5/5", 0);
                        bt.put("5/5 <", 0);

                        if(tZ.size() > 5){
                            for(int i = 0; i < tZ.size()-6; i++){
                                int curr = tZ.get(i).getTotalruns();

                                List<testInning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                                Collections.sort(sub, new Comparator<testInning>() {
                                    @Override
                                    public int compare(testInning o1, testInning o2) {
                                        return o1.getTotalruns()
                                                - o2.getTotalruns();
                                    }
                                });

                                if(curr < sub.get(0).getTotalruns()){
                                    bt.put("< 1/5", bt.get("< 1/5")+1 );
                                }
                                else if(curr >= sub.get(0).getTotalruns()
                                        && curr < sub.get(1).getTotalruns() ){
                                    bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                                }
                                else if(curr >= sub.get(1).getTotalruns()
                                        && curr < sub.get(2).getTotalruns() ){
                                    bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                                }
                                else if(curr >= sub.get(2).getTotalruns() 
                                        && curr < sub.get(3).getTotalruns() ){
                                    bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                                }
                                else if(curr >= sub.get(3).getTotalruns() 
                                        && curr < sub.get(4).getTotalruns() ){
                                    bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                                }
                                else{
                                    bt.put("5/5 <", bt.get("5/5 <")+1 );
                                }
                            } 
                        }

                        request.setAttribute("t_teamtwoBowlFirst0_bt", bt);
                    }
                    
                    if(true){
                        List<testMatch> totMatches = new ArrayList<>();
                        totMatches = db.gettestGroundInfo(groundName, matchType);
                        totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                        totMatches.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                        totMatches.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));
                        
                        List<testInning> tZ = new ArrayList<>();
                        for(int i = 0; i < totMatches.size(); i++){
                            tZ.add(totMatches.get(i).getInningOne1());
                        }
                        
                        Map<String,Integer> bt = new LinkedHashMap<>();
                        bt.put("< 1/5", 0);
                        bt.put("1/5 - 2/5", 0);
                        bt.put("2/5 - 3/5", 0);
                        bt.put("3/5 - 4/5", 0);
                        bt.put("4/5 - 5/5", 0);
                        bt.put("5/5 <", 0);

                        if(tZ.size() > 5){
                            for(int i = 0; i < tZ.size()-6; i++){
                                int curr = tZ.get(i).getTotalruns();

                                List<testInning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                                Collections.sort(sub, new Comparator<testInning>() {
                                    @Override
                                    public int compare(testInning o1, testInning o2) {
                                        return o1.getTotalruns()
                                                - o2.getTotalruns();
                                    }
                                });

                                if(curr < sub.get(0).getTotalruns()){
                                    bt.put("< 1/5", bt.get("< 1/5")+1 );
                                }
                                else if(curr >= sub.get(0).getTotalruns()
                                        && curr < sub.get(1).getTotalruns() ){
                                    bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                                }
                                else if(curr >= sub.get(1).getTotalruns()
                                        && curr < sub.get(2).getTotalruns() ){
                                    bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                                }
                                else if(curr >= sub.get(2).getTotalruns() 
                                        && curr < sub.get(3).getTotalruns() ){
                                    bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                                }
                                else if(curr >= sub.get(3).getTotalruns() 
                                        && curr < sub.get(4).getTotalruns() ){
                                    bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                                }
                                else{
                                    bt.put("5/5 <", bt.get("5/5 <")+1 );
                                }
                            } 
                        }

                        request.setAttribute("t_groundFirst1X0_bt", bt);
                    }
                    
                    if(true){
                        Map<String,Integer> A_bt = new LinkedHashMap<>();
                        A_bt.put("N", 0);
                        A_bt.put("< 1/10", 0);
                        A_bt.put("1/10 - 2/10", 0);
                        A_bt.put("2/10 - 3/10", 0);
                        A_bt.put("3/10 - 4/10", 0);
                        A_bt.put("4/10 - 5/10", 0);
                        A_bt.put("5/10 - 6/10", 0);
                        A_bt.put("6/10 - 7/10", 0);
                        A_bt.put("7/10 - 8/10", 0);
                        A_bt.put("8/10 - 9/10", 0);
                        A_bt.put("9/10 - 10/10", 0);
                        A_bt.put("10/10 <", 0);
                        A_bt.put("2-2-2-3 above", 0);
                        A_bt.put("4-4-4-7 below", 0);
                        A_bt.put("3-3-3-4 above", 0);
                        A_bt.put("3-3-3-6 below", 0);
                        A_bt.put("2 above", 0);
                        A_bt.put("3 above", 0);
                        A_bt.put("4 above", 0);
                        A_bt.put("6 below", 0);
                        A_bt.put("7 below", 0);
                        A_bt.put("8 below", 0);

                        int hind = 1;

                        List<testMatch> oneMatch = db.gettesthome(teamOne, 1, 1);
                        oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                        oneMatch.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                        oneMatch.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));

                        List<testMatch> twoMatch = db.gettestaway(teamTwo, 1, 2);
                        twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                        twoMatch.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                        twoMatch.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));

                        List<testMatch> grMatch = db.gettestGroundInfo(groundName, matchType);
                        grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                        grMatch.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                        grMatch.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));

                        if(oneMatch.size() > 5 && twoMatch.size() > 5){
                            for(int i = 0; i < oneMatch.size()-6; i++){

                                int curr = oneMatch.get(i).getInningOne1().getTotalruns();
                                Date currDate = oneMatch.get(i).getMatchDate();


                                List<testInning> sub = new ArrayList<>();
                                List<testInning> subA = new ArrayList<>();
                                List<testInning> subB = new ArrayList<>();
                                List<testInning> subG = new ArrayList<>();

                                for(int j = i+1; j < i+6; j++){
                                    sub.add(oneMatch.get(j).getInningOne1());
                                    subA.add(oneMatch.get(j).getInningOne1());
                                }

                                twoMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                                if(twoMatch.size() < 5){
                                    break;
                                }
                                for(int j = 0; j < 5; j++){
                                    sub.add(twoMatch.get(j).getInningOne1());
                                    subB.add(twoMatch.get(j).getInningOne1());
                                }

                                grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                                if(grMatch.size() >= 5){
                                    for(int j = 0; j < 5; j++){
                                        subG.add(twoMatch.get(j).getInningOne1());
                                    }
                                }

                                Comparator innComp = new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    };

                                Collections.sort(sub, innComp);
                                Collections.sort(subA, innComp);
                                Collections.sort(subB, innComp);
                                Collections.sort(subG, innComp);

                                A_bt.put("N", A_bt.get("N")+1);

                                if(curr < sub.get(0).getTotalruns()){
                                    A_bt.put("< 1/10", A_bt.get("< 1/10")+1);
                                }
                                else if(curr >= sub.get(0).getTotalruns()
                                        && curr < sub.get(1).getTotalruns()){
                                    A_bt.put("1/10 - 2/10", A_bt.get("1/10 - 2/10")+1);
                                }
                                else if(curr >= sub.get(1).getTotalruns()
                                        && curr < sub.get(2).getTotalruns()){
                                    A_bt.put("2/10 - 3/10", A_bt.get("2/10 - 3/10")+1);
                                }
                                else if(curr >= sub.get(2).getTotalruns()
                                        && curr < sub.get(3).getTotalruns() ){
                                    A_bt.put("3/10 - 4/10", A_bt.get("3/10 - 4/10")+1);
                                }
                                else if(curr >= sub.get(3).getTotalruns()
                                        && curr < sub.get(4).getTotalruns()){
                                    A_bt.put("4/10 - 5/10", A_bt.get("4/10 - 5/10")+1);
                                }
                                else if(curr >= sub.get(4).getTotalruns()
                                        && curr < sub.get(5).getTotalruns()){
                                    A_bt.put("5/10 - 6/10", A_bt.get("5/10 - 6/10")+1);
                                }
                                else if(curr >= sub.get(5).getTotalruns()
                                        && curr < sub.get(6).getTotalruns()){
                                    A_bt.put("6/10 - 7/10", A_bt.get("6/10 - 7/10")+1);
                                }
                                else if(curr >= sub.get(6).getTotalruns()
                                        && curr < sub.get(7).getTotalruns()){
                                    A_bt.put("7/10 - 8/10", A_bt.get("7/10 - 8/10")+1);
                                }
                                else if(curr >= sub.get(7).getTotalruns()
                                        && curr < sub.get(8).getTotalruns()){
                                    A_bt.put("8/10 - 9/10", A_bt.get("8/10 - 9/10")+1);
                                }
                                else if(curr >= sub.get(8).getTotalruns()
                                        && curr < sub.get(9).getTotalruns()){
                                    A_bt.put("9/10 - 10/10", A_bt.get("9/10 - 10/10")+1);
                                }
                                else{
                                    A_bt.put("10/10 <", A_bt.get("10/10 <")+1);
                                }

                                if(curr >= sub.get(2).getTotalruns()
                                        && curr >= subA.get(1).getTotalruns()
                                        && curr >= subB.get(1).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr >= subG.get(1).getTotalruns()){
                                            A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                    }
                                }

                                if(curr <= sub.get(6).getTotalruns()
                                        && curr <= subA.get(3).getTotalruns()
                                        && curr <= subB.get(3).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr <= subG.get(3).getTotalruns()){
                                            A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                    }
                                }

                                if(curr >= sub.get(3).getTotalruns()
                                        && curr >= subA.get(2).getTotalruns()
                                        && curr >= subB.get(2).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr >= subG.get(2).getTotalruns()){
                                            A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                    }
                                }

                                if(curr <= sub.get(5).getTotalruns()
                                        && curr <= subA.get(2).getTotalruns()
                                        && curr <= subB.get(2).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr <= subG.get(2).getTotalruns()){
                                            A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                    }
                                }

                                if(curr >= sub.get(1).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr >= subG.get(1).getTotalruns()){
                                            A_bt.put("2 above", A_bt.get("2 above")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("2 above", A_bt.get("2 above")+1 );
                                    }
                                }

                                if(curr >= sub.get(2).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr >= subG.get(1).getTotalruns()){
                                            A_bt.put("3 above", A_bt.get("3 above")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("3 above", A_bt.get("3 above")+1 );
                                    }
                                }

                                if(curr >= sub.get(3).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr >= subG.get(1).getTotalruns()){
                                            A_bt.put("4 above", A_bt.get("4 above")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("4 above", A_bt.get("4 above")+1 );
                                    }
                                }

                                if(curr <= sub.get(5).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr <= subG.get(3).getTotalruns()){
                                            A_bt.put("6 below", A_bt.get("6 below")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("6 below", A_bt.get("6 below")+1 );
                                    }
                                }

                                if(curr <= sub.get(6).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr <= subG.get(3).getTotalruns()){
                                            A_bt.put("7 below", A_bt.get("7 below")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("7 below", A_bt.get("7 below")+1 );
                                    }
                                }

                                if(curr <= sub.get(7).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr <= subG.get(3).getTotalruns()){
                                            A_bt.put("8 below", A_bt.get("8 below")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("8 below", A_bt.get("8 below")+1 );
                                    }
                                }
                            }
                        }

                        request.setAttribute("first0A_bt", A_bt);
                    }
                
                
                
                if(true){
                    
                    Map<String,Integer> B_bt = new LinkedHashMap<>();
                    B_bt.put("N", 0);
                    B_bt.put("< 1/10", 0);
                    B_bt.put("1/10 - 2/10", 0);
                    B_bt.put("2/10 - 3/10", 0);
                    B_bt.put("3/10 - 4/10", 0);
                    B_bt.put("4/10 - 5/10", 0);
                    B_bt.put("5/10 - 6/10", 0);
                    B_bt.put("6/10 - 7/10", 0);
                    B_bt.put("7/10 - 8/10", 0);
                    B_bt.put("8/10 - 9/10", 0);
                    B_bt.put("9/10 - 10/10", 0);
                    B_bt.put("10/10 <", 0);
                    B_bt.put("2-2-2-3 above", 0);
                    B_bt.put("4-4-4-7 below", 0);
                    B_bt.put("3-3-3-4 above", 0);
                    B_bt.put("3-3-3-6 below", 0);
                    B_bt.put("2 above", 0);
                    B_bt.put("3 above", 0);
                    B_bt.put("4 above", 0);
                    B_bt.put("6 below", 0);
                    B_bt.put("7 below", 0);
                    B_bt.put("8 below", 0);

                    int hind = 1;

                    List<testMatch> oneMatch = db.gettesthome(teamOne, 1, 1);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    oneMatch.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                    oneMatch.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));

                    List<testMatch> twoMatch = db.gettestaway(teamTwo, 1, 2);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    twoMatch.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                    twoMatch.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));

                    List<testMatch> grMatch = db.gettestGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    grMatch.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                    grMatch.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));

                    if(oneMatch.size() > 5 && twoMatch.size() > 5){

                        for(int i = 0; i < twoMatch.size()-6; i++){

                            int curr = twoMatch.get(i).getInningOne1().getTotalruns();
                            Date currDate = twoMatch.get(i).getMatchDate();


                            List<testInning> sub = new ArrayList<>();
                            List<testInning> subA = new ArrayList<>();
                            List<testInning> subB = new ArrayList<>();
                            List<testInning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(twoMatch.get(j).getInningOne1());
                                subB.add(twoMatch.get(j).getInningOne1());
                            }

                            oneMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(oneMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(oneMatch.get(j).getInningOne1());
                                subA.add(oneMatch.get(j).getInningOne1());
                            }

                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningOne1());
                                }
                            }

                            Comparator innComp = new Comparator<testInning>() {
                                    @Override
                                    public int compare(testInning o1, testInning o2) {
                                        return o1.getTotalruns()
                                                - o2.getTotalruns();
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            B_bt.put("N", B_bt.get("N")+1);

                            if(curr < sub.get(0).getTotalruns()){
                                B_bt.put("< 1/10", B_bt.get("< 1/10")+1);
                            }
                            else if(curr >= sub.get(0).getTotalruns()
                                    && curr < sub.get(1).getTotalruns()){
                                B_bt.put("1/10 - 2/10", B_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= sub.get(1).getTotalruns()
                                    && curr < sub.get(2).getTotalruns()){
                                B_bt.put("2/10 - 3/10", B_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= sub.get(2).getTotalruns()
                                    && curr < sub.get(3).getTotalruns()){
                                B_bt.put("3/10 - 4/10", B_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= sub.get(3).getTotalruns()
                                    && curr < sub.get(4).getTotalruns()){
                                B_bt.put("4/10 - 5/10", B_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= sub.get(4).getTotalruns()
                                    && curr < sub.get(5).getTotalruns()){
                                B_bt.put("5/10 - 6/10", B_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= sub.get(5).getTotalruns()
                                    && curr < sub.get(6).getTotalruns()){
                                B_bt.put("6/10 - 7/10", B_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= sub.get(6).getTotalruns()
                                    && curr < sub.get(7).getTotalruns()){
                                B_bt.put("7/10 - 8/10", B_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= sub.get(7).getTotalruns()
                                    && curr < sub.get(8).getTotalruns()){
                                B_bt.put("8/10 - 9/10", B_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= sub.get(8).getTotalruns()
                                    && curr < sub.get(9).getTotalruns()){
                                B_bt.put("9/10 - 10/10", B_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                B_bt.put("10/10 <", B_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= sub.get(2).getTotalruns()
                                    && curr >= subA.get(1).getTotalruns()
                                    && curr >= subB.get(1).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr >= subG.get(1).getTotalruns()){
                                        B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= sub.get(6).getTotalruns()
                                    && curr <= subA.get(3).getTotalruns()
                                    && curr <= subB.get(3).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr <= subG.get(3).getTotalruns()){
                                        B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= sub.get(3).getTotalruns()
                                    && curr >= subA.get(2).getTotalruns()
                                    && curr >= subB.get(2).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr >= subG.get(2).getTotalruns()){
                                        B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= sub.get(5).getTotalruns()
                                    && curr <= subA.get(2).getTotalruns()
                                    && curr <= subB.get(2).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr <= subG.get(2).getTotalruns()){
                                        B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= sub.get(1).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr >= subG.get(1).getTotalruns()){
                                        B_bt.put("2 above", B_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2 above", B_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= sub.get(2).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr >= subG.get(1).getTotalruns()){
                                        B_bt.put("3 above", B_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3 above", B_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= sub.get(3).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr >= subG.get(1).getTotalruns()){
                                        B_bt.put("4 above", B_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4 above", B_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= sub.get(5).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr <= subG.get(3).getTotalruns()){
                                        B_bt.put("6 below", B_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("6 below", B_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= sub.get(6).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr <= subG.get(3).getTotalruns()){
                                        B_bt.put("7 below", B_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("7 below", B_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= sub.get(7).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr <= subG.get(3).getTotalruns()){
                                        B_bt.put("8 below", B_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("8 below", B_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("first0B_bt", B_bt);
                }
                    
                    
                    /*
  //          matches.clear();
//Team two Bowls Second Innings LAST 5            
            matches = db.gettestaway(teamTwo,1,1);
            k = 5;
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                testMatch q = matches.get(i);
                String res =q.getResult();
                String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamTwo)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(teamTwo)){
                     worl = "Win";
                }
                else if(res.equals("Match drawn")){
                    worl = "Draw";
                }
                else{
                     worl = "Lose";
                }
                
                testInning m = q.getInningTwo1();
                m.setWinner(BorC + "/" + worl);
                
                t_teamtwoBowlSecond.add(m);
            }
  //          matches.clear();
//Team two Bowls Third Innings LAST 5            
            matches = db.gettestaway(teamTwo,1,2);
            k = 5;
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                testMatch q = matches.get(i);
                String res =q.getResult();
                String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamTwo)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(teamTwo)){
                     worl = "Win";
                }
                else if(res.equals("Match drawn")){
                    worl = "Draw";
                }
                else{
                     worl = "Lose";
                }
                
                testInning m = q.getInningOne2();
                m.setWinner(BorC + "/" + worl);
                
                t_teamtwoBowlThird.add(m);
            }
 //           matches.clear();
///Team two Bowls Fourth Innings LAST 5            
            matches = db.gettestaway(teamTwo,1,1);
            k = 5;
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                testMatch q = matches.get(i);
                String res =q.getResult();
                String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamTwo)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(teamTwo)){
                     worl = "Win";
                }
                else if(res.equals("Match drawn")){
                    worl = "Draw";
                }
                else{
                     worl = "Lose";
                }
                
                testInning m = q.getInningTwo2();
                m.setWinner(BorC + "/" + worl);
               
                t_teamtwoBowlFourth.add(m);
            }
  //          matches.clear();
                     */
                    List<String> headers = new ArrayList();
                    headers = db.getHeaders(matchType);

                    //System.out.println(t_oneBatFirst.size());
                    //out.println(t_oneBatSecond.size());
                    //out.println(t_twoBatFirst.size());
                    // out.println(t_twoBatSecond.size());
                    // out.println(t_oneBowlFirst.size());
                    // out.println(t_oneBowlSecond.size());
                    //out.println(t_twoBowlFirst.size());        
                    //  out.println(t_twoBowlSecond.size());
                    //   out.println(t_groundFirst1.size());
                    //    out.println(t_groundSecond1.size());
                    // out.println(t_groundFirst2.size());
                    request.setAttribute("hth", hth);
                    request.setAttribute("hthmatch", hthmatch);
                    request.setAttribute("onetotal", onetotal);
                    request.setAttribute("twototal", twototal);

                    request.setAttribute("headers", headers);
                    request.setAttribute("teamOne", teamOne);
                    request.setAttribute("teamTwo", teamTwo);
                    request.setAttribute("hometeam", hometeam);
                    request.setAttribute("t_oneBatFirst", t_oneBatFirst);
                    //request.setAttribute("t_oneBatFirstY", t_oneBatFirstY);
                    request.setAttribute("t_oneBatSecond", t_oneBatSecond);

                    request.setAttribute("t_twoBatFirst", t_twoBatFirst);

                    request.setAttribute("t_twoBatSecond", t_twoBatSecond);

                    request.setAttribute("t_oneBowlFirst", t_oneBowlFirst);
                    request.setAttribute("t_oneBowlSecond", t_oneBowlSecond);
                    request.setAttribute("t_twoBowlFirst", t_twoBowlFirst);
                    request.setAttribute("t_twoBowlSecond", t_twoBowlSecond);
//                        request.setAttribute("t_twoBowlFirstY", t_twoBowlFirstY);

                    request.setAttribute("t_groundName", groundName);
                    request.setAttribute("t_groundFirst1", t_groundFirst1);
                    request.setAttribute("t_groundSecond1", t_groundSecond1);
                    request.setAttribute("t_groundFirst2", t_groundFirst2);
                    request.setAttribute("t_groundSecond2", t_groundSecond2);

                    request.setAttribute("t_teamoneBatFirst", t_teamoneBatFirst1);
                    request.setAttribute("t_teamoneBatThird", t_teamoneBatThird1);
                    request.setAttribute("t_teamoneBowlSecond", t_teamoneBowlSecond1);
                    request.setAttribute("t_teamoneBowlFourth", t_teamoneBowlFourth1);
                    request.setAttribute("t_teamtwoBowlFirst", t_teamtwoBowlFirst1);
                    request.setAttribute("t_teamtwoBowlThird", t_teamtwoBowlThird1);
                    request.setAttribute("t_teamtwoBatSecond", t_teamtwoBatSecond1);
                    request.setAttribute("t_teamtwoBatFourth", t_teamtwoBatFourth1);

//            System.out.println(db.getHeaders(matchType));
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/testresults.jsp");
                    dispatcher.forward(request, response);

                } //if teamA is away           
                else {
                    //System.out.println("home was :" + teamTwo);
                    List<testInning> t_oneBatFirst = new ArrayList<>();
                    List<testInning> t_oneBatSecond = new ArrayList<>();
                    List<testInning> t_twoBatFirst = new ArrayList<>();
                    List<testInning> t_twoBatSecond = new ArrayList<>();

                    List<testInning> t_oneBowlFirst = new ArrayList<>();
                    List<testInning> t_oneBowlSecond = new ArrayList<>();
                    List<testInning> t_twoBowlFirst = new ArrayList<>();
                    List<testInning> t_twoBowlSecond = new ArrayList<>();

                    List<testInning> t_groundFirst1 = new ArrayList<>();
                    List<testInning> t_groundSecond1 = new ArrayList<>();
                    List<testInning> t_groundFirst2 = new ArrayList<>();
                    List<testInning> t_groundSecond2 = new ArrayList<>();

                    testInning temp;
                    List<testMatch> matches;
                    matches = db.gettestMatches(teamOne, matchType, 1);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    // out.println(matches);
                    int k = 5;
                    for (int i = 0; i < Math.min(k, matches.size()); i++) {
                        temp = matches.get(i).getInningOne1();
                        /*out.println(temp.getInningId());
                out.println(temp.getFirstwicket());
                out.println(temp.getFours());
                out.println(temp.getRuns5wicket());
                out.println(temp.getSixes() );
                out.println(temp.getTotalruns());
                //out.println(temp);   
                         */

                        int fours = matches.get(i).getInningOne1().getFours()
                                + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours()
                                + matches.get(i).getInningTwo2().getFours();
                        //out.println(fours);
                        int sixes = matches.get(i).getInningOne1().getSixes()
                                + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                                + matches.get(i).getInningTwo2().getSixes();
                        //out.println(sixes);
                        temp.setFours(fours);
                        temp.setSixes(sixes);
                        t_oneBatFirst.add(temp);
                        if (temp.getRuns5wicket() == -1) {
                            k++;
                        }
                    }

                    matches.clear();
                    k = 5;
                    matches = db.gettestMatches(teamTwo, matchType, 2);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(k, matches.size()); i++) {
                        temp = matches.get(i).getInningTwo1();
                        int fours = matches.get(i).getInningOne1().getFours()
                                + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours()
                                + matches.get(i).getInningTwo2().getFours();
                        int sixes = matches.get(i).getInningOne1().getSixes()
                                + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                                + matches.get(i).getInningTwo2().getSixes();
                        temp.setFours(fours);
                        temp.setSixes(sixes);
                        t_twoBatSecond.add(temp);
                        if (temp.getRuns5wicket() == -1) {
                            k++;
                        }
                    }

                    matches.clear();
                    k = 5;
                    matches = db.gettestMatches(teamOne, matchType, 1);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(k, matches.size()); i++) {
                        temp = matches.get(i).getInningTwo1();
                        int fours = matches.get(i).getInningOne1().getFours()
                                + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours()
                                + matches.get(i).getInningTwo2().getFours();
                        int sixes = matches.get(i).getInningOne1().getSixes()
                                + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                                + matches.get(i).getInningTwo2().getSixes();
                        temp.setFours(fours);
                        temp.setSixes(sixes);
                        t_oneBowlSecond.add(temp);
                        if (temp.getRuns5wicket() == -1) {
                            k++;
                        }
                    }

                    matches.clear();
                    k = 5;
                    matches = db.gettestMatches(teamTwo, matchType, 2);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(k, matches.size()); i++) {
                        temp = matches.get(i).getInningOne1();
                        int fours = matches.get(i).getInningOne1().getFours()
                                + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours()
                                + matches.get(i).getInningTwo2().getFours();
                        int sixes = matches.get(i).getInningOne1().getSixes()
                                + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                                + matches.get(i).getInningTwo2().getSixes();
                        temp.setFours(fours);
                        temp.setSixes(sixes);
                        t_twoBowlFirst.add(temp);
                        if (temp.getRuns5wicket() == -1) {
                            k++;
                        }
                    }

                    List<testInning> one1 = new ArrayList<>();
                    List<testInning> two1 = new ArrayList<>();
                    List<testInning> one2 = new ArrayList<>();
                    List<testInning> two2 = new ArrayList<>();

                    matches.clear();

                    testInning temp11;
                    testInning temp12;
                    testInning temp21;
                    testInning temp22;

                    matches = db.gettestMatches(teamOne, matchType, 0);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(5, matches.size()); i++) {
                        if (matches.get(i).getBattingFirst() == teamOne) {
                            temp11 = matches.get(i).getInningOne1();
                            temp12 = matches.get(i).getInningOne2();
                        } else {
                            temp11 = matches.get(i).getInningTwo1();
                            temp12 = matches.get(i).getInningTwo2();

                        }
                        int fours = matches.get(i).getInningOne1().getFours()
                                + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours()
                                + matches.get(i).getInningTwo2().getFours();
                        int sixes = matches.get(i).getInningOne1().getSixes()
                                + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                                + matches.get(i).getInningTwo2().getSixes();
                        temp11.setFours(fours);
                        temp12.setFours(fours);
                        temp11.setSixes(sixes);
                        temp12.setSixes(sixes);

                        one1.add(temp11);
                        one2.add(temp12);
                    }
                    request.setAttribute("one1", one1);
                    request.setAttribute("one2", one2);

                    matches.clear();

                    matches = db.gettestMatches(teamTwo, matchType, 0);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(5, matches.size()); i++) {
                        if (matches.get(i).getBattingFirst() == teamTwo) {
                            temp21 = matches.get(i).getInningOne1();
                            temp22 = matches.get(i).getInningOne2();
                        } else {
                            temp21 = matches.get(i).getInningTwo1();
                            temp22 = matches.get(i).getInningTwo2();

                        }
                        int fours = matches.get(i).getInningOne1().getFours()
                                + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours()
                                + matches.get(i).getInningTwo2().getFours();
                        int sixes = matches.get(i).getInningOne1().getSixes()
                                + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                                + matches.get(i).getInningTwo2().getSixes();
                        temp21.setFours(fours);
                        temp22.setFours(fours);
                        temp21.setSixes(sixes);
                        temp22.setSixes(sixes);

                        two1.add(temp21);
                        two2.add(temp22);
                    }
                    request.setAttribute("two1", two1);
                    request.setAttribute("two2", two2);

                    matches.clear();

                    k = 5;
                    matches = db.gettestGroundInfo(groundName, matchType);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    k = 5;
                    for (int i = 0; i < Math.min(k, matches.size()); i++) {
                        testMatch q = matches.get(i);

                        t_groundFirst1.add(q.getInningOne1());
                        t_groundFirst2.add(q.getInningOne2());
                        t_groundSecond1.add(q.getInningTwo1());
                        t_groundSecond2.add(q.getInningTwo2());
                    }
                    matches.clear();
                    /*           
            matches = db.gettestGroundInfo(groundName, matchType);
            
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                temp11 = matches.get(i).getInningOne1();
                temp12 = matches.get(i).getInningOne2();
                temp21 = matches.get(i).getInningTwo1();
                temp22 = matches.get(i).getInningTwo2();
                
                int fours = matches.get(i).getInningOne1().getFours()
                        + matches.get(i).getInningTwo1().getFours() + matches.get(i).getInningOne2().getFours() 
                        + matches.get(i).getInningTwo2().getFours();
                int sixes = matches.get(i).getInningOne1().getSixes()
                        + matches.get(i).getInningTwo1().getSixes() + matches.get(i).getInningOne2().getSixes()
                        + matches.get(i).getInningTwo2().getSixes(); 
                
                temp21.setFours(fours);
                temp22.setFours(fours);
                temp21.setSixes(sixes);
                temp22.setSixes(sixes);
                
                t_groundFirst1.add(temp11);
                t_groundFirst2.add(temp12);
                t_groundSecond1.add(temp21);
                t_groundSecond2.add(temp22);               

 //               if (temp.getParams().get(2).equals("-1") || temp2.getParams().get(2).equals("-1")) {
 //                   k++;
 //               }

            }
                     */
                    List<testInning> t_oneBatFirstX = new ArrayList<>();
                    List<testInning> t_twoBowlFirstX = new ArrayList<>();
                    List<testInning> t_groundFirst1X = new ArrayList<>();
                    List<testInning> t_groundSecond1X = new ArrayList<>();
                    List<testInning> t_groundFirst2X = new ArrayList<>();
                    List<testInning> t_groundSecond2X = new ArrayList<>();

                    for (testInning q : t_oneBatFirst) {
                        if (q.getRuns5wicket() != -1) {
                            t_oneBatFirstX.add(q);
                        }
                    }

                    for (testInning q : t_twoBowlFirst) {
                        if (q.getRuns5wicket() != -1) {
                            t_twoBowlFirstX.add(q);
                        }
                    }

                    for (testInning q : t_groundFirst1) {
                        if (q.getRuns5wicket() != -1) {
                            t_groundFirst1X.add(q);
                        }
                    }
                    for (testInning q : t_groundSecond1) {
                        if (q.getRuns5wicket() != -1) {
                            t_groundSecond1X.add(q);
                        }
                    }
                    for (testInning q : t_groundFirst2) {
                        if (q.getRuns5wicket() != -1) {
                            t_groundFirst2X.add(q);
                        }
                    }

                    for (testInning q : t_groundSecond2) {
                        if (q.getRuns5wicket() != -1) {
                            t_groundSecond2X.add(q);
                        }
                    }

                    t_oneBatFirst = t_oneBatFirst.subList(0, Math.min(5, t_oneBatFirst.size()));
                    t_twoBowlFirst = t_twoBowlFirst.subList(0, Math.min(5, t_twoBowlFirst.size()));
                    /*            t_groundFirst1 = t_groundFirst1.subList(0, Math.min(5, t_groundFirst1.size()));
            t_groundSecond1 = t_groundSecond1.subList(0, Math.min(5, t_groundSecond1.size()));
            t_groundFirst2 = t_groundFirst2.subList(0, Math.min(5, t_groundFirst2.size()));
            t_groundSecond2 = t_groundSecond2.subList(0, Math.min(5, t_groundSecond2.size()));
                     */
                    request.setAttribute("t_oneBatFirstX", t_oneBatFirstX);
                    request.setAttribute("t_twoBowlFirstX", t_twoBowlFirstX);
                    request.setAttribute("t_groundFirst1X", t_groundFirst1X.subList(0, Math.min(5, t_groundFirst1X.size())));
                    request.setAttribute("t_groundFirst1X", t_groundFirst1);
                    request.setAttribute("t_groundSecond1X", t_groundSecond1);
                    request.setAttribute("t_groundFirst2X", t_groundFirst2);
                    request.setAttribute("t_groundSecond2X", t_groundSecond2);
                    //request.setAttribute("t_groundSecond1X", t_groundSecond1X.subList(0, Math.min(5, t_groundSecond1X.size())));
                    // request.setAttribute("t_groundFirst2X", t_groundFirst2X.subList(0, Math.min(5, t_groundFirst2X.size())));
                    //  request.setAttribute("t_groundSecond2X", t_groundSecond2X.subList(0, Math.min(5, t_groundSecond2X.size())));

                    List<testInning> t_twoBatSecondX = new ArrayList<>();
                    List<testInning> t_oneBowlSecondX = new ArrayList<>();

                    for (testInning q : t_twoBatSecond) {
                        if (q.getRuns5wicket() != -1) {
                            t_twoBatSecondX.add(q);
                        }
                    }

                    for (testInning q : t_oneBowlSecond) {
                        if (q.getRuns5wicket() != -1) {
                            t_oneBowlSecondX.add(q);
                        }
                    }

                    t_twoBatSecond = t_twoBatSecond.subList(0, Math.min(5, t_twoBatSecond.size()));
                    t_oneBowlSecond = t_oneBowlSecond.subList(0, Math.min(5, t_oneBowlSecond.size()));

                    request.setAttribute("t_twoBatSecondX", t_twoBatSecondX);
                    request.setAttribute("t_oneBowlSecondX", t_oneBowlSecondX);

//            for(int i = 0; i < matches.size(); i++){
//                out.print("<h1>"+matches.get(i));
//            }
                    List<testInning> t_oneBatFirstY = new ArrayList<>();
                    List<testInning> t_twoBowlFirstY = new ArrayList<>();
                    List<testInning> onetotal = new ArrayList<>();
                    List<testInning> twototal = new ArrayList<>();

                    matches.clear();
                    matches = db.gettestaway(teamOne, 1, 0);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (testMatch q : matches) {
                        int fours;
                        int sixes;
                        fours = q.getInningOne1().getFours()
                                + q.getInningOne2().getFours() + q.getInningTwo1().getFours() + q.getInningTwo2().getFours();

                        sixes = q.getInningOne1().getSixes()
                                + q.getInningOne2().getSixes() + q.getInningTwo1().getSixes() + q.getInningTwo2().getSixes();
                        testInning m = q.getInningOne1();
                        m.setFours(fours);
                        m.setSixes(sixes);
                        onetotal.add(m);

                    }
                    
                    matches.clear();
                    matches = db.gettesthome(teamTwo, 1, 0);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (testMatch q : matches) {
                        int fours;
                        int sixes;
                        fours = q.getInningOne1().getFours()
                                + q.getInningOne2().getFours() + q.getInningTwo1().getFours() + q.getInningTwo2().getFours();

                        sixes = q.getInningOne1().getSixes()
                                + q.getInningOne2().getSixes() + q.getInningTwo1().getSixes() + q.getInningTwo2().getSixes();
                        testInning m = q.getInningOne1();
                        m.setFours(fours);
                        m.setSixes(sixes);
                        twototal.add(m);

                    }

                    matches.clear();
                    matches = db.gettestaway(teamOne, 1, 0);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (testMatch q : matches) {
                        String res = q.getResult();
                        String borc;
                        String worl;
                        if (res.contains(teamOne)) {
                            worl = "Win";
                        } else if (res.equals("Match drawn")) {
                            worl = "Draw";
                        } else {
                            worl = "Lose";
                        }

                        if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                            borc = "B";
                        } else {
                            borc = "C";
                        }
                        /*    String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamOne)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(" wicket")){
                    if(q.getHomeTeam().equalsIgnoreCase(teamOne)){
                        worl = "L";
                    }
                    else{
                        worl = "W";
                    }
                }
                else if(res.contains(" run")){
                    if(q.getHomeTeam().equalsIgnoreCase(teamOne)){
                        worl = "W";
                    }
                    else{
                        worl = "L";
                    }
                }else{
                    worl = "-";
                }
                         */
                        testInning m = q.getInningOne1();

                        m.setWinner(borc + "/" + worl);
                        t_oneBatFirstY.add(m);
                    }

                    matches.clear();
                    matches = db.gettesthome(teamTwo, 1, 0);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (testMatch q : matches) {
                        String res = q.getResult();
                        String borc;
                        String worl;

                        if (res.contains(teamTwo)) {
                            worl = "Win";
                        } else if (res.equals("Match drawn")) {
                            worl = "Draw";
                        } else {
                            worl = "Lose";
                        }

                        if (q.getHomeTeam().equalsIgnoreCase(teamTwo)) {
                            borc = "B";
                        } else {
                            borc = "C";
                        }
                        /*
                String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamOne)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(" wicket")){
                    if(q.getHomeTeam().equalsIgnoreCase(teamTwo)){
                        worl = "L";
                    }
                    else{
                        worl = "W";
                    }
                }
                else if(res.contains(" run")){
                    if(q.getHomeTeam().equalsIgnoreCase(teamTwo)){
                        worl = "W";
                    }
                    else{
                        worl = "L";
                    }
                }else{
                    worl = "-";
                }
                         */
                        testInning m = q.getInningOne1();

                        m.setWinner(borc + "/" + worl);
                        t_twoBowlFirstY.add(m);
                    }
                    List<testInning> t_oneBatFirst1 = new ArrayList<>();
                    List<testInning> t_twoBowlFirst1 = new ArrayList<>();
                    List<testInning> t_oneBatFirst2 = new ArrayList<>();
                    List<testInning> t_twoBowlFirst2 = new ArrayList<>();

                    for (testInning q : t_oneBatFirstY) {
                        if (q.getRuns5wicket() != -1) {
                            t_oneBatFirst1.add(q);
                        }
                    }

                    for (testInning q : t_twoBowlFirstY) {
                        if (q.getRuns5wicket() != -1) {
                            t_twoBowlFirst1.add(q);
                        }
                    }

                    t_oneBatFirst2 = t_oneBatFirst1.subList(0, Math.min(5, t_oneBatFirst1.size()));
                    t_twoBowlFirst2 = t_twoBowlFirst1.subList(0, Math.min(5, t_twoBowlFirst1.size()));
                    onetotal = onetotal.subList(0, Math.min(5, onetotal.size()));
                    twototal = twototal.subList(0, Math.min(5, twototal.size()));
                    request.setAttribute("t_oneBatFirstY", t_oneBatFirst2);
                    request.setAttribute("t_twoBowlFirstY", t_twoBowlFirst2);

                    List<testInning> hth = new ArrayList<>();
                    List<testMatch> hthmatch = new ArrayList<>();
                    matches = db.gettestHth(matchType, teamOne, teamTwo);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    for (int i = 0; i < Math.min(5, matches.size()); i++) {
                        testMatch q = matches.get(i);

                        String res = q.getResult();
                        String worl = "";

                        String BorC = "";
                        if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                            BorC = "B";
                        } else {
                            BorC = "C";
                        }

                        if (res.contains(" wicket")) {
                            if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                                worl = "L";
                            } else {
                                worl = "W";
                            }
                        } else if (res.contains(" run")) {
                            if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                                worl = "W";
                            } else {
                                worl = "L";
                            }
                        } else {
                            worl = "-";
                        }

                        testInning m = q.getInningOne1();
                        String bcwl = BorC + "/" + worl;
                        m.setWinner(bcwl);
                        hth.add(m);
                        hthmatch.add(q);

                    }

                    List<testInning> t_teamoneBatFirst = new ArrayList<>();
                    List<testInning> t_teamoneBatThird = new ArrayList<>();
                    List<testInning> t_teamoneBowlSecond = new ArrayList<>();
                    List<testInning> t_teamoneBowlFourth = new ArrayList<>();

                    List<testInning> t_teamtwoBowlFirst = new ArrayList<>();
                    List<testInning> t_teamtwoBowlThird = new ArrayList<>();
                    List<testInning> t_teamtwoBatSecond = new ArrayList<>();
                    List<testInning> t_teamtwoBatFourth = new ArrayList<>();

                    List<testInning> t_teamoneBatFirst1 = new ArrayList<>();
                    List<testInning> t_teamoneBatThird1 = new ArrayList<>();
                    List<testInning> t_teamoneBowlSecond1 = new ArrayList<>();
                    List<testInning> t_teamoneBowlFourth1 = new ArrayList<>();

                    List<testInning> t_teamtwoBowlFirst1 = new ArrayList<>();
                    List<testInning> t_teamtwoBowlThird1 = new ArrayList<>();
                    List<testInning> t_teamtwoBatSecond1 = new ArrayList<>();
                    List<testInning> t_teamtwoBatFourth1 = new ArrayList<>();

                    matches = db.gettestaway(teamOne, 1, 1);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    k = 5;
                    for (int i = 0; i < Math.min(k, matches.size()); i++) {
                        testMatch q = matches.get(i);
                        String res = q.getResult();
                        String worl = "";

                        String BorC = "";
                        if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                            BorC = "B";
                        } else {
                            BorC = "C";
                        }
                        if (res.contains(teamOne)) {
                            worl = "Win";
                        } else if (res.equals("Match drawn")) {
                            worl = "Draw";
                        } else {
                            worl = "Lose";
                        }

                        testInning m = q.getInningOne1();
                        m.setWinner(BorC + "/" + worl);

                        t_teamoneBatFirst.add(m);

                        testInning b = q.getInningOne2();
                        b.setWinner(BorC + "/" + worl);
                        t_teamoneBatThird.add(b);

                        testInning c = q.getInningTwo1();
                        c.setWinner(BorC + "/" + worl);
                        t_teamoneBowlSecond.add(c);

                        testInning d = q.getInningTwo2();
                        d.setWinner(BorC + "/" + worl);
                        t_teamoneBowlFourth.add(d);
                    }
                    //           matches.clear();

                    //           matches.clear();
//Team One Bats Fourth Innings LAST 5            
                    //          matches.clear();
//Team two Bowls First Innings LAST 5            
                    matches = db.gettesthome(teamTwo, 1, 2);
                    matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    k = 5;
                    for (int i = 0; i < Math.min(k, matches.size()); i++) {
                        testMatch q = matches.get(i);
                        String res = q.getResult();
                        String worl = "";

                        String BorC = "";
                        if (q.getHomeTeam().equalsIgnoreCase(teamTwo)) {
                            BorC = "B";
                        } else {
                            BorC = "C";
                        }

                        if (res.contains(teamTwo)) {
                            worl = "Win";
                        } else if (res.equals("Match drawn")) {
                            worl = "Draw";
                        } else {
                            worl = "Lose";
                        }

                        testInning m = q.getInningOne1();
                        m.setWinner(BorC + "/" + worl);

                        t_teamtwoBowlFirst.add(m);

                        testInning b = q.getInningOne2();
                        b.setWinner(BorC + "/" + worl);
                        t_teamtwoBowlThird.add(b);

                        testInning c = q.getInningTwo1();
                        c.setWinner(BorC + "/" + worl);
                        t_teamtwoBatSecond.add(c);

                        testInning d = q.getInningTwo2();
                        d.setWinner(BorC + "/" + worl);
                        t_teamtwoBatFourth.add(d);
                    }

                    for (testInning q : t_teamoneBatFirst) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamoneBatFirst1.add(q);
                        }
                    }
                    for (testInning q : t_teamoneBatThird) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamoneBatThird1.add(q);
                        }
                    }
                    for (testInning q : t_teamoneBowlSecond) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamoneBowlSecond1.add(q);
                        }
                    }
                    for (testInning q : t_teamoneBowlFourth) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamoneBowlFourth1.add(q);
                        }
                    }

                    for (testInning q : t_teamtwoBatSecond) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamtwoBatSecond1.add(q);
                        }
                    }
                    for (testInning q : t_teamtwoBatFourth) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamtwoBatFourth1.add(q);
                        }
                    }
                    for (testInning q : t_teamtwoBowlFirst) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamtwoBowlFirst1.add(q);
                        }
                    }
                    for (testInning q : t_teamtwoBowlThird) {
                        if (q.getRuns5wicket() != -1 && q.getTotalruns()!=0) {
                            t_teamtwoBowlThird1.add(q);
                        }
                    }

                    t_teamoneBatFirst1 = t_teamoneBatFirst1.subList(0, Math.min(5, t_teamoneBatFirst1.size()));
                    t_teamoneBatThird1 = t_teamoneBatThird1.subList(0, Math.min(5, t_teamoneBatThird1.size()));
                    t_teamoneBowlSecond1 = t_teamoneBowlSecond1.subList(0, Math.min(5, t_teamoneBowlSecond1.size()));
                    t_teamoneBowlFourth1 = t_teamoneBowlFourth1.subList(0, Math.min(5, t_teamoneBowlFourth1.size()));

                    t_teamtwoBowlFirst1 = t_teamtwoBowlFirst1.subList(0, Math.min(5, t_teamtwoBowlFirst1.size()));
                    t_teamtwoBowlThird1 = t_teamtwoBowlThird1.subList(0, Math.min(5, t_teamtwoBowlThird1.size()));
                    t_teamtwoBatSecond1 = t_teamtwoBatSecond1.subList(0, Math.min(5, t_teamtwoBatSecond1.size()));
                    t_teamtwoBatFourth1 = t_teamtwoBatFourth1.subList(0, Math.min(5, t_teamtwoBatFourth1.size()));
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    if(true){
                        List<testMatch> totMatches = new ArrayList<>();
                        totMatches = db.gettestaway(teamOne, 1, 1);
                        totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                        totMatches.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                        totMatches.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));
                        
                        List<testInning> tZ = new ArrayList<>();
                        for(int i = 0; i < totMatches.size(); i++){
                            tZ.add(totMatches.get(i).getInningOne1());
                        }
                        
                        Map<String,Integer> bt = new LinkedHashMap<>();
                        bt.put("< 1/5", 0);
                        bt.put("1/5 - 2/5", 0);
                        bt.put("2/5 - 3/5", 0);
                        bt.put("3/5 - 4/5", 0);
                        bt.put("4/5 - 5/5", 0);
                        bt.put("5/5 <", 0);

                        if(tZ.size() > 5){
                            for(int i = 0; i < tZ.size()-6; i++){
                                int curr = tZ.get(i).getTotalruns();

                                List<testInning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                                Collections.sort(sub, new Comparator<testInning>() {
                                    @Override
                                    public int compare(testInning o1, testInning o2) {
                                        return o1.getTotalruns()
                                                - o2.getTotalruns();
                                    }
                                });

                                if(curr < sub.get(0).getTotalruns()){
                                    bt.put("< 1/5", bt.get("< 1/5")+1 );
                                }
                                else if(curr >= sub.get(0).getTotalruns()
                                        && curr < sub.get(1).getTotalruns() ){
                                    bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                                }
                                else if(curr >= sub.get(1).getTotalruns()
                                        && curr < sub.get(2).getTotalruns() ){
                                    bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                                }
                                else if(curr >= sub.get(2).getTotalruns() 
                                        && curr < sub.get(3).getTotalruns() ){
                                    bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                                }
                                else if(curr >= sub.get(3).getTotalruns() 
                                        && curr < sub.get(4).getTotalruns() ){
                                    bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                                }
                                else{
                                    bt.put("5/5 <", bt.get("5/5 <")+1 );
                                }
                            } 
                        }

                        request.setAttribute("t_teamoneBatFirst0_bt", bt);
                    }
                    
                    
                    if(true){
                        List<testMatch> totMatches = new ArrayList<>();
                        totMatches = db.gettesthome(teamTwo, 1, 2);
                        totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                        totMatches.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                        totMatches.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));
                        
                        List<testInning> tZ = new ArrayList<>();
                        for(int i = 0; i < totMatches.size(); i++){
                            tZ.add(totMatches.get(i).getInningOne1());
                        }
                        
                        Map<String,Integer> bt = new LinkedHashMap<>();
                        bt.put("< 1/5", 0);
                        bt.put("1/5 - 2/5", 0);
                        bt.put("2/5 - 3/5", 0);
                        bt.put("3/5 - 4/5", 0);
                        bt.put("4/5 - 5/5", 0);
                        bt.put("5/5 <", 0);

                        if(tZ.size() > 5){
                            for(int i = 0; i < tZ.size()-6; i++){
                                int curr = tZ.get(i).getTotalruns();

                                List<testInning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                                Collections.sort(sub, new Comparator<testInning>() {
                                    @Override
                                    public int compare(testInning o1, testInning o2) {
                                        return o1.getTotalruns()
                                                - o2.getTotalruns();
                                    }
                                });

                                if(curr < sub.get(0).getTotalruns()){
                                    bt.put("< 1/5", bt.get("< 1/5")+1 );
                                }
                                else if(curr >= sub.get(0).getTotalruns()
                                        && curr < sub.get(1).getTotalruns() ){
                                    bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                                }
                                else if(curr >= sub.get(1).getTotalruns()
                                        && curr < sub.get(2).getTotalruns() ){
                                    bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                                }
                                else if(curr >= sub.get(2).getTotalruns() 
                                        && curr < sub.get(3).getTotalruns() ){
                                    bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                                }
                                else if(curr >= sub.get(3).getTotalruns() 
                                        && curr < sub.get(4).getTotalruns() ){
                                    bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                                }
                                else{
                                    bt.put("5/5 <", bt.get("5/5 <")+1 );
                                }
                            } 
                        }

                        request.setAttribute("t_teamtwoBowlFirst0_bt", bt);
                    }
                    
                    if(true){
                        List<testMatch> totMatches = new ArrayList<>();
                        totMatches = db.gettestGroundInfo(groundName, matchType);
                        totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                        totMatches.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                        totMatches.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));
                        
                        List<testInning> tZ = new ArrayList<>();
                        for(int i = 0; i < totMatches.size(); i++){
                            tZ.add(totMatches.get(i).getInningOne1());
                        }
                        
                        Map<String,Integer> bt = new LinkedHashMap<>();
                        bt.put("< 1/5", 0);
                        bt.put("1/5 - 2/5", 0);
                        bt.put("2/5 - 3/5", 0);
                        bt.put("3/5 - 4/5", 0);
                        bt.put("4/5 - 5/5", 0);
                        bt.put("5/5 <", 0);

                        if(tZ.size() > 5){
                            for(int i = 0; i < tZ.size()-6; i++){
                                int curr = tZ.get(i).getTotalruns();

                                List<testInning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                                Collections.sort(sub, new Comparator<testInning>() {
                                    @Override
                                    public int compare(testInning o1, testInning o2) {
                                        return o1.getTotalruns()
                                                - o2.getTotalruns();
                                    }
                                });

                                if(curr < sub.get(0).getTotalruns()){
                                    bt.put("< 1/5", bt.get("< 1/5")+1 );
                                }
                                else if(curr >= sub.get(0).getTotalruns()
                                        && curr < sub.get(1).getTotalruns() ){
                                    bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                                }
                                else if(curr >= sub.get(1).getTotalruns()
                                        && curr < sub.get(2).getTotalruns() ){
                                    bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                                }
                                else if(curr >= sub.get(2).getTotalruns() 
                                        && curr < sub.get(3).getTotalruns() ){
                                    bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                                }
                                else if(curr >= sub.get(3).getTotalruns() 
                                        && curr < sub.get(4).getTotalruns() ){
                                    bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                                }
                                else{
                                    bt.put("5/5 <", bt.get("5/5 <")+1 );
                                }
                            } 
                        }

                        request.setAttribute("t_groundFirst1X0_bt", bt);
                    }
                    
                    if(true){
                        Map<String,Integer> A_bt = new LinkedHashMap<>();
                        A_bt.put("N", 0);
                        A_bt.put("< 1/10", 0);
                        A_bt.put("1/10 - 2/10", 0);
                        A_bt.put("2/10 - 3/10", 0);
                        A_bt.put("3/10 - 4/10", 0);
                        A_bt.put("4/10 - 5/10", 0);
                        A_bt.put("5/10 - 6/10", 0);
                        A_bt.put("6/10 - 7/10", 0);
                        A_bt.put("7/10 - 8/10", 0);
                        A_bt.put("8/10 - 9/10", 0);
                        A_bt.put("9/10 - 10/10", 0);
                        A_bt.put("10/10 <", 0);
                        A_bt.put("2-2-2-3 above", 0);
                        A_bt.put("4-4-4-7 below", 0);
                        A_bt.put("3-3-3-4 above", 0);
                        A_bt.put("3-3-3-6 below", 0);
                        A_bt.put("2 above", 0);
                        A_bt.put("3 above", 0);
                        A_bt.put("4 above", 0);
                        A_bt.put("6 below", 0);
                        A_bt.put("7 below", 0);
                        A_bt.put("8 below", 0);

                        int hind = 1;

                        List<testMatch> oneMatch = db.gettestaway(teamOne, 1, 1);
                        oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                        oneMatch.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                        oneMatch.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));

                        List<testMatch> twoMatch = db.gettesthome(teamTwo, 1, 2);
                        twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                        twoMatch.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                        twoMatch.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));

                        List<testMatch> grMatch = db.gettestGroundInfo(groundName, matchType);
                        grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                        grMatch.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                        grMatch.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));

                        if(oneMatch.size() > 5 && twoMatch.size() > 5){
                            for(int i = 0; i < oneMatch.size()-6; i++){

                                int curr = oneMatch.get(i).getInningOne1().getTotalruns();
                                Date currDate = oneMatch.get(i).getMatchDate();


                                List<testInning> sub = new ArrayList<>();
                                List<testInning> subA = new ArrayList<>();
                                List<testInning> subB = new ArrayList<>();
                                List<testInning> subG = new ArrayList<>();

                                for(int j = i+1; j < i+6; j++){
                                    sub.add(oneMatch.get(j).getInningOne1());
                                    subA.add(oneMatch.get(j).getInningOne1());
                                }

                                twoMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                                if(twoMatch.size() < 5){
                                    break;
                                }
                                for(int j = 0; j < 5; j++){
                                    sub.add(twoMatch.get(j).getInningOne1());
                                    subB.add(twoMatch.get(j).getInningOne1());
                                }

                                grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                                if(grMatch.size() >= 5){
                                    for(int j = 0; j < 5; j++){
                                        subG.add(twoMatch.get(j).getInningOne1());
                                    }
                                }

                                Comparator innComp = new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    };

                                Collections.sort(sub, innComp);
                                Collections.sort(subA, innComp);
                                Collections.sort(subB, innComp);
                                Collections.sort(subG, innComp);

                                A_bt.put("N", A_bt.get("N")+1);

                                if(curr < sub.get(0).getTotalruns()){
                                    A_bt.put("< 1/10", A_bt.get("< 1/10")+1);
                                }
                                else if(curr >= sub.get(0).getTotalruns()
                                        && curr < sub.get(1).getTotalruns()){
                                    A_bt.put("1/10 - 2/10", A_bt.get("1/10 - 2/10")+1);
                                }
                                else if(curr >= sub.get(1).getTotalruns()
                                        && curr < sub.get(2).getTotalruns()){
                                    A_bt.put("2/10 - 3/10", A_bt.get("2/10 - 3/10")+1);
                                }
                                else if(curr >= sub.get(2).getTotalruns()
                                        && curr < sub.get(3).getTotalruns() ){
                                    A_bt.put("3/10 - 4/10", A_bt.get("3/10 - 4/10")+1);
                                }
                                else if(curr >= sub.get(3).getTotalruns()
                                        && curr < sub.get(4).getTotalruns()){
                                    A_bt.put("4/10 - 5/10", A_bt.get("4/10 - 5/10")+1);
                                }
                                else if(curr >= sub.get(4).getTotalruns()
                                        && curr < sub.get(5).getTotalruns()){
                                    A_bt.put("5/10 - 6/10", A_bt.get("5/10 - 6/10")+1);
                                }
                                else if(curr >= sub.get(5).getTotalruns()
                                        && curr < sub.get(6).getTotalruns()){
                                    A_bt.put("6/10 - 7/10", A_bt.get("6/10 - 7/10")+1);
                                }
                                else if(curr >= sub.get(6).getTotalruns()
                                        && curr < sub.get(7).getTotalruns()){
                                    A_bt.put("7/10 - 8/10", A_bt.get("7/10 - 8/10")+1);
                                }
                                else if(curr >= sub.get(7).getTotalruns()
                                        && curr < sub.get(8).getTotalruns()){
                                    A_bt.put("8/10 - 9/10", A_bt.get("8/10 - 9/10")+1);
                                }
                                else if(curr >= sub.get(8).getTotalruns()
                                        && curr < sub.get(9).getTotalruns()){
                                    A_bt.put("9/10 - 10/10", A_bt.get("9/10 - 10/10")+1);
                                }
                                else{
                                    A_bt.put("10/10 <", A_bt.get("10/10 <")+1);
                                }

                                if(curr >= sub.get(2).getTotalruns()
                                        && curr >= subA.get(1).getTotalruns()
                                        && curr >= subB.get(1).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr >= subG.get(1).getTotalruns()){
                                            A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                    }
                                }

                                if(curr <= sub.get(6).getTotalruns()
                                        && curr <= subA.get(3).getTotalruns()
                                        && curr <= subB.get(3).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr <= subG.get(3).getTotalruns()){
                                            A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                    }
                                }

                                if(curr >= sub.get(3).getTotalruns()
                                        && curr >= subA.get(2).getTotalruns()
                                        && curr >= subB.get(2).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr >= subG.get(2).getTotalruns()){
                                            A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                    }
                                }

                                if(curr <= sub.get(5).getTotalruns()
                                        && curr <= subA.get(2).getTotalruns()
                                        && curr <= subB.get(2).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr <= subG.get(2).getTotalruns()){
                                            A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                    }
                                }

                                if(curr >= sub.get(1).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr >= subG.get(1).getTotalruns()){
                                            A_bt.put("2 above", A_bt.get("2 above")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("2 above", A_bt.get("2 above")+1 );
                                    }
                                }

                                if(curr >= sub.get(2).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr >= subG.get(1).getTotalruns()){
                                            A_bt.put("3 above", A_bt.get("3 above")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("3 above", A_bt.get("3 above")+1 );
                                    }
                                }

                                if(curr >= sub.get(3).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr >= subG.get(1).getTotalruns()){
                                            A_bt.put("4 above", A_bt.get("4 above")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("4 above", A_bt.get("4 above")+1 );
                                    }
                                }

                                if(curr <= sub.get(5).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr <= subG.get(3).getTotalruns()){
                                            A_bt.put("6 below", A_bt.get("6 below")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("6 below", A_bt.get("6 below")+1 );
                                    }
                                }

                                if(curr <= sub.get(6).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr <= subG.get(3).getTotalruns()){
                                            A_bt.put("7 below", A_bt.get("7 below")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("7 below", A_bt.get("7 below")+1 );
                                    }
                                }

                                if(curr <= sub.get(7).getTotalruns()){
                                    if(subG.size()==5){
                                        if(curr <= subG.get(3).getTotalruns()){
                                            A_bt.put("8 below", A_bt.get("8 below")+1 );
                                        }
                                    }
                                    else{
                                        A_bt.put("8 below", A_bt.get("8 below")+1 );
                                    }
                                }
                            }
                        }

                        request.setAttribute("first0A_bt", A_bt);
                    }
                
                
                
                if(true){
                    
                    Map<String,Integer> B_bt = new LinkedHashMap<>();
                    B_bt.put("N", 0);
                    B_bt.put("< 1/10", 0);
                    B_bt.put("1/10 - 2/10", 0);
                    B_bt.put("2/10 - 3/10", 0);
                    B_bt.put("3/10 - 4/10", 0);
                    B_bt.put("4/10 - 5/10", 0);
                    B_bt.put("5/10 - 6/10", 0);
                    B_bt.put("6/10 - 7/10", 0);
                    B_bt.put("7/10 - 8/10", 0);
                    B_bt.put("8/10 - 9/10", 0);
                    B_bt.put("9/10 - 10/10", 0);
                    B_bt.put("10/10 <", 0);
                    B_bt.put("2-2-2-3 above", 0);
                    B_bt.put("4-4-4-7 below", 0);
                    B_bt.put("3-3-3-4 above", 0);
                    B_bt.put("3-3-3-6 below", 0);
                    B_bt.put("2 above", 0);
                    B_bt.put("3 above", 0);
                    B_bt.put("4 above", 0);
                    B_bt.put("6 below", 0);
                    B_bt.put("7 below", 0);
                    B_bt.put("8 below", 0);

                    int hind = 1;

                    List<testMatch> oneMatch = db.gettestaway(teamOne, 1, 1);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    oneMatch.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                    oneMatch.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));

                    List<testMatch> twoMatch = db.gettesthome(teamTwo, 1, 2);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    twoMatch.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                    twoMatch.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));

                    List<testMatch> grMatch = db.gettestGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    grMatch.removeIf(m -> (m.getInningOne1().getRuns5wicket() == -1));
                    grMatch.removeIf(m -> (m.getInningOne1().getTotalruns() == 0));

                    if(oneMatch.size() > 5 && twoMatch.size() > 5){

                        for(int i = 0; i < twoMatch.size()-6; i++){

                            int curr = twoMatch.get(i).getInningOne1().getTotalruns();
                            Date currDate = twoMatch.get(i).getMatchDate();


                            List<testInning> sub = new ArrayList<>();
                            List<testInning> subA = new ArrayList<>();
                            List<testInning> subB = new ArrayList<>();
                            List<testInning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(twoMatch.get(j).getInningOne1());
                                subB.add(twoMatch.get(j).getInningOne1());
                            }

                            oneMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(oneMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(oneMatch.get(j).getInningOne1());
                                subA.add(oneMatch.get(j).getInningOne1());
                            }

                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningOne1());
                                }
                            }

                            Comparator innComp = new Comparator<testInning>() {
                                    @Override
                                    public int compare(testInning o1, testInning o2) {
                                        return o1.getTotalruns()
                                                - o2.getTotalruns();
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            B_bt.put("N", B_bt.get("N")+1);

                            if(curr < sub.get(0).getTotalruns()){
                                B_bt.put("< 1/10", B_bt.get("< 1/10")+1);
                            }
                            else if(curr >= sub.get(0).getTotalruns()
                                    && curr < sub.get(1).getTotalruns()){
                                B_bt.put("1/10 - 2/10", B_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= sub.get(1).getTotalruns()
                                    && curr < sub.get(2).getTotalruns()){
                                B_bt.put("2/10 - 3/10", B_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= sub.get(2).getTotalruns()
                                    && curr < sub.get(3).getTotalruns()){
                                B_bt.put("3/10 - 4/10", B_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= sub.get(3).getTotalruns()
                                    && curr < sub.get(4).getTotalruns()){
                                B_bt.put("4/10 - 5/10", B_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= sub.get(4).getTotalruns()
                                    && curr < sub.get(5).getTotalruns()){
                                B_bt.put("5/10 - 6/10", B_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= sub.get(5).getTotalruns()
                                    && curr < sub.get(6).getTotalruns()){
                                B_bt.put("6/10 - 7/10", B_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= sub.get(6).getTotalruns()
                                    && curr < sub.get(7).getTotalruns()){
                                B_bt.put("7/10 - 8/10", B_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= sub.get(7).getTotalruns()
                                    && curr < sub.get(8).getTotalruns()){
                                B_bt.put("8/10 - 9/10", B_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= sub.get(8).getTotalruns()
                                    && curr < sub.get(9).getTotalruns()){
                                B_bt.put("9/10 - 10/10", B_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                B_bt.put("10/10 <", B_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= sub.get(2).getTotalruns()
                                    && curr >= subA.get(1).getTotalruns()
                                    && curr >= subB.get(1).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr >= subG.get(1).getTotalruns()){
                                        B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= sub.get(6).getTotalruns()
                                    && curr <= subA.get(3).getTotalruns()
                                    && curr <= subB.get(3).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr <= subG.get(3).getTotalruns()){
                                        B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= sub.get(3).getTotalruns()
                                    && curr >= subA.get(2).getTotalruns()
                                    && curr >= subB.get(2).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr >= subG.get(2).getTotalruns()){
                                        B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= sub.get(5).getTotalruns()
                                    && curr <= subA.get(2).getTotalruns()
                                    && curr <= subB.get(2).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr <= subG.get(2).getTotalruns()){
                                        B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= sub.get(1).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr >= subG.get(1).getTotalruns()){
                                        B_bt.put("2 above", B_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2 above", B_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= sub.get(2).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr >= subG.get(1).getTotalruns()){
                                        B_bt.put("3 above", B_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3 above", B_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= sub.get(3).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr >= subG.get(1).getTotalruns()){
                                        B_bt.put("4 above", B_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4 above", B_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= sub.get(5).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr <= subG.get(3).getTotalruns()){
                                        B_bt.put("6 below", B_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("6 below", B_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= sub.get(6).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr <= subG.get(3).getTotalruns()){
                                        B_bt.put("7 below", B_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("7 below", B_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= sub.get(7).getTotalruns()){
                                if(subG.size()==5){
                                    if(curr <= subG.get(3).getTotalruns()){
                                        B_bt.put("8 below", B_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("8 below", B_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("first0B_bt", B_bt);
                }
                    
                    
                    
                    
                    
                    
                    
                    

                    /*
            
//Team one Bat First Innings LAST 5      
            matches = db.gettestaway(teamOne,1,1);
            k = 5;
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                testMatch q = matches.get(i);
                String res =q.getResult();
                String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamOne)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(teamOne)){
                     worl = "Win";
                }
                else if(res.equals("Match drawn")){
                    worl = "Draw";
                }
                else{
                     worl = "Lose";
                }
                
                testInning m = q.getInningOne1();
                
                m.setWinner(BorC + "/" + worl);
                t_teamoneBatFirst.add(m);
            }
            matches.clear();
 /*           
 //Team One Bats Second Innings LAST 5          
            matches = db.gettestaway(teamOne,1,2);
            k = 5;
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                testMatch q = matches.get(i);
                String res =q.getResult();
                String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamOne)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(teamOne)){
                     worl = "Win";
                }
                else if(res.equals("Match drawn")){
                    worl = "Draw";
                }
                else{
                     worl = "Lose";
                }
                
                testInning m = q.getInningTwo1();
                m.setWinner(BorC + "/" + worl);
                
                t_teamoneBatSecond.add(m);
            }
            matches.clear();
                     */
//Team One Bats Third Innings LAST 5      
/*
            matches = db.gettestaway(teamOne,1,1);
            k = 5;
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                testMatch q = matches.get(i);
                String res =q.getResult();
                String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamOne)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(teamOne)){
                     worl = "Win";
                }
                else if(res.equals("Match drawn")){
                    worl = "Draw";
                }
                else{
                     worl = "Lose";
                }
                
                testInning m = q.getInningOne2();
                 m.setWinner(BorC + "/" + worl);
                
                t_teamoneBatThird.add(m);
            }
            matches.clear();
//Team One Bats Fourth Innings LAST 5            
            matches = db.gettestaway(teamOne,1,2);
            k = 5;
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                testMatch q = matches.get(i);
                String res =q.getResult();
                String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamOne)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(teamOne)){
                     worl = "Win";
                }
                else if(res.equals("Match drawn")){
                    worl = "Draw";
                }
                else{
                     worl = "Lose";
                }
                
                testInning m = q.getInningTwo2();
                m.setWinner(BorC + "/" + worl);
                
                t_teamoneBatFourth.add(m);
            }
            matches.clear();
//Team two Bowls First Innings LAST 5            
            matches = db.gettesthome(teamTwo,1,2);
            k = 5;
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                testMatch q = matches.get(i);
                String res =q.getResult();
                String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamTwo)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(teamTwo)){
                     worl = "Win";
                }
                else if(res.equals("Match drawn")){
                    worl = "Draw";
                }
                else{
                     worl = "Lose";
                }
                
                testInning m = q.getInningOne1();
                m.setWinner(BorC + "/" + worl);
                
                t_teamtwoBowlFirst.add(m);
            }
            matches.clear();
//Team two Bowls Second Innings LAST 5            
            matches = db.gettesthome(teamTwo,1,1);
            k = 5;
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                testMatch q = matches.get(i);
                String res =q.getResult();
                String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamTwo)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(teamTwo)){
                     worl = "Win";
                }
                else if(res.equals("Match drawn")){
                    worl = "Draw";
                }
                else{
                     worl = "Lose";
                }
                
                testInning m = q.getInningTwo1();
                m.setWinner(BorC + "/" + worl);
                
                t_teamtwoBowlSecond.add(m);
            }
            matches.clear();
//Team two Bowls Third Innings LAST 5            
            matches = db.gettesthome(teamTwo,1,2);
            k = 5;
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                testMatch q = matches.get(i);
                String res =q.getResult();
                String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamTwo)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(teamTwo)){
                     worl = "Win";
                }
                else if(res.equals("Match drawn")){
                    worl = "Draw";
                }
                else{
                     worl = "Lose";
                }
                
                testInning m = q.getInningOne2();
                m.setWinner(BorC + "/" + worl);
               
                t_teamtwoBowlThird.add(m);
            }
            matches.clear();
//Team two Bowls Fourth Innings LAST 5            
            matches = db.gettesthome(teamTwo,1,1);
            k = 5;
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                testMatch q = matches.get(i);
                String res =q.getResult();
                String worl = "";
                
                String BorC = "";
                if(q.getHomeTeam().equalsIgnoreCase(teamTwo)){
                    BorC = "B";
                }
                else{
                    BorC = "C";
                }
                
                if(res.contains(teamTwo)){
                     worl = "Win";
                }
                else if(res.equals("Match drawn")){
                    worl = "Draw";
                }
                else{
                     worl = "Lose";
                }
                
                testInning m = q.getInningTwo2();
                m.setWinner(BorC + "/" + worl);
                
                t_teamtwoBowlFourth.add(m);
            }
            matches.clear();
                     */
                    List<String> headers = new ArrayList();
                    headers = db.getHeaders(matchType);

                    // out.println(t_oneBatFirst.size());
                    //out.println(t_oneBatSecond.size());
                    //out.println(t_twoBatFirst.size());
                    // out.println(t_twoBatSecond.size());
                    // out.println(t_oneBowlFirst.size());
                    // out.println(t_oneBowlSecond.size());
                    // out.println(t_twoBowlFirst.size());
                    //  out.println(t_twoBowlSecond.size());
                    //   out.println(t_groundFirst1.size());
                    //    out.println(t_groundSecond1.size());
                    // out.println(t_groundFirst2.size());
                    request.setAttribute("hth", hth);
                    request.setAttribute("hthmatch", hthmatch);
                    request.setAttribute("onetotal", onetotal);
                    request.setAttribute("twototal", twototal);

                    request.setAttribute("headers", headers);
                    request.setAttribute("teamOne", teamOne);
                    request.setAttribute("teamTwo", teamTwo);
                    request.setAttribute("t_oneBatFirst", t_oneBatFirst);
                    //request.setAttribute("t_oneBatFirstY", t_oneBatFirstY);
                    request.setAttribute("t_oneBatSecond", t_oneBatSecond);

                    request.setAttribute("t_twoBatFirst", t_twoBatFirst);

                    request.setAttribute("t_twoBatSecond", t_twoBatSecond);

                    request.setAttribute("t_oneBowlFirst", t_oneBowlFirst);
                    request.setAttribute("t_oneBowlSecond", t_oneBowlSecond);
                    request.setAttribute("t_twoBowlFirst", t_twoBowlFirst);
                    request.setAttribute("t_twoBowlSecond", t_twoBowlSecond);
//                        request.setAttribute("t_twoBowlFirstY", t_twoBowlFirstY);

                    request.setAttribute("t_groundName", groundName);
                    request.setAttribute("t_groundFirst1", t_groundFirst1);
                    request.setAttribute("t_groundSecond1", t_groundSecond1);
                    request.setAttribute("t_groundFirst2", t_groundFirst2);
                    request.setAttribute("t_groundSecond2", t_groundSecond2);

                    request.setAttribute("t_teamoneBatFirst", t_teamoneBatFirst1);
                    request.setAttribute("t_teamoneBatThird", t_teamoneBatThird1);
                    request.setAttribute("t_teamoneBowlSecond", t_teamoneBowlSecond1);
                    request.setAttribute("t_teamoneBowlFourth", t_teamoneBowlFourth1);
                    request.setAttribute("t_teamtwoBowlFirst", t_teamtwoBowlFirst1);
                    request.setAttribute("t_teamtwoBowlThird", t_teamtwoBowlThird1);
                    request.setAttribute("t_teamtwoBatSecond", t_teamtwoBatSecond1);
                    request.setAttribute("t_teamtwoBatFourth", t_teamtwoBatFourth1);
                    request.setAttribute("hometeam", hometeam);

//            System.out.println(db.getHeaders(matchType));
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/testresults.jsp");
                    dispatcher.forward(request, response);
                }
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
            String hometeam = db.checkhomeoraway(teamOne, teamTwo, groundName);
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
                test(request, response);

            } else {

                //db.initDB();
                List<Inning> oneBatFirst = new ArrayList<>();
                List<Inning> oneBatSecond = new ArrayList<>();
                List<Inning> twoBatFirst = new ArrayList<>();
                List<Inning> twoBatSecond = new ArrayList<>();

                List<Inning> oneBowlFirst = new ArrayList<>();
                List<Inning> oneBowlSecond = new ArrayList<>();
                List<Inning> twoBowlFirst = new ArrayList<>();
                List<Inning> twoBowlSecond = new ArrayList<>();

                List<Inning> groundFirst = new ArrayList<>();
                List<Inning> groundSecond = new ArrayList<>();

                Inning temp;
                List<Match> matches = db.getMatches(teamOne, matchType, 1);
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                int k = 5;
                for (int i = 0; i < matches.size(); i++) {
                    temp = matches.get(i).getInningOne();
                    int fours = Integer.parseInt(matches.get(i).getInningOne().getParams().get(4))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(4));
                    int sixes = Integer.parseInt(matches.get(i).getInningOne().getParams().get(5))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(5));
                    List<String> ps = temp.getParams();
                    ps.set(4, String.valueOf(fours));
                    ps.set(5, String.valueOf(sixes));
                    temp.setParams(ps);
                    oneBatFirst.add(temp);
//                    if (temp.getParams().get(2).equals("-1")) {
//                        k++;
//                    }
                }
                
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getMatches(teamOne, matchType, 1);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningOne());
                    }
                    Map<String,Integer> bt = new LinkedHashMap<>();
                    bt.put("< 1/5", 0);
                    bt.put("1/5 - 2/5", 0);
                    bt.put("2/5 - 3/5", 0);
                    bt.put("3/5 - 4/5", 0);
                    bt.put("4/5 - 5/5", 0);
                    bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 1;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                bt.put("< 1/5", bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                bt.put("5/5 <", bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("oneBatFirst1_bt", bt);
                }
                
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getMatches(teamOne, matchType, 1);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningOne());
                    }
                    Map<String,Integer> bt = new LinkedHashMap<>();
                    bt.put("< 1/5", 0);
                    bt.put("1/5 - 2/5", 0);
                    bt.put("2/5 - 3/5", 0);
                    bt.put("3/5 - 4/5", 0);
                    bt.put("4/5 - 5/5", 0);
                    bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 3;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                bt.put("< 1/5", bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                bt.put("5/5 <", bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("oneBatFirst3_bt", bt);
                }
                
                
                

                matches.clear();
                k = 5;
                matches = db.getMatches(teamTwo, matchType, 2);
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                for (int i = 0; i < matches.size(); i++) {
                    temp = matches.get(i).getInningTwo();
                    int fours = Integer.parseInt(matches.get(i).getInningOne().getParams().get(4))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(4));
                    int sixes = Integer.parseInt(matches.get(i).getInningOne().getParams().get(5))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(5));
                    List<String> ps = temp.getParams();
                    ps.set(4, String.valueOf(fours));
                    ps.set(5, String.valueOf(sixes));
                    temp.setParams(ps);
                    twoBatSecond.add(temp);
//                    if (temp.getParams().get(2).equals("-1")) {
//                        k++;
//                    }
                }
                
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getMatches(teamTwo, matchType, 2);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningTwo());
                    }
                    Map<String,Integer> bt = new LinkedHashMap<>();
                    bt.put("< 1/5", 0);
                    bt.put("1/5 - 2/5", 0);
                    bt.put("2/5 - 3/5", 0);
                    bt.put("3/5 - 4/5", 0);
                    bt.put("4/5 - 5/5", 0);
                    bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 1;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                bt.put("< 1/5", bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                bt.put("5/5 <", bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("twoBatSecond1_bt", bt);
                }
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getMatches(teamTwo, matchType, 2);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningTwo());
                    }
                    Map<String,Integer> bt = new LinkedHashMap<>();
                    bt.put("< 1/5", 0);
                    bt.put("1/5 - 2/5", 0);
                    bt.put("2/5 - 3/5", 0);
                    bt.put("3/5 - 4/5", 0);
                    bt.put("4/5 - 5/5", 0);
                    bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 3;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                bt.put("< 1/5", bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                bt.put("5/5 <", bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("twoBatSecond3_bt", bt);
                }

                matches.clear();
                k = 5;
                matches = db.getMatches(teamOne, matchType, 1);
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                for (int i = 0; i < Math.min(k, matches.size()); i++) {
                    temp = matches.get(i).getInningTwo();
                    int fours = Integer.parseInt(matches.get(i).getInningOne().getParams().get(4))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(4));
                    int sixes = Integer.parseInt(matches.get(i).getInningOne().getParams().get(5))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(5));
                    List<String> ps = temp.getParams();
                    ps.set(4, String.valueOf(fours));
                    ps.set(5, String.valueOf(sixes));
                    temp.setParams(ps);
                    oneBowlSecond.add(temp);
                    if (temp.getParams().get(2).equals("-1")) {
                        k++;
                    }
                }
                
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getMatches(teamOne, matchType, 1);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningTwo());
                    }
                    Map<String,Integer> bt = new LinkedHashMap<>();
                    bt.put("< 1/5", 0);
                    bt.put("1/5 - 2/5", 0);
                    bt.put("2/5 - 3/5", 0);
                    bt.put("3/5 - 4/5", 0);
                    bt.put("4/5 - 5/5", 0);
                    bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 1;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                bt.put("< 1/5", bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                bt.put("5/5 <", bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("oneBowlSecond1_bt", bt);
                }
                
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getMatches(teamOne, matchType, 1);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningTwo());
                    }
                    Map<String,Integer> bt = new LinkedHashMap<>();
                    bt.put("< 1/5", 0);
                    bt.put("1/5 - 2/5", 0);
                    bt.put("2/5 - 3/5", 0);
                    bt.put("3/5 - 4/5", 0);
                    bt.put("4/5 - 5/5", 0);
                    bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 3;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                bt.put("< 1/5", bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                bt.put("5/5 <", bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("oneBowlSecond3_bt", bt);
                }
                
                // meth here
                secondBackTest(teamOne, teamTwo, groundName, backDate, db, matchType, request);
                
                if(true){
                    Map<String,Integer> A_bt = new LinkedHashMap<>();
                    A_bt.put("N", 0);
                    A_bt.put("< 1/10", 0);
                    A_bt.put("1/10 - 2/10", 0);
                    A_bt.put("2/10 - 3/10", 0);
                    A_bt.put("3/10 - 4/10", 0);
                    A_bt.put("4/10 - 5/10", 0);
                    A_bt.put("5/10 - 6/10", 0);
                    A_bt.put("6/10 - 7/10", 0);
                    A_bt.put("7/10 - 8/10", 0);
                    A_bt.put("8/10 - 9/10", 0);
                    A_bt.put("9/10 - 10/10", 0);
                    A_bt.put("10/10 <", 0);
                    A_bt.put("2-2-2-3 above", 0);
                    A_bt.put("4-4-4-7 below", 0);
                    A_bt.put("3-3-3-4 above", 0);
                    A_bt.put("3-3-3-6 below", 0);
                    A_bt.put("2 above", 0);
                    A_bt.put("3 above", 0);
                    A_bt.put("4 above", 0);
                    A_bt.put("6 below", 0);
                    A_bt.put("7 below", 0);
                    A_bt.put("8 below", 0);

                    int hind = 3;

                    List<Match> oneMatch = db.getMatches(teamTwo, matchType, 2);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> twoMatch = db.getMatches(teamOne, matchType, 1);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> grMatch = db.getGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    
                    if(oneMatch.size() > 5 && twoMatch.size() > 5){
                        for(int i = 0; i < oneMatch.size()-6; i++){

                            int curr = Integer.parseInt(oneMatch.get(i).getInningTwo().getParams().get(hind));
                            Timestamp currDate = oneMatch.get(i).getMatchDate();


                            List<Inning> sub = new ArrayList<>();
                            List<Inning> subA = new ArrayList<>();
                            List<Inning> subB = new ArrayList<>();
                            List<Inning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(oneMatch.get(j).getInningTwo());
                                subA.add(oneMatch.get(j).getInningTwo());
                            }

                            twoMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(twoMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(twoMatch.get(j).getInningTwo());
                                subB.add(twoMatch.get(j).getInningTwo());
                            }
                            
                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningTwo());
                                }
                            }

                            Comparator innComp = new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(hind))
                                                - Integer.parseInt(o2.getParams().get(hind));
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            A_bt.put("N", A_bt.get("N")+1);

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                A_bt.put("< 1/10", A_bt.get("< 1/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind))){
                                A_bt.put("1/10 - 2/10", A_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind))){
                                A_bt.put("2/10 - 3/10", A_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind))){
                                A_bt.put("3/10 - 4/10", A_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind))){
                                A_bt.put("4/10 - 5/10", A_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(4).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(5).getParams().get(hind))){
                                A_bt.put("5/10 - 6/10", A_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(6).getParams().get(hind))){
                                A_bt.put("6/10 - 7/10", A_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(7).getParams().get(hind))){
                                A_bt.put("7/10 - 8/10", A_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(7).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(8).getParams().get(hind))){
                                A_bt.put("8/10 - 9/10", A_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(8).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(9).getParams().get(hind))){
                                A_bt.put("9/10 - 10/10", A_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                A_bt.put("10/10 <", A_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(1).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(3).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("2 above", A_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("2 above", A_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("3 above", A_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3 above", A_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("4 above", A_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("4 above", A_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("6 below", A_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("6 below", A_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("7 below", A_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("7 below", A_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(7).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("8 below", A_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("8 below", A_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("threeSecondA_bt", A_bt);
                }
                
                
                
                if(true){
                    
                    Map<String,Integer> B_bt = new LinkedHashMap<>();
                    B_bt.put("N", 0);
                    B_bt.put("< 1/10", 0);
                    B_bt.put("1/10 - 2/10", 0);
                    B_bt.put("2/10 - 3/10", 0);
                    B_bt.put("3/10 - 4/10", 0);
                    B_bt.put("4/10 - 5/10", 0);
                    B_bt.put("5/10 - 6/10", 0);
                    B_bt.put("6/10 - 7/10", 0);
                    B_bt.put("7/10 - 8/10", 0);
                    B_bt.put("8/10 - 9/10", 0);
                    B_bt.put("9/10 - 10/10", 0);
                    B_bt.put("10/10 <", 0);
                    B_bt.put("2-2-2-3 above", 0);
                    B_bt.put("4-4-4-7 below", 0);
                    B_bt.put("3-3-3-4 above", 0);
                    B_bt.put("3-3-3-6 below", 0);
                    B_bt.put("2 above", 0);
                    B_bt.put("3 above", 0);
                    B_bt.put("4 above", 0);
                    B_bt.put("6 below", 0);
                    B_bt.put("7 below", 0);
                    B_bt.put("8 below", 0);

                    int hind = 3;

                    List<Match> oneMatch = db.getMatches(teamTwo, matchType, 2);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> twoMatch = db.getMatches(teamOne, matchType, 1);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> grMatch = db.getGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    if(oneMatch.size() > 5 && twoMatch.size() > 5){

                        for(int i = 0; i < twoMatch.size()-6; i++){

                            int curr = Integer.parseInt(twoMatch.get(i).getInningTwo().getParams().get(hind));
                            Timestamp currDate = twoMatch.get(i).getMatchDate();


                            List<Inning> sub = new ArrayList<>();
                            List<Inning> subA = new ArrayList<>();
                            List<Inning> subB = new ArrayList<>();
                            List<Inning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(twoMatch.get(j).getInningTwo());
                                subB.add(twoMatch.get(j).getInningTwo());
                            }

                            oneMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(oneMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(oneMatch.get(j).getInningTwo());
                                subA.add(oneMatch.get(j).getInningTwo());
                            }

                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningTwo());
                                }
                            }

                            Comparator innComp = new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(hind))
                                                - Integer.parseInt(o2.getParams().get(hind));
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            B_bt.put("N", B_bt.get("N")+1);

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                B_bt.put("< 1/10", B_bt.get("< 1/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind))){
                                B_bt.put("1/10 - 2/10", B_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind))){
                                B_bt.put("2/10 - 3/10", B_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind))){
                                B_bt.put("3/10 - 4/10", B_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind))){
                                B_bt.put("4/10 - 5/10", B_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(4).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(5).getParams().get(hind))){
                                B_bt.put("5/10 - 6/10", B_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(6).getParams().get(hind))){
                                B_bt.put("6/10 - 7/10", B_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(7).getParams().get(hind))){
                                B_bt.put("7/10 - 8/10", B_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(7).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(8).getParams().get(hind))){
                                B_bt.put("8/10 - 9/10", B_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(8).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(9).getParams().get(hind))){
                                B_bt.put("9/10 - 10/10", B_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                B_bt.put("10/10 <", B_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(1).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(3).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("2 above", B_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2 above", B_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("3 above", B_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3 above", B_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("4 above", B_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4 above", B_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("6 below", B_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("6 below", B_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("7 below", B_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("7 below", B_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(7).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("8 below", B_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("8 below", B_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("threeSecondB_bt", B_bt);
                }
                
                

                matches.clear();
                k = 5;
                matches = db.getMatches(teamTwo, matchType, 2);
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                for (int i = 0; i < matches.size(); i++) {
                    temp = matches.get(i).getInningOne();
                    int fours = Integer.parseInt(matches.get(i).getInningOne().getParams().get(4))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(4));
                    int sixes = Integer.parseInt(matches.get(i).getInningOne().getParams().get(5))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(5));
                    List<String> ps = temp.getParams();
                    ps.set(4, String.valueOf(fours));
                    ps.set(5, String.valueOf(sixes));
                    temp.setParams(ps);
                    twoBowlFirst.add(temp);
//                    if (temp.getParams().get(2).equals("-1")) {
//                        k++;
//                    }
                }
                
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getMatches(teamTwo, matchType, 2);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningOne());
                    }
                    Map<String,Integer> bt = new LinkedHashMap<>();
                    bt.put("< 1/5", 0);
                    bt.put("1/5 - 2/5", 0);
                    bt.put("2/5 - 3/5", 0);
                    bt.put("3/5 - 4/5", 0);
                    bt.put("4/5 - 5/5", 0);
                    bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 1;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                bt.put("< 1/5", bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                bt.put("5/5 <", bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("twoBowlFirst1_bt", bt);
                }
                
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getMatches(teamTwo, matchType, 2);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningOne());
                    }
                    Map<String,Integer> bt = new LinkedHashMap<>();
                    bt.put("< 1/5", 0);
                    bt.put("1/5 - 2/5", 0);
                    bt.put("2/5 - 3/5", 0);
                    bt.put("3/5 - 4/5", 0);
                    bt.put("4/5 - 5/5", 0);
                    bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 3;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                bt.put("< 1/5", bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                bt.put("5/5 <", bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("twoBowlFirst3_bt", bt);
                }
                
                if(true){
                    Map<String,Integer> A_bt = new LinkedHashMap<>();
                    A_bt.put("N", 0);
                    A_bt.put("< 1/10", 0);
                    A_bt.put("1/10 - 2/10", 0);
                    A_bt.put("2/10 - 3/10", 0);
                    A_bt.put("3/10 - 4/10", 0);
                    A_bt.put("4/10 - 5/10", 0);
                    A_bt.put("5/10 - 6/10", 0);
                    A_bt.put("6/10 - 7/10", 0);
                    A_bt.put("7/10 - 8/10", 0);
                    A_bt.put("8/10 - 9/10", 0);
                    A_bt.put("9/10 - 10/10", 0);
                    A_bt.put("10/10 <", 0);
                    A_bt.put("2-2-2-3 above", 0);
                    A_bt.put("4-4-4-7 below", 0);
                    A_bt.put("3-3-3-4 above", 0);
                    A_bt.put("3-3-3-6 below", 0);
                    A_bt.put("2 above", 0);
                    A_bt.put("3 above", 0);
                    A_bt.put("4 above", 0);
                    A_bt.put("6 below", 0);
                    A_bt.put("7 below", 0);
                    A_bt.put("8 below", 0);

                    int hind = 1;

                    List<Match> oneMatch = db.getMatches(teamOne, matchType, 1);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> twoMatch = db.getMatches(teamTwo, matchType, 2);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> grMatch = db.getGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    
                    if(oneMatch.size() > 5 && twoMatch.size() > 5){
                        for(int i = 0; i < oneMatch.size()-6; i++){

                            int curr = Integer.parseInt(oneMatch.get(i).getInningOne().getParams().get(hind));
                            Timestamp currDate = oneMatch.get(i).getMatchDate();


                            List<Inning> sub = new ArrayList<>();
                            List<Inning> subA = new ArrayList<>();
                            List<Inning> subB = new ArrayList<>();
                            List<Inning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(oneMatch.get(j).getInningOne());
                                subA.add(oneMatch.get(j).getInningOne());
                            }

                            twoMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(twoMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(twoMatch.get(j).getInningOne());
                                subB.add(twoMatch.get(j).getInningOne());
                            }
                            
                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningOne());
                                }
                            }

                            Comparator innComp = new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(hind))
                                                - Integer.parseInt(o2.getParams().get(hind));
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            A_bt.put("N", A_bt.get("N")+1);

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                A_bt.put("< 1/10", A_bt.get("< 1/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind))){
                                A_bt.put("1/10 - 2/10", A_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind))){
                                A_bt.put("2/10 - 3/10", A_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind))){
                                A_bt.put("3/10 - 4/10", A_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind))){
                                A_bt.put("4/10 - 5/10", A_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(4).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(5).getParams().get(hind))){
                                A_bt.put("5/10 - 6/10", A_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(6).getParams().get(hind))){
                                A_bt.put("6/10 - 7/10", A_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(7).getParams().get(hind))){
                                A_bt.put("7/10 - 8/10", A_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(7).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(8).getParams().get(hind))){
                                A_bt.put("8/10 - 9/10", A_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(8).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(9).getParams().get(hind))){
                                A_bt.put("9/10 - 10/10", A_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                A_bt.put("10/10 <", A_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(1).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(3).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("2 above", A_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("2 above", A_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("3 above", A_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3 above", A_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("4 above", A_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("4 above", A_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("6 below", A_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("6 below", A_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("7 below", A_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("7 below", A_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(7).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("8 below", A_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("8 below", A_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("oneA_bt", A_bt);
                }
                
                
                
                if(true){
                    
                    Map<String,Integer> B_bt = new LinkedHashMap<>();
                    B_bt.put("N", 0);
                    B_bt.put("< 1/10", 0);
                    B_bt.put("1/10 - 2/10", 0);
                    B_bt.put("2/10 - 3/10", 0);
                    B_bt.put("3/10 - 4/10", 0);
                    B_bt.put("4/10 - 5/10", 0);
                    B_bt.put("5/10 - 6/10", 0);
                    B_bt.put("6/10 - 7/10", 0);
                    B_bt.put("7/10 - 8/10", 0);
                    B_bt.put("8/10 - 9/10", 0);
                    B_bt.put("9/10 - 10/10", 0);
                    B_bt.put("10/10 <", 0);
                    B_bt.put("2-2-2-3 above", 0);
                    B_bt.put("4-4-4-7 below", 0);
                    B_bt.put("3-3-3-4 above", 0);
                    B_bt.put("3-3-3-6 below", 0);
                    B_bt.put("2 above", 0);
                    B_bt.put("3 above", 0);
                    B_bt.put("4 above", 0);
                    B_bt.put("6 below", 0);
                    B_bt.put("7 below", 0);
                    B_bt.put("8 below", 0);

                    int hind = 1;

                    List<Match> oneMatch = db.getMatches(teamOne, matchType, 1);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> twoMatch = db.getMatches(teamTwo, matchType, 2);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> grMatch = db.getGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    if(oneMatch.size() > 5 && twoMatch.size() > 5){

                        for(int i = 0; i < twoMatch.size()-6; i++){

                            int curr = Integer.parseInt(twoMatch.get(i).getInningOne().getParams().get(hind));
                            Timestamp currDate = twoMatch.get(i).getMatchDate();


                            List<Inning> sub = new ArrayList<>();
                            List<Inning> subA = new ArrayList<>();
                            List<Inning> subB = new ArrayList<>();
                            List<Inning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(twoMatch.get(j).getInningOne());
                                subB.add(twoMatch.get(j).getInningOne());
                            }

                            oneMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(oneMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(oneMatch.get(j).getInningOne());
                                subA.add(oneMatch.get(j).getInningOne());
                            }

                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningOne());
                                }
                            }

                            Comparator innComp = new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(hind))
                                                - Integer.parseInt(o2.getParams().get(hind));
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            B_bt.put("N", B_bt.get("N")+1);

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                B_bt.put("< 1/10", B_bt.get("< 1/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind))){
                                B_bt.put("1/10 - 2/10", B_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind))){
                                B_bt.put("2/10 - 3/10", B_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind))){
                                B_bt.put("3/10 - 4/10", B_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind))){
                                B_bt.put("4/10 - 5/10", B_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(4).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(5).getParams().get(hind))){
                                B_bt.put("5/10 - 6/10", B_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(6).getParams().get(hind))){
                                B_bt.put("6/10 - 7/10", B_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(7).getParams().get(hind))){
                                B_bt.put("7/10 - 8/10", B_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(7).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(8).getParams().get(hind))){
                                B_bt.put("8/10 - 9/10", B_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(8).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(9).getParams().get(hind))){
                                B_bt.put("9/10 - 10/10", B_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                B_bt.put("10/10 <", B_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(1).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(3).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("2 above", B_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2 above", B_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("3 above", B_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3 above", B_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("4 above", B_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4 above", B_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("6 below", B_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("6 below", B_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("7 below", B_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("7 below", B_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(7).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("8 below", B_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("8 below", B_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("oneB_bt", B_bt);
                }
                
                if(true){
                    Map<String,Integer> A_bt = new LinkedHashMap<>();
                    A_bt.put("N", 0);
                    A_bt.put("< 1/10", 0);
                    A_bt.put("1/10 - 2/10", 0);
                    A_bt.put("2/10 - 3/10", 0);
                    A_bt.put("3/10 - 4/10", 0);
                    A_bt.put("4/10 - 5/10", 0);
                    A_bt.put("5/10 - 6/10", 0);
                    A_bt.put("6/10 - 7/10", 0);
                    A_bt.put("7/10 - 8/10", 0);
                    A_bt.put("8/10 - 9/10", 0);
                    A_bt.put("9/10 - 10/10", 0);
                    A_bt.put("10/10 <", 0);
                    A_bt.put("2-2-2-3 above", 0);
                    A_bt.put("4-4-4-7 below", 0);
                    A_bt.put("3-3-3-4 above", 0);
                    A_bt.put("3-3-3-6 below", 0);
                    A_bt.put("2 above", 0);
                    A_bt.put("3 above", 0);
                    A_bt.put("4 above", 0);
                    A_bt.put("6 below", 0);
                    A_bt.put("7 below", 0);
                    A_bt.put("8 below", 0);

                    int hind = 3;

                    List<Match> oneMatch = db.getMatches(teamOne, matchType, 1);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> twoMatch = db.getMatches(teamTwo, matchType, 2);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> grMatch = db.getGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    
                    if(oneMatch.size() > 5 && twoMatch.size() > 5){
                        for(int i = 0; i < oneMatch.size()-6; i++){

                            int curr = Integer.parseInt(oneMatch.get(i).getInningOne().getParams().get(hind));
                            Timestamp currDate = oneMatch.get(i).getMatchDate();


                            List<Inning> sub = new ArrayList<>();
                            List<Inning> subA = new ArrayList<>();
                            List<Inning> subB = new ArrayList<>();
                            List<Inning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(oneMatch.get(j).getInningOne());
                                subA.add(oneMatch.get(j).getInningOne());
                            }

                            twoMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(twoMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(twoMatch.get(j).getInningOne());
                                subB.add(twoMatch.get(j).getInningOne());
                            }
                            
                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningOne());
                                }
                            }

                            Comparator innComp = new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(hind))
                                                - Integer.parseInt(o2.getParams().get(hind));
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            A_bt.put("N", A_bt.get("N")+1);

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                A_bt.put("< 1/10", A_bt.get("< 1/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind))){
                                A_bt.put("1/10 - 2/10", A_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind))){
                                A_bt.put("2/10 - 3/10", A_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind))){
                                A_bt.put("3/10 - 4/10", A_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind))){
                                A_bt.put("4/10 - 5/10", A_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(4).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(5).getParams().get(hind))){
                                A_bt.put("5/10 - 6/10", A_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(6).getParams().get(hind))){
                                A_bt.put("6/10 - 7/10", A_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(7).getParams().get(hind))){
                                A_bt.put("7/10 - 8/10", A_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(7).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(8).getParams().get(hind))){
                                A_bt.put("8/10 - 9/10", A_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(8).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(9).getParams().get(hind))){
                                A_bt.put("9/10 - 10/10", A_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                A_bt.put("10/10 <", A_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(1).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(3).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("2 above", A_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("2 above", A_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("3 above", A_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3 above", A_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("4 above", A_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("4 above", A_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("6 below", A_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("6 below", A_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("7 below", A_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("7 below", A_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(7).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("8 below", A_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("8 below", A_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("threeA_bt", A_bt);
                }
                
                
                
                if(true){
                    
                    Map<String,Integer> B_bt = new LinkedHashMap<>();
                    B_bt.put("N", 0);
                    B_bt.put("< 1/10", 0);
                    B_bt.put("1/10 - 2/10", 0);
                    B_bt.put("2/10 - 3/10", 0);
                    B_bt.put("3/10 - 4/10", 0);
                    B_bt.put("4/10 - 5/10", 0);
                    B_bt.put("5/10 - 6/10", 0);
                    B_bt.put("6/10 - 7/10", 0);
                    B_bt.put("7/10 - 8/10", 0);
                    B_bt.put("8/10 - 9/10", 0);
                    B_bt.put("9/10 - 10/10", 0);
                    B_bt.put("10/10 <", 0);
                    B_bt.put("2-2-2-3 above", 0);
                    B_bt.put("4-4-4-7 below", 0);
                    B_bt.put("3-3-3-4 above", 0);
                    B_bt.put("3-3-3-6 below", 0);
                    B_bt.put("2 above", 0);
                    B_bt.put("3 above", 0);
                    B_bt.put("4 above", 0);
                    B_bt.put("6 below", 0);
                    B_bt.put("7 below", 0);
                    B_bt.put("8 below", 0);

                    int hind = 3;

                    List<Match> oneMatch = db.getMatches(teamOne, matchType, 1);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> twoMatch = db.getMatches(teamTwo, matchType, 2);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> grMatch = db.getGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    if(oneMatch.size() > 5 && twoMatch.size() > 5){

                        for(int i = 0; i < twoMatch.size()-6; i++){

                            int curr = Integer.parseInt(twoMatch.get(i).getInningOne().getParams().get(hind));
                            Timestamp currDate = twoMatch.get(i).getMatchDate();


                            List<Inning> sub = new ArrayList<>();
                            List<Inning> subA = new ArrayList<>();
                            List<Inning> subB = new ArrayList<>();
                            List<Inning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(twoMatch.get(j).getInningOne());
                                subB.add(twoMatch.get(j).getInningOne());
                            }

                            oneMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(oneMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(oneMatch.get(j).getInningOne());
                                subA.add(oneMatch.get(j).getInningOne());
                            }

                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningOne());
                                }
                            }

                            Comparator innComp = new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(hind))
                                                - Integer.parseInt(o2.getParams().get(hind));
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            B_bt.put("N", B_bt.get("N")+1);

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                B_bt.put("< 1/10", B_bt.get("< 1/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind))){
                                B_bt.put("1/10 - 2/10", B_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind))){
                                B_bt.put("2/10 - 3/10", B_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind))){
                                B_bt.put("3/10 - 4/10", B_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind))){
                                B_bt.put("4/10 - 5/10", B_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(4).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(5).getParams().get(hind))){
                                B_bt.put("5/10 - 6/10", B_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(6).getParams().get(hind))){
                                B_bt.put("6/10 - 7/10", B_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(7).getParams().get(hind))){
                                B_bt.put("7/10 - 8/10", B_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(7).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(8).getParams().get(hind))){
                                B_bt.put("8/10 - 9/10", B_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(8).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(9).getParams().get(hind))){
                                B_bt.put("9/10 - 10/10", B_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                B_bt.put("10/10 <", B_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(1).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(3).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("2 above", B_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2 above", B_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("3 above", B_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3 above", B_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("4 above", B_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4 above", B_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("6 below", B_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("6 below", B_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("7 below", B_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("7 below", B_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(7).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("8 below", B_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("8 below", B_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("threeB_bt", B_bt);
                }

                List<Inning> oneFS = new ArrayList<>();
                List<Inning> twoFS = new ArrayList<>();
                matches.clear();

                k = 5;
                matches = db.getMatches(teamOne, matchType, 0);
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                for (int i = 0; i < Math.min(k, matches.size()); i++) {
                    if (matches.get(i).getResult().contains("D/L")) {
                        k++;
                        continue;
                    }
                    temp = matches.get(i).getInningOne();
                    int fours = Integer.parseInt(matches.get(i).getInningOne().getParams().get(4))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(4));
                    int sixes = Integer.parseInt(matches.get(i).getInningOne().getParams().get(5))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(5));
                    List<String> ps = temp.getParams();
                    ps.set(4, String.valueOf(fours));
                    ps.set(5, String.valueOf(sixes));
                    temp.setParams(ps);
                    oneFS.add(temp);
                }
                request.setAttribute("oneFS", oneFS);

                matches.clear();

                k = 5;
                matches = db.getMatches(teamTwo, matchType, 0);
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                for (int i = 0; i < Math.min(k, matches.size()); i++) {
                    if (matches.get(i).getResult().contains("D/L")) {
                        k++;
                        continue;
                    }
                    temp = matches.get(i).getInningOne();
                    int fours = Integer.parseInt(matches.get(i).getInningOne().getParams().get(4))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(4));
                    int sixes = Integer.parseInt(matches.get(i).getInningOne().getParams().get(5))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(5));
                    List<String> ps = temp.getParams();
                    ps.set(4, String.valueOf(fours));
                    ps.set(5, String.valueOf(sixes));
                    temp.setParams(ps);
                    twoFS.add(temp);
                }
                request.setAttribute("twoFS", twoFS);

                matches.clear();
                k = 5;
                matches = db.getGroundInfo(groundName, matchType);
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                for (int i = 0; i < matches.size(); i++) {
                    temp = matches.get(i).getInningOne();
                    int fours = Integer.parseInt(matches.get(i).getInningOne().getParams().get(4))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(4));
                    int sixes = Integer.parseInt(matches.get(i).getInningOne().getParams().get(5))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(5));
                    List<String> ps = temp.getParams();
                    ps.set(4, String.valueOf(fours));
                    ps.set(5, String.valueOf(sixes));
                    temp.setParams(ps);
                    groundFirst.add(temp);

                    Inning temp2 = matches.get(i).getInningTwo();
                    fours = Integer.parseInt(matches.get(i).getInningOne().getParams().get(4))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(4));
                    sixes = Integer.parseInt(matches.get(i).getInningOne().getParams().get(5))
                            + Integer.parseInt(matches.get(i).getInningTwo().getParams().get(5));
                    List<String> ps2 = temp2.getParams();
                    ps2.set(4, String.valueOf(fours));
                    ps2.set(5, String.valueOf(sixes));
                    temp2.setParams(ps2);
                    groundSecond.add(temp2);

                    if (temp.getParams().get(2).equals("-1") || temp2.getParams().get(2).equals("-1")) {
                        k++;
                    }

                }
                
                
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getGroundInfo(groundName, matchType);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningTwo());
                    }
                    Map<String,Integer> bt = new LinkedHashMap<>();
                    bt.put("< 1/5", 0);
                    bt.put("1/5 - 2/5", 0);
                    bt.put("2/5 - 3/5", 0);
                    bt.put("3/5 - 4/5", 0);
                    bt.put("4/5 - 5/5", 0);
                    bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 1;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                bt.put("< 1/5", bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                bt.put("5/5 <", bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("groundSecond1_bt", bt);
                }
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getGroundInfo(groundName, matchType);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningTwo());
                    }
                    Map<String,Integer> bt = new LinkedHashMap<>();
                    bt.put("< 1/5", 0);
                    bt.put("1/5 - 2/5", 0);
                    bt.put("2/5 - 3/5", 0);
                    bt.put("3/5 - 4/5", 0);
                    bt.put("4/5 - 5/5", 0);
                    bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 3;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                bt.put("< 1/5", bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                bt.put("5/5 <", bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("groundSecond3_bt", bt);
                }
                
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getGroundInfo(groundName, matchType);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningOne());
                    }
                    Map<String,Integer> bt = new LinkedHashMap<>();
                    bt.put("< 1/5", 0);
                    bt.put("1/5 - 2/5", 0);
                    bt.put("2/5 - 3/5", 0);
                    bt.put("3/5 - 4/5", 0);
                    bt.put("4/5 - 5/5", 0);
                    bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 1;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                bt.put("< 1/5", bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                bt.put("5/5 <", bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("groundFirst1_bt", bt);
                }
                
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getGroundInfo(groundName, matchType);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningOne());
                    }
                    Map<String,Integer> bt = new LinkedHashMap<>();
                    bt.put("< 1/5", 0);
                    bt.put("1/5 - 2/5", 0);
                    bt.put("2/5 - 3/5", 0);
                    bt.put("3/5 - 4/5", 0);
                    bt.put("4/5 - 5/5", 0);
                    bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 3;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                bt.put("< 1/5", bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                bt.put("5/5 <", bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("groundFirst3_bt", bt);
                }

                List<Inning> oneBatFirstX = new ArrayList<>();
                List<Inning> twoBowlFirstX = new ArrayList<>();
                List<Inning> groundFirstX = new ArrayList<>();
                List<Inning> groundSecondX = new ArrayList<>();

                for (Inning q : oneBatFirst) {
                    if (!q.getParams().get(2).contains("-1")) {
                        oneBatFirstX.add(q);
                    }
                }

                for (Inning q : twoBowlFirst) {
                    if (!q.getParams().get(2).contains("-1")) {
                        twoBowlFirstX.add(q);
                    }
                }

                for (Inning q : groundFirst) {
                    if (!q.getParams().get(2).contains("-1")) {
                        groundFirstX.add(q);
                    }
                }
                for (Inning q : groundSecond) {
                    if (!q.getParams().get(2).contains("-1")) {
                        groundSecondX.add(q);
                    }
                }
                
                Map<String,Integer> oneBatFirstX_bt = new LinkedHashMap<>();
                oneBatFirstX_bt.put("< 1/5", 0);
                oneBatFirstX_bt.put("1/5 - 2/5", 0);
                oneBatFirstX_bt.put("2/5 - 3/5", 0);
                oneBatFirstX_bt.put("3/5 - 4/5", 0);
                oneBatFirstX_bt.put("4/5 - 5/5", 0);
                oneBatFirstX_bt.put("5/5 <", 0);
                
                if(oneBatFirstX.size() > 5){
                    for(int i = 0; i < oneBatFirstX.size()-6; i++){
                        int hind = 2;
                        int curr = Integer.parseInt(oneBatFirstX.get(i).getParams().get(hind));
                        System.out.println("curr :: " + curr);
                        
                        List<Inning> sub = new ArrayList<>(oneBatFirstX.subList(i+1, i+6));
                        Collections.sort(sub, new Comparator<Inning>() {
                            @Override
                            public int compare(Inning o1, Inning o2) {
                                return Integer.parseInt(o1.getParams().get(hind))
                                        - Integer.parseInt(o2.getParams().get(hind));
                            }
                        });
                        
                        if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                            oneBatFirstX_bt.put("< 1/5", oneBatFirstX_bt.get("< 1/5")+1 );
                        }
                        else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                            oneBatFirstX_bt.put("1/5 - 2/5", oneBatFirstX_bt.get("1/5 - 2/5")+1 );
                        }
                        else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                            oneBatFirstX_bt.put("2/5 - 3/5", oneBatFirstX_bt.get("2/5 - 3/5")+1 );
                        }
                        else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                            oneBatFirstX_bt.put("3/5 - 4/5", oneBatFirstX_bt.get("3/5 - 4/5")+1 );
                        }
                        else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                            oneBatFirstX_bt.put("4/5 - 5/5", oneBatFirstX_bt.get("4/5 - 5/5")+1 );
                        }
                        else{
                            oneBatFirstX_bt.put("5/5 <", oneBatFirstX_bt.get("5/5 <")+1 );
                        }
                    } 
                }
                
                request.setAttribute("oneBatFirstX_bt", oneBatFirstX_bt);
                
                
                
                Map<String,Integer> twoBowlFirstX_bt = new LinkedHashMap<>();
                twoBowlFirstX_bt.put("< 1/5", 0);
                twoBowlFirstX_bt.put("1/5 - 2/5", 0);
                twoBowlFirstX_bt.put("2/5 - 3/5", 0);
                twoBowlFirstX_bt.put("3/5 - 4/5", 0);
                twoBowlFirstX_bt.put("4/5 - 5/5", 0);
                twoBowlFirstX_bt.put("5/5 <", 0);
                System.out.println("meh ::" + twoBowlFirstX.size());
                if(twoBowlFirstX.size() > 5){
                    for(int i = 0; i < twoBowlFirstX.size()-6; i++){
                        int hind = 2;
                        int curr = Integer.parseInt(twoBowlFirstX.get(i).getParams().get(hind));
                        System.out.println("curr :: " + curr);
                        
                        List<Inning> sub = new ArrayList<>(twoBowlFirstX.subList(i+1, i+6));
                        Collections.sort(sub, new Comparator<Inning>() {
                            @Override
                            public int compare(Inning o1, Inning o2) {
                                return Integer.parseInt(o1.getParams().get(hind))
                                        - Integer.parseInt(o2.getParams().get(hind));
                            }
                        });
                        
                        if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                            twoBowlFirstX_bt.put("< 1/5", twoBowlFirstX_bt.get("< 1/5")+1 );
                        }
                        else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                            twoBowlFirstX_bt.put("1/5 - 2/5", twoBowlFirstX_bt.get("1/5 - 2/5")+1 );
                        }
                        else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                            twoBowlFirstX_bt.put("2/5 - 3/5", twoBowlFirstX_bt.get("2/5 - 3/5")+1 );
                        }
                        else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                            twoBowlFirstX_bt.put("3/5 - 4/5", twoBowlFirstX_bt.get("3/5 - 4/5")+1 );
                        }
                        else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                            twoBowlFirstX_bt.put("4/5 - 5/5", twoBowlFirstX_bt.get("4/5 - 5/5")+1 );
                        }
                        else{
                            twoBowlFirstX_bt.put("5/5 <", twoBowlFirstX_bt.get("5/5 <")+1 );
                        }
                    }
                }
                
                request.setAttribute("twoBowlFirstX_bt", twoBowlFirstX_bt);
                
                Map<String,Integer> groundFirstX_bt = new LinkedHashMap<>();
                groundFirstX_bt.put("< 1/5", 0);
                groundFirstX_bt.put("1/5 - 2/5", 0);
                groundFirstX_bt.put("2/5 - 3/5", 0);
                groundFirstX_bt.put("3/5 - 4/5", 0);
                groundFirstX_bt.put("4/5 - 5/5", 0);
                groundFirstX_bt.put("5/5 <", 0);
                
                if(groundFirstX.size() > 5){
                    for(int i = 0; i < groundFirstX.size()-6; i++){
                        int hind = 2;
                        int curr = Integer.parseInt(groundFirstX.get(i).getParams().get(hind));
                        System.out.println("curr :: " + curr);
                        
                        List<Inning> sub = new ArrayList<>(groundFirstX.subList(i+1, i+6));
                        Collections.sort(sub, new Comparator<Inning>() {
                            @Override
                            public int compare(Inning o1, Inning o2) {
                                return Integer.parseInt(o1.getParams().get(hind))
                                        - Integer.parseInt(o2.getParams().get(hind));
                            }
                        });
                        
                        if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                            groundFirstX_bt.put("< 1/5", groundFirstX_bt.get("< 1/5")+1 );
                        }
                        else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                            groundFirstX_bt.put("1/5 - 2/5", groundFirstX_bt.get("1/5 - 2/5")+1 );
                        }
                        else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                            groundFirstX_bt.put("2/5 - 3/5", groundFirstX_bt.get("2/5 - 3/5")+1 );
                        }
                        else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                            groundFirstX_bt.put("3/5 - 4/5", groundFirstX_bt.get("3/5 - 4/5")+1 );
                        }
                        else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                            groundFirstX_bt.put("4/5 - 5/5", groundFirstX_bt.get("4/5 - 5/5")+1 );
                        }
                        else{
                            groundFirstX_bt.put("5/5 <", groundFirstX_bt.get("5/5 <")+1 );
                        }
                    }
                }
                
                request.setAttribute("groundFirstX_bt", groundFirstX_bt);
                
                //10 datapoint back testing:
                
                if(true){
                    Map<String,Integer> twoA_bt = new LinkedHashMap<>();
                    twoA_bt.put("N", 0);
                    twoA_bt.put("< 1/10", 0);
                    twoA_bt.put("1/10 - 2/10", 0);
                    twoA_bt.put("2/10 - 3/10", 0);
                    twoA_bt.put("3/10 - 4/10", 0);
                    twoA_bt.put("4/10 - 5/10", 0);
                    twoA_bt.put("5/10 - 6/10", 0);
                    twoA_bt.put("6/10 - 7/10", 0);
                    twoA_bt.put("7/10 - 8/10", 0);
                    twoA_bt.put("8/10 - 9/10", 0);
                    twoA_bt.put("9/10 - 10/10", 0);
                    twoA_bt.put("10/10 <", 0);
                    twoA_bt.put("2-2-2-3 above", 0);
                    twoA_bt.put("4-4-4-7 below", 0);
                    twoA_bt.put("3-3-3-4 above", 0);
                    twoA_bt.put("3-3-3-6 below", 0);
                    twoA_bt.put("2 above", 0);
                    twoA_bt.put("3 above", 0);
                    twoA_bt.put("4 above", 0);
                    twoA_bt.put("6 below", 0);
                    twoA_bt.put("7 below", 0);
                    twoA_bt.put("8 below", 0);

                    int hind = 2;

                    List<Match> oneMatch = db.getMatches(teamOne, matchType, 1);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    oneMatch.removeIf(m -> (m.getInningOne().getParams().get(2).contains("-1")));

                    List<Match> twoMatch = db.getMatches(teamTwo, matchType, 2);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    twoMatch.removeIf(m -> (m.getInningOne().getParams().get(2).contains("-1")));

                    List<Match> grMatch = db.getGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    grMatch.removeIf(m -> (m.getInningOne().getParams().get(2).contains("-1")));
                    
                    if(oneMatch.size() > 5 && twoMatch.size() > 5){
                        for(int i = 0; i < oneMatch.size()-6; i++){

                            int curr = Integer.parseInt(oneMatch.get(i).getInningOne().getParams().get(hind));
                            Timestamp currDate = oneMatch.get(i).getMatchDate();


                            List<Inning> sub = new ArrayList<>();
                            List<Inning> subA = new ArrayList<>();
                            List<Inning> subB = new ArrayList<>();
                            List<Inning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(oneMatch.get(j).getInningOne());
                                subA.add(oneMatch.get(j).getInningOne());
                            }

                            twoMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(twoMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(twoMatch.get(j).getInningOne());
                                subB.add(twoMatch.get(j).getInningOne());
                            }
                            
                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningOne());
                                }
                            }

                            Comparator innComp = new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(hind))
                                                - Integer.parseInt(o2.getParams().get(hind));
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            twoA_bt.put("N", twoA_bt.get("N")+1);

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                twoA_bt.put("< 1/10", twoA_bt.get("< 1/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind))){
                                twoA_bt.put("1/10 - 2/10", twoA_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind))){
                                twoA_bt.put("2/10 - 3/10", twoA_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind))){
                                twoA_bt.put("3/10 - 4/10", twoA_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind))){
                                twoA_bt.put("4/10 - 5/10", twoA_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(4).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(5).getParams().get(hind))){
                                twoA_bt.put("5/10 - 6/10", twoA_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(6).getParams().get(hind))){
                                twoA_bt.put("6/10 - 7/10", twoA_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(7).getParams().get(hind))){
                                twoA_bt.put("7/10 - 8/10", twoA_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(7).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(8).getParams().get(hind))){
                                twoA_bt.put("8/10 - 9/10", twoA_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(8).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(9).getParams().get(hind))){
                                twoA_bt.put("9/10 - 10/10", twoA_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                twoA_bt.put("10/10 <", twoA_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(1).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        twoA_bt.put("2-2-2-3 above", twoA_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    twoA_bt.put("2-2-2-3 above", twoA_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(3).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        twoA_bt.put("4-4-4-7 below", twoA_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    twoA_bt.put("4-4-4-7 below", twoA_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        twoA_bt.put("3-3-3-4 above", twoA_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    twoA_bt.put("3-3-3-4 above", twoA_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        twoA_bt.put("3-3-3-6 below", twoA_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    twoA_bt.put("3-3-3-6 below", twoA_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        twoA_bt.put("2 above", twoA_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    twoA_bt.put("2 above", twoA_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        twoA_bt.put("3 above", twoA_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    twoA_bt.put("3 above", twoA_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        twoA_bt.put("4 above", twoA_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    twoA_bt.put("4 above", twoA_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        twoA_bt.put("6 below", twoA_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    twoA_bt.put("6 below", twoA_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        twoA_bt.put("7 below", twoA_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    twoA_bt.put("7 below", twoA_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(7).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        twoA_bt.put("8 below", twoA_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    twoA_bt.put("8 below", twoA_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("twoA_bt", twoA_bt);
                }
                
                
                
                if(true){
                    
                    Map<String,Integer> twoB_bt = new LinkedHashMap<>();
                    twoB_bt.put("N", 0);
                    twoB_bt.put("< 1/10", 0);
                    twoB_bt.put("1/10 - 2/10", 0);
                    twoB_bt.put("2/10 - 3/10", 0);
                    twoB_bt.put("3/10 - 4/10", 0);
                    twoB_bt.put("4/10 - 5/10", 0);
                    twoB_bt.put("5/10 - 6/10", 0);
                    twoB_bt.put("6/10 - 7/10", 0);
                    twoB_bt.put("7/10 - 8/10", 0);
                    twoB_bt.put("8/10 - 9/10", 0);
                    twoB_bt.put("9/10 - 10/10", 0);
                    twoB_bt.put("10/10 <", 0);
                    twoB_bt.put("2-2-2-3 above", 0);
                    twoB_bt.put("4-4-4-7 below", 0);
                    twoB_bt.put("3-3-3-4 above", 0);
                    twoB_bt.put("3-3-3-6 below", 0);
                    twoB_bt.put("2 above", 0);
                    twoB_bt.put("3 above", 0);
                    twoB_bt.put("4 above", 0);
                    twoB_bt.put("6 below", 0);
                    twoB_bt.put("7 below", 0);
                    twoB_bt.put("8 below", 0);

                    int hind = 2;

                    List<Match> oneMatch = db.getMatches(teamOne, matchType, 1);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    oneMatch.removeIf(m -> (m.getInningOne().getParams().get(2).contains("-1")));

                    List<Match> twoMatch = db.getMatches(teamTwo, matchType, 2);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    twoMatch.removeIf(m -> (m.getInningOne().getParams().get(2).contains("-1")));
                    
                    List<Match> grMatch = db.getGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    grMatch.removeIf(m -> (m.getInningOne().getParams().get(2).contains("-1")));

                    if(oneMatch.size() > 5 && twoMatch.size() > 5){

                        for(int i = 0; i < twoMatch.size()-6; i++){

                            int curr = Integer.parseInt(twoMatch.get(i).getInningOne().getParams().get(hind));
                            Timestamp currDate = twoMatch.get(i).getMatchDate();


                            List<Inning> sub = new ArrayList<>();
                            List<Inning> subA = new ArrayList<>();
                            List<Inning> subB = new ArrayList<>();
                            List<Inning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(twoMatch.get(j).getInningOne());
                                subB.add(twoMatch.get(j).getInningOne());
                            }

                            oneMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(oneMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(oneMatch.get(j).getInningOne());
                                subA.add(oneMatch.get(j).getInningOne());
                            }

                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningOne());
                                }
                            }

                            Comparator innComp = new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(hind))
                                                - Integer.parseInt(o2.getParams().get(hind));
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            twoB_bt.put("N", twoB_bt.get("N")+1);

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                twoB_bt.put("< 1/10", twoB_bt.get("< 1/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind))){
                                twoB_bt.put("1/10 - 2/10", twoB_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind))){
                                twoB_bt.put("2/10 - 3/10", twoB_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind))){
                                twoB_bt.put("3/10 - 4/10", twoB_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind))){
                                twoB_bt.put("4/10 - 5/10", twoB_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(4).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(5).getParams().get(hind))){
                                twoB_bt.put("5/10 - 6/10", twoB_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(6).getParams().get(hind))){
                                twoB_bt.put("6/10 - 7/10", twoB_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(7).getParams().get(hind))){
                                twoB_bt.put("7/10 - 8/10", twoB_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(7).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(8).getParams().get(hind))){
                                twoB_bt.put("8/10 - 9/10", twoB_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(8).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(9).getParams().get(hind))){
                                twoB_bt.put("9/10 - 10/10", twoB_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                twoB_bt.put("10/10 <", twoB_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(1).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        twoB_bt.put("2-2-2-3 above", twoB_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    twoB_bt.put("2-2-2-3 above", twoB_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(3).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        twoB_bt.put("4-4-4-7 below", twoB_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    twoB_bt.put("4-4-4-7 below", twoB_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        twoB_bt.put("3-3-3-4 above", twoB_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    twoB_bt.put("3-3-3-4 above", twoB_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        twoB_bt.put("3-3-3-6 below", twoB_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    twoB_bt.put("3-3-3-6 below", twoB_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        twoB_bt.put("2 above", twoB_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    twoB_bt.put("2 above", twoB_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        twoB_bt.put("3 above", twoB_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    twoB_bt.put("3 above", twoB_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        twoB_bt.put("4 above", twoB_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    twoB_bt.put("4 above", twoB_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        twoB_bt.put("6 below", twoB_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    twoB_bt.put("6 below", twoB_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        twoB_bt.put("7 below", twoB_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    twoB_bt.put("7 below", twoB_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(7).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        twoB_bt.put("8 below", twoB_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    twoB_bt.put("8 below", twoB_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("twoB_bt", twoB_bt);
                }
                
                
//                if(true){
//                    Map<String,Integer> twoLogic_bt = new LinkedHashMap<>();
//                    twoLogic_bt.put("2-2-2-3 above", 0);
//                    twoLogic_bt.put("4-4-4-7 below", 0);
//                    
//
//                    int hind = 2;
//
//                    List<Match> oneMatch = db.getMatches(teamOne, matchType, 1);
//                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
//                    oneMatch.removeIf(m -> (m.getInningOne().getParams().get(2).contains("-1")));
//                    
//                    List<Match> twoMatch = db.getMatches(teamTwo, matchType, 2);
//                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
//                    twoMatch.removeIf(m -> (m.getInningOne().getParams().get(2).contains("-1")));
//                    
//                    List<Match> grMatch = db.getGroundInfo(groundName, matchType);
//                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
//                    grMatch.removeIf(m -> (m.getInningOne().getParams().get(2).contains("-1")));
//
//                    if(oneMatch.size() > 5 && twoMatch.size() > 5){
//
//                        for(int i = 0; i < oneMatch.size()-6; i++){
//
//                            int curr = Integer.parseInt(oneMatch.get(i).getInningOne().getParams().get(hind));
//                            Timestamp currDate = oneMatch.get(i).getMatchDate();
//
//
//                            List<Inning> sub = new ArrayList<>();
//                            List<Inning> subA = new ArrayList<>();
//                            List<Inning> subB = new ArrayList<>();
//                            List<Inning> subG = new ArrayList<>();
//                            
//                            for(int j = i+1; j < i+6; j++){
//                                sub.add(oneMatch.get(j).getInningOne());
//                                subA.add(oneMatch.get(j).getInningOne());
//                            }
//
//                            twoMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
//                            if(twoMatch.size() < 5){
//                                break;
//                            }
//                            for(int j = 0; j < 5; j++){
//                                sub.add(twoMatch.get(j).getInningOne());
//                                subB.add(twoMatch.get(j).getInningOne());
//                            }
//                            
//                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
//                            if(grMatch.size() >= 5){
//                                for(int j = 0; j < 5; j++){
//                                    subG.add(twoMatch.get(j).getInningOne());
//                                }
//                            }
//
//                            Comparator innComp = new Comparator<Inning>() {
//                                    @Override
//                                    public int compare(Inning o1, Inning o2) {
//                                        return Integer.parseInt(o1.getParams().get(hind))
//                                                - Integer.parseInt(o2.getParams().get(hind));
//                                    }
//                                };
//
//                            Collections.sort(sub, innComp);
//                            Collections.sort(subA, innComp);
//                            Collections.sort(subB, innComp);
//                            Collections.sort(subG, innComp);
//                            
//                            
//                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
//                                    && curr >= Integer.parseInt(subA.get(1).getParams().get(hind))
//                                    && curr >= Integer.parseInt(subB.get(1).getParams().get(hind))){
//                                if(subG.size()==5){
//                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
//                                        twoLogic_bt.put("2-2-2-3 above", twoLogic_bt.get("2-2-2-3 above")+1 );
//                                    }
//                                }
//                                else{
//                                    twoLogic_bt.put("2-2-2-3 above", twoLogic_bt.get("2-2-2-3 above")+1 );
//                                }
//                            }
//                            
//                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))
//                                    && curr <= Integer.parseInt(subA.get(3).getParams().get(hind))
//                                    && curr <= Integer.parseInt(subB.get(3).getParams().get(hind))){
//                                if(subG.size()==5){
//                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
//                                        twoLogic_bt.put("4-4-4-7 below", twoLogic_bt.get("4-4-4-7 below")+1 );
//                                    }
//                                }
//                                else{
//                                    twoLogic_bt.put("4-4-4-7 below", twoLogic_bt.get("4-4-4-7 below")+1 );
//                                }
//                            }
//                            
//                        }
//                    }
//
//                    request.setAttribute("twoLogic_bt", twoLogic_bt);
//                }
                
                
                
                request.setAttribute("oneBatFirstX", oneBatFirstX.subList(0, Math.min(5, oneBatFirstX.size())));
                request.setAttribute("twoBowlFirstX", twoBowlFirstX.subList(0, Math.min(5, twoBowlFirstX.size())));
                request.setAttribute("groundFirstX", groundFirstX.subList(0, Math.min(5, groundFirstX.size())));
                request.setAttribute("groundSecondX", groundSecondX.subList(0, Math.min(5, groundSecondX.size())));
                oneBatFirst = oneBatFirst.subList(0, Math.min(5, oneBatFirst.size()));
                twoBowlFirst = twoBowlFirst.subList(0, Math.min(5, twoBowlFirst.size()));
                groundFirst = groundFirst.subList(0, Math.min(5, groundFirst.size()));
                groundSecond = groundSecond.subList(0, Math.min(5, groundSecond.size()));

                List<Inning> twoBatSecondX = new ArrayList<>();
                List<Inning> oneBowlSecondX = new ArrayList<>();

                for (Inning q : twoBatSecond) {
                    if (!q.getParams().get(2).contains("-1")) {
                        twoBatSecondX.add(q);
                    }
                }

                for (Inning q : oneBowlSecond) {
                    if (!q.getParams().get(2).contains("-1")) {
                        oneBowlSecondX.add(q);
                    }
                }
                twoBatSecond = twoBatSecond.subList(0, Math.min(5, twoBatSecond.size()));
                oneBowlSecond = oneBowlSecond.subList(0, Math.min(5, oneBowlSecond.size()));
                request.setAttribute("twoBatSecondX", twoBatSecondX);
                request.setAttribute("oneBowlSecondX", oneBowlSecondX);
//            for(int i = 0; i < matches.size(); i++){
//                out.print("<h1>"+matches.get(i));
//            }

                List<Inning> oneBatFirstY = new ArrayList<>();
                List<Inning> twoBowlFirstY = new ArrayList<>();
                matches.clear();
                matches = db.getDB(matchType, teamOne);
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));

                for (Match q : matches) {
                    Inning m = q.getInningOne();
                    List<String> params = m.getParams();

                    String res = q.getResult();
                    String toss = q.getTossWinner();
                    String worl = "";
                    String BorC = "";
                    System.out.println("toss" + toss);
                    if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                        BorC = "B";
                    } else {
                        BorC = "C";
                    }
                    
                    if (res.contains(" wicket")) {
                        if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                            worl = "L";
                        } else {
                            worl = "W";
                        }
                    } else if (res.contains(" run")) {
                        if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                            worl = "W";
                        } else {
                            worl = "L";
                        }
                    } else {
                        worl = "-";
                    }
                    
                    if (res.contains("D/L")) {
                            worl = worl + "(D/L)";
                    }

                    params.set(7, BorC + "/" + worl);
                    m.setParams(params);
                    oneBatFirstY.add(m);
                }
                
                matches.clear();
                matches = db.getDB(matchType, teamTwo);
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
                    
                    if (res.contains(" wicket")) {
                        if (q.getHomeTeam().equalsIgnoreCase(teamTwo)) {
                            worl = "L";
                        } else {
                            worl = "W";
                        }
                    } else if (res.contains(" run")) {
                        if (q.getHomeTeam().equalsIgnoreCase(teamTwo)) {
                            worl = "W";
                        } else {
                            worl = "L";
                        }
                    } else {
                        worl = "-";
                    }
                    
                    if (res.contains("D/L")) {
                            worl = worl + "(D/L)";
                    }

                    params.set(7, BorC + "/" + worl);
                    m.setParams(params);
                    twoBowlFirstY.add(m);
                }
                oneBatFirstY = oneBatFirstY.subList(0, Math.min(5, oneBatFirstY.size()));
                twoBowlFirstY = twoBowlFirstY.subList(0, Math.min(5, twoBowlFirstY.size()));
                request.setAttribute("oneBatFirstY", oneBatFirstY);
                request.setAttribute("twoBowlFirstY", twoBowlFirstY);

                List<Inning> hth = new ArrayList<>();
                matches.clear();
                matches = db.getHth(matchType, teamOne, teamTwo);
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

                    if (res.contains(" wicket")) {
                        if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                            worl = "L";
                        } else {
                            worl = "W";
                        }
                    } else if (res.contains(" run")) {
                        if (q.getHomeTeam().equalsIgnoreCase(teamOne)) {
                            worl = "W";
                        } else {
                            worl = "L";
                        }
                    } else {
                        worl = "-";
                    }
                    
                    if (res.contains("D/L")) {
                            worl = worl + "(D/L)";
                    }

                    params.set(7, BorC + "/" + worl);
                    m.setParams(params);
                    hth.add(m);

                }

                matches.clear();
                List<Inning> oneBatFirstZ = new ArrayList<>();
                matches = db.getMatches(teamOne, matchType, 1);
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                k = 5;
                for (int i = 0; i < Math.min(k, matches.size()); i++) {
                    temp = matches.get(i).getInningOne();
//                List<String> ps = temp.getParams();
                    if (matches.get(i).getResult().contains("D/L")) {
                        k++;
                        List<String> params = temp.getParams();
                        String tot = params.get(6);
                        params.set(6, tot+" (D/L)");
                        temp.setParams(params);
                    }
//                temp.setParams(ps);
                    oneBatFirstZ.add(temp);
                }
                
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getMatches(teamOne, matchType, 1);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    totMatches.removeIf(m -> (m.getResult().contains("D/L")));
                    List<Inning> oZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        oZ.add(totMatches.get(i).getInningOne());
                    }
                    Map<String,Integer> oneBatFirstZ_bt = new LinkedHashMap<>();
                    oneBatFirstZ_bt.put("< 1/5", 0);
                    oneBatFirstZ_bt.put("1/5 - 2/5", 0);
                    oneBatFirstZ_bt.put("2/5 - 3/5", 0);
                    oneBatFirstZ_bt.put("3/5 - 4/5", 0);
                    oneBatFirstZ_bt.put("4/5 - 5/5", 0);
                    oneBatFirstZ_bt.put("5/5 <", 0);

                    if(oZ.size() > 5){
                        for(int i = 0; i < oZ.size()-6; i++){
                            int hind = 6;
                            int curr = Integer.parseInt(oZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(oZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                oneBatFirstZ_bt.put("< 1/5", oneBatFirstZ_bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                oneBatFirstZ_bt.put("1/5 - 2/5", oneBatFirstZ_bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                oneBatFirstZ_bt.put("2/5 - 3/5", oneBatFirstZ_bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                oneBatFirstZ_bt.put("3/5 - 4/5", oneBatFirstZ_bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                oneBatFirstZ_bt.put("4/5 - 5/5", oneBatFirstZ_bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                oneBatFirstZ_bt.put("5/5 <", oneBatFirstZ_bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("oneBatFirstZ_bt", oneBatFirstZ_bt);
                }
                

                matches.clear();
                List<Inning> twoBowlFirstZ = new ArrayList<>();
                k = 5;
                matches = db.getMatches(teamTwo, matchType, 2);
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                for (int i = 0; i < Math.min(k, matches.size()); i++) {
                    temp = matches.get(i).getInningOne();
                    if (matches.get(i).getResult().contains("D/L")) {
                        k++;
                        List<String> params = temp.getParams();
                        String tot = params.get(6);
                        params.set(6, tot+" (D/L)");
                        temp.setParams(params);
                    }
                    twoBowlFirstZ.add(temp);
                }
                
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getMatches(teamTwo, matchType, 2);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    totMatches.removeIf(m -> (m.getResult().contains("D/L")));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningOne());
                    }
                    Map<String,Integer> twoBowlFirstZ_bt = new LinkedHashMap<>();
                    twoBowlFirstZ_bt.put("< 1/5", 0);
                    twoBowlFirstZ_bt.put("1/5 - 2/5", 0);
                    twoBowlFirstZ_bt.put("2/5 - 3/5", 0);
                    twoBowlFirstZ_bt.put("3/5 - 4/5", 0);
                    twoBowlFirstZ_bt.put("4/5 - 5/5", 0);
                    twoBowlFirstZ_bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 6;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                twoBowlFirstZ_bt.put("< 1/5", twoBowlFirstZ_bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                twoBowlFirstZ_bt.put("1/5 - 2/5", twoBowlFirstZ_bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                twoBowlFirstZ_bt.put("2/5 - 3/5", twoBowlFirstZ_bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                twoBowlFirstZ_bt.put("3/5 - 4/5", twoBowlFirstZ_bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                twoBowlFirstZ_bt.put("4/5 - 5/5", twoBowlFirstZ_bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                twoBowlFirstZ_bt.put("5/5 <", twoBowlFirstZ_bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("twoBowlFirstZ_bt", twoBowlFirstZ_bt);
                }

                matches.clear();
                List<Inning> groundFirstZ = new ArrayList<>();
                List<Inning> groundSecondZ = new ArrayList<>();
                k = 5;
                matches = db.getGroundInfo(groundName, matchType);
                matches.removeIf(m -> (m.getMatchDate().after(backDate)));
                for (int i = 0; i < Math.min(k, matches.size()); i++) {
                    temp = matches.get(i).getInningOne();
                    Inning temp2 = matches.get(i).getInningTwo();

                    if (matches.get(i).getResult().contains("D/L")) {
                        k++;
                        List<String> params = temp.getParams();
                        String tot = params.get(6);
                        params.set(6, tot+" (D/L)");
                        temp.setParams(params);
                        
                        List<String> params2 = temp2.getParams();
                        String tot2 = params2.get(6);
                        params2.set(6, tot2+" (D/L)");
                        temp2.setParams(params2);
                    }

                    groundFirstZ.add(temp);
                    groundSecondZ.add(temp2);

                }
                
                if(true){
                    List<Match> totMatches = new ArrayList<>();
                    totMatches = db.getGroundInfo(groundName, matchType);
                    totMatches.removeIf(m -> (m.getMatchDate().after(backDate)));
                    totMatches.removeIf(m -> (m.getResult().contains("D/L")));
                    List<Inning> tZ = new ArrayList<>();
                    for(int i = 0; i < totMatches.size(); i++){
                        tZ.add(totMatches.get(i).getInningOne());
                    }
                    Map<String,Integer> bt = new LinkedHashMap<>();
                    bt.put("< 1/5", 0);
                    bt.put("1/5 - 2/5", 0);
                    bt.put("2/5 - 3/5", 0);
                    bt.put("3/5 - 4/5", 0);
                    bt.put("4/5 - 5/5", 0);
                    bt.put("5/5 <", 0);

                    if(tZ.size() > 5){
                        for(int i = 0; i < tZ.size()-6; i++){
                            int hind = 6;
                            int curr = Integer.parseInt(tZ.get(i).getParams().get(hind));

                            List<Inning> sub = new ArrayList<>(tZ.subList(i+1, i+6));
                            Collections.sort(sub, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(hind))
                                            - Integer.parseInt(o2.getParams().get(hind));
                                }
                            });

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                bt.put("< 1/5", bt.get("< 1/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind)) ){
                                bt.put("1/5 - 2/5", bt.get("1/5 - 2/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind)) ){
                                bt.put("2/5 - 3/5", bt.get("2/5 - 3/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind)) ){
                                bt.put("3/5 - 4/5", bt.get("3/5 - 4/5")+1 );
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind)) 
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind)) ){
                                bt.put("4/5 - 5/5", bt.get("4/5 - 5/5")+1 );
                            }
                            else{
                                bt.put("5/5 <", bt.get("5/5 <")+1 );
                            }
                        } 
                    }

                    request.setAttribute("groundFirstZ_bt", bt);
                }
                
                if(true){
                    Map<String,Integer> A_bt = new LinkedHashMap<>();
                    A_bt.put("N", 0);
                    A_bt.put("< 1/10", 0);
                    A_bt.put("1/10 - 2/10", 0);
                    A_bt.put("2/10 - 3/10", 0);
                    A_bt.put("3/10 - 4/10", 0);
                    A_bt.put("4/10 - 5/10", 0);
                    A_bt.put("5/10 - 6/10", 0);
                    A_bt.put("6/10 - 7/10", 0);
                    A_bt.put("7/10 - 8/10", 0);
                    A_bt.put("8/10 - 9/10", 0);
                    A_bt.put("9/10 - 10/10", 0);
                    A_bt.put("10/10 <", 0);
                    A_bt.put("2-2-2-3 above", 0);
                    A_bt.put("4-4-4-7 below", 0);
                    A_bt.put("3-3-3-4 above", 0);
                    A_bt.put("3-3-3-6 below", 0);
                    A_bt.put("2 above", 0);
                    A_bt.put("3 above", 0);
                    A_bt.put("4 above", 0);
                    A_bt.put("6 below", 0);
                    A_bt.put("7 below", 0);
                    A_bt.put("8 below", 0);

                    int hind = 6;

                    List<Match> oneMatch = db.getMatches(teamOne, matchType, 1);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    oneMatch.removeIf(m -> (m.getResult().contains("D/L")));

                    List<Match> twoMatch = db.getMatches(teamTwo, matchType, 2);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    twoMatch.removeIf(m -> (m.getResult().contains("D/L")));

                    List<Match> grMatch = db.getGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    grMatch.removeIf(m -> (m.getResult().contains("D/L")));
                    
                    if(oneMatch.size() > 5 && twoMatch.size() > 5){
                        for(int i = 0; i < oneMatch.size()-6; i++){

                            int curr = Integer.parseInt(oneMatch.get(i).getInningOne().getParams().get(hind));
                            Timestamp currDate = oneMatch.get(i).getMatchDate();


                            List<Inning> sub = new ArrayList<>();
                            List<Inning> subA = new ArrayList<>();
                            List<Inning> subB = new ArrayList<>();
                            List<Inning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(oneMatch.get(j).getInningOne());
                                subA.add(oneMatch.get(j).getInningOne());
                            }

                            twoMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(twoMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(twoMatch.get(j).getInningOne());
                                subB.add(twoMatch.get(j).getInningOne());
                            }
                            
                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningOne());
                                }
                            }

                            Comparator innComp = new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(hind))
                                                - Integer.parseInt(o2.getParams().get(hind));
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            A_bt.put("N", A_bt.get("N")+1);

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                A_bt.put("< 1/10", A_bt.get("< 1/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind))){
                                A_bt.put("1/10 - 2/10", A_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind))){
                                A_bt.put("2/10 - 3/10", A_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind))){
                                A_bt.put("3/10 - 4/10", A_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind))){
                                A_bt.put("4/10 - 5/10", A_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(4).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(5).getParams().get(hind))){
                                A_bt.put("5/10 - 6/10", A_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(6).getParams().get(hind))){
                                A_bt.put("6/10 - 7/10", A_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(7).getParams().get(hind))){
                                A_bt.put("7/10 - 8/10", A_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(7).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(8).getParams().get(hind))){
                                A_bt.put("8/10 - 9/10", A_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(8).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(9).getParams().get(hind))){
                                A_bt.put("9/10 - 10/10", A_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                A_bt.put("10/10 <", A_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(1).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(3).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("2 above", A_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("2 above", A_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("3 above", A_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3 above", A_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("4 above", A_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("4 above", A_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("6 below", A_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("6 below", A_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("7 below", A_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("7 below", A_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(7).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("8 below", A_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("8 below", A_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("totA_bt", A_bt);
                }
                
                
                
                if(true){
                    
                    Map<String,Integer> B_bt = new LinkedHashMap<>();
                    B_bt.put("N", 0);
                    B_bt.put("< 1/10", 0);
                    B_bt.put("1/10 - 2/10", 0);
                    B_bt.put("2/10 - 3/10", 0);
                    B_bt.put("3/10 - 4/10", 0);
                    B_bt.put("4/10 - 5/10", 0);
                    B_bt.put("5/10 - 6/10", 0);
                    B_bt.put("6/10 - 7/10", 0);
                    B_bt.put("7/10 - 8/10", 0);
                    B_bt.put("8/10 - 9/10", 0);
                    B_bt.put("9/10 - 10/10", 0);
                    B_bt.put("10/10 <", 0);
                    B_bt.put("2-2-2-3 above", 0);
                    B_bt.put("4-4-4-7 below", 0);
                    B_bt.put("3-3-3-4 above", 0);
                    B_bt.put("3-3-3-6 below", 0);
                    B_bt.put("2 above", 0);
                    B_bt.put("3 above", 0);
                    B_bt.put("4 above", 0);
                    B_bt.put("6 below", 0);
                    B_bt.put("7 below", 0);
                    B_bt.put("8 below", 0);

                    int hind = 6;

                    List<Match> oneMatch = db.getMatches(teamOne, matchType, 1);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    oneMatch.removeIf(m -> (m.getResult().contains("D/L")));

                    List<Match> twoMatch = db.getMatches(teamTwo, matchType, 2);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    twoMatch.removeIf(m -> (m.getResult().contains("D/L")));

                    List<Match> grMatch = db.getGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    grMatch.removeIf(m -> (m.getResult().contains("D/L")));

                    if(oneMatch.size() > 5 && twoMatch.size() > 5){

                        for(int i = 0; i < twoMatch.size()-6; i++){

                            int curr = Integer.parseInt(twoMatch.get(i).getInningOne().getParams().get(hind));
                            Timestamp currDate = twoMatch.get(i).getMatchDate();


                            List<Inning> sub = new ArrayList<>();
                            List<Inning> subA = new ArrayList<>();
                            List<Inning> subB = new ArrayList<>();
                            List<Inning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(twoMatch.get(j).getInningOne());
                                subB.add(twoMatch.get(j).getInningOne());
                            }

                            oneMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(oneMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(oneMatch.get(j).getInningOne());
                                subA.add(oneMatch.get(j).getInningOne());
                            }

                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningOne());
                                }
                            }

                            Comparator innComp = new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(hind))
                                                - Integer.parseInt(o2.getParams().get(hind));
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            B_bt.put("N", B_bt.get("N")+1);

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                B_bt.put("< 1/10", B_bt.get("< 1/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind))){
                                B_bt.put("1/10 - 2/10", B_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind))){
                                B_bt.put("2/10 - 3/10", B_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind))){
                                B_bt.put("3/10 - 4/10", B_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind))){
                                B_bt.put("4/10 - 5/10", B_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(4).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(5).getParams().get(hind))){
                                B_bt.put("5/10 - 6/10", B_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(6).getParams().get(hind))){
                                B_bt.put("6/10 - 7/10", B_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(7).getParams().get(hind))){
                                B_bt.put("7/10 - 8/10", B_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(7).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(8).getParams().get(hind))){
                                B_bt.put("8/10 - 9/10", B_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(8).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(9).getParams().get(hind))){
                                B_bt.put("9/10 - 10/10", B_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                B_bt.put("10/10 <", B_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(1).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(3).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("2 above", B_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2 above", B_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("3 above", B_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3 above", B_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("4 above", B_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4 above", B_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("6 below", B_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("6 below", B_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("7 below", B_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("7 below", B_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(7).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("8 below", B_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("8 below", B_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("totB_bt", B_bt);
                }

                request.setAttribute("oneBatFirstZ", oneBatFirstZ);
                request.setAttribute("twoBowlFirstZ", twoBowlFirstZ);
                request.setAttribute("groundFirstZ", groundFirstZ);
                request.setAttribute("groundSecondZ", groundSecondZ);

                

                request.setAttribute("hth", hth);

                request.setAttribute("headers", db.getHeaders(matchType));
                request.setAttribute("teamOne", teamOne);
                request.setAttribute("teamTwo", teamTwo);
                request.setAttribute("oneBatFirst", oneBatFirst);
                request.setAttribute("oneBatSecond", oneBatSecond);
                request.setAttribute("twoBatFirst", twoBatFirst);
                request.setAttribute("twoBatSecond", twoBatSecond);

                request.setAttribute("oneBowlFirst", oneBowlFirst);
                request.setAttribute("oneBowlSecond", oneBowlSecond);
                request.setAttribute("twoBowlFirst", twoBowlFirst);
                request.setAttribute("twoBowlSecond", twoBowlSecond);

                request.setAttribute("groundName", groundName);
                request.setAttribute("groundFirst", groundFirst);
                request.setAttribute("groundSecond", groundSecond);

//            System.out.println(db.getHeaders(matchType));
                RequestDispatcher dispatcher = request.getRequestDispatcher("/results.jsp");
                dispatcher.forward(request, response);

            }


    }
    
    private void secondBackTest(String teamOne, String teamTwo, String groundName, Date backDate, 
            CricDB db, int matchType, HttpServletRequest request){
        if(true){
                    Map<String,Integer> A_bt = new LinkedHashMap<>();
                    A_bt.put("N", 0);
                    A_bt.put("< 1/10", 0);
                    A_bt.put("1/10 - 2/10", 0);
                    A_bt.put("2/10 - 3/10", 0);
                    A_bt.put("3/10 - 4/10", 0);
                    A_bt.put("4/10 - 5/10", 0);
                    A_bt.put("5/10 - 6/10", 0);
                    A_bt.put("6/10 - 7/10", 0);
                    A_bt.put("7/10 - 8/10", 0);
                    A_bt.put("8/10 - 9/10", 0);
                    A_bt.put("9/10 - 10/10", 0);
                    A_bt.put("10/10 <", 0);
                    A_bt.put("2-2-2-3 above", 0);
                    A_bt.put("4-4-4-7 below", 0);
                    A_bt.put("3-3-3-4 above", 0);
                    A_bt.put("3-3-3-6 below", 0);
                    A_bt.put("2 above", 0);
                    A_bt.put("3 above", 0);
                    A_bt.put("4 above", 0);
                    A_bt.put("6 below", 0);
                    A_bt.put("7 below", 0);
                    A_bt.put("8 below", 0);

                    int hind = 1;

                    List<Match> oneMatch = db.getMatches(teamTwo, matchType, 2);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> twoMatch = db.getMatches(teamOne, matchType, 1);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> grMatch = db.getGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));
                    
                    if(oneMatch.size() > 5 && twoMatch.size() > 5){
                        for(int i = 0; i < oneMatch.size()-6; i++){

                            int curr = Integer.parseInt(oneMatch.get(i).getInningTwo().getParams().get(hind));
                            Timestamp currDate = oneMatch.get(i).getMatchDate();


                            List<Inning> sub = new ArrayList<>();
                            List<Inning> subA = new ArrayList<>();
                            List<Inning> subB = new ArrayList<>();
                            List<Inning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(oneMatch.get(j).getInningTwo());
                                subA.add(oneMatch.get(j).getInningTwo());
                            }

                            twoMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(twoMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(twoMatch.get(j).getInningTwo());
                                subB.add(twoMatch.get(j).getInningTwo());
                            }
                            
                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningTwo());
                                }
                            }

                            Comparator innComp = new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(hind))
                                                - Integer.parseInt(o2.getParams().get(hind));
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            A_bt.put("N", A_bt.get("N")+1);

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                A_bt.put("< 1/10", A_bt.get("< 1/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind))){
                                A_bt.put("1/10 - 2/10", A_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind))){
                                A_bt.put("2/10 - 3/10", A_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind))){
                                A_bt.put("3/10 - 4/10", A_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind))){
                                A_bt.put("4/10 - 5/10", A_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(4).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(5).getParams().get(hind))){
                                A_bt.put("5/10 - 6/10", A_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(6).getParams().get(hind))){
                                A_bt.put("6/10 - 7/10", A_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(7).getParams().get(hind))){
                                A_bt.put("7/10 - 8/10", A_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(7).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(8).getParams().get(hind))){
                                A_bt.put("8/10 - 9/10", A_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(8).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(9).getParams().get(hind))){
                                A_bt.put("9/10 - 10/10", A_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                A_bt.put("10/10 <", A_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(1).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("2-2-2-3 above", A_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(3).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("4-4-4-7 below", A_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3-3-3-4 above", A_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3-3-3-6 below", A_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("2 above", A_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("2 above", A_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("3 above", A_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("3 above", A_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        A_bt.put("4 above", A_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("4 above", A_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("6 below", A_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("6 below", A_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("7 below", A_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("7 below", A_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(7).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        A_bt.put("8 below", A_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    A_bt.put("8 below", A_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("oneSecondA_bt", A_bt);
                }
                
                
                
                if(true){
                    
                    Map<String,Integer> B_bt = new LinkedHashMap<>();
                    B_bt.put("N", 0);
                    B_bt.put("< 1/10", 0);
                    B_bt.put("1/10 - 2/10", 0);
                    B_bt.put("2/10 - 3/10", 0);
                    B_bt.put("3/10 - 4/10", 0);
                    B_bt.put("4/10 - 5/10", 0);
                    B_bt.put("5/10 - 6/10", 0);
                    B_bt.put("6/10 - 7/10", 0);
                    B_bt.put("7/10 - 8/10", 0);
                    B_bt.put("8/10 - 9/10", 0);
                    B_bt.put("9/10 - 10/10", 0);
                    B_bt.put("10/10 <", 0);
                    B_bt.put("2-2-2-3 above", 0);
                    B_bt.put("4-4-4-7 below", 0);
                    B_bt.put("3-3-3-4 above", 0);
                    B_bt.put("3-3-3-6 below", 0);
                    B_bt.put("2 above", 0);
                    B_bt.put("3 above", 0);
                    B_bt.put("4 above", 0);
                    B_bt.put("6 below", 0);
                    B_bt.put("7 below", 0);
                    B_bt.put("8 below", 0);

                    int hind = 1;

                    List<Match> oneMatch = db.getMatches(teamTwo, matchType, 2);
                    oneMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> twoMatch = db.getMatches(teamOne, matchType, 1);
                    twoMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    List<Match> grMatch = db.getGroundInfo(groundName, matchType);
                    grMatch.removeIf(m -> (m.getMatchDate().after(backDate)));

                    if(oneMatch.size() > 5 && twoMatch.size() > 5){

                        for(int i = 0; i < twoMatch.size()-6; i++){

                            int curr = Integer.parseInt(twoMatch.get(i).getInningTwo().getParams().get(hind));
                            Timestamp currDate = twoMatch.get(i).getMatchDate();


                            List<Inning> sub = new ArrayList<>();
                            List<Inning> subA = new ArrayList<>();
                            List<Inning> subB = new ArrayList<>();
                            List<Inning> subG = new ArrayList<>();
                            
                            for(int j = i+1; j < i+6; j++){
                                sub.add(twoMatch.get(j).getInningTwo());
                                subB.add(twoMatch.get(j).getInningTwo());
                            }

                            oneMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(oneMatch.size() < 5){
                                break;
                            }
                            for(int j = 0; j < 5; j++){
                                sub.add(oneMatch.get(j).getInningTwo());
                                subA.add(oneMatch.get(j).getInningTwo());
                            }

                            grMatch.removeIf(m -> (m.getMatchDate().after(currDate)));
                            if(grMatch.size() >= 5){
                                for(int j = 0; j < 5; j++){
                                    subG.add(twoMatch.get(j).getInningTwo());
                                }
                            }

                            Comparator innComp = new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(hind))
                                                - Integer.parseInt(o2.getParams().get(hind));
                                    }
                                };

                            Collections.sort(sub, innComp);
                            Collections.sort(subA, innComp);
                            Collections.sort(subB, innComp);
                            Collections.sort(subG, innComp);
                            
                            B_bt.put("N", B_bt.get("N")+1);

                            if(curr < Integer.parseInt(sub.get(0).getParams().get(hind))){
                                B_bt.put("< 1/10", B_bt.get("< 1/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(0).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(1).getParams().get(hind))){
                                B_bt.put("1/10 - 2/10", B_bt.get("1/10 - 2/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(2).getParams().get(hind))){
                                B_bt.put("2/10 - 3/10", B_bt.get("2/10 - 3/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(3).getParams().get(hind))){
                                B_bt.put("3/10 - 4/10", B_bt.get("3/10 - 4/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(4).getParams().get(hind))){
                                B_bt.put("4/10 - 5/10", B_bt.get("4/10 - 5/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(4).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(5).getParams().get(hind))){
                                B_bt.put("5/10 - 6/10", B_bt.get("5/10 - 6/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(6).getParams().get(hind))){
                                B_bt.put("6/10 - 7/10", B_bt.get("6/10 - 7/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(7).getParams().get(hind))){
                                B_bt.put("7/10 - 8/10", B_bt.get("7/10 - 8/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(7).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(8).getParams().get(hind))){
                                B_bt.put("8/10 - 9/10", B_bt.get("8/10 - 9/10")+1);
                            }
                            else if(curr >= Integer.parseInt(sub.get(8).getParams().get(hind))
                                    && curr < Integer.parseInt(sub.get(9).getParams().get(hind))){
                                B_bt.put("9/10 - 10/10", B_bt.get("9/10 - 10/10")+1);
                            }
                            else{
                                B_bt.put("10/10 <", B_bt.get("10/10 <")+1);
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(1).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2-2-2-3 above", B_bt.get("2-2-2-3 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(3).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4-4-4-7 below", B_bt.get("4-4-4-7 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))
                                    && curr >= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr >= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-4 above", B_bt.get("3-3-3-4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))
                                    && curr <= Integer.parseInt(subA.get(2).getParams().get(hind))
                                    && curr <= Integer.parseInt(subB.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(2).getParams().get(hind))){
                                        B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3-3-3-6 below", B_bt.get("3-3-3-6 below")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(1).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("2 above", B_bt.get("2 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("2 above", B_bt.get("2 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(2).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("3 above", B_bt.get("3 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("3 above", B_bt.get("3 above")+1 );
                                }
                            }
                            
                            if(curr >= Integer.parseInt(sub.get(3).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr >= Integer.parseInt(subG.get(1).getParams().get(hind))){
                                        B_bt.put("4 above", B_bt.get("4 above")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("4 above", B_bt.get("4 above")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(5).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("6 below", B_bt.get("6 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("6 below", B_bt.get("6 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(6).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("7 below", B_bt.get("7 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("7 below", B_bt.get("7 below")+1 );
                                }
                            }
                            
                            if(curr <= Integer.parseInt(sub.get(7).getParams().get(hind))){
                                if(subG.size()==5){
                                    if(curr <= Integer.parseInt(subG.get(3).getParams().get(hind))){
                                        B_bt.put("8 below", B_bt.get("8 below")+1 );
                                    }
                                }
                                else{
                                    B_bt.put("8 below", B_bt.get("8 below")+1 );
                                }
                            }
                        }
                    }

                    request.setAttribute("oneSecondB_bt", B_bt);
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
