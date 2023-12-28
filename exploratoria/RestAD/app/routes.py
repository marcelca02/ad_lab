from flask import jsonify, request, send_file 
from .db_methods import DBMethods
from .file_manager import FileManager
from .constants import *
import json 
from datetime import datetime
import base64

def config_routes(app):
    @app.route('/')
    def index():
        return "Hello World!"

    @app.route('/login', methods=['POST'])
    def login():
        print("login")
        username = request.form['username']
        password = request.form['password']
        if username and password:
            db_methods = DBMethods(app)
            if db_methods.is_logged(username, password):
                
                print("Logged")
                return json.dumps({'success':True}), 200, {'ContentType':'application/json'}
            else:
                return json.dumps({'success':False}), 403, {'ContentType':'application/json'}

    @app.route('/listImages', methods=['GET'])
    def list_images():
        db_methods = DBMethods(app)
        images = db_methods.list_images()
        image_list = []

        for image in images:
            # Ruta de la imagen que quieres convertir a base64
            file_path = IMAGE_DIR +  image.filename

            # Abre la imagen en modo de lectura en binario
            with open(file_path, 'rb') as imagen_file:
                # Lee los datos de la imagen
                imagen_datos = imagen_file.read()
                # Convierte los datos de la imagen a base64
                imagen_base64 = base64.b64encode(imagen_datos)

                # Decodifica la representación base64 a formato de cadena (si se desea)
                imagen_base64_str = imagen_base64.decode('utf-8')

                # Agrega los detalles de la imagen y la representación en base64 a la lista de imágenes
                image_list.append({
                    'id': image.id,
                    'name': image.name,
                    'description': image.description,
                    'keywords': image.keywords,
                    'author': image.author,
                    'creator': image.creator,
                    'date_capture': image.date_capture,
                    'date_upload': image.date_upload,
                    'filename': image.filename,
                    'base64': imagen_base64_str  # Agrega la representación base64 aquí
                })
        #print(image_list)
        json = jsonify(image_list)
        
        if json:
            return json, 200, {'ContentType':'application/json'}
        else:
            return json.dumps({'success':False}), 403, {'ContentType':'application/json'}

    @app.route('/createImage', methods=['POST'])
    def create_image():
        try:
            # Obtener los datos de la imagen desde la solicitud
            image_data = request.data
            
            # Obtener los siete atributos del encabezado enviado por el servlet
            name = request.headers.get('title')            
            description = request.headers.get('description')            
            keywords = request.headers.get('keywords')            
            author = request.headers.get('author')            
            creator = request.headers.get('creator')            
            filename = request.headers.get('filename')            
            date_capture = request.headers.get('f1')            
            date_upload = request.headers.get('f2')            
    
            if not date_upload or not date_capture:
                raise Exception('No se recibieron las fechas de captura y subida de la imagen')

            date_upload = datetime.strptime(date_upload, '%Y-%m-%d')
            date_capture = datetime.strptime(date_capture, '%Y-%m-%d')

            db_methods = DBMethods(app)
            if db_methods.create_image(name, description, keywords, author, creator, date_capture, date_upload, filename) and filename:
                # Guardar la imagen
                file_path = IMAGE_DIR + filename
                fm = FileManager()
                fm.create_file(file_path, image_data)
                
                return json.dumps({'success':True}), 200, {'ContentType':'application/json'}
            else:
                return json.dumps({'success':False}), 403, {'ContentType':'application/json'}
        
        except Exception as e:
            print(e)
            return json.dumps({'success':False}), 403, {'ContentType':'application/json'}

    @app.route('/modifyImage', methods=['POST'])
    def modify_image():
        id = request.form['id']
        name = request.form['name']
        description = request.form['description']
        keywords = request.form['keywords']
        author = request.form['author']
        creator = request.form['creator']
        date_capture = request.form['date_capture']
        filename = request.form['filename']
        try:
            db_methods = DBMethods(app)
            if db_methods.modify_image(id, name, description, keywords, author, creator, date_capture, filename):
                old_filename = IMAGE_DIR + db_methods.get_image_filename(id)
                filename = IMAGE_DIR + filename
                fm = FileManager()
                fm.rename_file(old_filename, filename)
                return json.dumps({'success':True}), 200, {'ContentType':'application/json'}
            else:
                return json.dumps({'success':False}), 403, {'ContentType':'application/json'}
        except Exception as e:
            print(e)
            return json.dumps({'success':False}), 403, {'ContentType':'application/json'}

    @app.route('/deleteImage', methods=['POST'])
    def delete_image():
        id = request.form['id']
        try:
            db_methods = DBMethods(app)
            if db_methods.delete_image(id):
                fm = FileManager()
                old_filename = IMAGE_DIR + db_methods.get_image_filename(id)
                fm.delete_file(old_filename)
                return json.dumps({'success':True}), 200, {'ContentType':'application/json'}
            else:
                return json.dumps({'success':False}), 403, {'ContentType':'application/json'}
        except Exception as e:
            print(e)
            return json.dumps({'success':False}), 403, {'ContentType':'application/json'}

    @app.route('/downloadImage/<filename>', methods=['GET'])
    def download_image(filename):
        try:
            db_methods = DBMethods(app)
            filename = IMAGE_DIR + filename
            return send_file(filename, mimetype='image/jpg')
        except Exception as e:
            print(e)
            return json.dumps({'success':False}), 403, {'ContentType':'application/json'}
