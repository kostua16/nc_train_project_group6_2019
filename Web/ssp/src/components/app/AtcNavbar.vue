<template>
   <nav class="navbar orange lighten-1">
      <div class="nav-wrapper">
        <div class="navbar-left">
          <a href="#" @click.prevent="$emit('click')">
            <i class="material-icons black-text">dehaze</i>
          </a>
          <span class="black-text">ATC</span>
        </div>

        <ul class="right hide-on-small-and-down">
          <li>
            <a class="dropdown-trigger black-text" href="#" data-target="dropdown" ref="dropdown" @click.prevent="logout">
              Выход
            </a>
          </li>
        </ul>
      </div>
    </nav>
</template>


<script>
import axios from 'axios'
export default {
  data: () => ({
      dropdown: null,
      username: '-'
    }),
  methods: {
    logout() { //метод выхода из системы// отдельный метод чтобы куки сбросить и.т.д
      //console.log('Logout')
      this.$router.push('/') //выгоняем из системы
    }
  },
  mounted() {
    this.dropdown = M.Dropdown.init(this.$refs.dropdown, {
      constrainWidth: true //materializecss.com/dropdown.html
    })
      this.getNameUser()
  },
  // beforeDestroy() { //избавляемся от утечек памяти // работает когда покидаем страницу //по сути не нужно
  //   if (this.dropdown && this.dropdown.destroy) {
  //     this.dropdown.destroy() //метод destroy
  //   }
  // }
}
</script>
