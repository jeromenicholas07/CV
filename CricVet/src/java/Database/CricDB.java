/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author DELL
 */
import models.OverallOHL;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import models.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.naming.InitialContext;
import models.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.naming.InitialContext;
import models.Inning;
import models.testMatch;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CricDB extends BaseDAO {

    public Location checkLocationOf(String team, String otherTeam, String groundName, int matchType) {
        List<String> homeGroundsOne = getHomeGroundsFor(team, matchType);
        List<String> homeGroundsTwo = getHomeGroundsFor(otherTeam, matchType);

        if (homeGroundsOne.contains(groundName)) {
            return Location.HOME;
        } else if (homeGroundsTwo.contains(groundName)) {
            return Location.AWAY;
        } else {
            return Location.NONE;
        }
    }

    public void addtestMatch(testMatch match) throws Exception {

        Connection con = null;
        Statement s = null;
        ResultSet r = null;
        try {
            con = getConnection();
            String sq = "select * from APP.TESTMATCH WHERE MATCHID=" + String.valueOf(match.getMatchId());
            s = con.createStatement();
            r = s.executeQuery(sq);
            if (r.next()) {
                System.out.println("Match " + String.valueOf(match.getMatchId()) + " already exists in DB");
                return;
            }

            int inn11 = addtestInning(match.getInningOne1());

            int inn21 = addtestInning(match.getInningTwo1());
            int inn12 = addtestInning(match.getInningOne2());

            int inn22 = addtestInning(match.getInningTwo2());

            String sql = "insert into APP.TESTMATCH (MATCHID, HOMETEAM, AWAYTEAM, MATCHDATE, TOSSWINNER, BCW, ONE1ID, TWO1ID, ONE2ID, TWO2ID, HOMESCORE, AWAYSCORE, RESULT, GROUNDNAME, TEAMATHOME,TEAMATAWAY) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, match.getMatchId());
            ps.setString(2, match.getHomeTeam());
            ps.setString(3, match.getAwayTeam());
            ps.setDate(4, match.getMatchDate());
            ps.setString(5, match.getTossWinner());
            ps.setString(6, match.getBCW());
            ps.setInt(7, inn11);
            ps.setInt(8, inn21);
            ps.setInt(9, inn12);
            ps.setInt(10, inn22);

            ps.setString(11, match.getHomeScore());
            ps.setString(12, match.getAwayScore());

            ps.setString(13, match.getResult());
            ps.setString(14, match.getGroundName());
            ps.setString(15, String.valueOf(match.getteamathome()));
            ps.setString(16, String.valueOf(match.getteamataway()));
            ps.execute();

            con.close();
        } catch (SQLException ex) {
            throw ex;
            //todo: show that ur unable to connect to db
        } finally {
            try {
                r.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                s.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
    }

    public int addtestInning(Inning i) throws Exception {
        int id = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = null;
            rs = null;
            if (i.getParams().size() != 6) {
                throw new Exception("Inning param count doesnt match");
            }
            String sql = "insert into APP.TESTINNING (PARAM1, PARAM2, PARAM3, PARAM4, PARAM5,PARAM6) values(?,?,?,?,?,?)";
            String generatedColumns[] = {"ID"};
            ps = con.prepareStatement(sql, generatedColumns);
            for (int it = 1; it <= 6; it++) {
                ps.setString(it, i.getParams().get(it - 1));
            }

            ps.execute();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            //todo: show that ur unable to connect to db
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return id;
    }

    public List<Match> getHth(int matchType, String A, String B) {
        List<Match> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();

            String sql = "select * from APP.Matches where matchtype= " + matchType + " and ((hometeam='" + A + "' AND awayteam='" + B + "') OR (hometeam='" + B + "' AND awayteam='" + A + "')) order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Timestamp matchDate = rs.getTimestamp("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String bcw = rs.getString("bcw");
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                Inning inningOne = getInning(inning1_id, con);
                Inning inningTwo = getInning(inning2_id, con);

                Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, bcw, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return matches;
    }
// get team A vs team B HTH

    public List<testMatch> getTestHth(String A, String B) {

        List<testMatch> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();

            String sql = "select * from APP.TESTMATCH where ((hometeam='" + A + "' AND awayteam='" + B + "') OR (hometeam='" + B + "' AND awayteam='" + A + "')) order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String BCW = rs.getString("BCW");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
                String teamathome = rs.getString("teamathome");
                String teamataway = rs.getString("teamataway");

                Inning inningOne1 = gettestInning(one1, con);
                Inning inningTwo1 = gettestInning(two1, con);
                Inning inningOne2 = gettestInning(one2, con);
                Inning inningTwo2 = gettestInning(two2, con);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1, inningOne2, inningTwo2, homeScore, awayscore, result, groundName, teamathome, teamataway);
                matches.add(m);
            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return matches;
    }

    public List<Match> getDB(int matchType, String teamName) {
        List<Match> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;

        try {
            con = getConnection();

            if (teamName.equals("-1")) {
                sql = "select * from APP.Matches where matchtype= " + matchType + " order by MATCHDATE DESC";
            } else {
                sql = "select * from APP.Matches where matchtype= " + matchType + " and (hometeam='" + teamName + "' OR awayteam='" + teamName + "') order by MATCHDATE DESC";
            }

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Timestamp matchDate = rs.getTimestamp("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String BCW = rs.getString("BCW");
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                Inning inningOne = getInning(inning1_id, con);
                Inning inningTwo = getInning(inning2_id, con);

                Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return matches;
    }

    public Match getMatchfromID(int matchID) {

        Match m = null;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();

            String sql = "select * from APP.Matches where matchid =" + matchID + "  order by MATCHDATE DESC";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Timestamp matchDate = rs.getTimestamp("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String BCW = rs.getString("bcw");
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
                int matchType = rs.getInt("matchtype");

                Inning inningOne = getInning(inning1_id, con);
                Inning inningTwo = getInning(inning2_id, con);

                m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);

            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return m;
    }

    public testMatch gettestMatchfromID(int matchID) {

        testMatch m = null;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();

            String sql = "select * from APP.TESTMATCH where matchid =" + matchID + "  order by MATCHDATE DESC";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {

                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String BCW = rs.getString("BCW");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
                String teamathome = rs.getString("teamathome");
                String teamataway = rs.getString("teamataway");
                Inning inningOne1 = gettestInning(one1, con);
                Inning inningTwo1 = gettestInning(two1, con);
                Inning inningOne2 = gettestInning(one2, con);
                Inning inningTwo2 = gettestInning(two2, con);

                Inning inningOne = gettestInning(one1, con);
                Inning inningTwo = gettestInning(two1, con);
                Inning inningThree = gettestInning(one2, con);
                Inning inningFour = gettestInning(two2, con);

                m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1, inningOne2, inningTwo2, homeScore, awayscore, result, groundName, teamathome, teamataway);

            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return m;
    }

    public List<testMatch> gettestDB(String TeamName) {
        List<testMatch> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();

            String sql = "select * from APP.TESTMATCH where and (hometeam='" + TeamName + "' OR awayteam='" + TeamName + "') order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String BCW = rs.getString("BCW");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");
                String teamathome = rs.getString("teamathome");
                String teamataway = rs.getString("teamataway");
                Inning inningOne1 = gettestInning(one1, con);
                Inning inningTwo1 = gettestInning(two1, con);
                Inning inningOne2 = gettestInning(one2, con);
                Inning inningTwo2 = gettestInning(two2, con);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1, inningOne2, inningTwo2, homeScore, awayscore, result, groundName, teamathome, teamataway);
                matches.add(m);

            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return matches;
    }

    public String deleteDB() {

        String status = "";

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "drop table \"APP\".TEAMNAMES";
            con = getConnection();
            stmt = con.createStatement();
            status.concat("\n - TeamNames SUCCESSFUL");
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            status.concat("\n - TeamNames FAILED");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        try {
            String sql = "drop table \"APP\".TESTINNING";
            con = getConnection();
            stmt = con.createStatement();
            status.concat("\n - TestInning SUCCESSFUL");
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            status.concat("\n - TestInning FAILED");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        try {
            String sql = "drop table \"APP\".TESTMATCH";
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            status.concat("\n - TestMatches SUCCESSFUL");
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            status.concat("\n - TestMatches FAILED");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        try {
            String sql = "drop table \"APP\".HEADERS";
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            status.concat("\n - Headers SUCCESSFUL");
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            status.concat("\n - Headers FAILED");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        try {
            String sql = "drop table \"APP\".MATCHES";
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            status.concat("\n - Matches SUCCESSFUL");
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            status.concat("\n - Matches FAILED");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        try {
            String sql = "drop table \"APP\".INNINGS";
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            status.concat("\n - Innings SUCCESSFUL");
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            status.concat("\n - Innings FAILED");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        return status;
    }

    public Map<String, String> getEditTeamNameDB() {
        Map<String, String> names = new LinkedHashMap<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();

            String sql = "select * from APP.TEAMNAMES";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String oldName = rs.getString("OLDNAME");
                String newName = rs.getString("NEWNAME");
                names.put(oldName, newName);
            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return names;
    }

    public void deleteFav() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "drop table \"APP\".FAVOURITES";
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }
    }

    public void initDB() {

        System.out.println("at init");

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;


        try {
            String sql = "ALTER TABLE APP.FAVOURITES ADD BIAS varchar(120)";
                                                                                                                                                                                                                                                                   
            
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Unable to add BIAS in fav");
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        
        try {
            String sql = "ALTER TABLE APP.FAVOURITES ADD OVERALLOHL blob";
                                                                                                                                                                                                                                                                   
            
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Unable to add OVERALLOHL in fav");
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        
        try {
            String sql = "create table \"APP\".TEAMNAMES\n"
                    + "(\n"
                    + "	OLDNAME VARCHAR(120) not null primary key,\n"
                    + "	NEWNAME VARCHAR(120) not null\n"
                    + ")";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Unable to create TEAMNAMES");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        try {
            String sql = "create table \"APP\".TESTMATCH\n"
                    + "(\n"
                    + "	MATCHID INTEGER default -1 not null primary key,\n"
                    + "	HOMETEAM VARCHAR(120) not null,\n"
                    + "	AWAYTEAM VARCHAR(120) not null,\n"
                    + "	MATCHDATE DATE,\n"
                    + "	TOSSWINNER VARCHAR(120) not null,\n"
                    + "	BCW VARCHAR(120) not null,\n"
                    + "	ONE1ID INTEGER not null,\n"
                    + "	TWO1ID INTEGER not null,\n"
                    + "	ONE2ID INTEGER not null,\n"
                    + "	TWO2ID INTEGER not null,\n"
                    + "	HOMESCORE VARCHAR(120) not null,\n"
                    + "	AWAYSCORE VARCHAR(120) not null,\n"
                    + "	RESULT VARCHAR(120),\n"
                    + "	GROUNDNAME VARCHAR(120),\n"
                    + "	TEAMATHOME VARCHAR(120),\n"
                    + "	TEAMATAWAY VARCHAR(120)\n"
                    + ")";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Unable to create TESTMATCH");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }

        try {
            String sql = "create table \"APP\".TESTINNING\n"
                    + "(\n"
                    + "	ID INTEGER GENERATED ALWAYS AS IDENTITY not null primary key,\n"
                    + "	PARAM1 VARCHAR(20),\n"
                    + "	PARAM2 VARCHAR(20),\n"
                    + "	PARAM3 VARCHAR(20),\n"
                    + "	PARAM4 VARCHAR(20),\n"
                    + "	PARAM5 VARCHAR(20),\n"
                    + "	PARAM6 VARCHAR(20) )";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Unable to create TESTINNING");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }

        try {
            String sql = "create table \"APP\".HEADERS\n"
                    + "(\n"
                    + "	MATCHTYPE INTEGER not null primary key,\n"
                    + "	PARAM1 VARCHAR(100),\n"
                    + "	PARAM2 VARCHAR(100),\n"
                    + "	PARAM3 VARCHAR(100),\n"
                    + "	PARAM4 VARCHAR(100),\n"
                    + "	PARAM5 VARCHAR(100),\n"
                    + "	PARAM6 VARCHAR(100),\n"
                    + "	PARAM7 VARCHAR(100),\n"
                    + "	PARAM8 VARCHAR(100)\n"
                    + ")";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);

            con.close();
        } catch (SQLException ex) {
            System.out.println("Unable to create HEADERS");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        try {
            String sql = "create table \"APP\".MATCHES\n"
                    + "(\n"
                    + "	MATCHID INTEGER default -1 not null primary key,\n"
                    + "	HOMETEAM VARCHAR(120) not null,\n"
                    + "	AWAYTEAM VARCHAR(120) not null,\n"
                    + "	MATCHDATE TIMESTAMP,\n"
                    + "	TOSSWINNER VARCHAR(120) not null,\n"
                    + "	BCW VARCHAR(120) not null,\n"
                    + "	INNING1_ID INTEGER not null,\n"
                    + "	INNING2_ID INTEGER not null,\n"
                    + "	HOMESCORE VARCHAR(120) not null,\n"
                    + "	AWAYSCORE VARCHAR(120) not null,\n"
                    + "	RESULT VARCHAR(120),\n"
                    + "	GROUNDNAME VARCHAR(120),\n"
                    + "	MATCHTYPE INTEGER\n"
                    + ")";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Unable to create MATCHES");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        try {
            String sql = "create table \"APP\".INNINGS\n"
                    + "(\n"
                    + "	ID INTEGER GENERATED ALWAYS AS IDENTITY not null primary key,\n"
                    + "	PARAM1 VARCHAR(20),\n"
                    + "	PARAM2 VARCHAR(20),\n"
                    + "	PARAM3 VARCHAR(20),\n"
                    + "	PARAM4 VARCHAR(20),\n"
                    + "	PARAM5 VARCHAR(20),\n"
                    + "	PARAM6 VARCHAR(20),\n"
                    + "	PARAM7 VARCHAR(20),\n"
                    + "	PARAM8 VARCHAR(20)\n"
                    + ")";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Unable to create INNING");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        try {
            String sql = "INSERT INTO APP.HEADERS (MATCHTYPE, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6,PARAM7,PARAM8) VALUES "
                    + "(117,    'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(2,     'First Over', 'First ten Overs', 'Last ten Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(158,   'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(159,   'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(748,   'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(205,   'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(3,     'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Unable to fill HEADERS");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        try {
            String sql = "create table \"APP\".FAVOURITES\n"
                    + "(\n"
                    + "	MATCHID INTEGER not null primary key,\n"
                    + "	FAVTEAM VARCHAR(120) not null,\n"
                    + "	BIAS VARCHAR(120) not null,"
                    + " OVERALLOHL blob )";
                                                                                                                                                                                                                                                                   
            
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Unable to create FAVOURITES");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        try {
            String sql = "create table \"APP\".HOMEGROUNDS\n"
                    + "(\n"
                    + "	MATCHTYPE INTEGER not null ,\n"
                    + "	GROUNDNAME VARCHAR(120) not null,\n"
                    + "	TEAMNAME VARCHAR(120)\n"
                    + ")";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Unable to create HOMEGROUNDS");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        try {
            String sql = "create table \"APP\".OHL\n"
                    + "(\n"
                    + "	MATCHID INTEGER not null primary key,\n"
                    + "	OHLOBJ blob )";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Unable to create OHL");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

//
//        try {
//            String sql = "UPDATE APP.MATCHES SET HOMETEAM = 'Delhi Capitals' WHERE HOMETEAM = 'Delhi Daredevils' ";
//
//            con = getConnection();
//            stmt = con.createStatement();
//            stmt.execute(sql);
//
//            con.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }finally {
//            try { rs.close(); } catch (Exception e) {  }
//            try { stmt.close(); } catch (Exception e) { }
//            try { con.close(); } catch (Exception e) {  }
//        }
//
//        try {
//            String sql = "UPDATE APP.MATCHES SET awayteam = 'Delhi Capitals' WHERE awayteam = 'Delhi Daredevils' ";
//
//            con = getConnection();
//            stmt = con.createStatement();
//            stmt.execute(sql);
//
//            con.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }finally {
//            try { rs.close(); } catch (Exception e) {  }
//            try { stmt.close(); } catch (Exception e) {  }
//            try { con.close(); } catch (Exception e) {  }
//        }
//
//
//        try {
//            String sql = "UPDATE APP.MATCHES SET tosswinner = 'Delhi Capitals , elected to field first' WHERE tosswinner = 'Delhi Daredevils , elected to field first' ";
//
//            con = getConnection();
//            stmt = con.createStatement();
//            stmt.execute(sql);
//
//            sql = "UPDATE APP.MATCHES SET tosswinner = 'Delhi Capitals , elected to bat first' WHERE tosswinner = 'Delhi Daredevils , elected to bat first' ";
//            Statement st = con.createStatement();
//            st.execute(sql);
//
//
//            con.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }finally {
//            try { rs.close(); } catch (Exception e) {  }
//            try { stmt.close(); } catch (Exception e) {  }
//            try { con.close(); } catch (Exception e) {  }
//        }
    }

    public void initHomeGrounds() {
        Map<String, String> impTeamMap = DataFetch.DataFetch.impTeamIndices;

        for (String teamName : impTeamMap.keySet()) {
            Document homeTeamGrounds = null;
            String homeGrUrl = "https://www.espncricinfo.com/ci/content/ground/grounds.html?object_id=index&country=" + impTeamMap.get(teamName);
            try {
                homeTeamGrounds = Jsoup.connect(homeGrUrl).get();
            } catch (Exception ex) {
                System.out.println("searchphrase xxx : unable to access home ground page");
                ex.printStackTrace();
            }

            List<String> homeGroundNames = homeTeamGrounds.getElementsByClass("grdLinks").eachText();

            for (String grName : homeGroundNames) {
                updateHomeGround(1, grName, teamName);
                updateHomeGround(2, grName, teamName);
                updateHomeGround(3, grName, teamName);
            }
        }
    }

    public Inning gettestInning(int id, Connection con) {
        Statement stmt = null;
        ResultSet rs = null;
        Inning inning = null;

        try {

            String sql = "select * from APP.TESTINNING where id = " + id + "";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int inningId = rs.getInt("id");

                List<String> params = new ArrayList<>();
                for (int i = 1; i <= 6; i++) {
                    params.add(rs.getString("param" + i));
                }

                inning = new Inning(params);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return inning;
    }

    public List<String> getHeaders(int matchType) {
        List<String> headers = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "SELECT * FROM APP.HEADERS WHERE MATCHTYPE=" + matchType;

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next() && matchType != 1) {
                for (int i = 1; i <= 8; i++) {
                    headers.add(rs.getString("param" + i));
                }
            } else if (matchType == 1) {
                String one = "Total Runs";
                String two = "Sixes";
                String three = "fours";
                String four = "Runs before First Wicket";
                String five = "Runs after 5 Wickets";
                String six = "winner";
                headers.add(one);
                headers.add(two);
                headers.add(three);
                headers.add(four);
                headers.add(five);
                headers.add(six);

            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return headers;
    }

    public void addMatch(Match match) throws Exception {

        Connection con = null;
        Statement s = null;
        ResultSet r = null;
        try {
            con = getConnection();
            String sq = "select * from APP.MATCHES WHERE MATCHID=" + String.valueOf(match.getMatchId());
            s = con.createStatement();
            r = s.executeQuery(sq);
            if (r.next()) {
                throw new Exception("Match " + String.valueOf(match.getMatchId()) + " already exists in DB");
                
            }

            int inn1 = addInning(match.getInningOne());

            int inn2 = addInning(match.getInningTwo());

            String sql = "insert into APP.MATCHES (MATCHID, HOMETEAM, AWAYTEAM, MATCHDATE, TOSSWINNER, BCW, INNING1_ID, INNING2_ID, HOMESCORE, AWAYSCORE, RESULT, GROUNDNAME, MATCHTYPE) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, match.getMatchId());
            ps.setString(2, match.getHomeTeam());
            ps.setString(3, match.getAwayTeam());
            ps.setTimestamp(4, match.getMatchDate());
            ps.setString(5, match.getTossWinner());
            ps.setString(6, match.getBCW());
            ps.setInt(7, inn1);
            ps.setInt(8, inn2);
            ps.setString(9, match.getHomeScore());
            ps.setString(10, match.getAwayScore());

            ps.setString(11, match.getResult());
            ps.setString(12, match.getGroundName());
            ps.setString(13, String.valueOf(match.getMatchType()));
            ps.execute();

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
            //todo: show that ur unable to connect to db
        } finally {
            try {
                r.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                s.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
    }

    public boolean updateMatch(int matchID, Match m) {

        Connection con = null;
        Statement s = null;
        ResultSet rs = null;
        Statement s2 = null;
        Statement s3 = null;
        
        try {
            con = getConnection();
            String sq0 = "update APP.MATCHES SET HOMETEAM=?, AWAYTEAM=?, MATCHDATE=?, TOSSWINNER=?, BCW=?, HOMESCORE=?, AWAYSCORE=?, RESULT=?, GROUNDNAME=?, MATCHTYPE=? WHERE MATCHID=" + matchID;
            PreparedStatement ps0 = con.prepareStatement(sq0);
            ps0.setString(1, m.getHomeTeam());
            ps0.setString(2, m.getAwayTeam());
            ps0.setTimestamp(3, m.getMatchDate());
            ps0.setString(4, m.getTossWinner());
            ps0.setString(5, m.getBCW());
            ps0.setString(6, m.getHomeScore());
            ps0.setString(7, m.getAwayScore());
            ps0.setString(8, m.getResult());
            ps0.setString(9, m.getGroundName());
            ps0.setInt(10, m.getMatchType());

            ps0.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        try {
            con = getConnection();
            String sq = "select * from APP.MATCHES WHERE MATCHID=" + matchID;
            s = con.createStatement();
            rs = s.executeQuery(sq);
            if (rs.next()) {
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");

                Inning one = m.getInningOne();
                Inning two = m.getInningTwo();

//                int matchType = rs.getInt("matchtype");
                String sq1 = "update APP.INNINGS SET PARAM1 =?,PARAM2=?,PARAM3=?,PARAM4=?,PARAM5=?,PARAM6=?,PARAM7=?,PARAM8=? WHERE ID = " + inning1_id;

                String sq2 = "update APP.INNINGS SET PARAM1 =?,PARAM2=?,PARAM3=?,PARAM4=?,PARAM5=?,PARAM6=?,PARAM7=?,PARAM8=? WHERE ID = " + inning2_id;

                PreparedStatement ps = con.prepareStatement(sq1);
                for (int it = 1; it <= 8; it++) {
                    ps.setString(it, one.getParams().get(it - 1));
                }
                PreparedStatement ps2 = con.prepareStatement(sq2);
                for (int it = 1; it <= 8; it++) {
                    ps2.setString(it, two.getParams().get(it - 1));
                }
                ps.executeUpdate();
                ps2.executeUpdate();

            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                s.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }

        return true;
    }

    public boolean updatetestMatch(int matchID, testMatch m) {

        Connection con = null;
        Statement s = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sq0 = "update APP.TESTMATCH SET HOMETEAM=?, AWAYTEAM=?, MATCHDATE=?, TOSSWINNER=?, BCW=?, HOMESCORE=?, AWAYSCORE=?, RESULT=?, GROUNDNAME=?, TEAMATHOME=?,TEAMATAWAY=? WHERE MATCHID=" + matchID;
            PreparedStatement ps0 = con.prepareStatement(sq0);
            ps0.setString(1, m.getHomeTeam());
            ps0.setString(2, m.getAwayTeam());
            ps0.setDate(3, m.getMatchDate());
            ps0.setString(4, m.getTossWinner());
            ps0.setString(5, m.getBCW());
            ps0.setString(6, m.getHomeScore());
            ps0.setString(7, m.getAwayScore());
            ps0.setString(8, m.getResult());
            ps0.setString(9, m.getGroundName());
            ps0.setString(10, m.getteamathome());
            ps0.setString(11, m.getteamataway());

            ps0.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        try {
            con = getConnection();
            String sq = "select * from APP.TESTMATCH WHERE MATCHID=" + matchID;
            s = con.createStatement();
            rs = s.executeQuery(sq);
            if (rs.next()) {
                int one1id = rs.getInt("one1id");
                int two1id = rs.getInt("two1id");
                int one2id = rs.getInt("one2id");
                int two2id = rs.getInt("two2id");

                Inning one1 = m.getInningOne1();
                Inning one2 = m.getInningOne2();
                Inning two1 = m.getInningTwo1();
                Inning two2 = m.getInningTwo2();

                String sq1 = "update APP.TESTINNING SET PARAM1 =?, PARAM2=?, PARAM3=?,PARAM4=?, PARAM5=?, PARAM6=? WHERE ID = " + one1id;

                String sq2 = "update APP.TESTINNING SET PARAM1 =?, PARAM2=?, PARAM3=?,PARAM4=?, PARAM5=?, PARAM6=? WHERE ID = " + two1id;

                String sq3 = "update APP.TESTINNING SET PARAM1 =?, PARAM2=?, PARAM3=?,PARAM4=?, PARAM5=?, PARAM6=? WHERE ID = " + one2id;

                String sq4 = "update APP.TESTINNING SET PARAM1 =?, PARAM2=?, PARAM3=?,PARAM4=?, PARAM5=?, PARAM6=? WHERE ID = " + two2id;

                PreparedStatement ps = con.prepareStatement(sq1);
                for (int it = 1; it <= 6; it++) {
                    ps.setString(it, one1.getParams().get(it - 1));
                }

                PreparedStatement ps2 = con.prepareStatement(sq2);
                for (int it = 1; it <= 6; it++) {
                    ps2.setString(it, two1.getParams().get(it - 1));
                }

                PreparedStatement ps3 = con.prepareStatement(sq3);
                for (int it = 1; it <= 6; it++) {
                    ps3.setString(it, one2.getParams().get(it - 1));
                }

                PreparedStatement ps4 = con.prepareStatement(sq4);
                for (int it = 1; it <= 6; it++) {
                    ps4.setString(it, two2.getParams().get(it - 1));
                }

                ps.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();
                ps4.executeUpdate();

            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                s.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return true;
    }

    public int addInning(Inning i) throws Exception {
        int id = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = null;
            rs = null;
            if (i.getParams().size() != 8) {
                throw new Exception("Inning params of not size 8");
            }
            String sql = "insert into APP.INNINGS (param1, param2, param3, param4, param5, param6, param7, param8) values(?,?,?,?,?,?,?,?)";
            String generatedColumns[] = {"ID"};
            ps = con.prepareStatement(sql, generatedColumns);
            for (int it = 1; it <= 8; it++) {
                ps.setString(it, i.getParams().get(it - 1));
            }
            ps.execute();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            //todo: show that ur unable to connect to db
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return id;
    }

    // case 0:any side ; 1: batting; 2: bowling
    public List<testMatch> getTestMatches(String teamName, int caseNo, Location type) {

        List<testMatch> testmatches = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql;
        String addCondn = "";
        if (type.equals(Location.HOME)) {
            addCondn = "AND teamathome = '" + teamName + "' ";
        } else if (type.equals(Location.AWAY)) {
            addCondn = "AND teamataway = '" + teamName + "' ";
        }

        switch (caseNo) {
            case 0:
                try {
                    con = getConnection();
                    if (teamName.equals("-1")) {
                        sql = "select * from APP.TESTMATCH where ORDER BY MATCHDATE DESC";
                    } else {
                        sql = "select * from APP.TESTMATCH where (hometeam = '" + teamName + "' OR awayteam = '" + teamName + "' ) " + addCondn + "  ORDER BY MATCHDATE DESC";
                    }

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String BCW = rs.getString("BCW");
                        int one1 = rs.getInt("one1id");
                        int two1 = rs.getInt("two1id");
                        int one2 = rs.getInt("one2id");
                        int two2 = rs.getInt("two2id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
                        String teamathome = rs.getString("teamathome");
                        String teamataway = rs.getString("teamataway");

                        Inning inningOne1 = gettestInning(one1, con);
                        Inning inningTwo1 = gettestInning(two1, con);
                        Inning inningOne2 = gettestInning(one2, con);
                        Inning inningTwo2 = gettestInning(two2, con);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1, inningOne2, inningTwo2, homeScore, awayscore, result, groundName, teamathome, teamataway);
                        testmatches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        stmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        con.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
                break;
// all home matches batting first               
            case 1:

                try {
                    con = getConnection();
                    sql = "select * from APP.TESTMATCH where HOMETEAM = '" + teamName + "' " + addCondn + " ORDER BY MATCHDATE DESC";
                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String BCW = rs.getString("BCW");
                        int one1 = rs.getInt("one1id");
                        int two1 = rs.getInt("two1id");
                        int one2 = rs.getInt("one2id");
                        int two2 = rs.getInt("two2id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
                        String teamathome = rs.getString("teamathome");
                        String teamataway = rs.getString("teamataway");

                        Inning inningOne1 = gettestInning(one1, con);
                        Inning inningTwo1 = gettestInning(two1, con);
                        Inning inningOne2 = gettestInning(one2, con);
                        Inning inningTwo2 = gettestInning(two2, con);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1, inningOne2, inningTwo2, homeScore, awayscore, result, groundName, teamathome, teamataway);
                        testmatches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        stmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        con.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
                break;
//bowling first               
            case 2:

                try {
                    con = getConnection();
                    sql = "select * from APP.TESTMATCH where AWAYTEAM = '" + teamName + "' " + addCondn + " order by MATCHDATE DESC";
                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String BCW = rs.getString("BCW");
                        int one1 = rs.getInt("one1id");
                        int two1 = rs.getInt("two1id");
                        int one2 = rs.getInt("one2id");
                        int two2 = rs.getInt("two2id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");
                        String teamathome = rs.getString("teamathome");
                        String teamataway = rs.getString("teamataway");

                        Inning inningOne1 = gettestInning(one1, con);
                        Inning inningTwo1 = gettestInning(two1, con);
                        Inning inningOne2 = gettestInning(one2, con);
                        Inning inningTwo2 = gettestInning(two2, con);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1, inningOne2, inningTwo2, homeScore, awayscore, result, groundName, teamathome, teamataway);
                        testmatches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        stmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        con.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
                break;
            default:
                break;
        }
        return testmatches;
    }
    
    private Timestamp get110thTimestamp(int matchType) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        
        try {
            con = getConnection();
            String sql = "select * from APP.MATCHES where matchtype = " + matchType + " order by MATCHDATE DESC OFFSET 100 ROWS FETCH FIRST 1 ROWS ONLY";

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {

                Timestamp matchDate = rs.getTimestamp("matchdate");
                if (matchDate != null) {
                    return matchDate;
                }
            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return new Timestamp(1990, 1, 1, 1, 1, 1, 1);
    }

    public List<Match> getMatches(String teamName, int matchType, int type) {

        List<Match> matches = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        switch (type) {
            case 0:

                try {
                    con = getConnection();
                    String sql = "select * from APP.MATCHES where (hometeam = '" + teamName + "' OR awayteam = '" + teamName + "' )  and matchtype = " + matchType + " order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Timestamp matchDate = rs.getTimestamp("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String BCW = rs.getString("BCW");
                        int inning1_id = rs.getInt("inning1_id");
                        int inning2_id = rs.getInt("inning2_id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                        Inning inningOne = getInning(inning1_id, con);
                        Inning inningTwo = getInning(inning2_id, con);

                        Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                        matches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        stmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        con.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
                break;
            case 1:

                try {
                    con = getConnection();
                    String sql = "select * from APP.MATCHES where hometeam = '" + teamName + "' and matchtype = " + matchType + " order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Timestamp matchDate = rs.getTimestamp("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String BCW = rs.getString("BCW");
                        int inning1_id = rs.getInt("inning1_id");
                        int inning2_id = rs.getInt("inning2_id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                        Inning inningOne = getInning(inning1_id, con);
                        Inning inningTwo = getInning(inning2_id, con);

                        Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                        matches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        stmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        con.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
                break;
            case 2:

                try {
                    con = getConnection();
                    String sql = "select * from APP.MATCHES where awayteam = '" + teamName + "' "
                            + "and matchtype = " + matchType + " order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Timestamp matchDate = rs.getTimestamp("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String BCW = rs.getString("BCW");
                        int inning1_id = rs.getInt("inning1_id");
                        int inning2_id = rs.getInt("inning2_id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                        Inning inningOne = getInning(inning1_id, con);
                        Inning inningTwo = getInning(inning2_id, con);

                        Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                        matches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        stmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        con.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
                break;
            default:
                break;
        }
        return matches;

    }

    public Inning getInning(int id, Connection conn) {
        Connection con = conn;
        Statement stmt = null;
        ResultSet rs = null;
        Inning inning = null;

        try {
            String sql = "select * from APP.INNINGS where id = " + id + "";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int inningId = rs.getInt("id");
                List<String> params = new ArrayList<>();
                int noOfParams = 8;

                for (int i = 1; i <= noOfParams; i++) {
                    params.add(rs.getString("param" + i));
                }

                inning = new Inning(params);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return inning;
    }

    public List<Match> getGroundInfo(String groundName, int matchType) {
        List<Match> matches = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "select * from APP.MATCHES where groundname = ? AND MATCHTYPE = ? order by MATCHDATE DESC";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, groundName);
            stmt.setInt(2, matchType);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Timestamp matchDate = rs.getTimestamp("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String BCW = rs.getString("BCW");
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");

                String result = rs.getString("result");
//                String groundName = rs.getString("groundname");

                Inning inningOne = getInning(inning1_id, con);
                Inning inningTwo = getInning(inning2_id, con);

                Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }

        return matches;
    }

    public List<testMatch> getTestGroundInfo(String groundName) {
        List<testMatch> testmatches = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "select * from APP.TESTMATCH where groundname = ? order by MATCHDATE DESC";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, groundName);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String BCW = rs.getString("BCW");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String teamathome = rs.getString("teamathome");
                String teamataway = rs.getString("teamataway");
                String result = rs.getString("result");
//                String groundName = rs.getString("groundname");

                Inning inningOne1 = gettestInning(one1, con);
                Inning inningTwo1 = gettestInning(two1, con);
                Inning inningOne2 = gettestInning(one2, con);
                Inning inningTwo2 = gettestInning(two2, con);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1, inningOne2, inningTwo2, homeScore, awayscore, result, groundName, teamathome, teamataway);
                testmatches.add(m);
            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }

        return testmatches;
    }

    public List<String> getGroundList(int matchType) {
        List<String> grounds = new ArrayList<String>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;

        try {
            con = getConnection();
            if (matchType == 1) {
                sql = "select distinct groundname from APP.TESTMATCH order by groundname ASC";
            } else {
                sql = "select distinct groundname from APP.MATCHES where MATCHTYPE = " + matchType + " order by groundname ASC";
            }
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String gName = rs.getString("groundname");
                if (!grounds.contains(gName)) {
                    grounds.add(gName);
                }
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return grounds;
    }

    public List<String> getTeamsList(int matchType) {
        List<String> teams = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "select distinct HOMETEAM from APP.MATCHES where MATCHTYPE = " + matchType + " "
                    + "union select distinct AWAYTEAM from APP.MATCHES where MATCHTYPE = " + matchType + "";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
//                ResultSetMetaData md = rs.getMetaData();
//                int colCount = md.getColumnCount();
//
//                for (int i = 1; i <= colCount; i++) {
//                    String col_name = md.getColumnName(i);
//                    System.out.println("col:"+col_name);
//                }

                String tName = rs.getString("1");
                if (!teams.contains(tName)) {
                    teams.add(tName);
                }
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return teams;
    }

    public List<String> getTestTeamsList() {
        int matchType = 1;
        List<String> teams = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "select distinct HOMETEAM from APP.TESTMATCH "
                    + "union select distinct AWAYTEAM from APP.TESTMATCH ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
//                ResultSetMetaData md = rs.getMetaData();
//                int colCount = md.getColumnCount();
//
//                for (int i = 1; i <= colCount; i++) {
//                    String col_name = md.getColumnName(i);
//                    System.out.println("col:"+col_name);
//                }

                String tName = rs.getString("1");
                if (!teams.contains(tName)) {
                    teams.add(tName);
                }
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return teams;
    }

    public String getLastMatchDate() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String op = "(N/A)";
        Timestamp lastDate = new Timestamp(0);
        try {
            con = getConnection();
            String sql = "select * from APP.MATCHES order by MATCHDATE DESC FETCH FIRST 1 ROW ONLY";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Timestamp matchDate = rs.getTimestamp("matchdate");

                lastDate = matchDate;
                op = matchDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("d MMM uuuu"));
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Matches table doesnt exist yet");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }

        try {
            con = getConnection();
            String sql = "select * from APP.TESTMATCH order by MATCHDATE DESC FETCH FIRST 1 ROW ONLY";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Date matchDate = rs.getDate("matchdate");

                if (matchDate.after(lastDate)) {
                    op = matchDate.toLocalDate().format(DateTimeFormatter.ofPattern("d MMM uuuu"));
                }

            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("testMatch teable doesnt exist yet");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }

        return op;
    }
//    BaseDAO db = new BaseDAO();;

    public List<String> getLoadedMatchIDs(int matchType) {
        List<String> matches = new ArrayList<String>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;

        try {
            con = getConnection();
            if (matchType == 1) {
                sql = "select MATCHID from APP.TESTMATCH";
            } else {
                sql = "select MATCHID from APP.MATCHES where MATCHTYPE = " + matchType + " ";
            }
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String mName = rs.getString("matchid");
                matches.add(mName);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return matches;
    }

    public boolean updateNameTable(String oldName, String newName) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;

        if (oldName == null || oldName.trim().isEmpty() || newName == null || newName.trim().isEmpty()) {
            return false;
        }

        try {
            con = getConnection();

            sql = "SELECT * FROM APP.TEAMNAMES where OLDNAME='" + oldName + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                sql = "UPDATE APP.TEAMNAMES SET NEWNAME = '" + newName + "' WHERE OLDNAME='" + oldName + "' ";
            } else {
                sql = "INSERT INTO APP.TEAMNAMES (OLDNAME, NEWNAME) \n"
                        + " VALUES ('" + oldName + "', '" + newName + "')";
            }

            stmt.executeUpdate(sql);

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        return true;
    }
    
    public boolean removeEntryFromEditTeamNameDB(String oldName, String newName) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;

        System.out.println("Deleting Edit team name DB; OldName:" + oldName +" , NewName: " + newName);
        if (oldName == null || oldName.trim().isEmpty() || newName == null || newName.trim().isEmpty()) {
            return false;
        }

        try {
            con = getConnection();

            sql = "DELETE FROM APP.TEAMNAMES where OLDNAME='" + oldName + "' AND NEWNAME='"+ newName +"' ";
            stmt = con.createStatement();
            stmt.execute(sql);

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        return true;
    }

    public boolean updateNameDB(String oldName, String newName) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;

        try {
            con = getConnection();
            stmt = con.createStatement();
            sql = "UPDATE APP.MATCHES SET HOMETEAM ='" + newName + "' WHERE HOMETEAM = '" + oldName + "'";

            stmt.executeUpdate(sql);

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        try {
            con = getConnection();
            stmt = con.createStatement();
            sql = "UPDATE APP.MATCHES SET AWAYTEAM ='" + newName + "' WHERE AWAYTEAM = '" + oldName + "'";

            stmt.executeUpdate(sql);

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        return true;
    }

    public String[] getFavourites(int matchID) {
        String[] favTeam = new String[2];

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;

        try {
            con = getConnection();
            sql = "select * from APP.FAVOURITES where MATCHID = " + matchID + " ";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                favTeam[0] = rs.getString("favteam");
                favTeam[1] = rs.getString("bias");
                return favTeam;

            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return null;
    }
    
    public OverallOHL getOverallOHL(int matchID) {
        OverallOHL ohl = null;
        
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;

        try {
            con = getConnection();
            sql = "select * from APP.FAVOURITES where MATCHID = " + matchID + " ";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                byte[] buf = rs.getBytes("OVERALLOHL");
                if (buf != null) {
                    ObjectInputStream objectIn = new ObjectInputStream(
                            new ByteArrayInputStream(buf));
                    ohl = (OverallOHL) objectIn.readObject();

                    objectIn.close();
                }

            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return ohl;
    }

    public boolean updateFavourites(int matchID, String favTeam, String bias, OverallOHL ohl) {
        Connection con = null;
        Statement stmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int nr = 0;
        String sql;

        try {
            con = getConnection();

            sql = "SELECT * FROM APP.FAVOURITES where MATCHID=" + matchID + " ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                sql = "UPDATE APP.FAVOURITES SET FAVTEAM = '" + favTeam + "', BIAS = '" + bias + "', "
                        + " OVERALLOHL = ? "
                        + "WHERE MATCHID=" + matchID + " ";
            } else {
                sql = "INSERT INTO APP.FAVOURITES (MATCHID, FAVTEAM, BIAS, OVERALLOHL) \n"
                        + " VALUES (" + matchID + ", '" + favTeam + "', '" + bias + "', ? )";
            }

            ps = con.prepareStatement(sql);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(baos);
            oout.writeObject(ohl);
            oout.close();

            ps.setBytes(1, baos.toByteArray());

            nr = ps.executeUpdate();

            con.close();
            return true;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
    }

    public boolean deleteMatch(int matchID, boolean isTest) {
        Connection con = null;
        Statement stmt = null;
        int nr = 0;
        String sql;

        try {
            con = getConnection();
            if (isTest) {
                sql = "Delete from APP.TESTMATCH where MATCHID = " + matchID;
            } else {
                sql = "Delete from APP.MATCHES where MATCHID = " + matchID;
            }
            stmt = con.createStatement();
            nr = stmt.executeUpdate(sql);

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return nr > 0;
    }

    public Map<String, String> getHomeGroundMap(int matchType) {
        Map<String, String> homeGrounds = new TreeMap<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "SELECT * FROM APP.HOMEGROUNDS WHERE MATCHTYPE=" + matchType;

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String groundName = rs.getString("GROUNDNAME");
                String teamName = rs.getString("TEAMNAME");
                homeGrounds.put(groundName, teamName);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return homeGrounds;
    }

    public boolean updateHomeGround(int matchType, String groundName, String teamName) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        int nr = 0;
        String sql;

        try {
            con = getConnection();

            sql = "SELECT * FROM APP.HOMEGROUNDS where MATCHTYPE=" + matchType + " "
                    + "AND GROUNDNAME = '" + groundName + "' ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                sql = "UPDATE APP.HOMEGROUNDS SET TEAMNAME = '" + teamName + "'  "
                        + "WHERE MATCHTYPE=" + matchType + " and GROUNDNAME = '" + groundName + "' ";
            } else {
                System.out.println("-----------------------------------------------------------");
                System.out.println(sql);
                sql = "INSERT INTO APP.HOMEGROUNDS (MATCHTYPE, GROUNDNAME, TEAMNAME) \n"
                        + " VALUES (" + matchType + ", '" + groundName + "', '" + teamName + "')";
            }

            nr = stmt.executeUpdate(sql);

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return nr > 0;
    }

    public List<String> getHomeGroundsFor(String teamName, int matchType) {
        List<String> homeGrounds = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "SELECT GROUNDNAME FROM APP.HOMEGROUNDS WHERE MATCHTYPE = " + matchType + " "
                    + "AND TEAMNAME = '" + teamName + "' ";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String groundName = rs.getString("GROUNDNAME");
                homeGrounds.add(groundName);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return homeGrounds;
    }

    public void deleteHomeGround(String groundName, int matchType) {
        Connection con = null;
        Statement stmt = null;
        int nr = 0;

        try {
            con = getConnection();
            String sql = "DELETE FROM APP.HOMEGROUNDS WHERE MATCHTYPE=" + matchType + ""
                    + "AND GROUNDNAME = '" + groundName + "' ";

            stmt = con.createStatement();
            nr = stmt.executeUpdate(sql);

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
    }

    public List<Match> getFavMatches(int matchType, boolean isBatting) {
        List<Match> favMatches = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql;

        try {
            con = getConnection();
            if (isBatting) {
                sql = "select * from APP.MATCHES INNER JOIN APP.FAVOURITES ON APP.MATCHES.MATCHID = APP.FAVOURITES.MATCHID "
                        + "WHERE HOMETEAM = FAVTEAM AND MATCHTYPE = " + matchType + " order by MATCHDATE DESC";
            } else {
                sql = "select * from APP.MATCHES AS A INNER JOIN APP.FAVOURITES AS B ON A.MATCHID = B.MATCHID "
                        + "WHERE AWAYTEAM = FAVTEAM AND MATCHTYPE = " + matchType + " order by MATCHDATE DESC";
            }

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Timestamp matchDate = rs.getTimestamp("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String BCW = rs.getString("BCW");
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                Inning inningOne = getInning(inning1_id, con);
                Inning inningTwo = getInning(inning2_id, con);

                Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                favMatches.add(m);
            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return favMatches;
    }

    public List<Match> getAllFavMatches(int matchType) {
        List<Match> favMatches = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql;

        try {
            con = getConnection();
            sql = "select * from APP.MATCHES INNER JOIN APP.FAVOURITES ON APP.MATCHES.MATCHID = APP.FAVOURITES.MATCHID "
                    + "WHERE MATCHTYPE = " + matchType + " order by MATCHDATE DESC";

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Timestamp matchDate = rs.getTimestamp("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String BCW = rs.getString("BCW");
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                Inning inningOne = getInning(inning1_id, con);
                Inning inningTwo = getInning(inning2_id, con);

                Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                favMatches.add(m);
              
            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return favMatches;
    }

    public List<testMatch> getAllFavTestMatches() {
        List<testMatch> favMatches = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql;

        try {
            con = getConnection();
            sql = "select * from APP.TESTMATCH INNER JOIN APP.FAVOURITES ON APP.MATCHES.MATCHID = APP.FAVOURITES.MATCHID "
                    + "WHERE order by MATCHDATE DESC";

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String BCW = rs.getString("BCW");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String teamathome = rs.getString("teamathome");
                String teamataway = rs.getString("teamataway");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");

                Inning inningOne1 = gettestInning(one1, con);
                Inning inningTwo1 = gettestInning(two1, con);
                Inning inningOne2 = gettestInning(one2, con);
                Inning inningTwo2 = gettestInning(two2, con);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1, inningOne2, inningTwo2, homeScore, awayscore, result, groundName, teamathome, teamataway);
                favMatches.add(m);
            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return favMatches;
    }

    public OHL getOHL(int matchID) {
        OHL ohlObj = null;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;

        try {
            con = getConnection();
            sql = "select * from APP.OHL where MATCHID = " + matchID + " ";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                byte[] buf = rs.getBytes("ohlobj");
                if (buf != null) {
                    ObjectInputStream objectIn = new ObjectInputStream(
                            new ByteArrayInputStream(buf));
                    ohlObj = (OHL) objectIn.readObject();

                    objectIn.close();
                }

            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return ohlObj;
    }

    public boolean updateOHL(int matchID, OHL ohlObj) {
        Connection con = null;
        Statement stmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int nr = 0;
        String sql;

        try {
            con = getConnection();

            sql = "SELECT * FROM APP.OHL where MATCHID=" + matchID + " ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                sql = "UPDATE APP.OHL SET OHLOBJ = ?  "
                        + "WHERE MATCHID=" + matchID + " ";
            } else {
                sql = "INSERT INTO APP.OHL (MATCHID, OHLOBJ) \n"
                        + " VALUES (" + matchID + ", ? )";
            }
            ps = con.prepareStatement(sql);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(baos);
            oout.writeObject(ohlObj);
            oout.close();

            ps.setBytes(1, baos.toByteArray());

            nr = ps.executeUpdate();

            con.close();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                con.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return nr > 0;
    }
}
