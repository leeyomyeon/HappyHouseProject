<template>
  <div class="w-80 mx-auto">
    <home-view />
  </div>
</template>

<script>
// @ is an alias to /src
import HomeView from '@/components/home/View.vue';

export default {
  name: 'Home',
  components: {
    HomeView,
  },
};
</script>

<style>
.w-80 {
  width: 80%;
}
</style>
