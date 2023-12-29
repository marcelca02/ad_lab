/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 *
 * @author Max Pasten
 */
@MultipartConfig
@WebServlet(name = "registroImagen", urlPatterns = {"/registroImagen"})
public class registroImagen extends HttpServlet {

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
       
    public registroImagen() {
        super();
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        System.out.println("REGISTROOO");

        // Obtención de parámetros del formulario
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String keywords = request.getParameter("keywords");
        String author = request.getParameter("author");
        String creator = request.getParameter("creator");
        String capture_date = request.getParameter("capture_date");
        String storage_date = request.getParameter("storage_date");
        // Obtener la parte del archivo de imagen del formulario
        Part filePart = request.getPart("filename"); 
        String filename = filePart.getSubmittedFileName();
        InputStream fileContent = filePart.getInputStream();
        byte[] fileBytes = fileContent.readAllBytes();
        
        System.out.println("title: " + title);
        System.out.println("description: " + description);
        System.out.println("keywords: " + keywords);
        System.out.println("author: " + author);
        System.out.println("creator: " +creator );
        System.out.println("capture_date: " + capture_date);
        System.out.println("storage_date: " +storage_date);
        System.out.println("Kfilename: " +filename );
        
        // URL del endpoint en Flask
        String flaskEndpoint = "http://127.0.0.1:5000/createImage"; 

        String f1 = capture_date.toString();
        String f2 = storage_date.toString();
        
        // Configurar la conexión HTTP con el endpoint en Flask
        URL url = new URL(flaskEndpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/octet-stream"); // Tipo MIME de la imagen
        // Enviar los atributos como encabezados
        connection.setRequestProperty("title", title); 
        connection.setRequestProperty("description", description);
        connection.setRequestProperty("keywords", keywords); 
        connection.setRequestProperty("author", author);
        connection.setRequestProperty("creator", creator); 
        connection.setRequestProperty("filename", filename);
        connection.setRequestProperty("f1", f1);
        connection.setRequestProperty("f2", f2);
        
        

        // Enviar los atributos y la imagen al servidor Flask
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(fileBytes);
            outputStream.flush();
        }

        // Obtener el código de respuesta
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Subido con exito");
            // Redirect
            response.sendRedirect("./menu.jsp");
        } else {
            // Redirect
            response.sendRedirect("./error.jsp");
        }

        connection.disconnect();

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
        processRequest(request, response);
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
        processRequest(request, response);
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
