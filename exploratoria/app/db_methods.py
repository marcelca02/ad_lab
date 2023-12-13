from . import db
from .models import User, Image

class DBMethods:
    def __init__(self, app):
        self.app = app

    def create_tables(self):
        with self.app.app_context():
            db.create_all()

    def drop_tables(self):
        with self.app.app_context():
            db.drop_all()

    def is_logged(self, username, password):        
    
        # Buscar el usuario en la base de datos
        user = User.query.filter_by(username=username, password=password).first()

        # Si se encontró un usuario con esa combinación de usuario y contraseña, retorna True
        return user is not None

    def create_image():
        pass
