/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Database.CricDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            CricDB db = new CricDB();
            
            int matchType = Integer.parseInt(request.getParameter("tournament"));
            String teamOne = request.getParameter("teamName1");
            String teamTwo = request.getParameter("teamName2");
            String groundName = request.getParameter("groundName");
            
            if(matchType == 1){
                
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
                if (temp.getRuns5wicket()== -1) {
                    k++;
                }
            }
            
         

            matches.clear();
            k = 5;
            matches = db.gettestMatches(teamTwo, matchType, 2);
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
                if (temp.getRuns5wicket()== -1) {
                    k++;
                }
            }

            matches.clear();
            k = 5;
            matches = db.gettestMatches(teamOne, matchType, 1);
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
                if (temp.getRuns5wicket()== -1) {
                    k++;
                }
           }

            matches.clear();
            k = 5;
            matches = db.gettestMatches(teamTwo, matchType, 2);
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
                if (temp.getRuns5wicket()== -1) {
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
            for (int i = 0; i < Math.min(5, matches.size()); i++) {
                if(matches.get(i).getBattingFirst()== teamOne){
                temp11 = matches.get(i).getInningOne1();
                temp12 = matches.get(i).getInningOne2();
                        }
                else{
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
            request.setAttribute("one2",one2);

            matches.clear();

            matches = db.gettestMatches(teamTwo, matchType, 0);
            for (int i = 0; i < Math.min(5, matches.size()); i++) {
                if(matches.get(i).getBattingFirst()== teamTwo){
                temp21 = matches.get(i).getInningOne1();
                temp22 = matches.get(i).getInningOne2();
                        }
                else{
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
            request.setAttribute("two2",two2);

            matches.clear();
            
            k = 5;
            
            
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

            List<testInning> t_oneBatFirstX = new ArrayList<>();
            List<testInning> t_twoBowlFirstX = new ArrayList<>();
            List<testInning> t_groundFirst1X = new ArrayList<>();
            List<testInning> t_groundSecond1X = new ArrayList<>();
            List<testInning> t_groundFirst2X = new ArrayList<>();
            List<testInning> t_groundSecond2X = new ArrayList<>();
            
            for (testInning q : t_oneBatFirst) {
                if (q.getRuns5wicket()!= -1) {
                    t_oneBatFirstX.add(q);
                }
            }

            for (testInning q : t_twoBowlFirst) {
                if (q.getRuns5wicket()!= -1) {
                    t_twoBowlFirstX.add(q);
                }
            }

            for (testInning q : t_groundFirst1) {
                if (q.getRuns5wicket()!= -1) {
                    t_groundFirst1X.add(q);
                }
            }
            for (testInning q : t_groundSecond1) {
                if (q.getRuns5wicket()!= -1) {
                    t_groundSecond1X.add(q);
                }
            }
            for (testInning q : t_groundFirst2) {
                if (q.getRuns5wicket()!= -1) {
                    t_groundFirst2X.add(q);
                }
            }
            
            for (testInning q : t_groundSecond2) {
                if (q.getRuns5wicket()!= -1) {
                    t_groundSecond2X.add(q);
                }
            }
            
            t_oneBatFirst = t_oneBatFirst.subList(0, Math.min(5, t_oneBatFirst.size()));
            t_twoBowlFirst = t_twoBowlFirst.subList(0, Math.min(5, t_twoBowlFirst.size()));
            t_groundFirst1 = t_groundFirst1.subList(0, Math.min(5, t_groundFirst1.size()));
            t_groundSecond1 = t_groundSecond1.subList(0, Math.min(5, t_groundSecond1.size()));
            t_groundFirst2 = t_groundFirst2.subList(0, Math.min(5, t_groundFirst2.size()));
            t_groundSecond2 = t_groundSecond2.subList(0, Math.min(5, t_groundSecond2.size()));
            
            request.setAttribute("t_oneBatFirstX", t_oneBatFirstX);
            request.setAttribute("t_twoBowlFirstX", t_twoBowlFirstX);
            request.setAttribute("t_groundFirst1X", t_groundFirst1X.subList(0, Math.min(5, t_groundFirst1X.size())));
            //request.setAttribute("t_groundSecond1X", t_groundSecond1X.subList(0, Math.min(5, t_groundSecond1X.size())));
           // request.setAttribute("t_groundFirst2X", t_groundFirst2X.subList(0, Math.min(5, t_groundFirst2X.size())));
          //  request.setAttribute("t_groundSecond2X", t_groundSecond2X.subList(0, Math.min(5, t_groundSecond2X.size())));

            List<testInning> t_twoBatSecondX = new ArrayList<>();
            List<testInning> t_oneBowlSecondX = new ArrayList<>();

            for (testInning q : t_twoBatSecond) {
                if (q.getRuns5wicket()!= -1) {
                    t_twoBatSecondX.add(q);
                }
            }

            for (testInning q : t_oneBowlSecond) {
                if (q.getRuns5wicket()!= -1) {
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
            
            for(testMatch q : db.gettestDB(matchType, teamOne)) {
                String res =q.getResult();
                String worl = "";
                
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
                testInning m = q.getInningOne1();
                String bcwl = BorC + "/" +worl;
                m.setWinner(bcwl);
                t_oneBatFirstY.add(m);            }

            for(testMatch q : db.gettestDB(matchType, teamTwo)) {
                String res =q.getResult();
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
                testInning m = q.getInningOne1();
                String bcwl = BorC + "/" +worl;
                m.setWinner(bcwl);
                t_twoBowlFirstY.add(m);
            }
            t_oneBatFirstY = t_oneBatFirstY.subList(0, Math.min(5, t_oneBatFirstY.size()));
            t_twoBowlFirstY = t_twoBowlFirstY.subList(0, Math.min(5, t_twoBowlFirstY.size()));
            request.setAttribute("oneBatFirstY", t_oneBatFirstY);
            request.setAttribute("twoBowlFirstY", t_twoBowlFirstY);

            
            List<testInning> hth = new ArrayList<>();
            matches = db.gettestHth(matchType, teamOne, teamTwo);
            for(int i = 0; i < Math.min(5, matches.size()); i++){
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
                testInning m = q.getInningOne1();
                String bcwl = BorC + "/" +worl;
                m.setWinner(bcwl);
                hth.add(m);
                
            }
            List<String> headers =  new ArrayList();
            headers = db.getHeaders(matchType);
            
           // out.println(t_oneBatFirst.size());
            //out.println(t_oneBatSecond.size());
            //out.println(t_twoBatFirst.size());
           // out.println(t_twoBatSecond.size());
           // out.println(t_oneBowlFirst.size());
           // out.println(t_oneBowlSecond.size());
            out.println(t_twoBowlFirst.size());
         //  out.println(t_twoBowlSecond.size());
         //   out.println(t_groundFirst1.size());
        //    out.println(t_groundSecond1.size());
           // out.println(t_groundFirst2.size());
            
            
            
            request.setAttribute("hth", hth);
            
            

            request.setAttribute("headers", headers);
            request.setAttribute("teamOne", teamOne);
            request.setAttribute("teamTwo", teamTwo);
            request.setAttribute("t_oneBatFirst", t_oneBatFirst);
            request.setAttribute("t_oneBatFirstY", t_oneBatFirstY);
            request.setAttribute("t_oneBatSecond", t_oneBatSecond);
            

            request.setAttribute("t_twoBatFirst", t_twoBatFirst);
            
            request.setAttribute("t_twoBatSecond", t_twoBatSecond);

            request.setAttribute("t_oneBowlFirst", t_oneBowlFirst);
            request.setAttribute("t_oneBowlSecond", t_oneBowlSecond);
            request.setAttribute("t_twoBowlFirst", t_twoBowlFirst);
            request.setAttribute("t_twoBowlSecond", t_twoBowlSecond);
                        request.setAttribute("t_twoBowlFirstY", t_twoBowlFirstY);
                        

            request.setAttribute("t_groundName", groundName);
            request.setAttribute("t_groundFirst1", t_groundFirst1);
            request.setAttribute("t_groundSecond1", t_groundSecond1);
            request.setAttribute("t_groundFirst2", t_groundFirst2);
            request.setAttribute("t_groundSecond2", t_groundSecond2);
            

//            System.out.println(db.getHeaders(matchType));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/testresults.jsp");
            dispatcher.forward(request, response);
            
            
            
            }
            
            
            else{
            
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
            int k = 5;
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
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
                if (temp.getParams().get(2).equals("-1")) {
                    k++;
                }
            }
            
            

            matches.clear();
            k = 5;
            matches = db.getMatches(teamTwo, matchType, 2);
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
                twoBatSecond.add(temp);
                if (temp.getParams().get(2).equals("-1")) {
                    k++;
                }
            }

            matches.clear();
            k = 5;
            matches = db.getMatches(teamOne, matchType, 1);
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

            matches.clear();
            k = 5;
            matches = db.getMatches(teamTwo, matchType, 2);
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
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
                if (temp.getParams().get(2).equals("-1")) {
                    k++;
                }
            }

            List<Inning> oneFS = new ArrayList<>();
            List<Inning> twoFS = new ArrayList<>();
            matches.clear();

            matches = db.getMatches(teamOne, matchType, 0);
            for (int i = 0; i < Math.min(5, matches.size()); i++) {
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

            matches = db.getMatches(teamTwo, matchType, 0);
            for (int i = 0; i < Math.min(5, matches.size()); i++) {
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
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
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
            oneBatFirst = oneBatFirst.subList(0, Math.min(5, oneBatFirst.size()));
            twoBowlFirst = twoBowlFirst.subList(0, Math.min(5, twoBowlFirst.size()));
            groundFirst = groundFirst.subList(0, Math.min(5, groundFirst.size()));
            groundSecond = groundSecond.subList(0, Math.min(5, groundSecond.size()));
            request.setAttribute("oneBatFirstX", oneBatFirstX);
            request.setAttribute("twoBowlFirstX", twoBowlFirstX);
            request.setAttribute("groundFirstX", groundFirstX.subList(0, Math.min(5, groundFirstX.size())));
            request.setAttribute("groundSecondX", groundSecondX.subList(0, Math.min(5, groundSecondX.size())));

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
            
            for(Match q : db.getDB(matchType, teamOne)) {
                String res =q.getResult();
                String worl = "";
                
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
                Inning m = q.getInningOne();
                List<String> params = m.getParams();
                
                params.set(7, BorC + "/" + worl);
                m.setParams(params);
                oneBatFirstY.add(m);
            }

            for(Match q : db.getDB(matchType, teamTwo)) {
                String res =q.getResult();
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
                Inning m = q.getInningOne();
                List<String> params = m.getParams();
                params.set(7, BorC + "/" + worl);
                m.setParams(params);
                twoBowlFirstY.add(m);
            }
            oneBatFirstY = oneBatFirstY.subList(0, Math.min(5, oneBatFirstY.size()));
            twoBowlFirstY = twoBowlFirstY.subList(0, Math.min(5, twoBowlFirstY.size()));
            request.setAttribute("oneBatFirstY", oneBatFirstY);
            request.setAttribute("twoBowlFirstY", twoBowlFirstY);

            
            List<Inning> hth = new ArrayList<>();
            matches = db.getHth(matchType, teamOne, teamTwo);
            for(int i = 0; i < Math.min(5, matches.size()); i++){
                Match q = matches.get(i);
                
                String res =q.getResult();
                String worl = "";
                
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
                Inning m = q.getInningOne();
                List<String> params = m.getParams();
                params.set(7, BorC + "/" +worl);
                m.setParams(params);
                hth.add(m);
                
            }
            out.println(oneBatFirst.size());
            out.println(oneBatSecond.size());
            out.println(twoBatFirst.size());
            out.println(twoBatSecond.size());
            out.println(oneBowlFirst.size());
            out.println(oneBowlSecond.size());
            out.println(twoBowlFirst.size());
            out.println(twoBowlSecond.size());
            out.println(groundFirst.size());
            out.println(groundSecond.size());
            
            
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
