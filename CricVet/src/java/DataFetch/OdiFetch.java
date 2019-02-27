/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataFetch;

import Database.CricDB;
import Models.*;
import Models.Match;
import Models.OdiInning;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import views.LoadODI;

/**
 *
 * @author DELL
 */
public class OdiFetch {
    
public static void main(String[] args) throws IOException, SQLException
{   int done =1;
     CricDB db = new CricDB();
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
                 OdiInning one = null;
                OdiInning two = null;
                
               /* if (count == 5) {
                    break;
                }*/
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
                   // out.print("<h1> error parsing date:" + matchDateString);
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
                String[] arrOfTossResult = tossResult.split(",", 2); 

                
                
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

              /*  out.print("<tr data-href=\"" + url + "\">");
                out.print("<td> " + matchDate.format(df));
                out.print("<td>" + homeTeamName + " (" + homeTeamId + ")");
                out.print("<td>" + awayTeamName + " (" + awayTeamId + ")");
                out.print("<td>" + tossResult);
                    */
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
                   /* out.print("<td>" + firstOverScore);
                    out.print("<td>" + tenOverScore);
                    out.print("<td>" + lastTenOverScore);
                    out.print("<td>" + firstWicketScore);
                    out.print("<td>" + fourCount);
                    out.print("<td>" + sixCount);*/
                    int id = ThreadLocalRandom.current().nextInt(1000, 99999);
                   if(inning==1)
                   one = new OdiInning(Integer.toString(id), firstOverScore, tenOverScore, lastTenOverScore, firstWicketScore, fourCount, sixCount);
                   else
                   two =  new OdiInning(Integer.toString(id), firstOverScore, tenOverScore, lastTenOverScore, firstWicketScore, fourCount, sixCount);
                }

                String homeScore = home.select("div.cscore_score").get(0).text();
                String awayScore = away.select("div.cscore_score").get(0).text();

                Elements winner = matchPage.getElementsByClass("cscore_notes");
                String winnerName = winner.select("span").first().text();
                
                Elements ground = matchPage.getElementsByClass("stadium-details");
                Elements sp = ground.select("span");
                String groundName = sp.text();
                String groundLink = ground.select("a").first().attr("href");
                
              /*  out.print("<td>" + homeScore);
                out.print("<td>" + awayScore);
                out.print("<td><a href=\"" + url + "\" >" + winnerName + " </a>");

                out.print("<td><a href=\"" + groundLink + "\" >" + groundName +"</a>");*/
                 int id = ThreadLocalRandom.current().nextInt(1000, 99999);
            String tossR;
            String winnerTeam;
            if(arrOfTossResult[1].equals(" elected to field first"))
                tossR = "chase";
            else
                tossR = "bat";
           
            winnerTeam = winnerName.split("won",2)[0]; 
            
                    
            
            
            
            
            OdiMatch match =  new OdiMatch( Integer.toString(id), homeTeamName, awayTeamName,  matchDate.format(df), arrOfTossResult[0], tossR, one.getInningId(), two.getInningId(), homeScore, awayScore, winnerTeam, winnerName, groundName) {
                    @Override
                    public int compareTo(Match o) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                };
            if(done != 0){
            db.addOdiInning(one);
            db.addOdiInning(two);
            db.addOdiMatch(match);
            }
    
    
            }
    
    
}


}




