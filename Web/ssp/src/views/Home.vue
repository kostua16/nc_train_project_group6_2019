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
              <td>15 гб</td>
              <td>300 мин</td>
              <td>20 шт</td>
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
    balance : 0
  }),
  mounted(){
    this.getMoney()
  },
  methods:{
    getMoney() {
      axios.get('http://localhost:8102/getAccount/?token='+localStorage.getItem('token')).then(response => {
         this.balance = response.data.balance
      }).catch(e => {
        console.log(e)
      })
    }
  }
}
</script>

