/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
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
        String authorB = request.getParameter("authorB");
        String keywordsB = request.getParameter("keywordsB");
        List<Image> images = new ArrayList<>();
        
        System.out.println("DATE START: "+date_start);
        System.out.println("DATE END: "+date_end);
        System.out.println("Autor: "+authorB);
        System.out.println("Keywords: "+keywordsB);
        
        try {
            
            dbConnection db = new dbConnection();            
            
            if (authorB.length() == 0 && keywordsB.length() == 0){
                System.out.println("Date");
                System.out.println("Autor: "+authorB);
                System.out.println("Keywords: "+keywordsB);
                images = db.searchImageDate(date_start, date_end);
            } else if (authorB.length() != 0 && keywordsB.length() != 0){
                System.out.println("DateAuthorKeywords");
                System.out.println("Autor: "+authorB);
                System.out.println("Keywords: "+keywordsB);
                images = db.searchImageDateAuthorKeywords(date_start, date_end, authorB, keywordsB);
            } else if (keywordsB.length() != 0 && authorB.length() == 0){
                System.out.println("Datekeyword");
                System.out.println("Autor: "+authorB);
                System.out.println("Keywords: "+keywordsB);
                images = db.searchImageDateKeywords(date_start, date_end, keywordsB);
                
                
            } else if (keywordsB.length() == 0&& authorB.length() != 0){
                System.out.println("DateAutor");
                System.out.println("Autor: "+authorB);
                System.out.println("Keywords: "+keywordsB);
                images = db.searchImageDateAuthor(date_start, date_end, authorB);
                
            }
            
            
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
