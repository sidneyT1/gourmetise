<template>
  <v-container class="pa-4 fond-page" style="max-width: 700px; min-width: 700px;">
    <v-row class="pa-4" justify="center">
      <v-col cols="12" class="text-center">
        <h1 class="titre-formulaire">Inscription au concours La Gourmetise</h1>
      </v-col>
    </v-row>

    <form @submit.prevent="submit">
      <v-text-field v-model="siren" :rules="sirenRules" label="N° SIREN/SIRET" />
      <v-text-field v-model="name" :rules="nameRules" label="Nom" />
      <v-text-field v-model="street" :rules="streetRules" label="Rue" />
      <v-row>
        <v-col cols="4">
          <v-text-field
            v-model="postalCode"
            :rules="postalCodeRules"
            label="Code Postal"
            @blur="trouveCodepostal"
          ></v-text-field>
        </v-col>
        <v-col cols="8">
          <v-text-field
            v-model="city"
            :rules="cityRules"
            label="Ville"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-text-field v-model="phone" :rules="phoneRules" label="Numéro de téléphone" />
      <v-text-field v-model="contactName" :rules="contactNameRules" label="Nom de contact" />
      <v-textarea v-model="description" :rules="descriptionRules" label="Description" rows="5" outlined></v-textarea>
      
   
        <v-card-text>
          <div class="conditions-text">
            En soumettant ce formulaire, J’ai lu et accepté les
            <a href="/ConditionsUtilisation" target="_blank" class="link-btn">conditions d’utilisation</a>
            relatives à la collecte de mes données.
          </div>
          
         
          <v-checkbox 
            v-model="checkbox" 
            
            label="J'accepte" 
            class="d-flex justify-center mt-4"
            required
          />
        </v-card-text>
      

      <v-row class="validation" justify="center">
        <v-btn to="/" class="large-button me-7">Retour</v-btn>
        <v-btn type="submit" class="large-button" :disabled="!empecheValid">Valider</v-btn> 
      </v-row>
    </form>
  </v-container>
</template>


<script setup>
import { ref, computed } from 'vue';

import axios from 'axios';
import { toast } from 'vue3-toastify';

const siren = ref('');
const name = ref('');
const street = ref('');
const postalCode = ref('');
const city = ref('');
const phone = ref('');
const contactName = ref('');
const description = ref('');
const checkbox = ref(false);

const userEmail = 'abc@gmail.com';

const sirenRules = [
  v => !!v || 'Le champ SIREN/SIRET est obligatoire.',
  v => /^(\d{9}|\d{14})$/.test(v) || 'Le N° SIREN/SIRET doit contenir 9 ou 14 chiffres.'
];
const nameRules = [v => !!v || 'Le champ nom est obligatoire.'];
const streetRules = [v => !!v || 'Le champ rue est obligatoire.'];
const postalCodeRules = [
  v => !!v || 'Le champ code postal est obligatoire.',
  v => /^\d{5}$/.test(v) || 'Le code postal doit contenir exactement 5 chiffres.'
];
const cityRules = [v => !!v || 'Le champ ville est obligatoire.'];
const phoneRules = [
  v => !!v || 'Le champ téléphone est obligatoire.',
  v => /^(0|\+33)[1-9]([-. ]?[0-9]{2}){4}$/.test(v) || 'Le numéro de téléphone doit contenir exactement 10 chiffres.'
];
const contactNameRules = [v => !!v || 'Le champ nom de contact est obligatoire.'];
const descriptionRules = [v => !!v || 'Le champ description est obligatoire.'];
const checkboxRules = [v => !!v || 'Vous devez accepter les conditions d\'utilisation.'];

const empecheValid = computed(() => {
  return [siren, name, street, postalCode, city, phone, contactName, description].every(field => field.value.length > 0) && checkbox.value;
});

const trouveCodepostal = async () => {
  if (postalCode.value.length === 5) {
    try {
      const response = await axios.get(`https://geo.api.gouv.fr/communes?codePostal=${postalCode.value}`);
      if (response.data.length > 0) {
        city.value = response.data[0].nom;
      } else {
        toast.error('Aucune ville trouvée pour ce code postal.');
        city.value = '';
      }
    } catch (error) {
      toast.error('Erreur lors de la récupération de la ville.');
      console.error(error);
    }
  } else {
    city.value = '';
  }
};

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

    toast.success('Formulaire soumis avec succès!');
    console.log(response.data);
  } catch (error) {
    if (error.response && error.response.data) {
      toast.error(error.response.data.error || 'Une erreur est survenue!');
    } else {
      toast.error('Une erreur inconnue est survenue!');
    }
    console.error('Erreur lors de la soumission du formulaire:', error);
  }
};


</script>

<style scoped>
.fond-page {
  background-color: rgb(252, 202, 202);
  padding: 20px;
  border-radius: 8px;
}

.titre-formulaire {
  font-size: 1.4rem;
  font-weight: 600;
}

.validation {
  margin-bottom: 10px;
  margin-top: 20px;
}

.large-button { 
  min-width: 150px; 
  padding: 8px; 
  font-size: 16px;
}

.conditions-card {
  margin-top: 20px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.conditions-text {
  font-size: 14px;
  text-align: center;
  margin-bottom: 10px;
}








</style>
