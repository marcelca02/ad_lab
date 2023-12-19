<%-- 
    Document   : lista
    Created on : 28 nov. de 2023, 21:04:52
    Author     : Max Pasten
--%>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Mostrar Imágenes</title>
</head>
<body>
    <% List<String> imageBase64List = (List<String>) request.getAttribute("imageBase64List");
       if (imageBase64List != null) {
           for (String base64Image : imageBase64List) { %>
               <img src="data:image/png;base64,<%= base64Image %>"><br>
           <% }
       } else { %>
           <p>No se encontraron imágenes.</p>
    <% } %>
</body>
</html>