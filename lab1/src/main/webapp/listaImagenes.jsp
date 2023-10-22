<%-- 
    Document   : listaImagenes
    Created on : 14 oct. de 2023, 12:40:57
    Author     : Max Pasten
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="classes.Image" %>
<%@ page import="utils.dbConnection" %>
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
            <%= session.getAttribute("username") %>
            <h1>Lista de Imagenes</h1>
        </div>
        <div class="content">
            <div class="sidebar">
                <ul class="menu">
                    <li><a href="/lab1/registro_imagen.jsp">Registrar Imagen</a></li>
                    <li><a href="/lab1/listaImagenes.jsp">Listar Imagenes</a></li>
                    <li><a href="/lab1/buscaImagen.jsp">Buscar Imagen</a></li>
                    <li><a href="/lab1/cerrarSesion">Cerrar Sesion</a></li>
                </ul>
            </div>
            <div class="main-content">
                <h2>Imagenes</h2>
                <div align="center">

                    <%
                        dbConnection db = new dbConnection();
                        List<Image> images = db.listImages();
                        db.closeDb();
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
                                    <div><span class="attribute-label">Palabras:</span>
                                        <c:set var="myArray" value="${image.keywords}" />
                                        <c:forEach var="elemento" items="${myArray}" varStatus="status">
                                            ${elemento}<c:if test="${!status.last}">,</c:if>
                                        </c:forEach>
                                    </div>
                                    <div><span class="attribute-label">Creador:</span> ${image.creator}</div>
                                    <div><span class="attribute-label">Autor:</span> ${image.author}</div>
                                    <div><span class="attribute-label">CaptureDate:</span> ${image.captureDate}</div>
                                    <div><span class="attribute-label">StorageDate:</span> ${image.storageDate}</div>
                                    <div><span class="attribute-label">Nombre Archivo:</span> ${image.filename}</div>
                                    <div><a href="images/${image.filename}" target="_blank">Imagen completa</a></div>
                                     
                                </div>
                                
                                <c:set var="userCre" value= "${image.creator}" />
                                <c:set var="keywordsList" value= "${image.keywords}" />
                                <% 
                                    String userCre = (String)pageContext.getAttribute("userCre");
                                    String userLog = (String)session.getAttribute("username");
                                    
                                    String[] keywordsList = (String[])pageContext.getAttribute("keywordsList");
                                    String keywords = String.join(", ", keywordsList);
                                    request.setAttribute("keywords", keywords);

                                    if (userCre.equals(userLog)){
                                    
                                %>
                                
                                <div class="button-container">
                                    <form action="modificarImagen.jsp" method="post">
                                        <input type="hidden" name="imageId" value="${image.id}">
                                        <input type="hidden" name="title" value="${image.title}">
                                        <input type="hidden" name="description" value="${image.description}">
                                        <input type="hidden" name="keywords" value="${keywords}">
                                        <input type="hidden" name="imageCreator" value="${image.creator}">
                                        <input type="hidden" name="author" value="${image.author}">
                                        <input type="hidden" name="date" value="${image.captureDate}">
                                        <input type="hidden" name="oldFilename" value="${image.filename}">
                                        <button type="submit" name="action" value="modificar">Modificar</button>
                                    </form>
                                    <form action="eliminarImagen" method="get">
                                        <input type="hidden" name="imageId" value="${image.id}">
                                        <input type="hidden" name="imageCreator" value="${image.creator}">
                                        <input type="hidden" name="filename" value="${image.filename}">
                                        <button type="submit">Eliminar</button>
                                    </form>
                                </div>

                                <% 
                                    }
                                %>
                            </div>
                        </c:forEach>
                    </table>


                </div>
            </div>
        </div>
    </body>
</html>
