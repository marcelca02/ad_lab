/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import utils.dbConnection;

/**
 *
 * @author marcel
 */
@WebServlet(name = "eliminarImagen", urlPatterns = {"/eliminarImagen"})
public class eliminarImagen extends HttpServlet {

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
        
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("username");
        user = "Silvia";
        
        String filename = request.getParameter("filename");
        
        try {
            dbConnection db = new dbConnection();
            int id = db.getIdFromFilename(filename);
            if (id != -1 && db.isOwner(id, user)) {
                db.deleteImage(id);
                // ELIMINAR FICHERO
                response.sendRedirect("./menu.jsp");
            } 
            // Image exists and user is the creator
            else response.sendRedirect("/lab1/error.jsp");
            db.closeDb();
        } catch (ClassNotFoundException | SQLException ex) {
            response.sendRedirect("/lab1/error.jsp");
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
