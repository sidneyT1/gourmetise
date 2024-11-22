<template>
  <v-container>
    <v-row>
      <v-col>
        <p>
          <strong>Titre :</strong> {{ params.title }}<br />
          <strong>Description :</strong> {{ params.description }}<br />
          <strong>Début des inscriptions :</strong> {{ params.startRegistration }}<br />
          <strong>Fin des inscriptions :</strong> {{ params.endRegistration }}<br />
          <strong>Début évaluation :</strong> {{ params.startEvaluation }}<br />
          <strong>Fin des evaluations :</strong> {{ params.endEvaluation }}<br />
        </p>
         
        
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref} from 'vue';
import axios from 'axios';


const params = ref({});


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