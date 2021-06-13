<template>
  <div class="h-100">
    <login-form />
  </div>
</template>

<script>
import LoginForm from '@/components/user/LoginForm.vue';
export default {
  name: 'Login',
  components: {
    LoginForm,
  },
};
</script>

<style>
.h-100 {
  height: 100%;
  background-color: #4e73df !important;
}
</style>
