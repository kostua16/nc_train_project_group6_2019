<template>
<div>
  <div class="page-title">
    <h3>Пополнить счет</h3>
  </div>

  <form class="col s12">
      <div class="row">
        <div class="input-field col s6">
          <i class="material-icons prefix">phone</i>
          <input 
          id="icon_telephone" 
          type="tel"
          v-model.trim="telephone"
          class="validate">


          <!-- <label for="icon_telephone">Номер телефона</label> -->
        </div>
      </div>
      <div class="row">
        <div class="input-field col s6">
          <i class="material-icons prefix">local_parking</i>
          <input 
          id="money" 
          type="tel" 
          v-model.trim="upbalance"
          class="validate">

          <!-- <label for="money">Сумма</label> -->
        </div>
      </div>
    </form>
            <button
            class="btn waves-effect  " 
            @click="upBalance"
        >
          Пополнить счет
          <i class="material-icons right"/>
        </button>
</div>
</template>

<script>
import axios from 'axios'
export default {
  data: () => ({
    telephone : '0',
    upbalance : 0,
  }),
  mounted(){
    this.getTelephone()
    // this.upBalance()
  },
  methods:{
    upBalance() {
      axios.get('http://localhost:8101/topup/?token='+localStorage.getItem('token')+'&amount='+ this.upbalance).then(response => {
        console.log(response.data)
     }).catch(e => {
        console.log(e)
      })
    },
    getTelephone() {
      axios.get('http://localhost:8101/home/?token='+localStorage.getItem('token')).then(response => {
         return this.telephone = response.data.telephone
          console.log(response.data.telephone)
      }).catch(e => {
        console.log(e)
      })
    }
  }
}//http://localhost:8101/topup/?token=!!!&amount=999
</script>
