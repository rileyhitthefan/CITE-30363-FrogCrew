const BASE_URL = 'http://localhost:3000/availability'

/**
 * POST /availability
 * Crew memeber adds new availability
 * Use case 7
 */
const submitAvailability = async ({ userId, scheduleId, availability, comment }) => {
    try {
        const response = await fetch(BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userId,
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

/**
 * Not defined like this in documentation (talk about it with backend)
 */
const getAllAvailability = async () => {
    const res = await fetch(BASE_URL)
    return await res.json()
  }


/**
 * PUT /availability
 * Update availability
 * Use Case 8
 */
const updateAvailability = async ({ userId, scheduleId, availability, comment }) => {
    try {
        // Step 1: GET the existing record
    const getResponse = await fetch(`${BASE_URL}?userId=${userId}&scheduleId=${scheduleId}`);
    if (!getResponse.ok) {
      throw new Error('Failed to fetch availability');
    }

    const data = await getResponse.json();
    if (data.length === 0) {
      throw new Error('Availability record not found');
    }

    const existingRecord = data[0];
    const availabilityId = existingRecord.id;

    // Step 2: PUT to the specific record's endpoint
    const putResponse = await fetch(`${BASE_URL}/${availabilityId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        ...existingRecord, // Include original userId and scheduleId
        availability,
        comment
      })
    });

        if (!putResponse.ok) {
            throw new Error('Failed to submit availability');
          }
      
          return await putResponse.json();

    } catch (error) {
        console.error(error)
        throw error //Rethrow the error to be caught by the caller
    }
}


export default {submitAvailability, getAllAvailability, updateAvailability}