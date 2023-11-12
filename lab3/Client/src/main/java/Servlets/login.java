/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {

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
        
        // Get login form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        URL url = new URL("http://localhost:8080/RestAD/resources/jakartaee9/login");
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
        // Configurar el método de la petición a
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        // Escribir los parámetros
        try (OutputStream output = connection.getOutputStream()){
            // Construye la cadena de datos a env
            String data = "username=" + username +
                            "&password=" +password;
            
            output.write(data.getBytes("UTF-8"));
            output.close();
            
            System.out.println("ENVIADO");
            
            
            // Recibe la respuesta del servidor
            int responsecode = connection.getResponseCode();
            if (responsecode == HttpURLConnection.HTTP_OK){
                // La conexión fue exitosa
                // Puedes hacer algo con la respuesta si es necesari
                response.getWriter( ).write("Datos enviados correctamente al servidor.");
            } else {
                response.getWriter().write("Error al enviar datos al servidor. Código de respuesta: " + responsecode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error:"+ e.getMessage());
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
