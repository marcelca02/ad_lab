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
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            
            
            
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
        //processRequest(request, response);
        
        
        
        System.out.println("In do post method of Display Image servlet.");
       
        
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
        
        System.out.println("Número de imágenes: " + images.size());
        //System.out.println("ID de la primera imagen: " + images.get(0).getId());

        RequestDispatcher rd;
        request.setAttribute("images",images); 
        //request.setAttribute("img",imgFileName);
        System.out.println("---- LISTA DE BUSQUEDA IMAGEN ENVIADA ----");
        rd=request.getRequestDispatcher("buscaImagen.jsp");
        rd.forward(request, response);
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
