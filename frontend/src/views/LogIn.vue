<template>
    <div class="login-page">
        <div class="welcome-heading">Welcome Frog Crew</div>

    <div class="login-container">
        <h2>Login</h2>
        <form @submit.prevent="handleLogin" class="login-form">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="text" id="email" v-model="email" required placeholder="Enter your email">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" v-model="password" required placeholder="Enter your password">
            </div>

            <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>


            <button type="submit" class="login-button" :disabled="isLoading">
                {{ isLoading ? 'Logging in...' : 'Login' }}
            </button>
        </form>
    </div>
</div>
</template>

<script setup>
import { login, getUserRole } from '@/apis/auth'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { reportsRoute, templatesRoute, manageCrewMembersRoute, inviteCrewMembersRoute } from '@/router/dynamicRoutes'

const email = ref('')
const password = ref('')
const isLoading = ref(false)

const router = useRouter()

const errorMessage = ref('')


async function handleLogin() {
    if (isLoading.value) {
        console.log('Login already in progress, ignoring click')
        return
    }
    
    try {
        isLoading.value = true
        console.log('Starting login process...')
        console.log('Email:', email.value)
        console.log('Password length:', password.value.length)
        
        const loginResult = await login(email.value, password.value)
        console.log('Login result:', loginResult)

        if (loginResult) {
            const userRole = getUserRole()
            console.log('User role:', userRole)

            // Dynamically add ADMIN-only routes
            if (userRole === 'ADMIN') {
                if (!router.hasRoute('reports')) {
                    router.addRoute('mainLayout', reportsRoute)
                }
                if (!router.hasRoute('templates')) {
                    router.addRoute('mainLayout', templatesRoute)
                }
                if (!router.hasRoute('manageCrewMembers')) {
                    router.addRoute('crewMembers', manageCrewMembersRoute)
                }
                if (!router.hasRoute('inviteCrewMembers')) {
                    router.addRoute('crewMembers', inviteCrewMembersRoute)
                }
            }

            // Clear any previous error
            errorMessage.value = ''

            //After successful login redirect to the home page
            router.push('/')
        } else {
            // Login failed but no exception thrown
            errorMessage.value = 'Login failed. Please check your credentials.'
        }
    } catch (error) {
        console.error('Login error in component:', error)
        errorMessage.value = `Login error: ${error.message}`
        // Don't redirect on error - let user see the error message
    } finally {
        isLoading.value = false
    }
}



</script>

<style scoped>

.login-page {
  min-height: 100vh;
  background-image: url('@/assets/img/Login.jpg'); 
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: center;
  justify-content: center;
}

.welcome-heading {
  position: absolute;
  top: 30px;
  width: 100%;
  text-align: center;
  font-size: 2rem;
  font-weight: bold;
  color: white;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
}

.login-container {
    padding: 1.5rem;
    background-color: #f5f5f5;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    max-width: 400px;
    margin: 2rem auto;

    h2 {
        margin-bottom: 1rem;
        font-size: 1.8rem;
        font-weight: bold;
        color: #333;
        text-align: center;
    }

    .login-form {
        display: flex;
        flex-direction: column;
        gap: 1rem;

        .form-group {
            display: flex;
            flex-direction: column;

            label {
                margin-bottom: 0.5rem;
                font-size: 1rem;
                color: #555;
            }

            input {
                padding: 0.5rem;
                border: 1px solid #ddd;
                border-radius: 6px;
                background-color: #fff;
                transition: all 0.3s ease;

                &:focus {
                    border-color: purple;
                    box-shadow: 0 0 4px rgba(0, 123, 255, 0.5);
                    outline: none;
                }
            }
        }

        .login-button {
            padding: 0.75rem;
            border: none;
            border-radius: 6px;
            background-color: purple;
            color: #fff;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s ease;

            &:hover {
                background-color: #0056b3;
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
                transform: translateY(-3px);
            }

            &:active {
                transform: translateY(3px);
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
        }
    }
}

.error-message {
  color: red;
  text-align: center;
  margin-top: -0.5rem;
  margin-bottom: 0.5rem;
  font-size: 0.95rem;
  background-color: #ffe5e5;
  padding: 0.4rem;
  border-radius: 4px;
}



</style>