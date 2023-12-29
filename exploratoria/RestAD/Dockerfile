# syntax=docker/dockerfile:1

FROM python:3.8-slim-buster

# Establecer directorio de trabajo
WORKDIR /restad-docker

# Copiar archivos de requerimientos
COPY requirements.txt requirements.txt

# Instalar requerimientos
RUN pip3 install --no-cache-dir -r requirements.txt

# Copiar archivos de la aplicación
COPY . /restad-docker

# Exponer puerto 5000
EXPOSE 5000

# Instalar SQLite en el contenedor
RUN apt-get update && \
    apt-get install -y sqlite

# Ejecutar aplicación
CMD [ "flask", "--app", "run", "run", "--host=0.0.0.0" ]

