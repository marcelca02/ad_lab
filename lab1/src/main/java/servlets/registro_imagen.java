/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import jakarta.servlet.RequestDispatcher;
import java.sql.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Max Pasten
 */
@MultipartConfig
@WebServlet(name = "registro_imagen", urlPatterns = {"/registro_imagen"})
public class registro_imagen extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private static final long serialVersionUID = 1L;
       
    public registro_imagen() {
        super();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        
        //**********************
        
        
        // Get registro_imagen form parameters
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String keywords = request.getParameter("keywords");
        String author = request.getParameter("author");
        String creator = request.getParameter("creator");
        String capture_date = request.getParameter("capture_date");
        String storage_date = request.getParameter("storage_date");
        //String filename = request.getParameter("filename");
        
        Date todayDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaActual = sdf.format(todayDate);
        

        Connection connection = null;
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("In do post method of Add Image servlet.");
            Part file=request.getPart("filename");
            
            // Nombre de Archivo
            String imageFileName=file.getSubmittedFileName();  // get selected image file name
            out.println("Selected Image File Name : "+imageFileName);
            
            String uploadPath="C:/Users/Max Pasten/Downloads/"+imageFileName;  // upload path where we have to upload our actual image
            out.println("Upload Path : "+uploadPath);

            // Uploading our selected image into the images folder

            try{

                FileOutputStream fos=new FileOutputStream(uploadPath);
                InputStream is=file.getInputStream();

                byte[] data=new byte[is.available()];
                is.read(data);
                fos.write(data);
                fos.close();

            }

            catch(Exception e){
                e.printStackTrace();
            }
            
            
            out.println(fechaActual);
            
            out.println("<br>title: " + title);
            out.println("<br>description: " + description);
            out.println("<br>Keywords: " + keywords);
            out.println("<br>Autor: " + author);
            out.println("<br>Creator: " + creator);
            out.println("<br>Fecha C: " + capture_date);
            out.println("<br>Fecha S: " + storage_date);
            out.println("<br>Imagen: " + imageFileName);
            
            
            String query;
            PreparedStatement statement;
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
            // Create db connection
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            
            out.println("Conexion exitosa");
            
            // Create query and statement
            // With preparedStatement, SQL Injection and other problems when inserting values in the database can be avoided
            query = "INSERT INTO IMAGE (TITLE, DESCRIPTION, KEYWORDS, AUTHOR, CREATOR, CAPTURE_DATE, STORAGE_DATE, FILENAME) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(query);           
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
            //out.println("<br>Id usuario = " + rs.getString("title"));
            out.println("<br>Insertado correctamente");
            
            //out.println("Registrado");
          
            
            /* TODO output your page here. You may use following sample code. */
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(registro_imagen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(registro_imagen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
