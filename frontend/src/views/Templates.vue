<template>
    <div>
        <h2>Templates</h2>

        <button @click="$router.push({ name: 'crewListTemplates' })">View Crew List Templates</button>

    </div>
</template>

<script setup>

</script>

<style  scoped>
div {
  padding: 2rem;
  font-family: Arial, sans-serif;
  text-align: center;
}

h2 {
  font-size: 1.8rem;
  margin-bottom: 1.5rem;
  color: #333;
}

button {
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}

</style>