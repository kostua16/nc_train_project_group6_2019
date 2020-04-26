<template>
  <div class="row">
    <form class="col s12">
      <div class="row">

        <div class="input-field col s6">
          <i class="material-icons prefix">phone_forwarded</i>
          <input id="icon_telephone" type="tel" v-model.trim="telephoneFrom" class="validate">
          <label for="icon_telephone">From the Telephone</label>
        </div>
        <div class="input-field col s6">
          <i class="material-icons prefix">phone_in_talk</i>
          <input id="icon_telephone2" type="tel" v-model.trim="telephoneTo" class="validate">
          <label for="icon_telephone2">In the Phone</label>
        </div>

      </div>
    </form>

    <form class="col s12">
      <div class="row">
        <div class="input-field col s4">
          <a class="waves-effect waves-light btn" @click.prevent="startModal('call')">
            <i class="material-icons right">send</i>Начать звонок</a>
        </div>
        <div class="input-field col s4">
          <a class="waves-effect waves-light btn" @click.prevent="startModal('sms')">
            <i class="material-icons right">send</i>Отправить СМС</a>
        </div>
        <div class="input-field col s4">
          <a class="waves-effect waves-light btn" @click.prevent="startModal('internet')">
            <i class="material-icons right">send</i>Включить интернет</a>
        </div>
      </div>
    </form>
    <modal
      v-if="showModal"
      v-bind:text="modalText"
      v-bind:active="!actionStopped"
      @close="showModal = false"
      @deactivate="actionStoppedByUser = true"
      @activate="spend(typeModal)"
    />
  </div>
</template>

<script>
  import Modal from "./Modal";
  import BasePage from "../../components/base/BasePage";

  export default {
    extends: BasePage,
    data: () => ({
      telephoneFrom: '',
      telephoneTo: '',
      showModal: false,
      modalText: '',
      actionStoppedByUser: false,
      actionStoppedBySystem: false,
      actionDuration: 0,
      typeModal: 'call',
    }),
    computed: {
      actionStopped() {
        return this.actionStoppedByUser === true || this.actionStoppedBySystem === true;
      }
    },
    mounted() {
    },
    methods: {
      startModal(type) {
        this.typeModal=type;
        this.showModal=true;
        switch (resource) {
          case "call":
            this.modalText = `Запустить звонок?`;
            break;
          case "sms":
            this.modalText = `Отправить СМС?`;
            break;
          case "internet":
            this.modalText = `Включить загрузку файлов через интернет?`;
            break;
        }
        this.actionDuration = 0;
      },
      async spend(resource) {

        if (this.showModal === true) {
          if (!this.actionStopped) {
            this.showModal = true;
            try {
              let operation = "";
              switch (resource) {
                case "call":
                  operation = "DO_CALL";
                  break;
                case "sms":
                  operation = "DO_SMS";
                  break;
                case "internet":
                  operation = "DO_INTERNET";
                  break;
              }
              await this.$store.dispatch(operation, {telephoneFrom: this.telephoneFrom, telephoneTo: this.telephoneTo});
              this.actionDuration += 1;
              var that = this;
              setTimeout(() => that.spend(resource), 1000);
            } catch (e) {
              this.actionStoppedBySystem = true;
            }
          }

          if (!this.actionStoppedByUser && !this.actionStoppedBySystem) {
            switch (resource) {
              case "call":
                this.modalText = `Абонент производит звонок. Длительность - ${this.actionDuration}`;
                break;
              case "sms":
                this.modalText = `Абонент отправляет смс. Количество - ${this.actionDuration}`;
                break;
              case "internet":
                this.modalText = `Абонент использует интернет. Объем - ${this.actionDuration}`;
                break;
            }
          } else {
            if (this.actionStoppedByUser) {
              switch (resource) {
                case "call":
                  this.modalText = `Звонок завершен абонентом. Длительность - ${this.actionDuration}`;
                  break;
                case "sms":
                  this.modalText = `Отправка смс завершена абонентом. Количество - ${this.actionDuration}`;
                  break;
                case "internet":
                  this.modalText = `Пользование интернетом завершено абонентом. Объем - ${this.actionDuration}`;
                  break;
              }
            } else {
              if (this.actionStoppedBySystem) {
                switch (resource) {
                  case "call":
                    this.modalText = `Звонок прерван системой. Длительность - ${this.actionDuration}`;
                    break;
                  case "sms":
                    this.modalText = `Отправка смс прервана системой. Количество - ${this.actionDuration}`;
                    break;
                  case "internet":
                    this.modalText = `Пользование интернетом прервано системой. Объем - ${this.actionDuration}`;
                    break;
                }
              }
            }
          }
        }
      },
    },
    components: {
      Modal
    }
  }
</script>
