/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.*;
import org.jsoup.select.*;
import org.jsoup.nodes.*;
import org.apache.http.client.utils.*;

/**
 *
 * @author DELL
 */
public class LoadODI extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoadODI</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadODI at " + request.getContextPath() + "</h1>");
            out.print("<table><tr><th>ODI Teams</tr>");

            Document doc = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/index.html").get();
            Element teamListContainer = doc.getElementById("recteam");
            Elements teams = teamListContainer.getElementsByClass("RecordLinks");

            for(Element e : teams) {

                String op;

                URL url = new URL(e.absUrl("href"));

                List<org.apache.http.NameValuePair> params = URLEncodedUtils.parse(url.toURI(), Charset.forName("UTF-8"));

                String id = params.get(0).getValue();

                out.print("<tr><td> <a href='/CricVet/teamInfo.jsp?id=" + id + "&team=" + e.text() + "' >" + e.text() + "</a>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } catch (URISyntaxException ex) {
            Logger.getLogger(LoadODI.class.getName()).log(Level.SEVERE, null, ex);
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
