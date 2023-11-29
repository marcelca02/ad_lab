/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;
import utils.constants;

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
        
        // Get registro_imagen form parameters
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String keywords = request.getParameter("keywords");
        String author = request.getParameter("author");
        String creator = request.getParameter("creator");
        String capture_date = request.getParameter("capture_date");
        String storage_date = request.getParameter("storage_date");
        Part file=request.getPart("filename");
        // Nombre de Archivo
        String filename=file.getSubmittedFileName();
        
        
        response.setContentType("text/html;charset=UTF-8");
        
        System.out.println("ENVIADO REGISTRO");
        // Controla el tiempo
        long startTime = System.currentTimeMillis();
            
            System.out.println(title + " " + description + " " + author + " " + keywords + " " + capture_date + " " + filename);
        
            final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
            StreamDataBodyPart filePart = new StreamDataBodyPart("file", file.getInputStream());
            FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
            final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart
                    .field("title", title, MediaType.TEXT_PLAIN_TYPE)
                    .field("description", description, MediaType.TEXT_PLAIN_TYPE)
                    .field("keywords", keywords, MediaType.TEXT_PLAIN_TYPE)
                    .field("author", author, MediaType.TEXT_PLAIN_TYPE)
                    .field("creator", author, MediaType.TEXT_PLAIN_TYPE)
                    .field("capture", capture_date, MediaType.TEXT_PLAIN_TYPE)
                    .field("filename", filename, MediaType.TEXT_PLAIN_TYPE)
                    .bodyPart(filePart);


            final WebTarget target = client.target("http://localhost:8080/RestAD/resources/jakartaee9/register");
            final Response resp = target.request().post(Entity.entity(multipart, multipart.getMediaType()));
            int responsecode = resp.getStatus();

            formDataMultiPart.close();
            multipart.close();
            
            // Recibe la respuesta del servidor
            
            if (responsecode == HttpURLConnection.HTTP_OK){
                System.out.println("Subido con exito");
                // Finaliza el tiempo
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("El tiempo de ejecución de registro fue de: " + elapsedTime + " milisegundos.");
                // Redirect
                response.sendRedirect("/Client/menu.jsp");
            } else {
                //response.getWriter().write("Error al enviar datos al servidor. Código de respuesta: " + responsecode);
                response.sendRedirect("/Client/error.jsp");
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
