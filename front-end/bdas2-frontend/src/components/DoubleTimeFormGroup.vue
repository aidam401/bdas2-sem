<template>
  <div class="d-flex flex-column align-items-start">
    <label class="h5 mb-0">{{item.text}}</label>
    <div class="d-flex flex-row justify-content-between">
      <div class="form-group mr-2">
        <label for="date1">Příjezd</label>
        <input class="form-control" type="time" id="date1" v-model="item.PRIJEZD" />
      </div>
      <div class="form-group ml-2">
        <label for="date2">Odjezd</label>
        <input class="form-control" type="time" id="date2" v-model="item.ODJEZD" />
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "DoubleTimeFormGroup",
  props: {
    item: Object,
  }
}
</script>