const BASE_URL = 'http://localhost:3000/scheduledGames'

/**
 * POST /scheduledGames/drop
 * Add shift to trade board
 */


const addShiftToTradeBoard = async ({ tradeId, dropperId, gameId, position, status, receiverId }) => {   
    try {
        const response = await fetch(BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                tradeId,
                dropperId,
                gameId,
                position,
                status,
                receiverId
            })
        })
        if (!response.ok) {
            throw new Error('Failed to submit notification');
          }
      
          return await response.json();

    } catch (error) {
        console.error(error)
        throw error //Rethrow the error to be caught by the caller
    }
}

/**
 * GET /scheduledGames/tradeboard
 * Return a list of all entries on the tradeboard
 * Use case 10
 */

const findAllTradeBoard = async () => {
    try {
        const response = await fetch(BASE_URL)
        if (!response.ok) {
            throw new Error(`Error fetching crew members: ${response.statusText}`)
        }
        return await response.json();
    } catch (error) {
        console.error(error)
        throw error //Rethrow the error to be caught by the caller
    }
}



export default { addShiftToTradeBoard, findAllTradeBoard}