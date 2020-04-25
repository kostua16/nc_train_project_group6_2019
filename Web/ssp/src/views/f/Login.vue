<template>

<form class="card auth-card" @submit.prevent="submitHandler"><!-- тут прослушиваем событие enter в кнопке и вызваем метод "submitHandler" -->
  <div class="card-content">
      <span class="card-title">Личный кабинет</span>
      <div class="input-field">
        <input
            id="email"
            type="text"
            v-model.trim="email"
            :class="{invalid: ($v.email.$dirty && !$v.email.required)||($v.email.$dirty && !$v.email.email)}"
        >
        <!--<input
            id="email"
            type="text"
            v-model.trim="email" -trim убирает пробелы
            :class="{класс для неправельности контрола invalid: ("проверка после нажатия кнопки а не сразу" и если не пустой)или($v.email.$dirty и это не имейл)}" -выделение
            :class="{invalid: ($v.email.$dirty && !$v.email.required)||($v.email.$dirty && !$v.email.email)}"
        > -->
        <label for="email">Email</label>
        <small
         class="helper-text invalid"
         v-if="($v.email.$dirty && !$v.email.required)||($v.email.$dirty && !$v.password.minLength)"
         >Email</small>
      </div>
      <div class="input-field">
        <input
            id="password"
            type="password"
            v-model.trim="password"
            :class="{invalid: ($v.password.$dirty && !$v.password.required)||($v.password.$dirty && !$v.password.minLength)}"
        >
        <label for="password">Пароль</label>
        <small
        class="helper-text invalid"
        v-if="($v.password.$dirty && !$v.password.required)||($v.password.$dirty && !$v.password.minLength)"
        >Минимум {{$v.password.$params.minLength.min}} символов.</small>
      </div>
    </div>
    <div class="card-action">
      <div>
        <button class="btn waves-effect waves-light auth-submit" type="submit" @click.prevent="goTo('/history')" >Войти
          <i class="material-icons right"/>
        </button>
        <small
        class="helper-text invalid"
        v-if="loginAndPassword"
        >Неправильный логин или пароль</small>
      </div>

      <p class="center">
        Нет аккаунта?
        <router-link to="/register">Зарегистрироваться</router-link>
      </p>
  </div>
</form>
</template>


<script>
import axios from 'axios'
import {email, required, minLength} from 'vuelidate/lib/validators'
import {mapState} from "vuex";
export default {
  name: 'login', //имя данной странице
  data: () => ({
    email: '',
    password: '',
    token: '',
  }),
  computed: mapState({
    loginAndPassword: state => state.LOGIN_FAILED
  }),
  validations: {
    email: {email, required}, /* required - пустое поле не принимаем*/
    password: {required, minLength: minLength(6)}

  },
  methods: {
    submitHandler() {
      //console.log(this.$v.password)
      if (this.$v.$invalid) {
        this.$v.$touch()
        return
      }
      this.$store.dispatch('LOGIN', {login: this.email, password: this.password}).then(() => {
        console.log("successfully logged in");
        this.$router.push('/');
      });
    },
      goTo(a) {
          this.$router.push(a)
      }

  }


}
</script> -->
