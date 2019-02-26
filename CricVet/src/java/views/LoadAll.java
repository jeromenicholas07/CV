/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Database.CricDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Inning;
import models.Match;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author DELL
 */
public class LoadAll extends HttpServlet {
    
    CricDB db = new CricDB();
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
            

//            Loading IPL - - - - - - - - - - - - - - -- - - - - - - -- - - - -- - - -- -- --- -- - - -
            String baseUrl = "http://stats.espncricinfo.com/";
            int matchType = 117;
            List<String> matchLinks = new ArrayList<>();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            for (int y = year; y >= 2018; y--) {
                
                Document matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + y + ";trophy=117;type=season").get();
                if (matches == null && matches.getElementsByClass("data1").first() == null) {
                    continue;
                }
                Elements rows = matches.getElementsByClass("data1");

                for (int i = (rows.size() - 1); i >= 0; i--) {
                    Element m = rows.get(i);
                    Elements cols = m.getElementsByClass("data-link");

                    String matchLink = cols.last().attr("href");
                    matchLinks.add(matchLink);
                }
            }
            for (String matchLink : matchLinks) {
                String url = baseUrl + matchLink;

                Document matchPage = Jsoup.connect(url).followRedirects(true).get();
                String matchUrl = matchPage.baseUri();
                String[] splitUrl = matchUrl.split("/");

                Elements teamsTopDivision = matchPage.getElementsByClass("layout-bc");

                LocalDate matchDate = LocalDate.now();
                DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM d yyyy");

                Element matchDateElement = teamsTopDivision.select("div.cscore_info-overview").first();
                String[] parts = matchDateElement.text().split(",");
                String matchDateString = parts[parts.length - 1];
                try {
                    matchDate = LocalDate.parse(matchDateString.trim(), df);
                } catch (DateTimeParseException ex) {
                    System.out.print("<h1> error parsing date:" + matchDateString);
                    Logger.getLogger(LoadODI.class.getName()).log(Level.SEVERE, null, ex);
                }

                Elements home = teamsTopDivision.select("li.cscore_item.cscore_item--home");
                String homeTeamName = home.select("span.cscore_name.cscore_name--long").text().trim();

                String homeTeamUrl = home.select("a").attr("href");
                String[] urlParts = homeTeamUrl.split("/");
                int pos = 0;
                for (int i = 0; i < urlParts.length; i++) {
                    if (urlParts[i].equals("id")) {
                        pos = i + 1;
                        break;
                    }
                }
                String homeTeamId = urlParts[pos];  //#todo

                Elements away = teamsTopDivision.select("li.cscore_item.cscore_item--away");
                String awayTeamName = away.select("span.cscore_name.cscore_name--long").text().trim();

                String awayTeamUrl = away.select("a").attr("href");
                urlParts = awayTeamUrl.split("/");
                pos = 0;
                for (int i = 0; i < urlParts.length; i++) {
                    if (urlParts[i].equals("id")) {
                        pos = i + 1;
                        break;
                    }
                }
                String awayTeamId = urlParts[pos];  //#todo

                String battingFirst = "";
                Elements gameInfoDivision = teamsTopDivision.select("article.sub-module.game-information.pre");
                Elements detailsColumn = gameInfoDivision.first().select("div.match-detail--right");
                String tossResult = detailsColumn.get(1).text();

                if (tossResult.contains(homeTeamName)) {
                    if (tossResult.contains("bat")) {
                        battingFirst = homeTeamName;
                    }
                    else{
                        battingFirst = awayTeamName;
                    }
                }
                else{
                    if (tossResult.contains("bat")) {
                        battingFirst = awayTeamName;
                    }
                    else{
                        battingFirst = homeTeamName;
                    }
                }

                int seriesPos = 0;
                for (int i = 0; i < splitUrl.length; i++) {
                    if (splitUrl[i].equals("series")) {
                        seriesPos = i + 1;
                        break;
                    }
                }

                int eventNoPos = 0;
                for (int i = 0; i < splitUrl.length; i++) {
                    if (splitUrl[i].equals("scorecard") || splitUrl[i].equals("game")) {
                        eventNoPos = i + 1;
                        break;
                    }
                }

                String seriesNo = splitUrl[seriesPos];
                String eventNo = splitUrl[eventNoPos];

                String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                String json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                JSONObject j = new JSONObject(json);
                int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                
                Inning one = null;
                Inning two = null;
                for (int inning = 1; inning <= 2; inning++) {

                    List<JSONObject> ballList = new ArrayList<>();
                    int firstOverScore = 0;
                    int sixOverScore = 0;
                    int lastFiveOverScore = 0;
                    int firstWicketScore = -1;
                    int fourCount = 0;
                    int sixCount = 0;

                    for (int i = 1; i <= pageCount; i++) {
                        String currentPageUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/"
                                + seriesNo + "/playbyplay?contentorigin=espn&event="
                                + eventNo + "&page=" + i + "&period=" + inning + "&section=cricinfo";

                        String body = Jsoup.connect(currentPageUrl).ignoreContentType(true).execute().body();
                        JSONObject jObj = new JSONObject(body);
                        //                    out.print("<td> " + i + "(" + jObj.getJSONObject("commentary").getInt("pageIndex")+")");

                        for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                            JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                            ballList.add(jItem);
                            //                        out.print("<td>" + jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it).getJSONObject("over").getFloat("overs")  );
                            //                        out.print(" (" +jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it).getJSONObject("playType").getString("description") + ")");

                            if (jItem.getJSONObject("over").getInt("unique") == 0) {
                                firstOverScore += jItem.getInt("scoreValue");
//                                out.print("<td>" + jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it).getJSONObject("over").getFloat("overs")  );
//                                out.print(" (" +jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it).getJSONObject("playType").getString("description") + ")");
                            }
                            if (jItem.getJSONObject("over").getInt("unique") < 6) {
                                sixOverScore += jItem.getInt("scoreValue");
                            }
                            if (jItem.getJSONObject("over").getInt("unique") >= 15) {
                                lastFiveOverScore += jItem.getInt("scoreValue");
                            }

                            if (jItem.getJSONObject("playType").getInt("id") == 9 && firstWicketScore == -1) {
                                firstWicketScore = jItem.getJSONObject("innings").getInt("runs");
                            }
                            if (jItem.getJSONObject("playType").getInt("id") == 3) {
                                fourCount++;
                            }
                            if (jItem.getJSONObject("playType").getInt("id") == 4) {
                                sixCount++;
                            }
                        }
                    }
                    
                    List<String> params = new ArrayList<>();
                    params.add(String.valueOf(firstOverScore) );
                    params.add(String.valueOf(sixOverScore) );
                    params.add(String.valueOf(lastFiveOverScore) );
                    params.add(String.valueOf(firstWicketScore) );
                    params.add(String.valueOf(fourCount) );
                    params.add(String.valueOf(sixCount) );
                    if(inning == 1){
                        one = new Inning(6, params);
                    }
                    if(inning == 2){
                        two = new Inning(6, params);
                    }
                }

                String homeScore = home.select("div.cscore_score").get(0).text();
                String awayScore = away.select("div.cscore_score").get(0).text();

                Elements winner = matchPage.getElementsByClass("cscore_notes");
                String result = winner.select("span").first().text();

                String winnerTeam = "";
                if(result.contains(homeTeamName)){
                    winnerTeam = homeTeamName;
                }
                else{
                    winnerTeam = awayTeamName;
                }
                
               
                
                Elements ground = matchPage.getElementsByClass("stadium-details");
                Elements sp = ground.select("span");
                String groundName = sp.text();
                String groundLink = ground.select("a").first().attr("href");
                
//                Todo:check if match already exits...if it does...dont add bogus innings to the db below
                int inn1 = db.addInning(one);
                one.setInningId(inn1);
                
                int inn2 = db.addInning(two);
                two.setInningId(inn2);
                
                Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Date.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, winnerTeam, result, groundName, matchType);
                db.addMatch(m);
                out.print("blah");
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
