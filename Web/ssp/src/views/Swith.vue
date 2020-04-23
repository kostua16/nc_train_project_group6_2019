<template>
    
<form class="card auth-card"><!-- тут прослушиваем событие enter в кнопке и вызваем метод "submitHandler" -->
  <div class="card-content ">
      <span class="card-title ">Swith модуль</span>
          
          
          

       <button
            class="btn waves-effect waves-light auth-submit"
            type="submit"
            @click="submitHander"
        >
          SSP
          <i class="material-icons right"/>
        </button>
       
       <button
            class="btn waves-effect waves-light auth-submit"
            type="submit"
        >
          CRM
          <i class="material-icons right"/>
        </button>

        <button
            class="btn waves-effect waves-light auth-submit"
            type="submit"
        >
          ATC
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
    submitHandler() {
       this.$router.push('/login')
    },
    
  }


}
</script> -->

<
<style scoped>
  .btn {
    margin-bottom: 2rem;

  }
</style>
