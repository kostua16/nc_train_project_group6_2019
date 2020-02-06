<template>
  <div id="app">
    <div id="nav">
      <router-link to="/">
        Home
      </router-link>
      |
      <router-link to="/api">
        Api
      </router-link>
      |
      <router-link to="/books">
        Books
      </router-link>
      |
      <router-link to="/search">
        Search
      </router-link>
      |
      <router-link to="/vue">
        VueInfo
      </router-link>
      |
      <a href="/thymeleaf/thymeleaf">ThymeleafInfo</a>
    </div>
    <router-view />
  </div>
</template>

<style lang="stylus">
#app
  font-family 'Avenir', Helvetica, Arial, sans-serif
  -webkit-font-smoothing antialiased
  -moz-osx-font-smoothing grayscale
  text-align center
  color #2c3e50
  margin-top 60px
</style>
