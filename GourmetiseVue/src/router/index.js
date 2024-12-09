import {createRouter, createWebHistory} from 'vue-router'
import Index from "@/views/index.vue";
import ContestParams from '@/components/ContestParams.vue';
import Formulaire from '@/components/Formulaire.vue';
import ConditionsUtilisation from '@/components/ConditionsUtilisation.vue';



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
        },
        {
            path: '/Formulaire',
            name: 'Formulaire',
            component: Formulaire,
        },
        {
            path: '/ConditionsUtilisation',
            name: 'ConditionsUtilisation',
            component: ConditionsUtilisation
          },
      
            
        
    ],
})

export default router
