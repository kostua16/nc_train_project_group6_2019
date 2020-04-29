<template>
  <div class="row">
    <div class="col s12">
      <div class="row">
        <div class="input-field col s3">
          <a class="waves-effect waves-light btn" @click.prevent="startImitator" :disabled="imitationState || imitatorCalled">
            <i class="material-icons right">send</i>Включить имитацию</a>
        </div>
        <div class="input-field col s3">
          <a class="waves-effect waves-light btn" @click.prevent="stopImitator" :disabled="!imitationState || imitatorCalled">
            <i class="material-icons right">send</i>Выключить имитацию</a>
        </div>
        <div class="input-field col s3">
          <a class="waves-effect waves-light btn" @click.prevent="runImReg" :disabled="imitatorCalled">
            <i class="material-icons right">send</i>Имитация регистрации</a>
        </div>
        <div class="input-field col s3">
          <a class="waves-effect waves-light btn" @click.prevent="runImUsage" :disabled="imitatorCalled">
            <i class="material-icons right">send</i>Имитация пользования системой</a>
        </div>
      </div>
      <div class="row">
        <div class="col s12">
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
          </div>

          <div class="row">
            <form class="col s12">
              <div class="input-field col s3 offset-sm-3">
                <a class="waves-effect waves-light btn" @click.prevent="startModal('call')">
                  <i class="material-icons right">send</i>Начать звонок</a>
              </div>
              <div class="input-field col s3">
                <a class="waves-effect waves-light btn" @click.prevent="startModal('sms')">
                  <i class="material-icons right">send</i>Отправить СМС</a>
              </div>
              <div class="input-field col s3">
                <a class="waves-effect waves-light btn" @click.prevent="startModal('internet')">
                  <i class="material-icons right">send</i>Включить интернет</a>
              </div>
            </form>
          </div>
        </div>
      </div>
      <div class="row">
        <modal
          v-if="showModal"
          v-bind:text="modalMessage"
          v-bind:active="actionIsActive"
          @close="stopModal"
          @deactivate="stopOperation"
          @activate="spend"
        />
      </div>

    </div>
  </div>


</template>

<script>
  import Modal from "./Modal";

  const actionNotStarted = 0;
  const actionStarted = 1;
  const actionStoppedByUser = 2;
  const actionStoppedBySystem = 3;

  export default {
    data: () => ({
      telephoneFrom: '',
      telephoneTo: '',
      showModal: false,
      actionDuration: 0,
      actionBalance: 0,
      actionPackageCount: 0,
      actionState:0,
      typeModal: 'call',
      imitatorCalled: false
    }),
    computed: {
      imitationState() {
        return this.$store.state.IMITATOR_STATE;
      },
      actionIsActive() {
        return this.actionState === actionStarted;
      },
      actionIsStopped() {
        return this.actionState === actionStoppedByUser || this.actionState === actionStoppedBySystem;
      },
      modalMessage() {
        switch (this.actionState) {
          case actionNotStarted:
            switch (this.typeModal) {
              case "call": return `Запустить звонок?`;
              case "sms": return `Отправить СМС?`;
              case "internet": return `Включить загрузку файлов через интернет?`;
              default: return ""
            }
          case actionStarted:
            switch (this.typeModal) {
              case "call": return `Абонент производит звонок. Длительность - ${this.actionDuration} мин. Баланс - ${this.actionBalance} р. Остаток пакета - ${this.actionPackageCount} мин. `;
              case "sms": return `Абонент отправляет смс. Количество - ${this.actionDuration} шт. Баланс - ${this.actionBalance} р. Остаток пакета - ${this.actionPackageCount} шт. `;
              case "internet": return `Абонент использует интернет. Объем - ${this.actionDuration} кб. Баланс - ${this.actionBalance} р. Остаток пакета - ${this.actionPackageCount} кб. `;
              default: return ""
            }
          case actionStoppedByUser:
            switch (this.typeModal) {
              case "call": return `Звонок завершен абонентом. Длительность - ${this.actionDuration} мин. Баланс - ${this.actionBalance} р. Остаток пакета - ${this.actionPackageCount} мин. `;
              case "sms": return `Отправка смс завершена абонентом. Количество - ${this.actionDuration} шт. Баланс - ${this.actionBalance} р. Остаток пакета - ${this.actionPackageCount} шт. `;
              case "internet": return `Пользование интернетом завершено абонентом. Объем - ${this.actionDuration} кб. Баланс - ${this.actionBalance} р. Остаток пакета - ${this.actionPackageCount} кб. `;
              default: return ""
            }
          case actionStoppedBySystem:
            switch (this.typeModal) {
              case "call": return `Звонок прерван системой. Длительность - ${this.actionDuration} мин. Баланс - ${this.actionBalance} р. Остаток пакета - ${this.actionPackageCount} мин. `;
              case "sms": return `Отправка смс прервана системой. Количество - ${this.actionDuration} шт. Баланс - ${this.actionBalance} р. Остаток пакета - ${this.actionPackageCount} шт. `;
              case "internet": return `Пользование интернетом прервано системой. Объем - ${this.actionDuration} кб. Баланс - ${this.actionBalance} р. Остаток пакета - ${this.actionPackageCount} кб. `;
              default: return ""
            }
          default: return ""
        }
      }
    },
    mounted() {
    },
    methods: {
      async startImitator() {
        this.imitatorCalled = true;
        await this.$store.dispatch("START_IMITATOR");
        this.imitatorCalled = false;
      },
      async stopImitator() {
        this.imitatorCalled = true;
        await this.$store.dispatch("STOP_IMITATOR");
        this.imitatorCalled = false;
      },
      async runImReg() {
        this.imitatorCalled = true;
        await this.$store.dispatch("RUN_REGISTRATION_IMITATION");
        this.imitatorCalled = false;
      },
      async runImUsage() {
        this.imitatorCalled = true;
        await this.$store.dispatch("RUN_USAGE_IMITATION");
        this.imitatorCalled = false;
      },
      startModal(type) {
        this.typeModal=type;
        this.actionDuration = 0;
        this.actionBalance = 0;
        this.actionPackageCount = 0;
        this.actionState = actionNotStarted;
        this.showModal = true;
      },
      stopOperation() {
        this.actionState = actionStoppedByUser;
        this.showModal = false;
      },
      stopModal() {
        this.stopOperation();
        this.showModal = false;
      },
      async spend() {
        if (this.showModal === true) {
          if (this.actionState === actionNotStarted) {
            this.actionState = actionStarted;
          }
          if (this.actionState === actionStarted) {
            this.showModal = true;
            try {
              let operation = "";
              switch (this.typeModal) {
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
              let result = await this.$store.dispatch(operation, {telephoneFrom: this.telephoneFrom, telephoneTo: this.telephoneTo});

              this.actionDuration =parseFloat(result.count) + parseFloat(this.actionDuration);
              this.actionBalance =  parseFloat(result.balance);
              this.actionPackageCount = parseFloat(result.package);
              if(result.stopped === "true" || result.stopped === true){
                this.actionState = actionStoppedBySystem;
              } else {
                const that = this;
                setTimeout(() => that.spend(), 1000);
              }

            } catch (e) {
              this.actionState = actionStoppedBySystem;
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
