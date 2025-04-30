<template>
   <div class="homepage">
    <div class="background"></div>
    <h1>Homepage</h1>
    <p class="welcome-message">Welcome back, {{ userName }}!</p>
  </div>
</template>

<script setup>
import { getUserFullName } from '@/apis/auth';

const userName = getUserFullName()

</script>

<style scoped>
.homepage {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('@/assets/img/Homepage1.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  filter: blur(2px);
  z-index: 1;
}

h1 {
  position: relative;
  z-index: 2;
  color: #ffffff;
  font-size: 72px;
  font-weight: 700;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  text-shadow: 4px 4px 12px rgba(0, 0, 0, 0.8);
  margin: 0;
  animation: fadeIn 1s ease-out;
}

.welcome-message {
  position: relative;
  z-index: 2;
  color: #f0f0f0;
  font-size: 40px;
  font-weight: 400;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  margin-top: 15px;
  text-shadow: 2px 2px 10px rgba(0, 0, 0, 0.6);
  animation: fadeIn 1.5s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(15px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

</style>