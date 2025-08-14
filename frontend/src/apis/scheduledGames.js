import { getApiUrl, getHeaders } from './config.js'

/**
 * POST /scheduledGames/drop
 * Add shift to trade board
 */


const addShiftToTradeBoard = async ({ tradeId, dropperId, gameId, position, status, receiverId }) => {   
    try {
        const response = await fetch(getApiUrl('/scheduledGames'), {
            method: 'POST',
            headers: getHeaders(),
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
        const response = await fetch(getApiUrl('/scheduledGames'))
        if (!response.ok) {
            throw new Error(`Error fetching crew members: ${response.statusText}`)
        }
        return await response.json();
    } catch (error) {
        console.error(error)
        throw error //Rethrow the error to be caught by the caller
    }
}


/**
 * DELETE 
 * Delete Trade Board post after someone has picked up the shift
 * Use Case 10
 */
const deleteTradeBoardPost = async (tradeId) => {
    try {
        // Step 1: GET the existing trade post by tradeId
        const getResponse = await fetch(getApiUrl(`/scheduledGames?tradeId=${tradeId}`));

        if (!getResponse.ok) throw new Error('Failed to fetch trade post');

        const data = await getResponse.json();
        if (data.length === 0) throw new Error('Trade post not found');

        console.log('GET response data:', data); // 🔍 Add this line

        const existingTradePost = data[0];
        const tradeRecordId = existingTradePost.id;

        // Step 2: DELETE the trade post using its ID
        const deleteResponse = await fetch(getApiUrl(`/scheduledGames/${tradeRecordId}`), {
            method: 'DELETE',
        });

        if (!deleteResponse.ok) {
            throw new Error(`Failed to delete trade post: ${deleteResponse.statusText}`);
        }

        return await deleteResponse.json();
    } catch (error) {
        console.error(error)
        throw error //Rethrow the error to be caught by the caller
    }
}



export default { addShiftToTradeBoard, findAllTradeBoard, deleteTradeBoardPost}