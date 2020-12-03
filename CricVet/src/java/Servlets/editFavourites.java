/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Database.CricDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jerome Nicholas
 */
public class editFavourites extends HttpServlet {

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

            int matchID = Integer.parseInt(request.getParameter("matchID"));
            if (matchID <= 0) {
                out.println("Invalid matchID");
                return;
            }
            String redirUrl = request.getParameter("redirUrl");
            request.setAttribute("redirUrl", redirUrl);
            CricDB db = new CricDB();

            if (request.getParameter("favTeam") != null && !request.getParameter("favTeam").trim().equals("")) {
                String favTeam = request.getParameter("favTeam");
                String open1 = request.getParameter("open1");
                String high1 = request.getParameter("high1");
                String low1 = request.getParameter("low1");
                String open2 = request.getParameter("open2");
                String high2 = request.getParameter("high2");
                String low2 = request.getParameter("low2");

                if (db.updateFavourites(matchID, favTeam, open1, high1, low1, open2, high2, low2)) {
                    out.println("<h2>Edit successful<br>");
                    out.println("<a href="+redirUrl+">< Back to DB page</a>");
                } else {
                    out.println("Edit UNSUCCESSFUL// Please try again.");
                }
            } else {
                String favTeam = "";
                String open1 = "";
                String high1 = "";
                String low1 = "";
                String open2 = "";
                String high2 = "";
                String low2 = "";

                List<String> details = db.getFavourites(matchID);
                if (details.size() == 7) {
                    favTeam = details.get(0);
                    open1 = details.get(1);
                    high1 = details.get(2);
                    low1 = details.get(3);
                    open2 = details.get(4);
                    high2 = details.get(5);
                    low2 = details.get(6);
                }
                
                String team1 = request.getParameter("team1");
                String team2 = request.getParameter("team2");

                request.setAttribute("matchID", matchID);
                request.setAttribute("team1", team1);
                request.setAttribute("team2", team2);
                request.setAttribute("favTeam", favTeam);
                request.setAttribute("open1", open1);
                request.setAttribute("high1", high1);
                request.setAttribute("low1", low1);
                request.setAttribute("open2", open2);
                request.setAttribute("high2", high2);
                request.setAttribute("low2", low2);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/editFavourites.jsp");
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
