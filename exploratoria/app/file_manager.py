import os

def rename_file(old_filename, new_filename):
    try:
        os.rename(old_filename, new_filename)
        return True
    except:
        return False

