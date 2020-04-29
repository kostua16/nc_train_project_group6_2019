<template>

<div>
  <div class="page-title">
    <h3>Счет</h3>

    <button class="btn waves-effect waves-light btn-small"  @click.prevent="refreshData">
      <i class="material-icons">refresh</i>
    </button>
  </div>

  <div class="row">
    <div class="col s12 m6 l4">
      <div class="card light-blue bill-card">
        <div class="card-content white-text">
          <span class="card-title">Баланс</span>

          <p class="currency-line">
            <span>{{balance}} Р</span>
          </p>
          <br/>
          <span class="card-title">Номер телефона</span>
          <p class="currency-line">
            <span><i class="material-icons prefix">phone</i> {{userPhone}}</span>
          </p>
        </div>
      </div>
    </div>

    <div class="col s12 m6 l8">
      <div class="card orange darken-3 bill-card">
        <div class="card-content white-text">
          <div class="card-header">
            <span class="card-title">Остатки трафика</span>
          </div>
          <table>
            <thead>
            <tr>
              <th>Интернет</th>
              <th>Минуты</th>
              <th>СМС</th>
            </tr>
            </thead>

            <tbody>
            <tr>
              <td>{{internet}} Мб</td>
              <td>{{minutes}} мин</td>
              <td>{{sms}} шт</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<script>
  import UserPage from '../base/UserView'
  import {mapState} from "vuex";
export default {
  extends: UserPage,
  data: () => ({ loginRoute: '/ssp/login' }),
  computed: mapState({
    balance: state => state.CURRENT_USER!=null ? (parseFloat(state.CURRENT_USER.balance)).toFixed(0) : 0,
    minutes: state => state.CURRENT_USER!=null ? (parseFloat(state.CURRENT_USER.minutes)/60).toFixed(0) : 0,
    sms: state => state.CURRENT_USER!=null ? (parseFloat(state.CURRENT_USER.sms)).toFixed(0) : 0,
    internet: state => state.CURRENT_USER!=null ? (parseFloat(state.CURRENT_USER.internet)/1000).toFixed(1) : 0,
    userPhone: state => state.CURRENT_USER!=null ? state.CURRENT_USER.telephone : ''
  }),
  mounted() {
    this.refreshData();
  },
  methods: {
    refreshData(){
      this.$store.dispatch("SYNC_CURRENT_USER");
    }
  }
}
</script>

