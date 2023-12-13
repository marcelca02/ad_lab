from flask import current_app as app, jsonify, request
from .models import User, Image,  db
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
                return json.dumps({'success':True}), 200, {'ContentType':'application/json'}
            else:
                return json.dumps({'success':False}), 403, {'ContentType':'application/json'}
