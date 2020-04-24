<template>
<div>
  <div class="page-title">
    <h3>Твой тариф: {{TariffOnline}}</h3>
  </div>

  <form class="form">

    <div class="row">
        <input  type="text"  v-model="changeTariff" >
    </div>
    <button class="btn waves-effect waves-light" type="button"  @click.prevent="choiceTariff">Перейти
      <i class="material-icons right">send</i>
    </button>
  </form>
  <div class="row">
    <div lass="t" v-if = "tariff.name != 'DEFAULT' &&  tariff.name != 'ADMINISTRATOR'" v-for = "(tariff) in tariffs">
      <div class="col s12 m6 l4">
        <div class="card orange darken-1 bill-card">
          <div class="card-content white-text">
            <div class="card-header">
              <span class="card-title">{{tariff.name}}</span>
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
import axios from 'axios'
export default {
  data: () => ({
    tariffs: {tariff:[]},
    myTariff: '',
    changeTariff: ''
  }),
  computed: { //отслеживает состояние функции
    TariffOnline(){
      return this.myTariff
    }
  },
  mounted() { //запускает сразу какогда ap вставится в ap
    this.getTariff()
    this.choiceTariff()
  },
    methods: {
    getTariff(){ //настроить адресс
      axios.get('http://localhost:8101/showtariff/?token='+localStorage.getItem('token')).then(response => {
         this.tariffs = response.data.tariffs
          this.myTariff=response.data.user
         console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
    },
        choiceTariff() { //настроить адресс
          if (this.changeTariff!=''){
              axios.get('http://localhost:8101/choicetariff/?token=' + localStorage.getItem('token') + '&tariff=' + this.changeTariff).then(response => {
                  console.log(response.data)
              }).catch(e => {
                  console.log(e)
              })
          }
        }
  }
}
</script>
