<template>
  <div>
    <label class="mr-2" for="select">Select an option:</label>
    <select class="custom-select" id="select" v-model="selected" v-bind:value="selected">
      <option v-for="option in options" :key="option.value" :value="option.value">
        {{ option.text }}
      </option>
    </select>
  </div>
</template>

<script>
export default {
  name: 'SelectBox',
  props: {
    selected: {
      type: String,
      required: true
    },
    options: {
      type: Array,
      required: true
    }
  }
}
</script>