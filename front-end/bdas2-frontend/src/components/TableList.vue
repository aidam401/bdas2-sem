<template>
  <table class="table table-striped">
    <thead>
      <tr>
        <th v-for="allowedHeader in allowedHeaderItems" scope="col">{{allowedHeader}}</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="item in items" @click="$router.push($route.path + '/' + item[detailIdKey])" >
          <td v-for="header in allowedHeaderItems"> {{item[header]}} </td>
      </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  name: "TableList",
  props: {
    detailIdKey: String,
    items: Array | Object | Proxy,
    allowedHeaderItems: Array | Object
  }
}
</script>

<style scoped>

</style>