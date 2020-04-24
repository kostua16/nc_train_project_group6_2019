<template>
  <div class="app-ssp-layout">

    <Navbar @click="isOpen =!isOpen"/> <!-- Титульник  --> <!-- сушаем значение клик  -->
    <Sidebar v-model="isOpen" /> <!-- меню слева  -->

    <main class="app-content" :class="{full: !isOpen}"> <!-- значение клика если не активировано то дописываем full в класс  -->
      <div class="app-page">
          <router-view />


    </div>
    </main>

</div>
</template>

<script>
import Navbar from '@/components/app/SspNavbar'
import Sidebar from '@/components/app/SspSidebar'
export default {
  name: 'ssp-layout', //любое имя можно задать
  data: () => ({
    isOpen: true
  }),
  components: {
    Navbar, Sidebar
  }
}
</script>
