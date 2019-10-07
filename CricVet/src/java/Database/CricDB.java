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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.naming.InitialContext;
import models.testInning;
import models.testMatch;


/**
 *
 * @author DELL
 */
public class CricDB extends BaseDAO {
    
    public void addtestMatch(testMatch match) {
        
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

            if (match.getResult().contains("No result")) {
                System.out.println("Match " + String.valueOf(match.getMatchId()) + " has no result");
                return;
            }

            int inn11 = addtestInning(match.getInningOne1());

            int inn21 = addtestInning(match.getInningTwo1());
            int inn12 = addtestInning(match.getInningOne2());

            int inn22 = addtestInning(match.getInningTwo2());

            String sql = "insert into APP.TESTMATCH (MATCHID, HOMETEAM, AWAYTEAM, MATCHDATE, TOSSWINNER, BATTINGFIRST, ONE1ID, TWO1ID, ONE2ID, TWO2ID, HOMESCORE, AWAYSCORE, RESULT, GROUNDNAME, MATCHTYPE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, match.getMatchId());
            ps.setString(2, match.getHomeTeam());
            ps.setString(3, match.getAwayTeam());
            ps.setDate(4, match.getMatchDate());
            ps.setString(5, match.getTossWinner());
            ps.setString(6, match.getBattingFirst());
            ps.setInt(7, inn11);
            ps.setInt(8, inn21);
            ps.setInt(9, inn12);
            ps.setInt(10, inn22);
            
            ps.setString(11, match.getHomeScore());
            ps.setString(12, match.getAwayScore());

            ps.setString(13, match.getResult());
            ps.setString(14, match.getGroundName());
            ps.setString(15, String.valueOf(match.getMatchType()));
            ps.execute();

            con.close();
        } catch (SQLException ex) {

            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            //todo: show that ur unable to connect to db
        }finally {
            try { r.close(); } catch (Exception e) { /* ignored */ }
            try { s.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    
    public int addtestInning(testInning i) {
        System.out.println(i.getNoOfParams());
        System.out.println(i.getParams());
        int id = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
                
        try {
            con = getConnection();
            ps = null;
            rs = null;
            
            String sql = "insert into APP.TESTINNING (TOTALRUNS, SIXES, FOURS, FIRSTWICKET, RUNS5WICKET,WINNER) values(?,?,?,?,?,?)";
            String generatedColumns[] = {"ID"};
            ps = con.prepareStatement(sql, generatedColumns);
            for (int it = 1; it <= i.getNoOfParams(); it++) {
                    ps.setString(it, i.getParams().get(it-1));
                }
                  
            ps.execute();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
                }
              

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            //todo: show that ur unable to connect to db
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return id;
    }
// fetches all test matches
    public List<testMatch> gettestMatch() {
        List<testMatch> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt=null;
        ResultSet rs=null;

        try {
            con = getConnection();

            String sql = "select * from APP.TESTMATCH order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
                int matchType = rs.getInt("matchtype");

                testInning inningOne1 = gettestInning(one1);
                testInning inningTwo1 = gettestInning(two1);
                testInning inningOne2 = gettestInning(one2);
                testInning inningTwo2 = gettestInning(two2);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }

// fetches all TEAM A's test matches
    public List<testMatch> getteamtestMatch(String teamName) {
        List<testMatch> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt=null;
        ResultSet rs=null;

        try {
            con = getConnection();

            String sql = "select * from APP.TESTMATCH where (hometeam='" + teamName + "' OR awayteam='" + teamName + "') order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
                int matchType = rs.getInt("matchtype");

                testInning inningOne1 = gettestInning(one1);
                testInning inningTwo1 = gettestInning(two1);
                testInning inningOne2 = gettestInning(one2);
                testInning inningTwo2 = gettestInning(two2);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }
    
    public List<Match> getHth(int matchType, String A, String B){
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
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                Inning inningOne = getInning(inning1_id);
                Inning inningTwo = getInning(inning2_id);

                Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }
// get team A vs team B HTH
    public List<testMatch> gettestHth(int matchType, String A, String B){
        
        List<testMatch> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
       
        try {
            con = getConnection();

            String sql = "select * from APP.TESTMATCH where matchtype= " + matchType + " and ((hometeam='" + A + "' AND awayteam='" + B + "') OR (hometeam='" + B + "' AND awayteam='" + A + "')) order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                testInning inningOne1 = gettestInning(one1);
                testInning inningTwo1 = gettestInning(two1);
                testInning inningOne2 = gettestInning(one2);
                testInning inningTwo2 = gettestInning(two2);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }

    public List<Match> getDB(int matchType, String TeamName) {
        List<Match> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt=null;
        ResultSet rs=null;

        try {
            con = getConnection();

            String sql = "select * from APP.Matches where matchtype= " + matchType + " and (hometeam='" + TeamName + "' OR awayteam='" + TeamName + "') order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                Inning inningOne = getInning(inning1_id);
                Inning inningTwo = getInning(inning2_id);

                Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }
    
    public List<testMatch> gettestDB(int matchType, String TeamName) {
        List<testMatch> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt=null;
        ResultSet rs=null;

        try {
            con = getConnection();

            String sql = "select * from APP.TESTMATCH where matchtype= " + matchType + " and (hometeam='" + TeamName + "' OR awayteam='" + TeamName + "') order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                testInning inningOne1 = gettestInning(one1);
                testInning inningTwo1 = gettestInning(two1);
                testInning inningOne2 = gettestInning(one2);
                testInning inningTwo2 = gettestInning(two2);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
                
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }

    public void initDB() {

        System.out.println("at init");

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        
        
        /*
        try {
            String sql = "drop table \"APP\".HEADERS";
            con = getConnection();
            stmt = con.createStatement();
            System.out.println("Dropping headers");
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) {  }
        }
        try {
            String sql = "drop table \"APP\".INNINGS";
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) {  }
        }
        try {
            String sql = "drop table \"APP\".MATCHES";
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }
        
        */
        
        
        try {
            String sql = "create table \"APP\".TESTMATCH\n"
                    + "(\n"
                    + "	MATCHID INTEGER default -1 not null primary key,\n"
                    + "	HOMETEAM VARCHAR(120) not null,\n"
                    + "	AWAYTEAM VARCHAR(120) not null,\n"
                    + "	MATCHDATE DATE,\n"
                    + "	TOSSWINNER VARCHAR(120) not null,\n"
                    + "	BATTINGFIRST VARCHAR(120) not null,\n"
                    + "	ONE1ID INTEGER not null,\n"
                    + "	TWO1ID INTEGER not null,\n"
                    + "	ONE2ID INTEGER not null,\n"
                    + "	TWO2ID INTEGER not null,\n"
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        
        try {
            String sql = "create table \"APP\".TESTINNING\n"
                    + "(\n"
                    + "	ID INTEGER GENERATED ALWAYS AS IDENTITY not null primary key,\n"
                    + "	TOTALRUNS VARCHAR(20),\n"
                    + "	SIXES VARCHAR(20),\n"
                    + "	FOURS VARCHAR(20),\n"
                    + "	FIRSTWICKET VARCHAR(20),\n"
                    + "	RUNS5WICKET VARCHAR(20)\n"
                    + ")";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }

        try {
            String sql = "create table \"APP\".MATCHES\n"
                    + "(\n"
                    + "	MATCHID INTEGER default -1 not null primary key,\n"
                    + "	HOMETEAM VARCHAR(120) not null,\n"
                    + "	AWAYTEAM VARCHAR(120) not null,\n"
                    + "	MATCHDATE DATE,\n"
                    + "	TOSSWINNER VARCHAR(120) not null,\n"
                    + "	BATTINGFIRST VARCHAR(120) not null,\n"
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
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
                    + "	PARAM8 VARCHAR(20),\n"
                    + "	NOOFPARAMS INTEGER default 6 not null\n"
                    + ")";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) { }
        }

        try {
            String sql = "INSERT INTO APP.HEADERS (MATCHTYPE, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6,PARAM7,PARAM8) VALUES (117, 'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(2, 'First Over', 'First ten Overs', 'Last ten Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(158, 'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(159, 'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(748, 'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(205, 'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(3, 'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }
        
        
        try {
            String sql = "UPDATE APP.MATCHES SET HOMETEAM = 'Delhi Capitals' WHERE HOMETEAM = 'Delhi Daredevils' ";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) {  }
        }
        
        try {
            String sql = "UPDATE APP.MATCHES SET awayteam = 'Delhi Capitals' WHERE awayteam = 'Delhi Daredevils' ";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }

        try {
            String sql = "UPDATE APP.MATCHES SET battingfirst = 'Delhi Capitals' WHERE battingfirst = 'Delhi Daredevils' ";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }
        
        try {
            String sql = "UPDATE APP.MATCHES SET tosswinner = 'Delhi Capitals , elected to field first' WHERE tosswinner = 'Delhi Daredevils , elected to field first' ";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            
            sql = "UPDATE APP.MATCHES SET tosswinner = 'Delhi Capitals , elected to bat first' WHERE tosswinner = 'Delhi Daredevils , elected to bat first' ";
            Statement st = con.createStatement();
            st.execute(sql);
            

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }

    }
    
    public boolean checktestMatchEntry(int matchId) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "SELECT * FROM APP.TESTMATCH WHERE MATCHID=" + String.valueOf(matchId);

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                con.close();
                return true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return false;
    }
    
    public testInning gettestInning(int id) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        testInning inning = null;

        try {
            con = getConnection();

            String sql = "select * from APP.TESTINNING where id = " + id + "";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int inningId = rs.getInt("id");
                int totalruns = rs.getInt("totalruns");
                int sixes = rs.getInt("sixes");
                int fours = rs.getInt("fours");
                int firstwicket = rs.getInt("firstwicket");
                int runs5wicket = rs.getInt("runs5wicket");
                String winner = rs.getString("winner");

                //for (int i = 1; i <= noOfParams; i++) {
                //    params.add(rs.getString("param" + i));
                //}

                inning = new testInning(inningId, totalruns, sixes, fours, firstwicket,runs5wicket,winner);

            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return headers;
    }

    public void addMatch(Match match) {
        
        Connection con = null;
        Statement s = null;
        ResultSet r = null;
        try {
            con = getConnection();
            String sq = "select * from APP.MATCHES WHERE MATCHID=" + String.valueOf(match.getMatchId());
            s = con.createStatement();
            r = s.executeQuery(sq);
            if (r.next()) {
                System.out.println("Match " + String.valueOf(match.getMatchId()) + " already exists in DB");
                return;
            }

            if (match.getResult().contains("No result")) {
                System.out.println("Match " + String.valueOf(match.getMatchId()) + " has no result");
                return;
            }

            int inn1 = addInning(match.getInningOne());

            int inn2 = addInning(match.getInningTwo());

            String sql = "insert into APP.MATCHES (MATCHID, HOMETEAM, AWAYTEAM, MATCHDATE, TOSSWINNER, BATTINGFIRST, INNING1_ID, INNING2_ID, HOMESCORE, AWAYSCORE, RESULT, GROUNDNAME, MATCHTYPE) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, match.getMatchId());
            ps.setString(2, match.getHomeTeam());
            ps.setString(3, match.getAwayTeam());
            ps.setDate(4, match.getMatchDate());
            ps.setString(5, match.getTossWinner());
            ps.setString(6, match.getBattingFirst());
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

            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            //todo: show that ur unable to connect to db
        }finally {
            try { r.close(); } catch (Exception e) { /* ignored */ }
            try { s.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public boolean checkMatchEntry(int matchId) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "SELECT * FROM APP.MATCHES WHERE MATCHID=" + String.valueOf(matchId);

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                con.close();
                return true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return false;
    }

    public int addInning(Inning i) {
        int id = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
                
        try {
            con = getConnection();
            ps = null;
            rs = null;
            if (i.getNoOfParams() == 4) {
                String sql = "insert into APP.INNINGS (param1, param2, param3, param4, noofparams) values(?,?,?,?,?)";
                String generatedColumns[] = {"ID"};
                ps = con.prepareStatement(sql, generatedColumns);
                for (int it = 1; it <= i.getNoOfParams(); it++) {
                    ps.setString(it, i.getParams().get(it - 1));
                }
                ps.setInt(5, i.getNoOfParams());
                ps.execute();

                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } else if (i.getNoOfParams() == 6) {
                String sql = "insert into APP.INNINGS (param1, param2, param3, param4, param5, param6, noofparams) values(?,?,?,?,?,?,?)";
                String generatedColumns[] = {"ID"};
                ps = con.prepareStatement(sql, generatedColumns);
                for (int it = 1; it <= i.getNoOfParams(); it++) {
                    ps.setString(it, i.getParams().get(it - 1));
                }
                ps.setInt(7, i.getNoOfParams());
                ps.execute();

                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } else if (i.getNoOfParams() == 8) {
                String sql = "insert into APP.INNINGS (param1, param2, param3, param4, param5, param6, param7, param8, noofparams) values(?,?,?,?,?,?,?,?,?)";
                String generatedColumns[] = {"ID"};
                ps = con.prepareStatement(sql, generatedColumns);
                for (int it = 1; it <= i.getNoOfParams(); it++) {
                    ps.setString(it, i.getParams().get(it - 1));
                }
                ps.setInt(9, i.getNoOfParams());
                ps.execute();

                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            //todo: show that ur unable to connect to db
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return id;
    }
// gets a Team's test match if 0 : all teams matches, 1 : team a bats first, 2 : team a bats second
    public List<testMatch> gettestMatches(String teamName, int matchType, int type){
        
        List<testMatch> testmatches = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        switch (type) {
            case 0:

                try {
                    con = getConnection();
                    String sql = "select * from APP.TESTMATCH where (hometeam = '" + teamName + "' OR awayteam = '" + teamName + "' ) order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int one1 = rs.getInt("one1id");
                        int two1 = rs.getInt("two1id");
                        int one2 = rs.getInt("one2id");
                        int two2 = rs.getInt("two2id");
                                
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                        testInning inningOne1 = gettestInning(one1);
                        testInning inningTwo1 = gettestInning(two1);
                        testInning inningOne2 = gettestInning(one2);
                        testInning inningTwo2 = gettestInning(two2);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType);
                        testmatches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
                
            case 1:

                try {
                    con = getConnection();
                    String sql = "select * from APP.TESTMATCH where battingfirst = '" + teamName + "' order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int one1 = rs.getInt("one1id");
                        int two1 = rs.getInt("two1id");
                        int one2 = rs.getInt("one2id");
                        int two2 = rs.getInt("two2id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                        testInning inningOne1 = gettestInning(one1);
                        testInning inningTwo1 = gettestInning(two1);
                        testInning inningOne2 = gettestInning(one2);
                        testInning inningTwo2 = gettestInning(two2);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType);
                        testmatches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
                
            case 2:

                try {
                    con = getConnection();
                    String sql = "select * from APP.TESTMATCH where ((hometeam = '" + teamName + "' or awayteam = '" + teamName + "') "
                            + "and matchtype = " + matchType + ") AND NOT battingfirst = '" + teamName + "' order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int one1 = rs.getInt("one1id");
                        int two1 = rs.getInt("two1id");
                        int one2 = rs.getInt("one2id");
                        int two2 = rs.getInt("two2id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                        testInning inningOne1 = gettestInning(one1);
                        testInning inningTwo1 = gettestInning(two1);
                        testInning inningOne2 = gettestInning(one2);
                        testInning inningTwo2 = gettestInning(two2);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType);
                        testmatches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
            default:
                break;
            }
            return testmatches;
            
        
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
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int inning1_id = rs.getInt("inning1_id");
                        int inning2_id = rs.getInt("inning2_id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                        Inning inningOne = getInning(inning1_id);
                        Inning inningTwo = getInning(inning2_id);

                        Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                        matches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
            case 1:

                try {
                    con = getConnection();
                    String sql = "select * from APP.MATCHES where battingfirst = '" + teamName + "' and matchtype = " + matchType + " order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int inning1_id = rs.getInt("inning1_id");
                        int inning2_id = rs.getInt("inning2_id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                        Inning inningOne = getInning(inning1_id);
                        Inning inningTwo = getInning(inning2_id);

                        Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                        matches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
            case 2:

                try {
                    con = getConnection();
                    String sql = "select * from APP.MATCHES where ((hometeam = '" + teamName + "' or awayteam = '" + teamName + "') "
                            + "and matchtype = " + matchType + ") AND NOT battingfirst = '" + teamName + "' order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int inning1_id = rs.getInt("inning1_id");
                        int inning2_id = rs.getInt("inning2_id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                        Inning inningOne = getInning(inning1_id);
                        Inning inningTwo = getInning(inning2_id);

                        Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                        matches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
            default:
                break;
            }
        return matches;
        
        
        

    }

    public Inning getInning(int id) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Inning inning = null;

        try {
            con = getConnection();

            String sql = "select * from APP.INNINGS where id = " + id + "";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int inningId = rs.getInt("id");
                List<String> params = new ArrayList<>();
                int noOfParams = rs.getInt("noofparams");

                for (int i = 1; i <= noOfParams; i++) {
                    params.add(rs.getString("param" + i));
                }

                inning = new Inning(inningId, noOfParams, params);

            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
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
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");

                String result = rs.getString("result");
//                String groundName = rs.getString("groundname");

                Inning inningOne = getInning(inning1_id);
                Inning inningTwo = getInning(inning2_id);

                Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }

        return matches;
    }
    
    public List<testMatch> gettestGroundInfo(String groundName, int matchType) {
        List<testMatch> testmatches = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "select * from APP.TESTMATCH where groundname = ? AND MATCHTYPE = ? order by MATCHDATE DESC";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, groundName);
            stmt.setInt(2, matchType);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");

                String result = rs.getString("result");
//                String groundName = rs.getString("groundname");

                testInning inningOne1 = gettestInning(one1);
                testInning inningTwo1 = gettestInning(two1);
                testInning inningOne2 = gettestInning(one2);
                testInning inningTwo2 = gettestInning(two2);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType);
                testmatches.add(m);
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
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
            if(matchType == 1){
             sql = "select distinct groundname from APP.TESTMATCH";
            }
            else{
                sql = "select distinct groundname from APP.MATCHES where MATCHTYPE = " + matchType + "";
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
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
            String sql = "select distinct HOMETEAM from APP.TESTMATCH where MATCHTYPE = " + matchType + " "
                    + "union select distinct AWAYTEAM from APP.TESTMATCH where MATCHTYPE = " + matchType + "";
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return teams;
    }
    
    
//    BaseDAO db = new BaseDAO();;

}
