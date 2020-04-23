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
          <a class="waves-effect waves-light btn" @click.prevent="getMinutes"><i class="material-icons right">send</i>to spend</a>
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
          <a class="waves-effect waves-light btn" @click.prevent="getSms"><i class="material-icons right">send</i>to spend</a>
        </div>
      </div>
    </form>

    <form class="col s12">
      <div class="row">
        <div class="input-field col s6">
          <i class="material-icons prefix">public</i>
          <input id="Internet" type="tel"  v-model.trim="internet" class="validate">
          <label for="Internet">Internet</label>
        </div>
        <div class="input-field col s6">
          <a class="waves-effect waves-light btn"  @click.prevent="getInternet"><i class="material-icons right">send</i>to spend</a>
        </div>
      </div>
    </form>
    
    <form class="col s12">
      <div class="row">
        
        <div class="input-field col s6">
          <a class="waves-effect waves-light btn" @click.prevent="spendAll"><i class="material-icons right">send</i>to spend all</a>
        </div>
      </div>
    </form>

  </div>
</template>

<script>
import axios from 'axios'
export default {
    data: () => ({
    telephoneFrom : '',
    telephoneTo: '',
    minutes : '',
    sms : null,
    internet: null
  }),
  mounted(){
    
  },
  methods:{       
    getMinutes() {//http://localhost:8102/callFromTo/?telephoneFrom=897654321&minutes=1&telephoneTo=999
      axios.get('http://localhost:8102/callFromTo/?telephoneFrom=' + this.telephoneFrom + '&minutes='+ this.minutes+'&telephoneTo='+this.telephoneTo).then(response => {
         console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
    },
    getSms() {//http://localhost:8102/smsFromTo/?telephoneFrom=897654321&sms=4&telephoneTo=999
      axios.get('http://localhost:8102/smsFromTo/?telephoneFrom=' + this.telephoneFrom + '&sms='+ parseFloat(this.sms)+'&telephoneTo='+this.telephoneTo).then(response => {
         console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
    },
    getInternet() {//  http://localhost:8102/useInternet/?telephoneFrom=897654321&kilobytes=4
      axios.get('http://localhost:8102/useInternet/?telephoneFrom=' + this.telephoneFrom + '&kilobytes='+ parseFloat(this.internet)*1024).then(response => {
         console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
    },
    spendAll() {
      getMinutes()
      getSms()
      getInternet()
    }
  }
}
</script>
