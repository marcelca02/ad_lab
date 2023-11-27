/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alumne   
 */
public class ImatgeInterface {
    Connection connection = null;
    public void ImatgeInterface() {
        
    }

    public void ImatgeConnect() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void ImatgeDisconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public boolean novaImatge(String titol, String descripcio, String paraulesclau, String autor, String creador, String datafoto, String datapujada, String nomfitxer) {
        try {
            String query = "INSERT INTO PR2.IMAGE(TITLE, DESCRIPTION, KEYWORDS, AUTHOR, CREATOR, CAPTURE_DATE, STORAGE_DATE, FILENAME) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement st = connection.prepareStatement(query);
            if(titol.equals(""))            titol = " ";
            if(descripcio.equals(""))       descripcio = " ";
            if(paraulesclau.equals(""))     paraulesclau = " ";
            if(autor.equals(""))            autor = " ";
            if(creador.equals(""))          creador = " ";
            if(datafoto.equals(""))         datafoto = " ";
            if(datapujada.equals(""))       datapujada = " ";
            if(nomfitxer.equals(""))        nomfitxer = " ";
            st.setString(1, titol);
            st.setString(2, descripcio);
            st.setString(3, paraulesclau);
            st.setString(4, autor);
            st.setString(5, creador);
            st.setString(6, datafoto);
            st.setString(7, datapujada);
            st.setString(8, nomfitxer);
            st.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;  
    }
    
    public int updateImage(int id, String titol, String descripcio, String paraulesclau, String autor, String creador, String datafoto) {
        try {
            String query = "UPDATE PR2.IMAGE SET TITLE = ?, DESCRIPTION = ?, KEYWORDS = ?, AUTHOR = ?, CREATOR = ?, CAPTURE_DATE = ? WHERE ID=?";
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, titol);
            st.setString(2, descripcio);
            st.setString(3, paraulesclau);
            st.setString(4, autor);
            st.setString(5, creador);
            st.setString(6, datafoto);
            st.setInt(7, id);
            return st.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }
    
    public void esborrarImage(int id) {
        try {    
            String query = "DELETE FROM PR2.IMAGE WHERE ID=?";
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public String llistarImatges() {
        try {
            String query = "SELECT * FROM PR2.IMAGE";
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery(); 
            return crearJsonArrayString(rs);     
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
        
    public String buscarID(int id) {
        try {
            String query = "SELECT * FROM PR2.IMAGE WHERE ID=?";
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                JsonObject object = new JsonObject();
                object.addProperty("ID", rs.getString("ID"));
                object.addProperty("TITLE", rs.getString("TITLE"));
                object.addProperty("DESCRIPTION", rs.getString("DESCRIPTION"));
                object.addProperty("KEYWORDS", rs.getString("KEYWORDS"));
                object.addProperty("AUTHOR", rs.getString("AUTHOR"));
                object.addProperty("CREATOR", rs.getString("CREATOR"));
                object.addProperty("CAPTURE_DATE", rs.getString("CAPTURE_DATE"));
                object.addProperty("STORAGE_DATE", rs.getString("STORAGE_DATE"));
                object.addProperty("FILENAME", rs.getString("FILENAME"));
                return object.toString();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public String buscarTitol(String element) {
        try {
            String query = "SELECT * FROM PR2.IMAGE WHERE TITLE LIKE ?";
            PreparedStatement st = connection.prepareStatement(query);
            String consulta = "%"+element+"%";
            st.setString(1, consulta);
            ResultSet rs = st.executeQuery();
            return crearJsonArrayString(rs);    
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public String buscarCreationDate(String element) {
        try {
            String query = "SELECT * FROM PR2.IMAGE WHERE CAPTURE_DATE LIKE ?";
            PreparedStatement st = connection.prepareStatement(query);
            String consulta = "%"+element+"%";
            st.setString(1, consulta);
            ResultSet rs = st.executeQuery();
            return crearJsonArrayString(rs);     
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public String buscarAutor(String element) {
        try {
            String query = "SELECT * FROM PR2.IMAGE WHERE AUTHOR LIKE ?";
            PreparedStatement st = connection.prepareStatement(query);
            String consulta = "%"+element+"%";
            st.setString(1, consulta);
            ResultSet rs = st.executeQuery();
            return crearJsonArrayString(rs);    
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public String buscarKeywords(String element) {
        try {
            String query = "SELECT * FROM PR2.IMAGE WHERE KEYWORDS LIKE ?";
            PreparedStatement st = connection.prepareStatement(query);
            String consulta = "%"+element+"%";
            st.setString(1, consulta);
            ResultSet rs = st.executeQuery();
            return crearJsonArrayString(rs);          
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public String buscarImatge(String like) {
        try {
            String query = "SELECT * FROM PR2.IMAGE WHERE TITLE LIKE ? OR DESCRIPTION LIKE ? OR KEYWORDS LIKE ? OR AUTHOR LIKE ? OR CREATOR LIKE ?";
            PreparedStatement st = connection.prepareStatement(query);
            String consulta = "%"+like+"%";
            st.setString(1, consulta);
            st.setString(2, consulta);
            st.setString(3, consulta);
            st.setString(4, consulta);
            st.setString(5, consulta);
            ResultSet rs = st.executeQuery();
            return crearJsonArrayString(rs);    
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private String crearJsonArrayString(ResultSet rs) {
        try {
            JsonArray array = new JsonArray();
            while(rs.next()) {
                JsonObject object = new JsonObject();
                object.addProperty("ID", rs.getString("ID"));
                object.addProperty("TITLE", rs.getString("TITLE"));
                object.addProperty("DESCRIPTION", rs.getString("DESCRIPTION"));
                object.addProperty("KEYWORDS", rs.getString("KEYWORDS"));
                object.addProperty("AUTHOR", rs.getString("AUTHOR"));
                object.addProperty("CREATOR", rs.getString("CREATOR"));
                object.addProperty("CAPTURE_DATE", rs.getString("CAPTURE_DATE"));
                object.addProperty("STORAGE_DATE", rs.getString("STORAGE_DATE"));
                object.addProperty("FILENAME", rs.getString("FILENAME"));
                array.add(object);
            }   
            return array.toString();            
        }
        catch (SQLException e) {
            return null;
        }
    }    
}
