<template>

  <form class="card auth-card"><!-- тут прослушиваем событие enter в кнопке и вызваем метод "submitHandler" -->
    <div class="card-content ">
      <span class="card-title ">Выберите модуль</span>
      <button
        class="btn waves-effect waves-light auth-submit"
        type="submit" v-for = "(module) in active_modules"
        @click.prevent="goToModuleNoBack(module)">{{module.name}}<i class="material-icons right"/>
      </button>
    </div>
  </form>
</template>


<script>
  import BasePage from '../components/base/BaseComponent'
  import {mapState} from "vuex";
  export default {
    extends: BasePage,
    name: 'login',
    computed: mapState({
      active_modules: state => state.ACTIVE_MODULES,
    }),
  }
</script>

<style scoped>
  .btn {
    margin-bottom: 2rem;
  }
</style>
