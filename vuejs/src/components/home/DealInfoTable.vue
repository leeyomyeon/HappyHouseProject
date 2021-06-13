<template>
  <transition name="fade" mode="out-in">
    <div v-if="isSelected">
      <v-card-title>
        <v-menu
          ref="menu"
          v-model="menu"
          :close-on-content-click="false"
          :return-value.sync="date"
          transition="scale-transition"
          offset-y
          max-width="290px"
          min-width="auto"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              v-model="date"
              label="Picker in menu"
              prepend-icon="mdi-calendar"
              readonly
              v-bind="attrs"
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
            v-model="date"
            :max="new Date().toISOString().substr(0, 7)"
            type="month"
            no-title
            scrollable
          >
            <v-spacer></v-spacer>
            <v-btn text color="primary" @click="menu = false"> Cancel </v-btn>
            <v-btn text color="primary" @click="dateSelect()"> OK </v-btn>
          </v-date-picker>
        </v-menu>
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
        v-model="selected"
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
        show-select
        item-key="id"
        @page-count="pageCount = $event"
        @click:row="handleClick"
      ></v-data-table>
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
import axios from 'axios';

export default {
  name: 'DealInfoTable',
  data: function () {
    return {
      date: new Date().toISOString().substr(0, 7),
      menu: false,
      modal: false,
      totalPages: 0,
      pageCount: 0,
      page: 1,
      totalVisible: 7,
      search: '',
      itemsPerPage: 10,
      headers: [
        { text: '이름', align: 'center', sortable: false, value: 'name' },
        { text: '주거형태', align: 'center', sortable: false, value: 'type' },
        { text: '거래금액', align: 'center', sortable: false, value: 'dealAmount' },
        { text: '전용면적', align: 'center', sortable: false, value: 'area' },
        { text: '층', align: 'center', sortable: false, value: 'floor' },
        { text: '건축년도', align: 'center', sortable: false, value: 'buildYear' },
        { text: '거래일', align: 'center', sortable: false, value: 'dealDay' },
      ],
      selected: [],
    };
  },
  computed: {
    items() {
      // 배열을 합치기위해
      // spread operator 적용

      return [...this.$store.getters.aptTrades, ...this.$store.getters.homeTrades];
    },
    isSelected() {
      return this.$store.getters.isSelected;
    },
    loading() {
      return this.$store.getters.loading;
    },
  },
  created() {
    this.selected = this.$store.getters.seleceted;
  },
  mounted() {
    this.selected = [];
  },
  methods: {
    async handleClick(value) {
      let center = await this.getGeocode(value);

      this.$store.commit('SET_CENTER', center.position);
    },
    async getMarker(val) {
      // console.log(val);
      let markers = [];

      for (let i = 0; i < val.length; ++i) {
        markers.push(await this.getGeocode(val[i]));
      }

      console.log(val);

      this.$store.commit('SET_MARKERS', markers);
    },
    async getGeocode(val) {
      let marker = {};
      const SERVICE_KEY = process.env.VUE_APP_GEOCODE_API_KEY;

      const SERVICE_URL = 'https://maps.googleapis.com/maps/api/geocode/json';

      const params = {
        address: `${this.$store.getters.selectCity}+${this.$store.getters.selectGugun}+${this.$store.getters.selectDong}+${val.jibun}`,
        key: SERVICE_KEY,
      };

      await axios
        .get(SERVICE_URL, {
          params,
        })
        .then((response) => {
          marker = {
            position: {
              lat: response.data.results[0].geometry.location.lat,
              lng: response.data.results[0].geometry.location.lng,
            },
            val: val,
          };
        })
        .catch((error) => {
          console.dir(error);
        });

      return marker;
    },
    async dateSelect() {
      this.$refs.menu.save(this.date);
      let proceesingDate = this.date.replace('-', '');

      this.loading = true;
      await this.$store.dispatch('getAptTrades', {
        code: this.$store.getters.selectCode,
        dong: this.$store.getters.selectDong,
        date: proceesingDate,
      });
      await this.$store.dispatch('getHomeTrades', {
        code: this.$store.getters.selectCode,
        dong: this.$store.getters.selectDong,
        date: proceesingDate,
      });

      this.loading = false;
    },
  },
  watch: {
    selected: function (val) {
      // console.log(val);

      // 차집합
      // let newSelect = [];
      // if (val.length < oldval.length) {
      //   newSelect.push(oldval.filter((x) => !val.includes(x)));
      //   console.log(newSelect);
      // } else {
      //   newSelect.push(val.filter((x) => !oldval.includes(x)));
      //   console.log(newSelect);
      // }
      this.getMarker(val);
    },
    isSelected: function () {
      this.selected = [];
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
