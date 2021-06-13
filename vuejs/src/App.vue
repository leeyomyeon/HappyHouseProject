<template>
  <v-app id="app">
    <main-navbar />

    <main-drawer />

    <main-view />
  </v-app>
</template>

<script>
import MainNavbar from '@/components/mainComponents/Navbar.vue';
import MainDrawer from '@/components/mainComponents/Drawer.vue';
import MainView from '@/components/mainComponents/View.vue';

export default {
  name: 'App',
  components: {
    MainNavbar,
    MainDrawer,
    MainView,
  },
};
</script>

<style></style>
