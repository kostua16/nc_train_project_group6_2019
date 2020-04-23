<template>
<div>
  <div class="page-title">
    <h3>Тарифы</h3>
  </div>

  <form class="form">
        <div class="input-field col s6">
          <i class="material-icons prefix">description</i>
          <input id="icon_telephone" type="tel" class="validate">
          <label for="icon_telephone">Введите имя тарифного плана</label>
        </div>
    
    <!-- <label>Введите имя тарифного плана</label>
  <input id="internet" type="text" class="validate">
  <label for="icon_telephone">Telephone</label> -->

    <button class="btn waves-effect waves-light" type="submit">
      Создать тариф
      <i class="material-icons right">send</i>
    </button>
  </form>
    <div class="row">
   
      <div class="col s12 m6 l4"> <!--col "колонка" s12 "одна карточка если размер окна не позволяет" l4 "три карточки в одну строку" м6 "2 карточки если размер окна не позволяет"  -->
        <div class="card orange darken-1 bill-card">
          <div class="card-content white-text"> <!--card-content "центрируем"  и это тоже сам  center-align -->
            <div class="card-header">
              <span class="card-title">Постоянный онлайн</span>
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
                <td>15 гб</td>
                <td>300 мин</td>
                <td>20 шт</td>
              </tr>

              </tbody>
     
            </table>

          </div>
        </div>
      </div>


      <div class="col s12 m6 l4">
        <div class="card orange darken-3 bill-card">
          <div class="card-content white-text">
            <div class="card-header">
              <span class="card-title">Говорю сколько хочу</span>
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
                <td>15 гб</td>
                <td>300 мин</td>
                <td>20 шт</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
     </div>
      <div class="col s12 m6 l4">
          <div class="card  red darken-4 bill-card">
          <div class="card-content white-text">
            <div class="card-header">
              <span class="card-title">Для самых близких</span>
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
                <td> <input id="internet" type="text" class="validate"> гб</td>
                <td> <input id="minut" type="text" class="validate"> мин</td>
                <td> <input id="chat" type="text" class="validate"> шт</td>
              </tr>
              </tbody>
               
              
                
              
            </table>
             <div>
                        <button
                        class="btn waves-effect waves-light auth-submit"
                        type="submit"
                    >
                      Удалить
                      <i class="material-icons right">clear</i>
                    </button>
                    </div>
            <!-- <button
            class="btn waves-effect waves-light auth-submit">
             <i class="material-icons right">clear<i/>CRM
            </button> -->
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
    tariff: 'Для самых близких'
  }),
  mounted () {
    this.getTariff()
  },
  methods: {
    getTariff(){ //настроить адресс
      axios.get('http://localhost:8101/showtariff/?token='+localStorage.getItem('token')).then(response => {
         this.tariff = response.data.user.tariff
         console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
    }
  }
}
</script>
