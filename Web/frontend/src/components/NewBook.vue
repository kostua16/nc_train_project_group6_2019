<template>
  <div class="newBook">
    <table>
      <tr>
        <td>Name</td>
        <td>
          <input
            v-model="name"
            type="text"
          >
        </td>
      </tr>
      <tr>
        <td>ISBN</td>
        <td>
          <input
            v-model="isbn"
            type="text"
          >
        </td>
      </tr>
      <tr>
        <td />
        <td>
          <button
            class="button"
            @click="saveChanges"
          >
            Add New Book
          </button>
        </td>
      </tr>
    </table>
  </div>
</template>
<script>
export default {
  props: {
  },
  data() {
    return {
      name: '',
      isbn: '',
    };
  },
  computed: {
  },
  methods: {
    saveChanges() {
      const result = {
        name: this.name,
        isbn: this.isbn,
      };
      this.$store.dispatch('ADD_BOOK', result);
      this.name = '';
      this.isbn = '';
    },
  },
};
</script>
<style scoped lang="stylus">
  .newBook
    margin-left: auto;
    margin-right: auto;
    margin-top: 10px;

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
</style>
