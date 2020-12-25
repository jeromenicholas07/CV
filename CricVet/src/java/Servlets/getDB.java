/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Database.CricDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.sql.Timestamp;
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
public class getDB extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            CricDB db = new CricDB();

            db.initDB();

            int matchType = Integer.parseInt(request.getParameter("tournament"));
            String teamOne = request.getParameter("teamName1");
            String homeoraway = request.getParameter("homeoraway");

            if (matchType == 1) {

                TestType side = homeoraway.equals("Home") ? TestType.HOME : TestType.AWAY;
                List<testMatch> matches = db.getTestMatches(teamOne, 0, side);

                request.setAttribute("team", teamOne);
                request.setAttribute("matches", matches);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/showtest.jsp");
                dispatcher.forward(request, response);
            } else {
                List<Match> matches = db.getDB(matchType, teamOne);

                List<dbMatch> dbMatches = new ArrayList<>();

                dbMatch temp;

                for (int i = 0; i < matches.size(); i++) {
                    int matchId;
                    String matchDate;
                    String team;
                    String oppTeam;
                    String tossWinner;
                    String BorC;
                    String result;

                    matchId = matches.get(i).getMatchId();
                    DateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy");
                    System.out.println(matches.get(i).getMatchId());
                    Timestamp ts = matches.get(i).getMatchDate();
                    matchDate = dateFormat.format(ts);
                    

                    if (matches.get(i).getHomeTeam().equals(teamOne)) {
                        team = matches.get(i).getHomeTeam();
                        oppTeam = matches.get(i).getAwayTeam();
                    } else {
                        team = matches.get(i).getAwayTeam();
                        oppTeam = matches.get(i).getHomeTeam();
                    }

                    if (matches.get(i).getTossWinner().contains(teamOne)) {
                        if (matches.get(i).getTossWinner().contains("field")) {
                            tossWinner = "W/C";
                            BorC = "C";
                        } else {
                            tossWinner = "W/B";
                            BorC = "B";
                        }
                    } else {
                        if (matches.get(i).getTossWinner().contains("field")) {
                            tossWinner = "L/B";
                            BorC = "B";
                        } else {
                            tossWinner = "L/C";
                            BorC = "C";
                        }
                    }

                    if (matches.get(i).getBCW().equals("B")) {
                        if (matches.get(i).getHomeTeam().equals(teamOne)) {
                            result = "W";
                        } else {
                            result = "L";
                        }
                    } else if (matches.get(i).getBCW().equals("C")) {
                        if (matches.get(i).getAwayTeam().equals(teamOne)) {
                            result = "W";
                        } else {
                            result = "L";
                        }
                    } else {
                        result = matches.get(i).getBCW();
                    }

                    Inning one;
                    Inning two;

                    if (BorC.equals("B")) {
                        one = matches.get(i).getInningOne();
                        two = matches.get(i).getInningTwo();
                    } else {
                        one = matches.get(i).getInningTwo();
                        two = matches.get(i).getInningOne();
                    }

                    int totalSixes = Integer.parseInt(one.getParams().get(5)) + Integer.parseInt(two.getParams().get(5));

                    List<String> details = db.getFavourites(matchId);
                    String favTeam = "";
                    String open1 = "";
                    String high1 = "";
                    String low1 = "";
                    String open2 = "";
                    String high2 = "";
                    String low2 = "";

                    if (details.size() == 7) {
                        favTeam = details.get(0);
                        open1 = details.get(1);
                        high1 = details.get(2);
                        low1 = details.get(3);
                        open2 = details.get(4);
                        high2 = details.get(5);
                        low2 = details.get(6);
                    }

                    temp = new dbMatch(matchId, matchDate, team, oppTeam, tossWinner, BorC, result, totalSixes, one, two, favTeam, open1, high1, low1, open2, high2, low2);
                    dbMatches.add(temp);

                }

                request.setAttribute("team", teamOne);
                request.setAttribute("matches", dbMatches);
                request.setAttribute("inningHeaders", db.getHeaders(matchType));

                RequestDispatcher dispatcher = request.getRequestDispatcher("/dbResults.jsp");
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
