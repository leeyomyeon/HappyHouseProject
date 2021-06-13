<template>
  <transition name="fade" mode="out-in">
    <div v-if="loadingComplete">
      <v-card-title>
        <h1 align="center">지역소식</h1>
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="items"
        :page.sync="page"
        :search="search"
        :loading="loading"
        loading-text="Loading... Please wait"
        no-data-text="검색된 결과가 없습니다."
        :items-per-page="itemsPerPage"
        hide-default-footer
        class="elevation-1 dt"
        @page-count="pageCount = $event"
      >
        <template v-slot:item="row">
          <tr>
            <td>{{ row.item.title }}</td>
            <!-- <td>{{ row.item.description }}</td> -->
            <td>
              <v-btn
                class="mx-2"
                fab
                dark
                small
                color="teal darken-2"
                @click="onButtonClick(row.item.url)"
              >
                <v-icon dark>mdi-open-in-new</v-icon>
              </v-btn>
            </td>
          </tr>
        </template>
      </v-data-table>
      <div class="text-center pt-2">
        <v-pagination
          v-model="page"
          :length="pageCount"
          :total-visible="totalVisible"
          next-icon="mdi-menu-right"
          prev-icon="mdi-menu-left"
        ></v-pagination>
      </div>
    </div>
  </transition>
</template>

<script>
import http from '@/util/http-common';

export default {
  name: 'DealInfoTable',
  data: function () {
    return {
      totalPages: 0,
      pageCount: 0,
      page: 1,
      loading: false,
      totalVisible: 7,
      search: '',
      itemsPerPage: 10,
      items: [],
      headers: [
        { text: '제목', align: 'center', sortable: false, value: 'title' },
        // { text: '내용', align: 'center', sortable: false, value: 'description' },
        { text: '바로가기', align: 'center', sortable: false, value: 'url' },
      ],
    };
  },
  computed: {
    loadingComplete() {
      return this.$store.getters.loadingComplete;
    },
  },
  created() {},
  mounted() {},
  methods: {
    onButtonClick(url) {
      let win = window.open(url, '_blank');
      win.focus();
    },
    async getNews() {
      this.items = [];
      this.loading = true;

      await http
        .get(`/newscroller/${this.$store.getters.selectCity}/${this.$store.getters.selectGugun}`)
        .then(({ data }) => {
          this.items = data;
        })
        .catch(() => {
          alert('에러가 발생했습니다.');
        });

      this.loading = false;
    },
  },
  watch: {
    loadingComplete: function (val) {
      if (val) {
        this.getNews();
      }
    },
  },
};
</script>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
