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
        return await response.json();
    } catch (error) {
        console.error(error)
        throw error //Rethrow the error to be caught by the caller
    }
}


export default {findAllCrewMembers, findMemberById}