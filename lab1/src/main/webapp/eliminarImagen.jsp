<%-- 
    Document   : eliminarImagen
    Created on : 14 oct 2023, 17:52:24
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
        <h1>Eliminar imagen</h1>
        <form action="eliminarImagen" method="post">
            <input name="filename" placeholder="Nombre del archivo">
            <button type="submit"></button>
        </form>
        <li><a href="./menu.jsp">Menú</a></li>
    </body>
</html>
