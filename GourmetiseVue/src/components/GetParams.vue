

<template>
    <div>
      
      <ul>
        <li v-for="params in params" :key="params.code">
          {{ params.nom }}
        </li>
      </ul>
  
      
    </div>
  </template>
  

<script setup>
import { ref, watch } from 'vue';
import axios from 'axios';


const params = ref([]);

const selectedCommunes = ref('');


const fetchCommunes = async () => {

  try {

    const response = await axios.get(import.meta.env.VITE_API_URL + `https://localhost:8000/api/contestParams`  );
    params.value = response.data; 
  } catch (error) {
    console.error('Erreur lors de la récupération des params', error);
  }
};

watch(searchQuery, fetchCommunes);


</script>

