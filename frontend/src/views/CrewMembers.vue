<template>
    <div>
        <h1>Crew Members</h1>

        <!-- Show buttown only for ADMIN-->
         <div v-if="userRole === 'ADMIN' ">
            <button @click="$router.push({ name: 'manageCrewMembers' })">Manage</button>
            <button @click="$router.push({ name: 'inviteCrewMembers' })">Invite</button>
        </div>
        <!-- This is where Manage or Invite view will be shown -->
        <router-view />

    </div>
</template>

<script setup>
import { userRole } from '@/apis/auth';

</script>

<style scoped>

</style>