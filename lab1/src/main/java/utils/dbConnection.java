/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author marcel
 */
public class dbConnection {
    
    final private String dbStringConnection = "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2";
    
    public dbConnection() {}
    
    private void connectDb(Connection c) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        // Create db connection
        c = (Connection) DriverManager.getConnection(this.dbStringConnection);
    }
    
    public void modifyImage(int id, String title, String description, String keywords, String filename) throws ClassNotFoundException, SQLException {
        
        Connection c = null;
        connectDb(c);
        
        // Create query and statement
        String query = "UPDATE IMAGE SET TITLE = ?, DESCRIPTION = ?, KEYWORDS = ?, FILENAME = ? WHERE ID = ?";
        PreparedStatement statement = c.prepareStatement(query);

        statement.setString(1, title);
        statement.setString(2, description);
        statement.setString(3, keywords);
        statement.setString(4,filename);
        statement.setString(5, Integer.toString(id));

        statement.executeUpdate();
    }
    
    public boolean isOwner(int id, String creator) throws ClassNotFoundException, SQLException {
        Connection c = null;
        connectDb(c);
        
        String query = "SELECT * FROM USUARIOS WHERE ID = ? AND CREATOR = ?";
        PreparedStatement statement = c.prepareStatement(query);
        
        statement.setString(1,Integer.toString(id));
        statement.setString(2,creator);
        
        ResultSet res = statement.executeQuery();
        return res.next();
    }
    
}
