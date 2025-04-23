<template>
  <div>
    <aside>
      <SideNavigation></SideNavigation>
    </aside>
    <main>
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup>
import SideNavigation from '@/components/SideNavigation.vue';


</script>

<style scoped>

</style>