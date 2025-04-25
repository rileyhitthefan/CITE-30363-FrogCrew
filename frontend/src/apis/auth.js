import { ref } from "vue"

const isAuthenticated = ref(false) //Global state that tracks whether the user is logged in


const userRole = ref('') //Stores 'CREW' or 'ADMIN


const login = async (username, password) => {
    //Simulate a successful login
    isAuthenticated.value = true
    userRole.value = username == 'john' ? 'ADMIN' : 'CREW'
}

const logout = async() => {
    isAuthenticated.value = false
    userRole.value = ''
}

const getUserRole = () => {
    return userRole.value
}


export { isAuthenticated, login, logout, getUserRole, userRole}