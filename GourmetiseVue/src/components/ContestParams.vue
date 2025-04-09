<template>
  <v-container>
    <h1>Paramètres du concours</h1>

    <v-alert v-if="!isEditable" type="warning" class="mb-4">
      Les inscriptions ont commencé. Vous ne pouvez plus modifier les paramètres.
    </v-alert>

    <v-form @submit.prevent="handleSubmit" v-if="contestParams" ref="form">
      <v-text-field v-model="contestParams.title" label="Titre du concours" :disabled="!isEditable" />
      <v-textarea v-model="contestParams.description" label="Description" :disabled="!isEditable" />

      <v-row>
        <v-col cols="12" md="6">
          <v-text-field
            v-model="contestParams.startRegistration"
            label="Début des inscriptions"
            type="datetime-local"
            :disabled="!isEditable"
          />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field
            v-model="contestParams.endRegistration"
            label="Fin des inscriptions"
            type="datetime-local"
            :disabled="!isEditable"
          />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field
            v-model="contestParams.startEvaluation"
            label="Début de l'évaluation"
            type="datetime-local"
            :disabled="!isEditable"
          />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field
            v-model="contestParams.endEvaluation"
            label="Fin de l'évaluation"
            type="datetime-local"
            :disabled="!isEditable"
          />
        </v-col>
      </v-row>

      <v-btn color="primary" type="submit" :disabled="!isEditable">Enregistrer</v-btn>
    </v-form>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const contestParams = ref(null);
const isEditable = ref(true);
const isNew = ref(false);
const router = useRouter();

const loadParams = async () => {
  try {
    const res = await axios.get(import.meta.env.VITE_API_URL + '/api/contestParams');

    const data = res.data;

    if (!data || Object.keys(data).length === 0) {
      contestParams.value = {
        title: '',
        description: '',
        startRegistration: '',
        endRegistration: '',
        startEvaluation: '',
        endEvaluation: ''
      };
      isNew.value = true;
    } else {
      // Formatage des dates
      data.startRegistration = formatDateForInput(data.startRegistration);
      data.endRegistration = formatDateForInput(data.endRegistration);
      data.startEvaluation = formatDateForInput(data.startEvaluation);
      data.endEvaluation = formatDateForInput(data.endEvaluation);

      contestParams.value = data;
      isNew.value = false;

      // Verrouille les champs si les inscriptions ont commencé
      const now = new Date();
      const startReg = new Date(data.startRegistration);
      if (now >= startReg) {
        isEditable.value = false;
      }
    }
  } catch (error) {
    console.error('Erreur lors du chargement des paramètres :', error);
  }
};

const handleSubmit = async () => {
  try {
    const payload = {
      ...contestParams.value,
      startRegistration: new Date(contestParams.value.startRegistration),
      endRegistration: new Date(contestParams.value.endRegistration),
      startEvaluation: new Date(contestParams.value.startEvaluation),
      endEvaluation: new Date(contestParams.value.endEvaluation),
    };

    if (isNew.value) {
      await axios.post(import.meta.env.VITE_API_URL +'/api/contestParams', payload);
      alert('Paramètres créés avec succès.');
    } else {
      await axios.put(import.meta.env.VITE_API_URL +'/api/contestParams', payload);
      alert('Paramètres mis à jour avec succès.');
    }

    router.push('/');
  } catch (error) {
    console.error('Erreur lors de la sauvegarde :', error);
  }
};

const formatDateForInput = (dateStr) => {
  if (!dateStr) return '';
  const date = new Date(dateStr);
  if (isNaN(date)) return '';
  const offset = date.getTimezoneOffset();
  date.setMinutes(date.getMinutes() - offset);
  return date.toISOString().slice(0, 16);
};

onMounted(() => {
  loadParams();
});
</script>
