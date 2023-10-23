<%-- 
    Document   : login
    Created on : 21 sept 2023, 13:03:04
    Author     : marcel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="css/general.css"/>
    </head>
    <body>
        <div class="navbar">
            <% 
        if (session.getAttribute("username") != null) {
            response.sendRedirect("/lab1/menu.jsp");
        }
            %>
            <h1>Login</h1>
        </div>
        <div class="content">
            <div class="sidebar">
                <ul class="menu">
                    <li><a type="hidden" href="/lab1/cerrarSesion">Cerrar Sesion</a></li>
                </ul>
            </div>
            <div class="main-content">
                <form action="login" method="post">
                    User: <input name="username" placeholder="user"><br><br></input>
                    Password: <input name="password" placeholder="password" type="password"><br><br></input>
                    <input type="submit" value="Enviar"></input>
                </form>                  
            </div>
        </div>
    </body>
</html>
