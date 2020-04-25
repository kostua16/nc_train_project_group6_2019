import Vue from 'vue'
import Plugins from './plugins'
import Vuelidate from 'vuelidate'
import App from './App.vue'
import router from './router'
import store from './store'
import './registerServiceWorker'
import '../node_modules/materialize-css/dist/js/materialize'

Vue.config.productionTip = false
Vue.use(Vuelidate)
Vue.use(Plugins)
Vue.config.devtools = true;


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

