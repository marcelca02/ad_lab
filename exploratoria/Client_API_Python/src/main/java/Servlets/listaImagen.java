/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.StringReader;



import utils.Image;
/**
 *
 * @author Max Pasten
 */
@WebServlet(name = "listaImagen", urlPatterns = {"/listaImagen"})
public class listaImagen extends HttpServlet {

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
        try {
            // URL del servicio
            URL url = new URL("http://127.0.0.1:5000/listImages");
            // Abrir la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Verificar el código de estado de la respuesta
            if (connection.getResponseCode() != 200) {
                // Redirect
                System.out.println("Error: Código de estado " + connection.getResponseCode());
                response.sendRedirect("./menu.jsp");
                throw new RuntimeException("Error: Código de estado " + connection.getResponseCode());
            }

            // Leer la respuesta JSON
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }

            reader.close();

            // Configurar tiempo de espera para la conexión 
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            // Convertir la respuesta JSON a una lista de objetos Image
            JsonArray jsonArray = Json.createReader(new StringReader(jsonResponse.toString())).readArray();
            List<Image> imagesList = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonImage = jsonArray.getJsonObject(i);

                // Crear un objeto Image y agregarlo a la lista
                Image image = new Image();
                image.setId(jsonImage.getInt("id"));
                System.out.println("Image: " + jsonImage.getInt("id"));
                image.setTitle(jsonImage.getString("name"));
                System.out.println("Image: " + jsonImage.getString("name"));
                image.setDescription(jsonImage.getString("description"));
                System.out.println("Image: " + jsonImage.getString("name"));
                image.setKeywords(jsonImage.getString("keywords").split("[,\\s]+"));
                System.out.println("Image: " + jsonImage.getString("keywords").split("[,\\s]+"));
                image.setAuthor(jsonImage.getString("author"));
                image.setCreator(jsonImage.getString("creator"));
                image.setCaptureDate(jsonImage.getString("date_capture"));
                image.setStorageDate(jsonImage.getString("date_upload"));
                image.setFilename(jsonImage.getString("filename"));
                image.setImage(jsonImage.getString("base64"));

                // Añadir el objeto Image a la lista
                imagesList.add(image);
            }

            // Cerrar la conexión
            connection.disconnect();

            // Establecer la lista de imágenes como un atributo en el request
            request.setAttribute("images", imagesList);
            // Verificar si la respuesta aún no ha sido confirmada o redirigida
            if (!response.isCommitted()) {
                // Redirigir a listaImagenes.jsp
                request.getRequestDispatcher("listaImagenes.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Manejo de errores: Redirigir a menu.jsp en caso de excepción
            response.sendRedirect("./menu.jsp");
            e.printStackTrace();
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
