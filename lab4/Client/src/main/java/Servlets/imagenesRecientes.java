/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
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
import utils.Image;

/**
 *
 * @author Max Pasten
 */
@WebServlet(name = "imagenesRecientes", urlPatterns = {"/imagenesRecientes"})
public class imagenesRecientes extends HttpServlet {

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
            URL url = new URL("http://localhost:8080/RestAD/resources/jakartaee9/imageRecent");
            // Abrir la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Verificar el código de estado de la respuesta
            if (connection.getResponseCode() != 200) {
                // Redirect
                response.sendRedirect("/Client/menu.jsp");
                throw new RuntimeException("Error: Código de estado " + connection.getResponseCode());
            }

            // Leer la respuesta JSON
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            JsonReader jsonReader = Json.createReader(reader);
            JsonArray jsonArray = jsonReader.readArray();

            // Convertir el JSON a una lista de objetos Image
            List<Image> imagesList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonImage = jsonArray.getJsonObject(i);

                // Crear un objeto Image y agregarlo a la lista
                Image image = new Image();
                image.setId(jsonImage.getInt("id"));
                image.setTitle(jsonImage.getString("title"));
                image.setDescription(jsonImage.getString("description"));
                image.setKeywords(getKeywordsArray(jsonImage.getJsonArray("keywords")));
                image.setAuthor(jsonImage.getString("author"));
                image.setCreator(jsonImage.getString("creator"));
                image.setCaptureDate(jsonImage.getString("captureDate"));
                image.setStorageDate(jsonImage.getString("storageDate"));
                image.setFilename(jsonImage.getString("filename"));
		image.setImage(jsonImage.getString("image"));

                // Añadir el objeto Image a la lista
                imagesList.add(image);
            }


            // Cerrar la conexión
            connection.disconnect();
            
            request.setAttribute("images", imagesList);
//            request.getRequestDispatcher("listaImagenes.jsp").forward(request, response);
            

        } catch (Exception e) {
            // Redirect
            response.sendRedirect("/Client/menu.jsp");
            e.printStackTrace();
        }
    }
    
    // Método auxiliar para convertir el JsonArray de keywords a un array de strings
    private static String[] getKeywordsArray(JsonArray keywordsArray) {
        String[] keywords = new String[keywordsArray.size()];
        for (int i = 0; i < keywordsArray.size(); i++) {
            keywords[i] = keywordsArray.getString(i);
        }
        return keywords;
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
