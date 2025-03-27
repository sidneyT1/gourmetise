<template>
    <div>
      <h1>Classement du Concours</h1>
  
  
      <div v-if="canPublish">
        <div v-if="ranking">
          <h2>Classement actuel</h2>
          <ul>
            <li v-for="(entry, index) in ranking" :key="index">
              {{ index + 1 }}. {{ entry.bakeryName }} - {{ entry.score }} points
            </li>
          </ul>
  
          <button @click="publishRanking" class="publish-btn">Publier le classement</button>
        </div>
        <p v-else>Aucun classement disponible pour le moment.</p>
      </div>
  
      <div v-else>
        <p class="warning">La période d'évaluation n'est pas encore terminée. Vous ne pouvez pas générer le classement pour l'instant.</p>
      </div>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from "vue";
  
  
  export default {
    name: "RankingPage",
    data() {
      return {
        ranking: null, 
        canPublish: false,  
        contestEndDate: "",  
      };
    },
    methods: {
      async loadRankingData() {
        try {
          const response = await RankingService.getRanking(); // Service pour récupérer le classement
          this.ranking = response.data;
        } catch (error) {
          console.error("Erreur lors du chargement du classement:", error);
        }
      },
  
      async checkCanPublish() {
        try {
          const response = await RankingService.getContestParams(); // Service pour récupérer la date de fin
          this.contestEndDate = response.data.endDate;  // Récupère la date de fin des évaluations
  
          const currentDate = new Date();
          const endDate = new Date(this.contestEndDate);
  
          // Si la date actuelle est après la date de fin, on permet la publication
          this.canPublish = currentDate > endDate;
        } catch (error) {
          console.error("Erreur lors de la vérification de la date de fin:", error);
        }
      },
  
      async publishRanking() {
        try {
          await RankingService.publishRanking(this.ranking);
          alert("Classement publié avec succès !");
        } catch (error) {
          alert("Erreur lors de la publication du classement.");
        }
      },
    },
    mounted() {
      this.loadRankingData();  // Charge les données du classement
      this.checkCanPublish();  // Vérifie si on peut publier
    },
  };
  </script>
  
  <style scoped>
  .warning {
    color: red;
    font-weight: bold;
  }
  
  .publish-btn {
    background-color: green;
    color: white;
    padding: 10px;
    border: none;
    cursor: pointer;
  }
  
  .publish-btn:hover {
    background-color: darkgreen;
  }
  </style>
  