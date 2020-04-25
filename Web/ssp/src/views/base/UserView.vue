<script>
  import BasePage from "../../components/base/BasePage"
  export default {
    extends: BasePage,
    name: 'userPage', //имя данной странице
    data: () => ({
      loginRoute: '/home'
    }),
    mounted() {
      if(!this.$store.state.LOGIN_SUCCESS){
        this.goTo(this.loginRoute);
      }
    },


  }
</script>
