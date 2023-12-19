/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package utils;

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

/**
 *
 * @author Max Pasten
 */
@WebServlet(name = "prueba", urlPatterns = {"/prueba"})
public class prueba extends HttpServlet {

     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Obtener los parámetros del formulario JSP
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Conectar con la API Flask
            URL url = new URL("http://127.0.0.1:5000/login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            // Enviar datos de usuario y contraseña
            String postData = "username=" + username + "&password=" + password;
            con.getOutputStream().write(postData.getBytes("UTF-8"));

            // Leer la respuesta de la API
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder responseContent = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            in.close();

            // Mostrar la respuesta de la API
            out.println("<html><body>");
            out.println("<h1>Respuesta de la API:</h1>");
            out.println("<pre>" + responseContent.toString() + "</pre>");
            out.println("</body></html>");

        } catch (Exception e) {
            out.println("Ocurrió un error al conectar con la API: " + e.getMessage());
        }
    }

}
