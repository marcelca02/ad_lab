<%-- 
    Document   : error
    Created on : 30 sept 2023, 19:18:39
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
        <h1>¡Ha ocurrido un error!</h1>
      <%
      String referer = request.getHeader("Referer");
      if (referer != null && !referer.isEmpty()){
      %>
        <a href="<% out.write(referer); %>">Volver atrás</a>
      <%
      }else {
      %>
        <a href="/">Ir a la página de inicio</a>
      <%
      }
      %>
    </body>
</html>
