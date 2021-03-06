/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Models.*;
import Models.Match;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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
import org.json.*;

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
            out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css\" integrity=\"sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS\" crossorigin=\"anonymous\">");
            out.println("</head>");
            out.println("<body>");

//            out.print("<table class=\"table table-striped\"><tr class=\"thead-dark\"><th>ODI Teams</tr>");
//
//            //getting page with list of ODI teams
//            Document doc = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/index.html").get();
//            Element teamListContainer = doc.getElementById("recteam");
//            Elements teams = teamListContainer.getElementsByClass("RecordLinks");//container with ODI teams
//
//            for (Element team : teams) {    //loop for every team
//
//                URL url = new URL(team.absUrl("href"));
//                List<org.apache.http.NameValuePair> params = URLEncodedUtils.parse(url.toURI(), Charset.forName("UTF-8"));
//
//                String id = params.get(0).getValue();   //#todo
//                String teamName = team.text();     //#todo
//
////                out.print("<tr><td> <a href='/CricVet/teamInfo.jsp?id=" + id + "&team=" + e.text() + "' >" + e.text() + "</a>");
//            }
//
//            out.println("</table>");
            out.print("<table class=\"table table-striped\">\n"
                    + "            <tr class=\"thead-dark\">\n"
                    + "<th colspan=\"4\" >"
                    + "<th colspan=\"6\">1st Inning  "
                    + "<th colspan=\"6\">2nd Inning  "
                    + "<th colspan=\"4\"> Result "
                    + "</tr>"
                    + "            <tr class=\"thead-dark\" >\n"
                    + "                <th>Date"
                    + "                <th>Home\n"
                    + "                <th>Away\n"
                    + "                <th>Toss\n"
                    + "                <th>First Over                \n"
                    + "                <th>10 overs\n"
                    + "                <th>Last 10 overs\n"
                    + "                <th>First wicket  <!--(After how many runs did the first wicket fall)-->\n"
                    + "                <th>Fours\n"
                    + "                <th>Sixes\n"
                    + "\n"
                    + "                <th>First Over                \n"
                    + "                <th>10 overs\n"
                    + "                <th>Last 10 overs\n"
                    + "                <th>First wicket  <!--(After how many runs did the first wicket fall)-->\n"
                    + "                <th>Fours\n"
                    + "                <th>Sixes\n"
                    + "\n"
                    + "                    <!--                <th>No. of sixes (Batting)\n"
                    + "                                    <th>No. of sixes (Bowling)-->\n"
                    + "\n"
                    + "                <th>Home Score\n"
                    + "                <th>Away Score\n"
                    + "\n"
                    + "                \n"
                    + "                <th>Win/Lose Margin\n"
                    + "                <th>Ground\n"
                    + "\n"
                    + "            </tr>");

            //load matchwise data ♣
            String baseUrl = "http://stats.espncricinfo.com/";
            List<String> matchLinks = new ArrayList<>();

            int year = Calendar.getInstance().get(Calendar.YEAR);
            for (int y = year; y >= 2019; y--) {
                Document matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?class=2;id=" + y + ";type=year").get();
                if(matches==null && matches.getElementsByClass("data1").first() == null){
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

            int count = 0;
            for (String matchLink : matchLinks) {
                count++;
                if (count == 5) {
                    break;
                }
                String url = baseUrl + matchLink;

                Document matchPage = Jsoup.connect(url).followRedirects(true).get();
                String matchUrl = matchPage.baseUri();
                String[] splitUrl = matchUrl.split("/");

                Elements teamsTopDivision = matchPage.getElementsByClass("layout-bc");

                LocalDate matchDate = LocalDate.now();
                DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM d yyyy");
                
                Element matchDateElement = teamsTopDivision.select("div.cscore_info-overview").first();
                String[] parts = matchDateElement.text().split(",");
                String matchDateString = parts[parts.length-1];
                try {
                    matchDate = LocalDate.parse(matchDateString.trim(), df);
                } catch (DateTimeParseException ex) {
                    out.print("<h1> error parsing date:" + matchDateString);
                    Logger.getLogger(LoadODI.class.getName()).log(Level.SEVERE, null, ex);
                }

                Elements home = teamsTopDivision.select("li.cscore_item.cscore_item--home");
                String homeTeamName = home.select("span.cscore_name.cscore_name--long").text();

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
                String awayTeamName = away.select("span.cscore_name.cscore_name--long").text();

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

                Elements gameInfoDivision = teamsTopDivision.select("article.sub-module.game-information.pre");
                Elements detailsColumn = gameInfoDivision.first().select("div.match-detail--right");
                String tossResult = detailsColumn.get(1).text();

                
                
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

                out.print("<tr data-href=\"" + url + "\">");
                out.print("<td> " + matchDate.format(df));
                out.print("<td>" + homeTeamName + " (" + homeTeamId + ")");
                out.print("<td>" + awayTeamName + " (" + awayTeamId + ")");
                out.print("<td>" + tossResult);

                for (int inning = 1; inning <= 2; inning++) {

                    List<JSONObject> ballList = new ArrayList<>();
                    int firstOverScore = 0;
                    int tenOverScore = 0;
                    int lastTenOverScore = 0;
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
                            if (jItem.getJSONObject("over").getInt("unique") < 10) {
                                tenOverScore += jItem.getInt("scoreValue");
                            }
                            if (jItem.getJSONObject("over").getInt("unique") >= 40 && jItem.getJSONObject("over").getInt("unique") <= 50) {
                                lastTenOverScore += jItem.getInt("scoreValue");
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

//                    JSONObject lastBall = ballList.get(ballList.size() - 1);
//                    float lastBallOverNo = lastBall.getJSONObject("over").getFloat("unique");
//                    float lastTenCounter = lastBallOverNo - 10;
//                    if (lastTenCounter <= 0) {
//                        lastTenOverScore = -1;
//                    } else {
//                        for(JSONObject jItem : ballList) {
//                            if (jItem.getJSONObject("over").getFloat("unique") >= lastTenCounter) {
//                                lastTenOverScore += jItem.getInt("scoreValue");
//                            }
//                        }
//                    }
                    out.print("<td>" + firstOverScore);
                    out.print("<td>" + tenOverScore);
                    out.print("<td>" + lastTenOverScore);
                    out.print("<td>" + firstWicketScore);
                    out.print("<td>" + fourCount);
                    out.print("<td>" + sixCount);
                    int id = ThreadLocalRandom.current().nextInt(1000, 99999);
                
                }

                String homeScore = home.select("div.cscore_score").get(0).text();
                String awayScore = away.select("div.cscore_score").get(0).text();

                Elements winner = matchPage.getElementsByClass("cscore_notes");
                String winnerName = winner.select("span").first().text();
                
                Elements ground = matchPage.getElementsByClass("stadium-details");
                Elements sp = ground.select("span");
                String groundName = sp.text();
                String groundLink = ground.select("a").first().attr("href");
                
                out.print("<td>" + homeScore);
                out.print("<td>" + awayScore);
                out.print("<td><a href=\"" + url + "\" >" + winnerName + " </a>");

                out.print("<td><a href=\"" + groundLink + "\" >" + groundName +"</a>");
                 int id = ThreadLocalRandom.current().nextInt(1000, 99999);
            String tossR;
            String winnerTeam;
           
           
            winnerTeam = winnerName.split("won",2)[0]; 
            
                    
            
            
            
            
         

//            out.println("<h1>" + matchLinks);
            out.println("</body>");
            out.println("</html>");
        }
//        catch (URISyntaxException ex) {
//            Logger.getLogger(LoadODI.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
    }
}
