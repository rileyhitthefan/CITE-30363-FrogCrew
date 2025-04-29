<template>
    <h2>Schedule</h2>
    <div>
        <button @click="$router.push({name: 'userGameSchedule'})">My Game Schedule</button>
        <button @click="$router.push({name: 'generalGameSchedule'})">General Game Schedule</button>

    </div>
</template>

<script setup>

</script>

<style scoped>
h2 {
  text-align: center;
  font-size: 2rem;
  margin-top: 2rem;
  color: #2c3e50;
}

div {
  display: flex;
  justify-content: center;
  gap: 1.5rem;
  margin-top: 2rem;
  flex-wrap: wrap;
}

button {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s ease, transform 0.1s ease;

  &:hover {
    background-color: #2980b9;
    transform: scale(1.03);
  }
}
</style>