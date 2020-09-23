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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.naming.InitialContext;
import models.Inning;
import models.testMatch;


public class CricDB extends BaseDAO {
    
    public String checkhomeoraway(String teamOne, String teamTwo,String groundName){
       Connection con = null;
        Statement s = null;
        ResultSet r = null;
        String hometeam = null;
//        try {
//            con = getConnection();
//            String sq = "select * from APP.HOMEGROUND WHERE teamname='" + teamOne + "'";
//            s = con.createStatement();
//            r = s.executeQuery(sq);
//            while (r.next()) {
//                String ground1 = r.getString("GROUND1");
//                String ground2 = r.getString("GROUND2");
//                String ground3 = r.getString("GROUND3");
//                String ground4 = r.getString("GROUND4");
//                String ground5 = r.getString("GROUND5");
//                String ground6 = r.getString("GROUND6");
//                String ground7 = r.getString("GROUND7");
//                String ground8 = r.getString("GROUND8");
//                String ground9 = r.getString("GROUND9");
//                String ground10 = r.getString("GROUND10");
//                String ground11 = r.getString("GROUND11");
//                String ground12 = r.getString("GROUND12");
//                String ground13 = r.getString("GROUND13");
//                String ground14 = r.getString("GROUND14");
//                String ground15 = r.getString("GROUND15");
//                String ground16 = r.getString("GROUND16");
//                String ground17 = r.getString("GROUND17");
//                String ground18 = r.getString("GROUND18");
//
//            if(groundName.equals(ground1)||groundName.equals(ground2)||groundName.equals(ground3)||groundName.equals(ground4)||groundName.equals(ground5)||groundName.equals(ground6)||groundName.equals(ground7)||groundName.equals(ground8)||groundName.equals(ground9)||groundName.equals(ground10)||groundName.equals(ground11)||groundName.equals(ground12)||groundName.equals(ground13)||groundName.equals(ground14)||groundName.equals(ground15)||groundName.equals(ground16)||groundName.equals(ground17)||groundName.equals(ground18)){
//                 hometeam = teamOne;
//                 return hometeam;
//
//            }
//            else{
//                hometeam = teamTwo;
//                return hometeam;
//            }
//
//            }
//            con.close();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
//        }finally {
//            try { r.close(); } catch (Exception e) { /* ignored */ }
//            try { s.close(); } catch (Exception e) { /* ignored */ }
//            try { con.close(); } catch (Exception e) { /* ignored */ }
//        }
        return teamOne;

    }

    public void addtestMatch(testMatch match) throws Exception{

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
            ps.setString(15,String.valueOf(match.getteamathome()));
            ps.setString(16,String.valueOf(match.getteamataway()));
            ps.execute();

            con.close();
        } catch (SQLException ex) {
            throw ex;
            //todo: show that ur unable to connect to db
        }finally {
            try { r.close(); } catch (Exception e) { /* ignored */ }
            try { s.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    
    public int addtestInning(Inning i) throws Exception{
        int id = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
                
        try {
            con = getConnection();
            ps = null;
            rs = null;
            if(i.getParams().size() != 6){
                throw new Exception("Inning param count doesnt match");
            }
            String sql = "insert into APP.TESTINNING (PARAM1, PARAM2, PARAM3, PARAM4, PARAM5,PARAM6) values(?,?,?,?,?,?)";
            String generatedColumns[] = {"ID"};
            ps = con.prepareStatement(sql, generatedColumns);
            for (int it = 1; it <= 6; it++) {
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }
// get team A vs team B HTH
    public List<testMatch> getTestHth(String A, String B){
        
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

                Inning inningOne1 = gettestInning(one1);
                Inning inningTwo1 = gettestInning(two1);
                Inning inningOne2 = gettestInning(one2);
                Inning inningTwo2 = gettestInning(two2);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, teamathome,teamataway);
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

            String sql = "select * from APP.Matches where matchtype= " + matchType + " and (hometeam='" + TeamName + "' OR awayteam='" + TeamName + "') order by MATCHDATE DESC";

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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }
    
    public Match getMatchfromID(int matchID) {

        Match m = null;
        Connection con = null;
        Statement stmt=null;
        ResultSet rs=null;

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
                String battingFirst = rs.getString("battingfirst");
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
                int matchType = rs.getInt("matchtype");

                Inning inningOne = getInning(inning1_id, con);
                Inning inningTwo = getInning(inning2_id, con);

                m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);

            }

            con.close();


        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return m;
    }

    public testMatch gettestMatchfromID(int matchID) {

        testMatch m = null;
        Connection con = null;
        Statement stmt=null;
        ResultSet rs=null;

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
                String teamathome = rs.getString("teamathome");
                String teamataway = rs.getString("teamataway");
                Inning inningOne1 = gettestInning(one1);
                Inning inningTwo1 = gettestInning(two1);
                Inning inningOne2 = gettestInning(one2);
                Inning inningTwo2 = gettestInning(two2);

                Inning inningOne = gettestInning(one1);
                Inning inningTwo = gettestInning(two1);
                Inning inningThree = gettestInning(one2);
                Inning inningFour = gettestInning(two2);

                m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, teamathome,teamataway);

            }

