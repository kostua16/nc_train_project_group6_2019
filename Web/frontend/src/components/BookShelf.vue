<template>
  <div id="booksShelf">
    <table>
      <tr>
        <th>Book Id</th>
        <th>Name</th>
        <th>ISBN</th>
      </tr>
      <tr
        v-for="book in booksList"
        :key="book.id"
      >
        <td>
          <router-link :to="'/book/' + book.id">
            {{ book.id }}
          </router-link>
        </td>
        <td>
          <EditableText
            :id="book.id"
            :text="book.name"
            name="name"
            @submit="onUpdate"
          />
        </td>
        <td>
          <EditableText
            :id="book.id"
            :text="book.isbn"
            name="isbn"
            @submit="onUpdate"
          />
        </td>
      </tr>
    </table>
    <div>
      <span>
        Books per page:
        <select v-model="bookPerPageValue" @change="onUpdatePerPage">
          <option
            v-for="option in bookPerPage"
            v-bind:key="option.books"
            v-bind:value="option.books"
          >
            {{ option.books }}
          </option>
        </select>
      </span>
      <span v-if="maxPage > 1">
        Page:
        <select v-model="bookPageValue" @change="onUpdatePage">
          <option
            v-for="page in maxPage"
            v-bind:key="page"
            v-bind:value="page - 1"
          >
            {{ page }}
          </option>
        </select>
      </span>
    </div><br>
    <NewBook />
  </div>
</template>


<script>
// @ is an alias to /src
import EditableText from '@/components/EditableText.vue';
import NewBook from '@/components/NewBook.vue';

export default {
  name: 'BookShelf',
  components: {
    EditableText,
    NewBook,
  },
  data() {
    return {
      bookPerPageValue: '10',
      bookPageValue: '0',
    };
  },
  computed: {
    booksList() {
      return this.$store.state.BOOKS;
    },
    maxPage() {
      return this.$store.state.BOOKS_MAX_PAGE;
    },
    bookPerPage() {
      return [
        { books: '10' },
        { books: '25' },
        { books: '50' },
        { books: '100' },
      ];
    },
  },
  mounted() {
    this.$store.dispatch('GET_BOOKS', { size: 10, page: 0 });
  },
  methods: {
    onUpdate(payload) {
      const result = {
        id: payload.id,
        property: payload.name,
        value: payload.new_value,
      };
      this.$store.dispatch('MODIFY_BOOK', result);
    },
    onUpdatePerPage() {
      this.bookPageValue = 0;
      this.$store.dispatch('GET_BOOKS', { size: this.bookPerPageValue, page: this.bookPageValue });
    },
    onUpdatePage() {
      this.$store.dispatch('GET_BOOKS', { size: this.bookPerPageValue, page: this.bookPageValue });
    },
  },

};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="stylus">
  #booksShelf
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

  div
    display: inline-block
</style>
