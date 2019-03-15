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
import models.Inning;
import models.Match;

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
            
            db.initDB();
            
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

            int matchType = Integer.parseInt(request.getParameter("tournament"));
            String teamOne = request.getParameter("teamName1");
            String teamTwo = request.getParameter("teamName2");
            String groundName = request.getParameter("groundName");
            
            Inning temp;
            List<Match> matches = db.getMatches(teamOne, matchType, 1);
            int k = 5;
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                oneBatFirst.add(matches.get(i).getInningOne());
                temp =  db.getInning(matches.get(i).getInningOne().getInningId());
                if( temp.getParams().get(3).equals("-1"))
                {
                    k++;
                }
            }

            matches.clear();
            k = 5;
            matches = db.getMatches(teamTwo, matchType, 2);
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                twoBatSecond.add(matches.get(i).getInningTwo());
                temp =  db.getInning(matches.get(i).getInningTwo().getInningId());
                  if( temp.getParams().get(3).equals("-1"))
                {
                    k++;
                }
            }

            matches.clear();
            k = 5;
            matches = db.getMatches(teamOne, matchType, 2);
            System.out.println("t:" + teamOne + " m:" + matches.size() + "mt: " + matchType);
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                oneBatSecond.add(matches.get(i).getInningTwo());
                temp =  db.getInning(matches.get(i).getInningTwo().getInningId());
                  if( temp.getParams().get(3).equals("-1"))
                {
                    k++;
                }
            }

            matches.clear();
            k = 5;
            matches = db.getMatches(teamTwo, matchType, 1);
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                twoBatFirst.add(matches.get(i).getInningOne());
                temp =  db.getInning(matches.get(i).getInningOne().getInningId());
                  if( temp.getParams().get(3).equals("-1"))
                {
                    k++;
                }
            }

            matches.clear();
            k = 5;
            matches = db.getMatches(teamOne, matchType, 1);
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                oneBowlSecond.add(matches.get(i).getInningTwo());
                temp =  db.getInning(matches.get(i).getInningTwo().getInningId());
                  if( temp.getParams().get(3).equals("-1"))
                {
                    k++;
                }
            }

            matches.clear();
            k = 5;
            matches = db.getMatches(teamOne, matchType, 2);
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                oneBowlFirst.add(matches.get(i).getInningOne());
                temp =  db.getInning(matches.get(i).getInningOne().getInningId());
                  if( temp.getParams().get(3).equals("-1"))
                {
                    k++;
                }
            }

            matches.clear();
            k = 5;
            matches = db.getMatches(teamTwo, matchType, 1);
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                twoBowlSecond.add(matches.get(i).getInningTwo());
                temp =  db.getInning(matches.get(i).getInningTwo().getInningId());
                  if( temp.getParams().get(3).equals("-1"))
                {
                    k++;
                }
            }

            matches.clear();
            k = 5;
            matches = db.getMatches(teamTwo, matchType, 2);
            for (int i = 0; i < Math.min(k, matches.size()); i++) {
                twoBowlFirst.add(matches.get(i).getInningOne());
                temp =  db.getInning(matches.get(i).getInningOne().getInningId());
                  if( temp.getParams().get(3).equals("-1"))
                {
                    k++;
                }
            }
            
            
            
            
            
            matches.clear();
            matches = db.getGroundInfo(groundName, matchType);
            for (int i = 0; i < Math.min(5, matches.size()); i++) {
                groundFirst.add(matches.get(i).getInningOne());
                groundSecond.add(matches.get(i).getInningTwo());
                
            }

//            for(int i = 0; i < matches.size(); i++){
//                out.print("<h1>"+matches.get(i));
//            }
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
