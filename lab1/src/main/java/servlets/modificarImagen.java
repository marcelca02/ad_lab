/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import classes.Image;
import utils.dbConnection;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author marcel
 */
@WebServlet(name = "modificarImagen", urlPatterns = {"/modificarImagen"})
public class modificarImagen extends HttpServlet {

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Image img = (Image) session.getAttribute("image");
        
        int id = img.getId();
        
        String title = request.getParameter("title");
        String keywords = request.getParameter("key");
        String description = request.getParameter("description");
        String filename = request.getParameter("filename");
        
        dbConnection db = new dbConnection();
        
        try {
            if (!db.isOwner(id, img.getCreator())) {
                //send redirect
            } 
            else db.modifyImage(id, title, description, keywords, filename);      
        } 
        catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(modificarImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
