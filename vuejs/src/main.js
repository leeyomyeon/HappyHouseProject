import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from '@/plugins/vuetify'; // path to vuetify export
import * as VueGoogleMaps from 'vue2-google-maps';
import VeeValidate from 'vee-validate';

Vue.config.productionTip = false;

Vue.use(VueGoogleMaps, {
  load: {
    key: process.env.VUE_APP_GEOCODE_API_KEY,
    libraries: 'places',
  },
});

new Vue({
  router,
  store,
  vuetify,
  VeeValidate,
  async beforeCreate() {
    let token = localStorage.getItem('access-token');
    if (store.state.userInfo == null && token) {
      await store.dispatch('GET_MEMBER_INFO', token);
    }
  },
  render: (h) => h(App),
}).$mount('#app');
