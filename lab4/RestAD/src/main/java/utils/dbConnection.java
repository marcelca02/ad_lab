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
import java.util.ArrayList;
import java.util.List;


public class dbConnection {
    
    private Connection c;
    
    final private String dbStringConnection = "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2";
    final private String dbStringDriver = "org.apache.derby.jdbc.ClientDriver";
    
    public dbConnection() throws ClassNotFoundException, SQLException {
        Class.forName(this.dbStringDriver);
        // Create db connection
        c = (Connection) DriverManager.getConnection(this.dbStringConnection);
    }
    
    public void closeDb() throws SQLException {
        c.close();
    }
    
    public String getFilename(int id) throws SQLException {
	String query = "SELECT * FROM IMAGE WHERE ID = ?";
	PreparedStatement statement = c.prepareStatement(query);
        
	statement.setString(1,Integer.toString(id));

	ResultSet res = statement.executeQuery();
	if (res.next()) return res.getString("FILENAME");
        else return null;
    }
    
    public void modifyImage(int id, String title, String description, String keywords, String author, String dateCapture, String filename) throws ClassNotFoundException, SQLException {       

        // Create query and statement
        String query = "UPDATE IMAGE SET TITLE = ?, DESCRIPTION = ?, KEYWORDS = ?, AUTHOR = ?, CAPTURE_DATE = ?, FILENAME = ? WHERE ID = ?";
        PreparedStatement statement = c.prepareStatement(query);

        statement.setString(1, title);
        statement.setString(2, description);
        statement.setString(3, keywords);
        statement.setString(4, author);
        statement.setString(5, dateCapture);
        statement.setString(6,filename);
        statement.setString(7, Integer.toString(id));

        statement.executeUpdate();
    }
    
    public boolean isOwner(int id, String creator) throws ClassNotFoundException, SQLException {

        String query = "SELECT * FROM IMAGE WHERE ID = ? AND CREATOR = ?";
        PreparedStatement statement = c.prepareStatement(query);
        
        statement.setString(1,Integer.toString(id));
        statement.setString(2,creator);
        
        ResultSet res = statement.executeQuery();
        return res.next();
    }
    
    public String isLoginCorrect(String username, String password) throws SQLException, ClassNotFoundException {
        
        // Create query and statement
        String query = "SELECT * FROM USUARIOS WHERE ID_USUARIO = ? AND PASSWORD = ?";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet res = statement.executeQuery();

        if (res.next()) return username;
        else return null;
    }
    
    public int getIdFromFilename(String filename) throws SQLException {
        
        String query = "SELECT * FROM IMAGE WHERE FILENAME = ?";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, filename);

        ResultSet res = statement.executeQuery();

