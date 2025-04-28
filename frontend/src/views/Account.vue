<template>
    <div>
        <h2>My Account</h2>
        <br />
        
        <!-- Display details of the account -->
        <div v-if="user">
            <p><strong>Full Name:</strong> {{ user.fullName }}</p>
            <p><strong>Email:</strong> {{ user.email }}</p>
            <p><strong>Phone Number:</strong> {{ user.phoneNumber }}</p>
            <p><strong>Username:</strong> {{ user.username }}</p>
            <p v-if="user.position"><strong>Positions:</strong> {{ user.position.join(', ') }}</p>
            </div>
        <div v-else>
        Loading user information...
        </div>     
                
        <br />
        <button>Change Account Information</button>


    </div>
</template>

<script setup>
import { getUserInfo } from '@/apis/auth';

const user = getUserInfo()

</script>

<style lang="scss" scoped>

</style>