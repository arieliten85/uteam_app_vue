<template>
  <div class="flex flex-col gap-2">
    <div
      v-for="comic in comics"
      :key="comic.id"
      @click="selectComic(comic)"
      :class="[
        'comic-card p-2 bg-white shadow-md rounded-lg cursor-pointer flex flex-row items-center',
        { 'bg-blue-100': comic.isSelected },
      ]"
    >
      <img
        :src="comic.thumbnail.path + '.' + comic.thumbnail.extension"
        alt="comic image"
        class="w-16 h-24 object-cover mr-2"
      />
      <h3 class="font-semibold text-left text-sm">{{ comic.title }}</h3>
    </div>
  </div>
</template>

<script>
export default {
  props: ['comics'],
  methods: {
    selectComic(comic) {
      comic.isSelected = !comic.isSelected
      this.$emit('select-comic', comic)
    },
  },
}
</script>

<style scoped>
.comic-card {
  width: 100%;
}
</style>
