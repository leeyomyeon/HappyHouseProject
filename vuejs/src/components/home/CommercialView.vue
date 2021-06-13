<template>
  <transition name="fade" mode="out-in">
    <div v-if="loadingComplete">
      <v-card-title>
        <v-row>
          <v-col align="left" cols="12" lg="6">
            <div>
              <h1>카페/마트</h1>
            </div>
          </v-col>
          <v-col align="right" cols="12" lg="6">
            <div v-if="selectCommercial != ''">
              <v-btn color="blue-grey white--text" class="ml-10" @click="createMarkers">
                위치보기
              </v-btn>
              <v-btn v-if="isMakeMarker" color="blue-grey white--text" class="ml-5" @click="clear">
                위치지우기
              </v-btn>
            </div>
          </v-col>
          <v-col cols="12">
            <v-select
              :items="selectItem"
              @change="getCI"
              v-model="selectCommercial"
              label="선택"
              no-data-text=""
              :disabled="loading"
              :menu-props="{ bottom: true, offsetY: true }"
              attach
            ></v-select>
          </v-col>
        </v-row>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="items"
        :page.sync="page"
        :search="search"
        :loading="loading"
        loading-text="Loading... Please wait"
        no-data-text="검색된 결과가 없습니다."
        :items-per-page="itemsPerPage"
        hide-default-footer
        class="elevation-1 dataTable"
        @page-count="pageCount = $event"
        @click:row="handleClick"
      >
      </v-data-table>
      <div class="text-center pt-2">
        <v-pagination
          v-model="page"
          :length="pageCount"
          :total-visible="totalVisible"
          next-icon="mdi-menu-right"
          prev-icon="mdi-menu-left"
        ></v-pagination>
      </div>
    </div>
  </transition>
</template>

<script>
import http from '@/util/http-common';

export default {
  name: 'DealInfoTable',
  data: function () {
    return {
      selectCommercial: '',
      totalPages: 0,
      pageCount: 0,
      page: 1,
      loading: false,
      totalVisible: 7,
      search: '',
      itemsPerPage: 10,
      selectItem: [
        '스타벅스',
        '할리스커피',
        '투썸플레이스',
        '이마트',
        '롯데마트',
        '홈플러스',
        '다이소',
      ],
      items: [],
      headers: [
        { text: '이름', align: 'center', sortable: false, value: 'shopName' },
        { text: '주소', align: 'center', sortable: false, value: 'jibun_address' },
      ],
      selected: [],
      isMakeMarker: false,
    };
  },
  computed: {
    loadingComplete() {
      return this.$store.getters.loadingComplete;
    },
  },
  created() {},
  mounted() {},
  methods: {
    async createMarkers() {
      let commercialMarkers = [];
      for (let i = 0; i < this.items.length; ++i) {
        commercialMarkers.push({
          position: {
            lat: parseFloat(this.items[i].latitude),
            lng: parseFloat(this.items[i].longitude),
          },
          address: this.items[i].jibun_address,
          shopName: this.items[i].shopName,
        });
      }

      this.$store.commit('SET_COMMERCIAL_MARCKERS', commercialMarkers);

      this.isMakeMarker = true;
    },
    clear() {
      this.isMakeMarker = false;
      this.$store.commit('SET_COMMERCIAL_MARCKERS', []);
    },
    async handleClick(value) {
      let position = {
        lat: parseFloat(value.latitude),
        lng: parseFloat(value.longitude),
      };

      this.$store.commit('SET_CENTER', position);
    },
    async getCI(name) {
      this.isMakeMarker = false;
      this.items = [];
      this.loading = true;

      await http
        .get(
          `/searchci/${name}/${this.$store.getters.selectCity}/${this.$store.getters.selectGugun}`
        )
        .then(({ data }) => {
          if (data) {
            this.items = data;
          }
        })
        .catch(() => {
          alert('에러가 발생했습니다.');
        });
      this.loading = false;
    },
  },
  watch: {
    loadingComplete: function (val) {
      if (val) {
        this.items = [];
        this.selectCommercial = '';
      }
    },
  },
};
</script>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
