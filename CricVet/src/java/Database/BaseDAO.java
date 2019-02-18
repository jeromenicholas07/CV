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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class BaseDAO {
    
    public Connection getConnection() throws SQLException{
    
    Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/CricVetDB", "dj", "dj");
    
    System.out.println("Conn Created");
    return conn;
}
    

}
