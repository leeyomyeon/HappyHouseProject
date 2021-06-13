<template>
  <v-container>
    <v-card-title>
      공지사항
      <div v-if="isLogin">
        <v-btn align="right" color="blue-grey white--text" class="ml-10" @click="moveReg">
          공지작성
        </v-btn>
      </div>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
        @click:append="goSearch"
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :page.sync="page"
      :items-per-page="itemsPerPage"
      :server-items-length="totalPages"
      :loading="loading"
      :search="search"
      no-data-text="검색된 결과가 없습니다."
      hide-default-footer
      class="elevation-1 dataTable"
      @click:row="handleClick"
    ></v-data-table>
    <div class="pt-2">
      <v-pagination
        v-model="page"
        :length="totalPageCnt"
        :total-visible="totalVisible"
        next-icon="mdi-menu-right"
        prev-icon="mdi-menu-left"
      ></v-pagination>
    </div>
  </v-container>
</template>

<script>
import { mapState } from 'vuex';
import http from '@/util/http-common';

export default {
  name: 'NoticeListView',
  data() {
    return {
      totalPages: 0,
      items: [],
      loading: true,
      page: 1,
      search: '',
      totalVisible: 7,
      itemsPerPage: 10,
      headers: [
        { text: '글 번호', align: 'center', sortable: false, value: 'boardNum' },
        { text: '글 제목', align: 'center', sortable: false, value: 'title' },
        { text: '작성자', align: 'center', sortable: false, value: 'writer' },
        { text: '등록일', align: 'center', sortable: false, value: 'regDate' },
      ],
      offset: 0,
    };
  },
  watch: {
    page: {
      handler() {
        this.getDataFromApi();
      },
      deep: true,
    },
  },
  mounted() {
    this.getDataFromApi();
  },
  methods: {
    async getDataFromApi() {
      this.loading = true;
      if (this.$store.getters.isBackward) {
        this.offset = this.$store.getters.backOffset;
        this.page = this.$store.getters.backPage;
        this.$store.commit('TOGGLE_IS_BACKWARD', false);
      } else {
        this.offset = (this.page - 1) * this.itemsPerPage;
      }
      let limit = 10;

      await http
        .get(`/noticeboard/pagelink/${limit}/${this.offset}`)
        .then(({ data }) => {
          this.items = data;
          this.loading = false;
        })
        .catch(() => {
          alert('에러가 발생했습니다.');
        });
    },
    handleClick(value) {
      this.$router.push({
        name: 'board',
        params: { boardType: 'notice', no: value.boardNum, offset: this.offset, page: this.page },
      });
    },
    goSearch() {
      console.log('hi');
    },
    moveReg() {
      this.$router.push({
        name: 'boardReg',
        params: { boardType: 'notice', offset: this.offset, page: this.page },
      });
    },
  },
  created() {
    http
      .get('/noticeboard/pagelink/totalCnt')
      .then(({ data }) => {
        this.totalPages = data;
      })
      .catch(() => {
        alert('에러가 발생했습니다.');
      });
  },

  computed: {
    totalPageCnt() {
      return Math.ceil(this.totalPages / this.itemsPerPage);
    },
    ...mapState(['isLogin']),
  },
};
</script>

<style>
.dataTable {
  cursor: pointer;
}
</style>
