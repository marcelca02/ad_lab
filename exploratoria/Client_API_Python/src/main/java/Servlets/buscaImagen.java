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
        
        
        System.out.println("CLIENT---DATE START: "+date_start);
        System.out.println("CLIENT---DATE END: "+date_end);
        System.out.println("CLIENT---Autor: "+authorB);
        System.out.println("CLIENT---Keywords: "+keywordsB);
        
        
        
        URL url = new URL("http://localhost:8080/RestAD/resources/jakartaee9/searchCombined");
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
        connection.setRequestProperty("Accept", "application/json");
        // Escribir los parámetros
        try (OutputStream output = connection.getOutputStream()){
            // Construye la cadena de datos 
            String data = "date_ini=" + date_start +
                            "&date_fin=" +date_end +
                            "&keywords=" +keywordsB +
                            "&author=" +authorB;
            
            output.write(data.getBytes("UTF-8"));
            output.close();
            
            System.out.println("ENVIADO BUSQUEDA");
            // Recibe la respuesta del servidor
            int responsecode = connection.getResponseCode();
            if (responsecode == HttpURLConnection.HTTP_OK){
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
                
                System.out.println("Envia a Servlet");

                RequestDispatcher rd;
                request.setAttribute("images",imagesList); 

                rd=request.getRequestDispatcher("buscaImagen.jsp");
                rd.forward(request, response);
                
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
    
    // Método auxiliar para convertir el JsonArray de keywords a un array de strings
    private static String[] getKeywordsArray(JsonArray keywordsArray) {
        String[] keywords = new String[keywordsArray.size()];
        for (int i = 0; i < keywordsArray.size(); i++) {
            keywords[i] = keywordsArray.getString(i);
        }
        return keywords;
    }
}
