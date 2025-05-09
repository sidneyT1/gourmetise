<template>
  <div class="ranking-container">
    <h1 class="ranking-title">Classement du Concours</h1>

    <!-- Affichage des 3 premiers participants (Podium) -->
    <div v-if="ranking && ranking.length > 0">
      <div v-if="userRole !== 'Gérant'" class="podium">
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

      <!-- Bouton pour voir son propre classement si l'utilisateur est un participant et connecté -->
      <router-link
        v-if="userRole === 'Participant' && isUserConnected"
        to="/personal-score"
        class="btn-see-personal-score"
      >
        Voir mon classement
      </router-link>

      <!-- Note pour le gérant -->
      <p v-if="userRole === 'Gérant'" class="admin-note">
        Classement complet réservé au gérant du concours
      </p>

      <!-- Affichage complet du classement pour le gérant -->
      <table v-if="userRole === 'Gérant' && isUserConnected && ranking.length > 0" class="ranking-table">
        <thead>
          <tr>
            <th>Rang</th>
            <th>Boulangerie</th>
            <th>Score</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(bakery, index) in ranking" :key="index">
            <td>{{ index + 1 }}</td>
            <td>{{ bakery.bakery_name }}</td>
            <td>{{ formatScore(bakery.avg_score) }} points</td>
          </tr>
        </tbody>
      </table>

      <!-- Bouton de publication du classement pour le gérant -->
      <button v-if="userRole === 'Gérant' && isUserConnected && !contestParams.isPublished" @click="publishRanking" class="publish-btn">
        Publier le classement
      </button>
    </div>

    <!-- Message d'erreur si aucun classement n'est disponible -->
    <p v-if="!ranking || ranking.length === 0" class="warning">
      {{ errorMessage }}
    </p>
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
      contestParams: null,
      userRole: null,
      isUserConnected: !!localStorage.getItem("access_token"),
    };
  },
  methods: {
    async loadUserRole() {
      const token = localStorage.getItem('access_token');
      if (token) {
        try {
          const response = await axios.get(import.meta.env.VITE_API_URL + '/api/profile', {
            headers: { Authorization: `Bearer ${token}` },
          });
          this.userRole = response.data.role; // Récupère le rôle à partir de l'API
        } catch (error) {
          console.error('Erreur lors de la récupération du rôle utilisateur:', error);
        }
      }
    },

    async loadRankingData() {
      try {
        // Vérifier si le concours est publié avant d'afficher le classement
        const paramsResponse = await axios.get(import.meta.env.VITE_API_URL + "/api/contestParams");

        // Si l'utilisateur est "Gérant", ignorez l'état de publication
        if (this.userRole !== 'Gérant' && !paramsResponse.data.isPublished) {
          this.errorMessage = "Le classement n'a pas encore été publié.";
          this.ranking = [];
          return;
        }

        // Si le concours est publié ou si l'utilisateur est un "Gérant", récupérer les top 3 boulangeries
        const response = await axios.get(import.meta.env.VITE_API_URL + "/api/leaderboard");

        if (response.status === 200 && response.data.length > 0) {
          this.ranking = response.data.slice(0, 3); // Limiter à top 3
          this.errorMessage = "";
          this.launchConfetti();
        } else {
          this.ranking = [];
          this.errorMessage = "Aucune donnée disponible pour le classement.";
        }
      } catch (error) {
        console.error("Erreur lors du chargement du classement:", error);
        if (error.response && error.response.status === 400) {
          this.errorMessage = "La période d'évaluation n'est pas encore terminée.";
        } else {
          this.errorMessage = "Une erreur est survenue lors de la récupération des résultats.";
        }
        this.ranking = [];
      }
    },

    async loadContestParams() {
      try {
        const response = await axios.get(import.meta.env.VITE_API_URL + "/api/contestParams");
        if (response.status === 200) {
          this.contestParams = response.data;
        }
      } catch (error) {
        console.error("Erreur lors du chargement des paramètres du concours:", error);
      }
    },

    async publishRanking() {
      try {
        await axios.put(import.meta.env.VITE_API_URL + "/api/contestParams", {
          isPublished: true,
        });
        this.contestParams.isPublished = true;
        alert("Classement publié avec succès !");
      } catch (error) {
        alert("Erreur lors de la publication du classement.");
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
    this.loadUserRole(); // Charger le rôle de l'utilisateur
    this.loadContestParams();  // Charger les paramètres du concours
    this.loadRankingData();  // Charger les résultats du classement
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

.ranking-table {
  width: 80%;
  margin-top: 20px;
  margin-left: auto;
  margin-right: auto;
  border-collapse: collapse;
  text-align: left;
}

.ranking-table th,
.ranking-table td {
  padding: 10px;
  border: 1px solid #ddd;
}

.ranking-table th {
  background-color: #f2f2f2;
}
.btn-see-personal-score {
  display: inline-block;
  background-color: #007bff;
  color: white;
  padding: 10px 25px;
  margin-top: 30px;
  font-size: 1.2rem;
  border-radius: 5px;
  text-decoration: none;
  transition: background-color 0.3s ease;
}

.btn-see-personal-score:hover {
  background-color: #0056b3;
}

</style>
