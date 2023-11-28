/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
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
import java.io.IOException;
import java.net.HttpURLConnection;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;
/**
 *
 * @author marcel
 */
@MultipartConfig
@WebServlet(name = "modificarImagen", urlPatterns = {"/modificarImagen"})
public class modificarImagen extends HttpServlet {
	/**
	* Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	* methods.
	*
	* @param request servlet request
	* @param response servlet response
	* @throws ServletException if a servlet-specific error occurs
	* @throws IOException if an I/O error occurs
	*/
       private static final long serialVersionUID = 1L;

       public modificarImagen() {
	   super();
       }

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
			String id = request.getParameter("imageId");
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String keywords = request.getParameter("key");
			String author = request.getParameter("author");
			String creator = request.getParameter("imageCreator");
			String capture_date = request.getParameter("date");
			final Part fileP = request.getPart("file");
			String filename = fileP.getSubmittedFileName();

			final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
			StreamDataBodyPart filePart = new StreamDataBodyPart("file", fileP.getInputStream());
			FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
			final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart
				.field("id", id, MediaType.TEXT_PLAIN_TYPE)
				.field("title", title, MediaType.TEXT_PLAIN_TYPE)
				.field("description", description, MediaType.TEXT_PLAIN_TYPE)
				.field("keywords", keywords, MediaType.TEXT_PLAIN_TYPE)
				.field("author", author, MediaType.TEXT_PLAIN_TYPE)
				.field("creator", creator, MediaType.TEXT_PLAIN_TYPE)
				.field("capture", capture_date, MediaType.TEXT_PLAIN_TYPE)
				.field("filename", filename, MediaType.TEXT_PLAIN_TYPE)
				.bodyPart(filePart);

			

			final WebTarget target = client.target("http://localhost:8080/RestAD/resources/jakartaee9/modify");
			final Response resp = target.request().post(Entity.entity(multipart, multipart.getMediaType()));
			int status = resp.getStatus();
			

			formDataMultiPart.close();
			multipart.close();
        
			System.out.println("STATUS: " + status);

			// Recibe la respuesta del servidor
            
			if (status == HttpURLConnection.HTTP_OK){
			    System.out.println("Modificado con éxito");
			    // Redirect
			    response.sendRedirect("/Client/menu.jsp");
			} else {
			    //response.getWriter().write("Error al enviar datos al servidor. Código de respuesta: " + responsecode);
			    response.sendRedirect("/Client/error.jsp");
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
