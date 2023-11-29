/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import com.google.gson.Gson;
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
import com.google.gson.Gson;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import utils.Image;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
        String jsonString = "";
        try {
            URL url = new URL("http://localhost:8080/RestADSecundario/resources/jakartaee9/list");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder responseData = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    responseData.append(inputLine);
                }
                in.close();

                // Obtener la respuesta como un String completo
                jsonString = responseData.toString();

                // Hacer algo con el String, como imprimirlo en la consola del servidor
                System.out.println("Datos recibidos: " + jsonString);

            
                //request.setAttribute("jsonData", jsonString);
                //request.getRequestDispatcher("tupagina.jsp").forward(request, response);
            } else {
                
            }
        
        
            System.out.println("Datos recibidos: " + jsonString);
          
            
            List<Image> images = convertToImageList(jsonString);

          
            // Ahora 'images' es una lista de objetos Image
            for (Image image : images) {
                // Puedes trabajar con cada objeto Image aquí
                System.out.println(image.getTitle());
                System.out.println(image.getId());
                System.out.println(image.getDescription());
                System.out.println(image.getKeywords());
                System.out.println(image.getAuthor());
                System.out.println(image.getCreator());
                System.out.println(image.getCaptureDate());
                System.out.println(image.getStorageDate());
                System.out.println(image.getFilename());
            }
        
        
                    
        } catch (Exception e) {
            
            e.printStackTrace();
            response.sendRedirect("/error.jsp");
        }

    }
    
    
    private static List<Image> convertToImageList(String data) {
        List<Image> images = new ArrayList<>();

        // Eliminar corchetes exteriores y dividir los objetos individuales
        String[] objects = data.substring(1, data.length() - 1).split("\\},\\{");

        for (String obj : objects) {
            // Dividir cada objeto en sus partes clave-valor
            String[] parts = obj.replaceAll("[{}\"]", "").split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            Image image = new Image();

            for (String part : parts) {
                String[] keyValue = part.split(":");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "ID":
                        image.setId(Integer.parseInt(value));
                        break;
                    case "TITLE":
                        image.setTitle(value);
                        break;
                    case "DESCRIPTION":
                        image.setDescription(value);
                        break;
                    case "KEYWORDS":
                        // Procesamiento de las palabras clave
                        String[] keywordsArray = value.split(", ");
                        image.setKeywords(keywordsArray);
                        break;
                    case "AUTHOR":
                        image.setAuthor(value);
                        break;
                    case "CREATOR":
                        image.setCreator(value);
                        break;
                    case "CAPTURE_DATE":
                        image.setCaptureDate(value);
                        break;
                    case "STORAGE_DATE":
                        image.setStorageDate(value);
                        break;
                    case "FILENAME":
                        image.setFilename(value);
                        break;
                }
            }

            images.add(image);
        }

        return images;
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
