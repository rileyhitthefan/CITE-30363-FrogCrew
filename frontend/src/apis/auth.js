const BASE_URL = 'https://cite-30363-frogcrew.onrender.com/api/v1/auth/login'


import { ref } from "vue"

const isAuthenticated = ref(false) //Global state that tracks whether the user is logged in
const userRole = ref('') //Stores 'CREW' or 'ADMIN
const userId = ref(null) //Stores full user info
const token = ref(null) //Stores JWT token

//Real API: POST /auth/login
//Login
const login = async (email, password) => {
    try {
        const response = await fetch(BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        })


        const result = await response.json()


        if (!response.ok || !result.flag) {
            console.error('Login failed:', result.message || 'Unknown error');
            throw new Error(result.message || 'Login failed')
        }


        //Successful login
        isAuthenticated.value = true
        userRole.value = result.data.role || 'MEMBER' // If role missing, default to CREW
        userInfo.value = {
            userId: result.data.userId,
            role: result.data.role
        }
        token.value = result.data.token
        return true
    } catch (error) {
        console.error(error)
        isAuthenticated.value = false
        userRole.value = ''
        userInfo.value = null
        token.value = null
        return false
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