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
        <title>Eliminar Imagen</title>
        <link rel="stylesheet" type="text/css" href="css/general.css"/>
    </head>
    <body>
        <div class="navbar">
            <h1>Eliminar Imagen</h1>
        </div>
        <div class="content">
            <div class="sidebar">
                <ul class="menu">
                    <li><a href="/lab1/registro_imagen.jsp">Registrar Imagen</a></li>
                    <li><a href="/lab1/listaImagenes.jsp">Listar Imagenes</a></li>
                    <li><a href="#">Buscar Imagen</a></li>
                    <li><a href="#">Cerrar Sesion</a></li>
                </ul>
            </div>
            <h1>Eliminar imagen</h1>
            <form action="eliminarImagen" method="post">
                <input name="filename" placeholder="Nombre del archivo">
                <input type="submit" value="Enviar"><br><br></input>
            </form>
            <li><a href="./menu.jsp">Menú</a></li>
        </div>
    </body>
</html>
