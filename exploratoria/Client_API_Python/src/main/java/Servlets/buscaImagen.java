/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Image;

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

        URL url = new URL("http://127.0.0.1:5000/searchImages");

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");

            try (OutputStream output = connection.getOutputStream()) {
                String data = "date_ini=" + URLEncoder.encode(date_start, "UTF-8") +
                              "&date_fin=" + URLEncoder.encode(date_end, "UTF-8") +
                              "&keywords=" + URLEncoder.encode(keywordsB, "UTF-8") +
                              "&author=" + URLEncoder.encode(authorB, "UTF-8");

                output.write(data.getBytes("UTF-8"));
            }

            int responsecode = connection.getResponseCode();
            if (responsecode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JsonArray jsonArray = Json.createReader(reader).readArray();

                List<Image> imagesList = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonImage = jsonArray.getJsonObject(i);
                    Image image = new Image();
                    image.setId(jsonImage.getInt("id"));
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

                    imagesList.add(image);
                }
                
                // Cerrar la conexión
                connection.disconnect();

                RequestDispatcher rd;
                request.setAttribute("images", imagesList);
                rd = request.getRequestDispatcher("buscaImagen.jsp");
                rd.forward(request, response);
            } else {

                response.sendRedirect("/Client_API_Python/error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/Client_API_Python/error.jsp");

        }
    
        
    }

}
