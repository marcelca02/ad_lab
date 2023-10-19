<%-- 
    Document   : registro_imagen
    Created on : 4 oct 2023, 19:47:25
    Author     : Max Pasten
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                    response.sendRedirect("/lab1/login.jsp");
                }
            %>
            <h1>Registro de Imagen</h1>
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
                <h2>Registrar Imagen</h2>
                <form action = "registro_imagen" method="POST" enctype="multipart/form-data" onsubmit="return previewImage();>
                    <label for="titulo">Titulo:</label>
                    <input type="text" id="title" name="title" required><br><br>

                    <label for="descripcion">Descripcion:</label><br>
                    <textarea id="descripcion" name="description" rows="4" cols="50"  required></textarea><br><br>

                    <label for="palabras_clave">Palabras clave:</label>
                    <input type="text" id="palabras_clave" name="keywords" required><br><br>

                    <label for="autor">Autor:</label>
                    <input type="text" id="autor" name="author" required><br><br>

                    <label for="creador">Creador:</label>
                    <input type="text" id="creador" name="creator"><br><br>

                    <label for="fecha_captura">Fecha de captura:</label>
                    <input type="date" id="fecha_captura" name="capture_date"><br><br>

                    <label for="fecha_ingreso">Fecha de ingreso:</label>
                    <input type="date" id="fecha_ingreso" name="storage_date"><br><br>

                    <label for="nombre_imagen">Nombre de imagen:</label>
                    <input type="file" id="imagen" name="filename" accept="image/*" onchange="previewImage(event)">

                    
                    
                    <% 
                        
                        
                        out.println("");
                 
                    %>
                    
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
