<template>
  <button @click="$router.back()">Back</button>
  <h2>Submit Availability</h2>

  <div v-if="filteredGames.length === 0">
    <p>No more games to submit availability for.</p>
  </div>

  <div v-else>
    <div v-for="game in filteredGames" :key="game.scheduleId" class="game-card">
      <p><strong>{{ game.sport }} vs {{ game.opponent }}</strong></p>
      <p>{{ game.gameDate }} at {{ game.gameStart }}</p>

      <label>
        Available:
        <select v-model="availabilitySelections[game.scheduleId].available" required>
          <option value="">Select</option>
          <option :value="true">Yes</option>
          <option :value="false">No</option>
        </select>      
        </label>

      <label>
        Comment:
        <input
          type="text"
          v-model="availabilitySelections[game.scheduleId].comment"
        />
      </label>

      <button @click="submit(game.scheduleId)">Submit</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getUserId } from '@/apis/auth'
import gameScheduleApi from '@/apis/gameSchedule'
import availabilityApi from '@/apis/availability'

const router = useRouter()
const currentUserId = getUserId()

const allGames = ref([])
const existingAvailability = ref([])
const filteredGames = ref([])
const availabilitySelections = ref({})

onMounted(async () => {
  try {
    allGames.value = await gameScheduleApi.findGeneralGameSchedule()
    existingAvailability.value = await availabilityApi.getAllAvailability()

    const currentUserIdValue = currentUserId.id; // Assuming currentUserId is an object with a property 'id'


    // Create a set of scheduleIds where the current user has already submitted availability
    const submittedGameIds = new Set(
      existingAvailability.value
        .filter(entry => String(entry.userId) === String(currentUserIdValue)) // Ensure matching type and value
        .map(entry => entry.scheduleId)
    );


    // Filter only games the user has NOT submitted for
    filteredGames.value = allGames.value.filter(
    game => !submittedGameIds.has(String(game.scheduleId))
    )


    // Initialize availability selections using scheduleId as key
    filteredGames.value.forEach(game => {
      availabilitySelections.value[game.scheduleId] = {
        available: '',
        comment: ''
      }
    })
  } catch (err) {
    console.error('Failed to fetch data:', err)
  }
})

const submit = async (scheduleId) => {
  const { available, comment } = availabilitySelections.value[scheduleId]

  if (available === "") {
    alert('Please specify availability.')
    return
  }

  try {
    await availabilityApi.submitAvailability({
      userId: currentUserId.id,
      scheduleId,
      availability: available,
      comment
    })


    alert('Availability submitted!')
    // Remove the game from the list after submission
    filteredGames.value = filteredGames.value.filter(game => game.scheduleId !== scheduleId)
  } catch (err) {
    console.error('Submission error:', err)
    alert('Failed to submit availability.')
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