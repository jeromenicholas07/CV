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

/**
 *
 * @author DELL
 */
public class CricDB extends BaseDAO {

    public List<String> getHeaders(int matchType) {
        List<String> headers = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "SELECT * FROM DJ.HEADERS WHERE MATCHTYPE=" + matchType;

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next() && matchType!=1) {
                for (int i = 1; i <= 6; i++) {
                    headers.add(rs.getString("param"+i));
                }
            }
            else if(matchType==1){
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
            String sq = "select * from DJ.MATCHES WHERE MATCHID=" + String.valueOf(match.getMatchId());
            Statement s = con.createStatement();
            ResultSet r = s.executeQuery(sq);
            if (r.next()) {
                System.out.println("Match " + String.valueOf(match.getMatchId()) + " already exists in DB");
                return;
            }

            int inn1 = addInning(match.getInningOne());

            int inn2 = addInning(match.getInningTwo());

            String sql = "insert into DJ.MATCHES (MATCHID, HOMETEAM, AWAYTEAM, MATCHDATE, TOSSWINNER, BATTINGFIRST, INNING1_ID, INNING2_ID, HOMESCORE, AWAYSCORE, RESULT, GROUNDNAME, MATCHTYPE) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            String sql = "SELECT * FROM DJ.MATCHES WHERE MATCHID=" + String.valueOf(matchId);

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
                String sql = "insert into DJ.INNINGS (param1, param2, param3, param4, noofparams) values(?,?,?,?,?)";
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
                String sql = "insert into DJ.INNINGS (param1, param2, param3, param4, param5, param6, noofparams) values(?,?,?,?,?,?,?)";
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
                String sql = "insert into DJ.INNINGS (param1, param2, param3, param4, param5, param6, param7, param8, noofparams) values(?,?,?,?,?,?,?,?,?)";
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
        Statement stmt = null;
        ResultSet rs = null;

        switch (type) {
            case 1:

                try {
                    con = getConnection();
                    String sql = "select * from DJ.MATCHES where battingfirst = '" + teamName + "' and matchtype = " + matchType + " order by MATCHDATE DESC";

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
                break;
            case 2:

                try {
                    con = getConnection();
                    String sql = "select * from DJ.MATCHES where ((hometeam = '" + teamName + "' or awayteam = '" + teamName + "') "
                            + "and matchtype = " + matchType + ") AND NOT battingfirst = '" + teamName + "' order by MATCHDATE DESC";

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

            String sql = "select * from DJ.INNINGS where id = " + id + "";
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
            String sql = "select * from DJ.MATCHES where groundname = '" + groundName + "' AND MATCHTYPE = '" + matchType + "' ";
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
            String sql = "select distinct groundname from DJ.MATCHES where MATCHTYPE = " + matchType + "";
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
            String sql = "select distinct HOMETEAM from DJ.MATCHES where MATCHTYPE = " + matchType + " "
                    + "union select distinct AWAYTEAM from DJ.MATCHES where MATCHTYPE = " + matchType + "";
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

    public void addT20Match(Match1 match) {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "insert into t20match (id, hometeam, awayteam, matchdate, tosswinner,"
                    + "tossresult, oneid, twoid, homescore, awayscore, winnerteam, result, groundname) "
                    + "values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, match.getMatchId());
            myStmt.setString(2, match.getHomeTeam());
            myStmt.setString(3, match.getAwayTeam());
            myStmt.setString(4, match.getMatchDate());
            myStmt.setString(5, match.getTossWinner());
            myStmt.setString(6, match.getTossResult());
            myStmt.setString(7, match.getOneId());
            myStmt.setString(8, match.getTwoId());
            myStmt.setString(9, match.getHomeScore());
            myStmt.setString(10, match.getAwayScore());
            myStmt.setString(11, match.getWinnerTeam());
            myStmt.setString(12, match.getResult());
            myStmt.setString(13, match.getGroundName());
            myStmt.execute();

            myConn.close();
            myStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addT20Inning(Inning1 inning) throws SQLException {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();
            String sql = "insert into t20inning ( id, firstover, firstfiveovers, lastfiveovers, firstwicket, fours, sixes)"
                    + "values(?, ?, ?, ?, ?, ?, ?) ";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, inning.getInningId());
            myStmt.setInt(2, inning.getFirstOver());
            myStmt.setInt(3, inning.getFirstFiveOvers());
            myStmt.setInt(4, inning.getLastFiveOvers());
            myStmt.setInt(5, inning.getFirstWicket());
            myStmt.setInt(6, inning.getFours());
            myStmt.setInt(7, inning.getSixes());

            myStmt.execute();

        } catch (SQLException e) {
        } finally {
            myConn.close();
            myStmt.close();
        }

    }

    public List<Match1> getMatches1(String teamId) throws Exception {
        List<Match1> matches = new ArrayList<Match1>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {

            myConn = getConnection();

            String sql = "select * from T20MATCH where hometeam = '" + teamId + "' or awayteam = '" + teamId + "' order by matchdate ";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {
                String matchId = myRs.getString("id");
                String homeTeamId = myRs.getString("hometeam");
                String awayTeamId = myRs.getString("awayteam");
                String matchDate = myRs.getString("matchdate");
                String tossWinner = myRs.getString("tosswinner");
                String tossResult = myRs.getString("tossresult");
                String oneId = myRs.getString("oneid");
                String twoId = myRs.getString("twoid");
                String homeScore = myRs.getString("homescore");
                String awayScore = myRs.getString("awayscore");
                String winnerTeam = myRs.getString("winnerteam");
                String result = myRs.getString("result");
                String groundName = myRs.getString("groundname");

                Match1 temp = new Match1(matchId, homeTeamId, awayTeamId, matchDate, tossWinner, tossResult, oneId, twoId, homeScore, awayScore, winnerTeam, result, groundName) {
                    @Override
                    public int compareTo(Match1 o) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                };

                matches.add(temp);

            }

            return matches;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            myConn.close();
            myStmt.close();
            myRs.close();

        }
        return null;

    }

    public Inning1 getInning1(String id) throws Exception {

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        Inning1 temp = null;
        try {

            myConn = getConnection();

            String sql = "select * from T20INNING where id = '" + id + "'";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);
            if (myRs.next()) {
                String inningId = myRs.getString("id");;
                int firstOver = myRs.getInt("firstover");
                int firstFiveOvers = myRs.getInt("firstfiveovers");
                int lastFiveOvers = myRs.getInt("lastfiveovers");
                int firstWicket = myRs.getInt("firstwicket");
                int fours = myRs.getInt("fours");
                int sixes = myRs.getInt("sixes");

                temp = new Inning1(inningId, firstOver, firstFiveOvers, lastFiveOvers, firstWicket, fours, sixes);

            }

            return temp;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            myConn.close();
            myStmt.close();
            myRs.close();

        }
        return null;

    }

    public List<Match1> getGroundData(String groundName1) throws Exception {
        List<Match1> matches = new ArrayList<Match1>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {

            myConn = getConnection();

            String sql = "select * from T20MATCH where groundname = '" + groundName1 + "'";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {
                String matchId = myRs.getString("id");
                String homeTeamId = myRs.getString("hometeam");
                String awayTeamId = myRs.getString("awayteam");
                String matchDate = myRs.getString("matchdate");
                String tossWinner = myRs.getString("tosswinner");
                String tossResult = myRs.getString("tossresult");
                String oneId = myRs.getString("oneid");
                String twoId = myRs.getString("twoid");
                String homeScore = myRs.getString("homescore");
                String awayScore = myRs.getString("awayscore");
                String winnerTeam = myRs.getString("winnerteam");
                String result = myRs.getString("result");
                String groundName = myRs.getString("groundname");

                Match1 temp = new Match1(matchId, homeTeamId, awayTeamId, matchDate, tossWinner, tossResult, oneId, twoId, homeScore, awayScore, winnerTeam, result, groundName) {
                    @Override
                    public int compareTo(Match1 o) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                };

                matches.add(temp);

            }

            return matches;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            myConn.close();
            myStmt.close();
            myRs.close();

        }
        return null;

    }

    public List<String> getGroundNames() throws Exception {
        List<String> names = new ArrayList<String>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {

            myConn = getConnection();

            String sql = "select distinct groundname from T20MATCH";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {

                String groundName = myRs.getString("groundname");

                names.add(groundName);

            }

            return names;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            myConn.close();
            myStmt.close();
            myRs.close();

        }
        return null;

    }

}
