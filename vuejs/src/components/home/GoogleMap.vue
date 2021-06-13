<template>
  <div>
    <GmapMap ref="mapRef" :center="center" :zoom="15" style="width: 100%; height: 550px">
      <!-- <google-map-cluster @click="toggleInfoWindowCluster"> -->
      <GmapMarker
        v-for="(m, index) in computeMarkers"
        :key="index"
        :position="m.position"
        :title="m.title"
        @click="toggleInfoWindow(m, index)"
      />
      <!-- </google-map-cluster> -->
      <GmapMarker
        :key="'S' + index"
        v-for="(commercial, index) in commercialMarkers"
        :id="'S' + index"
        :position="commercial.position"
        ref="commercial"
        :icon="iconSrc[commercial.shopName]"
      />
      <gmap-info-window
        :options="infoOptions"
        :position="infoWindowPos"
        :opened="infoWinOpen"
        @closeclick="infoWinOpen = false"
      >
        <div v-html="infoContent"></div>
      </gmap-info-window>
    </GmapMap>
  </div>
</template>

<script>
// import GoogleMapCluster from 'vue2-google-maps/dist/components/cluster';

const sample = [
  { latitude: 37.5743822, longitude: 126.9688505 },
  { latitude: 37.57348, longitude: 126.967792 },
];

export default {
  name: 'GoogleMap',
  data() {
    return {
      iconSrc: {
        스타벅스: { url: require('@/assets/starbucks.png') },
        할리스커피: { url: require('@/assets/hollys.png') },
        투썸플레이스: { url: require('@/assets/twosome.png') },
        이마트: { url: require('@/assets/emart.png') },
        롯데마트: { url: require('@/assets/lottemart.png') },
        홈플러스: { url: require('@/assets/homeplus.png') },
        다이소: { url: require('@/assets/daiso.png') },
      },
      data: sample,
      map: null,
      infoContent: '',
      infoWindowPos: {
        lat: 0,
        lng: 0,
      },
      infoWinOpen: false,
      infoOptions: {
        pixelOffset: {
          width: 0,
          height: -35,
        },
      },
    };
  },
  components: {
    // GoogleMapCluster,
  },
  methods: {
    toggleInfoWindow: function (marker, idx) {
      this.infoWindowPos = marker.position;

      console.log(marker);

      this.infoContent = this.getInfoWindowContent(marker);

      //check if its the same marker that was selected if yes toggle
      if (this.currentMidx == idx) {
        this.infoWinOpen = !this.infoWinOpen;
      }
      //if different marker set infowindow to open and reset current marker index
      else {
        this.infoWinOpen = true;
        this.currentMidx = idx;
      }
    },

    getInfoWindowContent: function (marker) {
      return `<div class="card">
      <div class="card-image">
        <figure class="image is-4by3">
          ${marker.val.type}
        </figure>
      </div>
      <div class="card-content">
        <div class="media">
          <div class="media-content">
            <p class="title is-4">${marker.val.name}</p>
          </div>
        </div>
        <div class="content">
          법정동 : ${marker.val.dong}
          <br>
          지번 : ${marker.val.jibun}
          <br>
          건축년도 : ${marker.val.buildYear}
        </div>
      </div>
    </div>`;
    },
  },
  watch: {
    commercialMarkers: function () {
      console.log(this.$store.getters.commercialMarkers);
    },
  },
  computed: {
    computeMarkers() {
      return this.$store.getters.markers;
    },
    center() {
      return this.$store.getters.center;
    },
    commercialMarkers() {
      return this.$store.getters.commercialMarkers;
    },
  },
  created() {
    this.$store.commit('SET_CENTER', {
      lat: 37.5013068,
      lng: 127.037471,
    });
    this.$store.commit('SET_MARKERS', []);
  },
};
</script>

<style></style>
