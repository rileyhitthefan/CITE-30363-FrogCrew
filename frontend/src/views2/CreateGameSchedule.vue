<template>
  <button @click="$router.push({name: 'schedule'})">Back</button>
   <div>
    <h2>Create Game Schedule</h2>
    
    <!-- Game Schedule Form -->
    <form @submit.prevent="submitSchedule">
      <div class="form-group">
        <label for="sport">Sport</label>
        <input type="text" v-model="schedule.sport" id="sport" required />
      </div>

      <div class="form-group">
        <label for="season">Season</label>
        <input type="text" v-model="schedule.season" id="season" required />
      </div>

      <div class="form-group">
        <label for="gameDate">Game Date</label>
        <input type="date" v-model="schedule.gameDate" id="gameDate" required />
      </div>

      <div class="form-group">
        <label for="gameStart">Game Start Time</label>
        <input type="time" v-model="schedule.gameStart" id="gameStart" required />
      </div>

      <div class="form-group">
        <label for="venue">Venue</label>
        <input type="text" v-model="schedule.venue" id="venue" required />
      </div>

      <div class="form-group">
        <label for="opponent">Opponent</label>
        <input type="text" v-model="schedule.opponent" id="opponent" required />
      </div>

      <!-- Crew Members Input Section -->
        <div class="crew-members">
        <h3>Crew Members</h3>
        <div v-for="(crew, index) in schedule.crewedMembers" :key="index" class="crew-member">
            <input type="text" v-model="crew.fullName" placeholder="Full Name" required />
            <input type="text" v-model="crew.position" placeholder="Position" required />
            <input type="time" v-model="crew.reportTime" placeholder="Report Time" required />
            <input type="text" v-model="crew.reportLocation" placeholder="Report Location" required />
            <button type="button" @click="removeCrewMember(index)" class="remove-btn">Remove</button>
        </div>
        <button type="button" @click="addCrewMember">Add Crew Member</button>
        </div>

      <div class="form-group">
        <button type="submit">Submit Schedule</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/apis/gameSchedule'

// Schedule data object
const schedule = ref({
  sport: '',
  season: '',
  gameDate: '',
  gameStart: '',
  venue: '',
  opponent: '',
  crewedMembers: [
    { fullName: '', position: '', reportTime: '', reportLocation: '' }
  ]
});

// Router instance for navigation
const router = useRouter()

// Method to add new crew member to the list
const addCrewMember = () => {
  schedule.value.crewedMembers.push({ fullName: '', position: '', reportTime: '', reportLocation: '' })
}

// Method to remove a crew member at a given index
const removeCrewMember = (index) => {
  if (schedule.value.crewedMembers.length > 1) {
    schedule.value.crewedMembers.splice(index, 1)
  } else {
    alert('At least one crew member is required.')
  }
}



// Submit method that calls the API
const submitSchedule = async () => {
  try {
    // Validate the data (you can add more specific checks here)
    if (!schedule.value.sport || !schedule.value.season || !schedule.value.gameDate || !schedule.value.gameStart || !schedule.value.venue || !schedule.value.opponent) {
      alert('Please fill in all required fields.')
      return
    }

    // Construct the schedule data
    const gameId = 'auto-generated-id'  // Example, you may want to generate this dynamically
    const scheduleId = 'auto-generated-id'  // Similarly, you might generate this on the backend or let the DB handle it
    const isFinalized = false  // Assuming a game is not finalized when creating the schedule

    // Call the provided API to create the game schedule
    const response = await api.createGameSchedule(
      gameId, 
      scheduleId, 
      schedule.value.sport, 
      schedule.value.season, 
      schedule.value.gameStart, 
      schedule.value.gameDate, 
      schedule.value.venue, 
      schedule.value.opponent, 
      isFinalized, 
      schedule.value.crewedMembers
    )

    console.log('API response:', response)

    
    if (response && response.id) {
        alert('Game Schedule Created Successfully')
        router.push({ name: 'generalGameSchedule' })
    } else {
        alert('Error creating game schedule')
        }

  } catch (error) {
    console.error('Error submitting schedule:', error)
    alert('There was an error while creating the schedule. Please try again.')
  }
}

</script>

<style scoped>
h2 {
  text-align: center;
  font-size: 2rem;
  margin-top: 20px;
  color: #333;
}

.form-group {
  margin: 10px 0;
}

input {
  padding: 10px;
  margin: 5px;
  width: 100%;
  max-width: 300px;
}

button {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
}

button:hover {
  background-color: #2980b9;
}

</style>