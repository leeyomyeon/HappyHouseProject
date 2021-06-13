<template>
  <v-main class="gray">
    <router-view />
  </v-main>
</template>

<script>
export default {
  name: 'mainView',
};
</script>

<style>
.gray {
  background-color: #e0e0e0;
}
</style>