        if (res.next()) return res.getInt("ID");
        else return -1;
    }
    
    public void deleteImage(int id) throws SQLException {
        
        String query = "DELETE FROM IMAGE WHERE ID = ?";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, Integer.toString(id));
        
        statement.executeUpdate();
    } 
    
    public List<Image> listImages() throws SQLException {
        
        String query = "select * from image";
        PreparedStatement statement = c.prepareStatement(query);
        ResultSet rs = statement.executeQuery();         

        List<Image> images = new ArrayList<>();

        while (rs.next()) {
            Image image = new Image();
            image.setId(rs.getInt("id"));
            image.setTitle(rs.getString("title"));
            image.setDescription(rs.getString("description"));

            String keywordsString = rs.getString("keywords"); // Obtener la cadena desde la base de datos
            String[] keywordsArray = keywordsString.split(","); // Convertir a array de strings
            image.setKeywords(keywordsArray); 

            image.setAuthor(rs.getString("author"));
            image.setCreator(rs.getString("creator"));
            image.setCaptureDate(rs.getString("capture_date"));
            image.setStorageDate(rs.getString("storage_date"));
            image.setFilename(rs.getString("filename"));
            images.add(image);
        }
        
        return images;
    } 
    
    public List<Image> recentImage() throws SQLException {
        
        String query = "SELECT * FROM IMAGE ORDER BY STORAGE_DATE DESC FETCH FIRST 5 ROWS ONLY";
        PreparedStatement statement = c.prepareStatement(query);
        ResultSet rs = statement.executeQuery();            

        List<Image> images = new ArrayList<>();

        while (rs.next()) {
            Image image = new Image();
            image.setId(rs.getInt("id"));
            image.setTitle(rs.getString("title"));
            image.setDescription(rs.getString("description"));

            String keywordsString = rs.getString("keywords"); // Obtener la cadena desde la base de datos
            String[] keywordsArray = keywordsString.split(","); // Convertir a array de strings
            image.setKeywords(keywordsArray); 

            image.setAuthor(rs.getString("author"));
            image.setCreator(rs.getString("creator"));
            image.setCaptureDate(rs.getString("capture_date"));
            image.setStorageDate(rs.getString("storage_date"));
            image.setFilename(rs.getString("filename"));
            images.add(image);
        }
        return images;
    }
    
    public void registerImage(String title, String description, String keywords, String author,
            String creator, String capture_date, String storage_date, String imageFileName) throws SQLException {
        
        String query = "INSERT INTO IMAGE (TITLE, DESCRIPTION, KEYWORDS, AUTHOR, CREATOR, CAPTURE_DATE, STORAGE_DATE, FILENAME) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = c.prepareStatement(query);           
        statement.setString(1, title);
        statement.setString(2, description);
        statement.setString(3, keywords);
        statement.setString(4, author);
        statement.setString(5, creator); // LLave foranea de Usuario
        statement.setString(6, capture_date);
        statement.setString(7, storage_date);
        statement.setString(8, imageFileName);   
        //out.println("Cargado registros");
        statement.executeUpdate(); 
    }
    
    public List<Image> searchImageByTitle(String title) throws SQLException {
        
        String query = "SELECT *\n"
                    + "FROM IMAGE\n"
                    + "WHERE TITLE = ?";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, title);
        ResultSet rs = statement.executeQuery();            

        List<Image> images = new ArrayList<>();

        while (rs.next()) {
            Image image = new Image();
            image.setId(rs.getInt("id"));
            image.setTitle(rs.getString("title"));
            image.setDescription(rs.getString("description"));

            String keywordsString = rs.getString("keywords"); // Obtener la cadena desde la base de datos
            String[] keywordsArray = keywordsString.split(","); // Convertir a array de strings
            image.setKeywords(keywordsArray); 

            image.setAuthor(rs.getString("author"));
            image.setCreator(rs.getString("creator"));
            image.setCaptureDate(rs.getString("capture_date"));
            image.setStorageDate(rs.getString("storage_date"));
            image.setFilename(rs.getString("filename"));
            images.add(image);
        }
        return images;
    }
    
    public List<Image> searchImageByAuthor(String author) throws SQLException {
        
        String query = "SELECT *\n"
                    + "FROM IMAGE\n"
                    + "WHERE AUTHOR = ?";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, author);
        ResultSet rs = statement.executeQuery();            

        List<Image> images = new ArrayList<>();

        while (rs.next()) {
            Image image = new Image();
            image.setId(rs.getInt("id"));
            image.setTitle(rs.getString("title"));
            image.setDescription(rs.getString("description"));

            String keywordsString = rs.getString("keywords"); // Obtener la cadena desde la base de datos
            String[] keywordsArray = keywordsString.split(","); // Convertir a array de strings
            image.setKeywords(keywordsArray); 

            image.setAuthor(rs.getString("author"));
            image.setCreator(rs.getString("creator"));
            image.setCaptureDate(rs.getString("capture_date"));
            image.setStorageDate(rs.getString("storage_date"));
            image.setFilename(rs.getString("filename"));
            images.add(image);
        }
        return images;
    }
    
    public List<Image> searchImageById(int id) throws SQLException {
        
        String query = "SELECT *\n"
                    + "FROM IMAGE\n"
                    + "WHERE ID = ?";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, Integer.toString(id));
        ResultSet rs = statement.executeQuery();            

        List<Image> images = new ArrayList<>();

        while (rs.next()) {
            Image image = new Image();
            image.setId(rs.getInt("id"));
            image.setTitle(rs.getString("title"));
            image.setDescription(rs.getString("description"));

            String keywordsString = rs.getString("keywords"); // Obtener la cadena desde la base de datos
            String[] keywordsArray = keywordsString.split(","); // Convertir a array de strings
            image.setKeywords(keywordsArray); 

            image.setAuthor(rs.getString("author"));
            image.setCreator(rs.getString("creator"));
            image.setCaptureDate(rs.getString("capture_date"));
            image.setStorageDate(rs.getString("storage_date"));
            image.setFilename(rs.getString("filename"));
            images.add(image);
        }
        return images;
    }
    
    public List<Image> searchImageByKeywords(String keywords) throws SQLException {
        
        String query = "SELECT *\n"
                    + "FROM IMAGE\n"
                    + "WHERE KEYWORDS = ?";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, keywords);
        ResultSet rs = statement.executeQuery();            

        List<Image> images = new ArrayList<>();

        while (rs.next()) {
            Image image = new Image();
            image.setId(rs.getInt("id"));
            image.setTitle(rs.getString("title"));
            image.setDescription(rs.getString("description"));

            String keywordsString = rs.getString("keywords"); // Obtener la cadena desde la base de datos
            String[] keywordsArray = keywordsString.split(","); // Convertir a array de strings
            image.setKeywords(keywordsArray); 

            image.setAuthor(rs.getString("author"));
            image.setCreator(rs.getString("creator"));
            image.setCaptureDate(rs.getString("capture_date"));
            image.setStorageDate(rs.getString("storage_date"));
            image.setFilename(rs.getString("filename"));
            images.add(image);
        }
        return images;
    }
    
    
    
    public List<Image> searchImageDate(String date_start, String date_end) throws SQLException {
        
        String query = "SELECT *\n"
                    + "FROM IMAGE\n"
                    + "WHERE STORAGE_DATE BETWEEN ? AND ?";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, date_start);
        statement.setString(2, date_end);
        ResultSet rs = statement.executeQuery();            

        List<Image> images = new ArrayList<>();

        while (rs.next()) {
            Image image = new Image();
            image.setId(rs.getInt("id"));
            image.setTitle(rs.getString("title"));
            image.setDescription(rs.getString("description"));

            String keywordsString = rs.getString("keywords"); // Obtener la cadena desde la base de datos
            String[] keywordsArray = keywordsString.split(","); // Convertir a array de strings
            image.setKeywords(keywordsArray); 

            image.setAuthor(rs.getString("author"));
            image.setCreator(rs.getString("creator"));
            image.setCaptureDate(rs.getString("capture_date"));
            image.setStorageDate(rs.getString("storage_date"));
            image.setFilename(rs.getString("filename"));
            images.add(image);
        }
        return images;
    }
    
    // MÉTODOS DE FILTRADO CONJUNTOS QUE SE USABAN EN LA PRÁCTICA 2
    
    public List<Image> searchImageDateAuthor(String date_start, String date_end, String author) throws SQLException {
        
        String query = "SELECT *\n"
                    + "FROM IMAGE\n"
                    + "WHERE STORAGE_DATE BETWEEN ? AND ? "
                    + "AND AUTHOR = ?";
        
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, date_start);
        statement.setString(2, date_end);
        statement.setString(3, author);
        ResultSet rs = statement.executeQuery();            

        List<Image> images = new ArrayList<>();

        while (rs.next()) {
            Image image = new Image();
            image.setId(rs.getInt("id"));
            image.setTitle(rs.getString("title"));
            image.setDescription(rs.getString("description"));

            String keywordsString = rs.getString("keywords"); // Obtener la cadena desde la base de datos
            String[] keywordsArray = keywordsString.split(","); // Convertir a array de strings
            image.setKeywords(keywordsArray); 

            image.setAuthor(rs.getString("author"));
            image.setCreator(rs.getString("creator"));
            image.setCaptureDate(rs.getString("capture_date"));
            image.setStorageDate(rs.getString("storage_date"));
            image.setFilename(rs.getString("filename"));
            images.add(image);
        }
        return images;
    }
    
    public List<Image> searchImageDateAuthorKeywords(String date_start, String date_end, String author, String keywords) throws SQLException {
        
        String query = "SELECT *\n"
                    + "FROM IMAGE\n"
                    + "WHERE STORAGE_DATE BETWEEN ? AND ? \n"
                    + "AND AUTHOR = ?\n"
                    + "AND KEYWORDS LIKE ? ";
        
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, date_start);
        statement.setString(2, date_end);
        statement.setString(3, author);
        statement.setString(4, "%" + keywords + "%");
        ResultSet rs = statement.executeQuery();            

        List<Image> images = new ArrayList<>();

        while (rs.next()) {
            Image image = new Image();
            image.setId(rs.getInt("id"));
            image.setTitle(rs.getString("title"));
            image.setDescription(rs.getString("description"));

            String keywordsString = rs.getString("keywords"); // Obtener la cadena desde la base de datos
            String[] keywordsArray = keywordsString.split(","); // Convertir a array de strings
            image.setKeywords(keywordsArray); 

            image.setAuthor(rs.getString("author"));
            image.setCreator(rs.getString("creator"));
            image.setCaptureDate(rs.getString("capture_date"));
            image.setStorageDate(rs.getString("storage_date"));
            image.setFilename(rs.getString("filename"));
            images.add(image);
        }
        return images;
    }
    
    public List<Image> searchImageDateKeywords(String date_start, String date_end, String keywords) throws SQLException {
        
        String query = "SELECT *\n"
                    + "FROM IMAGE\n"
                    + "WHERE STORAGE_DATE BETWEEN ? AND ? \n"
                    + "AND KEYWORDS LIKE ? ";
        
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, date_start);
        statement.setString(2, date_end);
        statement.setString(3, "%" + keywords + "%");
        ResultSet rs = statement.executeQuery();            

        List<Image> images = new ArrayList<>();

        while (rs.next()) {
            Image image = new Image();
            image.setId(rs.getInt("id"));
            image.setTitle(rs.getString("title"));
            image.setDescription(rs.getString("description"));

            String keywordsString = rs.getString("keywords"); // Obtener la cadena desde la base de datos
            String[] keywordsArray = keywordsString.split(","); // Convertir a array de strings
            image.setKeywords(keywordsArray); 

            image.setAuthor(rs.getString("author"));
            image.setCreator(rs.getString("creator"));
            image.setCaptureDate(rs.getString("capture_date"));
            image.setStorageDate(rs.getString("storage_date"));
            image.setFilename(rs.getString("filename"));
            images.add(image);
        }
        return images;
    }
    
    
    
    
    
}
