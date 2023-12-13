from flask import Flask
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

def init_app():
    app = Flask(__name__)
    app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///pr2.db'
    db.init_app(app)

    with app.app_context():
        from . import routes
        routes.config_routes(app)
        db.create_all()
        return app
