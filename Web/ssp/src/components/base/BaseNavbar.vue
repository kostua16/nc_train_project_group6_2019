<template>
   <nav class="navbar orange lighten-1">
      <div class="nav-wrapper">
        <div class="navbar-left">
          <a href="#" @click.prevent="$emit('click')">
            <i class="material-icons black-text">dehaze</i>
          </a>
          <span class="black-text">{{navbarName}}</span>
        </div>
        <ul class="right hide-on-small-and-down">
          <li>
            <a class="dropdown-trigger black-text" href="#" data-target="dropdown" ref="dropdown" >
              <!-- ref="dropdown" задали для обрашения к нему в mounted, значение ref может быть любым-->
              <span v-if="login_done">{{username}}</span>
              <span v-if="!login_done">Меню</span>
              <i class="material-icons right">arrow_drop_down</i>
            </a>
            <ul id='dropdown' class='dropdown-content'>
              <li class="divider" tabindex="-1"></li>
              <li>
                <a href="#" class="black-text" @click.prevent="toRoot">Модули</a>
              </li>
              <li class="divider" v-if="active_modules.length>0"></li>
              <li v-for = "(module) in active_modules">
                <a href="#" class="black-text" @click.prevent="goToModule(module)">{{module.name}}</a>
              </li>
              <li class="divider" v-if="login_done"></li>
              <li v-if="login_done">
                <a href="#" class="black-text" @click.prevent="logout"><i class="material-icons">assignment_return</i>Выйти</a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </nav>
</template>


<script>
import {mapState} from "vuex";
import BasePage from "./BasePage";
export default {
  extends: BasePage,
  data: () => ({
    dropdown: null,
  }),
  computed: mapState({
    username: state => state.CURRENT_USER != null ? state.CURRENT_USER.name : "Not Logged in",
    login_done: state => state.CURRENT_USER != null,
    active_modules: state => state.ACTIVE_MODULES,
    navbarName: state => state.NAVBAR_NAME
  }),
  methods: {
    logout() { //метод выхода из системы// отдельный метод чтобы куки сбросить и.т.д
      //console.log('Logout')
      this.$store.dispatch("LOGOUT");
      this.goTo("/")
    },
  },
  mounted() {
    this.dropdown = M.Dropdown.init(this.$refs.dropdown, {
      constrainWidth: true //materializecss.com/dropdown.html
    })
  },
}
</script>
