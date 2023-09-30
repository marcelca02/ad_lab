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
        <title>JSP Page</title>
    </head>
    <body>
        <div>
            <h1>Login Page</h1>
            <div>
                <form action="login" method="post">
                    User: <input name="user" placeholder="user"></input>
                    <br>
                    <br>
                    Password: <input name="password" placeholder="password" type="password"></input>
                    <button type="submit"></button>
                </form>                  
            </div>
        </div>
    </body>
</html>
