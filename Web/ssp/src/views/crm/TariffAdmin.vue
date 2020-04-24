<template>
<div>
  <div class="page-title">
    <h3>Тарифы</h3>
  </div>

  <form class="form">
        <div class="input-field col s6">
          <i class="material-icons prefix">description</i>
          <input id="icon_telephone" type="tel" v-model="deleteTariffName" class="validate">
          <label for="icon_telephone">Введите имя тарифного плана</label>
        </div>

    <!-- <label>Введите имя тарифного плана</label>
  <input id="internet" type="text" class="validate">
  <label for="icon_telephone">Telephone</label> -->

    <button class="btn waves-effect waves-light" type="submit"  @click.prevent="deleteTariff">Удалить
      <i class="material-icons right">send</i>
    </button>
  </form>

    <div class="row">
      <div lass="t" v-for = "(tariff) in tariffs">
        <div class="col s12 m6 l4">
          <div class="card orange darken-1 bill-card">
            <div class="card-content white-text">
              <div class="card-header">
                <span class="card-title">{{tariff.name}}</span>
              </div>
              <table>
                <thead>
                <tr>
                  <th>Интернет</th>
                  <th>Минуты</th>
                  <th>СМС</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                  <td>{{tariff.internet}} mb</td>
                  <td>{{tariff.call}} min</td>
                  <td>{{tariff.sms}}</td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <div class="col s12 m6 14">
          <div class="card  red darken-4 bill-card" style="height: 200% ; width: 100%" >
          <div class="card-content white-text">
              <input id="tariffName" type="text" style="color: white" placeholder="Введите название" v-model="newTariffName" class="validate">
            <table >
                <thead>
                <tr>
                  <th>Интернет</th>
                  <th>Минуты</th>
                  <th>СМС</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                  <td> <input id="internet" type="text" v-model="Internet_balance" class="validate"> Мб</td>
                  <td> <input id="minut" type="text" v-model="Call_balance" class="validate"> мин</td>
                  <td> <input id="chat" type="text" v-model="Sms_balance" class="validate"> шт</td>
                </tr>
                </tbody>
                  <thead>
                   <th>For Tariff</th>
                  </thead>
                <tbody>
                <tr>
                  <td> <input id="Tinternet" type="text" v-model="Internet_cost" class="validate"> Мб</td>
                  <td> <input id="Tminut" type="text" v-model="Call_cost" class="validate"> мин</td>
                  <td> <input id="Tchat" type="text" v-model="Sms_cost" class="validate"> шт</td>
                </tr>
                </tbody>
              <thead>
              <th>Without Tariff</th>
              </thead>
              <tbody>
                <tr>
                  <td> <input id="Winternet" type="text"  v-model="Default_internet_cost" class="validate"> Мб</td>
                  <td> <input id="Wminut" type="text" v-model="Default_call_cost" class="validate"> мин</td>
                  <td> <input id="Wchat" type="text"v-model="Default_sms_cost" class="validate"> шт</td>
                </tr>
                </tbody>
            </table>
             <div>
                <button class="btn waves-effect waves-light auth-submit" type="submit"@click.prevent="createTariff">Создать
                  <i class="material-icons right">clear</i>
                </button>
              </div>
          </div>
        </div>
      </div>
    </div>
</div>

</template>

<script>
import axios from 'axios'
export default {
  data: () => ({
    tariffs: {tariff:[]},
    newTariffName: '',
    Call_cost: '',
    Call_balance: '',
    Default_call_cost: '',
    Internet_cost: '',
    Internet_balance: '',
    Default_internet_cost: '',
    Sms_cost: '',
    Sms_balance: '',
    Default_sms_cost: '',
    deleteTariffName: ''
  }),
  mounted () {
    this.getTariff()
  },
  methods: {
    shiwTariff(){
      for (key in this.tariffs) {
        if (person.hasOwnProperty(key)) {
          //ключ = key
          //значение = person[key]
          console.log("Ключ = " + key);
          console.log("Значение = " + person[key]);
        } // если объект person имеет key (если у person есть свойство key)
      }
    },
    getTariff(){ //настроить адресс
      axios.get('http://localhost:8101/showT/?token='+localStorage.getItem('token')).then(response => {
         this.tariffs = response.data
         console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
    },
    deleteTariff(){
        axios.delete('http://localhost:8101/deleteT/?token='+localStorage.getItem('token') + '&name='+this.deleteTariffName).then(response => {
            console.log(response.data)
        }).catch(e => {
            console.log(e)
        })
    },
    createTariff(){
        var s={
            'tariff': {'tariffName': this.newTariffName},
            'tariffCall': {"Call_cost": this.Call_cost*60,"Call_balance": this.Call_balance,"Default_call_cost": this.Default_call_cost},
            'tariffInternet': {"Internet_cost": this.Internet_cost*1000,"Internet_balance": this.Internet_balance,"Default_internet_cost": this.Default_internet_cost},
            'tariffSms': {"Sms_cost": this.Sms_cost,"Sms_balance": this.Sms_balance, "Default_sms_cost": this.Default_sms_cost}
        }
        axios.post('http://localhost:8101/createT/?token='+localStorage.getItem('token'),s
        ).then(response => {
            console.log(response.data)
        }).catch(e => {
            console.log(e)
        })
    }
  }
}
</script>
