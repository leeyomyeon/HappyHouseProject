<template>
  <v-carousel cycle show-arrows-on-hover hide-delimiter-background height="400" interval="4000">
    <v-carousel-item v-for="(item, i) in items" :key="i" :src="item.src"> </v-carousel-item>
  </v-carousel>
</template>

<script>
export default {
  name: 'homeCarousel',
  data() {
    return {
      items: [
        { src: require(`@/assets/carousel/bg1.jpg`) },
        { src: require(`@/assets/carousel/bg2.jpg`) },
        { src: require(`@/assets/carousel/bg3.jpg`) },
        { src: require(`@/assets/carousel/bg4.jpg`) },
      ],
    };
  },
};
</script>

<style></style>
