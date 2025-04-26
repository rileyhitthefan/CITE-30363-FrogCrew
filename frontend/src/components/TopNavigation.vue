<template>
    <nav>
       <router-link :to="{name: 'notifications'}">Notifications</router-link>
       <router-link :to="{name: 'account'}">Account</router-link>
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
nav {
  display: flex;
  justify-content: flex-end;
  width: 100%;
  align-items: center;
  background-color: #C89FE9;
  padding: 0.75rem 1.5rem;
  gap: 1rem;
}

nav button {
  background-color: transparent;
  color: white;
  border: 2px solid transparent;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
  transition: all 0.3s ease;
  font-family: sans-serif;

}

nav button:hover {
  background-color: #9B30E2;
  border-color: white;
  transform: scale(1.05);
}

nav a {
  background-color: transparent;
  color: white;
  border: 2px solid transparent;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
  transition: all 0.3s ease;
  text-decoration: none;
  font-family: sans-serif;
}

nav a:hover {
  background-color: #9B30E2;
  border-color: white;
  transform: scale(1.05);
}


</style>