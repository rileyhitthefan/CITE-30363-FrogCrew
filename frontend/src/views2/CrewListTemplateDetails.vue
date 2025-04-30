<template>
   <div class="game-details">
    <button @click="$router.back()">Back</button>

    <div v-if="loading">Loading game details...</div>
    <div v-else-if="error">Error: {{ error }}</div>
    <div v-else>
      <h2>Crew List Template Details</h2>
      <p><strong>Sport:</strong> {{ game.sport }}</p>
      <p><strong>Opponent:</strong> {{ game.opponent }}</p>
      <p><strong>Season:</strong> {{ game.season }}</p>
      <p><strong>Venue:</strong> {{ game.venue }}</p>
      <p><strong>Start Time:</strong> {{ game.gameStart }}</p>

      <h3 style="margin-top: 2rem;">Crew List</h3>
      <table>
        <thead>
          <tr>
            <th>Full Name</th>
            <th>Position</th>
            <th>Report Time</th>
            <th>Report Location</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="member in game.crewedMembers" :key="member.id">
            <td>{{ member.fullName }}</td>
            <td>{{ member.Position }}</td>
            <td>{{ member.ReportTime }}</td>
            <td>{{ member.ReportLocation }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import api from '@/apis/gameSchedule'

const route = useRoute()
const gameId = route.params.gameId
const game = ref(null)
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    const allGames = await api.findGeneralGameSchedule()
    const selectedGame = allGames.find(g => g.id === gameId || g.id === parseInt(gameId))
    if (!selectedGame) {
      throw new Error('Game not found')
    }
    game.value = selectedGame
  } catch (err) {
    error.value = err.message || 'Failed to load game details'
    console.error(err)
  } finally {
    loading.value = false
  }
})

</script>

<style scoped>
.game-details {
  padding: 2rem;
  font-family: Arial, sans-serif;
  background-color: #fdfdfd;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
}

table {
  width: 100%;
  margin-top: 1.5rem;
  border-collapse: collapse;
}

th, td {
  padding: 0.75rem;
  border: 1px solid #ddd;
  text-align: left;
}

th {
  background-color: #f0f0f0;
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