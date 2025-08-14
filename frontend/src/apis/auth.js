import { ref } from "vue"
import { getApiUrl, getHeaders } from './config.js'

const isAuthenticated = ref(false) //Global state that tracks whether the user is logged in
const userRole = ref('') //Stores 'CREW' or 'ADMIN
const userId = ref(null) //Stores full user info
const fullName = ref('') // Stores the full name of the user
const token = ref(null) //Stores JWT token

//Login
const login = async (email, password) => {
    try {
        const response = await fetch(getApiUrl('/auth/login'), {
            method: 'POST',
            headers: getHeaders(),
            body: JSON.stringify({ email, password })
        })

        if (!response.ok) {
            throw new Error(`Network response was not ok: ${response.statusText}`)
        }

        const data = await response.json()

        if (data.success) {
            // Successful login - backend returns { userId, role, token }
            const userInfo = data.data
            isAuthenticated.value = true
            userRole.value = userInfo.role || 'CREW' // If role missing, default to CREW
            userId.value = userInfo.userId
            fullName.value = '' // We'll need to fetch this separately or add it to the response
            token.value = userInfo.token || null
            return true
        } else {
            // Wrong email or password
            isAuthenticated.value = false
            userRole.value = ''
            userId.value = null
            fullName.value = ''
            return false
        }
    } catch (error) {
        console.error(error)
        isAuthenticated.value = false
        userRole.value = ''
        userId.value = null
        fullName.value = ''
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

// Get user full name
const getUserFullName = () => {
    return fullName.value
}

// Get token
const getToken = () => {
    return token.value
}

export { isAuthenticated, login, logout, getUserRole, userRole, getUserId, getUserFullName, getToken }