<template>
    <div>
        <h2>Invite Crew Members</h2>
        <div>Enter emails to invite new crew members (one per line):</div>
        <br />
        <textarea v-model="emailsText" cols="80" rows="15" placeholder="Enter email addresses..."></textarea>
        <br />
        <button @click="sendInvites">Send</button>

        <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
        <div v-if="successMessage" class="success">{{ successMessage }}</div>

    </div>
</template>

<script setup>
import { ref } from 'vue'
import api from '@/apis/crewMembers'

const emailsText = ref('') // raw textarea content
const errorMessage = ref('')
const successMessage = ref('')

//Validate that emails entered have correct format
function validateEmail(email) {
  const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
  return re.test(email)
}


async function sendInvites() {
    errorMessage.value = ''
    successMessage.value = ''

    // Check if the textarea is empty
    if (!emailsText.value.trim()) {
        errorMessage.value = 'Please enter at least one email address.'
        return
    }

  const emailList = emailsText.value
    .split('\n')           // split by new lines
    .map(e => e.trim())     // remove spaces
    .filter(e => e.length)  // remove empty lines

  // Validate all emails
  const invalidEmails = emailList.filter(email => !validateEmail(email))

  if (invalidEmails.length > 0) {
    errorMessage.value = `Invalid email(s): ${invalidEmails.join(', ')}`
    return  //Don't send if there are invalid emails
  }

  try {
    await api.inviteCrewMembers(emailList) //pass as { emails: [...] }
    emailsText.value = '' //clear input
    successMessage.value = 'Invitations sent successfully!'
  } catch (error) {
    console.error(error)
  }
}
</script>

<style scoped>
/* Container for the whole form */
div {
  padding: 20px;
  max-width: 600px;
  margin: 0 auto;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* Title */
h2 {
  text-align: center;
  color: #333;
}

/* Label text for the textarea */
div {
  font-size: 16px;
  color: #555;
}

/* Styling the text area */
textarea {
  width: 100%;
  padding: 10px;
  margin-top: 10px;
  margin-bottom: 15px;
  border-radius: 5px;
  border: 1px solid #ccc;
  font-size: 14px;
  resize: vertical;
  transition: border-color 0.3s ease-in-out;
}

textarea:focus {
  border-color: #007bff;
  outline: none;
}

/* Button styling */
button {
  width: 100%;
  padding: 10px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #218838;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

/* Success message styling */
.success {
  color: #28a745;
  background-color: #e9f7e9;
  padding: 10px;
  border-radius: 5px;
  margin-top: 20px;
  border: 1px solid #28a745;
  font-size: 14px;
}

/* Error message styling */
.error {
  color: #dc3545;
  background-color: #f8d7da;
  padding: 10px;
  border-radius: 5px;
  margin-top: 20px;
  border: 1px solid #dc3545;
  font-size: 14px;
}

</style>