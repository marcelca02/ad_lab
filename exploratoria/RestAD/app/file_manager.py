import os
from .constants import *
import base64

class FileManager:
    def rename_file(self,old_filename, new_filename):
        try:
            print("Oldname: ", old_filename)
            print("Newname: ", new_filename)
            os.rename(old_filename, new_filename)
            return True
        except:
            return False

    def delete_file(self, filename):
        try:
            os.remove(filename)
            return True
        except:
            return False
    
    def create_file(self, file_path, image_data):
        try:
            with open(file_path, 'wb') as file:
                file.write(image_data)
                file.close()
            return True
        except:
            return False
    
    def get_base64(self, file_path):
        with open(file_path, 'rb') as imagen_file:
            imagen_datos = imagen_file.read()
            imagen_base64 = base64.b64encode(imagen_datos)
            imagen_base64_str = imagen_base64.decode('utf-8')
        return imagen_base64_str