            con.close();


        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return m;
    }




    public List<testMatch> gettestDB(String TeamName) {
        List<testMatch> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt=null;
        ResultSet rs=null;

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
                Inning inningOne1 = gettestInning(one1);
                Inning inningTwo1 = gettestInning(two1);
                Inning inningOne2 = gettestInning(one2);
                Inning inningTwo2 = gettestInning(two2);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, teamathome,teamataway);
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
    
    public boolean deleteDB(){
        
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        
        try {
            String sql = "drop table \"APP\".HOMEGROUND";
            con = getConnection();
            stmt = con.createStatement();
            System.out.println("Dropping test HOMEGROUND");
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) {  }
        }
        
        
        try {
            String sql = "drop table \"APP\".TESTINNING";
            con = getConnection();
            stmt = con.createStatement();
            System.out.println("Dropping test inning");
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) {  }
        }
        try {
            String sql = "drop table \"APP\".TESTMATCH";
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Dropping test matches");
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) {  }
        }
        
        try {
            String sql = "drop table \"APP\".HEADERS";
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Dropping headers");
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
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
            System.out.println("Dropping matches");
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
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
            System.out.println("Dropping test innings");
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) {  }
        }
                
        return true;
    }
    
    public Map<String,String> getteamNames() {
        Map<String, String> names = new LinkedHashMap<>();

        Connection con = null;
        Statement stmt=null;
        ResultSet rs=null;

        try {
            con = getConnection();

            String sql = "select * from APP.TEAMNAMES";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String oldName = rs.getString("OLDNAME");
                String newName = rs.getString("NEWNAME");      
                names.put(oldName,newName);
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return names;
    }

    public void initDB() {

        System.out.println("at init");

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
//        deleteDB();
//        
//        
        try {
            String sql = "create table \"APP\".TEAMNAMES\n"
                    + "(\n"
                    + "	ID INTEGER default -1 not null primary key,\n"
                    + "	OLDNAME VARCHAR(120) not null,\n"
                    + "	NEWNAME VARCHAR(120) not null\n"
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
            String sql = "INSERT INTO APP.TEAMNAMES (ID, OLDNAME, NEWNAME) VALUES "
                    + "(1,'Chennai Super Kings','Chennai Super Kings')\n"
                    + ",(2,'Delhi Capitals','Delhi Capitals')\n"
                    + ",(3,'Kings XI Punjab','Kings XI Punjab')\n"
                    + ",(4,'Kolkata Knight Riders','Kolkata Knight Riders')\n"
                    + ",(5,'Mumbai Indians','Mumbai Indians')\n"
                    + ",(6,'Rajasthan Royals','Rajasthan Royals')\n"
                    + ",(7,'Royal Challengers Bangalore','Royal Challengers Bangalore')\n"
                    + ",(8,'Sunrisers Hyderabad','Sunrisers Hyderabad')\n"
                    + ",(9,'Adelaide Strikers','Adelaide Strikers')\n"
                    + ",(10,'Brisbane Heat','Brisbane Heat')\n"
                    + ",(11,'Hobart Hurricanes','Hobart Hurricanes')\n"
                    + ",(12,'Melbourne Renegades','Melbourne Renegades')\n"
                    + ",(13,'Melbourne Stars','Melbourne Stars')\n"
                    + ",(14,'Perth Scorchers','Perth Scorchers')\n"
                    + ",(15,'Sydney Sixers','Sydney Sixers')\n"
                    + ",(16,'Sydney Thunder','Sydney Thunder')\n"
                    + ",(17,'Barbados Tridents','Barbados Tridents')\n"
                    + ",(18,'Guyana Amazon Warriors','Guyana Amazon Warriors')\n"
                    + ",(19,'Jamaica Tallawahs','Jamaica Tallawahs')\n"
                    + ",(20,'St Kitts & Nevis Patriots','St Kitts & Nevis Patriots')\n"
                    + ",(21,'St Lucia Zouks','St Lucia Zouks')\n"
                    + ",(22,'Trinbago Knight Riders','Trinbago Knight Riders')\n"
                    + ",(23,'Antigua Hawksbills','Antigua Hawksbills')\n"
                    + ",(24,'Islamabad United','Islamabad United')\n"
                    + ",(25,'Karachi Kings','Karachi Kings')\n"
                    + ",(26,'Lahore Qalandars','Lahore Qalandars')\n"
                    + ",(27,'Multan Sultans','Multan Sultans')\n"
                    + ",(28,'Peshawar Zalmi','Peshawar Zalmi')\n"
                    + ",(29,'Quetta Gladiators','Quetta Gladiators')\n"
                    + ",(30,'Chattogram Challengers','Chattogram Challengers')\n"
                    + ",(31,'Cumilla Warriors','Cumilla Warriors')\n"
                    + ",(32,'Dhaka Platoon','Dhaka Platoon')\n"
                    + ",(33,'Khulna Tigers','Khulna Tigers')\n"
                    + ",(34,'Rajshahi Royals','Rajshahi Royals')\n"
                    + ",(35,'Rangpur Rangers','Rangpur Rangers')\n"
                    + ",(36,'Sylhet Thunder','Sylhet Thunder')\n"
                    + ",(37,'Barisal Bulls','Barisal Bulls')";


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
                    + "	PARAM8 VARCHAR(20)\n"
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
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
//            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
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
//            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
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
//            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
//        }finally {
//            try { rs.close(); } catch (Exception e) {  }
//            try { stmt.close(); } catch (Exception e) {  }
//            try { con.close(); } catch (Exception e) {  }
//        }

    }

    public Inning gettestInning(int id) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Inning inning = null;

        try {
            con = getConnection();

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

    public void addMatch(Match match) throws Exception{
        
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
            //todo: show that ur unable to connect to db
        }finally {
            try { r.close(); } catch (Exception e) { /* ignored */ }
            try { s.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void updateMatch(int matchID, Match m, Inning one, Inning two) {

        Connection con = null;
        Statement s = null;
        ResultSet rs = null;
        Statement s2 = null;
        Statement s3 = null;


        try {
            con = getConnection();
            String sq0 = "update APP.MATCHES SET HOMETEAM=?, AWAYTEAM=?, MATCHDATE=?, TOSSWINNER=?, BCW=?, HOMESCORE=?, AWAYSCORE=?, RESULT=?, GROUNDNAME=?, MATCHTYPE=? WHERE MATCHID=" + matchID;
            PreparedStatement ps0 = con.prepareStatement(sq0);
            ps0.setString(1,m.getHomeTeam());
            ps0.setString(2,m.getAwayTeam());
            ps0.setTimestamp(3,m.getMatchDate());
            ps0.setString(4,m.getTossWinner());
            ps0.setString(5,m.getBCW());
            ps0.setString(6,m.getHomeScore());
            ps0.setString(7,m.getAwayScore());
            ps0.setString(8,m.getResult());
            ps0.setString(9,m.getGroundName());
            ps0.setInt(10,m.getMatchType());

            ps0.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }


        try {
            con = getConnection();
            String sq = "select * from APP.MATCHES WHERE MATCHID=" + matchID;
            s = con.createStatement();
            rs = s.executeQuery(sq);
            if (rs.next()) {
                        int inning1_id = rs.getInt("inning1_id");
                        int inning2_id = rs.getInt("inning2_id");

//                int matchType = rs.getInt("matchtype");

                        String sq1 = "update APP.INNINGS SET PARAM1 =?,PARAM2=?,PARAM3=?,PARAM4=?,PARAM5=?,PARAM6=?,PARAM7=? WHERE ID = " + inning1_id;

                        String sq2 = "update APP.INNINGS SET PARAM1 =?,PARAM2=?,PARAM3=?,PARAM4=?,PARAM5=?,PARAM6=?,PARAM7=? WHERE ID = " + inning2_id;

                        PreparedStatement ps = con.prepareStatement(sq1);
                        for (int it = 1; it <=7; it++) {
                                 ps.setString(it, one.getParams().get(it - 1));
                            }
                        PreparedStatement ps2 = con.prepareStatement(sq2);
                        for (int it = 1; it <=7; it++) {
                                 ps2.setString(it, two.getParams().get(it - 1));
                            }
                        ps.executeUpdate();
                        ps2.executeUpdate();




            }

            con.close();
        } catch (SQLException ex) {

            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            //todo: show that ur unable to connect to db
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { s.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void updatetestMatch(int matchID,testMatch m, Inning one1, Inning two1,Inning one2,Inning two2) {

        Connection con = null;
        Statement s = null;
        ResultSet rs = null;
        Statement s2 = null;
        Statement s3 = null;

        try {
            con = getConnection();
            String sq0 = "update APP.TESTMATCH SET HOMETEAM=?, AWAYTEAM=?, MATCHDATE=?, TOSSWINNER=?, BCW=?, HOMESCORE=?, AWAYSCORE=?, RESULT=?, GROUNDNAME=?, TEAMATHOME=?,TEAMATAWAY=? WHERE MATCHID=" + matchID;
            PreparedStatement ps0 = con.prepareStatement(sq0);
            ps0.setString(1,m.getHomeTeam());
            ps0.setString(2,m.getAwayTeam());
            ps0.setDate(3,m.getMatchDate());
            ps0.setString(4,m.getTossWinner());
            ps0.setString(5,m.getBCW());
            ps0.setString(6,m.getHomeScore());
            ps0.setString(7,m.getAwayScore());
            ps0.setString(8,m.getResult());
            ps0.setString(9,m.getGroundName());
            ps0.setString(10,m.getteamathome());
            ps0.setString(11,m.getteamataway());

            ps0.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
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

//                int matchType = rs.getInt("matchtype");

                        String sq1 = "update APP.TESTINNING SET TOTALRUNS =?,SIXES=?,FOURS=?,FIRSTWICKET=?,RUNS5WICKET=? WHERE ID = " + one1id;

                        String sq2 = "update APP.TESTINNING SET TOTALRUNS =?,SIXES=?,FOURS=?,FIRSTWICKET=?,RUNS5WICKET=? WHERE ID = " + two1id;

                        String sq3 = "update APP.TESTINNING SET TOTALRUNS =?,SIXES=?,FOURS=?,FIRSTWICKET=?,RUNS5WICKET=? WHERE ID = " + one2id;

                        String sq4 = "update APP.TESTINNING SET TOTALRUNS =?,SIXES=?,FOURS=?,FIRSTWICKET=?,RUNS5WICKET=? WHERE ID = " + two2id;

                        PreparedStatement ps = con.prepareStatement(sq1);
                        for (int it = 1; it <=5; it++) {
                                 ps.setString(it, one1.getParams().get(it - 1));
                            }

                        PreparedStatement ps2 = con.prepareStatement(sq2);
                        for (int it = 1; it <=5; it++) {
                                 ps2.setString(it, two1.getParams().get(it - 1));
                            }

                        PreparedStatement ps3 = con.prepareStatement(sq3);
                        for (int it = 1; it <=5; it++) {
                                 ps3.setString(it, one2.getParams().get(it - 1));
                            }

                        PreparedStatement ps4 = con.prepareStatement(sq4);
                        for (int it = 1; it <=5; it++) {
                                 ps4.setString(it, two2.getParams().get(it - 1));
                            }

                        ps.executeUpdate();
                        ps2.executeUpdate();
                        ps3.executeUpdate();
                        ps4.executeUpdate();




            }

            con.close();
        } catch (SQLException ex) {

            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            //todo: show that ur unable to connect to db
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { s.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public int addInning(Inning i) throws Exception{
        int id = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
                
        try {
            con = getConnection();
            ps = null;
            rs = null;
            if(i.getParams().size() != 8){
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            //todo: show that ur unable to connect to db
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return id;
    }

    // case 0:any side ; 1: batting; 2: bowling
    public List<testMatch> getTestMatches(String teamName, int caseNo, TestType type){
        
        List<testMatch> testmatches = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String addCondn = "";
        if(type.equals(TestType.HOME)){
            addCondn = "AND teamathome = '" + teamName + "' ";
        }
        else if(type.equals(TestType.AWAY)){
            addCondn = "AND teamataway = '" + teamName + "' ";
        }

        switch (caseNo) {
            case 0:
                try {
                    con = getConnection();
                    String sql = "select * from APP.TESTMATCH where (hometeam = '" + teamName + "' OR awayteam = '" + teamName + "' ) "+ addCondn +"  ORDER BY MATCHDATE DESC";

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

                        Inning inningOne1 = gettestInning(one1);
                        Inning inningTwo1 = gettestInning(two1);
                        Inning inningOne2 = gettestInning(one2);
                        Inning inningTwo2 = gettestInning(two2);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, teamathome,teamataway);
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
// all home matches batting first               
            case 1:

                try {
                    con = getConnection();
                    String sql = "select * from APP.TESTMATCH where HOMETEAM = '"+ teamName +"' "+ addCondn +" ORDER BY MATCHDATE DESC";
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

                        Inning inningOne1 = gettestInning(one1);
                        Inning inningTwo1 = gettestInning(two1);
                        Inning inningOne2 = gettestInning(one2);
                        Inning inningTwo2 = gettestInning(two2);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName,teamathome,teamataway);
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
//bowling first               
            case 2:

                try {
                    con = getConnection();
                    String sql = "select * from APP.TESTMATCH where AWAYTEAM = '"+ teamName +"' "+ addCondn +" order by MATCHDATE DESC";
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

                        Inning inningOne1 = gettestInning(one1);
                        Inning inningTwo1 = gettestInning(two1);
                        Inning inningOne2 = gettestInning(one2);
                        Inning inningTwo2 = gettestInning(two2);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, teamathome, teamataway);
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
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

                Inning inningOne1 = gettestInning(one1);
                Inning inningTwo1 = gettestInning(two1);
                Inning inningOne2 = gettestInning(one2);
                Inning inningTwo2 = gettestInning(two2);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, BCW, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName,teamathome,teamataway);
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
             sql = "select distinct groundname from APP.TESTMATCH order by groundname ASC";
            }
            else{
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return teams;
    }

    public String getLastMatchDate(){
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
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        
        try {
            con = getConnection();
            String sql = "select * from APP.TESTMATCH order by MATCHDATE DESC FETCH FIRST 1 ROW ONLY";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Date matchDate = rs.getDate("matchdate");
                
                if(matchDate.after(lastDate)){
                    op = matchDate.toLocalDate().format(DateTimeFormatter.ofPattern("d MMM uuuu"));
                }
                
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("testMatch teable doesnt exist yet");
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
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
            if(matchType == 1){
             sql = "select MATCHID from APP.TESTMATCH";
            }
            else{
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
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }

    public void updateNameTable(String oldName, String newName) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;
        
        try {
            con = getConnection();
            stmt = con.createStatement();
            sql = "UPDATE APP.TEAMNAMES SET NEWNAME ='"+newName+"' WHERE OLDNAME = '" +oldName+"'";
            stmt.executeUpdate(sql);
            /*PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newName);
            ps.setString(2,oldName);
            int affectedRows =ps.executeUpdate();
            System.out.println("AFFECTED" + affectedRows);
            
*/
            con.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) {  }
        }
    }

    public void updateNameDB(String oldName, String newName) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;
        String sql2;
        String sql3;
        
        try {
            con = getConnection();
            stmt = con.createStatement();
            sql = "UPDATE APP.MATCHES SET HOMETEAM ='"+newName+"' WHERE HOMETEAM = '" +oldName+"'";
            sql2 = "UPDATE APP.MATCHES SET AWAYTEAM ='"+newName+"' WHERE AWAYTEAM = '" +oldName+"'";
            sql3 = "UPDATE APP.TEAMNAMES SET NEWNAME ='"+newName+"' WHERE OLDNAME = '" +oldName+"'";

            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);
            /*PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newName);
            ps.setString(2,oldName);
            int affectedRows =ps.executeUpdate();
            System.out.println("AFFECTED" + affectedRows);
            
*/
            con.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) {  }
        }
    }

}
