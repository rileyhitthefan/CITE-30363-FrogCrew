<template>
   <div class="crew-list-page">
    <button @click="$router.back()">Back</button>

    <h2>Crew List Templates</h2>

    <div v-if="loading" class="status">Loading crew lists...</div>
    <div v-else-if="error" class="status error">Error: {{ error }}</div>
    <div v-else>
      <div v-for="game in games" :key="game.id" class="game-card">
        <h3>{{ game.sport }} – {{ game.opponent }} ({{ game.gameDate }})</h3>
        <button @click="$router.push({ name: 'crewListTemplateDetails', params: { gameId: game.id } })">
  More
</button>


        <table>
          <thead>
            <tr>
              <th>Full Name</th>
              <th>Position</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="member in game.crewedMembers" :key="member.id">
              <td>{{ member.fullName }}</td>
              <td>{{ member.Position }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/apis/gameSchedule'

const games = ref([])
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    const data = await api.findGeneralGameSchedule()
    games.value = data
  } catch (err) {
    error.value = err.message || 'Failed to fetch game schedules.'
    console.error(err)
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.crew-list-page {
  padding: 2rem;
  font-family: Arial, sans-serif;

  h2 {
    font-size: 2rem;
    margin-bottom: 1.5rem;
  }

  .status {
    font-size: 1.2rem;
    color: #555;

    &.error {
      color: red;
    }
  }

  .game-card {
    background: #f9f9f9;
    padding: 1rem 1.5rem;
    margin-bottom: 2rem;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);

    h3 {
      margin-bottom: 0.5rem;
      color: #333;
    }

    p {
      margin: 0.2rem 0;
    }

    table {
      width: 100%;
      margin-top: 1rem;
      border-collapse: collapse;

      th,
      td {
        border: 1px solid #ddd;
        padding: 0.5rem;
        text-align: left;
      }

      th {
        background-color: #eee;
      }
    }
  }
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