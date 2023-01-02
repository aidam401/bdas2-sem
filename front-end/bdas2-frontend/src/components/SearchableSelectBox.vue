<template>
  <div class="form-group">
    <label for="select">{{label}}</label>
    <div class="input-group">
      <input type="text" class="form-control" v-model="search" placeholder="Search options" @focus="filterOptions" @input="filterOptions">
      <div class="input-group-append">
        <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          {{ selectedOption?.text || 'Select option' }}
        </button>
        <div v-if="filteredOptions?.length > 0" class="dropdown-menu" >
          <a class="dropdown-item" v-for="option in filteredOptions" @click="selectOption(option)">{{ option.text }}</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SearchableSelectBox',
  props: {
    label: String,
    options: Array,
    selected: Object
  },
  data() {
    return {
      search: '',
      selectedOption: this.selected,
      filteredOptions: []
    }
  },
  created() {

    this.selectedOption = this.selected;
    if (!this.selectedOption) {
      this.filterOptions();
    }
  },
  methods: {
    filterOptions() {
      this.filteredOptions = this.options?.filter(option => option.text?.toLowerCase().includes(this.search.toLowerCase()))
      this.selectedOption = null;
    },
    selectOption(option) {
      this.selectedOption = option;
      this.search = '';
      this.filteredOptions = [];
      this.$emit('changeSelection', option.value, option.text);
    }
  }
}
</script>

<style scoped>
input {
  width: 60%;
}
.input-group-append {
  width: 40%;
}
.dropdown-toggle {
  width: 100%;
}
</style>
