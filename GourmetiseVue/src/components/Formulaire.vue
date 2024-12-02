<template>
  <v-container class="pa-4 fond-page" style="max-width: 700px;">
    <v-row class="pa-4" justify="center">
      <v-col cols="12" class="text-center">
        <h1 class="titre-formulaire">Inscription au concours de la meilleure boulangerie</h1>
      </v-col>
    </v-row>

    <form @submit.prevent="submit">
      <v-text-field
        v-model="name"
        :counter="10"
        :rules="[v => !!v || 'Le champ nom est obligatoire.']"
        label="Nom"
      ></v-text-field>

      <v-text-field
        v-model="street"
        :counter="7"
        :rules="[v => !!v || 'Le champ rue est obligatoire.']"
        label="Rue"
      ></v-text-field>

      <v-text-field
        v-model="postalCode"
        :rules="[
          v => !!v || 'Le champ code postal est obligatoire.',
          v => /^\d{5}$/.test(v) || 'Le code postal doit contenir exactement 5 chiffres.'
        ]"
        label="Code Postal"
      ></v-text-field>

      <v-text-field
        v-model="city"
        :rules="[v => !!v || 'Le champ ville est obligatoire.']"
        label="Ville"
      ></v-text-field>

      <v-text-field
        v-model="phone"
        :rules="[
          v => !!v || 'Le champ téléphone est obligatoire.',
          v => /^(0|\+33)[1-9]([-. ]?[0-9]{2}){4}$/.test(v) || 'Le numéro de téléphone doit contenir exactement 10 chiffres.'
        ]"
        label="Numéro de téléphone"
      ></v-text-field>

      <v-text-field
        v-model="siren"
        :rules="[
          v => !!v || 'Le champ SIREN/SIRET est obligatoire.',
          v => /^(\d{9}|\d{14})$/.test(v) || 'Le N° SIREN/SIRET doit contenir 9 ou 14 chiffres.'
        ]"
        label="N° SIREN/SIRET"
      ></v-text-field>

      <v-text-field
        v-model="contactName"
        :rules="[v => !!v || 'Le champ nom de contact est obligatoire.']"
        label="Nom de contact"
      ></v-text-field>

      <v-text-field
        v-model="description"
        :rules="[v => !!v || 'Le champ description est obligatoire.']"
        label="Description"
      ></v-text-field>

      <v-checkbox
        v-model="checkbox"
        :rules="[v => !!v || 'Vous devez accepter les conditions d\'utilisation.']"
        label="En soumettant ce formulaire, J’ai lu et accepté les conditions d’utilisation relatives à la collecte de mes données."
        required
      ></v-checkbox>

      <v-row class="validation" justify="center">
      <v-btn @click="resetForm">Annuler</v-btn>
      <v-btn type="submit" class="me-6">Valider</v-btn>
      </v-row>
    </form>
  </v-container>
</template>

<script setup>
  import { ref } from 'vue';
  import axios from 'axios';

  // Déclaration des champs
  const name = ref('');
  const street = ref('');
  const postalCode = ref('');
  const city = ref('');
  const phone = ref('');
  const siren = ref('');
  const contactName = ref('');
  const description = ref('');
  const checkbox = ref(false);

  const userEmail = 'abc@gmail.com';

  const submit = async () => {
    try {
      const data = {
        name: name.value,
        street: street.value,
        postcode: postalCode.value,
        city: city.value,
        phonenumber: phone.value,
        siren: siren.value,
        contactname: contactName.value,
        description: description.value,
        conditions_checkbox: checkbox.value,
        conditions_date: new Date().toISOString(),
        user: {
          mail: userEmail,
        },
      };

      const response = await axios.post(import.meta.env.VITE_API_URL + '/api/bakery', data);
      console.log(response.data);
    } catch (error) {
      console.error('Erreur lors de la soumission du formulaire:', error);
    }
  };

  const resetForm = () => {
    name.value = '';
    street.value = '';
    postalCode.value = '';
    city.value = '';
    phone.value = '';
    siren.value = '';
    contactName.value = '';
    description.value = '';
    checkbox.value = false;
  };
</script>

<style scoped>
.fond-page {
  background-color: rgb(230, 142, 142);
  padding: 20px;
  border-radius: 8px;
}

.titre-formulaire {
  font-size: 1.4rem;
  font-weight: 600;
}


</style>
