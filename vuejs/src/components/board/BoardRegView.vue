<template>
  <v-row justify="center" align="center" class="w-80 mx-auto">
    <v-col xl="10" lg="12" md="9">
      <v-card-title class="text-center" align="center">
        <h1>{{ name }}</h1>
      </v-card-title>
      <v-card class="pa-15">
        <v-text-field v-model="item.title" label="제목"></v-text-field>
        <v-row>
          <v-col cols="6">
            <div>{{ userInfo.id }}</div>
          </v-col>
        </v-row>
        <br />
        <br />
        <div style="boader">
          <v-textarea auto-grow solo row-height="50" label="내용" v-model="item.contents">
          </v-textarea>
        </div>

        <div align="center">
          <v-btn color="primary" outlined @click="regist" class="mb-5 mr-2"> 작성 </v-btn>
          <v-btn color="error" outlined @click="cancle" class="mb-5"> 취소 </v-btn>
        </div>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import http from '@/util/http-common';
import { mapState } from 'vuex';

export default {
  components: {},
  name: 'boardRegView',
  data: () => {
    return {
      name: '',
      item: {
        title: '',
        cotents: '',
      },
      user: {},
    };
  },
  computed: {
    ...mapState(['userInfo']),
  },
  created() {
    if (this.$route.params.boardType == 'notice') {
      this.link = '/noticeboard/';
      this.name = '공지사항';
    } else if (this.$route.params.boardType == 'qna') {
      this.link = '/qnaboard/';
      this.name = 'Q&A';
    }
  },
  mounted() {},
  methods: {
    async goBack() {
      await this.$store.commit('TOGGLE_IS_BACKWARD', true);
      await this.$store.commit('SET_BACK_OFFSET', this.$route.params.offset);
      await this.$store.commit('SET_BACK_PAGE', this.$route.params.page);

      this.$router.push(`/${this.$route.params.boardType}`);
    },
    async regist() {
      let items = {
        title: this.item.title,
        writer: this.userInfo.id,
        contents: this.item.contents,
      };

      console.log(items);
      await http
        .post(this.link + 'write', items)
        .then(() => {
          alert('글을 등록했습니다.');
        })
        .catch(() => {
          alert('에러가 발생했습니다.');
        });
      this.$router.push(`/${this.$route.params.boardType}`);
    },
    cancle() {
      this.goBack();
    },
  },
};
</script>

<style></style>
