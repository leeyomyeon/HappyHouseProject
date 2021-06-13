import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';

import createPersistedState from 'vuex-persistedstate';

import jwt_decode from 'jwt-decode';
import { findById } from '@/api/user.js';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    drawer: false,
    isSelected: false,
    isBackward: false,
    loading: false,
    loadingComplete: false,
    backOffset: '',
    backPage: '',
    selectCity: '',
    selectGugun: '',
    selectDong: '',
    selectCode: '',
    aptTrades: [],
    homeTrades: [],
    center: {},
    markers: [],
    commercialMarkers: [],

    isLogin: false, // 로그인 여부
    userInfo: null,

    beforePath: '',
  },
  getters: {
    drawer(state) {
      return state.drawer;
    },
    selectCity(state) {
      return state.selectCity;
    },
    selectGugun(state) {
      return state.selectGugun;
    },
    selectDong(state) {
      return state.selectDong;
    },
    selectCode(state) {
      return state.selectCode;
    },
    aptTrades(state) {
      return state.aptTrades;
    },
    homeTrades(state) {
      return state.homeTrades;
    },
    isSelected(state) {
      return state.isSelected;
    },
    center(state) {
      return state.center;
    },
    markers(state) {
      return state.markers;
    },
    isBackward(state) {
      return state.isBackward;
    },
    backOffset(state) {
      return state.backOffset;
    },
    backPage(state) {
      return state.backPage;
    },
    beforePath(state) {
      return state.beforePath;
    },
    loading(state) {
      return state.loading;
    },
    loadingComplete(state) {
      return state.loadingComplete;
    },
    commercialMarkers(state) {
      return state.commercialMarkers;
    },
  },
  mutations: {
    TOGGLE_DRAWER(state) {
      state.drawer = !state.drawer;
    },
    TOGGLE_LOADING(state, payload) {
      state.loading = payload;
    },
    TOGGLE_LOADING_COMPLETE(state, payload) {
      state.loadingComplete = payload;
    },
    TOGGLE_IS_SELECTED(state, payload) {
      state.isSelected = payload;
    },
    TOGGLE_IS_BACKWARD(state, payload) {
      state.isBackward = payload;
    },
    SET_DRAWER(state, payload) {
      state.drawer = payload;
    },
    SET_SELECT_CITY(state, city) {
      state.selectCity = city;
    },
    SET_SELECT_GUGUN(state, gugun) {
      state.selectGugun = gugun;
    },
    SET_SELECT_DONG(state, dong) {
      state.selectDong = dong;
    },
    SET_SELECT_CODE(state, code) {
      state.selectCode = code;
    },
    SET_APT_TRADES(state, aptTrades) {
      state.aptTrades = aptTrades;

      for (let i = 0; i < state.aptTrades.length; i++) {
        state.aptTrades[i].id = 'a' + i;
        state.aptTrades[i].type = '아파트';
      }

      console.log('파트 : ' + state.aptTrades.length);
    },
    SET_HOME_TRADES(state, homeTrades) {
      state.homeTrades = homeTrades;

      for (let i = 0; i < state.homeTrades.length; i++) {
        state.homeTrades[i].id = 'h' + i;
        state.homeTrades[i].type = '주택';
      }
      console.log('집 : ' + state.homeTrades.length);
    },
    EMPTY_APT_TRADES(state) {
      state.aptTrades = [];
    },
    EMPTY_HOME_TRADES(state) {
      state.homeTrades = [];
    },
    SET_CENTER(state, location) {
      state.center = location;
    },
    SET_MARKERS(state, markers) {
      state.markers = markers;
    },
    SET_BACK_OFFSET(state, offset) {
      state.backOffset = offset;
    },
    SET_BACK_PAGE(state, page) {
      state.backPage = page;
    },
    SET_BEFORE_PATH(state, beforePath) {
      state.beforePath = beforePath;
    },
    SET_COMMERCIAL_MARCKERS(state, commercialMarkers) {
      state.commercialMarkers = commercialMarkers;
    },

    setIsLogined(state, isLogin) {
      state.isLogin = isLogin;
    },
    setUserInfo(state, userInfo) {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    logout(state) {
      state.isLogin = false;
      state.userInfo = null;
    },
  },
  actions: {
    async getAptTrades({ commit }, info) {
      await axios
        .get(`http://localhost:8078/apttrade/${info.code}/${info.date}`)
        .then((response) => {
          // 해당 동만 찾기 위함
          let dealInfo = response.data.filter(function (item) {
            // API 상에서 공백이 포함되서 반환됨 이를 없에기위해 trim()을 사용
            return item.dong.trim() == info.dong.trim();
          });

          dealInfo.sort(function (a, b) {
            return a.name < b.name ? -1 : a.name > b.name ? 1 : 0;
          });

          commit('SET_APT_TRADES', dealInfo);
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    async getHomeTrades({ commit }, info) {
      await axios
        .get(`http://localhost:8078/hometrade/${info.code}/${info.date}`)
        .then((response) => {
          // 해당 동만 찾기 위함
          let dealInfo = response.data.filter(function (item) {
            // API 상에서 공백이 포함되서 반환됨 이를 없에기위해 trim()을 사용
            return item.dong.trim() == info.dong.trim();
          });

          dealInfo.sort(function (a, b) {
            return a.name < b.name ? -1 : a.name > b.name ? 1 : 0;
          });

          commit('SET_HOME_TRADES', dealInfo);
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    async getDongGeocode({ commit }, info) {
      const SERVICE_KEY = process.env.VUE_APP_GEOCODE_API_KEY;

      const SERVICE_URL = 'https://maps.googleapis.com/maps/api/geocode/json';

      const params = {
        address: `${info.city}+${info.gugun}+${info.dong}`,
        key: SERVICE_KEY,
      };

      await axios
        .get(SERVICE_URL, {
          params,
        })
        .then((response) => {
          commit('SET_CENTER', response.data.results[0].geometry.location);
        })
        .catch((error) => {
          console.dir(error);
        });
    },

    async GET_MEMBER_INFO({ commit }, token) {
      let decode = jwt_decode(token);

      await findById(
        decode.userid,
        (response) => {
          if (response.data.message === 'success') {
            console.log(response);
            commit('setUserInfo', response.data.userInfo);
            // router.push("/");
            // router.go(router.currentRoute);
          } else {
            console.log('유저 정보 없음!!');
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    LOGOUT({ commit }) {
      commit('logout');
      localStorage.removeItem('access-token');
      // axios.defaults.headers.common["auth-token"] = undefined;
    },
  },
  plugins: [createPersistedState()],
});
