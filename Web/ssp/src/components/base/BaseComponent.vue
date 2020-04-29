<script>
  import {mapState} from "vuex";
  export default {
    name: 'baseComponent',
    data: () => ({
      defaultPage: '/',
    }),
    computed: {
      currentPage(){ return this.$route.path; },
      ...mapState({
        lastPage: state => state.LAST_PATH,
        haveLastPage: state => state.LAST_PATH != null,
        appInitialized: state => state.INITIALIZATION_COMPLETED,
        appInitializationInProgress: state => state.INITIALIZATION_IN_PROGRESS
      })
    },
    methods: {
      waitInitialization(then) {
        this.$store.dispatch("WAIT_INITIALIZATION").then(() => { then() });
      },
      goToLastPage() {
        if(this.currentPage!==this.lastPage && this.haveLastPage){
          this.goTo(this.lastPage);
        } else {
          this.goTo(this.defaultPage);
        }

      },
      goTo(a) {
        this.$store.dispatch('STORE_LAST_PATH', this.$route.path);
        this.goToNoBack(a);
      },
      goToNoBack(a) {
        if(a!=null){
          if(this.currentPage!==a){
            this.$router.push(a);
            console.log(`goTo: ${a} from ${this.name}=${this.currentPage}`);
          }
        } else {
          this.goToNoBack(this.defaultPage)
        }
      },
      toRoot() {
        this.goTo("/")
      },
      goToModule(moduleInfo){
        this.$store.dispatch("UPDATE_NAVIGATION", moduleInfo);
        this.goTo(moduleInfo.url);
      },
      goToModuleNoBack(moduleInfo){
        this.$store.dispatch("UPDATE_NAVIGATION", moduleInfo);
        this.goToNoBack(moduleInfo.url);
      }
    }


  }
</script>
