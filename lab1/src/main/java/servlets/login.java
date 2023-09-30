/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.sql.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcel
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {
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
       // Get login form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Connection c = null;
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
          
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
            // Create db connection
            c = (Connection) DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");

            
            // Create query and statement
            String query = "SELECT * FROM USUARIOS WHERE ID_USUARIO = ? AND PASSWORD = ?";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            
            ResultSet res = statement.executeQuery();
            
            
            if (res.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("username",username);
                c.close();
                Cookie uNameCookie = new Cookie("username",username);
                response.addCookie(uNameCookie);
                response.sendRedirect("/lab1/index.html");
            }
            else {
                response.sendRedirect("/lab1/error.jsp");
            }

            
            
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
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
