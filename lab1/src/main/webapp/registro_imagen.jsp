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
        <title>JSP Page</title>
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
        </style>
    </head>
    <body>
        <div class="navbar">
        <h1>Registro de Imagen</h1>
        </div>
        <div class="content">
            <div class="sidebar">
                <ul class="menu">
                    <li><a href="/lab1/registro_imagen.jsp">Registrar Imagen</a></li>
                    <li><a href="#">Listar Imagenes</a></li>
                    <li><a href="#">Burcar Imagen</a></li>
                    <li><a href="#">Cerrar Sesion</a></li>
                </ul>
            </div>
            <div class="main-content">
                <h2>Registrar Imagen</h2>
                <form action = "registro_imagen" method="POST">
                    <label for="titulo">Titulo:</label>
                    <input type="text" id="title" name="title"><br><br>

                    <label for="descripcion">Descripcion:</label><br>
                    <textarea id="descripcion" name="description" rows="4" cols="50"></textarea><br><br>

                    <label for="palabras_clave">Palabras clave:</label>
                    <input type="text" id="palabras_clave" name="keywords"><br><br>

                    <label for="autor">Autor:</label>
                    <input type="text" id="autor" name="author"><br><br>

                    <label for="creador">Creador:</label>
                    <input type="text" id="creador" name="creator"><br><br>

                    <label for="fecha_captura">Fecha de captura:</label>
                    <input type="date" id="fecha_captura" name="capture_date"><br><br>

                    <label for="fecha_ingreso">Fecha de ingreso:</label>
                    <input type="date" id="fecha_ingreso" name="storage_date"><br><br>

                    <label for="nombre_imagen">Nombre de imagen:</label>
                    <input type="file" id="imagen" name="filename" accept="image/*"><br><br>

                    
                    <label for="local_date">Fecha local:</label>
                    
                    <% 
                        
                        
                        out.println("<p>El valor del atributo es: ${nombreAtributo}</p>");
                 
                    %>
                    
                    
                    <input type="submit" value="Enviar">
                    <input type="reset" value="Limpiar">
                
                
                    
                </form>
                
            </div>
        </div>
    </body>
</html>
