<template>
  <div class="books">
    <BookShelf :books="books" />
  </div>
</template>

<script>
// @ is an alias to /src
import BookShelf from '@/components/BookShelf.vue';

export default {
  name: 'Books',
  components: {
    BookShelf,
  },
  props: {
    // books: Array,
  },
  data() {
    return {
      books: [{ id: '1', name: 'book1', isbn: 'book1' }],
    };
  },
};
</script>

<style scoped lang="stylus">
</style>
