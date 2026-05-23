from flask import Flask, jsonify, request
import mysql.connector

app = Flask(__name__)

DB_CONFIG = {
    "host": "localhost",
    "port": 3806,
    "user": "root",
    "password": "eneas2805",
    "database": "basicosd"
}

@app.route("/api/health", methods=["GET"])
def health():
    return jsonify({
        "ok": True,
        "message": "API Flask funcionando correctamente"
    })

@app.route("/api/tests/file", methods=["GET"])
def file_test():
    mode = request.args.get("mode", "ok")

    if mode == "ok":
        return jsonify({
            "ok": True,
            "test": "file",
            "message": "Lectura de archivo correcta"
        })

    if mode == "missing":
        return jsonify({
            "ok": False,
            "errorType": "FILE_NOT_FOUND",
            "userMessage": "No se ha encontrado el archivo solicitado.",
            "technicalMessage": "FileNotFoundError simulado",
            "critical": False
        }), 404

    if mode == "read-error":
        return jsonify({
            "ok": False,
            "errorType": "FILE_READ_ERROR",
            "userMessage": "No se ha podido leer el archivo.",
            "technicalMessage": "Error de lectura simulado",
            "critical": False
        }), 500

    return jsonify({
        "ok": False,
        "errorType": "BAD_REQUEST",
        "userMessage": "Modo de prueba no válido.",
        "technicalMessage": f"Modo recibido: {mode}",
        "critical": False
    }), 400


@app.route("/api/tests/db", methods=["GET"])
def db_test():
    mode = request.args.get("mode", "ok")

    if mode == "ok":
        try:
            conn = mysql.connector.connect(**DB_CONFIG)
            cursor = conn.cursor()
            cursor.execute("SELECT COUNT(*) FROM user")
            total = cursor.fetchone()[0]
            cursor.close()
            conn.close()

            return jsonify({
                "ok": True,
                "test": "db",
                "message": f"Consulta correcta. Total de usuarios: {total}"
            })
        except Exception as e:
            return jsonify({
                "ok": False,
                "errorType": "DB_ERROR",
                "userMessage": "No se pudo realizar la consulta a la base de datos.",
                "technicalMessage": str(e),
                "critical": False
            }), 500

    if mode == "connection-error":
        try:
            bad_config = DB_CONFIG.copy()
            bad_config["port"] = 9999
            mysql.connector.connect(**bad_config)

            return jsonify({
                "ok": True,
                "message": "Conexión inesperadamente correcta"
            })
        except Exception as e:
            return jsonify({
                "ok": False,
                "errorType": "DB_CONNECTION_ERROR",
                "userMessage": "No se puede conectar con la base de datos.",
                "technicalMessage": str(e),
                "critical": False
            }), 500

    if mode == "bad-query":
        try:
            conn = mysql.connector.connect(**DB_CONFIG)
            cursor = conn.cursor()
            cursor.execute("SELECT * FROM tabla_que_no_existe")
            rows = cursor.fetchall()
            cursor.close()
            conn.close()

            return jsonify({
                "ok": True,
                "message": rows
            })
        except Exception as e:
            return jsonify({
                "ok": False,
                "errorType": "DB_BAD_QUERY",
                "userMessage": "La consulta SQL no es válida.",
                "technicalMessage": str(e),
                "critical": False
            }), 500

    return jsonify({
        "ok": False,
        "errorType": "BAD_REQUEST",
        "userMessage": "Modo de prueba no válido.",
        "technicalMessage": f"Modo recibido: {mode}",
        "critical": False
    }), 400

@app.route("/api/tests/pokemon", methods=["GET"])
def pokemon_test():
    mode = request.args.get("mode", "ok")
    name = request.args.get("name", "pikachu")

    if mode == "ok":
        try:
            import requests
            url = f"https://pokeapi.co/api/v2/pokemon/{name.lower()}"
            response = requests.get(url, timeout=5)

            if response.status_code == 200:
                data = response.json()
                return jsonify({
                    "ok": True,
                    "test": "pokemon",
                    "message": f"Pokémon encontrado correctamente: {data['name']}"
                })

            return jsonify({
                "ok": False,
                "errorType": "POKEMON_NOT_FOUND",
                "userMessage": "No se ha encontrado el Pokémon solicitado.",
                "technicalMessage": f"Status code: {response.status_code}",
                "critical": False
            }), 404

        except Exception as e:
            return jsonify({
                "ok": False,
                "errorType": "POKEMON_API_ERROR",
                "userMessage": "Error al consultar la API de Pokémon.",
                "technicalMessage": str(e),
                "critical": False
            }), 500

    if mode == "not-found":
        try:
            import requests
            url = "https://pokeapi.co/api/v2/pokemon/este-pokemon-no-existe-123456"
            response = requests.get(url, timeout=5)

            return jsonify({
                "ok": False,
                "errorType": "POKEMON_NOT_FOUND",
                "userMessage": "No se ha encontrado el Pokémon solicitado.",
                "technicalMessage": f"Status code: {response.status_code}",
                "critical": False
            }), 404

        except Exception as e:
            return jsonify({
                "ok": False,
                "errorType": "POKEMON_API_ERROR",
                "userMessage": "Error al consultar la API de Pokémon.",
                "technicalMessage": str(e),
                "critical": False
            }), 500

    if mode == "timeout":
        return jsonify({
            "ok": False,
            "errorType": "POKEMON_TIMEOUT",
            "userMessage": "La API externa ha tardado demasiado en responder.",
            "technicalMessage": "Timeout simulado en llamada a la API de Pokémon",
            "critical": False
        }), 500

    return jsonify({
        "ok": False,
        "errorType": "BAD_REQUEST",
        "userMessage": "Modo de prueba no válido.",
        "technicalMessage": f"Modo recibido: {mode}",
        "critical": False
    }), 400
    
    
if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)