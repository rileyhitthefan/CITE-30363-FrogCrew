import { createRouter, createWebHashHistory } from "vue-router"

//Import components we want to route to
import Homepage from "@/views/Homepage.vue"
import CrewMembers from "@/views/CrewMembers.vue"
import Schedule from "@/views/Schedule.vue"
import LogIn from "@/views/LogIn.vue"
import MainLayout from "@/Layouts/MainLayout.vue"
import { isAuthenticated } from "@/apis/auth"
import Notifications from "@/views/Notifications.vue"
import Account from "@/views/Account.vue"
import ChangeAccountDetails from "@/views2/ChangeAccountDetails.vue"
import ConfirmAccountChanges from "@/views2/ConfirmAccountChanges.vue"

//Create a router instance
const router = createRouter({
    //Provide the history implementation to use
    history: createWebHashHistory(),
    //Define some routes, each route record should map to a componenet
    routes: [
        {path: '/', name: 'mainLayout', component: MainLayout, redirect: {name: 'homepage'},
            children: [
                {path:'/', name:'homepage', component: Homepage, meta: { title: 'Homepage', isNavLink: true}},
                {path: '/crewMember', name: 'crewMembers', component: CrewMembers,  meta: { title: 'Crew Members', isNavLink: true},
                },
                {path: '/schedule', name: 'schedule', component: Schedule,  meta: { title: 'Schedule', isNavLink: true}}, 
                {path: '/notifications', name: 'notifications', component: Notifications},
                {path: '/account', name: 'account', component: Account},
                {path: '/account/changeAccountDetails/:id', name: 'changeAccountDetails', component: ChangeAccountDetails, props: true},
                {path: '/account/changeAccountDetails/:id/confirmAccountChanges', name: 'confirmAccountChanges', component: ConfirmAccountChanges}
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