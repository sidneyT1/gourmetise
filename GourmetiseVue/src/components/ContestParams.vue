<template>
  <v-container>
    <v-row>
      <v-col>
        <p v-if="params.title">
          {{ params.title }}
          {{ params.description }}
          {{ params.startRegistration }}
          {{ params.endRegistration }}
        </p>
        <p v-else>
          Chargement des données...
        </p>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import {ref} from 'vue';
import axios from 'axios';


const params = ref('');




async function GetParams(){


  try {

    const response = await axios.get(import.meta.env.VITE_API_URL + '/api/contestParams');
    params.value = response.data; 
  } catch (error) 
  {
    console.error('Erreur lors de la récupération des params', error);
  }
};


console.log('ContestParams component mounted');
GetParams();

</script>

