<template>
  <v-navigation-drawer v-model="drawer" app temporary>
    <v-list nav dense>
      <v-list-item-group v-model="group" active-class="deep-purple--text text--accent-4">
        <v-list-item to="/">
          <v-list-item-icon>
            <v-icon>mdi-home</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Home</v-list-item-title>
        </v-list-item>

        <v-list-item to="/notice">
          <v-list-item-icon>
            <v-icon>mdi-bullhorn</v-icon>
          </v-list-item-icon>
          <v-list-item-title>공지사항</v-list-item-title>
        </v-list-item>

        <v-list-item to="/qna">
          <v-list-item-icon>
            <v-icon>mdi-help-circle</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Q&A</v-list-item-title>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  name: 'mainDrawer',
  data: function () {
    return {
      group: [],
    };
  },
  computed: {
    drawer: {
      get() {
        return this.$store.getters.drawer;
      },
      set(val) {
        this.$store.commit('SET_DRAWER', val);
      },
    },
  },
  methods: {},
};
</script>
