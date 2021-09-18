/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Database.CricDB;
import java.io.IOException;
import java.io.PrintWriter;
import DataFetch.DataFetch;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.MatchReport;
import models.MatchStatus;

/**
 *
 * @author DELL
 */
public class LoadAll extends HttpServlet {

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

            DataFetch df = new DataFetch();
            CricDB db = new CricDB();

//            db.deleteFav();
            db.initDB();
            List<MatchReport> reports = new ArrayList<>();
//            reports.add(new MatchReport("blaa", MatchStatus.UNLOADED, new Exception("blalalala")));
//            reports.add(new MatchReport("blaa", MatchStatus.UNLOADED, new Exception("blalalala")));
//            reports.add(new MatchReport("blaa", MatchStatus.UNLOADED, new Exception("blalalala")));
//            reports.add(new MatchReport("blaa", MatchStatus.UNLOADED, new Exception("blalalala")));
//            reports.add(new MatchReport("https://stats.espncricinfo.com/ci/engine/match/1216504.html", MatchStatus.UNLOADED, new Exception("bawhdoajw")));
//            reports.add(new MatchReport("blaa", MatchStatus.LOADED, new Exception("blalalala")));
//            reports.add(new MatchReport("blaa", MatchStatus.LOADED, new Exception("blalalala")));
//            reports.add(new MatchReport("blaa", MatchStatus.LOADED, new Exception("blalalala")));


            reports.addAll(df.loadData());
            reports.addAll(df.loadTestData());

            request.setAttribute("reportSize", reports.size());
            request.setAttribute("loaded", reports.stream().filter(mr -> mr.getStatus()
                                        .equals(MatchStatus.LOADED)).collect(Collectors.toList()));
            request.setAttribute("notLoaded", reports.stream().filter(mr -> mr.getStatus()
                                        .equals(MatchStatus.UNLOADED)).collect(Collectors.toList()));
            request.setAttribute("misc", reports.stream().filter(mr -> mr.getStatus()
                                        .equals(MatchStatus.N_A)).collect(Collectors.toList()));

            RequestDispatcher dispatcher = request.getRequestDispatcher("/reportPage.jsp");
            dispatcher.forward(request, response);

    }

    public void alertInternet(PrintWriter out) {
        out.println("<script type=\"text/javascript\">");
        out.println("alert('Check internet connection.');");
        out.println("</script>");
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
