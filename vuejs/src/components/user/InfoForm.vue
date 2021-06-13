<template>
  <v-container>
    <v-row justify="center" align="center" class="w-80 mx-auto">
      <v-col xl="10" lg="12" md="9">
        <v-card class="my-10">
          <v-row>
            <v-col lg="6" class="d-none d-lg-flex py-0">
              <v-img :src="registImg" class="rounded-l"> </v-img>
            </v-col>
            <v-col lg="6" class="pa-15">
              <div class="text-center text-h4 mb-4">내 정보</div>
              <validation-observer ref="observer" v-slot="{ invalid }">
                <form @submit.prevent="submit">
                  <v-text-field :value="user.id" label="ID" readonly></v-text-field>
                  <ValidationProvider
                    name="비밀번호"
                    vid="password"
                    rules="min:8"
                    v-slot="{ errors }"
                  >
                    <v-text-field
                      v-model="user.pass"
                      :error-messages="errors"
                      label="PASSWORD"
                      type="password"
                      hint="최소 8글자 이상"
                    ></v-text-field>
                  </ValidationProvider>
                  <ValidationProvider
                    name="비밀번호"
                    rules="confirmed:password"
                    v-slot="{ errors }"
                  >
                    <v-text-field
                      v-model="chkPassword"
                      :error-messages="errors"
                      label="PASSWORD 확인"
                      type="password"
                    ></v-text-field>
                  </ValidationProvider>
                  <ValidationProvider name="이메일" rules="email|min:8|max:20" v-slot="{ errors }">
                    <v-text-field
                      v-model="user.mail"
                      :error-messages="errors"
                      label="EMAIL"
                      required
                    ></v-text-field>
                  </ValidationProvider>
                  <ValidationProvider name="전화번호" rules="numeric|min:11" v-slot="{ errors }">
                    <v-text-field
                      v-model="user.phone"
                      :error-messages="errors"
                      label="PHONE"
                      required
                    ></v-text-field>
                  </ValidationProvider>
                  <div class="text-center">
                    <v-btn
                      color="teal accent-4"
                      large
                      type="submit"
                      class="mb-7 mr-5 white--text"
                      :disabled="invalid"
                    >
                      정보수정
                    </v-btn>
                    <v-btn
                      color="error"
                      @click="withdraw"
                      large
                      class="mb-7 white--text"
                      :disabled="invalid"
                    >
                      회원탈퇴
                    </v-btn>
                  </div>
                </form>
              </validation-observer>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';
import { extend, ValidationObserver, ValidationProvider, setInteractionMode } from 'vee-validate';
import { numeric, email, required, min, max, confirmed } from 'vee-validate/dist/rules';
import { mapState } from 'vuex';

setInteractionMode('eager');

extend('numeric', {
  ...numeric,
  message: '{_field_}은(는) 숫자만 가능합니다. ({_value_})',
});

extend('required', {
  ...required,
  message: '{_field_}를 입력해주세요.',
});

extend('min', {
  ...min,
  message: '{_field_}은(는) {length}글자 이상 작성해야합니다.',
});

extend('max', {
  ...max,
  message: '{_field_}은(는) {length}글자 이하로 작성해야합니다.',
});

extend('email', {
  ...email,
  message: 'Email을 잘못 입력하셨습니다.',
});

extend('confirmed', {
  ...confirmed,
  message: '잘못 입력하셨습니다.',
});

export default {
  name: 'InfoForm',
  data: function () {
    return {
      user: {
        id: '',
        pass: '',
        mail: '',
        phone: '',
      },
      chkPassword: '',
      registImg: require('@/assets/bg4.jpg'),
    };
  },
  created() {
    this.user = this.userInfo;
  },
  components: {
    ValidationObserver,
    ValidationProvider,
  },
  methods: {
    async submit() {
      let isSuccess = false;
      if (this.$refs.observer.validate()) {
        await axios
          .put(`http://localhost:8078/user/modify`, this.user)
          .then(() => {
            console.log('수정완료!');
            alert('회원수정이 완료됬습니다.');
            isSuccess = true;
            // this.$router.go(this.$router.currentRoute);
          })
          .catch((error) => {
            console.dir(error);
            alert('회원수정에 실패하였습니다.');
            this.$router.go(this.$router.currentRoute);
          });

        if (isSuccess) {
          let token = localStorage.getItem('access-token');
          await this.$store.dispatch('GET_MEMBER_INFO', token);
          console.log(this.userInfo);

          this.$router.go(this.$router.currentRoute);
        }
      }
    },
    async withdraw() {
      let isSuccess = false;
      await axios
        .delete(`http://localhost:8078/user/delete/${this.userInfo.id}`)
        .then(() => {
          console.log('탈퇴완료!');
          isSuccess = true;
        })
        .catch((error) => {
          console.dir(error);
          alert('회원탈퇴에 실패하였습니다.');
          this.$router.go(this.$router.currentRoute);
        });

      if (isSuccess) {
        this.$store
          .dispatch('LOGOUT')
          .then(() => {
            // this.$router.push({ name: "" });
            alert('회원탈퇴가 완료됬습니다.');
            if (this.$route.path !== '/') this.$router.replace('/');
            // this.$router.push(-1).catch(() => {});
          })
          .catch(() => {
            console.log('로그아웃 문제!!!');
          });
      }
    },
  },
  watch: {},
  computed: {
    ...mapState(['userInfo']),
  },
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
