<template>
  <div class="user-list">
    <h2 class="text-2xl font-bold mb-4">3. User actions</h2>
    <ul>
      <li
        v-for="user in users"
        :key="user.id"
        class="flex justify-between items-center p-2 bg-gray-100 rounded-md mb-2"
      >
        <span class="text-gray-800"
          >{{ user.firstName }} {{ user.lastName }}</span
        >
        <div>
          <button
            @click="openEditModal(user)"
            class="bg-blue-500 text-white px-2 py-1 rounded mr-2 transition hover:bg-blue-600"
          >
            Edit
          </button>
          <button
            @click="deleteUser(user.id)"
            class="bg-red-500 text-white px-2 py-1 mr-2 rounded transition hover:bg-red-600"
          >
            Delete
          </button>
          <button
            @click="openDetailsModal(user)"
            class="bg-green-500 text-white px-2 py-1 rounded transition hover:bg-green-600"
          >
            Details
          </button>
        </div>
      </li>
    </ul>

    <!-- Modal for editing user -->
    <transition name="fade">
      <div
        v-if="isModalOpen"
        class="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-70"
      >
        <div class="bg-white p-8 rounded-lg shadow-lg w-96">
          <h3 class="text-lg font-semibold mb-4">Edit User</h3>
          <div class="mb-4">
            <label for="edit-first-name" class="block text-sm"
              >First Name</label
            >
            <input
              v-model="editedUser.firstName"
              id="edit-first-name"
              type="text"
              class="mt-1 block w-full p-2 border border-gray-300 rounded"
            />
          </div>
          <div class="mb-4">
            <label for="edit-last-name" class="block text-sm">Last Name</label>
            <input
              v-model="editedUser.lastName"
              id="edit-last-name"
              type="text"
              class="mt-1 block w-full p-2 border border-gray-300 rounded"
            />
          </div>
          <div class="mb-4">
            <label for="edit-birthdate" class="block text-sm">Birthdate</label>
            <input
              v-model="editedUser.birthdate"
              id="edit-birthdate"
              type="date"
              class="mt-1 block w-full p-2 border border-gray-300 rounded"
            />
          </div>
          <div class="mb-4">
            <label class="flex items-center">
              <input
                type="checkbox"
                v-model="editedUser.hasInsurance"
                class="mr-2"
              />
              Has Insurance
            </label>
            <p class="text-sm text-gray-600">
              {{
                editedUser.hasInsurance
                  ? 'This user has insurance.'
                  : 'This user does not have insurance.'
              }}
            </p>
          </div>
          <button
            @click="updateUser"
            class="bg-blue-600 text-white px-4 py-2 rounded transition hover:bg-blue-700"
          >
            Save
          </button>
          <button
            @click="closeEditModal"
            class="bg-gray-300 text-black px-4 py-2 rounded ml-2 transition hover:bg-gray-400"
          >
            Cancel
          </button>
        </div>
      </div>
    </transition>

    <!-- Modal for user details -->
    <transition name="fade">
      <div
        v-if="isDetailsModalOpen"
        class="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-70"
      >
        <div class="bg-white p-8 rounded-lg shadow-lg w-96">
          <h3 class="text-lg font-semibold mb-4">User Details</h3>
          <div class="mb-2">
            <p><strong>First Name:</strong> {{ userDetails.firstName }}</p>
          </div>
          <div class="mb-2">
            <p><strong>Last Name:</strong> {{ userDetails.lastName }}</p>
          </div>
          <div class="mb-2">
            <p><strong>Birthdate:</strong> {{ userDetails.birthdate }}</p>
          </div>
          <div class="mb-2">
            <p>
              <strong>Has Insurance:</strong>
              {{ userDetails.hasInsurance ? 'Yes' : 'No' }}
            </p>
          </div>
          <div class="mb-4">
            <strong>Favourite Comics:</strong>
            <ul>
              <li v-for="comic in userDetails.favouriteComics" :key="comic.id">
                {{ comic.title }}
              </li>
            </ul>
          </div>
          <button
            @click="closeDetailsModal"
            class="bg-gray-300 text-black px-4 py-2 rounded mt-4 transition hover:bg-gray-400"
          >
            Close
          </button>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  props: {
    users: Array,
  },
  data() {
    return {
      isModalOpen: false,
      editedUser: {},
      isDetailsModalOpen: false,
      userDetails: {},
    }
  },
  methods: {
    async deleteUser(userId) {
      try {
        await axios.delete(`http://localhost:8080/api/persons/${userId}`)
        this.$emit('user-deleted', userId)
      } catch (error) {
        console.error('Error deleting user:', error)
      }
    },
    openEditModal(user) {
      this.editedUser = { ...user }
      this.isModalOpen = true
    },
    closeEditModal() {
      this.isModalOpen = false
      this.editedUser = {}
    },
    async updateUser() {
      try {
        if (!this.editedUser.hasInsurance) {
          this.editedUser.hasInsurance = false
        }
        await axios.put(
          `http://localhost:8080/api/persons/${this.editedUser.id}`,
          this.editedUser,
        )
        this.$emit('user-updated', this.editedUser)
        this.closeEditModal()
      } catch (error) {
        console.error('Error updating user:', error)
      }
    },
    openDetailsModal(user) {
      this.userDetails = user
      this.isDetailsModalOpen = true
    },
    closeDetailsModal() {
      this.isDetailsModalOpen = false
      this.userDetails = {}
    },
  },
}
</script>
