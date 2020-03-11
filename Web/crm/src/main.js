import Vue from 'vue'; // сам на себя ссылается, говорит кто он такой
import './plugins/axios'; // импортируем плагины для видимости
import App from './app.vue'; // импорт папок
import router from './router'; // импорт папок
import store from './store'; // импорт папок
// import './registerServiceWorker';
// import '../node_modules/materialize-css/dist/js/materialize';

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
