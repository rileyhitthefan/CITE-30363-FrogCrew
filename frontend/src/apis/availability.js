const BASE_URL = 'http://localhost:3000/availability'

/**
 * POST /availability
 * Crew memeber adds new availability
 * Use case 7
 */
const submitAvailability = async ({ id, scheduleId, availability, comment }) => {
    try {
        const response = await fetch(BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id,
                scheduleId,
                availability,
                comment
            })
        })
        if (!response.ok) {
            throw new Error('Failed to submit availability');
          }
      
          return await response.json();

    } catch (error) {
        console.error(error)
        throw error //Rethrow the error to be caught by the caller
    }
}


const getAllAvailability = async () => {
    const res = await fetch(BASE_URL)
    return await res.json()
  }




export default {submitAvailability, getAllAvailability}