import { createRouter, createWebHistory } from 'vue-router';
import Index from "@/views/index.vue";
import ContestParams from '@/components/ContestParams.vue';
import Formulaire from '@/components/Formulaire.vue';
import ConditionsUtilisation from '@/components/ConditionsUtilisation.vue';
import Signup from '@/components/Signup.vue';
import Login from '@/components/Login.vue';

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
            meta: { requiresAuth: true } // Protection de la route
        },
        {
            path: '/Formulaire',
            name: 'Formulaire',
            component: Formulaire,
            meta: { requiresAuth: true } // Protection de la route
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
        }
    ],
});

// Vérification de l'authentification avant chaque navigation
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('access_token');

    if (to.meta.requiresAuth && !token) {
        next('/Login'); // Redirection si pas de token
    } else {
        next();
    }
});

export default router;
