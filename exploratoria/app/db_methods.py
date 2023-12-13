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
        user = User.query.filter_by(username=username).first()
        if user and user.password == password:
            return True
        else:
            return False
