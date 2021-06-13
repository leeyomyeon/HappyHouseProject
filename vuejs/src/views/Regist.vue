<template>
  <div class="h-100">
    <user-regist-form />
  </div>
</template>

<script>
import UserRegistForm from '@/components/user/UserRegistForm.vue';
export default {
  name: 'Regist',
  components: {
    UserRegistForm,
  },
};
</script>

<style>
.h-100 {
  height: 100%;
  background-color: #4e73df;
}
</style>
