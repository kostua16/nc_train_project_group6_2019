<template>
  <div id="app">
    <component :is="layout" />
  </div>
</template>
<script>

import EmptyLayout from '@/layouts/EmptyLayout';
import MainLayout from '@/layouts/MainLayout';

export default {
  components: {
    EmptyLayout, MainLayout,
  },
  computed: {
    layout() {
      return `${this.$route.meta.layout || 'Empty'}-Layout`;
    },
  },
};
</script>


<style lang="scss">
@import 'assets/index.css';
</style>
