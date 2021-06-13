<template>
  <v-row justify="center" align="center" class="w-80 mx-auto">
    <v-col xl="10" lg="12" md="9">
      <v-card-title class="text-center" align="center">
        <h1>{{ name }}</h1>
      </v-card-title>
      <v-card class="pa-15">
        <div v-if="!isModify">
          <h2>{{ item.title }}</h2>
        </div>
        <div v-if="isModify">
          <v-text-field v-model="item.title" label="제목"></v-text-field>
        </div>
        <v-row>
          <v-col cols="6">
            <div>{{ item.writer }}</div>
          </v-col>
          <v-col cols="6">
            <div align="right">{{ item.regDate }}</div>
          </v-col>
        </v-row>
        <br />
        <br />
        <div style="boader">
          <v-textarea
            auto-grow
            solo
            row-height="50"
            :readonly="!isModify"
            label="Label"
            v-model="item.contents"
          >
          </v-textarea>
        </div>

        <v-row>
          <div v-if="userInfo.id == item.writer">
            <v-col cols="6" sm="12">
              <div v-if="!isModify" align="left">
                <v-btn color="primary" outlined @click="modify" class="mb-5 mr-2"> 글 수정 </v-btn>
                <v-btn color="error" outlined @click="remove" class="mb-5"> 글 삭제 </v-btn>
              </div>
              <div v-if="isModify" align="left">
                <v-btn color="success" outlined @click="regist" class="mb-5 mr-2"> 수정 </v-btn>
                <v-btn color="error" outlined @click="cancle" class="mb-5"> 취소 </v-btn>
              </div>
            </v-col>
          </div>

          <v-col cols="6" sm="12">
            <div align="right">
              <a @click="goBack">목록으로</a>
            </div>
          </v-col>
        </v-row>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import http from '@/util/http-common';
import { mapState } from 'vuex';

export default {
  components: {},
  name: 'boardView',
  data: () => {
    return {
      name: '',
      item: {},
      isModify: false,
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

    this.isModify = false;
  },
  mounted() {
    this.getBoard();
  },
  methods: {
    async getBoard() {
      await http
        .get(this.link + `select/${this.$route.params.no}`)
        .then(({ data }) => {
          this.item = data;
          console.log(this.item);
        })
        .catch(() => {
          alert('에러가 발생했습니다.');
        });
    },
    async goBack() {
      await this.$store.commit('TOGGLE_IS_BACKWARD', true);
      await this.$store.commit('SET_BACK_OFFSET', this.$route.params.offset);
      await this.$store.commit('SET_BACK_PAGE', this.$route.params.page);

      this.$router.push(`/${this.$route.params.boardType}`);
    },
    modify() {
      this.isModify = true;
    },
    async remove() {
      await http
        .delete(this.link + `delete/${this.$route.params.no}`)
        .then(() => {
          alert('글을 삭제했습니다.');
        })
        .catch(() => {
          alert('에러가 발생했습니다.');
        });

      this.goBack();
    },
    async regist() {
      await http
        .put(this.link + 'modify', this.item)
        .then(() => {
          alert('글을 수정했습니다.');
        })
        .catch(() => {
          alert('에러가 발생했습니다.');
        });

      this.isModify = false;
      this.getBoard();
    },
    cancle() {
      this.isModify = false;
      this.getBoard();
    },
  },
};
</script>

<style></style>
