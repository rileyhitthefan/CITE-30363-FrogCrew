<template>
    <div class="homepage">
        <div class="background"></div>
        <h1> Homepage</h1>
    </div>
</template>

<script setup>

</script>

<style scoped>
.homepage {
  position: relative;
  width: 100%;
  height: 35vh; /* Full screen height */
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
  filter: blur(2px); /* <-- THIS BLURS IT */
  z-index: 1;
}

/* Make sure text is ABOVE the background */
h1 {
  position: relative;
  z-index: 2;
  color: white;
  font-size: 48px;
  text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.7);
}

</style>