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
        console.log('Login attempt for email:', email)
        console.log('API URL:', getApiUrl('/auth/login'))
        
        const response = await fetch(getApiUrl('/auth/login'), {
            method: 'POST',
            headers: getHeaders(),
            body: JSON.stringify({ email, password })
        })

        console.log('Response status:', response.status)
        console.log('Response ok:', response.ok)

        if (!response.ok) {
            const errorText = await response.text()
            console.error('Response error text:', errorText)
            throw new Error(`Network response was not ok: ${response.statusText}`)
        }

        const data = await response.json()
        console.log('Response data:', data)

        if (data.flag) {  // Backend uses 'flag' not 'success'
            // Successful login - backend returns { userId, role, token }
            const userInfo = data.data
            console.log('User info:', userInfo)
            isAuthenticated.value = true
            userRole.value = userInfo.role || 'CREW' // If role missing, default to CREW
            userId.value = userInfo.userId
            fullName.value = '' // We'll need to fetch this separately or add it to the response
            token.value = userInfo.token || null
            return true
        } else {
            // Wrong email or password
            console.log('Login failed - data.flag is false')
            isAuthenticated.value = false
            userRole.value = ''
            userId.value = null
            fullName.value = ''
            return false
        }
    } catch (error) {
        console.error('Login error:', error)
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