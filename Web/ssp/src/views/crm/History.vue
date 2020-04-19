<template>
  <div>
  <div class="page-title">
    <h3>История</h3>
  </div>

  <div class="history-chart">
    <canvas></canvas>
  </div>
    <div class="table-of-contents">
      <table>
        <tr>
          <th>Date</th>
          <th>Type</th>
          <th>Body</th>
        </tr> <!--ряд с ячейками заголовков-->
        <tr v-for = "(history) in histories">
            <td>{{history.date}}</td>
            <td>{{history.type}}</td>
            <td>{{history.body}}</td>
        </tr>
      </table>
    </div>
</div>
</template>
<script>
    import axios from 'axios'
    export default {
        data: () => ({
            histories: [history]
        }),
        mounted(){
            this.getHistory()
            console.log(localStorage.getItem('token'))
        },
        methods:{
            getHistory() {
                axios.get('http://localhost:8101/showHistory/?token='+localStorage.getItem('token')).then(response => {
                    this.histories=response.data
                    console.log(response.data)
                }).catch(e => {
                    console.log(e)
                })
            }
        }
    }
</script>

