<template>
  <table class="table table-striped">
    <thead>
    <!-- TODO v-for -->
      <tr>
        <th v-for="allowedHeader in allowedHeaderItems" scope="col">{{allowedHeader}}</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="item in items">
        <td v-for="header in allowedHeaderItems"> {{item[header]}} </td>
      </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  name: "TableList",
  props: {
    items: Array | Object | Proxy,
    allowedHeaderItems: Array | Object
  }
}
</script>

<style scoped>

</style>