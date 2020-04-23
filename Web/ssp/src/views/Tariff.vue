<template>
<div>
  <div class="page-title">
    <h3>Твой тариф: {{tariff}}</h3>
  </div>

  <form class="form">

    
    <label>Переход выполнится после окончания предыдущего тарифного плана</label>
  <select class="browser-default">
    <option value="" disabled selected>Выберите свой тариф</option>
    <option value="1">Для самых близких 300 руб</option>
    <option value="2">Постоянный онлайн 400 руб</option>
    <option value="3">Говорю сколько хочу 500 руб</option>
  </select>

    <button class="btn waves-effect waves-light" type="submit">
      Перейти
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
                <td>15 гб</td>
                <td>300 мин</td>
                <td>20 шт</td>
              </tr>
              </tbody>
            </table>
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
