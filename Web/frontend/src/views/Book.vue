<template>
  <div class="book">
    <div>
      <table>
        <tr>
          <th>Key</th>
          <th>Value</th>
        </tr>
        <tr
          v-for="(value, key) in bookValues"
          :key="key"
        >
          <td>
            {{ key }}
          </td>
          <td>
            {{ value }}
          </td>
        </tr>
        <tr v-if="status">
          <td colspan="2">
            <button
              class="button"
              @click="deleteBook"
            >
              Remove Book
            </button>
          </td>
        </tr>
        <tr v-if="!status">
          <td colspan="2">
            <router-link to="/books">
              Books
            </router-link>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Book',
  components: {
    // EditableText,
    // NewBook,
  },
  computed: {
    bookValues() {
      return this.$store.state.BOOK;
    },
    status() {
      return this.$store.state.STATUS_DELETE_BOOK;
    },
  },
  mounted() {
    this.$store.dispatch('GET_BOOK', this.$route.params.id);
  },
  methods: {
    // cheak(){
    //
    // },
    deleteBook() {
      this.$store.dispatch('DELETE_BOOK', this.$route.params.id);
    },
  },
};
</script>

<style scoped lang="stylus">
  .book
    margin-left: auto;
    margin-right: auto;
    margin-top: 20px;

  table
    border: 2px solid #42b983;
    border-radius: 3px;
    background-color: #fff;


  th
    background-color: #42b983;
    color: rgba(255,255,255,0.66);
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;

  td
    background-color: #f9f9f9;


  th, td
    min-width: 120px;
    padding: 10px 20px;


  th.active
    color: #fff;


  th.active .arrow
    opacity: 1;


  .arrow
    display: inline-block;
    vertical-align: middle;
    width: 0;
    height: 0;
    margin-left: 5px;
    opacity: 0.66;


  .arrow.asc
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-bottom: 4px solid #fff;

  .arrow.dsc
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-top: 4px solid #fff;

  .button
    border: 2px solid #42b983;
    border-radius: 3px;
    background-color: #fff;
    cursor : pointer;

  .button:hover
    border: 2px solid #348c62;
    border-radius: 3px;
    background-color: #cecece;
    cursor : pointer;

  div
    display: inline-block
</style>
