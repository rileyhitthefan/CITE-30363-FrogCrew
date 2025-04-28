<template>
    <div>
        <h1>List of Crew Members</h1>

        <!--List of crew members fetched from back end-->
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone number</th>
                </tr> 
            </thead>
            <tbody>
                <tr v-for="crewMember in crewMembers" :key="crewMember.userId">
                    <td>{{ crewMember.fullName }}</td>
                    <td>{{ crewMember.email }}</td>
                    <td>{{ crewMember.phoneNumber }}</td>
                    <td><button @click="$router.push({name: 'detailsCrewMember',  params: { id: crewMember.id } })">MORE</button></td>
                </tr> 
            </tbody>
        </table> 
        
        <br />
        <!-- Show buttown only for ADMIN-->
        <div v-if="userRole === 'ADMIN' ">
            <button @click="$router.push({ name: 'manageCrewMembers' })">Manage</button>
            <button @click="$router.push({ name: 'inviteCrewMembers' })">Invite</button>
            
            <!-- This is where Manage or Invite view will be shown -->
            <router-view />
        
        </div>

    </div>
</template>

<script setup>
import { userRole } from '@/apis/auth';

import api from '@/apis/crewMembers'
import { ref, onMounted} from 'vue'

const crewMembers = ref([])

onMounted(loadCrewMembers)

async function loadCrewMembers(){
    crewMembers.value = await api.findAllCrewMembers()
}

</script>

<style scoped>
h1 {
  text-align: center;
  margin-top: 20px;
  color: #333;
}

table {
  width: 90%;
  margin: 20px auto;
  border-collapse: collapse;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
  background-color: #fff;
  border-radius: 12px;
  overflow: hidden;
}

thead {
  background-color: #007bff;
  color: white;
}

th, td {
  padding: 15px;
  text-align: center;
  border-bottom: 1px solid #ddd;
  font-size: 16px;
}

tr:hover {
  background-color: #f1f1f1;
}

button {
  padding: 8px 16px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s;
  font-size: 14px;
}

button:hover {
  background-color: #0056b3;
}

div {
  margin-top: 30px;
  text-align: center;
}

.router-view {
  margin-top: 20px;
}


</style>