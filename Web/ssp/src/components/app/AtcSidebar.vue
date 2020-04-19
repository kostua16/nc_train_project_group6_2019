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


</template>


<script>
export default {
  props: ['value'],
  data: () => ({ //стрелочная функция
    links: [
      {title: 'Mobile', url: '/atc/mobile'},//блокировка номера, удалить акаунт,
    ]//массив
  })

}
</script>


