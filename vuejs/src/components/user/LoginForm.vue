<template>
  <v-container>
    <v-row :justify="center" :align="center" class="w-80 mx-auto">
      <v-col xl="10" lg="12" md="9">
        <v-card class="my-10">
          <v-row>
            <v-col lg="6" class="d-none d-lg-flex py-0">
              <v-img :src="loginImg" class="rounded-l"> </v-img>
            </v-col>
            <v-col lg="6" class="pa-15">
              <div class="text-center text-h4 mb-4">로그인</div>
              <validation-observer ref="observer">
                <form @submit.prevent="submit">
                  <ValidationProvider name="아이디" rules="required" v-slot="{ errors }">
                    <v-text-field
                      v-model="user.userid"
                      :error-messages="errors"
                      ref="inInput"
                      label="ID"
                      required
                    ></v-text-field>
                  </ValidationProvider>
                  <ValidationProvider name="비밀번호" rules="required" v-slot="{ errors }">
                    <v-text-field
                      v-model="user.password"
                      :error-messages="errors"
                      ref="psInput"
                      label="PASSWORD"
                      type="password"
                      required
                    ></v-text-field>
                  </ValidationProvider>

                  <v-checkbox v-model="saveId" label="아이디 저장"></v-checkbox>

                  <div class="text-center">
                    <v-btn outlined color="teal accent-4" rounded type="submit" large class="mb-7">
                      로그인
                    </v-btn>
                  </div>
                </form>
              </validation-observer>
              <div class="text-center">
                <router-link to="/regist" class="registerLink">회원가입</router-link>
              </div>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { extend, ValidationObserver, ValidationProvider, setInteractionMode } from 'vee-validate';
import { required } from 'vee-validate/dist/rules';
import { login } from '@/api/user.js';

setInteractionMode('eager');

extend('required', {
  ...required,
  message: '{_field_}를 입력해주세요.',
});

export default {
  name: 'LoginForm',
  data: function () {
    return {
      center: 'center',
      user: {
        userid: '',
        password: '',
      },
      loginImg: require('@/assets/bg2.jpg'),
      isLoginError: false,
      saveId: false,
    };
  },
  components: {
    ValidationObserver,
    ValidationProvider,
  },
  mounted() {
    let id = this.getCookie('saveid');
    console.log(id);
    if (id) {
      this.saveId = true;
      this.user.userid = id;
    }
  },
  methods: {
    async submit() {
      if (this.$refs.observer.validate()) {
        this.confirm();
      }
    },
    async confirm() {
      localStorage.setItem('access-token', '');
      await login(
        this.user,
        (response) => {
          if (response.data.message === 'success') {
            let token = response.data['access-token'];
            this.$store.commit('setIsLogined', true);
            localStorage.setItem('access-token', token);

            this.$store.dispatch('GET_MEMBER_INFO', token);

            this.$router.push('/');
          } else {
            this.$refs.psInput.clearableCallback();
            this.isLoginError = true;
            alert('아이디 또는 비밀번호가 일치하지 않습니다.');
          }
        },
        (error) => {
          console.error(error);
        }
      );

      if (this.saveId) {
        this.saveCookie();
      } else {
        this.deleteCookie();
      }
    },
    saveCookie() {
      let exdate = new Date();
      // 쿠키를 유지할 날짜 설정
      exdate.setDate(exdate.getDate() + 365);

      let cookieName = 'saveid';
      let cookieValue = escape(this.user.userid) + '; expires=' + exdate.toGMTString();
      document.cookie = cookieName + '=' + cookieValue;
    },
    deleteCookie() {
      let expireDate = new Date();
      expireDate.setDate(expireDate.getDate() - 1);

      let cookieName = 'saveid';

      document.cookie = cookieName + '=' + '; expires=' + expireDate.toGMTString();
    },
    getCookie(cookieName) {
      cookieName = cookieName + '=';
      let cookieData = document.cookie;
      let start = cookieData.indexOf(cookieName);
      let cookieValue = '';
      if (start != -1) {
        start += cookieName.length;
        let end = cookieData.indexOf(';', start);
        if (end == -1) {
          end = cookieData.length;
        }
        cookieValue = cookieData.substring(start, end);
      }

      return unescape(cookieValue);
    },
  },
  watch: {},
};
</script>

<style scoped>
.login-btn {
  width: 30%;
}

.w-80 {
  width: 80%;
}

.h-80 {
  height: 80%;
}

.registerLink:link {
  text-decoration: none;
}

.registerLink:link {
  text-decoration: none;
}

.registerLink:visited {
  text-decoration: none;
}

.registerLink:hover {
  text-decoration: underline;
}

.registerLink:active {
  text-decoration: underline;
}
</style>
