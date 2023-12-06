from app import create_app
from lib import init_lib

app = create_app()
init_lib(app)


if __name__ == '__main__':
    app.run()
