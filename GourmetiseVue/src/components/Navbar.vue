<template>
  <v-app-bar app color="primary" dark>
    <v-row align="center" class="flex-grow-1">
      <v-col class="d-flex" cols="auto">
        <img src="../assets/logo.png" alt="Logo La Gourmetise" class="logo" />
      </v-col>

      <v-col class="d-flex" cols="auto">
        <v-app-bar-title>La Gourmetise - Concours</v-app-bar-title>
      </v-col>
    </v-row>

    <v-spacer></v-spacer>

    <v-row align="center" class="d-flex">
      <v-btn>
        <router-link to="/" class="router-link">
          Accueil
        </router-link>
      </v-btn>
      <v-btn>
        <router-link to="/Formulaire" class="router-link">
          S'inscrire
        </router-link>
      </v-btn>
      <v-btn>
        <router-link to="/results" class="router-link">
          Afficher résultats
        </router-link>
      </v-btn>

     
      <v-btn v-if="isLoggedIn" @click="logout" color="red">
        Déconnexion
      </v-btn>
    </v-row>
  </v-app-bar>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();


const isLoggedIn = ref(localStorage.getItem('access_token') !== null);


watch(() => localStorage.getItem('access_token'), (newValue) => {
  isLoggedIn.value = newValue !== null;
});


const logout = () => {
  localStorage.removeItem('access_token'); 
  router.push('/Login'); 
};
</script>

<style scoped>
a {
  text-decoration: none;
}

.router-link {
  color: white;
  text-decoration: none;
}

img {
  max-height: 65px;
}
</style>
