/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import utils.Image;
import utils.dbConnection;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.constants;


/**
 *
 * @author marcel
 */
@WebServlet(name = "modificarImagen", urlPatterns = {"/modificarImagen"})
public class modificarImagen extends HttpServlet {

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //HttpSession session = request.getSession();
        //Image img = (Image) session.getAttribute("imagen");
        
        String imageId = request.getParameter("imageId");
        String imageCreator = request.getParameter("imageCreator");
        String oldFilename = request.getParameter("oldFilename");
        
        
        if (imageId != null) {
        
            int id = Integer.parseInt(imageId);

            String title = request.getParameter("title");
            String keywords = request.getParameter("key");
            String description = request.getParameter("description");
            String author = request.getParameter("author");
            String dateCapture = request.getParameter("date");
            String filename = request.getParameter("filename");

            try {
                dbConnection db = new dbConnection();
                if (!db.isOwner(id, imageCreator)) {
                    response.sendRedirect("./error.jsp");
                } 
                else {
                    // Modificar nombre del archivo
                    File oldfile = new File(constants.IMAGESDIR+oldFilename);
                    File newfile = new File(constants.IMAGESDIR+filename);
                    if (oldfile.renameTo(newfile)) {
                        db.modifyImage(id, title, description, keywords, author, dateCapture, filename);
                        response.sendRedirect("/lab1/menu.jsp");
                    } else {
                        db.modifyImage(id, title, description, keywords, author, dateCapture, oldFilename);
                        response.sendRedirect("/lab1/error.jsp");
                    }
                }
                db.closeDb();
            } 
            catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(modificarImagen.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("/lab1/error.jsp");
            }
        }
        else response.sendRedirect("/lab1/error.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
