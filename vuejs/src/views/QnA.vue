<template>
  <div class="w-80 mx-auto">
    <qna-list />
  </div>
</template>

<script>
import QnaList from '@/components/board/QnAListView.vue';
export default {
  name: 'QnA',
  components: {
    QnaList,
  },
};
</script>

<style>
.w-80 {
  width: 80%;
}
</style>
