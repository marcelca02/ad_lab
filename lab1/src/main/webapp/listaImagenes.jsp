<%-- 
    Document   : listaImagenes
    Created on : 14 oct. de 2023, 12:40:57
    Author     : Max Pasten
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import= "java.sql.Connection" %>

<%@ page import= "java.sql.DriverManager" %>
<%@ page import= "java.sql.PreparedStatement" %>
<%@ page import= "java.sql.ResultSet" %>
<%@ page import= "java.sql.SQLException" %>

<%@ page import="java.io.File" %>
<%@ page import="java.util.Arrays" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Display Image</title>
</head>
<body>
<h1 style="color:red" align="center">DISPLAY IMAGE DETAIL</h1>

<div align="center">

<form action="lista_imagenes" method="post">
   Enter Image Id :
   <input type="number" name="imageId">
   <input type="submit" value="Display Image">
</form>

</div>

<hr>

<%
    String imgFileName=(String)request.getAttribute("img");
    String imgId=(String)request.getAttribute("id");
    System.out.println(imgFileName);
    System.out.println("asdfsdfsdf");
%>

<div align="center">
     <table border="5px" style="width:600px">
          <tr>
              <th>Image Id </th>
              <th>Image</th>
          </tr>
         
         <%
             
             if(imgFileName!="" && imgId!="")
             {
         %>
          
          <tr>
              <td><%=imgId %></td>
              <td><img src="images/<%=imgFileName%>" style="width:300px"></td>
          </tr>
        <%
             }
        %>  
     </table>
     
     <c:forEach var="img" items="${data}">
         
         
         
     </c:forEach>
     
     
</div>

</body>
</html>