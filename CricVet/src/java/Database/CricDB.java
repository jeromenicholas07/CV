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
import Models.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class CricDB {
    
    BaseDAO db = new BaseDAO();;

   
    public void addT20Match(Match match) throws SQLException{
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        
        
        try{
         myConn = db.getConnection();  
         
         String sql = "insert into t20match (id, hometeam, awayteam, matchdate, tosswinner,"
                 + "tossresult, oneid, twoid, homescore, awayscore, winnerteam, result, groundname) "
                 + "values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
          
         myStmt=  myConn.prepareStatement(sql);
         
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
         
        }
        catch(SQLException e){
        }
        finally
        {
             myConn.close();
             myStmt.close();
        }
        
        
        
        
    }
    
    
    public void addT20Inning(Inning inning) throws SQLException{
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        try{
         myConn = db.getConnection();  
         String sql = "insert into t20inning ( id, firstover, firstfiveovers, lastfiveovers, firstwicket, fours, sixes)"
                 + "values(?, ?, ?, ?, ?, ?, ?) ";
         
         myStmt=  myConn.prepareStatement(sql);
         
         myStmt.setString(1, inning.getInningId());
         myStmt.setInt(2, inning.getFirstOver());
         myStmt.setInt(3 , inning.getFirstFiveOvers());
         myStmt.setInt(4, inning.getLastFiveOvers());
         myStmt.setInt(5, inning.getFirstWicket());
         myStmt.setInt(6, inning.getFours());
         myStmt.setInt(7, inning.getSixes());
        
           
         
         myStmt.execute();
          
            
        }
        catch(SQLException e){
        }
        finally
        {
            myConn.close();
            myStmt.close();
        }
        
        
    }
    
    public  List<Match> getMatches(String teamId) throws Exception
    {
        List<Match> matches = new ArrayList<Match>();
        
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        
        try{
            
            myConn =  db.getConnection(); 
            
            String sql = "select * from T20MATCH where hometeam = '"+ teamId+"' or awayteam = '"+ teamId +"'";
            
            myStmt = myConn.createStatement();
            
            myRs = myStmt.executeQuery(sql);
            
            while (myRs.next())
            {
                     String matchId= myRs.getString("id");
                     String homeTeamId= myRs.getString("hometeam");
                     String awayTeamId= myRs.getString("awayteam");
                     String matchDate= myRs.getString("matchdate");
                     String tossWinner= myRs.getString("tosswinner");
                     String tossResult= myRs.getString("tossresult");
                     String oneId= myRs.getString("oneid");
                     String twoId= myRs.getString("twoid");
                     String homeScore= myRs.getString("homescore");
                     String awayScore= myRs.getString("awayscore");
                     String winnerTeam= myRs.getString("winnerteam");
                     String result= myRs.getString("result");
                     String groundName= myRs.getString("groundname");
                
                     
                     Match temp =  new Match(matchId, homeTeamId, awayTeamId, matchDate, tossWinner, tossResult, oneId, twoId, homeScore, awayScore, winnerTeam, result, groundName) {
                         @Override
                         public int compareTo(Match o) {
                             throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                         }
                     };
                     
                     matches.add(temp);
                     
            }
            
           return matches;   
            
        }catch(Exception e)
        {
            
            e.printStackTrace();
        }
        finally{
             myConn.close();
            myStmt.close();
            myRs.close();
            
            
        }
        return null;
        
        
    }
    
     public  Inning getInning(String id) throws Exception
    {
        
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        Inning temp = null;
        try{
            
            myConn =  db.getConnection(); 
            
            String sql = "select * from T20INNING where id = '" + id +"'";
            
            myStmt = myConn.createStatement();
            
            myRs = myStmt.executeQuery(sql);
            if (myRs.next()){
                String inningId= myRs.getString("id");;
                int firstOver = myRs.getInt("firstover");	
                int firstFiveOvers = myRs.getInt("firstfiveovers");
                int lastFiveOvers= myRs.getInt("lastfiveovers");	
                int firstWicket= myRs.getInt("firstwicket");	
                int fours= myRs.getInt("fours");	
                int sixes= myRs.getInt("sixes");    
                     
              
                temp = new Inning(inningId, firstOver, firstFiveOvers, lastFiveOvers, firstWicket, fours, sixes);
                
            }
            
                
                
           return temp;   
            
        }catch(Exception e)
        {
            
            e.printStackTrace();
        }
        finally{
             myConn.close();
            myStmt.close();
            myRs.close();
            
            
        }
        return null;
        
        
    }
     
      public  List<Match> getGroundData(String groundName1) throws Exception
    {
        List<Match> matches = new ArrayList<Match>();
        
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        
        try{
            
            myConn =  db.getConnection(); 
            
            String sql = "select * from T20MATCH where groundname = '"+ groundName1 +"'";
            
            myStmt = myConn.createStatement();
            
            myRs = myStmt.executeQuery(sql);
            
            while (myRs.next())
            {
                     String matchId= myRs.getString("id");
                     String homeTeamId= myRs.getString("hometeam");
                     String awayTeamId= myRs.getString("awayteam");
                     String matchDate= myRs.getString("matchdate");
                     String tossWinner= myRs.getString("tosswinner");
                     String tossResult= myRs.getString("tossresult");
                     String oneId= myRs.getString("oneid");
                     String twoId= myRs.getString("twoid");
                     String homeScore= myRs.getString("homescore");
                     String awayScore= myRs.getString("awayscore");
                     String winnerTeam= myRs.getString("winnerteam");
                     String result= myRs.getString("result");
                     String groundName= myRs.getString("groundname");
                
                     
                     Match temp =  new Match(matchId, homeTeamId, awayTeamId, matchDate, tossWinner, tossResult, oneId, twoId, homeScore, awayScore, winnerTeam, result, groundName) {
                         @Override
                         public int compareTo(Match o) {
                             throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                         }
                     };
                     
                     matches.add(temp);
                     
            }
            
           return matches;   
            
        }catch(Exception e)
        {
            
            e.printStackTrace();
        }
        finally{
             myConn.close();
            myStmt.close();
            myRs.close();
            
            
        }
        return null;
        
        
    }
      
      
      public  List<String> getGroundNames() throws Exception
    {
        List<String> names = new ArrayList<String>();
        
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        
        try{
            
            myConn =  db.getConnection(); 
            
            String sql = "select distinct groundname from T20MATCH";
            
            myStmt = myConn.createStatement();
            
            myRs = myStmt.executeQuery(sql);
            
            while (myRs.next())
            {
                     
                     String groundName= myRs.getString("groundname");
                
                     
                     
                     
                     names.add(groundName);
                     
            }
            
           return names;   
            
        }catch(Exception e)
        {
            
            e.printStackTrace();
        }
        finally{
             myConn.close();
            myStmt.close();
            myRs.close();
            
            
        }
        return null;
        
        
    }

    public void addOdiInning(OdiInning one) throws SQLException {
       //To change body of generated methods, choose Tools | Templates.
    
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        try{
         myConn = db.getConnection();  
         String sql = "insert into odiinning ( inningid, firstover, firsttenovers, lasttenovers, firstwicket, fours, sixes)"
                 + "values(?, ?, ?, ?, ?, ?, ?) ";
         
         myStmt=  myConn.prepareStatement(sql);
         
         myStmt.setString(1, one.getInningId());
         myStmt.setInt(2, one.getFirstOver());
         myStmt.setInt(3 , one.getFirstTenOvers());
         myStmt.setInt(4, one.getLastTenOvers());
         myStmt.setInt(5, one.getFirstWicket());
         myStmt.setInt(6, one.getFours());
         myStmt.setInt(7, one.getSixes());
        
           
         
         myStmt.execute();
          
            
        }
        catch(SQLException e){
        }
        finally
        {
            myConn.close();
            myStmt.close();
        }
    
    
    }

    public void addOdiMatch(OdiMatch match) throws SQLException {
      Connection myConn = null;
        PreparedStatement myStmt = null;
        
        
        
        try{
         myConn = db.getConnection();  
         
         String sql = "insert into odimatch (id, hometeam, awayteam, matchdate, tosswinner,"
                 + "tossresult, oneid, twoid, homescore, awayscore, winnerteam, result, groundname) "
                 + "values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
          
         myStmt=  myConn.prepareStatement(sql);
         
         myStmt.setString(1, match.getMatchId());
         myStmt.setString(2, match.getHomeTeamId());
         myStmt.setString(3, match.getAwayTeamId());
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
         
        }
        catch(SQLException e){
        }
        finally
        {
             myConn.close();
             myStmt.close();
        }
        
    }
     
      public  List<OdiMatch> getOdiMatches(String teamId) throws Exception
    {
        List<OdiMatch> matches = new ArrayList<OdiMatch>();
        
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        
        try{
            
            myConn =  db.getConnection(); 
            
            String sql = "select * from ODIMATCH where hometeam = '"+ teamId+"' or awayteam = '"+ teamId +"'";
            
            myStmt = myConn.createStatement();
            
            myRs = myStmt.executeQuery(sql);
            
            while (myRs.next())
            {
                     String matchId= myRs.getString("id");
                     String homeTeamId= myRs.getString("hometeam");
                     String awayTeamId= myRs.getString("awayteam");
                     String matchDate= myRs.getString("matchdate");
                     String tossWinner= myRs.getString("tosswinner");
                     String tossResult= myRs.getString("tossresult");
                     String oneId= myRs.getString("oneid");
                     String twoId= myRs.getString("twoid");
                     String homeScore= myRs.getString("homescore");
                     String awayScore= myRs.getString("awayscore");
                     String winnerTeam= myRs.getString("winnerteam");
                     String result= myRs.getString("result");
                     String groundName= myRs.getString("groundname");
                
                     
                     OdiMatch temp =  new OdiMatch(matchId, homeTeamId, awayTeamId, matchDate, tossWinner, tossResult, oneId, twoId, homeScore, awayScore, winnerTeam, result, groundName) {
                         @Override
                         public int compareTo(Match o) {
                             throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                         }
                     } ;
                         
                     
                     
                     matches.add(temp);
                     
            }
            
           return matches;   
            
        }catch(Exception e)
        {
            
            e.printStackTrace();
        }
        finally{
             myConn.close();
            myStmt.close();
            myRs.close();
            
            
        }
        return null;
        
        
    }
    
     public  OdiInning getOdiInning(String id) throws Exception
    {
        
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        OdiInning temp = null;
        try{
            
            myConn =  db.getConnection(); 
            
            String sql = "select * from ODIINNING where inningid = '" + id +"'";
            
            myStmt = myConn.createStatement();
            
            myRs = myStmt.executeQuery(sql);
            if (myRs.next()){
                String inningId= myRs.getString("inningid");;
                int firstOver = myRs.getInt("firstover");	
                int firstTenOvers = myRs.getInt("firsttenovers");
                int lastTenOvers= myRs.getInt("lasttenovers");	
                int firstWicket= myRs.getInt("firstwicket");	
                int fours= myRs.getInt("fours");	
                int sixes= myRs.getInt("sixes");    
                     
              
                temp = new OdiInning(inningId, firstOver, firstTenOvers, lastTenOvers, firstWicket, fours, sixes);
                
            }
            
                
                
           return temp;   
            
        }catch(Exception e)
        {
            
            e.printStackTrace();
        }
        finally{
             myConn.close();
            myStmt.close();
            myRs.close();
            
            
        }
        return null;
        
        
    }

    public List<OdiMatch> getOdiGroundData(String groundName1) throws SQLException {
       List<OdiMatch> matches = new ArrayList<OdiMatch>();
        
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        
        try{
            
            myConn =  db.getConnection(); 
            
            String sql = "select * from ODIMATCH where groundname = '"+ groundName1 +"'";
            
            myStmt = myConn.createStatement();
            
            myRs = myStmt.executeQuery(sql);
            
            while (myRs.next())
            {
                     String matchId= myRs.getString("id");
                     String homeTeamId= myRs.getString("hometeam");
                     String awayTeamId= myRs.getString("awayteam");
                     String matchDate= myRs.getString("matchdate");
                     String tossWinner= myRs.getString("tosswinner");
                     String tossResult= myRs.getString("tossresult");
                     String oneId= myRs.getString("oneid");
                     String twoId= myRs.getString("twoid");
                     String homeScore= myRs.getString("homescore");
                     String awayScore= myRs.getString("awayscore");
                     String winnerTeam= myRs.getString("winnerteam");
                     String result= myRs.getString("result");
                     String groundName= myRs.getString("groundname");
                
                     
                     OdiMatch temp =  new OdiMatch(matchId, homeTeamId, awayTeamId, matchDate, tossWinner, tossResult, oneId, twoId, homeScore, awayScore, winnerTeam, result, groundName) {
                         @Override
                         public int compareTo(Match o) {
                             throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                         }
                     };
                     
                     matches.add(temp);
                     
            }
            
           return matches;   
            
        }catch(Exception e)
        {
            
            e.printStackTrace();
        }
        finally{
             myConn.close();
            myStmt.close();
            myRs.close();
            
            
        }
        return null;
        
    }
    
}

