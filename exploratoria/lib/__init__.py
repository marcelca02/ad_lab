from .db_connection import DBConnection
from .models import User, Image

def init_lib(app):
    db_connection = DBConnection(app)
    db_connection.create_tables()
