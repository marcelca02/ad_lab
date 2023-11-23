<%-- 
    Document   : prueba
    Created on : 13 nov. de 2023, 21:34:32
    Author     : Max Pasten
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
        <form action = "testUpload" METHOD = "POST" enctype="multipart/form-data"> 
            <label> Title:
                <input type="text" name="title" placeholder="Enter title..." required>
            </label>
            <br>
            <label> Description:
                <input type="text" name="description" placeholder="Enter description..." required>
            </label>
            <br>
            <label> Keyword:
                <input type="text" name="keyword" placeholder="Enter keyword..." required>
            </label>
            <br>
            <label> Author:
                <input type="text" name="author" placeholder="Enter author..." required>
            </label>
            <br>
            <label> Creation date:
                <input type="text" name="cr_date" placeholder="Enter creation date..." required>
            </label>
            <br>
            <label> File:
                <input type="file" name="image" required>
            </label>
            <br>
            <input type="submit" name="submit" value="Add">             
        </form>
</html>
