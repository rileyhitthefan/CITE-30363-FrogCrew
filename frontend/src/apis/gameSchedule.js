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


export default { findScheduledGamesByUserId, findGeneralGameSchedule }