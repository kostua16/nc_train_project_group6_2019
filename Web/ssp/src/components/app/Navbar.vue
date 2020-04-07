<template>
   <nav class="navbar orange lighten-1">
      <div class="nav-wrapper">
        <div class="navbar-left">
          <a href="#" @click.prevent="$emit('click')">
            <i class="material-icons black-text">dehaze</i>
          </a>
          <span class="black-text">Личный кабинет</span>
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
              <li>
                <a href="#" class="black-text">
                  <i class="material-icons">account_circle</i>Профиль
                </a>
              </li>
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
      username: 'username'
    }),
  methods: {
    getNameUser() {
      // token = localStorage.getItem('token')
       axios.get('http://localhost:8102/getAccount/?token='+localStorage.getItem('token')).then(response => {
         this.username = response.data.name
        //  console.log(response.data)
      //  console.log(this.username) //Токен
        // localStorage.setItem('token',this.token)
       // console.log(localStorage.getItem('token'))
      }).catch(e => {
        console.log(e)
      })
    },
    logout() { //метод выхода из системы// отдельный метод чтобы куки сбросить и.т.д
      //console.log('Logout')
      this.$router.push('/login?message=logout') //передаем сообщение что мы вышли
    }
  },
  mounted() {
    this.dropdown = M.Dropdown.init(this.$refs.dropdown, {
      constrainWidth: true //materializecss.com/dropdown.html
    })
    // console.log(localStorage.getItem('token'))
    this.getNameUser()
  },
  beforeDestroy() { //избавляемся от утечек памяти // работает когда покидаем страницу //по сути не нужно
  //  console.log('beforeDestroy')
    if (this.dropdown && this.dropdown.destroy) {
      this.dropdown.destroy() //метод destroy
    }
  }
}
</script>
