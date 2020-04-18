<template>

<div>
  <div class="page-title">
    <h3>Счет</h3>

    <button class="btn waves-effect waves-light btn-small">
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
              <td>{{parseFloat(internet/1024).toFixed(1)}} Мб</td>
              <td>{{parseFloat(minutes/60).toFixed(0)}} мин</td>
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
import axios from 'axios'
export default {
  data: () => ({
    balance : 0,
    minutes : '0',
    sms : '0',
    internet: 0
  }),
  mounted(){
    this.getMoney()
    console.log(localStorage.getItem('token'))
  },
  methods:{
    getMoney() {
      axios.get('http://localhost:8101/home/?token='+localStorage.getItem('token')).then(response => {
         this.balance = response.data.balance
         this.minutes = response.data.minutes
         this.sms = response.data.sms
         this.internet = response.data.internet
         console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
    }
  }
}
</script>

