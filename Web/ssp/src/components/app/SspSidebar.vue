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
      {title: 'Счет', url: '/ssp/home', exact: true}, // {ключ, значение}
      {title: 'Пополнить счет', url: '/ssp/bank'},
      {title: 'Изменить тариф', url: '/ssp/tariff'},//тарифы
    ]//массив
  })

}
</script>


