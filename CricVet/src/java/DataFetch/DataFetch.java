/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataFetch;

import Database.CricDB;
import java.io.IOException;
import java.security.SecureRandom;

import java.security.cert.X509Certificate;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import models.*;
import models.testInning;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import views.LoadODI;
import models.*;
import models.testInning;
import models.testMatch;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author DELL
 */
public class DataFetch {

    Map<String, String> unloaded = new HashMap<>();

    public Map<String, String> getUnloaded() {
        return unloaded;
    }

    public void clearUnloaded() {
        unloaded.clear();
    }

    static {
        TrustManager[] trustAllCerts;
        trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

// Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int yr = 2017;

    CricDB db = new CricDB();

    public boolean loadIPLData() {
        boolean ret = true;

        String baseUrl = "http://stats.espncricinfo.com/";
        int matchType = 117;
        List<String> matchLinks = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = year; y >= yr; y--) {

            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + y + ";trophy=117;type=season").get();
            } catch (Exception ex) {
                ex.printStackTrace();
                unloaded.put(ex.getMessage() + ":", "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + y + ";trophy=117;type=season");
                ret = false;
                continue;
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

        MATCHLABEL:
        for (String matchLink : matchLinks) {
            try {

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
                } catch (Exception ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    unloaded.put("" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
                }
                String matchUrl = matchPage.baseUri();
                String[] splitUrl = matchUrl.split("/");

                Elements teamsTopDivision = matchPage.getElementsByClass("layout-bc");

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
                    unloaded.put("LIVE:" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
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
                if (result.contains(" wicket")) {
                    BorC = "C";
                } else if (result.contains(" run")) {
                    BorC = "B";
                } else {
                    BorC = "-";
                }

                String dateUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                LocalDateTime matchDate = LocalDateTime.now();
                String dJson = "";
                try {
                    dJson = Jsoup.connect(dateUrl).ignoreContentType(true).execute().body();
                } catch (Exception ex) {
                    ret = false;
                    unloaded.put("Date acquire error", url);
                    continue;
                }

                JSONObject dj = new JSONObject(dJson);
                String date = dj.getJSONObject("commentary").getJSONArray("items").getJSONObject(0).getString("date");
                try {
                    matchDate = LocalDateTime.parse(date);
                } catch (DateTimeParseException ex) {
                    ret = false;
                    unloaded.put("Date parse error", url);
                    continue;
                }


                Inning one = null;
                Inning two = null;
                for (int inning = 1; inning <= 2; inning++) {
                    String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                    String json;
                    try {
                        json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                    } catch (Exception ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        unloaded.put("" + mId, url);
                        ret = false;
                        continue MATCHLABEL;

                    }
                    JSONObject j = new JSONObject(json);
                    int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                    List<JSONObject> ballList = new ArrayList<>();
                    int firstOverScore = 0;
                    int sixOverScore = 0;
                    int lastFiveOverScore = -1;
                    int lastFlag = -1;
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
                        } catch (Exception ex) {
                            Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }

                        JSONObject jObj = new JSONObject();

                        try {
                            jObj = new JSONObject(body);

                        } catch (JSONException je) {
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }

                        //                    out.print("<td> " + i + "(" + jObj.getJSONObject("commentary").getInt("pageIndex")+")");
                        for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                            JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                            System.out.println("it:" + it + " url: " + currentPageUrl);
                            if (jItem.getJSONObject("playType").getInt("id") == 0) {
                                continue;
                            }

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

                            if (jItem.getJSONObject("over").getFloat("unique") == 15.01 && lastFlag == -1) {
                                if (jItem.getJSONObject("innings").getInt("wickets") > 7) {
                                    lastFlag = 1;
                                }
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
                    if (lastFlag == 1) {
                        lastFiveOverScore = -1;
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

                Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Timestamp.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
                db.addMatch(m);

                skipAndConti:
                ;

            } catch (Exception ex) {
                ex.printStackTrace();
                ret = false;

                unloaded.put(ex.getMessage() + ":", baseUrl+matchLink);
            }
        }
        return ret;
    }

    public boolean loadODIData() {
        boolean ret = true;

        int matchType = 2;
        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = year; y >= yr; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?class=2;id=" + y + ";type=year").get();
            } catch (Exception ex) {
                ex.printStackTrace();
                unloaded.put(ex.getMessage() + ":", "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?class=2;id=" + y + ";type=year");
                continue;
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

        MATCHLABEL:
        for (String matchLink : matchLinks) {
            try {

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
                } catch (Exception ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    unloaded.put("" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
                }
                String matchUrl = matchPage.baseUri();
                String[] splitUrl = matchUrl.split("/");

                Elements teamsTopDivision = matchPage.getElementsByClass("layout-bc");


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
                    unloaded.put("LIVE:" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
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
                if (result.contains(" wicket")) {
                    BorC = "C";
                } else if (result.contains(" run")) {
                    BorC = "B";
                } else {
                    BorC = "-";
                }
                
                String dateUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                LocalDateTime matchDate = LocalDateTime.now();
                String dJson = "";
                try {
                    dJson = Jsoup.connect(dateUrl).ignoreContentType(true).execute().body();
                } catch (Exception ex) {
                    ret = false;
                    unloaded.put("Date acquire error", url);
                    continue;
                }

                JSONObject dj = new JSONObject(dJson);
                String date = dj.getJSONObject("commentary").getJSONArray("items").getJSONObject(0).getString("date");
                try {
                    matchDate = LocalDateTime.parse(date);
                } catch (DateTimeParseException ex) {
                    ret = false;
                    unloaded.put("Date parse error", url);
                    continue;
                }

                Inning one = null;
                Inning two = null;
                for (int inning = 1; inning <= 2; inning++) {
                    String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=" + inning + "&section=cricinfo";

                    String json;
                    try {
                        json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                    } catch (Exception ex) {
                        System.out.println(commentaryUrl);
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        unloaded.put("" + mId, url);
                        ret = false;
                        continue MATCHLABEL;
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
                    int lastFlag = -1;
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
                        } catch (Exception ex) {
                            System.out.println(currentPageUrl);
                            Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }
                        System.out.println(currentPageUrl);
                        JSONObject jObj = new JSONObject();
                        try {
                            jObj = new JSONObject(body);
                        } catch (JSONException je) {
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }
                        //                    out.print("<td> " + i + "(" + jObj.getJSONObject("commentary").getInt("pageIndex")+")");

                        for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                            JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                            if (jItem.getJSONObject("playType").getInt("id") == 0) {
                                continue;
                            }

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

                            if (jItem.getJSONObject("over").getFloat("unique") == 40.01 && lastFlag == -1) {
                                if (jItem.getJSONObject("innings").getInt("wickets") > 7) {
                                    lastFlag = 1;
                                }
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

                    if (lastFlag == 1) {
                        lastTenOverScore = -1;
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

                Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Timestamp.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
                db.addMatch(m);
            } catch (Exception ex) {
                ex.printStackTrace();
                ret = false;
                unloaded.put(ex.getMessage() + ":", baseUrl+matchLink);

            }
        }

        return ret;
    }

    public boolean loadBBLData() {
        boolean ret = true;

        int matchType = 158;
        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR) + 1;
        for (int y = year; y >= yr; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + (y - 1) + "%2F" + (y % 100) + ";trophy=158;type=season").get();
            } catch (Exception ex) {
                ex.printStackTrace();
                unloaded.put(ex.getMessage() + ":", "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + (y - 1) + "%2F" + (y % 100) + ";trophy=158;type=season");
                continue;
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

        MATCHLABEL:
        for (String matchLink : matchLinks) {
            try {
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
                } catch (Exception ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    unloaded.put("" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
                }
                String matchUrl = matchPage.baseUri();
                String[] splitUrl = matchUrl.split("/");

                Elements teamsTopDivision = matchPage.getElementsByClass("layout-bc");

                

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
                    unloaded.put("LIVE:" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
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
                if (result.contains(" wicket")) {
                    BorC = "C";
                } else if (result.contains(" run")) {
                    BorC = "B";
                } else {
                    BorC = "-";
                }
                String dateUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                LocalDateTime matchDate = LocalDateTime.now();
                String dJson = "";
                try {
                    dJson = Jsoup.connect(dateUrl).ignoreContentType(true).execute().body();
                } catch (Exception ex) {
                    ret = false;
                    unloaded.put("Date acquire error", url);
                    continue;
                }

                JSONObject dj = new JSONObject(dJson);
                String date = dj.getJSONObject("commentary").getJSONArray("items").getJSONObject(0).getString("date");
                try {
                    matchDate = LocalDateTime.parse(date);
                } catch (DateTimeParseException ex) {
                    ret = false;
                    unloaded.put("Date parse error", url);
                    continue;
                }

                Inning one = null;
                Inning two = null;
                for (int inning = 1; inning <= 2; inning++) {
                    String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                    String json;
                    try {
                        json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                    } catch (Exception ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        unloaded.put("" + mId, url);
                        ret = false;
                        continue MATCHLABEL;
                    }
                    JSONObject j = new JSONObject(json);
                    int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                    List<JSONObject> ballList = new ArrayList<>();
                    int firstOverScore = 0;
                    int sixOverScore = 0;
                    int lastFlag = -1;
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
                        } catch (Exception ex) {
                            Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }

                        JSONObject jObj = new JSONObject();
                        try {
                            jObj = new JSONObject(body);
                        } catch (JSONException je) {
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }

                        for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                            JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                            if (jItem.getJSONObject("playType").getInt("id") == 0) {
                                continue;
                            }

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

                            if (jItem.getJSONObject("over").getFloat("unique") == 15.01 && lastFlag == -1) {
                                if (jItem.getJSONObject("innings").getInt("wickets") > 7) {
                                    lastFlag = 1;
                                }
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

                    if (lastFlag == 1) {
                        lastFiveOverScore = -1;
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

                Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Timestamp.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
                db.addMatch(m);
            } catch (Exception ex) {
                ex.printStackTrace();
                ret = false;
                unloaded.put(ex.getMessage() + ":", baseUrl+matchLink);
            }

        }
        return ret;
    }

    public boolean loadBPLData() {
        boolean ret = true;

        int matchType = 159;
        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR) + 1;
        for (int y = year; y >= yr; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + (y - 1) + "%2F" + (y % 100) + ";trophy=159;type=season").get();
            } catch (Exception ex) {
                ex.printStackTrace();
                unloaded.put(ex.getMessage() + ":", "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + (y - 1) + "%2F" + (y % 100) + ";trophy=159;type=season");
                continue;
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

        MATCHLABEL:
        for (String matchLink : matchLinks) {
            try {
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
                } catch (Exception ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    unloaded.put("" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
                }
                String matchUrl = matchPage.baseUri();
                String[] splitUrl = matchUrl.split("/");

                Elements teamsTopDivision = matchPage.getElementsByClass("layout-bc");

                

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
                    unloaded.put("LIVE:" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
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
                if (result.contains(" wicket")) {
                    BorC = "C";
                } else if (result.contains(" run")) {
                    BorC = "B";
                } else {
                    BorC = "-";
                }
                
                String dateUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                LocalDateTime matchDate = LocalDateTime.now();
                String dJson = "";
                try {
                    dJson = Jsoup.connect(dateUrl).ignoreContentType(true).execute().body();
                } catch (Exception ex) {
                    ret = false;
                    unloaded.put("Date acquire error", url);
                    continue;
                }

                JSONObject dj = new JSONObject(dJson);
                String date = dj.getJSONObject("commentary").getJSONArray("items").getJSONObject(0).getString("date");
                try {
                    matchDate = LocalDateTime.parse(date);
                } catch (DateTimeParseException ex) {
                    ret = false;
                    unloaded.put("Date parse error", url);
                    continue;
                }

                Inning one = null;
                Inning two = null;
                for (int inning = 1; inning <= 2; inning++) {
                    String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                    String json;
                    try {
                        json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                    } catch (Exception ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        unloaded.put("" + mId, url);
                        ret = false;
                        continue MATCHLABEL;
                    }
                    JSONObject j = new JSONObject(json);
                    int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                    List<JSONObject> ballList = new ArrayList<>();
                    int firstOverScore = 0;
                    int sixOverScore = 0;
                    int lastFlag = -1;
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
                        } catch (Exception ex) {
                            Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }

                        JSONObject jObj = new JSONObject();
                        try {
                            jObj = new JSONObject(body);
                        } catch (JSONException je) {
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }

                        for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                            JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                            if (jItem.getJSONObject("playType").getInt("id") == 0) {
                                continue;
                            }
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

                            if (jItem.getJSONObject("over").getFloat("unique") == 15.01 && lastFlag == -1) {
                                if (jItem.getJSONObject("innings").getInt("wickets") > 7) {
                                    lastFlag = 1;
                                }
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

                    if (lastFlag == 1) {
                        lastFiveOverScore = -1;
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

                Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Timestamp.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
                db.addMatch(m);
            } catch (Exception ex) {
                ex.printStackTrace();
                ret = false;
                unloaded.put(ex.getMessage() + ":", baseUrl+matchLink);
                continue;
            }

        }
        return ret;
    }

    public boolean loadCPLData() {
        boolean ret = true;

        int matchType = 748;
        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = year; y >= yr; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + y + ";trophy=748;type=season").get();
            } catch (Exception ex) {
                ex.printStackTrace();
                unloaded.put(ex.getMessage() + ":", "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + y + ";trophy=748;type=season");
                continue;
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

        MATCHLABEL:
        for (String matchLink : matchLinks) {
            try {

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
                } catch (Exception ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    unloaded.put("" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
                }
                String matchUrl = matchPage.baseUri();
                String[] splitUrl = matchUrl.split("/");

                Elements teamsTopDivision = matchPage.getElementsByClass("layout-bc");

                

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
                    unloaded.put("LIVE:" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
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
                if (result.contains(" wicket")) {
                    BorC = "C";
                } else if (result.contains(" run")) {
                    BorC = "B";
                } else {
                    BorC = "-";
                }
                String dateUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                LocalDateTime matchDate = LocalDateTime.now();
                String dJson = "";
                try {
                    dJson = Jsoup.connect(dateUrl).ignoreContentType(true).execute().body();
                } catch (Exception ex) {
                    ret = false;
                    unloaded.put("Date acquire error", url);
                    continue;
                }

                JSONObject dj = new JSONObject(dJson);
                String date = dj.getJSONObject("commentary").getJSONArray("items").getJSONObject(0).getString("date");
                try {
                    matchDate = LocalDateTime.parse(date);
                } catch (DateTimeParseException ex) {
                    ret = false;
                    unloaded.put("Date parse error", url);
                    continue;
                }
                

                Inning one = null;
                Inning two = null;
                for (int inning = 1; inning <= 2; inning++) {
                    String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                    String json;
                    try {
                        json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                    } catch (Exception ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        unloaded.put("" + mId, url);
                        ret = false;
                        continue MATCHLABEL;
                    }
                    JSONObject j = new JSONObject(json);
                    int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                    List<JSONObject> ballList = new ArrayList<>();
                    int firstOverScore = 0;
                    int sixOverScore = 0;
                    int lastFiveOverScore = -1;
                    int lastFlag = -1;
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
                        } catch (Exception ex) {
                            System.out.println("Missing json for : " + currentPageUrl);
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }

                        JSONObject jObj = new JSONObject();
                        try {
                            jObj = new JSONObject(body);
                        } catch (JSONException je) {
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }

                        for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                            JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                            if (jItem.getJSONObject("playType").getInt("id") == 0) {
                                continue;
                            }

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

                            if (jItem.getJSONObject("over").getFloat("unique") == 15.01 && lastFlag == -1) {
                                if (jItem.getJSONObject("innings").getInt("wickets") > 7) {
                                    lastFlag = 1;
                                }
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

                    if (lastFlag == 1) {
                        lastFiveOverScore = -1;
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

                Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Timestamp.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
                db.addMatch(m);

            } catch (Exception ex) {
                ex.printStackTrace();
                ret = false;
                unloaded.put(ex.getMessage() + ":", baseUrl+matchLink);
            }
        }
        return ret;
    }

    public boolean loadPSLData() {

        boolean ret = true;

        int matchType = 205;

        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR) + 1;
        for (int y = year; y >= yr; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + (y - 1) + "%2F" + (y % 100) + ";trophy=205;type=season").get();
            } catch (Exception ex) {
                ex.printStackTrace();
                unloaded.put(ex.getMessage() + ":", "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + (y - 1) + "%2F" + (y % 100) + ";trophy=205;type=season");
                continue;
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

        MATCHLABEL:
        for (String matchLink : matchLinks) {
            try {
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
                } catch (Exception ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    unloaded.put("" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
                }
                String matchUrl = matchPage.baseUri();
                String[] splitUrl = matchUrl.split("/");

                Elements teamsTopDivision = matchPage.getElementsByClass("layout-bc");

                

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
                    unloaded.put("LIVE:" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
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
                if (result.contains(" wicket")) {
                    BorC = "C";
                } else if (result.contains(" run")) {
                    BorC = "B";
                } else {
                    BorC = "-";
                }
                String dateUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                LocalDateTime matchDate = LocalDateTime.now();
                String dJson = "";
                try {
                    dJson = Jsoup.connect(dateUrl).ignoreContentType(true).execute().body();
                } catch (Exception ex) {
                    ret = false;
                    unloaded.put("Date acquire error", url);
                    continue;
                }

                JSONObject dj = new JSONObject(dJson);
                String date = dj.getJSONObject("commentary").getJSONArray("items").getJSONObject(0).getString("date");
                try {
                    matchDate = LocalDateTime.parse(date);
                } catch (DateTimeParseException ex) {
                    ret = false;
                    unloaded.put("Date parse error", url);
                    continue;
                }

                Inning one = null;
                Inning two = null;
                for (int inning = 1; inning <= 2; inning++) {
                    String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                    String json;
                    try {
                        json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                    } catch (Exception ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        unloaded.put("" + mId, url);
                        ret = false;
                        continue MATCHLABEL;
                    }
                    JSONObject j = new JSONObject(json);
                    int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                    List<JSONObject> ballList = new ArrayList<>();
                    int firstOverScore = 0;
                    int sixOverScore = 0;
                    int lastFlag = -1;
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
                        } catch (Exception ex) {
                            Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }

                        JSONObject jObj = new JSONObject();
                        try {
                            jObj = new JSONObject(body);
                        } catch (JSONException je) {
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }

                        for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                            JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                            if (jItem.getJSONObject("playType").getInt("id") == 0) {
                                continue;
                            }
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

                            if (jItem.getJSONObject("over").getFloat("unique") == 15.01 && lastFlag == -1) {
                                if (jItem.getJSONObject("innings").getInt("wickets") > 7) {
                                    lastFlag = 1;
                                }
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

                    if (lastFlag == 1) {
                        lastFiveOverScore = -1;
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

                Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Timestamp.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
                db.addMatch(m);

            } catch (Exception ex) {
                ex.printStackTrace();
                ret = false;
                unloaded.put(ex.getMessage() + ":", baseUrl+matchLink);
            }

        }
        return ret;
    }

    public boolean loadT20IData() {
        boolean ret = true;

        int matchType = 3;
        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = year; y >= yr; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?class=3;id=" + y + ";type=year").get();
            } catch (Exception ex) {
                ex.printStackTrace();
                unloaded.put(ex.getMessage() + ":", "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?class=3;id=" + y + ";type=year");
                continue;
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

        MATCHLABEL:
        for (String matchLink : matchLinks) {
            try {
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
                } catch (Exception ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    unloaded.put("" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
                }
                String matchUrl = matchPage.baseUri();
                String[] splitUrl = matchUrl.split("/");

                Elements teamsTopDivision = matchPage.getElementsByClass("layout-bc");

                

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
                    unloaded.put("LIVE:" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
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
                if (result.contains(" wicket")) {
                    BorC = "C";
                } else if (result.contains(" run")) {
                    BorC = "B";
                } else {
                    BorC = "-";
                }
                
                String dateUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                LocalDateTime matchDate = LocalDateTime.now();
                String dJson = "";
                try {
                    dJson = Jsoup.connect(dateUrl).ignoreContentType(true).execute().body();
                } catch (Exception ex) {
                    ret = false;
                    unloaded.put("Date acquire error", url);
                    continue;
                }

                JSONObject dj = new JSONObject(dJson);
                String date = dj.getJSONObject("commentary").getJSONArray("items").getJSONObject(0).getString("date");
                try {
                    matchDate = LocalDateTime.parse(date);
                } catch (DateTimeParseException ex) {
                    ret = false;
                    unloaded.put("Date parse error", url);
                    continue;
                }

                Inning one = null;
                Inning two = null;
                for (int inning = 1; inning <= 2; inning++) {
                    String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=1&section=cricinfo";

                    String json;
                    try {
                        json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                    } catch (Exception ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        unloaded.put("" + mId, url);
                        ret = false;
                        continue MATCHLABEL;
                    }
                    JSONObject j = new JSONObject(json);
                    int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                    List<JSONObject> ballList = new ArrayList<>();
                    int firstOverScore = 0;
                    int sixOverScore = 0;
                    int lastFlag = -1;
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
                        } catch (Exception ex) {
                            Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }

                        JSONObject jObj = new JSONObject();
                        try {
                            jObj = new JSONObject(body);
                        } catch (JSONException je) {
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }

                        for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                            JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                            if (jItem.getJSONObject("playType").getInt("id") == 0) {
                                continue;
                            }
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

                            if (jItem.getJSONObject("over").getFloat("unique") == 15.01 && lastFlag == -1) {
                                if (jItem.getJSONObject("innings").getInt("wickets") > 7) {
                                    lastFlag = 1;
                                }
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

                    if (lastFlag == 1) {
                        lastFiveOverScore = -1;
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

                Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Timestamp.valueOf(matchDate), tossResult, battingFirst, one, two, homeScore, awayScore, result, groundName, matchType);
                db.addMatch(m);
            } catch (Exception ex) {
                ex.printStackTrace();
                ret = false;
                unloaded.put(ex.getMessage() + ":", baseUrl+matchLink);
            }
        }

        return ret;
    }

    public boolean loadTestData() {

        boolean ret = true;

        int matchType = 1;

        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);

        for (int y = year; y >= 2016; y--) {
            Document matches;
            try {
                matches = Jsoup.connect("http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?class=1;id=" + y + ";type=year").get();
            } catch (Exception ex) {
                ex.printStackTrace();
                unloaded.put(ex.getMessage() + ":", "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?class=1;id=" + y + ";type=year");
                continue;
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

        MATCHLABEL:
        for (String matchLink : matchLinks) {
            try {
                int foflag = 0;

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

                if (db.checktestMatchEntry(mId)) {
                    System.out.println("Match " + mId + " exists");
                    continue;
                }

                String url = baseUrl + matchLink;

                Document matchPage;
                System.out.println("try test0 :" + url);
                try {
                    matchPage = Jsoup.connect(url).followRedirects(true).get();
                } catch (Exception ex) {
                    Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                    unloaded.put("" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
                }
                //           Element first = matchPage.getElementById("div.gp-inning-00");
                //           System.out.println(first);

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

                // String homeScore = home.select("div.cscore_score").get(0).text();
                // String awayScore = away.select("div.cscore_score").get(0).text();
                String homeScore = home.select("div.cscore_score").text();
                String awayScore = away.select("div.cscore_score").text();

                // System.out.println(awayScore);
                if (awayScore.contains("f/o") || homeScore.contains("f/o")) {
                    foflag = 1;

                }

                Element liveOrNot = teamsTopDivision.select("span.cscore_time").first();
                if (liveOrNot.text().equals("Live") || homeScore.contains("*") || awayScore.contains("*")) {
                    unloaded.put("LIVE:" + mId, url);
                    ret = false;
                    continue MATCHLABEL;
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

                Elements winner = matchPage.getElementsByClass("cscore_notes");
                String result = winner.select("span").first().text();

                String BorC = "";
                if (result.contains(" wicket")) {
                    BorC = "C";
                } else if (result.contains(" run")) {
                    BorC = "B";
                } else {
                    BorC = "-";
                }

                String seriesNo = splitUrl[seriesPos];
                String eventNo = splitUrl[eventNoPos];

                testInning one = null;
                testInning two = null;
                testInning three = null;
                testInning four = null;

                for (int inning = 1; inning <= 4; inning++) {
                    List<JSONObject> ballList = new ArrayList<>();
                    int totalRuns = 0;
                    int firstWicketScore = -1;
                    int sixCount = 0;
                    int afterFifthWicketScore = -1;
                    int fourCount = 0;

                    int wicketCount = 0;
                    String commentaryUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/" + seriesNo + "/playbyplay?contentorigin=espn&event=" + eventNo + "&page=1&period=" + inning + "&section=cricinfo";
                    String json;
                    try {
                        json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                    } catch (Exception ex) {
                        Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                        unloaded.put("" + mId, url);
                        ret = false;
                        continue MATCHLABEL;
                    }
                    JSONObject j = new JSONObject(json);
                    int pageCount = j.getJSONObject("commentary").getInt("pageCount");

                    for (int i = 1; i <= pageCount; i++) {
                        String currentPageUrl = "http://site.web.api.espn.com/apis/site/v2/sports/cricket/"
                                + seriesNo + "/playbyplay?contentorigin=espn&event="
                                + eventNo + "&page=" + i + "&period=" + inning + "&section=cricinfo";

                        String body;
                        System.out.println("trying test :" + currentPageUrl);
                        try {
                            body = Jsoup.connect(currentPageUrl).ignoreContentType(true).execute().body();
                        } catch (Exception ex) {
                            Logger.getLogger(DataFetch.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("misiing JSON : " + currentPageUrl);
                            unloaded.put("" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }
                        JSONObject jObj = new JSONObject();
                        try {
                            jObj = new JSONObject(body);
                        } catch (JSONException je) {
                            unloaded.put("CORRUPT:" + mId, url);
                            ret = false;
                            continue MATCHLABEL;
                        }

                        for (int it = 0; it < jObj.getJSONObject("commentary").getJSONArray("items").length(); it++) {
                            JSONObject jItem = jObj.getJSONObject("commentary").getJSONArray("items").getJSONObject(it);
                            System.out.println("it:" + it + " url: " + currentPageUrl);
                            if (jItem.getJSONObject("playType").getInt("id") == 0) {
                                continue;
                            }
                            ballList.add(jItem);

                            if (jItem.getJSONObject("playType").getInt("id") == 9) {
                                wicketCount++;
                            }

                            if (wicketCount >= 5) {
                                afterFifthWicketScore += jItem.getInt("scoreValue");
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
                            //afterFifthWicketScore = totalRuns - afterFifthWicketScore;

                        }
                    }

                    List<String> params = new ArrayList<>();
                    params.add(String.valueOf(totalRuns));
                    params.add(String.valueOf(sixCount));
                    params.add(String.valueOf(fourCount));
                    params.add(String.valueOf(firstWicketScore));
                    params.add(String.valueOf(afterFifthWicketScore));
                    params.add(BorC);

                    if (inning == 1) {
                        one = new testInning(6, params);

                    } else if (inning == 2) {
                        two = new testInning(6, params);

                    } else if (inning == 3) {
                        three = new testInning(6, params);
                    } else if (inning == 4) {
                        four = new testInning(6, params);
                    }

                }

                Elements ground = matchPage.getElementsByClass("stadium-details");
                Elements sp = ground.select("span");
                String groundName = sp.text();
                String groundLink = ground.select("a").first().attr("href");
                String teamathome = db.checkhomeoraway(homeTeamName, awayTeamName, groundName);
                String teamataway = db.getawayteam(homeTeamName, awayTeamName, groundName);
                System.out.println("AWAYTEAM IS " + teamataway);

                if (foflag == 0) {
                    testMatch m = new testMatch(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Date.valueOf(matchDate), tossResult, battingFirst, one, two, three, four, homeScore, awayScore, result, groundName, matchType, teamathome, teamataway);
                    db.addtestMatch(m);
                    System.out.println("new test match added");
                } else if (foflag == 1) {
                    testMatch m = new testMatch(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Date.valueOf(matchDate), tossResult, battingFirst, one, two, four, three, homeScore, awayScore, result, groundName, matchType, teamathome, teamataway);
                    db.addtestMatch(m);
                }
                System.out.println("new test match added");

                skipAndConti:
                ;

            } catch (Exception ex) {
                ex.printStackTrace();
                ret = false;
                unloaded.put(ex.getMessage() + ":", baseUrl+matchLink);

            }
        }

        return ret;

    }

}
