<template>
    <div>
        <h1>Crew Members</h1>
        <button @click="$router.push({ name: 'manageCrewMembers' })">Manage</button>
        <button @click="$router.push({ name: 'inviteCrewMembers' })">Invite</button>

        <!-- This is where Manage or Invite view will be shown -->
        <router-view />

    </div>
</template>

<script setup>


</script>

<style scoped>

</style>