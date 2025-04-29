<template>
    <div>
        <h2>Crew Members List</h2>

        <!-- Sorting and Filtering Controls -->
        <div class="controls-container">
            <!-- Sorting Controls -->
            <div class="control-group">
                <strong>Sort By</strong><br />
                <select v-model="sortBy">
                    <option value="">Default</option>
                    <option value="role">Crew Member Role</option>
                    <option value="firstName">First Name</option>
                    <option value="lastName">Last Name</option>
                    <option value="availability">Availability Status</option>
                </select>
            </div>

            <!-- Filter Options -->
            <div class="control-group">
                <strong>Filter Options</strong><br />
                <select v-model="filterActiveInactive">
                    <option value="">Active and Inactive</option>
                    <option value="Active">Active Only</option>
                    <option value="Inactive">Inactive Only</option>
                </select>

                <label>
                    <input type="checkbox" v-model="filterAvailableOnly" />
                    Available for Upcoming Games Only
                </label>

                <select v-model="filterExperience">
                    <option value="">All Experience Levels</option>
                    <option value="New">New</option>
                    <option value="Experienced">Experienced</option>
                    <option value="Senior">Senior</option>
                </select>

                <select v-model="filterAssignedUnassigned">
                    <option value="">Assigned and Unassigned</option>
                    <option value="Assigned">Assigned Only</option>
                    <option value="Unassigned">Unassigned Only</option>
                </select>
            </div>
        </div>

        <!--Search bar-->
        <input
            type="text"
            class="search-bar"
            placeholder="Search crew members by first or last name..."
            v-model="searchQuery"
        />

        <!-- Download and Bulk Actions -->
        <div style="margin-bottom: 20px;">
            <button @click="exportCSV">Download (CSV)</button>
            <button @click="exportExcel">Download (Excel)</button>

            <select v-model="bulkAction" style="margin-left: 20px;">
                <option value="">Bulk Actions</option>
                <option value="sendMessage">Send Message</option>
                <option value="updateRole">Update Role</option>
                <option value="deactivate">Deactivate Account</option>
            </select>
            <button @click="performBulkAction" :disabled="!selectedMembers.length || !bulkAction">Confirm</button>
        </div>

        <!--List of crew members fetched from back end-->
        <table>
            <thead>
                <tr>
                    <th><input type="checkbox" :checked="allSelected" @change="toggleSelectAll" /></th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone number</th>
                    <th>Details</th>
                    <th>Actions</th>
                </tr> 
            </thead>
            <tbody>
                <tr v-for="crewMember in filteredCrewMembers" :key="crewMember.userId">
                    <td><input type="checkbox" :value="crewMember.userId" :checked="selectedMembers.includes(crewMember.userId)" @change="toggleMemberSelection(crewMember.userId, $event.target.checked)"/></td>
                    <td>{{ crewMember.fullName }}</td>
                    <td>{{ crewMember.email }}</td>
                    <td>{{ crewMember.phoneNumber }}</td>
                    <td><button @click="$router.push({name: 'detailsCrewMember',  params: { id: crewMember.id } })">MORE</button></td>
                    <td><button class="delete" @click="handleDeleteCrewMember(crewMember.id)">Delete</button></td>
                </tr> 
            </tbody>
        </table> 
   </div>
</template>

<script setup>
import api from '@/apis/crewMembers'
import { ref, computed, onMounted} from 'vue'
import { getUserId } from '@/apis/auth';

const crewMembers = ref([])

//For Search Bar to function
const searchQuery = ref('')

//For filters
const sortBy = ref('')
const filterActiveInactive = ref('')
const filterAvailableOnly = ref(false)
const filterExperience = ref('')
const filterAssignedUnassigned = ref('')

//Bulk actions
const selectedMembers = ref([])
const bulkAction = ref('')

onMounted(loadCrewMembers)

//Crew member list fetched from api
async function loadCrewMembers(){
    crewMembers.value = await api.findAllCrewMembers()

}

