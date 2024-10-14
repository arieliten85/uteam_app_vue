<template>
  <div class="container mx-auto p-4 h-screen">
    <div class="grid grid-cols-1 md:grid-cols-3 gap-4 h-full">
      <div class="h-full overflow-y-scroll">
        <h2 class="text-2xl font-bold mb-4">1. Select comics</h2>
        <comic-list
          @select-comic="handleSelectComic"
          :comics="comics"
        ></comic-list>
      </div>

      <div class="h-full overflow-y-scroll">
        <user-form
          :selected-comics="selectedComics"
          @create-user="handleCreateUser"
          @reset-comics-selection="resetComicSelection"
        ></user-form>
      </div>

      <div class="h-full overflow-y-scroll">
        <user-list
          :users="users"
          @user-deleted="handleUserDeleted"
          @user-updated="handleUserUpdated"
        ></user-list>
      </div>
    </div>

    <!-- Spinner de carga -->
    <div
      v-if="isLoading"
      class="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-70"
    >
      <div class="loader"></div>
    </div>
  </div>
</template>

<script>
import ComicList from './components/ComicList.vue'
import UserForm from './components/UserForm.vue'
import UserList from './components/UserList.vue'
import axios from 'axios'
import md5 from 'md5'

export default {
  components: {
    ComicList,
    UserForm,
    UserList,
  },
  data() {
    return {
      comics: [],
      selectedComics: [],
      users: [],
      isLoading: true, // Nueva variable de estado para manejar la carga
    }
  },
  async mounted() {
    await this.fetchComics()
    await this.fetchUsers()
  },
  methods: {
    async fetchComics() {
      const ts = Date.now()
      const publicKey = 'beb48fbe5e6dbd80a09b2786a3e52795'
      const privateKey = 'bc067120e117f0addcd1e0fb68e9de98479ee1ce'
      const hash = md5(ts + privateKey + publicKey)
      const url = `https://gateway.marvel.com/v1/public/comics?ts=${ts}&apikey=${publicKey}&hash=${hash}`

      this.isLoading = true // Comienza la carga
      try {
        const response = await axios.get(url)
        this.comics = response.data.data.results.map(comic => ({
          ...comic,
          isSelected: false,
        }))
      } catch (error) {
        console.error('Error fetching comics:', error)
      } finally {
        this.isLoading = false // Finaliza la carga
      }
    },
    async fetchUsers() {
      this.isLoading = true // Comienza la carga
      try {
        const response = await axios.get('http://localhost:8080/api/persons')
        this.users = response.data
      } catch (error) {
        console.error('Error fetching users:', error)
      } finally {
        this.isLoading = false // Finaliza la carga
      }
    },
    handleSelectComic(comic) {
      if (!this.selectedComics.includes(comic)) {
        this.selectedComics.push(comic)
      } else {
        this.selectedComics = this.selectedComics.filter(c => c.id !== comic.id)
      }
    },
    resetComicSelection() {
      this.selectedComics = [] // Limpiar la selección de cómics
      this.comics.forEach(comic => {
        comic.isSelected = false // Desmarcar todos los cómics
      })
    },
    async handleCreateUser(user) {
      this.users.push(user)
      console.log('User created:', user)
    },
    handleUserDeleted(userId) {
      this.users = this.users.filter(user => user.id !== userId)
    },
    handleUserUpdated(updatedUser) {
      const index = this.users.findIndex(user => user.id === updatedUser.id)
      if (index !== -1) {
        this.users.splice(index, 1, updatedUser) // Reemplazar el usuario actualizado
      }
    },
  },
}
</script>

<style scoped>
.loader {
  border: 8px solid #f3f3f3;
  border-top: 8px solid #3498db;
  border-radius: 50%;
  width: 50px;
  height: 50px;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>
