/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alumne
 */
public class UsuarisInterface {
    Connection connection = null;
    public void UsuarisInterface() {
        
    }

    public void UsuarisConnect() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void UsuarisDisconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public boolean existeixUsuari(String nom, String contrasenya) {
        boolean b = false;
        try {
            String query = "SELECT * FROM PR2.USUARIOS WHERE ID_USUARIO=? and password=?";
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, nom);
            st.setString(2, contrasenya);
            ResultSet resultat = st.executeQuery();
            b = resultat.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return b;
    }
    
    public boolean nouUsuari(String nom, String contrasenya) {
        try {
            String query = "insert into PR2.USUARIOS values(?,?)";
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, nom);
            st.setString(2, contrasenya);
            st.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;           
    }    
}
