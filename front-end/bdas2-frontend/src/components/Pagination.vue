<template>
  <nav aria-label="Page navigation example">
    <ul class="pagination">

      <template v-if="current > 3">
        <li class="page-item">
          <a class="page-link" href="#" v-on:click="goToPage(1)">1</a>
        </li>
        <li class="page-item disabled"  v-if="current > 4">
          <span class="page-link">...</span>
        </li>
      </template>

      <template v-for="page in pages" v-bind:key="page" >
        <li v-if="page < current + 3 && page > current - 3" v-bind:class="{ 'active': page === current }" class="page-item">
          <a class="page-link" href="#" v-on:click="goToPage(page)">{{ page }}</a>
        </li>
      </template>

      <template v-if="current < pages.length - 2">
        <li class="page-item disabled" v-if="current < pages.length - 3">
          <span class="page-link">...</span>
        </li>
        <li class="page-item">
          <a class="page-link" href="#" v-on:click="goToPage(pages.length)">{{ pages.length }}</a>
        </li>
      </template>

    </ul>
  </nav>
</template>

<script>
export default {
  name: "Pagination",
  props: {
    itemsCount: Number
  },
  data() {
    return {
      current: 1,
      perPage: 15,
      pages: []
    }
  },
  methods: {
    goToPage(page) {
      this.current = page;
      this.$emit('pageChanged', page, this.perPage);
    }
  },
  mounted() {
    console.log("COUNT", this.itemsCount);
    for (let i = 1; i <= Math.ceil(this.itemsCount / this.perPage); i++) {
      this.pages.push(i);
    }
  }
}
</script>

<style>
.pagination {
  justify-content: center;
}
</style>