<template>
    <div>
      <h2>Change Account Details</h2>
      <br />
      <div v-if="user">
        <form @submit.prevent="goToConfirm"> <!-- Form submission will trigger goToConfirm -->
          <label>First Name: 
              <input v-model="user.firstName" type="text" required/>
          </label>
          <br />
          <label>Last Name: 
              <input v-model="user.lastName" type="text" required/>
          </label>
          <br />
          <label>Phone Number: 
              <input v-model="user.phoneNumber" type="tel" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" required/>
          </label>
          <br />
          <label>Email: 
              <input v-model="user.email" required type="email"/>
          </label>
          <br />
          <label>Position: 
              <input v-model="positions" placeholder="comma separated (e.g. Camera, Director)" type="text" required/>
              (separate positions with a comma)
          </label>
          <!-- Submit button -->
          <button type="submit">Finish Changes</button>
        </form> 
      </div>
      <br />
      <button @click="$router.push({name : 'account'})">Discard Changes</button>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useRouter } from 'vue-router'
import api from '@/apis/crewMembers'
import { useAccountChangesStore } from '@/stores/accountChangesStore'

// Create reactive variables
const user = ref(null)
const positions = ref('')

// Get route (to access params)
const route = useRoute()

const accountChangesStore = useAccountChangesStore()


onMounted(async () => {
  const id = route.params.id
  if (id) {
    try {
      user.value = await api.findMemberById(id)
      positions.value = user.value.position.join(', ') // prefill the input for positions
    } catch (error) {
      console.error('Failed to load user info:', error)
    }
  }
})

const router = useRouter()

const goToConfirm = () => {
    accountChangesStore.setUser({ ...user.value })
    accountChangesStore.setPositions(positions.value)
    
    router.push({ name: 'confirmAccountChanges', params: { id: user.value.id } })
}

</script>

<style scoped>
div {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  background: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);

  h2 {
    text-align: center;
    margin-bottom: 20px;
  }

  label {
    display: block;
    margin-bottom: 15px;
    font-weight: 500;
    color: #333;

    input {
      display: block;
      width: 100%;
      margin-top: 5px;
      padding: 8px 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
      font-size: 14px;
    }
  }

  button {
    margin-top: 20px;
    width: 100%;
    padding: 10px;
    background-color: #4CAF50; /* Green button */
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.2s ease;

    &:hover {
      background-color: #45a049;
    }
  }
}


</style>