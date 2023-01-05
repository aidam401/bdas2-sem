<template>
  <div>
    <ul class="list-group">
      <li class="list-group-item" v-for="item in items" :key="item.name">
        <span class="font-weight-bold">{{ item.NAZEV_ZASTAVKA }}</span>
        <span class="float-right">{{ item.PRAVIDELNY_PRIJEZD }}</span>
        <br>
        <span>Pravidelný odjezd:</span>
        <span class="float-right">{{ item.PRAVIDELNY_ODJEZD }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'CasyZastavek',
  props: {
    items: Object
  },
}
</script>