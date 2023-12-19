from flask import jsonify, request
from .db_methods import DBMethods
import json 

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

    @app.route('/list', methods=['GET'])
    def list():
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
        print("Create image")

        name = request.form['name']
        description = request.form['description']
        keywords = request.form['keywords']
        author = request.form['author']
        creator = request.form['creator']
        date_capture = request.form['date_capture']
        date_upload = request.form['date_upload']
        filename = request.form['filename']

        try :
            db_methods = DBMethods(app)
            if db_methods.create_image(name, description, keywords, author, creator, date_capture, date_upload, filename):
                return json.dumps({'success':True}), 200, {'ContentType':'application/json'}
            else:
                return json.dumps({'success':False}), 403, {'ContentType':'application/json'}
        except Exception as e:
            print(e)
            return json.dumps({'success':False}), 403, {'ContentType':'application/json'}
        
    @app.route('/listImages', methods=['GET'])
    def listImages():
        print("List images")
        try:
            db_methods = DBMethods(app)
            images = db_methods.list_images()
            print("IMAGES:")
            print(images)

        except Exception as e:
            print(e)
            return json.dumps({'success':False}), 403, {'ContentType':'application/json'}
        pass

    @app.route('/modifyImage', methods=['POST'])
    def modifyImage():
        pass
