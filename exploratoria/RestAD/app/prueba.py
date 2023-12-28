from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/datos', methods=['GET'])
def obtener_datos():
    datos = {
        'id': 1,
        'nombre': 'Ejemplo',
        'descripcion': 'Esto es un ejemplo de datos'
    }
    return jsonify(datos)

if __name__ == '__main__':
    app.run(debug=True)
    