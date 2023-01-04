<template>
  <div>
    <label class="mr-2" for="select">{{ label }}</label>
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
    label: String,
    selected: String | Number,
    options: {
      type: Array,
      required: true
    }
  }
}
</script>