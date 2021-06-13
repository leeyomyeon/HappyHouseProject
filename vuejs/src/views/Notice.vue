<template>
  <div class="w-80 mx-auto">
    <notice-list />
  </div>
</template>

<script>
import NoticeList from '@/components/board/NoticeListView.vue';
export default {
  name: 'Notice',
  components: {
    NoticeList,
  },
};
</script>

<style>
.w-80 {
  width: 80%;
}
</style>
