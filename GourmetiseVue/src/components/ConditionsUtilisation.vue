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
            En accédant ou en utilisant ce site Web, vous acceptez de vous conformer aux présentes conditions d'utilisation. Si vous n'acceptez pas ces conditions, veuillez ne pas utiliser ce site ni ses services.
          </p>

          <h2>Objet du site</h2>
          <p>
            Le site a pour but de gérer le concours de la meilleure boulangerie, organisé par <b>La Gourmetise</b>. Ce concours est réservé aux boulangers et établissements de boulangerie immatriculés au Registre National des Entreprises (RNE).
          </p>

          <h2>Inscription et obligations des participants</h2>
          <p>
            Les participants doivent fournir des informations exactes lors de l'inscription, y compris leur N° SIREN/SIRET, nom, adresse, numéro de téléphone, et une description de leur activité. Toute inscription incomplète ou contenant des informations incorrectes pourra être refusée.
          </p>

          <h2>Collecte et utilisation des données personnelles</h2>
          <p>
            Conformément au RGPD, nous collectons et traitons vos données personnelles dans le cadre strictement nécessaire à la gestion du concours :
          </p>
          <ul>
            <li><b>Données collectées :</b> N° SIREN/SIRET, nom, adresse, téléphone, nom de contact, description de l'établissement, et les votes des juges.</li>
            <li><b>Utilisation des données :</b> Gestion des inscriptions, suivi des votes, publication des résultats, et amélioration du concours.</li>
            <li><b>Durée de conservation :</b> Les données seront conservées jusqu'à la fin de la période légale liée au concours ou selon les exigences réglementaires applicables.</li>
          </ul>

          <h2>Droits des utilisateurs</h2>
          <p>
            Conformément au RGPD, vous disposez des droits suivants concernant vos données personnelles :
          </p>
          <ul>
            <li>Droit d'accès, de rectification, et d'effacement des données.</li>
            <li>Droit à la limitation et à l'opposition au traitement.</li>
            <li>Droit à la portabilité des données.</li>
          </ul>
          

          <h2>Sécurité des données</h2>
          <p>
            Nous appliquons les recommandations de l'OWASP pour sécuriser vos données. Cependant, aucune méthode de transmission sur Internet n'est totalement sécurisée. En cas de faille, nous informerons les utilisateurs concernés conformément à la réglementation.
          </p>

          <h2>Propriété intellectuelle</h2>
          <p>
            Tout contenu présent sur ce site (textes, images, logos, etc.) est protégé par des droits de propriété intellectuelle. Toute utilisation non autorisée est interdite.
          </p>

          <h2>Responsabilité</h2>
          <p>
            <b>Participants :</b> Les participants sont responsables de la véracité des informations fournies.
          </p>
          
          <p>
            <b>La Gourmetise :</b> Nous ne sommes pas responsables des pertes ou dommages indirects liés à l'utilisation de ce site.
          </p>

          <h2>Modifications des conditions</h2>
          <p>
            Nous nous réservons le droit de modifier ces conditions d'utilisation à tout moment. Les modifications prendront effet dès leur publication sur cette page.
          </p>

          <h2>Loi applicable et juridiction</h2>
          <p>
            Ces conditions d'utilisation sont régies par les lois françaises. Tout litige sera soumis à la compétence exclusive des tribunaux français.
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

p, li {
  font-size: 1rem;
  color: #555;
  margin-top: 10px;
}

ul {
  margin-left: 20px;
}

.large-button {
  min-width: 150px;
  padding: 8px;
  font-size: 16px;
  margin-top: 10px;
}
</style>
