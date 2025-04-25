<template>
    <nav>
        <router-link v-for="route in navigableRoutes"
        :key="route.path"
        :to="route.path">
            {{ route.meta.title }}
        </router-link>
       
    </nav>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter();

const navigableRoutes = router.getRoutes()
.filter((route) => route.meta.isNavLink)


</script>

<style  scoped>
nav {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

nav a {
    text-decoration: none;
    color: black;
    font-size: 16px;
    font-family: sans-serif;

}

nav a:hover {
  background-color: #C89FE9;
  padding: 0.25rem 1rem;
  transform: scale(1.1);
  border-radius: 12px;
}



</style>