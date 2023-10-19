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
            <h1>Hola</h1>
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
            <div class="main-content">
                <form action="modificarImagen" method="post">
                    Título: <input name="title" value="${title}" required></input>
                    <br>
                    <br>
                    Descripción: <input name="description" placeholder="Descripción" value="${description}" required></input>
                    <br>
                    <br>
                    Palabras clave: <input name="key" value="${keywords}" required></input>
                    <br>
                    <br>
                    Autor: <input name="author" value="${author}" required></input>
                    <br>
                    <br>
                    Fecha de captura: <input type="date" name="" value="${date}" required></input>
                    <br>
                    <br>
                    Nombre del fichero: <input name="filename" value="${filename}" required><br><br></input>
                    <input type="submit" value="Enviar"></input>
                </form>                  
            </div>
        </div>
    </body>
</html>
