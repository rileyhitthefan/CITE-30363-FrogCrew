const BASE_URL = 'http://localhost:3000/gamesSchedule'


/**
 * GET /scheduledGames/get/{userId}
 * Find all scheduled games of a user by UserId
 * Use Case 4
 
 */
const findScheduledGamesByUserId = async (id) => {
    try {
      const response = await fetch(BASE_URL)
  
      if (!response.ok) {
        throw new Error(`Error fetching games: ${response.statusText}`)
      }
  
      const games = await response.json()
  
      // Filter games where user is part of crewedMembers
      const assignedGames = games.filter(game =>
        game.crewedMembers.some(member => String(member.crewedUserId) === String(id))
      )
  
      return assignedGames
    } catch (error) {
      console.error(error)
      throw error
    }
  }


 /**
  * GET /gameSchedule/games
  * Find all games
  * Use Case 5
  *  */ 
 const findGeneralGameSchedule = async () => {
  try {
    const response = await fetch(BASE_URL)

    if (!response.ok) {
      throw new Error(`Error fetching games: ${response.statusText}`)
    }

    return await response.json();
  } catch (error) {
    console.error(error)
    throw error
  }
}

/**
 * GET /crewList/{gameId}
 * Find Crew List by game Id
 * Use Case 6
 
 */
const findCrewListByGameId = async (gameId) => {
  try {
    const response = await fetch(`${BASE_URL}?gameId=${gameId}`);

    if (!response.ok) {
      throw new Error(`Error fetching game crew list: ${response.statusText}`)
    }

    return await response.json();

  } catch (error) {
    console.error(error)
    throw error
  }
}


/**
 * PUT
 * Update game schedule
 * Use Case 10
 */

const updateGameScheduleByGameId = async (gameId, scheduleId, sport, season, gameStart, gameDate, venue, opponent, isFinalized, crewedMembers) => {
  try {
    // Step 1: GET the existing game by gameId
    const getResponse = await fetch(`${BASE_URL}?gameId=${gameId}`);
    if (!getResponse.ok) throw new Error('Failed to fetch game schedule');
    
    const data = await getResponse.json();
    if (data.length === 0) throw new Error('Game schedule not found');
    
    const existingGame = data[0];
    const gameRecordId = existingGame.id;

    // Step 2: PUT to /gamesSchedule/:id with updated values
    const putResponse = await fetch(`${BASE_URL}/${gameRecordId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          gameId, 
          scheduleId, 
          sport, 
          season, 
          gameStart, 
          gameDate, 
          venue, 
          opponent, 
          isFinalized, 
          crewedMembers
    }),
    });
    
    if (!putResponse.ok) {
        throw new Error('Failed to update member');
    }

    const updatedData = await putResponse.json()
    return updatedData

    } catch (error) {
        console.error(error)
        throw error
    }
}  
  


export default { findScheduledGamesByUserId, findGeneralGameSchedule, findCrewListByGameId, updateGameScheduleByGameId }