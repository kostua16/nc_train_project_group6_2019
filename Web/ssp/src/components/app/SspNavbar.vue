<template>
   <nav class="navbar orange lighten-1">
      <div class="nav-wrapper">
        <div class="navbar-left">
          <a href="#" @click.prevent="$emit('click')">
            <i class="material-icons black-text">dehaze</i>
          </a>
          <span class="black-text">SSP</span>
        </div>

        <ul class="right hide-on-small-and-down">
          <li>
            <a
                class="dropdown-trigger black-text"
                href="#"
                data-target="dropdown"
                ref="dropdown"
            >
            <!-- ref="dropdown" задали для обрашения к нему в mounted, значение ref может быть любым-->
              {{username}}
              <i class="material-icons right">arrow_drop_down</i>
            </a>

            <ul id='dropdown' class='dropdown-content'>
              <!--<li>
                <router-link to="/" class="black-text">
                  <i class="material-icons">account_circle</i>Профиль
                </router-link>
              </li>-->
              <li class="divider" tabindex="-1"></li>
              <li>
                <a href="#" class="black-text" @click.prevent="logout">
                  <i class="material-icons">assignment_return</i>Выйти
                </a>
              </li>
            </ul>
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
      username: ''
    }),
  methods: {
    getNameUser() {
       axios.get('http://localhost:8101/home/?token='+localStorage.getItem('token')).then(response => {
         this.username = response.data.name
      }).catch(e => {
        console.log(e)
      })
    },
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
