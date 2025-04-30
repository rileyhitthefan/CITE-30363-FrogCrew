<template>
    <h2>Schedule</h2>
    <div>
      <button @click="$router.push({name: 'userGameSchedule'})">My Game Schedule</button>
      <button @click="$router.push({name: 'generalGameSchedule'})">General Game Schedule</button>
      <button @click="$router.push({name: 'availability'})">Availability</button>

      <!-- This button will now be inside the same flex context -->
      <button
        v-if="userRole === 'ADMIN'"
        class="purple-button"
        @click="$router.push({ name: 'createGameSchedule' })"
      >
        Create Game Schedule
      </button>
    </div>
</template>

<script setup>
import { userRole } from '@/apis/auth';


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

.purple-button {
  background-color: #9b59b6; /* Purple */
}

.purple-button:hover {
  background-color: #8e44ad; /* Darker purple */
}

</style>