<template>
  <div class="listApi">
    <h3>{{ title }}</h3>
    <table>
      <tr>
        <th>Method</th>
        <th>URL</th>
      </tr>
      <tr
        v-for="(api, key) in apis"
        :key="key"
      >
        <td>
          <span
            v-for="(value, key) in api.method"
            :key="key"
          >
            {{ value }}
          </span>
        </td>
        <td>
          <span
            v-for="(value, key) in api.url"
            :key="key"
          >
            <a :href="value">{{ value }}</a>
          </span>
        </td>
      </tr>
    </table>
  </div>
</template>


<script>
export default {
  name: 'ListApi',
  props: {
    apis: Array,
    title: String,
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="stylus">
  #listApi
    margin-left: auto;
    margin-right: auto;
    margin-top: 60px;

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

</style>
