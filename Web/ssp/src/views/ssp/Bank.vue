<template>
  <div>
    <div class="page-title">
      <h3>Пополнить счет</h3>
    </div>
    <form class="col s12">
      <div class="row">
        <div class="input-field col s6">
          <i class="material-icons prefix">phone</i>
          <input id="icon_telephone" type="tel" v-model.trim="phone" class="validate">
          <!-- <label for="icon_telephone">Номер телефона</label> -->
        </div>
      </div>
      <div class="row">
        <div class="input-field col s6">
          <i class="material-icons prefix">local_parking</i>
          <input id="money" type="tel" v-model.trim="upbalance" class="validate">
          <!-- <label for="money">Сумма</label> -->
        </div>
      </div>
    </form>
    <button class="btn waves-effect" @click="upBalance" >Пополнить счет <i class="material-icons right">attach_money</i>
    </button>
    <transition name="statusUpdate" v-on:enter="fadeOut">
      <p v-if="showSuccess && show" class="green--text">Баланс пополнен на <span v-text="upbalance"/> руб. </p>
      <p v-if="showFail && show" class="red--text">Ошибка при пополнении баланса</p>
    </transition>

  </div>
</template>

<script>
  import UserPage from '../base/UserView'
  import {mapState} from "vuex";

  export default {
    extends: UserPage,
    data: () => ({
      upbalance: 0,
      loginRoute: '/#/ssp/login',
      phone: '',
      showSuccess: false,
      showFail: false,
      show: false
    }),
    computed: mapState({
      userPhone: state => state.CURRENT_USER!==null ? state.CURRENT_USER.telephone : ''
    }),
    mounted() {
      this.phone = this.userPhone;
    },
    methods: {
      upBalance() {
        this.$store.dispatch("UPDATE_USER_BALANCE", {amount: this.upBalance, phone: this.phone } )
          .then(()=>{this.showSuccess = true; this.showFail = false; this.show = true;})
          .catch(()=>{this.showSuccess = false; this.showFail = true; this.show = true;});
      },
      fadeOut: function(el, done) {
        var that = this;
        setTimeout(function() {
          that.show = false;
          that.showSuccess = false;
          that.showFail = false;
        }, 2000); // hide the message after 3 seconds
      }
    }
  }//http://localhost:8101/topup/?token=!!!&amount=999
</script>

<style scoped>
  .statusUpdate-enter-active,
  .statusUpdate-leave-active {
    transition: opacity 1s
  }

  .statusUpdate-enter,
  .statusUpdate-leave-to
    /* .fade-leave-active in <2.1.8 */

  {
    opacity: 0
  }
</style>
