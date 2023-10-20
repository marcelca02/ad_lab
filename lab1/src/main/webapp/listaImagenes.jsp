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
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                display: flex;
                height: 100vh;
                flex-direction: column;
            }

            .navbar {
                background-color: #333;
                color: #fff;
                padding: 10px;
                text-align: center;
                width: 100%;
            }

            .content {
                display: flex;
                flex-direction: row;
                flex-grow: 1;
            }

            .sidebar {
                background-color: #f4f4f4;
                width: 250px;
                box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            }

            .menu {
                list-style: none;
                padding: 0;
            }

            .menu li {
                padding: 10px;
                border-bottom: 1px solid #ccc;
            }

            .menu li a {
                text-decoration: none;
                color: #333;
            }

            .main-content {
                flex-grow: 1;
                padding: 20px;
            }
            
            
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
                    <li><a href="#">Cerrar Sesion</a></li>
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
                                    <div><span class="attribute-label">Creador:</span> ${image.author}</div>
                                    <div><span class="attribute-label">Autor:</span> ${image.creator}</div>
                                    <div><span class="attribute-label">CaptureDate:</span> ${image.captureDate}</div>
                                    <div><span class="attribute-label">StorageDate:</span> ${image.storageDate}</div>
                                    <div><span class="attribute-label">Nombre Archivo:</span> ${image.filename}</div>
                                    <div><a href="images/${image.filename}" target="_blank">Imagen completa</a></div>
                                     
                                </div>
                                
                                <c:set var="userCre" value= "${image.creator}" />
                                <% 
                                    String userCre = (String)pageContext.getAttribute("userCre");
                                    String userLog = (String)session.getAttribute("username");
                                    System.out.println("UserLog: " + userLog);
                                    System.out.println("UserCre: " + userCre);
                                    

                                    if (userCre.equals(userLog)){
                                    
                                %>    
                                

                                <div class="button-container">
                                    <form action="modificarImagen.jsp" method="post">
                                        <input type="hidden" name="imageId" value="${image.id}">
                                        <input type="hidden" name="imageCreator" value="${image.creator}">
                                        <button type="submit" name="action" value="modificar">Modificar</button>
                                    </form>
                                    <form action="eliminarImagen" method="post">
                                        <input type="hidden" name="imageId" value="${image.id}">
                                        <input type="hidden" name="imageCreator" value="${image.creator}">
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
