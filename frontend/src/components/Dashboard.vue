<template>
  <div class="dashboard-layout">
      <aside class="sidebar">
        <SideNavigation></SideNavigation>
      </aside>
      <div class="main-content">
        <header class="topnav">
          <TopNavigation></TopNavigation>
        </header>
        <main class="content">
        <router-view></router-view>
        </main>
      </div>
  </div>
</template>
  
<script setup>
import SideNavigation from './SideNavigation.vue';
import TopNavigation from './TopNavigation.vue';

  
</script>
  
<style scoped>
  .dashboard-layout {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 150px;
  background-color: #E2E0E0;
  color: white;
  padding: 1rem;
  box-shadow: 2px 0 5px rgba(0,0,0,0.1);
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.topnav {
  height: 60px;
  background-color: #C89FE9;
  color: white;
  display: flex;
  align-items: center;
  padding: 0 1rem;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.content {
  flex: 1;
  padding: 1.5rem;
  overflow-y: auto;
  background-color: #f4f4f4;
}
  
</style>