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
              <div class="text-center text-h4 mb-4">회원가입</div>
              <validation-observer ref="observer" v-slot="{ invalid }">
                <form @submit.prevent="submit">
                  <ValidationProvider
                    name="아이디"
                    rules="required|min:4|alpha_num|idCheck"
                    v-slot="{ errors }"
                  >
                    <v-text-field
                      v-model="user.id"
                      :error-messages="errors"
                      label="ID"
                      hint="최소 4글자 이상"
                      required
                    ></v-text-field>
                  </ValidationProvider>
                  <ValidationProvider
                    name="비밀번호"
                    vid="password"
                    rules="required|min:8"
                    v-slot="{ errors }"
                  >
                    <v-text-field
                      v-model="user.pass"
                      :error-messages="errors"
                      label="PASSWORD"
                      type="password"
                      hint="최소 8글자 이상"
                      required
                    ></v-text-field>
                  </ValidationProvider>
                  <ValidationProvider
                    name="비밀번호"
                    rules="required|confirmed:password"
                    v-slot="{ errors }"
                  >
                    <v-text-field
                      v-model="chkPassword"
                      :error-messages="errors"
                      label="PASSWORD 확인"
                      type="password"
                      required
                    ></v-text-field>
                  </ValidationProvider>
                  <ValidationProvider
                    name="이메일"
                    rules="email|required|min:8"
                    v-slot="{ errors }"
                  >
                    <v-text-field
                      v-model="user.mail"
                      :error-messages="errors"
                      label="EMAIL"
                      required
                    ></v-text-field>
                  </ValidationProvider>
                  <ValidationProvider
                    name="전화번호"
                    rules="numeric|required|min:11"
                    v-slot="{ errors }"
                  >
                    <v-text-field
                      v-model="user.phone"
                      :error-messages="errors"
                      label="PHONE"
                      required
                    ></v-text-field>
                  </ValidationProvider>
                  <div class="text-center">
                    <v-btn
                      outlined
                      color="teal accent-4"
                      rounded
                      large
                      type="submit"
                      class="mb-7"
                      :disabled="invalid"
                    >
                      회원가입
                    </v-btn>
                  </div>
                </form>
              </validation-observer>
              <div class="text-center">
                <router-link to="/login" class="registerLink">로그인</router-link>
              </div>
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
import { alpha_num, numeric, email, required, min, confirmed } from 'vee-validate/dist/rules';

setInteractionMode('eager');

extend('alpha_num', {
  ...alpha_num,
  message: '{_field_}은(는) 숫자와 영어만 가능합니다. ({_value_})',
});

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

extend('email', {
  ...email,
  message: 'Email을 잘못 입력하셨습니다.',
});

extend('confirmed', {
  ...confirmed,
  message: '잘못 입력하셨습니다.',
});

extend('idCheck', {
  validate: async function (value) {
    let flag = false;

    await axios
      .get(`http://localhost:8078/user/search/${value}`)
      .then((response) => {
        if (response.data == 'ok') {
          flag = true;
        }
      })
      .catch((error) => {
        console.dir(error);
        return '서버 오류가 발생했습니다.';
      });

    if (flag) {
      return true;
    } else {
      return '이미 사용중인 ID 입니다.';
    }
  },
});

export default {
  name: 'LoginForm',
  data: function () {
    return {
      user: {
        id: '',
        pass: '',
        mail: '',
        phone: '',
        chkId: '',
      },
      chkPassword: '',
      registImg: require('@/assets/bg3.jpg'),
    };
  },
  components: {
    ValidationObserver,
    ValidationProvider,
  },
  methods: {
    async submit() {
      if (this.$refs.observer.validate()) {
        await axios
          .post(`http://localhost:8078/user/insert`, this.user)
          .then(() => {
            console.log('완료!');
            alert('회원가입이 완료됬습니다.');
            this.$router.push('/login');
          })
          .catch((error) => {
            console.dir(error);
            alert('회원가입에 실패하였습니다.');
            this.$router.push('/regist');
          });
      }
    },
    getPassword() {
      return this.user.pass;
    },
  },
  watch: {},
  computed: {},
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
