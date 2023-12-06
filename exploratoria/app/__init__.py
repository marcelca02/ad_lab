from flask import Flask
from .routes import config_routes

def create_app():
    app = Flask(__name__)
    config_routes(app)
    return app
