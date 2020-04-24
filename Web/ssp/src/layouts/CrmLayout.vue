<template>
  <div class="app-crm-layout">

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
import Navbar from '@/components/app/CrmNavbar'
import Sidebar from '@/components/app/CrmSidebar'
export default {
    name: 'crm-layout', //любое имя можно задать
  data: () => ({
    isOpen: true
  }),
  components: {
    Navbar, Sidebar
  }
}
</script>