const filteredCrewMembers = computed(() => {
  let members = crewMembers.value

  // 1. Filtering
  if (filterActiveInactive.value === 'Active') {
    members = members.filter(member => member.isActive) // assuming you have isActive field
  } else if (filterActiveInactive.value === 'Inactive') {
    members = members.filter(member => !member.isActive)
  }

  if (filterAvailableOnly.value) {
    members = members.filter(member => member.isAvailableUpcoming) // assuming you have isAvailableUpcoming field
  }

  if (filterExperience.value) {
    members = members.filter(member => member.experienceLevel === filterExperience.value)
  }

  if (filterAssignedUnassigned.value === 'Assigned') {
    members = members.filter(member => member.isAssigned) // assuming you have isAssigned field
  } else if (filterAssignedUnassigned.value === 'Unassigned') {
    members = members.filter(member => !member.isAssigned)
  }

  // 2. Searching
  if (searchQuery.value) {
    members = members.filter(member =>
      member.fullName.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  // 3. Sorting
  if (sortBy.value === 'role') {
    members = [...members].sort((a, b) => a.role.localeCompare(b.role))
  } else if (sortBy.value === 'firstName') {
    members = [...members].sort((a, b) => a.firstName.localeCompare(b.firstName))
  } else if (sortBy.value === 'lastName') {
    members = [...members].sort((a, b) => a.lastName.localeCompare(b.lastName))
  } else if (sortBy.value === 'availability') {
    members = [...members].sort((a, b) => a.availabilityStatus.localeCompare(b.availabilityStatus))
  }

  return members
})

//Download CSV and Excel
const downloadCSV = (filename) => {
  const rows = filteredCrewMembers.value.map(member => ({
    Name: member.fullName,
    Email: member.email,
    Phone: member.phoneNumber
  }))

  const csvContent = "data:text/csv;charset=utf-8," 
    + ["Name,Email,Phone", ...rows.map(r => `${r.Name},${r.Email},${r.Phone}`)].join("\n")

  const encodedUri = encodeURI(csvContent)
  const link = document.createElement("a")
  link.setAttribute("href", encodedUri)
  link.setAttribute("download", filename)
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
}

const exportCSV = () => {
  downloadCSV("crew_members.csv")
}

const exportExcel = () => {
  downloadCSV("crew_members.xls")
}

//Bulk actions
const performBulkAction = () => {
  if (!bulkAction.value) return;

  const membersToActOn = crewMembers.value.filter(member => selectedMembers.value.includes(member.userId));

  if (bulkAction.value === 'sendMessage') {
    alert(`Sending message to ${membersToActOn.length} members`);
    // Implement real sendMessage logic here
  } else if (bulkAction.value === 'updateRole') {
    alert(`Updating roles for ${membersToActOn.length} members`);
    // Implement real updateRole logic here
  } else if (bulkAction.value === 'deactivate') {
    alert(`Deactivating ${membersToActOn.length} members`);
    // Implement real deactivate logic here
  }

  // Clear after action
  selectedMembers.value = [];
  bulkAction.value = '';
}

const toggleSelectAll = (event) => {
  if (event.target.checked) {
    selectedMembers.value = filteredCrewMembers.value.map(member => member.userId);
  } else {
    selectedMembers.value = [];
  }
}


const allSelected = computed(() => {
  return selectedMembers.value.length === filteredCrewMembers.value.length && filteredCrewMembers.value.length > 0;
})

//Delete button
const handleDeleteCrewMember = async (id) => {
    if (confirm('Are you sure you want to delete this member?')) {
    try {
      await api.deleteCrewMember(id)
      await loadCrewMembers(); // refresh the list after successful deletion
    } catch (error) {
      alert('Failed to delete the crew member.');
    }
  }
}




</script>

<style scoped>

.delete {
    background-color: red;
}

/* Controls Layout */
.controls-container {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
  margin-bottom: 20px;
}

/* Each control group (sorting, filtering) */
.control-group {
  background: #f9f9f9;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #ddd;
  min-width: 200px;  /* Increased width to allow filter options to fit */
  font-size: 14px;
  display: flex;
  flex-wrap: wrap; /* Allow wrapping within control group */
  gap: 10px; /* Space between options */
}

/* Smaller select and checkbox inside controls */
.control-group select,
.control-group label {
  display: inline-block; /* Allow display inline */
  margin: 5px 0;
  font-size: 13px;
  width: auto; /* Let them take necessary space */
}

.control-group input[type="checkbox"] {
  transform: scale(0.9);
}

/* Form Controls */
select, input[type="text"], label {
  margin: 10px 10px 10px 0;
  padding: 8px;
  font-size: 14px;
}

select, input[type="text"] {
  border: 1px solid #ccc;
  border-radius: 4px;
}

/* Checkbox */
input[type="checkbox"] {
  margin-right: 5px;
}

/* Table and other styles */
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

thead {
  background-color: #f2f2f2;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

/* Table Hover Effect */
tbody tr:hover {
  background-color: #f9f9f9;
}

/* Search Bar */
.search-bar {
  width: 500px; /* Make it wider */
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 6px;
}

/* Buttons */
button {
  padding: 6px 12px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #2980b9;
}

/* Responsive tweaks */
@media (max-width: 768px) {
  .controls-container {
    flex-direction: column; /* Stack controls vertically on smaller screens */
  }

  .control-group {
    width: 100%; /* Allow full width for each control group */
  }

  table, thead, tbody, th, td, tr {
    display: block;
  }

  th, td {
    text-align: right;
    position: relative;
    padding-left: 50%;
  }

  th::before, td::before {
    position: absolute;
    left: 10px;
    top: 12px;
    white-space: nowrap;
  }

  th:nth-child(2)::before { content: "Name"; }
  th:nth-child(3)::before { content: "Email"; }
  th:nth-child(4)::before { content: "Phone Number"; }
}


</style>