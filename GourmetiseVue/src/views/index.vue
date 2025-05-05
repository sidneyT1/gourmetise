<template>
  <v-container>
    <v-row class="text-center my-12" justify="center">
      <v-col cols="12">
        <img alt="Logo La Gourmetise" class="logo" src="../assets/logo.png"/>
        <h1>Bienvenue au Concours de la Meilleure Boulangerie</h1>
        <p>
          Participez au concours de la meilleure boulangerie organisé par La Gourmetise
          et découvrez les talents de la boulangerie dans votre région.
        </p>
      </v-col>
    </v-row>
    <v-row class="text-center" justify="center">
      <v-col cols="12" md="6">
        <v-card outlined>
          <v-card-title class="headline">Participez en tant que boulanger</v-card-title>
          <v-card-text>
            Inscrivez votre boulangerie pour tenter de remporter le titre de la meilleure boulangerie.
          </v-card-text>
          <v-card-actions>
            <v-btn color="primary" to="/Formulaire">S'inscrire</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
      <v-col cols="12" md="6">
        <v-card outlined>
          <v-card-title class="headline"> Résultats du concours</v-card-title>
          <v-card-text>
            Voir les résultats des concours de la meilleure boulangerie
          </v-card-text>
          <v-card-actions>
            <v-btn color="primary" @click="handleShowResults">Afficher Résultats</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
      <v-col cols="12" md="6">
        <v-card outlined>
          <v-card-title class="headline"> Paramètres du concours</v-card-title>
          <v-card-text>
            Définir les paramètres du concours
          </v-card-text>
          <v-card-actions>
            <v-btn color="primary" to ="/ContestParams">Définir Paramètres</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>

    <v-dialog v-model="dialogVisible" max-width="400px" persistent>
      <v-card>
        <v-card-title class="d-flex align-center justify-center">
          <v-icon class="mr-2">mdi-information</v-icon>
          Alerte
        </v-card-title>
        <v-card-text class="text-center">
          Le classement n'a pas encore été publié. Vous pourrez consulter les résultats une fois qu'ils seront disponibles.
        </v-card-text>
        <v-card-actions class="d-flex justify-center">
          <v-btn color="primary" @click="dialogVisible = false">Fermer</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const userRole = ref(null);
const isPublished = ref(false);
const dialogVisible = ref(false);

const fetchIsPublished = async () => {
  try {
    const response = await axios.get(import.meta.env.VITE_API_URL + '/api/contestParams');
    isPublished.value = response.data.isPublished === true;
  } catch (error) {
    console.error('Erreur isPublished:', error);
  }
};

const fetchUserRole = async () => {
  try {
    const token = localStorage.getItem('access_token');
    if (!token) return;

    const response = await axios.get(import.meta.env.VITE_API_URL + '/api/profile', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    userRole.value = response.data.role;
  } catch (error) {
    console.error('Erreur récupération rôle utilisateur :', error);
  }
};

const handleShowResults = async () => {
  await Promise.all([fetchIsPublished(), fetchUserRole()]);
  if (isPublished.value || userRole.value === 'Gérant') {
    router.push('/Classement');
  } else {
    dialogVisible.value = true;
  }
};

onMounted(() => {
  fetchIsPublished();
  fetchUserRole();
});
</script>


<style scoped>
.my-12 {
  margin-top: 3rem;
  margin-bottom: 3rem;
}

.v-card-title {
  display: flex;
  justify-content: center;
  align-items: center;
}

.v-card-title .v-icon {
  margin-right: 10px;
}

.v-card-text {
  text-align: center;
}
</style>
