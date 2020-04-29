<script>
  import BasePage from "../../components/base/BaseComponent"
  import {mapState} from "vuex";
  export default {
    extends: BasePage,
    name: 'userPage', //имя данной странице
    data: () => ({
      loginRoute: '/home'
    }),
    computed: mapState({
      login_done: state => state.CURRENT_USER != null,
    }),
    mounted() {
      if(!this.login_done){
        this.goTo(this.loginRoute);
      }
    },
    methods: {
      loginCheck() {
        if(!this.login_done && this.currentPage !== this.loginRoute){
          this.goTo(this.loginRoute);
        } else {
          const that = this;
          setTimeout(() => that.loginCheck(), 1000);
        }
      }
    }


  }
</script>
