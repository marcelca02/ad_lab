/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author marcel
 */
@WebServlet(name = "modificarImagen", urlPatterns = {"/modificarImagen"})
public class modificarImagen extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id;
        
        String name;
        
        Connection c = null;
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
          
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
            // Create db connection
            c = (Connection) DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            
            // Create query and statement
            String query = "SELECT * FROM IMAGES WHERE ID = ?";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, id);
            
            ResultSet res = statement.executeQuery();
            
            if (res.next()) {
                
            }
            else {
                response.sendRedirect("/lab1/error.jsp");
            }

        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSesion session = request.getSession();
        
        
        String title = request.getParameter("title");
        String keywords = request.getParameter("key");
        String description = request.getParameter("description");
        String filename = request.getParameter("filename");
        
        Connection c = null;
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {           
          
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
            // Create db connection
            c = (Connection) DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            
            // Create query and statement
            String query = "UPDATE IMAGES SET TITLE = ?, DESCRIPTION = ?, KEYWORDS = ?, FILENAME = ? WHERE ID = ?";
            PreparedStatement statement = c.prepareStatement(query);
            
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, keywords);
            statement.setString(4,filename);
            statement.setString(5, id);
            
            statement.executeUpdate();
            
            
            

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
