/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import classes.Image;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //HttpSession session = request.getSession();
        //String user = (String) session.getAttribute("username");
        
        //Image img = (Image) session.getAttribute("imagen");
        //String filename = img.getFilename();
        
        String imageId = request.getParameter("imageId");
        String imageCreator = request.getParameter("imageCreator");
        String filename = request.getParameter("filename");
        
        System.out.println("Servlet ID: " +imageId);
        System.out.println("Servlet Creator: " +imageCreator);
        System.out.println("Servlet oldfilename: " +filename);
        
        int id = Integer.parseInt(imageId);
        
        try {
            dbConnection db = new dbConnection();
            if (db.isOwner(id, imageCreator)) {
                db.deleteImage(id);
                File imagen = new File("C:\\Users\\Max Pasten\\lab1\\images\\"+filename);
                FileInputStream readImage = new FileInputStream(imagen);
                readImage.close();
                imagen.delete();
                response.sendRedirect("/lab1/eliminarImagen.jsp");
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
