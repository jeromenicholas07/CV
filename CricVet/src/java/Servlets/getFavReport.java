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
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.*;

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
    private List<String> headers5 = Arrays.asList("0/5", "0-1/4", "0-2/3", "2/2", "3/0-2", "4/0-1", "5/0");
    private List<String> headers10 = Arrays.asList("0/10", "0-1/9", "0-2/8", "0-3/7", "0-4/6", "5/5", "6/0-4", "7/0-3", "8/0-2", "9/0-1", "10/0");
    private String initialRatio = "0/0";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int matchType = parseInt(request.getParameter("tournament"));
        boolean isBatting = request.getParameter("sideSelect").equalsIgnoreCase("B");       //  B/C
        String tossWinner = request.getParameter("tossSelect");         //  W/L
        String openingOdds = request.getParameter("openingOdds");   // A/B above/below
        String locSelect = request.getParameter("locSelect");        // H/A/N home away neutral
        Location location = locSelect.equals("H") ? Location.HOME : (locSelect.equals("A") ? Location.AWAY : Location.NONE);
        
        CricDB db = new CricDB();
        List<Match> favMatches = db.getFavMatches(matchType, isBatting);
        favMatches.removeIf(m -> {
            double openVal = isBatting ? db.getOverallOHL(m.getMatchId()).getInning1().getOpen() : db.getOverallOHL(m.getMatchId()).getInning2().getOpen();
            return (openingOdds.equals("A") ? openVal >= 50 : openVal < 50);
        });
        List<Match> allFavMatches = db.getAllFavMatches(matchType);

        Overall:
        {
            // <editor-fold defaultstate="collapsed">
            Map<String, String> overall = new LinkedHashMap<>();
            overall.put("OO", initialRatio);
            overall.put("OG", initialRatio);

            for (Match currMatch : favMatches) {
                String currBCW = currMatch.getBCW();
                String favTeam = isBatting ? currMatch.getHomeTeam() : currMatch.getAwayTeam();
                String nonFavTeam = !isBatting ? currMatch.getHomeTeam() : currMatch.getAwayTeam();
                String currGround = currMatch.getGroundName();

                if (!(currBCW.equals("B") || currBCW.equals("C"))) {
                    continue;
                }

                boolean isNumerator = false;
                if ((isBatting && currBCW.equals("B")) || (!isBatting && currBCW.equals("C"))) {
                    isNumerator = true;
                }

                increment(overall, "OO", isNumerator);

                String currToss = currMatch.getTossWinner();
                if (tossWinner.equalsIgnoreCase("W")) {
                    if (!currToss.toLowerCase().contains(favTeam.toLowerCase())) {
                        continue;
                    }
                } else if (tossWinner.equalsIgnoreCase("L")) {
                    if (currToss.toLowerCase().contains(favTeam.toLowerCase())) {
                        continue;
                    }
                }

                Location currLocation = db.checkLocationOf(favTeam, nonFavTeam, currGround, matchType);
                if(!currLocation.equals(location)){
                    continue;
                }

                isNumerator = false;
                if ((isBatting && currBCW.equals("B")) || (!isBatting && currBCW.equals("C"))) {
                    isNumerator = true;
                }
                increment(overall, "OG", isNumerator);
            }

            request.setAttribute("overall", overall);
            // </editor-fold>
        }

        Head_to_head:
        {
            // <editor-fold defaultstate="collapsed">
            Map<String, String> h2h = new LinkedHashMap<>();

            initMap(h2h, 5);

            for (Match currMatch : favMatches) {
                Date currDate = currMatch.getMatchDate();
                String currBCW = currMatch.getBCW();

                if (!(currBCW.equals("B") || currBCW.equals("C"))) {
                    continue;
                }

                String favTeam = isBatting ? currMatch.getHomeTeam() : currMatch.getAwayTeam();

                List<Match> hthMatches = db.getHth(matchType, currMatch.getHomeTeam(), currMatch.getAwayTeam());
                hthMatches.removeIf(m -> (m.getMatchDate().after(currDate)));

                if (hthMatches.size() < 3) {
                    continue;
                }

                int count = 0, num = 0, den = 0;
                for (Match hthMatch : hthMatches) {
                    String homeTeam = hthMatch.getHomeTeam();
                    String awayTeam = hthMatch.getAwayTeam();
                    String bcw = hthMatch.getBCW();

                    if (bcw.equals("B") || bcw.equals("C")) {
                        if ((favTeam.equals(homeTeam) && bcw.equals("B")) || (favTeam.equals(awayTeam) && bcw.equals("C"))) {
                            num++;
                        } else {
                            den++;
                        }
                    }

                    count++;
                    if (count >= 5) {
                        break;
                    }
                }
                String header = getHeader5(num, den);

                if (header == null) {
                    continue;
                }

                boolean isNumerator = false;
                if ((isBatting && currBCW.equals("B")) || (!isBatting && currBCW.equals("C"))) {
                    isNumerator = true;
                }

                increment(h2h, header, isNumerator);
            }

            request.setAttribute("h2h", h2h);
            // </editor-fold>
        }

        Form_Guide:
        {
            // <editor-fold defaultstate="collapsed">

            Map<String, String> FG_F = new LinkedHashMap<>();
            Map<String, String> FG_NF = new LinkedHashMap<>();
            Map<String, String> FG_O = new LinkedHashMap<>();

            initMap(FG_F, 5);
            initMap(FG_NF, 5);
            initMap(FG_O, 10);

            for (Match currMatch : favMatches) {
                Date currDate = currMatch.getMatchDate();
                String currBCW = currMatch.getBCW();

                if (!(currBCW.equals("B") || currBCW.equals("C"))) {
                    continue;
                }

                String favTeam = isBatting ? currMatch.getHomeTeam() : currMatch.getAwayTeam();
                String nonFavTeam = !isBatting ? currMatch.getHomeTeam() : currMatch.getAwayTeam();

                List<Match> ffMatches = db.getDB(matchType, favTeam);
                ffMatches.removeIf(m -> (m.getMatchDate().after(currDate)));

                List<Match> nfMatches = db.getDB(matchType, nonFavTeam);
                nfMatches.removeIf(m -> (m.getMatchDate().after(currDate)));

                if (ffMatches.size() < 3 || nfMatches.size() < 3) {
                    continue;
                }

                int oNum = 0, oDen = 0;

                int count = 0, num = 0, den = 0;
                for (Match match : ffMatches) {
                    String homeTeam = match.getHomeTeam();
                    String awayTeam = match.getAwayTeam();
                    String bcw = match.getBCW();

                    if (bcw.equals("B") || bcw.equals("C")) {
                        if ((favTeam.equals(homeTeam) && bcw.equals("B")) || (favTeam.equals(awayTeam) && bcw.equals("C"))) {
                            num++;
                        } else {
                            den++;
                        }
                    }

                    count++;
                    if (count >= 5) {
                        break;
                    }
                }
                oNum = num;
                oDen = den;
                String header = getHeader5(num, den);

                if (header != null) {
                    boolean isNumerator = false;
                    if ((isBatting && currBCW.equals("B")) || (!isBatting && currBCW.equals("C"))) {
                        isNumerator = true;
                    }

                    increment(FG_F, header, isNumerator);
                }

                count = 0;
                num = 0;
                den = 0;
                for (Match match : nfMatches) {
                    String homeTeam = match.getHomeTeam();
                    String awayTeam = match.getAwayTeam();
                    String bcw = match.getBCW();

                    if (bcw.equals("B") || bcw.equals("C")) {
                        if ((nonFavTeam.equals(homeTeam) && bcw.equals("B")) || (nonFavTeam.equals(awayTeam) && bcw.equals("C"))) {
                            num++;
                        } else {
                            den++;
                        }
                    }

                    count++;
                    if (count >= 5) {
                        break;
                    }
                }
                oNum += den;
                oDen += num;
                header = getHeader5(num, den);

                if (header != null) {
                    boolean isNumerator = false;
                    if ((isBatting && currBCW.equals("C")) || (!isBatting && currBCW.equals("B"))) {
                        isNumerator = true;
                    }

                    increment(FG_NF, header, isNumerator);
                }

                header = getHeader10(oNum, oDen);

                if (header != null) {
                    boolean isNumerator = false;
                    if ((isBatting && currBCW.equals("B")) || (!isBatting && currBCW.equals("C"))) {
                        isNumerator = true;
                    }

                    increment(FG_O, header, isNumerator);
                }

            }
            request.setAttribute("FG_F", FG_F);
            request.setAttribute("FG_NF", FG_NF);
            request.setAttribute("FG_O", FG_O);

            // </editor-fold>
        }

        BCW:
        {
            // <editor-fold defaultstate="collapsed">

            Map<String, String> BC_F = new LinkedHashMap<>();
            Map<String, String> BC_NF = new LinkedHashMap<>();
            Map<String, String> BC_G = new LinkedHashMap<>();
            Map<String, String> BC_O = new LinkedHashMap<>();

            initMap(BC_F, 5);
            initMap(BC_NF, 5);
            initMap(BC_G, 5);
            initMap(BC_O, 10);

            for (Match currMatch : favMatches) {
                Date currDate = currMatch.getMatchDate();
                String currBCW = currMatch.getBCW();

                if (!(currBCW.equals("B") || currBCW.equals("C"))) {
                    continue;
                }

                String favTeam = isBatting ? currMatch.getHomeTeam() : currMatch.getAwayTeam();
                String nonFavTeam = !isBatting ? currMatch.getHomeTeam() : currMatch.getAwayTeam();

                List<Match> ffMatches = db.getMatches(favTeam, matchType, isBatting ? 1 : 2);
                ffMatches.removeIf(m -> (m.getMatchDate().after(currDate)));
                ffMatches.removeIf(m -> m.getBCW().contains("--"));

                List<Match> nfMatches = db.getMatches(nonFavTeam, matchType, !isBatting ? 1 : 2);
                nfMatches.removeIf(m -> (m.getMatchDate().after(currDate)));
                nfMatches.removeIf(m -> m.getBCW().contains("--"));

                String groundName = currMatch.getGroundName();
                List<Match> gMatches = db.getGroundInfo(groundName, matchType);
                gMatches.removeIf(m -> (m.getMatchDate().after(currDate)));
                gMatches.removeIf(m -> m.getBCW().contains("--"));

                if (ffMatches.size() < 3 || nfMatches.size() < 3) {
                    continue;
                }

                int oNum = 0, oDen = 0;

                int count = 0, num = 0, den = 0;
                for (Match match : ffMatches) {
                    String bcw = match.getBCW();

                    if (bcw.equals("B")) {
                        num++;
                    } else if (bcw.equals("C")) {
                        den++;
                    }

                    count++;
                    if (count >= 5) {
                        break;
                    }
                }
                oNum = num;
                oDen = den;
                String header = getHeader5(num, den);

                if (header != null) {
                    boolean isNumerator = false;
                    if ((isBatting && currBCW.equals("B")) || (!isBatting && currBCW.equals("C"))) {
                        isNumerator = true;
                    }

                    increment(BC_F, header, isNumerator);
                }

                count = 0;
                num = 0;
                den = 0;
                for (Match match : nfMatches) {
                    String bcw = match.getBCW();

                    if (bcw.equals("B")) {
                        num++;
                    } else if (bcw.equals("C")) {
                        den++;
                    }

                    count++;
                    if (count >= 5) {
                        break;
                    }
                }
                oNum += num;
                oDen += den;
                header = getHeader5(num, den);

                if (header != null) {
                    boolean isNumerator = false;
                    if ((isBatting && currBCW.equals("C")) || (!isBatting && currBCW.equals("B"))) {
                        isNumerator = true;
                    }

                    increment(BC_NF, header, isNumerator);
                }

                header = getHeader10(oNum, oDen);

                if (header != null) {
                    boolean isNumerator = false;
                    if ((isBatting && currBCW.equals("B")) || (!isBatting && currBCW.equals("C"))) {
                        isNumerator = true;
                    }

                    increment(BC_O, header, isNumerator);
                }

                count = 0;
                num = 0;
                den = 0;
                for (Match match : gMatches) {
                    String bcw = match.getBCW();

                    if (bcw.equals("B")) {
                        num++;
                    } else if (bcw.equals("C")) {
                        den++;
                    }

                    count++;
                    if (count >= 5) {
                        break;
                    }
                }
                header = getHeader5(num, den);

                if (header != null) {
                    boolean isNumerator = false;
                    if ((isBatting && currBCW.equals("B")) || (!isBatting && currBCW.equals("C"))) {
                        isNumerator = true;
                    }

                    increment(BC_G, header, isNumerator);
                }

            }
            request.setAttribute("BC_F", BC_F);
            request.setAttribute("BC_NF", BC_NF);
            request.setAttribute("BC_G", BC_G);
            request.setAttribute("BC_O", BC_O);

            // </editor-fold>
        }
