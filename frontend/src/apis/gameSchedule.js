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
    const response = await fetch(`${BASE_URL}?gameId=${gameId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(
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
        ),
    });
    
    if (!response.ok) {
        throw new Error('Failed to update member');
    }

    const data = await response.json()
    return data

    } catch (error) {
        console.error(error)
        throw error
    }
}  
  


export default { findScheduledGamesByUserId, findGeneralGameSchedule, findCrewListByGameId, updateGameScheduleByGameId }