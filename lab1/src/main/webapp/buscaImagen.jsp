<%-- 
    Document   : buscaImagen
    Created on : 18 oct. de 2023, 10:11:32
    Author     : Max Pasten
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="classes.Image" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Busqueda</title>
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
            <h1>Busqueda</h1>
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
                
                <h2>Busqueda por fechas</h2>
                
                <form action="buscaImagen" method="post">
                    
                    <label for="fecha_inicio">Fecha de Inicio:</label>
                    <input type="date" id="fecha_inicio" name="fecha_inicio" required><br><br>

                    <label for="fecha_fin">Fecha de Fin:</label>
                    <input type="date" id="fecha_fin" name="fecha_fin" required><br><br>

                    <input type="submit" value="Buscar">
                </form>
                
                
                
                <div align="center">

                    <%    
                        List<Image> images = (List<Image>)request.getAttribute("images");
                        
                        if (images != null && !images.isEmpty()) {
                            
                    %>
                    <h3>Imagenes Encontradas</h3>
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
                                <c:set var="imagen" value= "${image.creator}" />
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
                    <%
                        } else {
                    %>
                    
                    <div style="text-align: center; margin-top: 50vh; transform: translateY(-50%);">
                        <h1>No se encontro ninguna imagen</h1>
                    </div>
                    
                    <%
                        }
                    %>

                </div>
                
            </div>
        </div>
    </body>
</html>

