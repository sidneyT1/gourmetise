<template>
  <div class="score-personnel">
    <h1>📊 Mon classement</h1>
    <div v-if="personalScore" class="personalScore">
      <h2>{{ personalScore.bakery_name }}</h2>
      <p>Score : {{ formatScore(personalScore.avg_score) }} points</p>
      <p>Rang : {{ personalScore.rank }}</p>
    </div>
    <p v-else class="warning">Aucun score trouvé pour votre boulangerie.</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ScorePersonnel",
  data() {
    return {
      personalScore: null,
    };
  },
  methods: {
    formatScore(score) {
      return parseFloat(score).toFixed(2);
    },
    async fetchPersonalScore() {
      const token = localStorage.getItem("access_token");

      if (!token) {
        console.warn("Aucun token trouvé. Veuillez vous connecter.");
        return;
      }

      try {
        const response = await axios.get("http://localhost:8000/api/bakery/score", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        this.personalScore = response.data;
      } catch (error) {
        console.error("Erreur lors de la récupération du score personnel :", error);
      }
    },
  },
  mounted() {
    this.fetchPersonalScore();
  },
};
</script>



<style scoped>
.score-personnel {
  padding: 20px;
  text-align: center;
}

.personalScore {
  background-color: #f5f5f5;
  border-radius: 10px;
  padding: 20px;
  margin-top: 20px;
  display: inline-block;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.warning {
  color: #a94442;
  margin-top: 20px;
}
</style>
