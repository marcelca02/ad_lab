<%-- 
    Document   : modificarImagen
    Created on : 5 oct 2023, 12:20:53
    Author     : marcel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificar Imagen</title>
        <link rel="stylesheet" type="text/css" href="css/general.css"/>
    </head>
    <body>
        <div class="navbar">
        <% session.getAttribute("username"); %>
                
        <% 
        int imageId = Integer.parseInt(request.getParameter("imageId"));
        request.setAttribute("imageId", imageId);
        
        String imageCreator = request.getParameter("imageCreator");
        request.setAttribute("imageCreator", imageCreator);
        
        String title = request.getParameter("title");
        request.setAttribute("title", title);
        
        String description = request.getParameter("description");
        request.setAttribute("description", description);
        
        String keywords = request.getParameter("keywords");
        request.setAttribute("keywords", keywords);
        
        String date = request.getParameter("date");
        request.setAttribute("date", date);
        
        String author = request.getParameter("author");
        request.setAttribute("author", author);
        
        String oldFilename = request.getParameter("oldFilename");
        request.setAttribute("oldFilename", oldFilename);
        
        if (session.getAttribute("username") == null) {
            response.sendRedirect("/Client/listaImagenes.jsp");
        }
        %>
            <h1>Modificar Imagen</h1>
        </div>
        <div class="content">
            <div class="sidebar">
                <ul class="menu">
                    <li><a href="/Client/registro_imagen.jsp">Registrar Imagen</a></li>
                    <li><a href="/Client/listaImagenes.jsp">Listar Imagenes</a></li>
                    <li><a href="/Client/buscaImagen.jsp">Buscar Imagen</a></li>
                    <li><a href="/Client/cerrarSesion">Cerrar Sesion</a></li>
                </ul>
            </div>
            <div class="main-content">
                <form action="modificarImagen" method="post">
                    <label2>Título:</label>
                    <input name="title" value="${title}" required></input>
                    <br>
                    <br>
                    <label>Descripción:</label><br>
                    <textarea name="description" rows="4" cols="50" value="${description}" required></textarea>
                    <br>
                    <br>
                    <label>Palabras Clave:</label>
                    <input name="key" value="${keywords}" required></input>
                    <br>
                    <br>
                    <label>Autor:</label>
                    <input name="author" value="${author}" required></input>
                    <br>
                    <br>
                    <label>Fecha Captura:</label>
                    <input type="date" name="date" value="${date}" required></input>
		    <br>
                    <br>
		    <label>Autor:</label>
                    <input name="filename" value="${oldFilename}" required></input>
		    <br>
                    <br>
		    <label for="nombre_imagen">Nombre Archivo:</label>
                    <input type="file" id="imagen" name="file" accept="image/*" onchange="previewImage(event)" required>
		    
                    

                    
                    <input type="hidden" name="imageId" value="${imageId}">
                    <input type="hidden" name="imageCreator" value="${imageCreator}">
                    <input type="hidden" name="oldFilename" value="${oldFilename}">
                    
                    
                    <input type="submit" value="Enviar"></input>
                </form>                 
            </div>
        </div>
    </body>
</html>
