<template>
  <table class="table table-striped">
    <thead>
      <tr>
        <th v-for="allowedHeader in allowedHeaderItems" scope="col">{{allowedHeader}}</th>
        <th v-if="allowedDeletion" style="text-align: right">Akce</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="item in items">
        <td @click="$router.push($route.path + '/' + item[detailIdKey])" v-for="header in allowedHeaderItems"> {{item[header]}} </td>
        <td v-if="allowedDeletion" style="text-align: right">
          <button type="button" @click="deleteItem(item[detailIdKey])" class="btn-close" aria-label="Close"></button>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  name: "TableList",
  props: {
    allowedDeletion: {
      type: Boolean,
      default: false
    },
    detailIdKey: String,
    items: Array | Object | Proxy,
    allowedHeaderItems: Array | Object
  },
  methods: {
    deleteItem(id) {
      this.$emit('deleteItem', id);
    }
  }
}
</script>

<style scoped>

</style>