# syntax=docker/dockerfile:1

FROM python:3.8-slim-buster

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de la aplicación
COPY . /app

# Instalar requerimientos
RUN pip3 install --no-cache-dir -r requirements.txt

# Exponer puerto 5000
EXPOSE 5000

# Ejecutar aplicación
CMD [ "flask", "--app", "run", "run", "--host=0.0.0.0" ]

