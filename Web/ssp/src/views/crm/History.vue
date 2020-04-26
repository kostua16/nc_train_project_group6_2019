<template>
  <div>
  <div class="page-title">
    <h3>История</h3>
  </div>

  <div class="history-chart">
    <canvas></canvas>
  </div>
    <div class="table-of-contents">
      <table>
        <tr>
          <th>Date</th>
          <th>Type</th>
          <th>Body</th>
        </tr> <!--ряд с ячейками заголовков-->
        <tr v-for = "(history) in histories">
            <td>{{history.date}}</td>
            <td>{{history.type}}</td>
            <td>{{history.body}}</td>
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
      }),
      computed: mapState({
        histories: state => state.CURRENT_USER!=null ? state.CURRENT_USER.history : []
      }),
    }
</script>

