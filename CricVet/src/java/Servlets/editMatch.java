/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Database.CricDB;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Match;
import models.testMatch;

/**
 *
 * @author ferdi
 */
public class editMatch extends HttpServlet {

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
        
            int matchID = Integer.parseInt(request.getParameter("matchID"));
            boolean isTest = Boolean.parseBoolean(request.getParameter("isTest"));
            //System.out.println(matchID);
            CricDB db = new CricDB();
            if (isTest) {
                testMatch match;

                match = db.gettestMatchfromID(matchID);
                if (match == null) {
                    match = new testMatch(matchID);
                }
                //System.out.println(match.getGroundName());

                request.setAttribute("match", match);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/edittest.jsp");
                dispatcher.forward(request, response);
            } else {
                Match match;

                match = db.getMatchfromID(matchID);

                if (match == null) {
                    match = new Match(matchID);
                }
                //System.out.println(match.getGroundName());

                request.setAttribute("match", match);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/edit.jsp");
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
