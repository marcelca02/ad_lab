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

    def create_image (self, name, description, keywords, author, creator, date_capture, date_upload, filename):
        image = Image(name, description, keywords, author, creator, date_capture, date_upload, filename)
        db.session.add(image)
        db.session.commit()

        return True

    def list_images ():
        images = Image.query.all()
        print(images)        
        return images

    def modify_image ():
        pass

    def delete_image ():
        pass
    
    