//        MasterStudy:
//        {
//            int num1 = 0, den1 = 0;
//            int num2 = 0, den2 = 0;
//            for (Match m : allFavMatches) {
//                String favTeam = db.getFavourites(m.getMatchId());
//                if (favTeam.equals(m.getHomeTeam())) {
//                    if (m.getBCW().equals("B")) {
//                        num1++;
//                    } else {
//                        den1++;
//                    }
//                } else {
//                    if (m.getBCW().equals("C")) {
//                        num2++;
//                    } else {
//                        den2++;
//                    }
//                }
//
//            }
//            request.setAttribute("masterBatting", num1 + "/" + den1);
//            request.setAttribute("masterChasing", num2 + "/" + den2);
//        }
//        TossWon:
//        {
//            int num1 = 0, den1 = 0;
//            int num2 = 0, den2 = 0;
//            for (Match m : allFavMatches) {
//                String favTeam = db.getFavourites(m.getMatchId());
//                if (favTeam.equals(m.getHomeTeam()) && m.isHomeTeamTossWinner()) {
//                    if (m.getBCW().equals("B")) {
//                        num1++;
//                    } else {
//                        den1++;
//                    }
//                } else if (favTeam.equals(m.getAwayTeam()) && !m.isHomeTeamTossWinner()) {
//                    if (m.getBCW().equals("C")) {
//                        num2++;
//                    } else {
//                        den2++;
//                    }
//                }
//            }
//            request.setAttribute("tossWB", num1 + "/" + den1);
//            request.setAttribute("tossWC", num2 + "/" + den2);
//        }
//        
//        TossLost:
//        {
//            int num1 = 0, den1 = 0;
//            int num2 = 0, den2 = 0;
//            for (Match m : allFavMatches) {
//                String favTeam = db.getFavourites(m.getMatchId());
//                if (favTeam.equals(m.getHomeTeam()) && !m.isHomeTeamTossWinner()) {
//                    if (m.getBCW().equals("B")) {
//                        num1++;
//                    } else {
//                        den1++;
//                    }
//                } else if (favTeam.equals(m.getAwayTeam()) && m.isHomeTeamTossWinner()) {
//                    if (m.getBCW().equals("C")) {
//                        num2++;
//                    } else {
//                        den2++;
//                    }
//                }
//            }
//            request.setAttribute("tossLB", num1 + "/" + den1);
//            request.setAttribute("tossLC", num2 + "/" + den2);
//        }
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

    private void initMap(Map<String, String> map, int max) {
        if (max == 5) {
            headers5.forEach(header -> map.put(header, initialRatio));
        } else if (max == 10) {
            headers10.forEach(header -> map.put(header, initialRatio));
        }
    }

    private void increment(Map<String, String> btMap, String header, boolean isNumerator) {
        String val = btMap.get(header);
        String[] splits = val.split("/");

        if (splits.length != 2) {
            throw new Error("length should be 2 like : 0/0");
        }

        int num = parseInt(splits[0]);
        int den = parseInt(splits[1]);

        if (isNumerator) {
            num++;
        } else {
            den++;
        }

        btMap.put(header, num + "/" + den);
    }

    private boolean isInBetween(int val, int min, int max) {
        return val >= min && val <= max;
    }

    private String getHeader5(int num, int den) {
        String header = null;
        if ((num == 0 && den == 5) || (num == 5 && den == 0) || (num == 2 && den == 2)) {
            header = num + "/" + den;
        } else if (isInBetween(num, 0, 1) && den == 4) {
            header = "0-1/4";
        } else if (isInBetween(num, 0, 2) && den == 3) {
            header = "0-2/3";
        } else if (num == 3 && isInBetween(den, 0, 2)) {
            header = "3/0-2";
        } else if (num == 4 && isInBetween(den, 0, 1)) {
            header = "4/0-1";
        }
        return header;
    }

    private String getHeader10(int num, int den) {
        String header = null;
        if ((num == 0 && den == 10) || (num == 5 && den == 5) || (num == 10 && den == 0)) {
            header = num + "/" + den;
        } else if (isInBetween(num, 0, 1) && den == 9) {
            header = "0-1/9";
        } else if (isInBetween(num, 0, 2) && den == 8) {
            header = "0-2/8";
        } else if (isInBetween(num, 0, 3) && den == 7) {
            header = "0-3/7";
        } else if (isInBetween(num, 0, 4) && den == 6) {
            header = "0-4/6";
        } else if (num == 6 && isInBetween(den, 0, 4)) {
            header = "6/0-4";
        } else if (num == 7 && isInBetween(den, 0, 3)) {
            header = "7/0-3";
        } else if (num == 8 && isInBetween(den, 0, 2)) {
            header = "8/0-2";
        } else if (num == 9 && isInBetween(den, 0, 1)) {
            header = "9/0-1";
        }
        return header;
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
