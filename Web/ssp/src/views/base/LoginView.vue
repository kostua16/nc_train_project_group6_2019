<template>
  <form class="card auth-card" @submit.prevent="loginClick">
    <div class="card-content">
      <span class="card-title" v-text="title">Личный кабинет</span>
      <div class="input-field">
        <input id="email" type="text" v-model.trim="email"
               :class="{invalid: ($v.email.$dirty && !$v.email.required)||($v.email.$dirty && !$v.email.email)}">
        <label for="email">Email</label>
        <small class="helper-text invalid"
               v-if="($v.email.$dirty && !$v.email.required)||($v.email.$dirty && !$v.password.minLength)">Email</small>
      </div>
      <div class="input-field">
        <input id="password" type="password" v-model.trim="password"
               :class="{invalid: ($v.password.$dirty && !$v.password.required)||($v.password.$dirty && !$v.password.minLength)}">
        <label for="password">Пароль</label>
        <small class="helper-text invalid"
               v-if="($v.password.$dirty && !$v.password.required)||($v.password.$dirty && !$v.password.minLength)">Минимум
          {{$v.password.$params.minLength.min}} символов.</small>
      </div>
    </div>
    <div class="card-action">
      <div>
        <button class="btn waves-effect waves-light auth-submit" type="submit" :disabled="loginStarted">Войти
          <i class="material-icons right"/>
        </button>

        <button class="btn waves-effect waves-light auth-submit" type="submit" :disabled="loginStarted" @click.prevent="goTo('/')">Назад</button>
        <small class="helper-text invalid" v-if="loginFailed">Неправильный логин или пароль</small>
      </div>
    </div>
  </form>
</template>


<script>
  import BasePage from "../../components/base/BasePage"
  import {mapState} from 'vuex'
  import {email, minLength, required} from 'vuelidate/lib/validators'

  export default {
    extends: BasePage,
    name: 'login', //имя данной странице
    data: () => ({
      title: 'Личный кабинет',
      email: '',
      password: '',
      loginFailed: false,
      loginStarted: false,
    }),
    computed: mapState({
      login_done: state => state.CURRENT_USER != null,
    }),
    mounted() {
      this.email = this.$store.state.DEFAULT_USER_NAME;
      this.password = this.$store.state.DEFAULT_USER_PASS;
      if(this.login_done){
        this.goToLastPage();
      }
    },
    validations: {
      email: {email, required}, /* required - пустое поле не принимаем*/
      password: {required, minLength: minLength(6)}
    },
    methods: {
      loginClick() {
        if (this.$v.$invalid) {
          this.$v.$touch()
          return
        }
        this.loginStarted = true;
        this.$store.dispatch("LOGIN", {login: this.email, password: this.password}).then(() => {
          this.loginFailed = false
          this.loginStarted = false;
          this.goToLastPage();
        }).catch(() => {
          this.loginFailed = true;
          this.loginStarted = false;
        });

      }
    }
  }
</script> -->

<style scoped>
  .btn {
    margin-bottom: 1rem;
  }
</style>
