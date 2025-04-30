<template>
   <div class="notifications-page">
    <h1>Notifications</h1>

    <div v-if="notifications.length === 0">
      <p>No notifications to display.</p>
    </div>

    <ul v-else>
      <li
        v-for="(notification, index) in notifications"
        :key="index"
        class="notification"
        :class="{ read: notification.read }"
      >
        <p>{{ notification.message }}</p>
        <p class="date">{{ formatDate(notification.date) }}</p>
        <button
          v-if="!notification.read"
          @click="markAsRead(notification)"
        >
          Mark as Read
        </button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUserId } from '@/apis/auth'
import api from '@/apis/notifications'

const notifications = ref([])
let userId = null

onMounted(async () => {
  try {
    const user = getUserId()
    userId = user.id
    const data = await api.getNotificationsById(userId)
    notifications.value = data
  } catch (error) {
    console.error('Failed to load notifications:', error)
  }
})

const markAsRead = async (notification) => {
  try {

    await api.markNotificationAsRead(
      notification.id,
      notification.userId,
      notification.userIdCreatedNotification,
      notification.message,
      true,
      notification.date
    )

    notification.read = true

  } catch (error) {
    console.error('Failed to mark notification as read:', error)
  }
}

const formatDate = (isoDate) => {
  const date = new Date(isoDate)
  return date.toLocaleString()
}

</script>

<style scoped>
.notifications-page {
  max-width: 600px;
  margin: 2rem auto;
  padding: 1rem;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

h1 {
  text-align: center;
  margin-bottom: 2rem;
}

.notification {
  background-color: #f1f1f1;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease;
}

.notification.read {
  background-color: #d1ffd1;
}

.notification .date {
  color: #888;
  font-size: 0.875rem;
}

button {
  margin-top: 0.5rem;
  background-color: #007bff;
  color: white;
  border: none;
  padding: 0.4rem 0.8rem;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

</style>