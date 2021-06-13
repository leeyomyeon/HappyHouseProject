<template>
  <v-app-bar class="mainNav" app color="grey lighten-3" flat>
    <v-container class="py-0 fill-height">
      <v-app-bar-nav-icon class="d-md-none" @click="toggleDrawer"></v-app-bar-nav-icon>

      <!-- <v-img
          :src="require('@/assets/happyhouse.png')"
          class="mr-5 logo"
          contain
          height="48"
          width="48"
          max-width="48"
          @click="goMain"
        /> -->

      <v-toolbar-title class="title" @click="goLink('/')">HappyHouse</v-toolbar-title>

      <div class="d-none d-md-block">
        <v-btn text @click="goLink('/')">Home</v-btn>
        <v-btn text @click="goLink('/notice')">공지사항</v-btn>
        <v-btn text @click="goLink('/qna')">Q&A</v-btn>
      </div>

      <v-spacer></v-spacer>
      <div v-if="userInfo == null">
        <v-btn text @click="goLink('/regist')"
          ><v-icon dark left> mdi-account-plus </v-icon>회원가입</v-btn
        >
        <v-btn text @click="goLink('/login')"
          ><v-icon dark left> mdi-login-variant </v-icon>로그인
        </v-btn>
      </div>
      <div v-if="userInfo !== null">
        <v-btn text @click="goLink('/userinfo')"
          ><v-icon dark left> mdi-account-box </v-icon>내정보</v-btn
        >
        <v-btn text @click.prevent="onClickLogout"
          ><v-icon dark left> mdi-logout-variant </v-icon>로그아웃
        </v-btn>
      </div>
      <!-- <v-responsive max-width="200">
          <v-text-field dense flat hide-details rounded solo-inverted></v-text-field>
        </v-responsive> -->
    </v-container>
  </v-app-bar>
</template>

<script>
import { mapState } from 'vuex';
export default {
  data: () => ({}),
  methods: {
    goLink(link) {
      if (link === '/login') {
        this.$store.commit('SET_BEFORE_PATH', this.$router.currentRoute.path);
      }
      if (link === this.$router.currentRoute.path) {
        this.$router.go(this.$router.currentRoute);
      }

      this.$router.push(link).catch(() => {});
    },
    toggleDrawer() {
      this.$store.commit('SET_DRAWER', true);
    },
    onClickLogout() {
      this.$store
        .dispatch('LOGOUT')
        .then(() => {
          // this.$router.push({ name: "" });
          if (this.$route.path !== '/') this.$router.replace('/');
          // this.$router.push(-1).catch(() => {});
        })
        .catch(() => {
          console.log('로그아웃 문제!!!');
        });
    },
  },
  computed: {
    ...mapState(['userInfo', 'isLogin']),
  },
};
</script>

<style>
.mainNav {
  margin-bottom: 100px;
}

.logo {
  cursor: pointer;
}

.title {
  margin-right: 30px;
  cursor: pointer;
}
</style>
