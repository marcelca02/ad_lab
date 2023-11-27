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
import jakarta.servlet.http.Part;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;
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
			final Part fileP = request.getPart("image");
			String file_name = fileP.getSubmittedFileName();

			System.out.println(title + " " + description + " " + author + " " + keywords + " " + cr_date + " " + file_name);

			final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
			StreamDataBodyPart filePart = new StreamDataBodyPart("file", fileP.getInputStream());
			FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
			final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart
				.field("title", title, MediaType.TEXT_PLAIN_TYPE)
				.field("description", description, MediaType.TEXT_PLAIN_TYPE)
				.field("keywords", keywords, MediaType.TEXT_PLAIN_TYPE)
				.field("author", author, MediaType.TEXT_PLAIN_TYPE)
				.field("creator", author, MediaType.TEXT_PLAIN_TYPE)
				.field("capture", capture_date, MediaType.TEXT_PLAIN_TYPE)
				.field("filename", file_name, MediaType.TEXT_PLAIN_TYPE)
				.bodyPart(filePart);


			final WebTarget target = client.target("http://localhost:8080/RestAD/resources/jakartaee9/registerTest");
			final Response resp = target.request().post(Entity.entity(multipart, multipart.getMediaType()));
			int status = resp.getStatus();

			formDataMultiPart.close();
			multipart.close();
        
			System.out.println("STATUS: " + status);

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
