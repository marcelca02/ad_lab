from flask import jsonify, request
from .db_methods import DBMethods
import json 

def config_routes(app):
    @app.route('/')
    def index():
        return 'Hello world from routes.py!'

    @app.route('/login', methods=['POST'])
    def login():
        username = request.form['username']
        password = request.form['password']
        if username and password:
            db_methods = DBMethods(app)
            if db_methods.is_logged(username, password):
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
        return jsonify(image_list)
