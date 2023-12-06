from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

class DBConnection:
    def __init__(self, app):
        self.app = app
        self.init_app()

    def init_app(self):
        self.app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///pr2.db'
        self.app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
        db.init_app(self.app)

    def create_tables(self):
        with self.app.app_context():
            db.create_all()
