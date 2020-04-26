<template>
  <div class="app-main-layout" v-if="app_initialized">

    <Navbar @click="isOpen =!isOpen"/> <!-- Титульник  --> <!-- сушаем значение клик  -->
    <Sidebar v-model="isOpen"/> <!-- меню слева  -->

    <main class="app-content" :class="{full: !isOpen}">
      <!-- значение клика если не активировано то дописываем full в класс  -->
      <div class="app-page" >
        <router-view/>
      </div>
    </main>
  </div>
  <div class="app-main-layout" v-if="!app_initialized && app_initialization_in_progress">
    <div>Интерфейс загружается</div>
  </div>
  <div class="app-main-layout" v-if="!app_initialized && !app_initialization_in_progress">
    <div>Не удалось загрузить интерфейс</div>
  </div>
</template>

<script>
  import Navbar from '../components/base/BaseNavbar'
  import Sidebar from '../components/base/BaseSidebar'
  import {mapState} from 'vuex'
  export default {
    name: 'main-layout', //любое имя можно задать
    data: () => ({
      isOpen: true
    }),
    computed: mapState({
      app_initialized: state => state.INITIALIZATION_COMPLETED,
      app_initialization_in_progress: state => state.INITIALIZATION_IN_PROGRESS
    }),
    components: {
      Navbar, Sidebar
    }
  }
</script>
