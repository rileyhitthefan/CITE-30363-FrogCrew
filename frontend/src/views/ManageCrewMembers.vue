<template>
    <div>
        <h2>Crew Members List</h2>
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
                </tr> 
            </tbody>
        </table> 
   </div>
</template>

<script setup>
import api from '@/apis/crewMembers'
import { ref, onMounted} from 'vue'

const crewMembers = ref([])

onMounted(loadCrewMembers)

async function loadCrewMembers(){
    crewMembers.value = await api.findAllCrewMembers()
}

</script>

<style scoped>

</style>