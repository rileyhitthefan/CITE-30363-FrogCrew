<template>
    <div>
        <h2>Account Changes Confirmation Page</h2>
        <br />
        <div v-if="user">
            <p><strong>First Name:</strong> {{ user.firstName }}</p>
            <p><strong>Last Name:</strong> {{ user.lastName }}</p>
            <p><strong>Phone Number:</strong> {{ user.phoneNumber }}</p>
            <p><strong>Email:</strong> {{ user.email }}</p>
            <p><strong>Positions:</strong> {{ positions }}</p>
        </div>

        <br /> 
        <button @click="confirmChanges">Confirm Changes</button>
        <button @click="$router.push({ name: 'changeAccountDetails', params: { id: user.id } })">Change Details</button>
        <button @click="$router.push({ name: 'account' })">Discard Changes</button>


    </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import api from '@/apis/crewMembers'
import { useAccountChangesStore } from '@/stores/accountChangesStore'


const route = useRoute()
const router = useRouter()


const accountChangesStore = useAccountChangesStore()
const user = ref(accountChangesStore.user)
const positions = ref(accountChangesStore.positions)

// Confirm API call
const confirmChanges = async () => {
  if (!user.value) return

  try {
    const updatedData = {
      id: user.value.id,  // Ensure the id is also included
      userId: user.value.userId,
      fullName: user.value.fullName,
      firstName: user.value.firstName,
      lastName: user.value.lastName,
      phoneNumber: user.value.phoneNumber,
      email: user.value.email,
      password: user.value.password,  // Include the password
      role: user.value.role,  // Include the role
      position: positions.value.split(',').map(p => p.trim()),
    }

    await api.updateMemberById(user.value.id, updatedData)

    accountChangesStore.clear()

    // Redirect after success
    router.push({ name: 'account' })
    
    // Show alert
    alert('Your account has been successfully updated!')
  } catch (error) {
    console.error('Failed to update member:', error)
  }
}

</script>
s
<style lang="scss" scoped>
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