<template>
    <div class="personal-score-container">
      <h1>Mon Score Personnel</h1>
  
      <div class="personalScore">
        <h2>{{ personalScore.bakery_name }}</h2>
        <p>Score : {{ formatScore(personalScore.avg_score) }} points</p>
        <p>Rang : {{ personalScore.rank }}</p>
      </div>
  
      <p v-if="!personalScore" class="warning">
        Aucun score trouvé pour votre boulangerie.
      </p>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from "vue";
  import axios from "axios";
  
  export default {
    name: "PersonalScore",
    data() {
      return {
        personalScore: null,
        bakeryId: this.$route.params.bakeryId, 
      };
    },
    methods: {
      async loadPersonalScore() {
        try {
          const response = await axios.get(`${import.meta.env.VITE_API_URL}/api/bakery/${this.bakeryId}/score`);
          if (response.status === 200) {
            this.personalScore = response.data;
          } else {
            this.personalScore = null;
          }
        } catch (error) {
          console.error("Erreur lors du chargement du score personnel:", error);
          this.personalScore = null;
        }
      },
  
      formatScore(score) {
        return parseFloat(score).toFixed(2);
      }
    },
  
    mounted() {
      if (this.bakeryId) {
        this.loadPersonalScore();
      }
    },
  };
  </script>
  