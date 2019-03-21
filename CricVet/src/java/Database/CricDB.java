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

/**
 *
 * @author DELL
 */
public class CricDB extends BaseDAO {

    public List<Match> getDB(int matchType, String TeamName) {
        List<Match> matches = new ArrayList<>();

        Connection con;
        Statement stmt;
        ResultSet rs;

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
        }
        return matches;
    }

    public void initDB() {

        System.out.println("at init");

        Connection con;
        Statement stmt;
        ResultSet rs;

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
        }

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
                //test
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return headers;
    }

    public void addMatch(Match match) {
        try {
            Connection con = getConnection();
            String sq = "select * from APP.MATCHES WHERE MATCHID=" + String.valueOf(match.getMatchId());
            Statement s = con.createStatement();
            ResultSet r = s.executeQuery(sq);
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
        }
        return false;
    }

    public int addInning(Inning i) {
        int id = -1;
        try {
            Connection con = getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
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
        }
        return id;
    }

    public List<Match> getMatches(String teamName, int matchType, int type) {

        List<Match> matches = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        switch (type) {
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
        }
        return inning;
    }

    public List<Match> getGroundInfo(String groundName, int matchType) {
        List<Match> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "select * from APP.MATCHES where groundname = '" + groundName + "' AND MATCHTYPE = " + matchType + " ";
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
//                String groundName = rs.getString("groundname");

                Inning inningOne = getInning(inning1_id);
                Inning inningTwo = getInning(inning2_id);

                Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return matches;
    }

    public List<String> getGroundList(int matchType) {
        List<String> grounds = new ArrayList<String>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "select distinct groundname from APP.MATCHES where MATCHTYPE = " + matchType + "";
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
        }
        return teams;
    }
//    BaseDAO db = new BaseDAO();;

}
