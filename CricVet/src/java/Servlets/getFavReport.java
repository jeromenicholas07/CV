/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Database.CricDB;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Match;

/**
 *
 * @author Jerome Nicholas
 */
public class getFavReport extends HttpServlet {

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

        int matchType = parseInt(request.getParameter("tournament"));
        boolean isBatting = request.getParameter("sideSelect").equalsIgnoreCase("B");       //  B/C
        String tossWinner = request.getParameter("tossSelect");         //  W/L

        CricDB db = new CricDB();
        List<Match> favMatches = db.getFavMatches(matchType, isBatting);
        List<Match> allFavMatches = db.getAllFavMatches(matchType);

        MasterStudy:
        {
            int num1 = 0, den1 = 0;
            int num2 = 0, den2 = 0;
            for (Match m : allFavMatches) {
                String favTeam = db.getFavourites(m.getMatchId()).get(0);
                if (favTeam.equals(m.getHomeTeam())) {
                    if (m.getBCW().equals("B")) {
                        num1++;
                    } else {
                        den1++;
                    }
                } else {
                    if (m.getBCW().equals("C")) {
                        num2++;
                    } else {
                        den2++;
                    }
                }

            }
            request.setAttribute("masterBatting", num1 + "/" + den1);
            request.setAttribute("masterChasing", num2 + "/" + den2);
        }
        TossWon:
        {
            int num1 = 0, den1 = 0;
            int num2 = 0, den2 = 0;
            for (Match m : allFavMatches) {
                String favTeam = db.getFavourites(m.getMatchId()).get(0);
                if (favTeam.equals(m.getHomeTeam()) && m.isHomeTeamTossWinner()) {
                    if (m.getBCW().equals("B")) {
                        num1++;
                    } else {
                        den1++;
                    }
                } else if (favTeam.equals(m.getAwayTeam()) && !m.isHomeTeamTossWinner()) {
                    if (m.getBCW().equals("C")) {
                        num2++;
                    } else {
                        den2++;
                    }
                }
            }
            request.setAttribute("tossWB", num1 + "/" + den1);
            request.setAttribute("tossWC", num2 + "/" + den2);
        }
        
        TossLost:
        {
            int num1 = 0, den1 = 0;
            int num2 = 0, den2 = 0;
            for (Match m : allFavMatches) {
                String favTeam = db.getFavourites(m.getMatchId()).get(0);
                if (favTeam.equals(m.getHomeTeam()) && !m.isHomeTeamTossWinner()) {
                    if (m.getBCW().equals("B")) {
                        num1++;
                    } else {
                        den1++;
                    }
                } else if (favTeam.equals(m.getAwayTeam()) && m.isHomeTeamTossWinner()) {
                    if (m.getBCW().equals("C")) {
                        num2++;
                    } else {
                        den2++;
                    }
                }
            }
            request.setAttribute("tossLB", num1 + "/" + den1);
            request.setAttribute("tossLC", num2 + "/" + den2);
        }

        HomeRatio:
        {
            int num = 0, den = 0;
            for (Match m : favMatches) {
                String favTeamName = isBatting ? m.getHomeTeam() : m.getAwayTeam();
                if (db.getHomeGroundsFor(favTeamName, matchType).contains(m.getGroundName())) {
                    // fav is home
                    den++;
                    if ((m.getBCW().equalsIgnoreCase("B") && isBatting)
                            || (m.getBCW().equalsIgnoreCase("C") && !isBatting)) {
                        //fav won match
                        num++;
                    }

                }
            }
            request.setAttribute("homeRatio", num + "/" + den);
        }

        H2H_Ratio:
        {
            List<Match> favH2hMatches = db.getAllFavMatches(matchType);
            int num = 0, den = 0;
            for (Match m : favH2hMatches) {
                den++;
                if ((m.getBCW().equalsIgnoreCase("B") && isBatting)
                        || (m.getBCW().equalsIgnoreCase("C") && !isBatting)) {
                    //fav won match
                    num++;
                }
            }
            request.setAttribute("h2hRatio", num + "/" + den);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/favReport.jsp");
        dispatcher.forward(request, response);
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
