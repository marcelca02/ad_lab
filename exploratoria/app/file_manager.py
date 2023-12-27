import os
from .constants import *

class FileManager:
    def rename_file(self,old_filename, new_filename):
        try:
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

