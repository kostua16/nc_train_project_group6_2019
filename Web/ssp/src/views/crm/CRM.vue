<template>
  <h1>CRM</h1>
</template>

<script>
    import axios from 'axios'
    export default {
        data: () => ({
            balance : 0,
            minutes : '0',
            sms : '0',
            internet: 0
        }),
        mounted(){
            this.getMoney()
            console.log(localStorage.getItem('token'))
        },
        methods:{
            getMoney() {
                axios.get('http://localhost:8101/home/?token='+localStorage.getItem('token')).then(response => {
                    this.balance = response.data.balance
                    this.minutes = response.data.minutes
                    this.sms = response.data.sms
                    this.internet = response.data.internet
                    console.log(response.data)
                }).catch(e => {
                    console.log(e)
                })
            }
        }
    }
</script>

<style scoped>

</style>
