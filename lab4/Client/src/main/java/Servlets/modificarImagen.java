/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.constants;

/**
 *
 * @author marcel
 */
@WebServlet(name = "modificarImagen", urlPatterns = {"/modificarImagen"})
public class modificarImagen extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and
	 * <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String oldFilename = request.getParameter("oldFilename");
		String imageId = request.getParameter("imageId");
		
		if (imageId != null) {
                    
                    String title = request.getParameter("title");
                    String description = request.getParameter("description");
                    String keywords = request.getParameter("key");
                    String author = request.getParameter("author");
                    String creator = request.getParameter("imageCreator");
                    String capture_date = request.getParameter("date");
                    String filename = request.getParameter("filename");

                    //System.out.println("Title: "+ title);
                    //System.out.println("description: "+ description);
                    //System.out.println("keywords: "+ keywords);
                    //System.out.println("author: "+ author);
                    //System.out.println("creator: "+ creator);
                    //System.out.println("capture_date: "+ capture_date);
                    //System.out.println("filename: "+ filename);


                    URL url = new URL("http://localhost:8080/RestAD/resources/jakartaee9/modify");
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
                        String data = "id=" + imageId +
                                        "&title=" + title +
                                        "&description=" +description +
                                        "&keywords=" +keywords +
                                        "&author=" +author +
                                        "&creator=" + creator+
                                        "&capture=" + capture_date +
                                        "&filename=" + filename;

                        output.write(data.getBytes("UTF-8"));
                        output.close();
                        // Recibe la respuesta del servidor
                        int responsecode = connection.getResponseCode();
                        if (responsecode == HttpURLConnection.HTTP_OK){
                                File oldfile = new File(constants.IMAGESDIR+oldFilename);
                                File newfile = new File(constants.IMAGESDIR+filename);
                                oldfile.renameTo(newfile);

                                response.sendRedirect("./menu.jsp");
                        } else {
                                response.sendRedirect("./error.jsp");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.getWriter().write("Error:"+ e.getMessage());
                    }
		}
		else response.sendRedirect("./error.jsp");
		
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
