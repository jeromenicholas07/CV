/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Database.CricDB;
import models.*;
import com.mysql.cj.util.StringUtils;
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

            //using the same servlet to render the form and save the value
            if (request.getParameter("submitted") != null && request.getParameter("submitted").trim().equals("true")) {
                boolean favSuccess = false, ohlSuccess = false;
                String favTeam = request.getParameter("favTeam");
                String bias = request.getParameter("bias");

                int open;
                int high;
                int low;

                open = parseInt(request.getParameter("O1_O"));
                high = parseInt(request.getParameter("O1_H"));
                low = parseInt(request.getParameter("O1_L"));
                Header inning1 = new Header(open, high, low);

                open = parseInt(request.getParameter("O2_O"));
                high = parseInt(request.getParameter("O2_H"));
                low = parseInt(request.getParameter("O2_L"));
                Header inning2 = new Header(open, high, low);

                OverallOHL O_OHL = new OverallOHL(inning1, inning2);

                favSuccess = db.updateFavourites(matchID, favTeam, bias, O_OHL);

                open = parseInt(request.getParameter("FW_O"));
                high = parseInt(request.getParameter("FW_H"));
                low = parseInt(request.getParameter("FW_L"));
                Header FW = new Header(open, high, low);

                open = parseInt(request.getParameter("FX_O"));
                high = parseInt(request.getParameter("FX_H"));
                low = parseInt(request.getParameter("FX_L"));
                Header FX = new Header(open, high, low);

                open = parseInt(request.getParameter("LX_O"));
                high = parseInt(request.getParameter("LX_H"));
                low = parseInt(request.getParameter("LX_L"));
                Header LX = new Header(open, high, low);

                open = parseInt(request.getParameter("T_O"));
                high = parseInt(request.getParameter("T_H"));
                low = parseInt(request.getParameter("T_L"));
                Header T = new Header(open, high, low);

                open = parseInt(request.getParameter("FW2_O"));
                high = parseInt(request.getParameter("FW2_H"));
                low = parseInt(request.getParameter("FW2_L"));
                Header FW2 = new Header(open, high, low);

                open = parseInt(request.getParameter("FX2_O"));
                high = parseInt(request.getParameter("FX2_H"));
                low = parseInt(request.getParameter("FX2_L"));
                Header FX2 = new Header(open, high, low);

                OHL ohlObj = new OHL(FW, FX, LX, T, FW2, FX2);

                if (ohlObj.isEmpty()) {
                    ohlSuccess = true;
                } else {
                    ohlSuccess = db.updateOHL(matchID, ohlObj);
                }

                if (favSuccess && ohlSuccess) {
                    out.println("<h2>Edit successful<br>");
                    out.println("<a href=" + redirUrl + ">< Back to DB page</a>");
                } else {
                    out.println("Edit UNSUCCESSFUL// Please try again.");
                }
            } else {
                String favTeam = null;
                String bias = null;

                String[] tempFav = db.getFavourites(matchID);
                if (tempFav != null) {
                    favTeam = tempFav[0];
                    bias = tempFav[1];
                }

                OverallOHL O_ohl = db.getOverallOHL(matchID);
                if (O_ohl != null) {
                    request.setAttribute("O_OHL", O_ohl);
                }

                OHL ohl = db.getOHL(matchID);
                if (ohl != null) {
                    request.setAttribute("OHL", ohl);
                }

                String team1 = request.getParameter("team1");
                String team2 = request.getParameter("team2");

                request.setAttribute("matchID", matchID);
                request.setAttribute("team1", team1);
                request.setAttribute("team2", team2);
                request.setAttribute("favTeam", favTeam);
                request.setAttribute("bias", bias);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/editFavourites.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    public static int parseInt(String s) {
        s = s.trim();
        if (StringUtils.isEmptyOrWhitespaceOnly(s)) {
            return 0;
        }
        double d = Double.parseDouble(s);
        return (int) d;
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
