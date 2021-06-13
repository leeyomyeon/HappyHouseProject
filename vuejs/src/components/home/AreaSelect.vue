<template>
  <div>
    <div class="text-center"></div>
    <v-row align="center">
      <v-col class="d-flex" cols="12" sm="4">
        <v-select
          :items="city"
          @change="getGugun"
          v-model="selectCity"
          label="도/시"
          no-data-text=""
          :menu-props="{ bottom: true, offsetY: true }"
          attach
        ></v-select>
      </v-col>
      <v-col class="d-flex" cols="12" sm="4">
        <v-select
          :items="gugun"
          label="시/구/군"
          @change="getDong"
          v-model="selectGugun"
          no-data-text="앞 항목을 선택해주세요."
          :menu-props="{ bottom: true, offsetY: true }"
          attach
        ></v-select>
      </v-col>
      <v-col class="d-flex" cols="12" sm="4">
        <v-select
          :items="dong"
          item-text="dong"
          label="동"
          v-model="selectDong"
          @change="setArea"
          no-data-text="앞 항목을 선택해주세요."
          :menu-props="{ bottom: true, offsetY: true }"
          attach
        ></v-select>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'AreaSelect',
  data: () => ({
    date: new Date().toISOString().substr(0, 7),
    city: [],
    gugun: [],
    dong: [],
    selectCity: '',
    selectGugun: '',
    selectDong: '',
    code: '',
    // items: ['Foo', 'Bar', 'Fizz', 'Buzz'],
  }),
  created() {
    this.city = [];
    this.gugun = [];
    this.dong = [];
  },
  mounted() {
    axios
      .get('http://localhost:8078/addressinfo/citylist')
      .then((response) => {
        this.city = response.data.sort();
      })
      .catch((error) => {
        this.city = [];
        console.dir(error);
      });
  },
  methods: {
    async getGugun() {
      this.$store.commit('TOGGLE_IS_SELECTED', false);
      this.$store.commit('TOGGLE_LOADING_COMPLETE', false);
      this.$store.commit('SET_COMMERCIAL_MARCKERS', []);

      await axios
        .get(`http://localhost:8078/addressinfo/gugunlist/${this.selectCity}`)
        .then((response) => {
          this.dong = [];
          this.gugun = response.data.sort();
        })
        .catch((error) => {
          console.dir(error);
        });
    },

    async getDong() {
      this.$store.commit('TOGGLE_IS_SELECTED', false);
      this.$store.commit('TOGGLE_LOADING_COMPLETE', false);
      this.$store.commit('SET_COMMERCIAL_MARCKERS', []);

      await axios
        .get(`http://localhost:8078/addressinfo/dongList/${this.selectCity}/${this.selectGugun}`)
        .then((response) => {
          response.data.sort(function (a, b) {
            return a.dong < b.dong ? -1 : a.dong > b.dong ? 1 : 0;
          });

          this.dong = response.data.map(function (element) {
            return element;
          });

          // 구 코드이므로 맨 처음것만 가져오면 된다.
          this.code = response.data[0].dongCode;
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    async setArea() {
      this.$store.commit('TOGGLE_LOADING_COMPLETE', false);
      this.$store.commit('TOGGLE_IS_SELECTED', false);
      this.$store.commit('SET_COMMERCIAL_MARCKERS', []);
      this.$store.commit('EMPTY_APT_TRADES');
      this.$store.commit('EMPTY_HOME_TRADES');

      this.$store.commit('TOGGLE_LOADING', true);
      this.$store.commit('TOGGLE_IS_SELECTED', true);
      this.$store.commit('SET_SELECT_CITY', this.selectCity);
      this.$store.commit('SET_SELECT_GUGUN', this.selectGugun);
      this.$store.commit('SET_SELECT_DONG', this.selectDong);
      this.$store.commit('SET_SELECT_CODE', this.code);

      let date = this.date.replace('-', '');

      await this.$store.dispatch('getAptTrades', {
        code: this.$store.getters.selectCode,
        dong: this.$store.getters.selectDong,
        date: date,
      });
      await this.$store.dispatch('getHomeTrades', {
        code: this.$store.getters.selectCode,
        dong: this.$store.getters.selectDong,
        date: date,
      });

      await this.$store.dispatch('getDongGeocode', {
        city: this.$store.getters.selectCity,
        gugun: this.$store.getters.selectGugun,
        dong: this.$store.getters.selectDong,
      });

      this.$store.commit('TOGGLE_LOADING', false);
      this.$store.commit('TOGGLE_LOADING_COMPLETE', true);
    },
  },
};
</script>

<style></style>
