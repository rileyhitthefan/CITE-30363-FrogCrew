const BASE_URL = 'http://localhost:3000/crewMembers'

/**
 * Fetch all crew members from the API
 * @returns {Promise<Array>} A promise that resolves to an array of crew members
 */
const findAllCrewMembers = async () => {
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

/**
 * Fetch a single member by its UserId
 * @param {number} id The userID of the member to fetch
 * @returns {Promise<Object>} A promise that resolves to the member object
 */
const findMemberById = async (id) => {
    try {
        const response = await fetch(`${BASE_URL}/${id}`)
        
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
 * PUT /crewMember/{userId}
 * Updates user account information
 */
const updateMemberById = async (id, updatedData) => {
    try {
        const response = await fetch(`${BASE_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updatedData),
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

/**
 * POST /invite
 * Admin sends invite to new crew members by specifing their emails
 */
const inviteCrewMembers = async (emails) => {
    try {
        const response = await fetch(BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ emails }),
            })

        if (!response.ok) {
            throw new Error('Failed to invite crew members')
        }
        return await response.json()

        } catch (error) {
            console.error(error)
            throw error
        }
}


/**
 * DELETE /crewMember/{id}
 * Admin deletes a crew member (Use Case 15)
 */
const deleteCrewMember = async (id) => {
    try {
        const response = await fetch(`${BASE_URL}/${id}`, {
            method: 'DELETE' })
    
        if (!response.ok) {
            throw new Error('Failed to delete crew members')
        }
    } catch (error) {
        console.error(error)
        throw error //Rethrow the error to be caught by the caller
    }
}

/**
 * Fetch a single member by its UserId
 * 
 */
const findMemberByUserId = async (userId) => {
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


export default {findAllCrewMembers, findMemberById, updateMemberById, inviteCrewMembers, deleteCrewMember, findMemberByUserId}