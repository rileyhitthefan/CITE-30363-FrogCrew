<template>
    <h1>Crew Member Details</h1>
    <div v-if="crewMember">
        <h2>{{ crewMember.fullName }}</h2>
        <p><strong>First Name:</strong> {{ crewMember.firstName }}</p>
        <p><strong>Last Name:</strong> {{ crewMember.lastName }}</p>
        <p><strong>Email:</strong> {{ crewMember.email }}</p>
        <p><strong>Phone Number:</strong> {{ crewMember.phoneNumber }}</p>
        <p><strong>Position:</strong> {{ crewMember.position.join(', ') }}</p>
        <p><strong>Role:</strong> {{ crewMember.role }}</p>
    </div>
    <div v-else>
        Loading crew member details...
    </div>

    <button @click="$router.push({name: 'crewMembers'})">Back to Full List</button>

</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import api from '@/apis/crewMembers' // import your api

const route = useRoute()
const crewMember = ref(null)

onMounted(async () => {
  const id = route.params.id
  try {
    crewMember.value = await api.findMemberById(id)
  } catch (error) {
    console.error('Failed to fetch crew member details:', error)
  }
})

</script>

<style scoped>
h1 {
  text-align: center;
  margin-top: 20px;
  color: #333;
}

h2 {
  text-align: center;
  margin: 20px 0;
  color: #555;
}

div {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 12px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
}

p {
  font-size: 16px;
  margin: 10px 0;
  color: #444;
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