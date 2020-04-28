<template>
  <div>
  <div class="page-title">
    <h3>История</h3>
  </div>

  <div class="history-chart">
    <canvas></canvas>
  </div>
    <div class="table-of-contents">
      <form class="row" @submit.prevent="changePage">
        <div class="input-field col s2">
          <a class="waves-effect waves-light btn" @click.prevent="changePage(page-1)"><i class="material-icons right">arrow_back</i>{{page-1}}</a>
        </div>
        <div class="input-field col s2">
          <label for="history_page">Номер страницы</label>
          <input name="history_page" id="history_page" class="input-field" type="number" v-model="page" />
        </div>
        <div class="input-field col s2 ">
          <a class="waves-effect waves-light btn" @click.prevent="changePage(page+1)">{{page+1}}<i class="material-icons right">arrow_forward</i></a>
        </div>
      </form>
      <table class="col s12">
        <tr class="row">
          <th class="col s2">Date</th>
          <th class="col s2">Type</th>
          <th class="col s8">Body</th>
        </tr> <!--ряд с ячейками заголовков-->
        <tr class="row" v-for = "(history) in histories">
            <td class="col s2">{{history.date}}</td>
            <td class="col s2">{{history.type}}</td>
            <td class="col s8">{{history.body}}</td>
        </tr>
      </table>
    </div>
</div>
</template>
<script>
  import UserPage from '../base/UserView'
  import {mapState} from "vuex";
    export default {
      extends: UserPage,
      data: () => ({
        loginRoute: '/crm/login',
        page: 0,
      }),
      mounted() {
        this.$store.dispatch("LOAD_HISTORY");
        this.page = this.$store.state.HISTORY_PAGE;
      },
      computed: mapState({
        histories: state => state.ACTIONS_HISTORY
      }),
      methods: {
        async changePage(value) {
          await this.$store.dispatch("UPDATE_HISTORY_PAGE", value);
          this.page = this.$store.state.HISTORY_PAGE;
        }
      }
    }
</script>

