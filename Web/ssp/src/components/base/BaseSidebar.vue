<template>
  <ul class="sidenav app-sidenav" :class="{open: value}">
    <!-- сушаем значение клик если оно активировано дописываем open в класс  -->
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
  import {mapState} from "vuex";
  import BasePage from "./BaseComponent";

  export default {
    extends: BasePage,
    props: ['value'],
    computed: mapState({
      links: state => state.SIDEBAR_LINKS
    }),

  }
</script>


