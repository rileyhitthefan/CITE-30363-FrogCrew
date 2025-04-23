import { createRouter, createWebHashHistory } from "vue-router"

//Import components we want to route to
import Homepage from "@/views/Homepage.vue"
import CrewMembers from "@/views/CrewMembers.vue"

//Create a router instance
const router = createRouter({
    //Provide the history implementation to use
    history: createWebHashHistory(),
    //Define some routes, each route record should map to a componenet
    routes: [
        {path:'/', name:'homepage', component: Homepage},
        {path: '/crewMember', name: 'crewMembers', component: CrewMembers},
    ],
})

//Export the router instance
export default router