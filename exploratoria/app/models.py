from . import db

class User(db.Model):
    
    __tablename__ = 'USER'

    username = db.Column(db.String(80), primary_key=True, nullable=False)
    password = db.Column(db.String(120), nullable=False)

    def __init__(self, username, password):
        self.username = username
        self.password = password

    def __repr__(self):
       return "<User(username='%s', password='%s')>" % (
                            self.username, self.password)
    
    

class Image(db.Model):

    __tablename__ = 'IMAGE'

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    name = db.Column(db.String(80), nullable=False)
    description = db.Column(db.String(255))
    keywords = db.Column(db.String(255))
    author = db.Column(db.String(80), nullable=False)
    creator = db.Column(db.String(80), db.ForeignKey('USER.username'), nullable=False)
    date_capture = db.Column(db.DateTime, nullable=False)
    date_upload = db.Column(db.DateTime, nullable=False)
    filename = db.Column(db.String(255), nullable=False)

    def __init__(self, name, description, keywords, author, creator, date_capture, date_upload, filename):
        self.name = name
        self.description = description
        self.keywords = keywords
        self.author = author
        self.creator = creator
        self.date_capture = date_capture
        self.date_upload = date_upload
        self.filename = filename

    def __repr__(self):
        return "<Image(name='%s', description='%s', keywords='%s', author='%s', creator='%s', date_capture='%s', date_upload='%s', filename='%s')>" % (
            self.name, self.description, self.keywords, self.author, self.creator, self.date_capture, self.date_upload, self.filename)
        

