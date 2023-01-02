<template>
  <div class="filters">
    <search @search="search"/>
    <button class="btn btn-outline-primary" @click="$router.push($route.path + '/add')" ><i class="fas fa-plus"></i> Přidat nový záznam</button>
  </div>
</template>

<script>
import Search from "@/components/Search.vue";
export default {
  name: "Filters",
  components: {Search},
  methods: {
    search(searchQuery) {
      this.$emit('search', searchQuery);
    }
  }
}
</script>