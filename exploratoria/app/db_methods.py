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
            print("User logged")
            return user
        else:
            return False
    
    def list_images(self):
        images = Image.query.all()
        return images
        

    def create_image (self, name, description, keywords, author, creator, date_capture, date_upload, filename):
        image = Image(name, description, keywords, author, creator, date_capture, date_upload, filename)
        db.session.add(image)
        db.session.commit()
        return True

    def modify_image(self, id, name, description, keywords, author, creator, date_capture, filename):
        image = Image.query.filter_by(id=id).first()
        if image:
            image.name = name
            image.description = description
            image.keywords = keywords
            image.author = author
            image.creator = creator
            image.date_capture = date_capture
            image.filename = filename
            db.session.commit()
            return True
        else:
            return False

    def delete_image (self, id):
        deleted = Image.query.filter_by(id=id).delete()
        db.session.commit()
        return deleted

