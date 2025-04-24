<template>
    <div>
      <aside>
        <UserSideNavigation></UserSideNavigation>
      </aside>
      <main>
        <router-view></router-view>
      </main>
    </div>
  </template>
  
  <script setup>
  import UserSideNavigation from '@/components/UserSideNavigation.vue';  
  
  </script>
  
  <style scoped>
  
  </style>