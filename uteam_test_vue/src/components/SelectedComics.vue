<template>
  <div>
    <div class="grid grid-cols-2 gap-4">
      <div
        v-for="comic in comics"
        :key="comic.id"
        @click="selectComic(comic)"
        class="comic-card p-4 bg-white shadow-md rounded-lg cursor-pointer flex flex-col items-center"
      >
        <img
          :src="comic.thumbnail.path + '.' + comic.thumbnail.extension"
          alt="comic image"
          class="w-full h-48 object-cover mb-4"
        />
        <h3 class="font-semibold text-center text-lg">{{ comic.title }}</h3>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['comics'],
  methods: {
    selectComic(comic) {
      this.$emit('select-comic', comic)
    },
  },
}
</script>
