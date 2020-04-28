<template>
  <div id="app-container">
    <div id="app" v-if="appInitialized">
      <component :is="layout"> <!-- @контент меняется от условия layout()@ -->
        <!-- <router-view/> @контент меняется от адресной строки@ Наличие имени у <router-view> определяет отображение компонента с соответствующим именем из опции components сопоставленного маршрута. -->
      </component>
    </div>
    <div id="appLoading" v-if="!appInitialized && appInitializationInProgress">
      <h3 class="">Приложение загружается</h3>
    </div>
    <div id="appLoadFailed" v-if="!appInitialized && !appInitializationInProgress">
      <h3 class="">Не удалось загрузить приложение</h3>
    </div>
  </div>

</template>

<script>
import MainLayout from '@/layouts/MainLayout'
import BaseComponent from "./components/base/BaseComponent";
export default {
  extends: BaseComponent,
  computed: {
    layout(){ //свойство- возвращает строку названия лаяута которое будет использоватся из 21 строчки
       //console.log(this.$route.meta.layout)
      //this.$route.meta.layout возврашает empty или main
       return (this.$route.meta.layout || 'Main') + '-Layout' //возврашаяет состояние <router-view/> свойство и имя
    }
  },
  mounted() {
    this.$store.dispatch('INITIALIZE');
  },
  components: {
    MainLayout,
  }
}
</script>

<style lang="scss">
@import '~materialize-css/dist/css/materialize.min.css';
@import 'assets/index.css';
</style>
