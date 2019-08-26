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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        try (PrintWriter out = response.getWriter()) {

            DataFetch df = new DataFetch();
            CricDB db = new CricDB();

            db.initDB();

            /*out.print("<h1>Loading IPL data");
            if (!df.loadIPLData()) {
                out.print("<h3>Error Loading IPL..Try again");
            } else {
                alertInternet(out);
                out.print("<h3>IPL Loaded successfully");
            }

            out.print("<h1>Loading ODI data");
            if (!df.loadODIData()) {
                out.print("<h3>Error Loading ODI..Try again");
            } else {
                alertInternet(out);
                out.print("<h3>ODI Loaded successfully");
            }

            out.print("<h1>Loading T20I data..");
            if (!df.loadT20IData()) {
                out.print("<h3>Error Loading T20I..Try again");
            } else {
                alertInternet(out);
                out.print("<h3>T20I Loaded successfully");
            }

            out.print("<h1>Loading BBL data..");
            if (!df.loadBBLData()) {
                out.print("<h3>Error Loading BBL..Try again");
            } else {
                alertInternet(out);
                out.print("<h3>BBL Loaded successfully");
            }

            out.print("<h1>Loading BPL data..");
            if (!df.loadBPLData()) {
                out.print("<h3>Error Loading BPL..Try again");
            } else {
                alertInternet(out);
                out.print("<h3>BPL Loaded successfully");
            }

            out.print("<h1>Loading CPL data..");
            if (!df.loadCPLData()) {
                out.print("<h3>Error Loading CPL..Try again");
            } else {
                alertInternet(out);
                out.print("<h3>CPL Loaded successfully");
            }

            out.print("<h1>Loading PSL data..");
            if (!df.loadPSLData()) {
                out.print("<h3>Error Loading PSL..Try again");
            } else {
                alertInternet(out);
                out.print("<h3>PSL Loaded successfully");
            }
*/
            out.print("<h1>Loading Test data..");
            if(!df.loadTestData()){
                out.print("<h3>Error Loading Test..Try again");
            }
            else{
                out.print("<h3>Test matches Loaded successfully");
            }
        }

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
