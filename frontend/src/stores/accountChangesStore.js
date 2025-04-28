import { defineStore } from 'pinia'

export const useAccountChangesStore = defineStore('accountChanges', {
  state: () => ({
    user: null,
    positions: '',
  }),
  actions: {
    setUser(user) {
      this.user = user
    },
    setPositions(positions) {
      this.positions = positions
    },
    clear() {
      this.user = null
      this.positions = ''
    }
  }
})
