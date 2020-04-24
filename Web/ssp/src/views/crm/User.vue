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

        <div class="input-field col s12">
          <input id="tariff" type="text" v-model="tariff" class="validate">
          <label for="tariff">tariff</label>
        </div>
        <div class="input-field col s12">
          <input id="rang" type="text" v-model="rang" class="validate">
          <label for="rang">rang</label>
        </div>

        <div class="input-field col s12">
         <!-- <button class="btn waves-effect waves-light" type="submit" @click.prevent="pustUser" name="action">Добавить пользователя
            <i class="material-icons right">send</i>
          </button>-->
          <a class="waves-effect waves-light btn" @click.prevent="pustUser"><i class="material-icons right">send
          </i>Добавить пользователя</a>
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
   data: ()=>  ({
       /*login: "timur@mail.ru",
       logindelet: "",
       password: "123456",
       name: "123",
       balance: 11111,
       tariff: "DEFAULT",
       telephone: "88005553538",
       rang: "USER",*/
          login: "",
          logindelet: "",
          password: "",
          name: "",
          balance: null,
          tariff: "",
          telephone: "",
          rang: "",

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

        var s={
            'login': this.login,
            'password': this.password,
            'name': this.name,
            'balance': this.balance,
            'tariff': this.tariff,
            'telephone': this.telephone,
            'rang': this.rang
        }
          axios.post('http://localhost:8101/createA/?token=' + localStorage.getItem('token'),s
          ).then(response =>{
              console.log(response)
          }).catch(e => {
              console.log(u)
              console.log(e)
          })
    },
  },
  mounted() {
    this.getTariff()
  }
}
</script>
