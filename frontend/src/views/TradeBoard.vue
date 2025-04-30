<template>
    <div class="trade-board">
    <h1>Available Shifts</h1>

    <div v-if="loading">Loading...</div>

    <div v-else>
      <div
        v-for="(game, index) in enrichedTradeBoard"
        :key="index"
        class="trade-card"
      >
        <h2>{{ game.sport }} vs {{ game.opponent }}</h2>
        <p><strong>Date:</strong> {{ game.gameDate }}</p>
        <p><strong>Time:</strong> {{ game.gameStart }}</p>
        <p><strong>Venue:</strong> {{ game.venue }}</p>
        <p><strong>Role Needed:</strong> {{ game.position }}</p>
        <button @click="pickUpShift(game)" class="request-btn">Pick Up Shift</button>

    </div>

      <div v-if="enrichedTradeBoard.length === 0">
        <p>No shifts are currently available for trade.</p>
      </div>
    </div>
  </div>
        
</template>

<script setup>
import { ref, onMounted } from 'vue'
import tradeBoardApi from '@/apis/scheduledGames'
import gameApi from '@/apis/gameSchedule'
import { useRouter } from 'vue-router'
import { getUserId, getUserFullName } from '@/apis/auth';
import crewMemberApi from '@/apis/crewMembers'
import notificationApi from '@/apis/notifications'


const currentUserId = getUserId()
const currentUserFullName = getUserFullName()

const loading = ref(true)
const enrichedTradeBoard = ref([])

const router = useRouter()

//Find all shifts for the trade board (shifts where coverage has been requested)
onMounted(async () => {
  try {
    const scheduledGames = await tradeBoardApi.findAllTradeBoard()
    const allGames = await gameApi.findGeneralGameSchedule()

    enrichedTradeBoard.value = scheduledGames
      .filter(game => game.status === 'AVAILABLE')
      .map(trade => {
        const gameDetails = allGames.find(g => g.gameId === trade.gameId)
        const crewMatch = gameDetails?.crewedMembers.find(
          cm => cm.crewedUserId === trade.dropperId && cm.Position === trade.position
        )

        return {
          ...trade,
          gameDate: gameDetails?.gameDate ?? 'Unknown',
          gameStart: gameDetails?.gameStart ?? 'Unknown',
          venue: gameDetails?.venue ?? 'Unknown',
          sport: gameDetails?.sport ?? 'Unknown',
          opponent: gameDetails?.opponent ?? 'Unknown',

        }
      })
  } catch (err) {
    console.error('Error loading trade board or game schedule:', err)
  } finally {
    loading.value = false
  }
})

// Function to get crew member position using crewMemberApi
async function getCrewMemberPosition(currentUserId) {
  try {

    // Access the actual value of the ref to get the userId
    const userId = currentUserId.id;

    console.log("Fetching crew member for userId:", currentUserId);
    
    const data = await crewMemberApi.findMemberById(userId);
    console.log("Crew member API response:", data);

    if (!data || data.length === 0) {
      console.warn("No crew member found for userId:", currentUserId);
      return null;
    }

    return data.position;
  } catch (err) {
    console.error("Error fetching crew member data:", err);
    return null;
  }
}

// Function to handle picking up a shift
async function pickUpShift(game) {
  try {
    // Get the current user's crew position
    const userPosition = await getCrewMemberPosition(currentUserId)

    if (!userPosition) {
      alert("Error fetching your position.")
      return
    }

     // Check if the user's position matches any of the positions needed for the game
     if (!userPosition.includes(game.position)) {
      alert("You're not eligible to pick up this shift (position mismatch).")
      return
    }

    // Create the new crew member object to add to the crewedMembers array
    const crewMemberB = {
      crewedUserId: currentUserId,
      id: currentUserId, 
      gameId: game.gameId,
      Position: game.position,
      fullName: currentUserFullName, 
      ReportTime: game.reportTime,
      ReportLocation: game.reportLocation,
    }

    // Ensure crewedMembers is an array and initialize it if necessary
    const crewedMembers = Array.isArray(game.crewedMembers) ? game.crewedMembers : [];


    // Add Crew Member B to the crewedMembers array
    crewedMembers.push(crewMemberB);

    // Update the game schedule by adding Crew Member B and removing the shift from available list
    const updatedGameSchedule = await gameApi.updateGameScheduleByGameId(
      game.gameId,
      game.scheduleId,
      game.sport,
      game.season,
      game.gameStart,
      game.gameDate,
      game.venue,
      game.opponent,
      game.isFinalized,
      crewedMembers
    )

    const dateNow = new Date().toISOString()

    // Notify Crew Member A (dropperId)
    await notificationApi.submitNotification({
      userId: game.dropperId,
      userIdCreatedNotification: currentUserId,
      message: `${currentUserFullName} has picked up your shift as ${game.position} for ${game.sport} vs ${game.opponent} on ${game.gameDate}.`,
      read: false,
      date: dateNow
    })

    // Notify Crew Member B
    await notificationApi.submitNotification({
      userId: currentUserId,
      userIdCreatedNotification: currentUserId,
      message: `You have successfully picked up a shift as ${game.position} for ${game.sport} vs ${game.opponent} on ${game.gameDate}.`,
      read: false,
      date: dateNow
    })
 
    // Successfully picked up the shift
    alert("Shift successfully picked up!")

     // Step 3: DELETE the shift from the trade board (this will remove the shift from the available shifts)
     const tradeId = `${game.gameId}-${game.dropperId}`;
     console.log("Deleting trade post with tradeId:", tradeId);

    await tradeBoardApi.deleteTradeBoardPost(tradeId);

    console.log('Trade post successfully deleted from the board.');

    router.push({name: 'notifications'})

  } catch (err) {
    console.error('Error processing the shift:', err)
    alert('There was a problem processing the shift.')
  }
}




</script>

<style scoped>
.trade-board {
  padding: 2rem;
  max-width: 800px;
  margin: 0 auto;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.trade-board h1 {
  text-align: center;
  margin-bottom: 2rem;
  color: #2c3e50;
}

.trade-card {
  background-color: #ffffff;
  border-left: 6px solid #3498db;
  padding: 1.5rem;
  margin-bottom: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.trade-card h2 {
  margin-top: 0;
  color: #2c3e50;
}

.trade-card p {
  margin: 0.5rem 0;
  font-size: 0.95rem;
  color: #34495e;
}

.trade-card strong {
  color: #000;
}

.request-btn {
  margin-top: 1rem;
  padding: 0.5rem 1rem;
  background-color: #27ae60;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

.request-btn:hover {
  background-color: #219150;
}

</style>