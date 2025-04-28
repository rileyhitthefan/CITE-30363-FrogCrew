const BASE_URL = 'http://localhost:3000/crewMembers'

import { ref } from "vue"

const isAuthenticated = ref(false) //Global state that tracks whether the user is logged in

const userRole = ref('') //Stores 'CREW' or 'ADMIN

const userInfo = ref(null) //Stores full user info

//Login
const login = async (username, password) => {
    try {
        const response = await fetch(`${BASE_URL}?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`)

        if (!response.ok) {
            throw new Error(`Network response was not ok: ${response.statusText}`)
        }

        const data = await response.json()

        if (data.length > 0) {
            // Successful login
            const user = data[0]
            isAuthenticated.value = true
            userRole.value = user.role || 'CREW' // If role missing, default to CREW
            userInfo.value = user
            return true
        } else {
        // Wrong username or password
        isAuthenticated.value = false
        userRole.value = ''
        userInfo.value = null
        return false
        }
    } catch (error) {
        console.error(error)
        isAuthenticated.value = false
        userRole.value = ''
        userInfo.value = null
        throw error //Rethrow the error to be caught by the caller
    }
}

//Logout
const logout = async() => {
    isAuthenticated.value = false
    userRole.value = ''
    userInfo.value = null
}

//Get user role
const getUserRole = () => {
    return userRole.value
}

const getUserInfo = () => {
    return userInfo.value
  }

export { isAuthenticated, login, logout, getUserRole, getUserInfo, userRole}