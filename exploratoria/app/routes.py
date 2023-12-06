from flask import Flask

def config_routes(app):
    @app.route('/')
    def index():
        return 'Hello world from routes.py!'

app = Flask(__name__)
config_routes(app)
