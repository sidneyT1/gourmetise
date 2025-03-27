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
        }
    ],
});

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('access_token');

    if (to.meta.requiresAuth && !token) {
        next('/Login'); 
    } else {
        next();
    }
});

export default router;
