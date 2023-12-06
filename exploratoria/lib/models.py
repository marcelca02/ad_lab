from .db_connection import db

class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(80), unique=True, nullable=False)
    password = db.Column(db.String(120), nullable=False)

class Image(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(80), nullable=False)
    description = db.Column(db.String(255))
    keywords = db.Column(db.String(255))
    author = db.Column(db.String(80), nullable=False)
    creator = db.Column(db.String(80), nullable=False)
    date_capture = db.Column(db.DateTime, nullable=False)
    date_upload = db.Column(db.DateTime, nullable=False)
    filename = db.Column(db.String(255), nullable=False)
