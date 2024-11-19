import {createRouter, createWebHistory} from 'vue-router'
import Index from "@/views/index.vue";
import ContestParams from "@/components/ContestParams.vue";

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
            name :'ContestParams',
            component : ContestParams,
        }
    ],
})

export default router
