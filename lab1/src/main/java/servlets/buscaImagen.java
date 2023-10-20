/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.dbConnection;

import classes.Image;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Max Pasten
 */
@WebServlet(name = "buscaImagen", urlPatterns = {"/buscaImagen"})
public class buscaImagen extends HttpServlet {

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
        
        String date_start = request.getParameter("fecha_inicio");
        String date_end = request.getParameter("fecha_fin");
        List<Image> images = new ArrayList<>();
        
        try {
            
            dbConnection db = new dbConnection();            
            images = db.searchImage(date_start, date_end);
            db.closeDb();
           

        }
        catch (Exception e)
        {
                System.out.println(e);
        }
        
        RequestDispatcher rd;
        request.setAttribute("images",images); 
        
        rd=request.getRequestDispatcher("buscaImagen.jsp");
        rd.forward(request, response);
    }

}
