const BASE_URL = 'http://localhost:3000/notification'

/**
 * POST /notifications
 * Not defined on API documentation but needed for multiple use cases
 * Use Case 9
 */
const submitNotification = async ({ userId, message, read, date }) => {   
    try {
        const response = await fetch(BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userId,
                message,
                read,
                date
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