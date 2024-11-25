<template>
  <v-container>
    <v-row>
      <v-col>
        <p>
          <strong>Titre :</strong> {{ params.title }}<br />
          <strong>Description :</strong> {{ params.description }}<br />
          <strong>Début des inscriptions :</strong> {{ formatDate(params.startRegistration) }}<br />
          <strong>Fin des inscriptions :</strong> {{ formatDate(params.endRegistration) }}<br />
          <strong>Début évaluation :</strong> {{ formatDate(params.startEvaluation) }}<br />
          <strong>Fin des évaluations :</strong> {{ formatDate(params.endEvaluation) }}<br />
        </p>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const params = ref({});


function formatDate(date) {
  if (!date) return ''; 
  const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit' };
  return new Intl.DateTimeFormat('fr-FR', options).format(new Date(date));
}

async function GetParams() {
  try {
    const response = await axios.get(import.meta.env.VITE_API_URL + '/api/contestParams');
    params.value = response.data;
  } catch (error) {
    console.error('Erreur lors de la récupération des paramètres:', error);
  }
};

GetParams();
</script>
