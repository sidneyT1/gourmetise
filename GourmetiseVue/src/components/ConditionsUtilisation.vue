<template>
    <v-container class="conditions-container pa-4" style="max-width: 800px; min-width: 800px;">
      <v-row class="pa-4" justify="center">
        <v-col cols="12" class="text-center">
          <h1 class="titre-conditions">Conditions d'utilisation</h1>
        </v-col>
      </v-row>
  
      <v-card outlined>
        <v-card-text>
          <div class="conditions-text">
            <h2>Introduction</h2>
            <p>
              En utilisant ce site Web, vous acceptez de vous conformer aux présentes conditions d'utilisation. Si vous n'êtes pas d'accord avec ces conditions, veuillez ne pas utiliser ce site.
            </p>
  
            <h2>Collecte des données personnelles</h2>
            <p>
              Nous collectons certaines informations personnelles lorsque vous vous inscrivez sur notre site, y compris votre nom, adresse e-mail, adresse, numéro de téléphone et d'autres informations nécessaires à votre inscription.
            </p>
            
            <h2>Utilisation des informations</h2>
            <p>
              Les informations collectées peuvent être utilisées pour vous contacter en rapport avec le concours, pour vous fournir des informations liées à votre inscription et pour améliorer l'expérience utilisateur.
            </p>
  
            <h2>Protection des données</h2>
            <p>
              Nous prenons des mesures de sécurité pour protéger vos données personnelles contre tout accès non autorisé. Cependant, aucune méthode de transmission sur Internet n'est totalement sécurisée, et nous ne pouvons garantir la sécurité de vos informations.
            </p>
  
            <h2>Propriété intellectuelle</h2>
            <p>
              Tous les contenus présents sur ce site (textes, images, logos, etc.) sont protégés par des droits de propriété intellectuelle et ne peuvent être utilisés sans autorisation.
            </p>
  
            <h2>Responsabilité</h2>
            <p>
              Nous ne serons pas responsables des pertes ou dommages causés par l'utilisation ou l'incapacité d'utiliser ce site, sauf en cas de négligence grave de notre part.
            </p>
  
            <h2>Modifications des conditions</h2>
            <p>
              Nous nous réservons le droit de modifier ces conditions d'utilisation à tout moment. Toute modification sera publiée sur cette page, et elle sera effective dès sa publication.
            </p>
  
            <h2>Loi applicable</h2>
            <p>
              Les présentes conditions d'utilisation sont régies par les lois du pays où nous opérons. Toute action légale liée à ce site sera soumise à la juridiction des tribunaux compétents dans ce pays.
            </p>
          </div>
        </v-card-text>
      </v-card>
  
      <v-row class="text-center" justify="center" style="margin-top: 20px;">
        
      </v-row>
    </v-container>
  </template>
  
  <script setup>
   
  </script>
  
  <style scoped>
  .conditions-container {
    background-color: #f9f9f9;
    padding: 20px;
    border-radius: 8px;
  }
  
  .titre-conditions {
    font-size: 1.8rem;
    font-weight: bold;
    margin-bottom: 20px;
  }
  
  .conditions-text {
    font-size: 1rem;
    line-height: 1.6;
    color: #333;
    padding: 10px;
  }
  
  h2 {
    font-size: 1.2rem;
    color: #1976D2;
    margin-top: 20px;
  }
  
  p {
    font-size: 1rem;
    color: #555;
    margin-top: 10px;
  }
  
  .large-button {
    min-width: 150px;
    padding: 8px;
    font-size: 16px;
    margin-top: 10px;
  }
  </style>
  