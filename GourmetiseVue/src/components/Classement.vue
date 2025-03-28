<template>
  <div class="ranking-container">
    <h1 class="ranking-title">Classement du Concours</h1>

    <div v-if="canPublish">
      <div v-if="ranking && ranking.length > 0">
        <!-- Podium -->
        <div class="podium">
          <div class="podium-item gold">
            <div class="rank-icon">🥇</div>
            <div class="bakery-name">{{ ranking[0]?.bakery_name }}</div>  <!-- Remplacé bakeryName par name -->
            <div class="score">{{ formatScore(ranking[0]?.avg_score) }} points</div>
          </div>
          <div class="podium-item silver">
            <div class="rank-icon">🥈</div>
            <div class="bakery-name">{{ ranking[1]?.bakery_name }}</div>  <!-- Remplacé bakeryName par name -->
            <div class="score">{{ formatScore(ranking[1]?.avg_score) }} points</div>
          </div>
          <div class="podium-item bronze">
            <div class="rank-icon">🥉</div>
            <div class="bakery-name">{{ ranking[2]?.bakery_name }}</div>  <!-- Remplacé bakeryName par name -->
            <div class="score">{{ formatScore(ranking[2]?.avg_score) }} points</div>
          </div>
        </div>

        <!-- Button to publish the ranking -->
        <button @click="publishRanking" class="publish-btn">Publier le classement</button>
      </div>

      <p v-else>Aucun classement disponible pour le moment.</p>
    </div>

    <div v-else>
      <p class="warning">{{ errorMessage }}</p>
    </div>
  </div>
</template>


<script>
import { ref, onMounted } from "vue";
import axios from "axios";
import confetti from "canvas-confetti";

export default {
  name: "RankingPage",
  data() {
    return {
      ranking: null,
      canPublish: false, // Détermine si le classement peut être publié
      errorMessage: "",  // Message d'erreur à afficher si la période d'évaluation n'est pas terminée
    };
  },
  methods: {
    async loadRankingData() {
      try {
        const response = await axios.get(import.meta.env.VITE_API_URL + '/api/leaderboard');
        
        if (response.status === 200 && response.data.length > 0) {
          this.ranking = response.data;
          this.canPublish = true;
          this.errorMessage = '';
          
          // Lance l'animation des confettis lorsque les données sont disponibles
          this.launchConfetti();
        } else {
          this.ranking = [];
          this.canPublish = false;
          this.errorMessage = "La période d'évaluation n'est pas encore terminée. Vous ne pouvez pas générer le classement pour l'instant.";
        }
      } catch (error) {
        console.error("Erreur lors du chargement du classement:", error);
        this.errorMessage = "Une erreur est survenue lors de la récupération des résultats.";
        this.canPublish = false;
      }
    },

    // Publie le classement
    async publishRanking() {
      try {
        alert("Classement publié avec succès !");
      } catch (error) {
        alert("Erreur lors de la publication du classement.");
      }
    },

    // Animation des confettis
    launchConfetti() {
      confetti({
        particleCount: 400,
        angle: 90,
        spread: 300,
        origin: { x: 0.5, y: 0.5 },
      });
    },

    // Formate et arrondie le score à 2 chiffres après la virgule
    formatScore(score) {
      return parseFloat(score).toFixed(2); // Arrondir à 2 décimales
    },
  },

  mounted() {
    this.loadRankingData();
  },
};
</script>

<style scoped>
/* General Styles */
body {
  background-color: #f9f9f9;
  font-family: 'Arial', sans-serif;
}

.ranking-container {
  text-align: center;
  margin-top: 50px;
}

.ranking-title {
  font-size: 3rem;
  color: #382121;
  margin-bottom: 30px;
}

/* Podium Styles */
.podium {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 50px;
}

.podium-item {
  width: 150px;
  height: 300px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #fff;
  border-radius: 20px;
  margin: 0 20px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  padding: 20px;
  position: relative;
}

.gold {
  background-color: #ffd700;
}

.silver {
  background-color: #c0c0c0;
}

.bronze {
  background-color: #cd7f32;
}

.rank-icon {
  font-size: 3rem;
}

.bakery-name {
  font-size: 1.5rem;
  font-weight: bold;
  margin-top: 10px;
}

.score {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
  margin-top: 10px;
  border: 2px solid #333;
  border-radius: 50%;
  padding: 10px 20px;
}

/* Publish Button */
.publish-btn {
  background-color: green;
  color: white;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
  font-size: 1.2rem;
  margin-top: 30px;
  border-radius: 5px;
}

.publish-btn:hover {
  background-color: darkgreen;
}

/* Warning Message */
.warning {
  color: red;
  font-weight: bold;
  margin-top: 30px;
  font-size: 1.5rem;
}
</style>
