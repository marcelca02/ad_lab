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


import java.util.Map;

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
        
        // Eliminar los corchetes del principio y final del JSON
        jsonString = jsonString.substring(1, jsonString.length() - 1);

        // Dividir el JSON en objetos individuales
        String[] jsonObjects = jsonString.split(Pattern.quote("},{"));
        
            System.out.println("SDFASFDSDF");

        for(String d: jsonObjects){
            System.out.println(d);
        }
        
        jsonObjects[0] = jsonObjects[0].substring(1, jsonObjects[0].length());
        
        jsonObjects[jsonObjects.length - 1] = jsonObjects[jsonObjects.length - 1].substring(0, jsonObjects[jsonObjects.length - 1].length() - 1);
        
            System.out.println("CAMBIO");
        
            List<Image> images = new ArrayList<>();
        for(String d: jsonObjects){
            
            
            String modifiedString = d.replace("\",\"", "\";\"");
            System.out.println(modifiedString);
            
            String[] values = modifiedString.split(";");
            for(String v: values){
                System.out.println("SPLIT");
                System.out.println(v);
            }
            
            // Extracción de los valores
            System.out.println("ID" + values[0].split(":")[1].substring(1, values[0].split(":")[1].length() - 1));
            int id = Integer.parseInt(values[0].split(":")[1].substring(1, values[0].split(":")[1].length() - 1));
            System.out.println("ID :"+ id);
            String title = values[1].split(":")[1].substring(1, values[1].split(":")[1].length() - 1);
            String description = values[2].split(":")[1].substring(1, values[2].split(":")[1].length() - 1);
            String[] keywords = values[3].split(":")[1].split(" "); // Separar las palabras clave
            String author = values[4].split(":")[1].substring(1, values[4].split(":")[1].length() - 1);
            String creator = values[5].split(":")[1].substring(1, values[5].split(":")[1].length() - 1);
            String captureDate = values[6].split(":")[1].substring(1, values[6].split(":")[1].length() - 1);
            String storageDate = values[7].split(":")[1].substring(1, values[7].split(":")[1].length() - 1);
            String filename = values[8].split(":")[1].substring(1, values[8].split(":")[1].length() - 1);

            System.out.println("CREA");
            // Creación del objeto Image
            Image image = new Image(id, title, description, keywords, author, creator, captureDate, storageDate, filename);
            
            // Verificación imprimiendo los valores del objeto Image
            System.out.println("ID: " + image.getId());
            System.out.println("Title: " + image.getTitle());
            System.out.println("Description: " + image.getDescription());
            System.out.println("Keywords: ");
            for (String keyword : image.getKeywords()) {
                System.out.println("\t" + keyword);
            }
            System.out.println("Author: " + image.getAuthor());
            System.out.println("Creator: " + image.getCreator());
            System.out.println("Capture Date: " + image.getCaptureDate());
            System.out.println("Storage Date: " + image.getStorageDate());
            System.out.println("Filename: " + image.getFilename());
            
            images.add(image);
    
        }
        request.setAttribute("images", images);
                request.getRequestDispatcher("listaImagenes.jsp").forward(request, response);
        
        

        
        
        /*
        
        // Procesar cada objeto JSON individual
        for (String jsonObject : jsonObjects) {
            // Convertir los campos del objeto JSON en campos de Image
            String[] fields = jsonObject.split(",");
            Image image = new Image();

            for (String field : fields) {
                String[] keyValue = field.split(":");
                String key = keyValue[0].replaceAll("\"", "").trim();
                String value = keyValue[1].replaceAll("\"", "").trim();
                
                System.out.println(field);
                System.out.println("key: "+ key);
                System.out.println("Value: "+ value);

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
                        image.setKeywords(value.split(", "));
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

        // Verificar los datos
        for (Image image : images) {
            System.out.println("Title: " + image.getTitle());
            System.out.println("Keywords:");
            for (String keyword : image.getKeywords()) {
                System.out.println("- " + keyword);
            }
            System.out.println("------------------------");
        }
    
            
          /*
            
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
        
        */
                    
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
