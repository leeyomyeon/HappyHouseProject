<template>
  <v-container>
    <!-- <home-carousel /> -->
    <div class="mb-10">
      <h1 align="center">HappyHouse</h1>
      <h5 align="center">검색해보세요....</h5>
      <br />
      <div v-if="userInfo !== null">
        <h4 align="center">{{ userInfo.id }}님 환영합니다.</h4>
      </div>
    </div>
    <area-select />
    <div class="mb-10">
      <v-row>
        <v-col :lg="isSelected ? 6 : 12" cols="12" class="my-auto"><gmap-map /></v-col>
        <v-col lg="6" md="12" sm="12"><deal-info-table /></v-col>
      </v-row>
    </div>
    <div>
      <v-row>
        <v-col lg="6" md="12" sm="12"><commercial-view /></v-col>
        <v-col lg="6" md="12" sm="12"><news-view /></v-col>
      </v-row>
    </div>
  </v-container>
</template>

<script>
// import HomeCarousel from '@/components/home/Carousel.vue';
import GmapMap from '@/components/home/GoogleMap.vue';
import AreaSelect from '@/components/home/AreaSelect.vue';
import DealInfoTable from '@/components/home/DealInfoTable.vue';
import NewsView from '@/components/home/NewsView.vue';
import CommercialView from '@/components/home/CommercialView.vue';
import { mapState } from 'vuex';

export default {
  name: 'homeView',
  components: {
    // HomeCarousel,
    GmapMap,
    AreaSelect,
    DealInfoTable,
    NewsView,
    CommercialView,
  },
  created() {
    this.$store.commit('TOGGLE_IS_SELECTED', false);
    this.$store.commit('TOGGLE_LOADING_COMPLETE', false);
    this.$store.commit('SET_COMMERCIAL_MARCKERS', []);
  },
  computed: {
    isSelected() {
      return this.$store.getters.isSelected;
    },
    ...mapState(['userInfo', 'isLogin']),
  },
};
</script>
