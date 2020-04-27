<template>

  <div class="col s12">
    <div class="row">
      <form class="col s12" @submit.prevent="search">
        <div class="input-field col s10">
          <input id="query" type="text" v-model.trim="searchQuery" class="validate" :disabled="searchCalled">
          <label for="query">Поиск</label>
        </div>
        <div class="input-field col s2">
          <button class="btn waves-effect waves-light auth-submit" type="submit" :disabled="searchCalled">Поиск
            <i class="material-icons right"/>
          </button>
        </div>
      </form>
    </div>
    <div class="row">
      <table class="col s12">
        <tr class="row">
          <th class="col s2">Login</th>
          <th class="col s2">ФИО</th>
          <th class="col s2">Баланс</th>
          <th class="col s2">Тарифный план</th>
          <th class="col s2">Номер телефона</th>
          <th class="col s2">Пополнение</th>
          <th class="col s2">Удаление</th>
        </tr> <!--ряд с ячейками заголовков-->
        <tr v-for = "(result) in searchResults">
          <td class="col s2">{{result.login}}</td>
          <td class="col s2">{{result.name}}</td>
          <td class="col s2">{{result.balance}}</td>
          <td class="col s2">{{result.tariff}}</td>
          <td class="col s2">{{result.telephone}}</td>
          <td class="col s2">
            <editable-number-field v-bind:label="'Сумма пополнения'" v-bind:init-value="0" v-bind:callback="(amt) => {updateBalance(result.telephone, amt)}" />
          </td>
          <td class="col s2">
            <button class="btn waves-effect waves-light" type="submit" @click.prevent="deleteAk(result.login)" name="deleteAction">Удалить
              <i class="material-icons right">send</i>
            </button>
          </td>
        </tr>
      </table>
    </div>


    <div class="row">
      <form class="col s12">
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
            <a class="waves-effect waves-light btn" @click.prevent="pustUser"><i class="material-icons right">send
            </i>Добавить пользователя</a>
          </div>
        </div>
      </form>

<!--      <form class="col s6">-->
<!--        <div class="row">-->
<!--          <div class="input-field col s12">-->
<!--            <input id="email2" type="email"  v-model="logindelet" class="validate">-->
<!--            <label for="email2">Email</label>-->
<!--          </div>-->
<!--          <button class="btn waves-effect waves-light" type="submit" @click.prevent="deleteAk" name="action">Удалить пользователя-->
<!--            <i class="material-icons right">send</i>-->
<!--          </button>-->
<!--        </div>-->
<!--      </form>-->
    </div>
  </div>


</template>

<script>
  import UserPage from '../base/UserView'
  import EditableNumberField from "../ssp/EditableNumberField";
  import Navbar from "../../components/base/BaseNavbar";
  import Sidebar from "../../components/base/BaseSidebar";
export default {
  extends: UserPage,
  data: ()=>  ({
       /*login: "timur@mail.ru",
       logindelet: "",
       password: "123456",
       name: "123",
       balance: 11111,
       tariff: "DEFAULT",
       telephone: "88005553538",
       rang: "USER",*/
          loginRoute: '/crm/login',
          login: "",
          logindelet: "",
          password: "",
          name: "",
          balance: null,
          tariff: "",
          telephone: "",
          rang: "",
          searchQuery:"",
          searchCalled:false

    }),
  computed: {
    searchResults() {
      return this.$store.state.LIST_ACCOUNTS;
    }
  },
  methods: {//deleteA
   async search() {
     this.searchCalled = true;
     await this.$store.dispatch("SEARCH_ACCOUNTS", this.searchQuery);
     this.searchCalled = false;
   } ,
    async updateBalance(phone, amt) {
      await this.$store.dispatch("UPDATE_USER_BALANCE", {amount: amt, phone: phone } );
      await this.search();
    },
    async deleteAk(login) {
     await this.$store.dispatch("DELETE_USER", login);
     await this.search();
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
        };
        this.$store.dispatch("CREATE_USER", s);
    },
  },
  components: {
    EditableNumberField
  }
}
</script>
