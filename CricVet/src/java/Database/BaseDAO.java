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
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.naming.*;

/**
 *
 * @author DELL
 */
public class BaseDAO {

    public Connection getConnection() throws SQLException {

            Connection conn=null;
        try {
            System.out.println("trying conn");
//            InitialContext ctx;
//            DataSource ds;
//            Statement stmt;
//            ResultSet rs;
//            
//            ctx = new InitialContext();
//
//            ds = (DataSource) ctx.lookup("jdbc/jres");
//            conn = ds.getConnection();

           conn = DriverManager.getConnection("jdbc:derby://localhost:1527/CricVetDB", "dj", "dj");
            
            System.out.println("Conn Created");
        } catch (Exception ex) {
            Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            return conn;
    }

}
