<%-- 
    Document   : menu
    Created on : 4 oct 2023, 12:20:47
    Author     : Max Pasten
--%>

<%@ page import="utils.Image" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        </style>
    </head>
    <body>
        <div class="navbar">
            <%= session.getAttribute("username") %>
            <h1>Menu</h1>
        </div>
        <div class="content">
            <div class="sidebar">
                <ul class="menu">
                    <li><a href="/ClientOtroGrupo/registro_imagen.jsp">Registrar Imagen</a></li>
                    <li><a href="/ClientOtroGrupo/listaImagenes.jsp">Listar Imagenes</a></li>
                    <li><a href="/ClientOtroGrupo/buscaImagen.jsp">Buscar Imagen</a></li>
                    <li><a href="/ClientOtroGrupo/cerrarSesion">Cerrar Sesion</a></li>
                </ul>
            </div>
            <div class="main-content">
                <h2>Recientes</h2>
                <div align="center">
                    
                    <%
                        request.getRequestDispatcher("/imagenesRecientes").include(request, response);
                        List<Image> images = (List<Image>)request.getAttribute("images");
                    %>

                    <table border="1">
                         <c:forEach var="image" items="${images}">
                            <div class="image-container">
                                <img src="images/${image.filename}" alt="Imagen" />
                                <div class="image-attributes">
                                    <div><span class="attribute-label">ID:</span> ${image.id}</div>
                                    <div><span class="attribute-label">Título:</span> ${image.title}</div>
                                    <div><span class="attribute-label">Descripción:</span> ${image.description}</div>
                                    <div><span class="attribute-label">Palabras Clave:</span>
                                        <c:set var="myArray" value="${image.keywords}" />
                                        <c:forEach var="elemento" items="${myArray}" varStatus="status">
                                            ${elemento}<c:if test="${!status.last}">,</c:if>
                                        </c:forEach>
                                    </div>
                                    <div><span class="attribute-label">Creador:</span> ${image.author}</div>
                                    <div><span class="attribute-label">Autor:</span> ${image.creator}</div>
                                    <div><span class="attribute-label">Fecha Captura:</span> ${image.captureDate}</div>
                                    <div><span class="attribute-label">Fecha Registro:</span> ${image.storageDate}</div>
                                    <div><span class="attribute-label">Nombre Archivo:</span> ${image.filename}</div>
                                    <div><a href="images/${image.filename}" target="_blank">Imagen completa</a></div>
                                </div>
                            </div>
                        </c:forEach>
                    </table>


                </div>
                
            </div>
        </div>
    </body>
</html>
