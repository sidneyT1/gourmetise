<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const router = useRouter();

const email = ref("");
const password = ref("");
const errorMessage = ref("");

const register = async () => {
  try {
    const response = await axios.post("http://localhost:8000/api/register", {
      mail: email.value,
      password: password.value,
    });

    
    const token = response.data.token;
    
   
    localStorage.setItem("access_token", token);

  
    router.push("/dashboard");
  } catch (error) {
    errorMessage.value = error.response?.data?.message || "Erreur lors de l'inscription.";
  }
};
</script>

<template>
  <v-container class="pa-4" style="max-width: 500px; min-width: 500px;">
    <v-row class="pa-4" justify="center">
      <v-col cols="12" class="text-center">
        <h1 class="titre-formulaire">Créer un compte</h1>
      </v-col>
    </v-row>

    <form @submit.prevent="register">
      <v-text-field
        v-model="email"
        label="Email"
        type="email"
        required
        outlined
        placeholder="Votre email"
      />
      <v-text-field
        v-model="password"
        label="Mot de passe"
        type="password"
        required
        outlined
        placeholder="Votre mot de passe"
      />

      <v-btn
        type="submit"
        class="large-button"
        :disabled="!email || !password"
        block
      >
        S'inscrire
      </v-btn>

      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>
  </v-container>
</template>

<style scoped>
.titre-formulaire {
  font-size: 1.4rem;
  font-weight: 600;
}

.large-button {
  min-width: 150px;
  padding: 8px;
  font-size: 16px;
}

.error {
  color: red;
  margin-top: 10px;
  text-align: center;
}
</style>
