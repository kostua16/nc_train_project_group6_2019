<template>
  <div id="app">
    <component :is="layout"> <!-- @контент меняется от условия layout()@ -->
      <!-- <router-view/> @контент меняется от адресной строки@ Наличие имени у <router-view> определяет отображение компонента с соответствующим именем из опции components сопоставленного маршрута. -->
    </component>
  </div>
</template>

<script>
import EmptyLayout from '@/layouts/EmptyLayout'
import MainLayout from '@/layouts/MainLayout'
import AtcLayout from '@/layouts/AtcLayout'
import SspLayout from '@/layouts/SspLayout'
import CrmLayout from '@/layouts/CrmLayout'
export default {
  computed: {
    layout(){ //свойство- возвращает строку названия лаяута которое будет использоватся из 21 строчки
       //console.log(this.$route.meta.layout)
      //this.$route.meta.layout возврашает empty или main
       return (this.$route.meta.layout || 'Empty' || 'Crm') + '-Layout' //возврашаяет состояние <router-view/> свойство и имя
    }
  },
  components: {
    EmptyLayout,
    MainLayout,
    AtcLayout,
    SspLayout,
    CrmLayout  //импортируем для тега <component :is="layout">
  }
}
</script>

<style lang="scss">
@import '~materialize-css/dist/css/materialize.min.css';
@import 'assets/index.css';
</style>
