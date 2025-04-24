<template>
    <div>
      <aside>
        <AdminSideNavigation></AdminSideNavigation>
      </aside>
      <main>
        <router-view></router-view>
      </main>
    </div>
  </template>
  
  <script setup>
import AdminSideNavigation from './AdminSideNavigation.vue';
  
  </script>
  
  <style scoped>
  
  </style>