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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import utils.constants;
/**
 *
 * @author marcel
 */
@WebServlet(name = "descargarImagen", urlPatterns = {"/descargarImagen"})
public class descargarImagen extends HttpServlet {

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
            
                
		String id = request.getParameter("imageId");
		String filename = request.getParameter("filename");
                
		
		/*
		// URL del servicio
		URL url = new URL("http://localhost:8080/RestAD/resources/jakartaee9/getImage"+"/"+filename);
		// Abrir la conexión HTTP
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// Configurar la solicitud
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");

		// Verificar el código de estado de la respuesta
		if (connection.getResponseCode() != 200) {
		    // Redirect
		    response.sendRedirect("/Client/listarImagenes.jsp");
		    throw new RuntimeException("Error: Código de estado " + connection.getResponseCode());
		}
		
		// OBTENER EL ARCHIVO QUE LLEGA DE LA LLAMADA
		*/
                
                
                try {
                    String serverURL = "http://localhost:8080/RestAD/resources/jakartaee9/getImage"+"/"+filename;
                    URL url = new URL(serverURL);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        FileOutputStream outputStream = new FileOutputStream(constants.IMAGESDIR + "/" + filename);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }

                        outputStream.close();
                        inputStream.close();
                        System.out.println("Imagen descargada exitosamente.");
                    } else {
                        System.out.println("Error al descargar la imagen. Código de respuesta: " + responseCode);
                    }
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        
        }
