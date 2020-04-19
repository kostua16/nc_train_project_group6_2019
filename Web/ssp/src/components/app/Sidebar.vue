<template>
<ul class="sidenav app-sidenav" :class="{open: value}"> <!-- сушаем значение клик если оно активировано дописываем open в класс  -->
  <router-link
  v-for="link in links"
  :key="link.url"
  tag='li'
  active-class="active"
  :to="link.url"
  :exact="link.exact"
  > <!--v-for="link in links" - цикл для вывода
  :key="link.url"  - ключ
  tag='li' - тег
  active-class="active" - для стиля materiliz
  :to="link.url" -  ссылка
  :exact="link.exact - значени поулмолчания-->
  <a href="#" class="waves-effect waves-orange pointer">{{link.title}}</a>
  </router-link>
</ul>



<!--
      <li>
        <a href="#" class="waves-effect waves-orange pointer">Счет</a>
      </li>
      <li>
        <a href="#" class="waves-effect waves-orange pointer">Пополнить счет</a>
      </li>
      <li>
        <a href="#" class="waves-effect waves-orange pointer">Изменить тариф</a>
      </li>
      <li>
        <a href="#" class="waves-effect waves-orange pointer">История</a>
      </li>
      <li>
        <a href="#" class="waves-effect waves-orange pointer">Услуги и опции</a>
      </li>
-->
</template>


<script>
export default {
  props: ['value'],
  name: 'Sidebar',
  data: () => ({ //стрелочная функция
    /*links: [
      {title: 'Счет', url: '/', exact: true}, // {ключ, значение}
      {title: 'Пополнить счет', url: '/bank'},
      {title: 'Изменить тариф', url: '/tariff'},//тарифы
      {title: 'Пользователи', url: '/user'},//блокировка номера, удалить акаунт,
      {title: 'Тарифы', url: '/tariffadmin'},
      {title: 'Mobile', url: '/mobile'},//блокировка номера, удалить акаунт,
      {title: 'История', url: '/history'}
    ]//массив*/
  })

}
</script>


