<template>
  <div>
    <ul class="list-group">
      <li class="list-group-item" v-for="item in items" :key="item.name">
        <span class="font-weight-bold">{{ item.NAZEV_ZASTAVKA  }}</span>
        <span @click="handleClick" class="float-right">{{ timeOnly( extract(item.PRAVIDELNY_PRIJEZD, 1)) }}h</span>
        <br>
        <span>Pravidelný odjezd:</span>
        <span class="float-right">{{ timeOnly(extract(item.PRAVIDELNY_ODJEZD, 1)) }}h</span>
      </li>
    </ul>
  </div>
</template>

<script>

import DateTimeMixin from "@/mixins/DateTimeMixin.vue";

export default {
  name: 'CasyZastavek',
  mixins: [DateTimeMixin],
  props: {
    items: Object
  },
  methods: {
    handleClick (e) {
      console.log(
          typeof this.items[0].PRAVIDELNY_ODJEZD,
          this.items[0].PRAVIDELNY_ODJEZD,

      )
    }
  }
}
</script>