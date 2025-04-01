from flask import Flask, request, jsonify
import pandas as pd

app = Flask(__name__)

# Carregar o CSV na inicialização
def carregar_dados():
    dados = pd.read_csv("Relatorio_cadop.csv", encoding="utf-8", delimiter=";", dtype=str)
    return dados

info = carregar_dados()

@app.route("/buscar", methods=["GET"])
def buscar_operadora():
    registro = request.args.get("registro", "").strip()

    if not registro:
        return jsonify({"erro": "Parâmetro 'registro' é obrigatório"}), 400
    
    resultado = info[info["Registro_ANS"].astype(str) == registro]
    
    return jsonify(resultado.to_dict(orient="records"))


if __name__ == "__main__":
    app.run(debug=True)



