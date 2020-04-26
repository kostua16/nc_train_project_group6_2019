<script>
  import BasePage from "../../components/base/BasePage"
  import {mapState} from "vuex";
  export default {
    extends: BasePage,
    name: 'userPage', //имя данной странице
    data: () => ({
      loginRoute: '/'
    }),
    computed: mapState({
      login_done: state => state.CURRENT_USER != null,
    }),
    mounted() {
      if(!this.login_done){
        this.goTo(this.loginRoute);
      }
    },


  }
</script>
