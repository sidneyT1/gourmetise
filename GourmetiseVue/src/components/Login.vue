<script setup>
import { ref } from "vue";
import api from "@/api";
import { useRouter } from "vue-router";

const router = useRouter();

const email = ref("");
const password = ref("");
const errorMessage = ref("");

const login = async () => {
  try {
    const response = await api.post("/login", {
      mail: email.value,
      password: password.value,
    });

    const token = response.data.token;
    const userRole = response.data.user.role;

    

    localStorage.setItem("access_token", token);
    localStorage.setItem("user_role", userRole);

    router.push("/");
  } catch (error) {
    errorMessage.value = error.response?.data?.message || "Erreur lors de la connexion";
  }
};
</script>

<template>
  <v-container class="pa-4" style="max-width: 500px; min-width: 500px;">
    <v-row class="pa-4" justify="center">
      <v-col cols="12" class="text-center">
        <h1 class="titre-formulaire">Connexion</h1>
      </v-col>
    </v-row>

    <form @submit.prevent="login">
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
        Se connecter
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
