<template>
    <div class="schedule">
        <button @click="$router.push({name: 'schedule'})">Back</button>
        <h1>General Game Schedule</h1>

        <!-- Filter + Sort Controls -->
        <div class="controls">
        <input
            v-model="searchQuery"
            type="text"
            placeholder="Search opponent..."
        />
        
        <select v-model="sortOption">
            <option value="date">Sort by Date</option>
            <option value="opponent">Sort by Opponent</option>
            <option value="venue">Sort by Venue</option>
        </select>
        </div>
        <br />

        <div v-if="filteredAndSortedSchedule.length === 0">
            <p>No assigned games found.</p>
        6</div>

        <div v-if="schedule.length === 0">
            <p>You are not scheduled for any upcoming games.</p>
        </div>

        <div v-else>
         <div
            v-for="(game, index) in filteredAndSortedSchedule"
            :key="index"
            class="game-card">

        <h2>{{ game.sport }} vs {{ game.opponent }}</h2>
        <p><strong>Date:</strong> {{ game.gameDate }}</p>
        <p><strong>Time:</strong> {{ game.gameStart }}</p>
        <p><strong>Venue:</strong> {{ game.venue }}</p>

        <button @click="$router.push({name: 'crewListGame', params: { gameId: game.gameId } })">View Crew List</button>

      </div>
    </div>

    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import api from '@/apis/gameSchedule' 


const schedule = ref([])
const searchQuery = ref('')
const sortOption = ref('date')

onMounted(async () => {
  try {
    const games = await api.findGeneralGameSchedule()
    schedule.value = games
  } catch (error) {
    console.error('Failed to load general scheduled games info:', error)
  }
})

const filteredAndSortedSchedule = computed(() => {
  let filtered = schedule.value

  // Search by opponent
  if (searchQuery.value.trim() !== '') {
    filtered = filtered.filter(game =>
      game.opponent.toLowerCase().includes(searchQuery.value.trim().toLowerCase())
    )
  }

  // Sort
  if (sortOption.value === 'date') {
    filtered = filtered.slice().sort((a, b) => new Date(a.gameDate) - new Date(b.gameDate))
  } else if (sortOption.value === 'opponent') {
    filtered = filtered.slice().sort((a, b) => a.opponent.localeCompare(b.opponent))
  } else if (sortOption.value === 'venue') {
    filtered = filtered.slice().sort((a, b) => a.venue.localeCompare(b.venue))
  }

  return filtered
})

</script>

<style scoped>
.schedule {
  max-width: 800px;
  margin: 2rem auto;
  padding: 1rem;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

h1 {
  text-align: center;
  margin-bottom: 2rem;
  font-size: 2rem;
  color: #2c3e50;
}

.controls {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1.5rem;
  gap: 1rem;
}

.controls input, .controls select {
  padding: 0.5rem;
  font-size: 1rem;
  flex: 1;
}

.game-card {
  background-color: #f8f9fa;
  border-left: 6px solid #3498db;
  border-radius: 8px;
  padding: 1.5rem;
  margin-bottom: 1.5rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s;
}

.game-card:hover {
  transform: scale(1.01);
}

.game-card h2 {
  margin-bottom: 0.75rem;
  font-size: 1.25rem;
  color: #34495e;
}

.game-card p {
  margin: 0.3rem 0;
  font-size: 1rem;
  color: #555;
}

strong {
  color: #2c3e50;
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