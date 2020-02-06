<template>
  <div class="api">
    <div>
      <ListApi
        title="Rest service"
        :apis="apiBook"
      />
    </div>
    &nbsp;<br>
    <div>
      <ListApi
        title="Proxy Rest service"
        :apis="apiProxyBook"
      />
    </div>
    &nbsp;<br>
    <div><a href="/docs/books.html">API METHODS DOCUMENTATION</a></div>
    &nbsp;<br>
  </div>
</template>

<script>
// @ is an alias to /src
import ListApi from '@/components/ListApi.vue';

export default {
  name: 'Books',
  components: {
    ListApi,
  },
  data() {
    return {
      books: [{ id: '1', name: 'book1', isbn: 'book1' }],
    };
  },
  computed: {
    apiBook() {
      return this.$store.state.API_BOOKS;
    },
    apiProxyBook() {
      return this.$store.state.API_PROXY_BOOKS;
    },
  },
  mounted() {
    this.$store.dispatch('GET_API_BOOKS');
    this.$store.dispatch('GET_API_PROXY_BOOKS');
  },
};
</script>

<style scoped lang="stylus">
  div
    display: inline-block

  .api
    margin-left: auto;
    margin-right: auto;
    margin-top: 20px;
</style>
