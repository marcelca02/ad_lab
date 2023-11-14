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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        URL url = new URL("http://localhost:8080/RestAD/resources/jakartaee9/register");
        // Conectar URL
        try {
            URLConnection myURLConnection = url.openConnection();
            myURLConnection.connect();
        }
        catch (MalformedURLException e) {
            // Fallo de URL
            Logger.getAnonymousLogger().log(Level.SEVERE,"URL error", e);
        } 
        catch (IOException e){
            // fallo de la conexion
            Logger.getAnonymousLogger( ).log(Level.SEVERE, "I0 error",e);
        }
        
        //Abrir una conexión HTTP
        HttpURLConnection connection =(HttpURLConnection)url.openConnection();
        // Configurar el método de la petición 
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        // Escribir los parámetros
        try (OutputStream output = connection.getOutputStream()){
            // Construye la cadena de datos 
            String data = "title=" + title +
                            "&description=" +description +
                            "&keywords=" +keywords +
                            "&author=" +author +
                            "&creator=" + creator+
                            "&capture=" + capture_date +
                            "&filename=" + filename;
            
            output.write(data.getBytes("UTF-8"));
            output.close();
            
            System.out.println("ENVIADO REGISTRO");
            // Recibe la respuesta del servidor
            int responsecode = connection.getResponseCode();
            if (responsecode == HttpURLConnection.HTTP_OK){
                // La conexión fue exitosa
                //response.getWriter( ).write("Datos enviados correctamente al servidor.");
                
                
                
                
                // Redirect
                response.sendRedirect("/Client/menu.jsp");
            } else {
                //response.getWriter().write("Error al enviar datos al servidor. Código de respuesta: " + responsecode);
                response.sendRedirect("/Client/error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //response.getWriter().write("Error:"+ e.getMessage());
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
