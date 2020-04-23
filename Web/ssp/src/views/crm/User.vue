<template>
  <div class="row">

    <form class="col s6">
      <div class="row">
        <div class="input-field col s12">
            <input id="email" type="email" v-model="login" class="validate">
            <label for="email">Email</label>
          </div>
        <div class="input-field col s12">
            <input id="password" type="password"  v-model="password" class="validate">
            <label for="password">Password</label>
          </div>
      
        <div class="input-field col s12">
              <input id="first_name" type="text" v-model="name" class="validate">
              <label for="first_name">First Name</label>
        </div>
        <div class="input-field col s12">
            <input id="balance" type="number" v-model="balance" class="validate">
            <label for="balance">balance</label>
          </div>
          <div class="input-field col s12">
            <input id="telephone_numbe" type="number" v-model="telephone" class="validate">
            <label for="telephone_numbe">telephone number</label>
          </div>

          <div>
              <select class="browser-default col s12" v-model="name">
              <option value="" disabled selected>Выберите тариф пользователю</option>
              <option value="1">Для самых близких 300 руб</option>
              <option value="2">Постоянный онлайн 400 руб</option>
              <option value="3">Говорю сколько хочу 500 руб</option>
            </select>
         </div>
         <div>
              <select class="browser-default col s12" v-model="rang">
              <option value="" disabled selected>Выберите ранг</option>
              <option value="USER">USER</option>
              <option value="ADMIN">ADMIN</option>
            </select>
         </div>
        <div class="input-field col s12">
          <a class="waves-effect waves-light btn" @click.prevent="pustUser"><i class="material-icons right">send</i>Добавить пользователя</a>
        </div>
      </div>
    </form>

    <form class="col s6">
      <div class="row">
        <div class="input-field col s12">
            <input id="email2" type="email"  v-model="logindelet" class="validate">
            <label for="email2">Email</label>
          </div>
      

          <button class="btn waves-effect waves-light" type="submit" @click.prevent="deleteAk" name="action">Удалить пользователя
             <i class="material-icons right">send</i>
          </button>
      </div>
    </form>

  

  </div>

</template>

<script>
import axios from 'axios'
export default {
   data: () => ({
          login: "timur@mail.ru",         
          logindelet: "",
          password: "123456",
          name: "123",
          balance: "11111111",
          tariff: "DEFAULT",
          telephone: "88005553538",
          rang: "USER"
    }),
  methods: {//deleteA
  //showT
  getTariff() {
    axios.get('http://localhost:8101/showT/?token='+localStorage.getItem('token')).then(response => {
         console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
  },
   deleteAk() {
      axios.delete('http://localhost:8101/deleteA/?token='+ localStorage.getItem('token') +'&login=' + this.logindelet).then(response => {
         console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
    },
   
   pustUser() { 
       axios.post('http://localhost:8101/createA/?token=' + localStorage.getItem('token'), {
          login: this.login,
          password: this.password,
          name: this.name,
          balance: this.balance,
          tariff: this.tariff,
          telephone: this.telephone,
          rang: this.rang
       }).then(response =>{
         console.log(response)
      }).catch(e => {
        console.log(e)
      })
    },
  },
  mounted() {
    this.getTariff()
  }
}
</script>
