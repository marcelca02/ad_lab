<%-- 
    Document   : confirmacionEliminar
    Created on : 14 nov 2023, 20:09:05
    Author     : marcel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="css/general.css"/>
    </head>
    <body>
	<div class="navbar">
            <h1>Eliminar Imagen</h1>
        </div>
        <div class="content">
            <div class="sidebar">
                <ul class="menu">
                    <li><a href="/Client/cerrarSesion">Cerrar Sesion</a></li>
                </ul>
            </div>
            <div class="main-content">
		<h1>¿Estás seguro de que quieres eliminar la imagen?</h1>
		<div>

		    <%session.getAttribute("username"); %>
		    <% 
		    int imageId = Integer.parseInt(request.getParameter("imageId"));
		    request.setAttribute("imageId", imageId);

		    String imageCreator = request.getParameter("imageCreator");
		    request.setAttribute("imageCreator", imageCreator);

		    String filename = request.getParameter("filename");
		    request.setAttribute("filename", filename);

		    if (session.getAttribute("username") == null) {
			response.sendRedirect("./listaImagenes.jsp");
		    }
		    %>

		    <form action="eliminarImagen" method="post">
			<input type="hidden" name="imageId" value="${imageId}">
			<input type="hidden" name="imageCreator" value="${imageCreator}">
			<input type="hidden" name="filename" value="${filename}">
			<button type="submit">Sí</button>
		    </form>
		    <form action="listaImagenes.jsp" method="get">
			<button type="submit">No</button>
		    </form>
		</div>
	    </div>
	</div>
    </body>
</html>
