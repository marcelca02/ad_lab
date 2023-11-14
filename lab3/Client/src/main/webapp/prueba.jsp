<%-- 
    Document   : prueba
    Created on : 13 nov. de 2023, 21:34:32
    Author     : Max Pasten
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buscador de Imágenes</title>
    <style>
        .formulario {
            display: none;
        }

        .formulario:target {
            display: block;
        }
    </style>
</head>
<body>

    <h1>Buscador de Imágenes</h1>

    <label for="opciones">Selecciona una opción:</label>
    <select id="opciones">
        <option value="#busquedaNombre">Buscar por Nombre</option>
        <option value="#busquedaAutor">Buscar por Autor</option>
        <option value="#busquedaFecha">Buscar por Fecha</option>
    </select>

    <div id="busquedaNombre" class="formulario">
        <h2>Buscar por Nombre</h2>
        <!-- Formulario para búsqueda por nombre -->
        <form>
            <label for="nombreImagen">Nombre de la Imagen:</label>
            <input type="text" id="nombreImagen" name="nombreImagen">
            <input type="submit" value="Buscar">
        </form>
    </div>

    <div id="busquedaAutor" class="formulario">
        <h2>Buscar por Autor</h2>
        <!-- Formulario para búsqueda por autor -->
        <form>
            <label for="autorImagen">Autor de la Imagen:</label>
            <input type="text" id="autorImagen" name="autorImagen">
            <input type="submit" value="Buscar">
        </form>
    </div>

    <div id="busquedaFecha" class="formulario">
        <h2>Buscar por Fecha</h2>
        <!-- Formulario para búsqueda por fecha -->
        <form>
            <label for="fechaImagen">Fecha de la Imagen:</label>
            <input type="date" id="fechaImagen" name="fechaImagen">
            <input type="submit" value="Buscar">
        </form>
    </div>

</body>
</html>
