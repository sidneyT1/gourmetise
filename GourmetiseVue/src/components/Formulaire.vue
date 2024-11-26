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
          :error-messages="name.errorMessage"
          label="Nom"
        ></v-text-field>
  
        <v-text-field
          v-model="street" 
          :counter="7"
          :error-messages="street.errorMessage"
          label="Rue"
        ></v-text-field>
  
        <v-text-field
          v-model="postalCode" 
          :error-messages="postalCode.errorMessage"
          label="Code Postal"
        ></v-text-field>
  
        <v-text-field
          v-model="city" 
          :error-messages="city.errorMessage"
          label="Ville"
        ></v-text-field>
  
        <v-text-field
          v-model="phone" 
          :error-messages="phone.errorMessage"
          label="Numéro de téléphone"
        ></v-text-field>
  
        <v-text-field
          v-model="siren" 
          :error-messages="siren.errorMessage"
          label="N° SIREN/SIRET"
        ></v-text-field>
  
        <v-text-field
          v-model="contactName" 
          :error-messages="contactName.errorMessage"
          label="Nom de contact"
        ></v-text-field>
  
        <v-text-field
          v-model="description" 
          :error-messages="description.errorMessage"
          label="Description"
        ></v-text-field>
  
        <v-checkbox
          v-model="checkbox" 
          
          :error-messages="checkbox.errorMessage"
          label="En soumettant ce formulaire, J’ai lu et accepté les conditions d’utilisation relatives à la collecte de mes données."
          type="checkbox"
          required
          
          
        ></v-checkbox>

        <v-btn @click="Reinitialiser">
          Annuler
        </v-btn>

        <v-btn type="submit" class="me-6">
          Valider
        </v-btn>
  
      </form>
    </v-container>
</template>

<script setup>
    import { ref } from 'vue';
    import axios from 'axios';
  
    const name = ref('');
    const street = ref('');
    const postalCode = ref('');
    const city = ref('');
    const phone = ref('');
    const siren = ref('');
    const contactName = ref('');
    const description = ref('');
    const checkbox = ref(false);


    const userEmail = 'manager@gmail.com';
  

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
  

    const Reinitialiser = () => {
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
