<template>
  <div>
    <span
      v-if="mode === 'view'"
      class="view-mode"
      @click="toEditMode"
    >
      {{ renderText }}
    </span>
    <span
      v-if="mode === 'edit'"
      class="edit-mode"
    >
      <input
        v-model="editText"
        type="text"
        @focusout="saveChanges"
        @blur="saveChanges"
      >
    </span>
  </div>
</template>
<script>
export default {
  props: {
    id: {
      default: 0,
      type: Number,
    },
    text: {
      default: '',
      type: String,
    },
    name: {
      default: 'text',
      type: String,
    },
  },
  data() {
    return {
      mode: 'view',
      editText: '',
    };
  },
  computed: {
    renderText() {
      return this.text;
    },
  },
  methods: {
    toEditMode() {
      this.editText = this.renderText;
      this.mode = 'edit';
    },
    saveChanges() {
      this.mode = 'view';
      const result = {
        id: this.id,
        name: this.name,
        old_value: this.text,
        new_value: this.editText,
      };
      this.$emit('submit', result);
      this.$emit('update:text', result);
    },
  },
};
</script>
<style scoped lang="stylus">
  .button {
    font-style italic
  }
  .edit-mode {
    background-color darkkhaki
  }
  .view-mode {
    background-color white
  }
</style>
