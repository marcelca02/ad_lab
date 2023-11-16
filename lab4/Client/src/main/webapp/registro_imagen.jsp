<%-- 
    Document   : registro_imagen
    Created on : 4 oct 2023, 19:47:25
    Author     : Max Pasten
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro de Imagen</title>
        <link rel="stylesheet" type="text/css" href="css/general.css"/>
    </head>
    <body>
        <div class="navbar">
            <%= session.getAttribute("username") %>
            <%
                if (session.getAttribute("username") == null) {
                    response.sendRedirect("/Client/login.jsp");
                }
            %>
            <h1>Registro de Imagen</h1>
        </div>
        <div class="content">
            <div class="sidebar">
                <ul class="menu">
                    <li><a href="/Client/registro_imagen.jsp">Registrar Imagen</a></li>
                    <li><a href="/Client/listaImagenes.jsp">Listar Imágenes</a></li>
                    <li><a href="/Client/buscaImagen.jsp">Buscar Imagen</a></li>
                    <li><a href="/Client/cerrarSesion">Cerrar Sesión</a></li>
                </ul>
            </div>
            
            <%
                // Fecha Actual
                Date todayDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaActual = sdf.format(todayDate);
                
                System.out.println("Fecha actual: " + fechaActual);
                request.setAttribute("fechaActual", fechaActual);
                
                String creator = (String)session.getAttribute("username");
                request.setAttribute("creator", creator);

            %>    
            
            <div class="main-content">
                <h2>Registrar Imagen</h2>
                <form action = "registroImagen" method="POST" enctype="multipart/form-data" onsubmit="return previewImage();>
                    <label for="titulo">Título:</label>
                    <input type="text" id="title" name="title" required><br><br>

                    <label for="descripcion">Descripción:</label><br>
                    <textarea id="descripcion" name="description" rows="4" cols="50"  required></textarea><br><br>

                    <label for="palabras_clave">Palabras Clave:</label>
                    <input type="text" id="palabras_clave" name="keywords" required><br><br>

                    <label for="autor">Autor:</label>
                    <input type="text" id="autor" name="author" required><br><br>

                    
                    <label for="creador">Creador:</label>
                    <input type="text" id="creador" name="creator" value="${creator}" readonly><br><br>

                    <label for="fecha_captura">Fecha Captura:</label>
                    <input type="date" id="fecha_captura" name="capture_date" required><br><br>

                    <label for="fecha_ingreso">Fecha Ingreso:</label>
                    <input type="date" id="fecha_ingreso" name="storage_date" value="${fechaActual}" readonly><br><br>

                    <label for="nombre_imagen">Nombre Archivo:</label>
                    <input type="file" id="imagen" name="filename" accept="image/*" onchange="previewImage(event)" required>
                    
                    <br>
                    <br>
                    <input type="submit" value="Enviar">
                    <input type="reset" value="Limpiar">
                    
                    <div id="imagePreview" style="display:none;">
                        <h4>Imagen:</h4>
                        <div align="center">
                            <img id="preview" src="" alt="Image Preview" style="max-height: 300px;">
                        </div>
                       
                    </div>
                </form>
                
            </div>
        </div>
        <script>
            function previewImage(event) {
                var input = event.target;
                var preview = document.getElementById('preview');
                var previewContainer = document.getElementById('imagePreview');

                var file = input.files[0];
                var reader = new FileReader();

                reader.onload = function() {
                    preview.src = reader.result;
                    previewContainer.style.display = 'block';
                }

                if (file) {
                    reader.readAsDataURL(file);
                }
            }
        </script>            
    </body>
</html>
