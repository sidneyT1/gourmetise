<template>
  <div class="ranking-container">
    <h1 class="ranking-title">Classement du Concours</h1>

    <div v-if="ranking && ranking.length > 0">
      <!-- Podium: Les 3 premières boulangeries -->
      <div class="podium">
        <div class="podium-item gold">
          <div class="rank-icon">🥇</div>
          <div class="bakery-name">{{ ranking[0]?.bakery_name }}</div>
          <div class="score">{{ formatScore(ranking[0]?.avg_score) }} points</div>
        </div>
        <div class="podium-item silver">
          <div class="rank-icon">🥈</div>
          <div class="bakery-name">{{ ranking[1]?.bakery_name }}</div>
          <div class="score">{{ formatScore(ranking[1]?.avg_score) }} points</div>
        </div>
        <div class="podium-item bronze">
          <div class="rank-icon">🥉</div>
          <div class="bakery-name">{{ ranking[2]?.bakery_name }}</div>
          <div class="score">{{ formatScore(ranking[2]?.avg_score) }} points</div>
        </div>
      </div>

      <p v-if="userRole === 'Gérant'" class="admin-note">
        Vous consultez le classement complet en tant que Gérant.
      </p>

      <p v-if="!ranking || ranking.length === 0" class="warning">
        {{ errorMessage }}
      </p>
    </div>

    <button
      v-if="userRole === 'Gérant' && isUserConnected && !isPublished"
      @click="publishRanking"
      class="publish-btn"
    >
      Publier le classement
    </button>
  </div>
</template>

<script>
import axios from "axios";
import confetti from "canvas-confetti";

export default {
  name: "RankingPage",
  data() {
    return {
      ranking: null,
      canPublish: false,
      errorMessage: "",
      isPublished: false,
      userRole: null, 
      isUserConnected: !!localStorage.getItem("access_token"),
    };
  },
  methods: {
    async loadRankingData() {
      try {
        const response = await axios.get(import.meta.env.VITE_API_URL + "/api/leaderboard");

        if (response.status === 200 && response.data.length > 0) {
          this.ranking = response.data;
          this.canPublish = true;
          this.errorMessage = "";
          this.launchConfetti();
        } else {
          this.ranking = [];
          this.canPublish = false;
          this.errorMessage = "La période d'évaluation n'est pas encore terminée.";
        }

        const paramsResponse = await axios.get(import.meta.env.VITE_API_URL + "/api/contestParams");
        if (paramsResponse.status === 200) {
          this.isPublished = paramsResponse.data.isPublished;
        }

      } catch (error) {
        console.error("Erreur lors du chargement du classement:", error);
        if (error.response && error.response.status === 400) {
          this.errorMessage = "La période d'évaluation n'est pas encore terminée.";
        } else {
          this.errorMessage = "Une erreur est survenue lors de la récupération des résultats.";
        }
        this.ranking = [];
        this.canPublish = false;
      }
    },

    async publishRanking() {
      try {
        const response = await axios.put(import.meta.env.VITE_API_URL + "/api/contestParams", {
          isPublished: true,
        });

        if (response.status === 200) {
          this.isPublished = true;
          alert("Classement publié avec succès !");
        }
      } catch (error) {
        alert("Erreur lors de la publication du classement.");
        console.error(error);
      }
    },

    launchConfetti() {
      confetti({
        particleCount: 400,
        angle: 90,
        spread: 300,
        origin: { x: 0.5, y: 0.5 },
      });
    },

    formatScore(score) {
      return parseFloat(score).toFixed(2);
    },
  },

  mounted() {
    this.loadRankingData();
  },
};
</script>

<style scoped>
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

.admin-note {
  font-style: italic;
  color: #2c3e50;
  margin-top: 20px;
}

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

.warning {
  color: red;
  font-weight: bold;
  margin-top: 30px;
  font-size: 1.5rem;
}
</style>
