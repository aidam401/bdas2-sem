<template>
  <div class="form-group">
    <label for="selectBox">{{title}}</label>
    <div class="input-group">
      <select class="custom-select" id="selectBox" v-model="selected">
        <option v-for="option in options" :value="option.value">{{ option.text }}</option>
      </select>
      <div class="input-group-append">
        <button :disabled="!selected" class="btn btn-outline-secondary" @click="addOption">Přidat</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SelectBoxAddButton',
  props: {
    title: String,
    options: Array
  },
  data() {
    return {
      selected: null
    };
  },
  methods: {
    addOption() {
      if (this.selected) {
        this.$emit('addItem', this.selectedOption);
        this.selected = null;
      }
    },
  },
  computed: {
    selectedOption() {
      return this.options.find((option) => option.value === this.selected);
    },
  }
};
</script>
