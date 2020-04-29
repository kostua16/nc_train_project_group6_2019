<template>
  <form class="input-field col s12" @submit.prevent="updateBalance">
    <for class="col s8">
<!--      <label for="inputField">{{label}}</label>-->
      <input id="inputField" type="number" v-model.trim="value" class="validate"  >
      <div class="error" v-if="!$v.value.required">Введите сумму</div>
      <div class="error" v-if="!$v.value.isNumber">Введите числовое значение</div>
    </for>
    <for  class="col s4">
    <button class="btn waves-effect waves-light" type="submit" @click.prevent="updateBalance" name="up">
      <i class="material-icons right">send</i>
    </button>
    </for>
  </form>
</template>
<script>
  import {numeric, required} from 'vuelidate/lib/validators'

  export default {
    props: {
      label: '',
      initValue: 0,
      phoneNumber: 0,
      smallWindow: false,
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
      },
      async updateBalance() {
        await this.$store.dispatch("UPDATE_USER_BALANCE", {amount: this.value, phone: this.phoneNumber});
        await this.$emit('changed');
      },
    }
  }
</script>

<style scoped>

</style>
