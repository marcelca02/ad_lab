<%-- 
    Document   : menu
    Created on : 4 oct 2023, 12:20:47
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
            <h1>Menu</h1>
        </div>
        <div class="content">
            <div class="sidebar">
                <ul class="menu">
                    <li><a href="#">Registrar Imagen</a></li>
                    <li><a href="#">Listar Imagenes</a></li>
                    <li><a href="#">Burcar Imagen</a></li>
                    <li><a href="#">Cerrar Sesion</a></li>
                </ul>
            </div>
            <div class="main-content">
                <h2>Recientes</h2>
                <p>Imagenes</p>
            </div>
        </div>
    </body>
</html>
