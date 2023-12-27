from flask import jsonify, request
from .db_methods import DBMethods
from .file_manager import rename_file
import json 
import os
from datetime import datetime

def config_routes(app):
    @app.route('/')
    def index():
        return 'Hello world from routes.py!'

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
        image_list = [
            {
                'id': image.id,
                'name': image.name,
                'description': image.description,
                'keywords': image.keywords,
                'author': image.author,
                'creator': image.creator,
                'date_capture': image.date_capture,
                'date_upload': image.date_upload,
                'filename': image.filename
            } for image in images
        ]
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

            date_upload = datetime.strptime(date_upload, '%Y-%m-%d')
            date_capture = datetime.strptime(date_capture, '%Y-%m-%d')

            db_methods = DBMethods(app)
            if db_methods.create_image(name, description, keywords, author, creator, date_capture, date_upload, filename):
                
                print("Entra")
                # Guardar la imagen
                upload_folder = 'C:/Users/Max Pasten/lab1/python/' 
                file_path = os.path.join(upload_folder, filename)
                with open(file_path, 'wb') as file:
                    file.write(image_data)
                
                print("Image saved at:", file_path)
                
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
                old_filename = db_methods.get_image_filename(id)
                rename_file(old_filename,filename) 
                return json.dumps({'success':True}), 200, {'ContentType':'application/json'}
            else:
                return json.dumps({'success':False}), 403, {'ContentType':'application/json'}
        except Exception:
            return json.dumps({'success':False}), 403, {'ContentType':'application/json'}

    @app.route('/deleteImage', methods=['POST'])
    def delete_image():
        id = request.form['id']
        try:
            db_methods = DBMethods(app)
            if db_methods.delete_image(id):
                return json.dumps({'success':True}), 200, {'ContentType':'application/json'}
            else:
                return json.dumps({'success':False}), 403, {'ContentType':'application/json'}
        except Exception as e:
            return json.dumps({'success':False}), 403, {'ContentType':'application/json'}

