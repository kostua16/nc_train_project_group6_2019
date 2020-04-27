<template>
  <form class="input-field" @submit.prevent="onSubmit">
    <label for="inputField">{{label}}</label><input id="inputField" type="number" v-model.trim="value" class="validate">
    <div class="error" v-if="!$v.value.required">Введите сумму</div>
    <div class="error" v-if="!$v.value.isNumber">Введите числовое значение</div>
  </form>
</template>
<script>
  import {numeric, required} from 'vuelidate/lib/validators'

  export default {
    props: {
      label: '',
      initValue: 0,
      callback: (value) => {
      }
    },
    data: () => ({
      value: 0
    }),
    mounted() {
      this.value = this.initValue;
    },
    validations: {
      value: {required, isNumber: numeric}
    },
    methods: {
      onSubmit() {
        this.callback(this.value);
      }
    }
  }
</script>

<style scoped>

</style>
