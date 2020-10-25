/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Database.CricDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Inning;
import models.Match;
import models.testMatch;

/**
 *
 * @author ferdi
 */
public class editDB extends HttpServlet {

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
            boolean isSuccess;
            boolean isTest = false;

            db.initDB();

            int matchId = Integer.parseInt(request.getParameter("matchID"));
            int matchType = Integer.parseInt(request.getParameter("matchType"));

            String homeTeam = request.getParameter("homeTeam");
            String awayTeam = request.getParameter("awayTeam");
            String matchDateString = request.getParameter("matchDate");

            String tossWinner = request.getParameter("tossWinner");
            String BCW = request.getParameter("BCW");

            String homeScore = request.getParameter("homeScore");
            String awayScore = request.getParameter("awayScore");
            String groundName = request.getParameter("groundName");

            String result = request.getParameter("result");

            if (matchType == 1) {
                isTest = true;
                String teamathome = request.getParameter("teamathome");
                String teamataway;
                if (teamathome.equals(homeTeam)) {
                    teamataway = awayTeam;
                } else {
                    teamataway = homeTeam;
                }

                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate matchDate = LocalDate.parse(matchDateString.trim(), df);

                int totalRuns11 = Integer.parseInt(request.getParameter("totalRuns11"));
                int sixes11 = Integer.parseInt(request.getParameter("sixes11"));
                int fours11 = Integer.parseInt(request.getParameter("fours11"));
                int firstWicket11 = Integer.parseInt(request.getParameter("firstWicket11"));
                int runs511 = Integer.parseInt(request.getParameter("runs511"));
                String winner11 = BCW;

                int totalRuns21 = Integer.parseInt(request.getParameter("totalRuns21"));
                int sixes21 = Integer.parseInt(request.getParameter("sixes21"));
                int fours21 = Integer.parseInt(request.getParameter("fours21"));
                int firstWicket21 = Integer.parseInt(request.getParameter("firstWicket21"));
                int runs521 = Integer.parseInt(request.getParameter("runs521"));
                String winner21 = BCW;

                int totalRuns12 = Integer.parseInt(request.getParameter("totalRuns12"));
                int sixes12 = Integer.parseInt(request.getParameter("sixes12"));
                int fours12 = Integer.parseInt(request.getParameter("fours12"));
                int firstWicket12 = Integer.parseInt(request.getParameter("firstWicket12"));
                int runs512 = Integer.parseInt(request.getParameter("runs512"));
                String winner12 = BCW;

                int totalRuns22 = Integer.parseInt(request.getParameter("totalRuns22"));
                int sixes22 = Integer.parseInt(request.getParameter("sixes22"));
                int fours22 = Integer.parseInt(request.getParameter("fours22"));
                int firstWicket22 = Integer.parseInt(request.getParameter("firstWicket22"));
                int runs522 = Integer.parseInt(request.getParameter("runs522"));
                String winner22 = BCW;

                List<String> params11 = new ArrayList<>();
                params11.add(String.valueOf(totalRuns11));
                params11.add(String.valueOf(fours11));
                params11.add(String.valueOf(sixes11));
                params11.add(String.valueOf(firstWicket11));
                params11.add(String.valueOf(runs511));
                params11.add(winner11);

                List<String> params21 = new ArrayList<>();
                params21.add(String.valueOf(totalRuns21));
                params21.add(String.valueOf(fours21));
                params21.add(String.valueOf(sixes21));
                params21.add(String.valueOf(firstWicket21));
                params21.add(String.valueOf(runs521));
                params21.add(winner21);

                List<String> params12 = new ArrayList<>();
                params12.add(String.valueOf(totalRuns12));
                params12.add(String.valueOf(fours12));
                params12.add(String.valueOf(sixes12));
                params12.add(String.valueOf(firstWicket12));
                params12.add(String.valueOf(runs512));
                params12.add(winner12);

                List<String> params22 = new ArrayList<>();
                params22.add(String.valueOf(totalRuns22));
                params22.add(String.valueOf(fours22));
                params22.add(String.valueOf(sixes22));
                params22.add(String.valueOf(firstWicket22));
                params22.add(String.valueOf(runs522));
                params22.add(winner22);

                Inning one1 = new Inning(params11);
                Inning two1 = new Inning(params21);
                Inning one2 = new Inning(params12);
                Inning two2 = new Inning(params22);

                System.out.println(one1.getParams());
                System.out.println(two1.getParams());
                System.out.println(one2.getParams());
                System.out.println(two2.getParams());

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, Date.valueOf(matchDate), tossWinner, BCW, one1, two1, one2, two2, homeScore, awayScore, result, groundName, teamathome, teamataway);

                isSuccess = db.updatetestMatch(matchId, m);

            } else {

                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                LocalDateTime matchDate = LocalDateTime.parse(matchDateString.trim(), df);

                int firstOver1 = Integer.parseInt(request.getParameter("firstOver1"));
                int first61 = Integer.parseInt(request.getParameter("first61"));
                int last51 = Integer.parseInt(request.getParameter("last51"));
                int firstWicket1 = Integer.parseInt(request.getParameter("firstWicket1"));
                int fours1 = Integer.parseInt(request.getParameter("fours1"));
                int sixes1 = Integer.parseInt(request.getParameter("sixes1"));
                int totalRuns1 = Integer.parseInt(request.getParameter("totalRuns1"));
                String winner1 = BCW;

                int firstOver2 = Integer.parseInt(request.getParameter("firstOver2"));
                int first62 = Integer.parseInt(request.getParameter("first62"));
                int last52 = Integer.parseInt(request.getParameter("last52"));
                int firstWicket2 = Integer.parseInt(request.getParameter("firstWicket2"));
                int fours2 = Integer.parseInt(request.getParameter("fours2"));
                int sixes2 = Integer.parseInt(request.getParameter("sixes2"));
                int totalRuns2 = Integer.parseInt(request.getParameter("totalRuns2"));
                String winner2 = BCW;

                List<String> params = new ArrayList<>();
                params.add(String.valueOf(firstOver1));
                params.add(String.valueOf(first61));
                params.add(String.valueOf(last51));
                params.add(String.valueOf(firstWicket1));
                params.add(String.valueOf(fours1));
                params.add(String.valueOf(sixes1));
                params.add(String.valueOf(totalRuns1));
                params.add(winner1);

                List<String> params2 = new ArrayList<>();
                params2.add(String.valueOf(firstOver2));
                params2.add(String.valueOf(first62));
                params2.add(String.valueOf(last52));
                params2.add(String.valueOf(firstWicket2));
                params2.add(String.valueOf(fours2));
                params2.add(String.valueOf(sixes2));
                params2.add(String.valueOf(totalRuns2));
                params2.add(winner2);

                Inning one = new Inning(params);
                Inning two = new Inning(params2);

                Match m = new Match(matchId, homeTeam, awayTeam, Timestamp.valueOf(matchDate), tossWinner, BCW, one, two, homeScore, awayScore, result, groundName, matchType);

                isSuccess = db.updateMatch(matchId, m);
            }

            if (isSuccess) {
                out.println("<script type=\"text/javascript\">");
                    out.println("alert('Match edited successfully!');");
                out.println("</script>");
            } else {
                out.println("<script type=\"text/javascript\">");
                    out.println("alert('Match edit FAILED!');");
                out.println("</script>");
            }

            out.print("<meta http-equiv=\"refresh\" content=\"0;url=/CricVet/editMatch?matchID="+matchId+"&isTest="+isTest+"\" />");
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
