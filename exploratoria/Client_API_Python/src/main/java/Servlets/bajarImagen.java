/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import jakarta.servlet.RequestDispatcher;
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
import java.util.List;

/**
 *
 * @author Max Pasten
 */
@WebServlet(name = "bajarImagen", urlPatterns = {"/bajarImagen"})
public class bajarImagen extends HttpServlet {

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Realizar la solicitud al endpoint del servidor que proporciona el JSON con las imágenes en base64
        String serverURL = "http://localhost:8080/RestAD/resources/jakartaee9/imageList"; // Reemplaza con la URL correcta
        HttpURLConnection connection = (HttpURLConnection) new URL(serverURL).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response0 = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response0.append(line);
            }
            reader.close();

            // Obtener la lista de imágenes en base64 desde la respuesta JSON
            List<String> imageBase64List = parseImageNames(response0.toString());

            // Enviar los datos al JSP para que los muestre
            request.setAttribute("imageBase64List", imageBase64List);
            RequestDispatcher dispatcher = request.getRequestDispatcher("lista.jsp"); // Reemplaza con tu JSP
            dispatcher.forward(request, response);
        } else {
            // Manejar el caso de error al obtener las imágenes
            // Puedes redirigir a una página de error o mostrar un mensaje adecuado
            response.getWriter().println("Error al obtener las imágenes. Código de respuesta: " + responseCode);
        }
    }

    // Método para analizar el JSON y obtener la lista de imágenes en base64
    private List<String> parseImageNames(String jsonResponse) {
        // Implementa la lógica para analizar la respuesta JSON y obtener la lista de imágenes en base64
        // Retorna la lista de imágenes en base64
        return null; // Reemplaza esto con la lista de imágenes en base64
    }
}
