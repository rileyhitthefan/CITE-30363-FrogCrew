<template>
    <button @click="$router.back()">Back</button>
    <h2>Update Availability</h2>
    <div v-if="submittedGames.length === 0">
    <p>No submitted availabilities found.</p>
  </div>

  <div v-for="(game, index) in submittedGames" :key="game.scheduleId" class="game-card">
  <p><strong>{{ game.sport }} vs {{ game.opponent }}</strong></p>
  <p>{{ game.gameDate }} at {{ game.gameStart }}</p>

  <div v-if="!game.editing">
    <p><strong>Available:</strong> {{ game.availability ? 'Yes' : 'No' }}</p>
    <p><strong>Comment:</strong> {{ game.comment || 'None' }}</p>
    <button @click="editGame(index)">Edit</button>
  </div>

  <div v-else>
    <label>
      <strong>Available:</strong>
      <select v-model="game.tempAvailability" required>
        <option :value="true">Yes</option>
        <option :value="false">No</option>
      </select>
    </label>
    <label>
      <strong>Comment:</strong>
      <input v-model="game.tempComment" />
    </label>
    <button @click="confirmEdit(index)">Confirm</button>
    <button @click="cancelEdit(index)">Cancel</button>
  </div>
</div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUserId } from '@/apis/auth'
import gameScheduleApi from '@/apis/gameSchedule'
import availabilityApi from '@/apis/availability'

const currentUserId = getUserId()
const submittedGames = ref([])

onMounted(async () => {
  try {
    const allGames = await gameScheduleApi.findGeneralGameSchedule()
    const allAvailability = await availabilityApi.getAllAvailability()

    // Only get availability entries for this user
    const userAvailability = allAvailability.filter(
      entry => String(entry.userId) === String(currentUserId.id)
    )

    // Get games where the user submitted availability
    submittedGames.value = userAvailability.map(entry => {
  const game = allGames.find(g => g.scheduleId === entry.scheduleId)
  return {
    ...game,
    userId: entry.userId,
    scheduleId: entry.scheduleId,
    availability: entry.availability,
    comment: entry.comment,
    editing: false,
    tempAvailability: entry.availability,
    tempComment: entry.comment
  }
})

  } catch (error) {
    console.error('Failed to load submitted games:', error)
  }
})

const editGame = (index) => {
  submittedGames.value[index].editing = true
}

const cancelEdit = (index) => {
  const game = submittedGames.value[index]
  game.tempAvailability = game.availability
  game.tempComment = game.comment
  game.editing = false
}

const confirmEdit = async (index) => {
  const game = submittedGames.value[index]

  // Simple validation
  if (game.tempAvailability === null || game.tempAvailability === undefined) {
    alert('Please select availability.')
    return
  }

  try {
    await availabilityApi.updateAvailability({
      userId: game.userId,
      scheduleId: game.scheduleId,
      availability: game.tempAvailability,
      comment: game.tempComment
    })


    // Apply the changes
    game.availability = game.tempAvailability
    game.comment = game.tempComment
    game.editing = false
    alert('Availability updated successfully.')
  } catch (error) {
    alert('Failed to update availability.')
    console.error(error)
  }
}


</script>

<style scoped>
h2 {
  text-align: center;
  font-size: 2rem;
  margin-top: 2rem;
  color: #2c3e50;
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
  margin-top: 1rem;
}

button:hover {
  background-color: #2980b9;
  transform: scale(1.03);
}

.game-card {
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 12px;
  padding: 1.5rem;
  width: 300px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.game-card p {
  margin: 0.25rem 0;
  font-size: 1rem;
}

label {
  display: flex;
  flex-direction: column;
  font-weight: 500;
  font-size: 0.95rem;
}

input[type="text"] {
  margin-top: 0.25rem;
  padding: 0.5rem;
  border-radius: 6px;
  border: 1px solid #ccc;
}

input[type="checkbox"] {
  margin-top: 0.5rem;
  transform: scale(1.2);
}

div {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 2rem;
  margin: 2rem auto;
  max-width: 1000px;
}

@media (max-width: 600px) {
  .game-card {
    width: 90%;
  }

  h2 {
    font-size: 1.5rem;
  }
}


</style>