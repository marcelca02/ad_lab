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
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Modificar Imagen</h1>
        <div>
            <form action="modificarImagen" method="post">
                Título: <input name="title" value="${title}" ></input>
                <br>
                <br>
                Descripción: <input name="description" placeholder="Descripción" value="${description}""></input>
                <br>
                <br>
                Palabras clave: <input name="key" value="${keywords}"></input>
                <br>
                <br>
                Nombre del fichero: <input name="filename" value="${filename}"></input>
                <button type="submit"></button>
            </form>                  
        </div>
    </body>
</html>
