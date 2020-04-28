<template>
  <div class="app-main-layout" v-if="appInitialized">

    <Navbar @click="isOpen =!isOpen"/> <!-- Титульник  --> <!-- сушаем значение клик  -->
    <Sidebar v-model="isOpen"/> <!-- меню слева  -->

    <main class="app-content" :class="{full: !isOpen}">
      <!-- значение клика если не активировано то дописываем full в класс  -->
      <div class="app-page container" >
        <router-view/>
      </div>
    </main>
  </div>
  <div class="app-main-layout" v-if="!appInitialized && appInitializationInProgress">
    <div>Интерфейс загружается</div>
  </div>
  <div class="app-main-layout" v-if="!appInitialized && !appInitializationInProgress">
    <div>Не удалось загрузить интерфейс</div>
  </div>
</template>

<script>
  import Navbar from '../components/base/BaseNavbar'
  import Sidebar from '../components/base/BaseSidebar'
  import BaseComponent from "../components/base/BaseComponent";
  export default {
    extends: BaseComponent,
    name: 'main-layout', //любое имя можно задать
    data: () => ({
      isOpen: true
    }),
    components: {
      Navbar, Sidebar
    }
  }
</script>
