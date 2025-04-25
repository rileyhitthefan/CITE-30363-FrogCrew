<template>
    <nav>
       <button>Notifications</button>
       <button>My Account</button>
       <button @click="handleLogout" v-if="isAuthenticated">Log Out</button>
    </nav>
</template>

<script setup>
import { isAuthenticated, logout } from '@/apis/auth';
import { useRouter } from 'vue-router';

const router = useRouter()

const handleLogout = () => {
    //Remove dynamically added routes if any
    if (router.hasRoute('reports')) {
        router.removeRoute('reports')
    }

    if (router.hasRoute('templates')) {
        router.removeRoute('templates')
    }

    if (router.hasRoute('manageCrewMembers')) {
        router.removeRoute('manageCrewMembers')
    }

    if (router.hasRoute('inviteCrewMembers')) {
        router.removeRoute('inviteCrewMembers')
    }


    logout()
    router.push('/login')
}

</script>

<style  scoped>

</style>