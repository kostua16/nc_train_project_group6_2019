<template>
  <div>
    <div class="page-title">
      <h3>Твой тариф: {{myTariff}}</h3>
    </div>

    <form class="form">
    </form>
    <div class="row">
      <div lass="t" v-if="tariff.name != 'DEFAULT' &&  tariff.name != 'ADMINISTRATOR'" v-for="(tariff) in tariffs">
        <div class="col s12 m6 l4">
          <div class="card orange darken-1 bill-card">
            <div class="card-content white-text">
              <div class="card-header">
                <span class="card-title">{{tariff.name}}</span>
                <button v-if="tariff.name !== myTariff" class="btn waves-effect waves-light" type="button" @click.prevent="choiceTariff(tariff.name)">Перейти
                  <i class="material-icons right">send</i>
                </button>
                <button v-if="tariff.name === myTariff" class="btn waves-effect waves-light disabled" type="button">Текущий тариф</button>

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
                  <td>{{parseFloat(tariff.internet/1000).toFixed(1)}} mb</td>
                  <td>{{parseFloat(tariff.call/60).toFixed(0)}} min</td>
                  <td>{{tariff.sms}}</td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
  import {mapState} from "vuex";
  import UserPage from '../base/UserView'

  export default {
    extends: UserPage,
    data: () => ({
      changeTariff: '',
      loginRoute: '/ssp/login'
    }),
    computed: mapState({
      tariffs: state => state.CURRENT_USER!=null ? state.CURRENT_USER.availableTariffs : [],
      myTariff: state => state.CURRENT_USER!=null ? state.CURRENT_USER.tariff : '',
    }),
    methods: {
      choiceTariff(newTariff) { //настроить адресс
        if (newTariff !== '') {
          this.$store.dispatch("UPDATE_USER_PRICE_PLAN", newTariff);
        }
      }
    }
  }
</script>
