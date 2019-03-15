/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataFetch;

import Database.CricDB;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Inning;
import models.Match;
import org.json.JSONException;
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
public class DataFetch {

    int yr = 2015;

    CricDB db = new CricDB();

    public boolean loadIPLData() {

        String baseUrl = "http://stats.espncricinfo.com/";
        int matchType = 117;
        List<String> matchLinks = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = year; y >= yr; y--) {

            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + y + ";trophy=117;type=season").get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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

            int mPos = 0;
            String[] mParts = matchLink.split("/");
            for (int i = 0; i < mParts.length; i++) {
                if (mParts[i].equals("match")) {
                    mPos = i + 1;
                    break;
                }
            }
            String mid = mParts[mPos];
            int mId = Integer.parseInt(mid.substring(0, mid.length() - 5));

            if (db.checkMatchEntry(mId)) {
                System.out.println("Match " + mId + " exists");
                continue;
            }

            String url = baseUrl + matchLink;

            Document matchPage;
            try {
                matchPage = Jsoup.connect(url).followRedirects(true).get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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
            if (matchDate.equals(LocalDate.now().minusDays(1))) {
                System.out.println("Match " + mId + " today.");
                continue;
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

            String homeScore = home.select("div.cscore_score").get(0).text();
            String awayScore = away.select("div.cscore_score").get(0).text();

            Element liveOrNot = teamsTopDivision.select("span.cscore_time").first();
            if (liveOrNot.text().equals("Live") || homeScore.contains("*") || awayScore.contains("*")) {
                continue;
            }

            Elements gameInfoDivision = teamsTopDivision.select("article.sub-module.game-information.pre");
            Elements detailsColumn = gameInfoDivision.first().select("div.match-detail--right");
            String tossResult = detailsColumn.get(1).text();

            String battingFirst;
            if (tossResult.contains(homeTeamName)) {
                if (tossResult.contains("bat")) {
                    battingFirst = homeTeamName;
                } else {
                    battingFirst = awayTeamName;
                }
            } else {
                if (tossResult.contains("bat")) {
                    battingFirst = awayTeamName;
                } else {
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

            Elements winner = matchPage.getElementsByClass("cscore_notes");
            String result = winner.select("span").first().text();

            String BorC = "";
            if (result.contains(" wickets")) {
                BorC = "C";
            } else if (result.contains(" runs")) {
                BorC = "B";
            } else {
                BorC = "-";
            }

            Inning one = null;
            Inning two = null;
            for (int inning = 1; inning <= 2; inning++) {
                String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                String json;
                try {
                    json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                } catch (IOException ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
                JSONObject j = new JSONObject(json);
                int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                List<JSONObject> ballList = new ArrayList<>();
                int firstOverScore = 0;
                int sixOverScore = 0;
                int lastFiveOverScore = -1;
                int firstWicketScore = -1;
                int fourCount = 0;
                int sixCount = 0;
                int totalRuns = 0;

                for (int i = 1; i <= pageCount; i++) {
                    String currentPageUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/"
                            + seriesNo + "/playbyplay?contentorigin=espn&event="
                            + eventNo + "&page=" + i + "&period=" + inning + "&section=cricinfo";

                    String body;
                    try {
                        body = Jsoup.connect(currentPageUrl).ignoreContentType(true).execute().body();
                    } catch (IOException ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }

                    JSONObject jObj = new JSONObject();
                    for (int x = 0; x < 7; x++) {
                        try {
                            jObj = new JSONObject(body);
                            break;
                        } catch (JSONException je) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            je.printStackTrace();
                            
                            if(x == 6){
                                return false;
                            }
                        }
                    }
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
                        totalRuns += jItem.getInt("scoreValue");
                    }
                }
                if (lastFiveOverScore != -1) {
                    lastFiveOverScore++;
                }

                List<String> params = new ArrayList<>();
                params.add(String.valueOf(firstOverScore));
                params.add(String.valueOf(sixOverScore));
                params.add(String.valueOf(lastFiveOverScore));
                params.add(String.valueOf(firstWicketScore));
                params.add(String.valueOf(fourCount));
                params.add(String.valueOf(sixCount));
                params.add(String.valueOf(totalRuns));
                params.add(BorC);

                if (inning == 1) {
                    one = new Inning(8, params);
                }
                if (inning == 2) {
                    two = new Inning(8, params);
                }
            }

            Elements ground = matchPage.getElementsByClass("stadium-details");
            Elements sp = ground.select("span");
            String groundName = sp.text();
            String groundLink = ground.select("a").first().attr("href");

            Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Date.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
            db.addMatch(m);
        }
        return true;
    }

    public boolean loadODIData() {
        int matchType = 2;
        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = year; y >= yr; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?class=2;id=" + y + ";type=year").get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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

            int mPos = 0;
            String[] mParts = matchLink.split("/");
            for (int i = 0; i < mParts.length; i++) {
                if (mParts[i].equals("match")) {
                    mPos = i + 1;
                    break;
                }
            }
            String mid = mParts[mPos];
            int mId = Integer.parseInt(mid.substring(0, mid.length() - 5));

            if (db.checkMatchEntry(mId)) {
                System.out.println("Match " + mId + " exists");
                continue;
            }

            String url = baseUrl + matchLink;

            Document matchPage;
            try {
                matchPage = Jsoup.connect(url).followRedirects(true).get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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

                Logger.getLogger(LoadODI.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (matchDate.equals(LocalDate.now().minusDays(1))) {
                System.out.println("Match " + mId + " today.");
                continue;
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

            String homeScore = home.select("div.cscore_score").get(0).text();
            String awayScore = away.select("div.cscore_score").get(0).text();

            Element liveOrNot = teamsTopDivision.select("span.cscore_time").first();
            if (liveOrNot.text().equals("Live") || homeScore.contains("*") || awayScore.contains("*")) {
                continue;
            }

            Elements gameInfoDivision = teamsTopDivision.select("article.sub-module.game-information.pre");
            Elements detailsColumn = gameInfoDivision.first().select("div.match-detail--right");
            String tossResult = detailsColumn.get(1).text();

            String battingFirst;
            if (tossResult.contains(homeTeamName)) {
                if (tossResult.contains("bat")) {
                    battingFirst = homeTeamName;
                } else {
                    battingFirst = awayTeamName;
                }
            } else {
                if (tossResult.contains("bat")) {
                    battingFirst = awayTeamName;
                } else {
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

            Elements winner = matchPage.getElementsByClass("cscore_notes");
            String result = winner.select("span").first().text();

            String BorC = "";
            if (result.contains(" wickets")) {
                BorC = "C";
            } else if (result.contains(" runs")) {
                BorC = "B";
            } else {
                BorC = "-";
            }

            Inning one = null;
            Inning two = null;
            for (int inning = 1; inning <= 2; inning++) {
                String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=" + inning + "&section=cricinfo";

                String json;
                try {
                    json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                } catch (IOException ex) {
                    System.out.println(commentaryUrl);
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
                System.out.println(commentaryUrl);

                JSONObject j = new JSONObject(json);
//                while (true) {
//                    int
//                    try {
//                        j = new JSONObject(json);
//                        break;
//                    } catch (JSONException je) {
//
//                        je.printStackTrace();
//                    }
//                }
                int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                List<JSONObject> ballList = new ArrayList<>();
                int firstOverScore = 0;
                int tenOverScore = 0;
                int lastTenOverScore = -1;
                int firstWicketScore = -1;
                int fourCount = 0;
                int sixCount = 0;
                int totalRuns = 0;

                for (int i = 1; i <= pageCount; i++) {
                    String currentPageUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/"
                            + seriesNo + "/playbyplay?contentorigin=espn&event="
                            + eventNo + "&page=" + i + "&period=" + inning + "&section=cricinfo";

                    String body;
                    try {
                        body = Jsoup.connect(currentPageUrl).ignoreContentType(true).execute().body();
                    } catch (IOException ex) {
                        System.out.println(currentPageUrl);
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                    System.out.println(currentPageUrl);
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

                        totalRuns += jItem.getInt("scoreValue");
                    }
                }
                if (lastTenOverScore != -1) {
                    lastTenOverScore++;
                }

                List<String> params = new ArrayList<>();
                params.add(String.valueOf(firstOverScore));
                params.add(String.valueOf(tenOverScore));
                params.add(String.valueOf(lastTenOverScore));
                params.add(String.valueOf(firstWicketScore));
                params.add(String.valueOf(fourCount));
                params.add(String.valueOf(sixCount));
                params.add(String.valueOf(totalRuns));
                params.add(BorC);

                if (inning == 1) {
                    one = new Inning(8, params);
                }
                if (inning == 2) {
                    two = new Inning(8, params);
                }
            }

            Elements ground = matchPage.getElementsByClass("stadium-details");
            Elements sp = ground.select("span");
            String groundName = sp.text();
            String groundLink = ground.select("a").first().attr("href");

            Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Date.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
            db.addMatch(m);
        }

        return true;
    }

    public boolean loadBBLData() {
        int matchType = 158;
        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = year; y >= yr; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + (y - 1) + "%2F" + (y % 100) + ";trophy=158;type=season").get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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

            int mPos = 0;
            String[] mParts = matchLink.split("/");
            for (int i = 0; i < mParts.length; i++) {
                if (mParts[i].equals("match")) {
                    mPos = i + 1;
                    break;
                }
            }
            String mid = mParts[mPos];
            int mId = Integer.parseInt(mid.substring(0, mid.length() - 5));

            if (db.checkMatchEntry(mId)) {
                System.out.println("Match " + mId + " exists");
                continue;
            }

            String url = baseUrl + matchLink;

            Document matchPage;
            try {
                matchPage = Jsoup.connect(url).followRedirects(true).get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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

            if (matchDate.equals(LocalDate.now().minusDays(1))) {
                System.out.println("Match " + mId + " today.");
                continue;
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

            String homeScore = home.select("div.cscore_score").get(0).text();
            String awayScore = away.select("div.cscore_score").get(0).text();

            Element liveOrNot = teamsTopDivision.select("span.cscore_time").first();
            if (liveOrNot.text().equals("Live") || homeScore.contains("*") || awayScore.contains("*")) {
                continue;
            }

            Elements gameInfoDivision = teamsTopDivision.select("article.sub-module.game-information.pre");
            Elements detailsColumn = gameInfoDivision.first().select("div.match-detail--right");
            String tossResult = detailsColumn.get(1).text();

            String battingFirst;
            if (tossResult.contains(homeTeamName)) {
                if (tossResult.contains("bat")) {
                    battingFirst = homeTeamName;
                } else {
                    battingFirst = awayTeamName;
                }
            } else {
                if (tossResult.contains("bat")) {
                    battingFirst = awayTeamName;
                } else {
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

            Elements winner = matchPage.getElementsByClass("cscore_notes");
            String result = winner.select("span").first().text();

            String BorC = "";
            if (result.contains(" wickets")) {
                BorC = "C";
            } else if (result.contains(" runs")) {
                BorC = "B";
            } else {
                BorC = "-";
            }

            Inning one = null;
            Inning two = null;
            for (int inning = 1; inning <= 2; inning++) {
                String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                String json;
                try {
                    json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                } catch (IOException ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
                JSONObject j = new JSONObject(json);
                int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                List<JSONObject> ballList = new ArrayList<>();
                int firstOverScore = 0;
                int sixOverScore = 0;
                int lastFiveOverScore = -1;
                int firstWicketScore = -1;
                int fourCount = 0;
                int sixCount = 0;
                int totalRuns = 0;

                for (int i = 1; i <= pageCount; i++) {
                    String currentPageUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/"
                            + seriesNo + "/playbyplay?contentorigin=espn&event="
                            + eventNo + "&page=" + i + "&period=" + inning + "&section=cricinfo";

                    String body;
                    try {
                        body = Jsoup.connect(currentPageUrl).ignoreContentType(true).execute().body();
                    } catch (IOException ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                    JSONObject jObj = new JSONObject(body);

                    for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                        JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                        ballList.add(jItem);

                        if (jItem.getJSONObject("over").getInt("unique") == 0) {
                            firstOverScore += jItem.getInt("scoreValue");
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

                        totalRuns += jItem.getInt("scoreValue");
                    }
                }
                if (lastFiveOverScore != -1) {
                    lastFiveOverScore++;
                }

                List<String> params = new ArrayList<>();
                params.add(String.valueOf(firstOverScore));
                params.add(String.valueOf(sixOverScore));
                params.add(String.valueOf(lastFiveOverScore));
                params.add(String.valueOf(firstWicketScore));
                params.add(String.valueOf(fourCount));
                params.add(String.valueOf(sixCount));
                params.add(String.valueOf(totalRuns));
                params.add(BorC);

                if (inning == 1) {
                    one = new Inning(8, params);
                }
                if (inning == 2) {
                    two = new Inning(8, params);
                }
            }

            Elements ground = matchPage.getElementsByClass("stadium-details");
            Elements sp = ground.select("span");
            String groundName = sp.text();
            String groundLink = ground.select("a").first().attr("href");

            Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Date.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
            db.addMatch(m);
        }

        return true;
    }

    public boolean loadBPLData() {
        int matchType = 159;
        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = year; y >= yr; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + (y - 1) + "%2F" + (y % 100) + ";trophy=159;type=season").get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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
            int mPos = 0;
            String[] mParts = matchLink.split("/");
            for (int i = 0; i < mParts.length; i++) {
                if (mParts[i].equals("match")) {
                    mPos = i + 1;
                    break;
                }
            }
            String mid = mParts[mPos];
            int mId = Integer.parseInt(mid.substring(0, mid.length() - 5));

            if (db.checkMatchEntry(mId)) {
                System.out.println("Match " + mId + " exists");
                continue;
            }

            String url = baseUrl + matchLink;

            Document matchPage;
            try {
                matchPage = Jsoup.connect(url).followRedirects(true).get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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

            if (matchDate.equals(LocalDate.now().minusDays(1))) {
                System.out.println("Match " + mId + " today.");
                continue;
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

            String homeScore = home.select("div.cscore_score").get(0).text();
            String awayScore = away.select("div.cscore_score").get(0).text();

            Element liveOrNot = teamsTopDivision.select("span.cscore_time").first();
            if (liveOrNot.text().equals("Live") || homeScore.contains("*") || awayScore.contains("*")) {
                continue;
            }

            Elements gameInfoDivision = teamsTopDivision.select("article.sub-module.game-information.pre");
            Elements detailsColumn = gameInfoDivision.first().select("div.match-detail--right");
            String tossResult = detailsColumn.get(1).text();

            String battingFirst;
            if (tossResult.contains(homeTeamName)) {
                if (tossResult.contains("bat")) {
                    battingFirst = homeTeamName;
                } else {
                    battingFirst = awayTeamName;
                }
            } else {
                if (tossResult.contains("bat")) {
                    battingFirst = awayTeamName;
                } else {
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

            Elements winner = matchPage.getElementsByClass("cscore_notes");
            String result = winner.select("span").first().text();

            String BorC = "";
            if (result.contains(" wickets")) {
                BorC = "C";
            } else if (result.contains(" runs")) {
                BorC = "B";
            } else {
                BorC = "-";
            }

            Inning one = null;
            Inning two = null;
            for (int inning = 1; inning <= 2; inning++) {
                String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                String json;
                try {
                    json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                } catch (IOException ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
                JSONObject j = new JSONObject(json);
                int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                List<JSONObject> ballList = new ArrayList<>();
                int firstOverScore = 0;
                int sixOverScore = 0;
                int lastFiveOverScore = -1;
                int firstWicketScore = -1;
                int fourCount = 0;
                int sixCount = 0;
                int totalRuns = 0;

                for (int i = 1; i <= pageCount; i++) {
                    String currentPageUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/"
                            + seriesNo + "/playbyplay?contentorigin=espn&event="
                            + eventNo + "&page=" + i + "&period=" + inning + "&section=cricinfo";

                    String body;
                    try {
                        body = Jsoup.connect(currentPageUrl).ignoreContentType(true).execute().body();
                    } catch (IOException ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                    JSONObject jObj = new JSONObject(body);

                    for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                        JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                        ballList.add(jItem);

                        if (jItem.getJSONObject("over").getInt("unique") == 0) {
                            firstOverScore += jItem.getInt("scoreValue");
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
                        totalRuns += jItem.getInt("scoreValue");
                    }
                }
                if (lastFiveOverScore != -1) {
                    lastFiveOverScore++;
                }

                List<String> params = new ArrayList<>();
                params.add(String.valueOf(firstOverScore));
                params.add(String.valueOf(sixOverScore));
                params.add(String.valueOf(lastFiveOverScore));
                params.add(String.valueOf(firstWicketScore));
                params.add(String.valueOf(fourCount));
                params.add(String.valueOf(sixCount));
                params.add(String.valueOf(totalRuns));
                params.add(BorC);

                if (inning == 1) {
                    one = new Inning(8, params);
                }
                if (inning == 2) {
                    two = new Inning(8, params);
                }
            }

            Elements ground = matchPage.getElementsByClass("stadium-details");
            Elements sp = ground.select("span");
            String groundName = sp.text();
            String groundLink = ground.select("a").first().attr("href");

            Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Date.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
            db.addMatch(m);
        }

        return true;
    }

    public boolean loadCPLData() {

        int matchType = 748;
        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = year; y >= yr; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + y + ";trophy=748;type=season").get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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

            int mPos = 0;
            String[] mParts = matchLink.split("/");
            for (int i = 0; i < mParts.length; i++) {
                if (mParts[i].equals("match")) {
                    mPos = i + 1;
                    break;
                }
            }
            String mid = mParts[mPos];
            int mId = Integer.parseInt(mid.substring(0, mid.length() - 5));

            if (db.checkMatchEntry(mId)) {
                System.out.println("Match " + mId + " exists");
                continue;
            }

            String url = baseUrl + matchLink;

            Document matchPage;
            try {
                matchPage = Jsoup.connect(url).followRedirects(true).get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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

            if (matchDate.equals(LocalDate.now().minusDays(1))) {
                System.out.println("Match " + mId + " today.");
                continue;
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

            String homeScore = home.select("div.cscore_score").get(0).text();
            String awayScore = away.select("div.cscore_score").get(0).text();

            Element liveOrNot = teamsTopDivision.select("span.cscore_time").first();
            if (liveOrNot.text().equals("Live") || homeScore.contains("*") || awayScore.contains("*")) {
                continue;
            }

            Elements gameInfoDivision = teamsTopDivision.select("article.sub-module.game-information.pre");
            Elements detailsColumn = gameInfoDivision.first().select("div.match-detail--right");
            String tossResult = detailsColumn.get(1).text();

            String battingFirst;
            if (tossResult.contains(homeTeamName)) {
                if (tossResult.contains("bat")) {
                    battingFirst = homeTeamName;
                } else {
                    battingFirst = awayTeamName;
                }
            } else {
                if (tossResult.contains("bat")) {
                    battingFirst = awayTeamName;
                } else {
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

            Elements winner = matchPage.getElementsByClass("cscore_notes");
            String result = winner.select("span").first().text();

            String BorC = "";
            if (result.contains(" wickets")) {
                BorC = "C";
            } else if (result.contains(" runs")) {
                BorC = "B";
            } else {
                BorC = "-";
            }

            Inning one = null;
            Inning two = null;
            for (int inning = 1; inning <= 2; inning++) {
                String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                String json;
                try {
                    json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                } catch (IOException ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
                JSONObject j = new JSONObject(json);
                int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                List<JSONObject> ballList = new ArrayList<>();
                int firstOverScore = 0;
                int sixOverScore = 0;
                int lastFiveOverScore = -1;
                int firstWicketScore = -1;
                int fourCount = 0;
                int sixCount = 0;
                int totalRuns = 0;

                for (int i = 1; i <= pageCount; i++) {
                    String currentPageUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/"
                            + seriesNo + "/playbyplay?contentorigin=espn&event="
                            + eventNo + "&page=" + i + "&period=" + inning + "&section=cricinfo";

                    String body;
                    try {
                        body = Jsoup.connect(currentPageUrl).ignoreContentType(true).execute().body();
                    } catch (IOException ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                    JSONObject jObj = new JSONObject(body);

                    for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                        JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                        ballList.add(jItem);

                        if (jItem.getJSONObject("over").getInt("unique") == 0) {
                            firstOverScore += jItem.getInt("scoreValue");
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
                        totalRuns += jItem.getInt("scoreValue");
                    }
                }
                if (lastFiveOverScore != -1) {
                    lastFiveOverScore++;
                }

                List<String> params = new ArrayList<>();
                params.add(String.valueOf(firstOverScore));
                params.add(String.valueOf(sixOverScore));
                params.add(String.valueOf(lastFiveOverScore));
                params.add(String.valueOf(firstWicketScore));
                params.add(String.valueOf(fourCount));
                params.add(String.valueOf(sixCount));
                params.add(String.valueOf(totalRuns));
                params.add(BorC);

                if (inning == 1) {
                    one = new Inning(8, params);
                }
                if (inning == 2) {
                    two = new Inning(8, params);
                }

            }

            Elements ground = matchPage.getElementsByClass("stadium-details");
            Elements sp = ground.select("span");
            String groundName = sp.text();
            String groundLink = ground.select("a").first().attr("href");

            Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Date.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
            db.addMatch(m);

        }
        return true;
    }

    public boolean loadPSLData() {
        int matchType = 205;

        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = year; y >= yr; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + (y - 1) + "%2F" + (y % 100) + ";trophy=205;type=season").get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

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
            int mPos = 0;
            String[] mParts = matchLink.split("/");
            for (int i = 0; i < mParts.length; i++) {
                if (mParts[i].equals("match")) {
                    mPos = i + 1;
                    break;
                }
            }
            String mid = mParts[mPos];
            int mId = Integer.parseInt(mid.substring(0, mid.length() - 5));

            if (db.checkMatchEntry(mId)) {
                System.out.println("Match " + mId + " exists");
                continue;
            }

            String url = baseUrl + matchLink;

            Document matchPage;
            try {
                matchPage = Jsoup.connect(url).followRedirects(true).get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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

            if (matchDate.equals(LocalDate.now().minusDays(1))) {
                System.out.println("Match " + mId + " too recent.");
                continue;
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

            String homeScore = home.select("div.cscore_score").get(0).text();
            String awayScore = away.select("div.cscore_score").get(0).text();

            Element liveOrNot = teamsTopDivision.select("span.cscore_time").first();
            if (liveOrNot.text().equals("Live") || homeScore.contains("*") || awayScore.contains("*")) {
                continue;
            }

            Elements gameInfoDivision = teamsTopDivision.select("article.sub-module.game-information.pre");
            Elements detailsColumn = gameInfoDivision.first().select("div.match-detail--right");
            String tossResult = detailsColumn.get(1).text();

            String battingFirst;
            if (tossResult.contains(homeTeamName)) {
                if (tossResult.contains("bat")) {
                    battingFirst = homeTeamName;
                } else {
                    battingFirst = awayTeamName;
                }
            } else {
                if (tossResult.contains("bat")) {
                    battingFirst = awayTeamName;
                } else {
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

            Elements winner = matchPage.getElementsByClass("cscore_notes");
            String result = winner.select("span").first().text();

            String BorC = "";
            if (result.contains(" wickets")) {
                BorC = "C";
            } else if (result.contains(" runs")) {
                BorC = "B";
            } else {
                BorC = "-";
            }

            Inning one = null;
            Inning two = null;
            for (int inning = 1; inning <= 2; inning++) {
                String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                String json;
                try {
                    json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                } catch (IOException ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
                JSONObject j = new JSONObject(json);
                int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                List<JSONObject> ballList = new ArrayList<>();
                int firstOverScore = 0;
                int sixOverScore = 0;
                int lastFiveOverScore = -1;
                int firstWicketScore = -1;
                int fourCount = 0;
                int sixCount = 0;
                int totalRuns = 0;

                for (int i = 1; i <= pageCount; i++) {
                    String currentPageUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/"
                            + seriesNo + "/playbyplay?contentorigin=espn&event="
                            + eventNo + "&page=" + i + "&period=" + inning + "&section=cricinfo";

                    String body;
                    try {
                        body = Jsoup.connect(currentPageUrl).ignoreContentType(true).execute().body();
                    } catch (IOException ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                    JSONObject jObj = new JSONObject(body);

                    for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                        JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                        ballList.add(jItem);

                        if (jItem.getJSONObject("over").getInt("unique") == 0) {
                            firstOverScore += jItem.getInt("scoreValue");
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
                        totalRuns += jItem.getInt("scoreValue");
                    }
                }
                if (lastFiveOverScore != -1) {

                    lastFiveOverScore++;
                }

                List<String> params = new ArrayList<>();
                params.add(String.valueOf(firstOverScore));
                params.add(String.valueOf(sixOverScore));
                params.add(String.valueOf(lastFiveOverScore));
                params.add(String.valueOf(firstWicketScore));
                params.add(String.valueOf(fourCount));
                params.add(String.valueOf(sixCount));
                params.add(String.valueOf(totalRuns));
                params.add(BorC);

                if (inning == 1) {
                    one = new Inning(8, params);
                }
                if (inning == 2) {
                    two = new Inning(8, params);
                }
            }

            Elements ground = matchPage.getElementsByClass("stadium-details");
            Elements sp = ground.select("span");
            String groundName = sp.text();
            String groundLink = ground.select("a").first().attr("href");

            Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Date.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
            db.addMatch(m);

        }

        return true;
    }

    public boolean loadT20IData() {

        int matchType = 3;
        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = year; y >= yr; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?class=3;id=" + y + ";type=year").get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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
            int mPos = 0;
            String[] mParts = matchLink.split("/");
            for (int i = 0; i < mParts.length; i++) {
                if (mParts[i].equals("match")) {
                    mPos = i + 1;
                    break;
                }
            }
            String mid = mParts[mPos];
            int mId = Integer.parseInt(mid.substring(0, mid.length() - 5));

            if (db.checkMatchEntry(mId)) {
                System.out.println("Match " + mId + " exists");
                continue;
            }

            String url = baseUrl + matchLink;

            Document matchPage;
            try {
                matchPage = Jsoup.connect(url).followRedirects(true).get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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

            if (matchDate.equals(LocalDate.now().minusDays(1))) {
                System.out.println("Match " + mId + " today.");
                continue;
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

            String homeScore = home.select("div.cscore_score").get(0).text();
            String awayScore = away.select("div.cscore_score").get(0).text();

            Element liveOrNot = teamsTopDivision.select("span.cscore_time").first();
            if (liveOrNot.text().equals("Live") || homeScore.contains("*") || awayScore.contains("*")) {
                continue;
            }

            Elements gameInfoDivision = teamsTopDivision.select("article.sub-module.game-information.pre");
            Elements detailsColumn = gameInfoDivision.first().select("div.match-detail--right");
            String tossResult = detailsColumn.get(1).text();

            String battingFirst;
            if (tossResult.contains(homeTeamName)) {
                if (tossResult.contains("bat")) {
                    battingFirst = homeTeamName;
                } else {
                    battingFirst = awayTeamName;
                }
            } else {
                if (tossResult.contains("bat")) {
                    battingFirst = awayTeamName;
                } else {
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

            Elements winner = matchPage.getElementsByClass("cscore_notes");
            String result = winner.select("span").first().text();

            String BorC = "";
            if (result.contains(" wickets")) {
                BorC = "C";
            } else if (result.contains(" runs")) {
                BorC = "B";
            } else {
                BorC = "-";
            }

            Inning one = null;
            Inning two = null;
            for (int inning = 1; inning <= 2; inning++) {
                String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                String json;
                try {
                    json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                } catch (IOException ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
                JSONObject j = new JSONObject(json);
                int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                List<JSONObject> ballList = new ArrayList<>();
                int firstOverScore = 0;
                int sixOverScore = 0;
                int lastFiveOverScore = -1;
                int firstWicketScore = -1;
                int fourCount = 0;
                int sixCount = 0;
                int totalRuns = 0;

                for (int i = 1; i <= pageCount; i++) {
                    String currentPageUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/"
                            + seriesNo + "/playbyplay?contentorigin=espn&event="
                            + eventNo + "&page=" + i + "&period=" + inning + "&section=cricinfo";

                    String body;
                    try {
                        body = Jsoup.connect(currentPageUrl).ignoreContentType(true).execute().body();
                    } catch (IOException ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                    JSONObject jObj = new JSONObject(body);

                    for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                        JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                        ballList.add(jItem);

                        if (jItem.getJSONObject("over").getInt("unique") == 0) {
                            firstOverScore += jItem.getInt("scoreValue");

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
                        totalRuns += jItem.getInt("scoreValue");
                    }
                }
                if (lastFiveOverScore != -1) {

                    lastFiveOverScore++;
                }

                List<String> params = new ArrayList<>();
                params.add(String.valueOf(firstOverScore));
                params.add(String.valueOf(sixOverScore));
                params.add(String.valueOf(lastFiveOverScore));
                params.add(String.valueOf(firstWicketScore));
                params.add(String.valueOf(fourCount));
                params.add(String.valueOf(sixCount));
                params.add(String.valueOf(totalRuns));
                params.add(BorC);
                if (inning == 1) {
                    one = new Inning(8, params);
                }
                if (inning == 2) {
                    two = new Inning(8, params);
                }

            }

            Elements ground = matchPage.getElementsByClass("stadium-details");
            Elements sp = ground.select("span");
            String groundName = sp.text();
            String groundLink = ground.select("a").first().attr("href");

            Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Date.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
            db.addMatch(m);
        }

        return true;
    }

    public boolean loadTestData() {
        int matchType = 1;

        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = year; y >= yr; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?class=1;id=" + y + ";type=year").get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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

            int mPos = 0;
            String[] mParts = matchLink.split("/");
            for (int i = 0; i < mParts.length; i++) {
                if (mParts[i].equals("match")) {
                    mPos = i + 1;
                    break;
                }
            }
            String mid = mParts[mPos];
            int mId = Integer.parseInt(mid.substring(0, mid.length() - 5));

            if (db.checkMatchEntry(mId)) {
                System.out.println("Match " + mId + " exists");
                continue;
            }

            String url = baseUrl + matchLink;

            Document matchPage;
            try {
                matchPage = Jsoup.connect(url).followRedirects(true).get();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            String matchUrl = matchPage.baseUri();
            String[] splitUrl = matchUrl.split("/");

            Elements teamsTopDivision = matchPage.getElementsByClass("layout-bc");

            LocalDate matchDate = LocalDate.now();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM d yyyy");

            Element matchDateElement = teamsTopDivision.select("div.cscore_info-overview").first();
            String[] parts = matchDateElement.text().trim().split(",");
            String[] parts2 = parts[parts.length - 1].split("-");
            String[] parts3 = parts2[1].split(" ");
            String matchDateString = parts2[0].trim() + " " + parts3[parts3.length - 1];

            try {
                matchDate = LocalDate.parse(matchDateString.trim(), df);
            } catch (DateTimeParseException ex) {
                System.out.print("<h1> error parsing date:" + matchDateString);
                Logger.getLogger(LoadODI.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (matchDate.equals(LocalDate.now().minusDays(7))) {
                System.out.println("Match " + mId + " this week.");
                continue;
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

            String homeScore = home.select("div.cscore_score").get(0).text();
            String awayScore = away.select("div.cscore_score").get(0).text();

            Element liveOrNot = teamsTopDivision.select("span.cscore_time").first();
            if (liveOrNot.text().equals("Live") || homeScore.contains("*") || awayScore.contains("*")) {
                continue;
            }

            Elements gameInfoDivision = teamsTopDivision.select("article.sub-module.game-information.pre");
            Elements detailsColumn = gameInfoDivision.first().select("div.match-detail--right");
            String tossResult = detailsColumn.get(1).text();

            String battingFirst;
            if (tossResult.contains(homeTeamName)) {
                if (tossResult.contains("bat")) {
                    battingFirst = homeTeamName;
                } else {
                    battingFirst = awayTeamName;
                }
            } else {
                if (tossResult.contains("bat")) {
                    battingFirst = awayTeamName;
                } else {
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

            String json;
            try {
                json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
            } catch (IOException ex) {
                Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            JSONObject j = new JSONObject(json);
            int pageCount = j.getJSONObject("commentary").getInt("pageCount");

            Inning one = null;
            Inning two = null;
            List<String> params1 = new ArrayList<>();
            List<String> params2 = new ArrayList<>();
            List<String> params = new ArrayList<>();
            for (int inning = 1; inning <= 4; inning++) {

                List<JSONObject> ballList = new ArrayList<>();

                int totalRuns = 0;
                int firstWicketScore = -1;
                int sixCount = 0;
                int afterFifthWicketScore = -1;

                int wicketCount = 0;

                for (int i = 1; i <= pageCount; i++) {
                    String currentPageUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/"
                            + seriesNo + "/playbyplay?contentorigin=espn&event="
                            + eventNo + "&page=" + i + "&period=" + inning + "&section=cricinfo";

                    String body;
                    try {
                        body = Jsoup.connect(currentPageUrl).ignoreContentType(true).execute().body();
                    } catch (IOException ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                    JSONObject jObj = new JSONObject(body);

                    for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                        JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                        ballList.add(jItem);

                        totalRuns += jItem.getInt("scoreValue");

                        if (jItem.getJSONObject("playType").getInt("id") == 9) {
                            wicketCount++;
                        }

                        if (wicketCount <= 5) {
                            afterFifthWicketScore += jItem.getInt("scoreValue");
                        }

                        if (jItem.getJSONObject("playType").getInt("id") == 9 && firstWicketScore == -1) {
                            firstWicketScore = jItem.getJSONObject("innings").getInt("runs");
                        }
//                            if (jItem.getJSONObject("playType").getInt("id") == 3) {
//                                fourCount++;
//                            }
                        if (jItem.getJSONObject("playType").getInt("id") == 4) {
                            sixCount++;
                        }
                    }
                }

                params.add(String.valueOf(totalRuns));
                params.add(String.valueOf(firstWicketScore));
                params.add(String.valueOf(sixCount));
                params.add(String.valueOf(afterFifthWicketScore));

                if (inning == 2) {
                    one = new Inning(8, params);
                    params.clear();
                }
                if (inning == 4) {
                    two = new Inning(8, params);
                }
            }

            Elements ground = matchPage.getElementsByClass("stadium-details");
            Elements sp = ground.select("span");
            String groundName = sp.text();
            String groundLink = ground.select("a").first().attr("href");

            Elements winner = matchPage.getElementsByClass("cscore_notes");
            String result = winner.select("span").first().text();

            Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Date.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
            db.addMatch(m);
        }

        return true;
    }
}
