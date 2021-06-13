<template>
  <div class="h-100">
    <info-form />
  </div>
</template>

<script>
import InfoForm from '@/components/user/InfoForm.vue';
export default {
  name: 'Login',
  components: {
    InfoForm,
  },
};
</script>

<style>
.h-100 {
  height: 100%;
  background-color: #4e73df !important;
}
</style>
