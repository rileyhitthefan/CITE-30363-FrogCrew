<template>
    <div class="schedule">
        <button @click="$router.push({name: 'schedule'})">Back</button>
        <h1>My Game Schedule</h1>

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

        <div v-if="schedule.length === 0">
          <p>You are not scheduled for any upcoming games.</p>
        </div>
        <div v-else-if="filteredAndSortedSchedule.length === 0">
          <p>No assigned games found.</p>
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

        <!-- Show user's specific assignment -->
        <div v-if="game.myAssignment">
            <p><strong>Role:</strong> {{ game.myAssignment.Position || 'N/A' }}</p>
            <p><strong>Report Time:</strong> {{ game.myAssignment.ReportTime || 'N/A' }}</p>
            <p><strong>Report Location:</strong> {{ game.myAssignment.ReportLocation || 'N/A' }}</p>
        </div>
        <br />

        <button @click="$router.push({name: 'crewListGame', params: { gameId: game.gameId } })">View Crew List</button>
        <button
            @click="requestCoverage(game)"
            :disabled="requestedTrades.has(`${game.gameId}-${getUserId().id}`)">
            Request Coverage
          </button>
      </div>
    </div>

    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import scheduleApi from '@/apis/gameSchedule' 
import { getUserId } from '@/apis/auth'
import { useRouter } from 'vue-router'
import notificationApi from '@/apis/notifications'
import crewApi from '@/apis/crewMembers'
import tradeBoardApi from '@/apis/scheduledGames'


const schedule = ref([])
const searchQuery = ref('')
const sortOption = ref('date')
const requestedTrades = ref(new Set())

const router = useRouter()

onMounted(async () => {
    try {
        const loggedInUser = getUserId()

        const games = await scheduleApi.findScheduledGamesByUserId(loggedInUser.id)
   
        // Attach myAssignment manually
        schedule.value = games.map(game => {
        const myAssignment = game.crewedMembers.find(
            member => String(member.crewedUserId) === String(loggedInUser.id)
        )
        return { ...game, myAssignment }
        })

        // Fetch all trade board entries
        const allTrades = await tradeBoardApi.findAllTradeBoard()

        // Track all trade board entries where the current user has already requested coverage
        allTrades.forEach(trade => {
          if (String(trade.dropperId) === String(loggedInUser.id)) {
            requestedTrades.value.add(`${trade.gameId}-${trade.dropperId}`)
          }


          console.log("Requested trades:", Array.from(requestedTrades.value)) // Debugging line

    })
   
    } catch (error) {
        console.error('Failed to load scheduled games info:', error)
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

//Request Coverage
const requestCoverage = async (game) => {
  const currentUser = getUserId()
  const date = new Date().toISOString()

  // Convert game start time to Date object
  const gameStartDate = new Date(game.gameDate + ' ' + game.gameStart)
  const currentTime = new Date()
  const timeDifference = gameStartDate - currentTime
  const hoursRemaining = timeDifference / (1000 * 60 * 60)

  if (hoursRemaining < 24) {
    alert('You cannot request coverage for a game that is within 24 hours of the start time.')
    return
  }

  try {
    const allCrew = await crewApi.findAllCrewMembers()

    const position = game.myAssignment.Position.toLowerCase()

    // Find eligible members (same position, not current user)
    const eligibleMembers = allCrew.filter(member => 
      // Normalize position comparison (lowercase and array check)
      member.position.some(p => p.toLowerCase() === position) &&
      String(member.id) !== String(currentUser.id)
    )


    // Notify each eligible crew member
    const notifications = eligibleMembers.map(member => {
      return notificationApi.submitNotification({
        userId: member.id,
        userIdCreatedNotification: currentUser.id,
        message: `A shift is available for ${game.sport} vs ${game.opponent} on ${game.gameDate} (${game.myAssignment.Position}).`,
        read: false,
        date,
      })
    })

    await Promise.all(notifications)

    //Notify requester
    await notificationApi.submitNotification({
      userId: currentUser.id,
      userIdCreatedNotification: currentUser.id,
      message: `You requested coverage for ${game.sport} vs ${game.opponent} on ${game.gameDate}.`,
      read: false,
      date,
    })

    // Add shift to trade board
    try {
      await tradeBoardApi.addShiftToTradeBoard({
        tradeId: `${game.gameId}-${currentUser.id}`,
        dropperId: currentUser.id,
        gameId: game.gameId,
        position: game.myAssignment.Position,
        status: 'pending',
        receiverId: null
      })
    } catch (err) {
      console.error('Failed to add shift to trade board:', err)
      alert('Coverage notifications sent, but failed to add to trade board.')
    }

    alert('Coverage requested successfully. All eligible crew members have been notified.')
  
    router.push({name: 'notifications'})
    
  } catch (error) {
    console.error('Coverage request failed:', error)
    alert('Failed to request coverage. Please try again.')
  }
}

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

button:disabled {
  background-color: #dcdcdc; /* Light gray background */
  color: #7f8c8d; /* Slightly darker gray for the text */
  cursor: not-allowed; /* Indicate the button is unclickable */
  transform: scale(1); /* Remove hover effect */
}



</style>