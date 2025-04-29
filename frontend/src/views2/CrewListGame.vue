<template>
    <div class="crew-list">
    <button @click="$router.back()">Back</button>
    <h1>Crew List for Game vs {{ crewData?.opponent || '...' }}</h1>

    <!-- Loading Indicator -->
    <div v-if="loading" class="loading">Loading...</div>

    <!-- Error Handling -->
    <div v-else-if="error" class="error">{{ error }}</div>

    <!-- Crew Data -->
    <div v-else>
        <p v-if="crewData">
        <strong>Sport:</strong> {{ crewData.sport }}
      </p>
      <p v-if="crewData">
        <strong>Date:</strong> {{ crewData.gameDate }}
      </p>
      <p v-if="crewData">
        <strong>Time:</strong> {{ crewData.gameStart }}
      </p>
      <p v-if="crewData">
        <strong>Venue:</strong> {{ crewData.venue }}
      </p>

      <h2 v-if="crewData">Crew Members</h2>
      <ul v-if="crewData">
        <li v-for="member in crewData.crewedMembers" :key="member.crewedUserId">
          <strong>{{ member.Position }}:</strong> {{ member.fullName }}<br />
          <em>Report at {{ member.ReportTime }} to {{ member.ReportLocation }}</em>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import api from '@/apis/gameSchedule' 

const route = useRoute()
const gameId = route.params.gameId

const crewData = ref(null)
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    const response = await api.findCrewListByGameId(gameId)
    console.log('API Response:', response)  // Check the structure of the response
  
    crewData.value = response[0]
  } catch (err) {
    console.error('Failed to load crew list for game.value:', error)
} finally {
    // Ensure loading is set to false once the request is complete
    loading.value = false
}

})



</script>

<style  scoped>
.crew-list {
  max-width: 700px;
  margin: 2rem auto;
  font-family: 'Segoe UI', sans-serif;
  padding: 1rem;
}

h1 {
  font-size: 2rem;
  color: #2c3e50;
}

h2 {
  margin-top: 2rem;
  font-size: 1.5rem;
  color: #34495e;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  background: #f4f4f4;
  border-left: 4px solid #3498db;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 5px;
}

button {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 0.6rem 1.2rem;
  font-size: 1rem;
  font-weight: 500;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.2s ease, transform 0.1s ease;
  margin-bottom: 1.5rem;
}

button:hover {
  background-color: #2980b9;
  transform: scale(1.02);
}

</style>