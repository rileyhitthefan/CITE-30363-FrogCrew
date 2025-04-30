const BASE_URL = 'http://localhost:3000/notification'

/**
 * POST /notifications
 * Not defined on API documentation but needed for multiple use cases
 * Use Case 9
 */
const submitNotification = async ({ userId, userIdCreatedNotification,  message, read, date }) => {   
    try {
        const response = await fetch(BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userId,
                userIdCreatedNotification,
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


/**
 * GET /notifications/{userId}
 * Return a list of notifications for a specific user
 * Use Case 9
 */
const getNotificationsById = async (userId) => {
    try {
        const response = await fetch(`${BASE_URL}?userId=${userId}`)
        
        if (!response.ok) {
            throw new Error(`Error fetching crew member: ${response.statusText}`)
        }
        const data = await response.json(); 
        return data;

    } catch (error) {
        console.error(error)
        throw error //Rethrow the error to be caught by the caller
    }
}


/**
 * PUT /notifications/{notificationId}
 * Mark a notification as read
 * Use Case 9
 */
const markNotificationAsRead = async (id, userId, userIdCreatedNotification, message, read, date) => {
    try {
        const response = await fetch(`${BASE_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userId,
                userIdCreatedNotification,
                message,
                read,
                date
            }),
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


export default { submitNotification, getNotificationsById, markNotificationAsRead }