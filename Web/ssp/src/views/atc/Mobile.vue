<template>
  <div class="row">
    <form class="col s12">
      <div class="row">

        <div class="input-field col s6">
          <i class="material-icons prefix">phone_forwarded</i>
          <input id="icon_telephone" type="tel" v-model.trim="telephoneFrom" class="validate">
          <label for="icon_telephone">From the Telephone</label>
        </div>
        <div class="input-field col s6">
          <i class="material-icons prefix">phone_in_talk</i>
          <input id="icon_telephone2" type="tel" v-model.trim="telephoneTo" class="validate">
          <label for="icon_telephone2">In the Phone</label>
        </div>

      </div>
    </form>

    <form class="col s12">
      <div class="row">
        <div class="input-field col s6">
          <i class="material-icons prefix">phone</i>
          <input id="minutes" type="tel" v-model.trim="minutes" class="validate">
          <label for="minutes">Minutes</label>
        </div>
        <div class="input-field col s6">
          <a class="waves-effect waves-light btn" @click.prevent="spendMinutes" @click="multiplyActionsMinutes"><i
            class="material-icons right">send</i>to spend</a>
          <!--<div>
            <span v-for="n in 1*minutes" :key="n">
              {{n}}
            </span>
          </div>-->
        </div>
      </div>
    </form>

    <form class="col s12">
      <div class="row">
        <div class="input-field col s6">
          <i class="material-icons prefix">sms</i>
          <input id="sms" type="tel" v-model.trim="sms" class="validate">
          <label for="sms">Sms</label>
        </div>
        <div class="input-field col s6">
          <a class="waves-effect waves-light btn" @click.prevent="spendSms" @click="multiplyActionsSms"><i
            class="material-icons right">send</i>to spend</a>
        </div>
      </div>
    </form>

    <form class="col s12">
      <div class="row">
        <div class="input-field col s6">
          <i class="material-icons prefix">public</i>
          <input id="Internet" type="tel" v-model.trim="internet" class="validate">
          <label for="Internet">Internet</label>
        </div>
        <div class="input-field col s6">
          <a class="waves-effect waves-light btn" @click.prevent="spendInternet" @click="multiplyActionsInternet"><i
            class="material-icons right">send</i>to spend</a>
        </div>
      </div>
    </form>
    <!--
        <form class="col s12">
          <div class="row">

            <div class="input-field col s6">
              <a class="waves-effect waves-light btn" @click.prevent="spendAll"><i class="material-icons right">send</i>to spend all</a>
            </div>
          </div>
        </form>-->
    <!--<button id="show-modal" @click="showModal = true">Show Modal</button>-->
    <modal v-if="showModal" v-bind:minutes="minutes" v-bind:sms="sms" v-bind:internet="internet" v-bind:type="typeModal"
           @close="showModal = false">
    </modal>
  </div>
</template>

<script>
  import Modal from "./Modal";

  export default {
    data: () => ({
      telephoneFrom: '',
      telephoneTo: '',
      minutes: null,
      sms: null,
      internet: null,
      showModal: false,
      typeModal: 'call',/*
    stopModal: true*/
    }),
    mounted() {
    },
    methods: {
      multiplyActionsMinutes() {
        this.typeModal = 'call'
        this.showModal = true

      },
      multiplyActionsSms() {
        this.typeModal = 'sms'
        this.showModal = true
      },
      multiplyActionsInternet() {
        this.typeModal = 'internet'
        this.showModal = true
      },
      sleep(milliseconds) {
        const date = Date.now();
        let currentDate = null;
        do {
          currentDate = Date.now();
        } while (currentDate - date < milliseconds);
      },
      spendMinutes() {
        if (this.minutes > 0) {
          this.$store.dispatch("DO_CALL", {telephoneFrom: this.telephoneFrom, telephoneTo: this.telephoneTo})
            .then(() => {
              this.minutes -= 1;
              this.sleep(1000);
              this.spendMinutes()
            })
            .catch(e => {
              console.log(e)
            })
        }
      },
      spendSms() {
        if (this.sms > 0) {
          this.$store.dispatch("DO_SMS", {telephoneFrom: this.telephoneFrom, telephoneTo: this.telephoneTo})
            .then(() => {
              this.sms -= 1;
              this.sleep(1000);
              this.spendSms()
            })
            .catch(e => {
              console.log(e)
            })
        }
      },
      spendInternet() {//  http://localhost:8102/useInternet/?telephoneFrom=897654321&kilobytes=4
        if (this.internet > 0) {
          this.$store.dispatch("DO_INTERNET", {telephoneFrom: this.telephoneFrom, telephoneTo: this.telephoneTo})
            .then(() => {
              this.internet -= 1;
              this.sleep(1000);
              this.spendInternet()
            })
            .catch(e => {
              console.log(e)
            })
        }
      },
    },
    components: {
      Modal
    }
  }
</script>
