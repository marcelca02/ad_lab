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
    </head>
    <body>
        <h1>¿Estás seguro de que quieres eliminar la imagen?</h1>
	<div>
	    <form action="eliminarImagen" method="get">
		<input type="hidden" name="imageId" value="${image.id}">
		<input type="hidden" name="imageCreator" value="${image.creator}">
		<input type="hidden" name="filename" value="${image.filename}">
		<button type="submit">Sí</button>
	    </form>
	    <form action="listaImagenes.jsp" method="get">
		<button type="submit">No</button>
	    </form>
	</div>
    </body>
</html>
