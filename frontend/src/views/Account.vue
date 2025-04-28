<template>
    <div>
        <h2>My Account</h2>
        <br />
        
        <!-- Display details of the account -->
        <div v-if="user">
            <p><strong>First Name:</strong> {{ user.firstName }}</p>
            <p><strong>Last Name:</strong> {{ user.lastName }}</p>
            <p><strong>Phone Number:</strong> {{ user.phoneNumber }}</p>
            <p><strong>Email:</strong> {{ user.email }}</p>
            <p v-if="user.position"><strong>Positions:</strong> {{ user.position.join(', ') }}</p>
            </div>
        <div v-else>
        Loading user information...
        </div>     
                
        <br />
        <button @click="$router.push({ name: 'changeAccountDetails', params: { id: user.id } })">Change Account Details</button>



    </div>
</template>

<script setup>
import { getUserId } from '@/apis/auth';
import api from '@/apis/crewMembers'
import { ref, onMounted} from 'vue'

const user = ref(null)

onMounted(async () => {
  const loggedInUser = getUserId()
  if (loggedInUser && loggedInUser.id) {
    try {
      user.value = await api.findMemberById(loggedInUser.id)
    } catch (error) {
      console.error('Failed to load user info:', error)
    }
  }
})



</script>

<style scoped>
div {
  max-width: 600px;
  margin: 40px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 12px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

p {
  font-size: 16px;
  margin: 10px 0;
  color: #555;
}

strong {
  color: #222;
}

button {
  display: block;
  margin: 30px auto 0;
  padding: 12px 24px;
  background-color: #007bff;
  color: white;
  font-size: 16px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}

</style>