<template>

<form class="card auth-card"><!-- тут прослушиваем событие enter в кнопке и вызваем метод "submitHandler" -->
  <div class="card-content ">
      <span class="card-title ">Swith модуль</span>
       <button class="btn waves-effect waves-light auth-submit" type="submit" @click.prevent="goTo('/ssp/login')">SSP (users)
          <i class="material-icons right"/>
        </button>

       <button class="btn waves-effect waves-light auth-submit" type="submit"@click.prevent="goTo('/crm/login')">CRM (admins)
          <i class="material-icons right"/>
        </button>

        <button class="btn waves-effect waves-light auth-submit" type="submit"@click.prevent="goTo('/atc/mobile')"> ATC
          <i class="material-icons right"/>
        </button>
    </div>
</form>
</template>


<script>
import axios from 'axios'
export default {
  name: 'login', //имя данной странице
  data: () => ({
    email: '',
    password: '',
    token: '',
    loginAndPassword: false
  }),
  methods: {
      goTo(a) {
       this.$router.push(a)
    },

  }


}
</script>

<style scoped>
  .btn {
    margin-bottom: 2rem;

  }
</style>
