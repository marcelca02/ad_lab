/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;


import utils.dbConnection;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import utils.constants;

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
            throws ServletException, IOException, ClassNotFoundException, SQLException{

        // Get registro_imagen form parameters
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String keywords = request.getParameter("keywords");
        String author = request.getParameter("author");
        String creator = request.getParameter("creator");
        String capture_date = request.getParameter("capture_date");
        String storage_date = request.getParameter("storage_date");
        //String filename = request.getParameter("filename");
        
        System.out.println("Fecha: "+ storage_date);
        System.out.println("Creator:" + creator);
        
        // Controla el tiempo
        long startTime = System.currentTimeMillis();

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            dbConnection db = new dbConnection();
            
            Part file=request.getPart("filename");
            
            // Nombre de Archivo
            String imageFileName=file.getSubmittedFileName();  // get selected image file name

            out.println("Selected Image File Name : "+imageFileName);
            
            //String uploadPath="C:\\Users\\Max Pasten\\lab1\\images\\"+imageFileName;
            String uploadPath=constants.IMAGESDIR+imageFileName;
            out.println("Upload Path : "+uploadPath);

            // Uploading our selected image into the images folder

            try{

                FileOutputStream fos=new FileOutputStream(uploadPath);
                InputStream is=file.getInputStream();

                byte[] data=new byte[is.available()];
                is.read(data);
                fos.write(data);
                fos.close();
                
                
                db.registerImage(title, description, keywords, author, creator, capture_date, storage_date, imageFileName);

            }

            catch(Exception e){
                out.println("--ERROR-- : "+e);
                e.printStackTrace();
                response.sendRedirect("/lab1/error.jsp");
            }
            
            
            db.closeDb();
            
            // Finaliza el tiempo
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("El tiempo de ejecución de registro fue de: " + elapsedTime + " milisegundos.");
            
            response.sendRedirect("/lab1/menu.jsp");
            /* TODO output your page here. You may use following sample code. */
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(registro_imagen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            System.err.println(e.getMessage());
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(registro_imagen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(registro_imagen.class.getName()).log(Level.SEVERE, null, ex);
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
