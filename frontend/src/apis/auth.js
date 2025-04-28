const BASE_URL = 'http://localhost:3000/crewMembers'
//Real API: POST /auth/login


import { ref } from "vue"

const isAuthenticated = ref(false) //Global state that tracks whether the user is logged in
const userRole = ref('') //Stores 'CREW' or 'ADMIN
const userId = ref(null) //Stores full user info
const token = ref(null) //Stores JWT token

//Login
const login = async (email, password) => {
    try {
        const response = await fetch(`${BASE_URL}?email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`)

        if (!response.ok) {
            throw new Error(`Network response was not ok: ${response.statusText}`)
        }

        const data = await response.json()

        if (data.length > 0) {
            // Successful login
            const user = data[0]
            isAuthenticated.value = true
            userRole.value = user.role || 'CREW' // If role missing, default to CREW
            userId.value = user
            return true
        } else {
        // Wrong email or password
        isAuthenticated.value = false
        userRole.value = ''
        userId.value = null
        return false
        }
    } catch (error) {
        console.error(error)
        isAuthenticated.value = false
        userRole.value = ''
        userId.value = null
        throw error //Rethrow the error to be caught by the caller
    }
}

//Logout
const logout = async() => {
    isAuthenticated.value = false
    userRole.value = ''
    userId.value = null
    token.value = null
}

//Get user role
const getUserRole = () => {
    return userRole.value
}

// Get user ID
const getUserId = () => {
    return userId.value
}

export { isAuthenticated, login, logout, getUserRole, userRole, getUserId}