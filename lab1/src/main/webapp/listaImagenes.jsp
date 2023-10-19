<%-- 
    Document   : listaImagenes
    Created on : 14 oct. de 2023, 12:40:57
    Author     : Max Pasten
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import= "java.sql.Connection" %>

<%@ page import= "java.sql.DriverManager" %>
<%@ page import= "java.sql.PreparedStatement" %>
<%@ page import= "java.sql.ResultSet" %>
<%@ page import= "java.sql.SQLException" %>


<%@ page import="classes.Image" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu</title>
        <link rel="stylesheet" type="text/css" href="css/general.css"/>

        <style>
            
            .image-container {
                display: flex;
                align-items: center;
                border-bottom: 1px solid black; /* Agrega un borde inferior a cada imagen */
                padding: 10px; /* Espacio entre la imagen y los atributos */
            }

            .image-container img {
                width: 300px;
                margin-right: 2px; /* Espacio entre la imagen y los atributos de texto */
            }

            .image-attributes {
                flex-grow: 1;
                text-align: left; /* Centra el texto */
                margin-left: 10px;
            }
            .attribute-label {
                font-weight: bold; /* Pone el texto en negritas */
            }
            
            
            .button-container {
                display: flex;
                justify-content: space-between;
            }

            .button-container button {
                margin: 5px;
            }
            
        </style>
    </head>
    <body>
        <div class="navbar">
            <h1>Lista de Imagenes</h1>
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
                <h2>Imagenes</h2>
                <div align="center">

                    <%


                        Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");

                        String query;
                        PreparedStatement statement;

                        Class.forName("org.apache.derby.jdbc.ClientDriver");

                        // create a database connection
                        connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");

                        query = "select * from image";
                        statement = connection.prepareStatement(query);
                        ResultSet rs = statement.executeQuery();         

                        List<Image> images = new ArrayList<>();

                        while (rs.next()) {
                            Image image = new Image();
                            image.setId(rs.getInt("id"));
                            image.setTitle(rs.getString("title"));
                            image.setFilename(rs.getString("filename"));
                            images.add(image);
                        }

                        request.setAttribute("images", images);
                        //request.getRequestDispatcher("tuPagina.jsp").forward(request, response);


                    %>

                    <table border="1">
                         <c:forEach var="image" items="${images}">
                            <div class="image-container">
                                <img src="images/${image.filename}" alt="Imagen" />
                                <div class="image-attributes">
                                    <div><span class="attribute-label">ID:</span> ${image.id}</div>
                                    <div><span class="attribute-label">Title:</span> ${image.title}</div>
                                    <div><span class="attribute-label">Description:</span> ${image.description}</div>
                                    <div><span class="attribute-label">Palabras:</span> ${image.keywords}</div>
                                    <div><span class="attribute-label">Creador:</span> ${image.author}</div>
                                    <div><span class="attribute-label">Autor:</span> ${image.creator}</div>
                                    <div><span class="attribute-label">CaptureDate:</span> ${image.captureDate}</div>
                                    <div><span class="attribute-label">StorageDate:</span> ${image.storageDate}</div>
                                    <div><span class="attribute-label">Nombre Archivo:</span> ${image.filename}</div>
                                </div>
                                <div class="button-container">
                                    <button onclick="window.location.href='/lab1/error.jsp'">Modificar</button>
                                    <button onclick="window.location.href='/lab1/error.jsp'">Eliminar</button>
                                </div>
                            </div>
                        </c:forEach>
                    </table>


                </div>
            </div>
        </div>
    </body>
</html>
