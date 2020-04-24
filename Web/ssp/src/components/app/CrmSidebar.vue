<template>
<ul class="sidenav app-sidenav" :class="{open: value}"> <!-- сушаем значение клик если оно активировано дописываем open в класс  -->
  <router-link
  v-for="link in links"
  :key="link.url"
  tag='li'
  active-class="active"
  :to="link.url"
  :exact="link.exact"
  >
  <a href="#" class="waves-effect waves-orange pointer">{{link.title}}</a>
  </router-link>
</ul>

</template>


<script>
export default {
  props: ['value'],
  data: () => ({ //стрелочная функция
    links: [
      {title: 'Пользователи', url: '/crm/user'},//блокировка номера, удалить акаунт,
      {title: 'Тарифы', url: '/crm/tariffadmin'},
      {title: 'История', url: '/crm/history'}
    ]//массив
  })

}
</script>


