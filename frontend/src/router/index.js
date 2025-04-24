import { createRouter, createWebHashHistory } from "vue-router"

//Import components we want to route to
import Homepage from "@/views/Homepage.vue"
import CrewMembers from "@/views/CrewMembers.vue"
import ManageCrewMembers from "@/views/ManageCrewMembers.vue"
import InviteCrewMembers from "@/views/InviteCrewMembers.vue"
import Schedule from "@/views/Schedule.vue"
import Reports from "@/views/Reports.vue"
import LogIn from "@/views/LogIn.vue"
import MainLayout from "@/Layouts/MainLayout.vue"
import { isAuthenticated } from "@/apis/auth"

//Create a router instance
const router = createRouter({
    //Provide the history implementation to use
    history: createWebHashHistory(),
    //Define some routes, each route record should map to a componenet
    routes: [
        {path: '/', name: 'mainLayout', component: MainLayout, redirect: {name: 'homepage'},
            children: [
                {path:'/', name:'homepage', component: Homepage},
                {path: '/crewMember', name: 'crewMembers', component: CrewMembers,
                    children: [
                    {path: 'manage', name: 'manageCrewMembers', component: ManageCrewMembers},
                    {path: 'invite', name: 'inviteCrewMembers', component: InviteCrewMembers},
                    ]
                },
                {path: '/schedule', name: 'schedule', component: Schedule},
                {path: '/reports', name: 'reports', component: Reports},
    
            ]
        },

        {path:'/login', name: 'login', component: LogIn },
    
    ],
})

//Global navigation guard
//If it is not authenticated, prompt the user to login
router.beforeEach((to, from) => {
    console.log(from.name, '->', to.name)
    if(to.name == 'homepage' && !isAuthenticated.value) {
        return '/login'
    }
})

//If the user is already logged in do not allow it to go back to the login page
router.beforeEach((to, from) => {
    console.log(from.name, '->', to.name)
    if(to.name == 'login' && isAuthenticated.value) {
        return false
    }
})




//Export the router instance
export default router