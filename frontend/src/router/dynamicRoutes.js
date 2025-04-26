import Reports from "@/views/Reports.vue"
import Templates from "@/views/Templates.vue"
import ManageCrewMembers from "@/views/ManageCrewMembers.vue"
import InviteCrewMembers from "@/views/InviteCrewMembers.vue"

export const reportsRoute = {
    path: '/reports', name: 'reports', component: Reports, 
    meta: {
    title: 'Reports', isNavLink: true, roles: ['ADMIN']
}}   

export const templatesRoute = {
    path: '/templates', name: 'templates', component: Templates,
    meta: {
        title: 'Templates', isNavLink: true, roles: ['ADMIN']
    }
}

export const manageCrewMembersRoute = {
    path: '/manageCrewMembers', name: 'manageCrewMembers', component: ManageCrewMembers,
    meta: {
        title: 'Manage', isNavLink: false, roles: ['ADMIN']
    }
}


export const inviteCrewMembersRoute = {
    path: '/inviteCrewMembers', name: 'inviteCrewMembers', component: InviteCrewMembers,
    meta: {
        title: 'Invite', isNavLink: false, roles: ['ADMIN']
    }
}






   

