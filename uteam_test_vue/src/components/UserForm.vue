<template>
  <div class="flex flex-col justify-center items-center">
    <h2 class="text-2xl font-bold mb-4">2. Create a New User</h2>
    <div class="w-full max-w-md p-6 bg-white rounded-lg shadow-md">
      <form @submit.prevent="createUser">
        <div class="mb-4">
          <label
            for="first-name"
            class="block text-sm font-medium text-gray-600"
            >First Name</label
          >
          <input
            v-model="firstName"
            type="text"
            id="first-name"
            class="mt-1 block w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-400 transition"
            placeholder="First Name"
          />
        </div>
        <div class="mb-4">
          <label for="last-name" class="block text-sm font-medium text-gray-600"
            >Last Name</label
          >
          <input
            v-model="lastName"
            type="text"
            id="last-name"
            class="mt-1 block w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-400 transition"
            placeholder="Last Name"
          />
        </div>
        <div class="mb-4">
          <label for="birthdate" class="block text-sm font-medium text-gray-600"
            >Birthdate</label
          >
          <input
            v-model="birthdate"
            type="date"
            id="birthdate"
            class="mt-1 block w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-400 transition"
          />
        </div>
        <div class="mb-4 flex items-center">
          <input
            v-model="hasInsurance"
            type="checkbox"
            class="h-4 w-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
          />
          <label class="ml-2 text-sm font-medium text-gray-600"
            >Has Insurance</label
          >
        </div>
        <button
          type="submit"
          :disabled="!isFormValid"
          :class="[
            'p-3 mt-4 w-full rounded-md transition',
            isFormValid
              ? 'bg-blue-600 text-white hover:bg-blue-700'
              : 'bg-gray-400 text-gray-700 cursor-not-allowed',
          ]"
        >
          Create User
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  props: {
    selectedComics: Array,
  },
  data() {
    return {
      firstName: '',
      lastName: '',
      birthdate: '',
      hasInsurance: false,
    }
  },
  computed: {
    isFormValid() {
      return (
        this.firstName.trim() !== '' &&
        this.lastName.trim() !== '' &&
        this.birthdate !== '' &&
        this.selectedComics.length > 0
      )
    },
  },
  methods: {
    async createUser() {
      const newUser = {
        id: Date.now(),
        firstName: this.firstName,
        lastName: this.lastName,
        birthdate: this.birthdate,
        hasInsurance: this.hasInsurance,
        favouriteComics: this.selectedComics,
      }

      try {
        const response = await axios.post(
          'http://localhost:8080/api/persons',
          newUser,
        )

        console.log('User created:', response.data)
        this.$emit('create-user', response.data)

        this.$emit('reset-comics-selection')

        this.resetForm()
      } catch (error) {
        console.error('Error al crear el usuario:', error)
      }
    },

    resetForm() {
      this.firstName = ''
      this.lastName = ''
      this.birthdate = ''
      this.hasInsurance = false
    },
  },
}
</script>
