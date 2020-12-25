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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import models.testMatch;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFetch {

    List<MatchReport> reports = new ArrayList<>();
    List<String> impTeams = Arrays.asList("England", "India", "New Zealand", "Australia", "South Africa", "Pakistan", "Bangladesh",
            "Sri Lanka", "West Indies", "Afghanistan", "Ireland", "Zimbabwe", "Netherlands", "Scotland");

    public List<MatchReport> getReports() {
        return reports;
    }

    public static Map<String, String> impTeamIndices = new HashMap<String, String>() {
        {
            put("England", "1");
            put("India", "6");
            put("New Zealand", "5");
            put("Australia", "2");
            put("South Africa", "3");
            put("Pakistan", "7");
            put("Bangladesh", "25");
            put("Sri Lanka", "8");
            put("West Indies", "4");
            put("Afghanistan", "40");
            put("Ireland", "29");
            put("Zimbabwe", "9");
            put("Netherlands", "15");
            put("Scotland", "30");
        }
    };

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
    Pattern matchIdFinder = Pattern.compile("/ci/engine/match/(.*)\\.html");
    Pattern teamIndexFinder = Pattern.compile("/team/_/id/(.*)/(.*)");
    Pattern datePattern = Pattern.compile("(?<MM>Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|June?|July?|Aug(ust)?|Sep(t(ember)?)?|Oct(ober)?|Nov(ember)?|Dec(ember)?)\\s(?<dd>\\d{1,2})(\\s?-\\s?(Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|June?|July?|Aug(ust)?|Sep(t(ember)?)?|Oct(ober)?|Nov(ember)?|Dec(ember)?)?\\s?\\d{1,2})?\\s(?<yyyy>20[0-9][0-9])");
    Pattern timePattern = Pattern.compile("(?<hh>0[0-9]|1[0-9]|2[0-3])(([:.](?<mm>[0-5]\\d))|(\\s?a\\.?m\\.?|\\s?p\\.?m\\.?))");
    Pattern seriesNoPattern = Pattern.compile("\\d{5,}");

    CricDB db = new CricDB();

    public List<MatchReport> loadData() {
        System.out.println("------- Starting loadData() -------");
        String baseUrl = "http://stats.espncricinfo.com/";

        List<Integer> matchTypes = Arrays.asList(205, 117, 2, 158, 159, 748, 3);
        for (int matchType : matchTypes) {
            System.out.println("------- matchType: " + matchType + " -------");
            List<String> loadedMatchIDs = db.getLoadedMatchIDs(matchType);
            List<String> matchLinks = new ArrayList<>();
            int year = Calendar.getInstance().get(Calendar.YEAR) + 1;
            for (int y = year; y >= yr; y--) {
                Document matches;
                String matchListPage = null;
                switch (matchType) {
                    case 117:
                        if (y > 2019) {
                            matchListPage = "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + y + "%2F" + ((y + 1) % 100) + ";trophy=" + matchType + ";type=season";
                        } else {
                            matchListPage = "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + y + ";trophy=" + matchType + ";type=season";
                        }
                        break;
                    case 748:
                        matchListPage = "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + y + ";trophy=" + matchType + ";type=season";
                        break;
                    case 2:
                        matchListPage = "https://stats.espncricinfo.com/ci/engine/records/team/match_results.html?class=" + matchType + ";id=" + y + ";type=year";
                        break;
                    case 158:
                    case 159:
                    case 205:
                        matchListPage = "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=" + (y - 1) + "%2F" + (y % 100) + ";trophy=" + matchType + ";type=season";
                        break;
                    case 3:
                        matchListPage = "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?class=" + matchType + ";id=" + y + ";type=year";
                        break;
                }

                try {
                    matches = Jsoup.connect(matchListPage).get();
                } catch (Exception ex) {
                    reports.add(new MatchReport(matchListPage, MatchStatus.N_A, ex));
                    continue;
                }
                if (matches == null || matches.getElementsByClass("data1").first() == null) {
                    reports.add(new MatchReport(matchListPage, MatchStatus.N_A, new Exception("Data null.(Maybe wrong matchList page)")));
                    continue;
                }

                Elements rows = matches.getElementsByClass("data1");
                for (int i = (rows.size() - 1); i >= 0; i--) {
                    Element m = rows.get(i);
                    Elements cols = m.getElementsByClass("data-link");

                    String matchLink = cols.last().attr("href");
                    String url = baseUrl + matchLink;
                    Matcher idMatcher = matchIdFinder.matcher(matchLink);
                    if (idMatcher.find()) {
                        String currId = idMatcher.group(1);
                        if (loadedMatchIDs.contains(currId)) {
                            reports.add(new MatchReport(url, MatchStatus.ALREADY_LOADED, null));
                        } else {
                            matchLinks.add(matchLink);
                        }
                    } else {
                        reports.add(new MatchReport(url, MatchStatus.UNLOADED, new Exception("Unable to extract matchId")));
                    }
                }
            }

            MATCHLABEL:
            for (String matchLink : matchLinks) {
                String url = baseUrl + matchLink;
                try {

                    int mId = -1;
                    Matcher idMatcher = matchIdFinder.matcher(matchLink);
                    if (idMatcher.find()) {
                        String currId = idMatcher.group(1);
                        mId = Integer.parseInt(currId);
                    } else {
                        throw new Exception("Couldn't extract match ID");
                    }
                    if (mId == -1) {
                        throw new Exception("Couldn't extract match ID");
                    }

                    Document matchPage = Jsoup.connect(url).followRedirects(true).get();
                    String matchUrl = matchPage.baseUri();
                    String[] splitUrl = matchUrl.split("/");

                    Element matchHeader = matchPage.getElementsByClass("match-header").first();

                    Elements teamLinkRows = matchHeader.select(".teams .team");
                    if (teamLinkRows.size() != 2) {
                        throw new Exception("home/away name selector.size() did not return 2");
                    }
                    Element home = teamLinkRows.first();
                    Element away = teamLinkRows.last();
                    String homeTeamName = home.select("a.name-link").text();
                    String awayTeamName = away.select("a.name-link").text();

                    if (matchType == 2 || matchType == 3) {
                        if (!impTeams.contains(homeTeamName) || !impTeams.contains(awayTeamName)) {
                            continue;
                        }
                    }

                    Elements topTabs = matchPage.getElementsByClass("widget-tab-link");
                    List<String> tabTexts = topTabs.eachText();

                    Elements liveStatusLabel = matchHeader.getElementsByClass("status");
                    if (tabTexts.contains("Live") || liveStatusLabel.text().trim().equalsIgnoreCase("live")) {
                        throw new Exception("Live match");
                    }

                    String homeScore = home.select("span.score").text();
                    String awayScore = away.select("span.score").text();

                    String summaryText = matchHeader.getElementsByClass("status-text").first().text();

                    Elements homewinnerIcon = home.select("i");
                    Elements awaywinnerIcon = away.select("i");

                    String BCW;
                    if (homewinnerIcon.size() > 0) {
                        BCW = "B";
                    } else if (awaywinnerIcon.size() > 0) {
                        BCW = "C";
                    } else if (summaryText.contains(" tie") || summaryText.contains(" draw")) {
                        BCW = "T";
                    } else {
                        BCW = "--";
                    }

                    Elements detailsTable = matchPage.getElementsByClass("w-100 table match-details-table");
                    String tossResult = "N/A";

                    LocalDateTime matchDateTime = null;

                    LocalDate matchDate;
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("d MMM yyyy");
                    String resultTitle = matchHeader.getElementsByClass("description").first().text();
                    Matcher dm = datePattern.matcher(resultTitle);
                    if (dm.find()) {
                        String parsedDate = dm.group("dd") + " " + dm.group("MM") + " " + dm.group("yyyy");
                        matchDate = LocalDate.parse(parsedDate, df);
                    } else {
                        throw new Exception("Couldn't parse date in: " + resultTitle);
                    }

                    String timeString = "";
                    LocalTime time = LocalTime.MIDNIGHT;
                    DateTimeFormatter tf = DateTimeFormatter.ofPattern("H:m");

                    String seriesNo = null;
                    String eventNo = String.valueOf(mId);

                    for (int i = 0; i < detailsTable.select("tr").size(); i++) {
                        if (detailsTable.select("tr").get(i).text().contains("Hours of play")) {
                            timeString = detailsTable.select("tr").get(i).text().trim();
                        }
                        if (detailsTable.select("tr").get(i).text().contains("Toss")) {
                            tossResult = detailsTable.select("tr").get(i).text();
                        }
                        if (detailsTable.select("tr").get(i).selectFirst("td").text().trim().equalsIgnoreCase("series")) {
                            String seriesLink = detailsTable.select("tr").get(i).selectFirst("a").attr("href");

                            Matcher sm = seriesNoPattern.matcher(seriesLink);
                            if (sm.find()) {
                                seriesNo = sm.group();
                            }
                        }
                    }

                    if (seriesNo == null) {
                        throw new Exception("Cant parse series no.");
                    }

                    Matcher tm = timePattern.matcher(timeString);
                    if (tm.find()) {
                        String parsedTime = tm.group("hh") + ":" + tm.group("mm");
                        time = LocalTime.parse(parsedTime, tf);
                    }

                    matchDateTime = matchDate.atTime(time);

                    Inning one = null;
                    Inning two = null;
                    //NEW API
                    int firstX = 5;
                    int lastX = 14;
                    int majorOv = 19;
                    if (matchType == 2) {
                        firstX = 9;
                        lastX = 39;
                        majorOv = 47;
                    }

                    boolean majorityPlayed = false;
                    for (int inning = 1; inning <= 2; inning++) {
                        String fourcheck = " FOUR";
                        String sixcheck = " SIX";
                        int firstOverScore = -1;
                        int XOverScore = -1;
                        int lastXOverScore = -1;
                        int firstWicketScore = -1;
                        int fourCount = 0;
                        int sixCount = 0;
                        int totalRuns;
                        if (inning == 1) {
                            try {
                                StringTokenizer st = new StringTokenizer(homeScore, " /");
                                totalRuns = Integer.parseInt(st.nextToken());
                            } catch (Exception ex) {
                                totalRuns = -1;
                            }
                        } else {
                            try {
                                StringTokenizer st = new StringTokenizer(awayScore, " /");
                                totalRuns = Integer.parseInt(st.nextToken());
                            } catch (Exception ex) {
                                totalRuns = -1;
                            }
                        }

                        String commentaryUrl = "https://hsapi.espncricinfo.com/v1/pages/match/comments?lang=en&leagueId=" + seriesNo + "&eventId=" + eventNo + "&period=" + inning + "&page=1&filter=full&liveTest=false";
                        String json;
                        try {
                            json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                        } catch (Exception ex) {
                            throw new Exception("Unable to access page", new Exception(commentaryUrl));
                        }
                        JSONObject j = new JSONObject(json);
                        int pageCount = j.getJSONObject("pagination").getInt("pageCount");

                        for (int i = 1; i <= pageCount; i++) {
                            String currentPageUrl = "https://hsapi.espncricinfo.com/v1/pages/match/comments?lang=en&leagueId=" + seriesNo + "&eventId=" + eventNo + "&period=" + inning + "&page=" + i + "&filter=full&liveTest=false";

                            String body;
                            System.out.println("trying test :" + currentPageUrl);
                            try {
                                body = Jsoup.connect(currentPageUrl).ignoreContentType(true).execute().body();
                            } catch (Exception ex) {
                                throw new Exception("Unable to access page", new Exception(currentPageUrl));
                            }
                            JSONObject jObj = new JSONObject();
                            try {
                                jObj = new JSONObject(body);
                            } catch (JSONException je) {
                                throw new JSONException("corrupt JSON", new Exception(currentPageUrl, je));
                            }

                            for (int it = 0; it < jObj.getJSONArray("comments").length(); it++) {
                                JSONObject jItem = jObj.getJSONArray("comments").getJSONObject(it);
                                System.out.println("it:" + it + " url: " + currentPageUrl);

                                if (jItem.getInt("over") == 0 && jItem.getInt("ball") == 6) {
                                    firstOverScore = jItem.getJSONObject("currentInning").getInt("runs");
                                }

                                if (jItem.getInt("over") == firstX && jItem.getInt("ball") == 6) {
                                    XOverScore = jItem.getJSONObject("currentInning").getInt("runs");
                                }

                                if (jItem.getInt("over") == lastX && jItem.getInt("ball") == 6) {
                                    lastXOverScore = totalRuns - jItem.getJSONObject("currentInning").getInt("runs");
                                    if (jItem.getJSONObject("currentInning").getInt("wickets") > 7) {
                                        lastXOverScore = -1;
                                    }
                                }

                                if (jItem.getJSONObject("currentInning").getInt("wickets") == 1 && jItem.has("matchWicket") && firstWicketScore == -1) {
                                    firstWicketScore = jItem.getJSONObject("currentInning").getInt("runs");
                                }
                                try {
                                    if (jItem.getString("shortText").toLowerCase().contains(fourcheck.toLowerCase())) {
                                        fourCount++;
                                    }
                                    if (jItem.getString("shortText").toLowerCase().contains(sixcheck.toLowerCase())) {
                                        sixCount++;
                                    }
                                } catch (JSONException e) {
                                    System.out.println(e.getMessage());
                                }

                                if (inning == 1) {
                                    if (jItem.getInt("over") >= majorOv) {
                                        majorityPlayed = true;
                                    }
                                }
                            }
                        }

                        if (firstWicketScore == -1) {
                            firstWicketScore = totalRuns;
                        }

                        List<String> params = new ArrayList<>();
                        params.add(String.valueOf(firstOverScore));
                        params.add(String.valueOf(XOverScore));
                        params.add(String.valueOf(lastXOverScore));
                        params.add(String.valueOf(firstWicketScore));
                        params.add(String.valueOf(fourCount));
                        params.add(String.valueOf(sixCount));
                        params.add(String.valueOf(totalRuns));
                        params.add(BCW);

                        if (inning == 1) {
                            one = new Inning(params);
                        }
                        if (inning == 2) {
                            two = new Inning(params);
                        }
                    }

                    if (!majorityPlayed && summaryText.contains("D/L")) {
                        summaryText = summaryText.concat(" MAJ_QUIT");
                    }

                    String groundName = detailsTable.select("tr").get(0).text();
                    String groundLink = matchPage.getElementsByClass("font-weight-bold match-venue").select("a").attr("href");

                    Match m = new Match(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Timestamp.valueOf(matchDateTime), tossResult, BCW, one, two, homeScore, awayScore, summaryText, groundName, matchType);
                    db.addMatch(m);
                    reports.add(new MatchReport(url, MatchStatus.LOADED, null));

                } catch (Exception ex) {
                    reports.add(new MatchReport(url, MatchStatus.UNLOADED, ex));
                }
            }
        }
        return reports;
    }

    public List<MatchReport> loadTestData() {
        List<MatchReport> reports = new ArrayList<>();
        int matchType = 1;
        List<String> loadedMatchIDs = db.getLoadedMatchIDs(matchType);

        String baseUrl = "http://stats.espncricinfo.com/";
        List<String> matchLinks = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);

        for (int y = year; y >= 2016; y--) {
            Document matches = null;
            String matchListLink = "http://stats.espncricinfo.com/ci/engine/records/team/match_results.html?class=1;id=" + y + ";type=year";
            try {
                matches = Jsoup.connect(matchListLink).get();
                if (matches == null && matches.getElementsByClass("data1").first() == null) {
                    throw new NullPointerException();
                }

                Elements rows = matches.getElementsByClass("data1");
                for (int i = (rows.size() - 1); i >= 0; i--) {
                    Element m = rows.get(i);
                    Elements cols = m.getElementsByClass("data-link");

                    String matchLink = cols.last().attr("href");
                    String url = baseUrl + matchLink;
                    Matcher idMatcher = matchIdFinder.matcher(matchLink);
                    if (idMatcher.find()) {
                        String currId = idMatcher.group(1);
                        if (loadedMatchIDs.contains(currId)) {
                            reports.add(new MatchReport(url, MatchStatus.ALREADY_LOADED, null));
                        } else {
                            matchLinks.add(matchLink);
                        }
                    } else {
                        reports.add(new MatchReport(url, MatchStatus.UNLOADED, new Exception("Unable to extract matchId")));
                    }
                }
            } catch (Exception ex) {
                reports.add(new MatchReport(matchListLink, MatchStatus.N_A, ex));
            }
        }

        MATCHLABEL:
        for (String matchLink : matchLinks) {
            String url = baseUrl + matchLink;

            try {
                int foflag = 0;

                String currId;
                Matcher idMatcher = matchIdFinder.matcher(matchLink);
                if (idMatcher.find()) {
                    currId = idMatcher.group(1);
                } else {
                    throw new Exception("Unable to extract match ID.");
                }
                int mId = Integer.parseInt(currId);

                Document matchPage;
                matchPage = Jsoup.connect(url).followRedirects(true).get();

                Element matchHeader = matchPage.getElementsByClass("match-header").first();

                Elements teamLinkRows = matchHeader.select(".teams .team");
                if (teamLinkRows.size() != 2) {
                    throw new Exception("home/away name selector.size() did not return 2");
                }
                Element home = teamLinkRows.first();
                Element away = teamLinkRows.last();
                String homeTeamName = home.select("a.name-link").text();
                String awayTeamName = away.select("a.name-link").text();

                int homeIndex = -1;
                String homeTeamUrl = home.select("a").attr("href");
                Matcher homeIndexMatcher = teamIndexFinder.matcher(homeTeamUrl);
                if (homeIndexMatcher.find()) {
                    homeIndex = Integer.parseInt(homeIndexMatcher.group(1));
                }

                int awayIndex = -1;
                String awayTeamUrl = away.select("a").attr("href");
                Matcher awayIndexMatcher = teamIndexFinder.matcher(awayTeamUrl);
                if (awayIndexMatcher.find()) {
                    awayIndex = Integer.parseInt(awayIndexMatcher.group(1));
                }

                if (homeIndex == -1 || awayIndex == -1) {
                    throw new Exception("Unable to extract team index");
                }

                if (!impTeams.contains(homeTeamName) || !impTeams.contains(awayTeamName)) {
                    continue;
                }
                Element foCheck = matchHeader.getElementsByClass("teams").first();
                if (foCheck.text().contains("f/o")) {
                    foflag = 1;
                }
                
                LocalDate matchDate;
                DateTimeFormatter df = DateTimeFormatter.ofPattern("d MMM yyyy");
                String resultTitle = matchHeader.getElementsByClass("description").first().text();
                Matcher dm = datePattern.matcher(resultTitle);
                if (dm.find()) {
                    String parsedDate = dm.group("dd") + " " + dm.group("MM") + " " + dm.group("yyyy");
                    matchDate = LocalDate.parse(parsedDate, df);
                } else {
                    throw new Exception("Couldn't parse date in: " + resultTitle);
                }


                String homeScore = home.select("span.score").text();
                String awayScore = away.select("span.score").text();

                Elements topTabs = matchPage.getElementsByClass("widget-tabs match-home-tabs");
                List<String> tabTexts = topTabs.select("a").eachText();

                Elements liveStatusLabel = matchHeader.getElementsByClass("status");
                if (tabTexts.contains("Live") || liveStatusLabel.text().trim().equalsIgnoreCase("live")) {
                    throw new Exception("Live match");
                }

                String seriesNo = null;
                String eventNo = String.valueOf(mId);

                Elements detailsTable = matchPage.getElementsByClass("w-100 table match-details-table");

                String tossResult = "N/A";
                for (int i = 0; i < detailsTable.select("tr").size(); i++) {
                    if (detailsTable.select("tr").get(i).text().contains("Toss")) {
                        tossResult = detailsTable.select("tr").get(i).text();
                    }

                    if (detailsTable.select("tr").get(i).selectFirst("td").text().trim().equalsIgnoreCase("series")) {
                        String seriesLink = detailsTable.select("tr").get(i).selectFirst("a").attr("href");

                        Matcher sm = seriesNoPattern.matcher(seriesLink);
                        if (sm.find()) {
                            seriesNo = sm.group();
                        }
                    }
                }

                if (seriesNo == null) {
                    throw new Exception("Cant parse series no.");
                }

                String summaryText = matchHeader.getElementsByClass("status-text").first().text();

                Elements homewinnerIcon = home.select("i");
                Elements awaywinnerIcon = away.select("i");

                String BCW;
                if (homewinnerIcon.size() > 0) {
                    BCW = "B";
                } else if (awaywinnerIcon.size() > 0) {
                    BCW = "C";
                } else if (summaryText.contains(" tie") || summaryText.contains(" draw")) {
                    BCW = "T";
                } else {
                    BCW = "--";
                }

                Inning one = null;
                Inning two = null;
                Inning three = null;
                Inning four = null;

                for (int inning = 1; inning <= 4; inning++) {
                    String fourcheck = " FOUR";
                    String sixcheck = " SIX";

                    int totalRuns = -1;
                    int firstWicketScore = -1;
                    int sixCount = 0;
                    int afterFifthWicketScore = -1;
                    int fourCount = 0;

                    String commentaryUrl = "https://hsapi.espncricinfo.com/v1/pages/match/comments?lang=en&leagueId=" + seriesNo + "&eventId=" + eventNo + "&period=" + inning + "&page=1&filter=full&liveTest=false";
                    String json;
                    try {
                        json = Jsoup.connect(commentaryUrl).ignoreContentType(true).execute().body();
                    } catch (IOException ex) {
                        throw new Exception("Unable to access page", new Exception(commentaryUrl));
                    }
                    JSONObject j = new JSONObject(json);
                    int pageCount = j.getJSONObject("pagination").getInt("pageCount");

                    for (int i = 1; i <= pageCount; i++) {
                        String currentPageUrl = "https://hsapi.espncricinfo.com/v1/pages/match/comments?lang=en&leagueId=" + seriesNo + "&eventId=" + eventNo + "&period=" + inning + "&page=" + i + "&filter=full&liveTest=false";

                        String body;
                        JSONObject jObj = new JSONObject();
                        try {
                            body = Jsoup.connect(currentPageUrl).ignoreContentType(true).execute().body();
                            jObj = new JSONObject(body);
                        } catch (Exception ex) {
                            throw new Exception("Unable to access page", new Exception(currentPageUrl));
                        }

                        for (int it = 0; it < jObj.getJSONArray("comments").length(); it++) {
                            JSONObject jItem = jObj.getJSONArray("comments").getJSONObject(it);

                            if (jItem.getJSONObject("currentInning").getInt("wickets") == 5 && jItem.has("matchWicket") && afterFifthWicketScore == -1) {
                                afterFifthWicketScore = jItem.getJSONObject("currentInning").getInt("runs");
                            }

                            if (jItem.getJSONObject("currentInning").getInt("wickets") == 1 && jItem.has("matchWicket") && firstWicketScore == -1) {
                                firstWicketScore = jItem.getJSONObject("currentInning").getInt("runs");
                            }
                            try {
                                if (jItem.getString("shortText").toLowerCase().contains(fourcheck.toLowerCase())) {
                                    fourCount++;
                                }
                                if (jItem.getString("shortText").toLowerCase().contains(sixcheck.toLowerCase())) {
                                    sixCount++;
                                }
                            } catch (JSONException e) {
                                System.out.println(e.getMessage());
                            }
                            totalRuns += jItem.getInt("runs");
                        }
                    }

                    /*
                    //try to take score from scoreboard
                    if(inning == 1){
                        StringTokenizer st = new StringTokenizer(homeScore, " /");
                        totalRuns = Integer.parseInt(st.nextToken());
                    }
                    else if(inning==2){
                        StringTokenizer st = new StringTokenizer(awayScore, " /");
                        totalRuns = Integer.parseInt(st.nextToken());
                    }
                    else if(inning==3){
                        StringTokenizer st = new StringTokenizer(awayScore, " /");
                        totalRuns = Integer.parseInt(st.nextToken());
                    }
                    else if(inning==4){
                        StringTokenizer st = new StringTokenizer(awayScore, " /");
                        totalRuns = Integer.parseInt(st.nextToken());
                    }
                     */
                    if (afterFifthWicketScore != -1) {
                        afterFifthWicketScore = totalRuns - afterFifthWicketScore;
                    }

                    if (firstWicketScore == -1) {
                        firstWicketScore = totalRuns;
                    }

                    List<String> params = new ArrayList<>();
                    params.add(String.valueOf(totalRuns));
                    params.add(String.valueOf(fourCount));
                    params.add(String.valueOf(sixCount));
                    params.add(String.valueOf(firstWicketScore));
                    params.add(String.valueOf(afterFifthWicketScore));
                    params.add(BCW);

                    switch (inning) {
                        case 1:
                            one = new Inning(params);
                            break;
                        case 2:
                            two = new Inning(params);
                            break;
                        case 3:
                            three = new Inning(params);
                            break;
                        case 4:
                            four = new Inning(params);
                            break;
                        default:
                            break;
                    }

                }
                String groundName = detailsTable.select("tr").get(0).text();

                String teamathome;
                String teamataway;

                if (db.getHomeGroundsFor(homeTeamName, 1).contains(groundName)) {
                    teamathome = homeTeamName;
                    teamataway = awayTeamName;
                } else {
                    teamathome = awayTeamName;
                    teamataway = homeTeamName;
                }

                testMatch m = null;
                if (foflag == 0) {
                    m = new testMatch(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Date.valueOf(matchDate), tossResult, BCW, one, two, three, four, homeScore, awayScore, summaryText, groundName, teamathome, teamataway);
                } else if (foflag == 1) {
                    m = new testMatch(Integer.parseInt(eventNo), homeTeamName, awayTeamName, Date.valueOf(matchDate), tossResult, BCW, one, two, four, three, homeScore, awayScore, summaryText, groundName, teamathome, teamataway);
                }
                db.addtestMatch(m);
                reports.add(new MatchReport(url, MatchStatus.LOADED, null));
            } catch (Exception ex) {
                reports.add(new MatchReport(url, MatchStatus.UNLOADED, ex));
            }
        }
        return reports;
    }
}
