<template>
    <div class="schedule">
        <h1>My Game Schedule</h1>

        <div v-if="schedule.length === 0">
            <p>No assigned games found.</p>
        </div>

        <div v-else>
         <div
            v-for="(game, index) in schedule"
            :key="index"
            class="game-card">

        <h2>{{ game.sport }} vs {{ game.opponent }}</h2>
        <p><strong>Date:</strong> {{ game.gameDate }}</p>
        <p><strong>Time:</strong> {{ game.gameStart }}</p>
        <p><strong>Venue:</strong> {{ game.venue }}</p>

        <!-- Show user's specific assignment -->
        <div v-if="game.myAssignment">
          <p><strong>Role:</strong> {{ game.myAssignment.Position }}</p>
          <p><strong>Report Time:</strong> {{ game.myAssignment.ReportTime }}</p>
          <p><strong>Report Location:</strong> {{ game.myAssignment.ReportLocation }}</p>
        </div>
      </div>
    </div>

    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/apis/gameschedule' 

const userId = 1 // Or dynamically get it from auth
const schedule = ref([])

onMounted(async () => {
  try {
    const games = await api.findScheduledGamesByUserId(userId)

    // Add user's assignment details into each game object
    schedule.value = games.map(game => {
      const myAssignment = game.crewedMembers.find(
        member => String(member.crewedUserId) === String(userId)
      )
      return {
        ...game,
        myAssignment
      }
    })
  } catch (error) {
    console.error('Failed to load schedule', error)
  }
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


</style>