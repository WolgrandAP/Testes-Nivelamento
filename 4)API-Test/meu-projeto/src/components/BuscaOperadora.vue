<template>
  <div class="container">
    <h2>Buscar Operadora</h2>
    <input v-model="registro" @input="buscarOperadora" placeholder="Digite o Registro ANS..." />
    
    <div v-if="erro" class="erro">{{ erro }}</div>

    <ul v-if="resultados.length">
      <li v-for="operadora in resultados" :key="operadora.Registro_ANS">
        {{ operadora.Nome_Fantasia }} - Registro: {{ operadora.Registro_ANS }}
      </li>
    </ul>
    <p v-else>Nenhuma operadora encontrada.</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      registro: '',
      resultados: [],
      erro: null
    };
  },
  methods: {
    async buscarOperadora() {
      if (this.registro.length > 2) {
        try {
          const response = await axios.get(`http://127.0.0.1:5000/buscar?registro=${this.registro}`);
          this.resultados = response.data;
          this.erro = null;
        } catch (error) {
          this.erro = 'Erro ao buscar operadora';
          console.error(error);
        }
      } else {
        this.resultados = [];
      }
    }
  }
};
</script>

<style scoped>
.container {
  max-width: 500px;
  margin: auto;
  text-align: center;
}
input {
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
}
.erro {
  color: red;
}
</style>
