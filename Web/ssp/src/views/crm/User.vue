<template>

  <div class="col s12">
    <div class="row">
      <form class="col s12" @submit.prevent="search">
        <div class="input-field col s10">
          <input id="query" type="text" v-model.trim="searchQuery" :disabled="searchCalled">
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
          <th class="col s1">Баланс</th>
          <th class="col s2">Тарифный план</th>
          <th class="col s1">Номер телефона</th>
          <th class="col s2">Пополнение</th>
          <th class="col s2">Удаление</th>
        </tr> <!--ряд с ячейками заголовков-->
        <tr class="row" v-for="(result) in searchResults">
          <td class="col s2">{{result.login}}</td>
          <td class="col s2">{{result.name}}</td>
          <td class="col s1">{{result.balance}}</td>
          <td class="col s2">{{result.tariff}}</td>
          <td class="col s1">{{result.telephone}}</td>
          <td class="col s2">
            <editable-number-field v-bind:label="'Сумма пополнения'" v-bind:init-value="0"
                                   v-bind:callback="(amt) => {updateBalance(result.telephone, amt)}"/>
          </td>
          <td class="col s2">
            <button class="btn waves-effect waves-light" type="submit" @click.prevent="deleteAk(result.login)"
                    name="deleteAction">Удалить
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
            <input id="email" type="email" v-model="newUser.login" class="validate" :disabled="creationCalled">
            <label for="email">Email</label>
          </div>
          <div class="input-field col s12">
            <input id="password" type="password" v-model="newUser.password" class="validate" :disabled="creationCalled">
            <label for="password">Пароль</label>
          </div>

          <div class="input-field col s12">
            <input id="first_name" type="text" v-model="newUser.name" class="validate" :disabled="creationCalled">
            <label for="first_name">ФИО</label>
          </div>
          <div class="input-field col s12">
            <input id="balance" type="number" v-model="newUser.balance" class="validate" :disabled="creationCalled">
            <label for="balance">Баланс</label>
          </div>
          <div class="input-field col s12">
            <input id="telephone_numbe" type="number" v-model="newUser.telephone" class="validate" :disabled="creationCalled">
            <label for="telephone_numbe">Номер телефона</label>
          </div>

          <div class="input-field col s12">
            <select id="tariff" class="validate" v-model="newUser.tariff">
              <option v-for="tariff in tariffs" v-bind:value="tariff.name">{{ tariff.name }}</option>
            </select>
            <label for="tariff">Тарифный план</label>
          </div>
          <div class="input-field col s12">
            <select id="rang" class="validate" v-model="newUser.rang">
              <option value="USER">User</option>
              <option value="ADMINISTRATOR">Administrator</option>
            </select>
            <label for="rang">Тип пользователя</label>
          </div>

          <div class="input-field col s12">
            <a class="waves-effect waves-light btn" @click.prevent="pustUser" :disabled="creationCalled"><i class="material-icons right">send
            </i>Добавить пользователя</a>
          </div>
          <small class="helper-text invalid col s12" v-if="creationFailed">Не удалось создать пользователя, повторите попытку позже</small>
        </div>
      </form>
    </div>
  </div>


</template>

<script>
  import UserPage from '../base/UserView'
  import EditableNumberField from "../ssp/EditableNumberField";
  import {mapState} from "vuex";

  export default {
    extends: UserPage,
    data: () => ({
      newUser: {
        'login': "",
        'password': "",
        'name': "",
        'balance': 0,
        'tariff': "",
        'telephone': "",
        'rang': ""
      },
      nullUser: {
        'login': "",
        'password': "",
        'name': "",
        'balance': 0,
        'tariff': "",
        'telephone': "",
        'rang': ""
      },
      loginRoute: '/crm/login',
      searchQuery: "",
      searchCalled: false,
      creationCalled: false,
      creationFailed: false

    }),
    computed: mapState({
      searchResults: state => state.LIST_ACCOUNTS,
      tariffs: state => state.CURRENT_USER!=null ? state.CURRENT_USER.availableTariffs : [],
    }),
    mounted() {
      this.dropdown = M.FormSelect.init(this.$refs.tariff, {
        constrainWidth: true
      });
      this.dropdown = M.FormSelect.init(this.$refs.rang, {
        constrainWidth: true
      });
    },
    methods: {//deleteA
      async search() {
        this.searchCalled = true;
        await this.$store.dispatch("SEARCH_ACCOUNTS", this.searchQuery);
        this.searchCalled = false;
      },
      async updateBalance(phone, amt) {
        await this.$store.dispatch("UPDATE_USER_BALANCE", {amount: amt, phone: phone});
        await this.search();
      },
      async deleteAk(login) {
        await this.$store.dispatch("DELETE_USER", login);
        await this.search();
      },
      async pustUser() {
        this.creationCalled = true;
        try {
          await this.$store.dispatch("CREATE_USER", this.newUser);
          this.newUser = this.nullUser;
          await this.search();
          this.creationFailed = false;
        } catch (e) {
          this.creationFailed = true;
        }
        this.creationCalled = false;
      },
    },
    components: {
      EditableNumberField
    }
  }
</script>
