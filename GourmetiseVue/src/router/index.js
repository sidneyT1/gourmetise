import { createRouter, createWebHistory } from 'vue-router';
import Index from "@/views/index.vue";
import ContestParams from '@/components/ContestParams.vue';
import Formulaire from '@/components/Formulaire.vue';
import ConditionsUtilisation from '@/components/ConditionsUtilisation.vue';
import Signup from '@/components/Signup.vue';
import Login from '@/components/Login.vue';
import Classement from '@/components/Classement.vue';
import ScorePersonnel from '@/components/ScorePersonnel.vue';
import axios from 'axios';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: Index,
        },
        {
            path: '/ContestParams',
            name: 'ContestParams',
            component: ContestParams,
            meta: { requiresAuth: true }
        },
        {
            path: '/Formulaire',
            name: 'Formulaire',
            component: Formulaire,
            meta: { requiresAuth: true }
        },
        {
            path: '/ConditionsUtilisation',
            name: 'ConditionsUtilisation',
            component: ConditionsUtilisation
        },
        {
            path: '/Signup',
            name: 'Signup',
            component: Signup
        },
        {
            path: '/Login',
            name: 'Login',
            component: Login
        },
        {
            path: '/Classement',
            name: 'Classement',
            component: Classement,
           
        },
        {
            path: "/personal-score",
            component: ScorePersonnel,
        }
    ],
});

router.beforeEach(async (to, from, next) => {
    const token = localStorage.getItem('access_token');
    
    // Si la route nécessite une authentification et que l'utilisateur n'est pas connecté
    if (to.meta.requiresAuth && !token) {
      return next('/Login');
    }
  
    // Si l'utilisateur essaie d'accéder à la route "/Classement"
    if (to.name === "Classement") {
      try {
        // Récupérer les paramètres du concours
        const paramsRes = await axios.get(import.meta.env.VITE_API_URL + '/api/contestParams');
        const isPublished = paramsRes.data.isPublished;
        
        // Si le classement est publié, autoriser l'accès
        if (isPublished) {
          return next(); // Permet d'accéder à la page Classement
        } else {
          return next('/'); // Rediriger vers la page d'accueil si le classement n'est pas publié
        }
      } catch (error) {
        console.error("Erreur avant navigation :", error);
        return next('/'); // En cas d'erreur, rediriger vers la page d'accueil
      }
    }
  
    // Continuer avec le reste des routes
    next();
  });
  
  

export default router;
