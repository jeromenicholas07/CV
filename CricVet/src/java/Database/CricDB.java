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
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class CricDB {
    
    BaseDAO db = new BaseDAO();;

   
    public void addT20Match(T20Match match) throws SQLException{
        
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
    
    
    public void addT20Inning(T20Inning inning) throws SQLException{
        
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
    
    
    
}

