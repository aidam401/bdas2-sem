<template>
  <form class="form-inline my-2 my-lg-0">
    <input
        class="form-control mr-sm-2"
        type="search"
        placeholder="Search"
        aria-label="Search"
        v-model="query"
        @input="search"
    />
    <button class="btn btn-outline-success my-2 my-sm-0" type="submit" @click.prevent="search">
      Search
    </button>
  </form>
</template>
<script>
export default {
  name: "Search",
  data() {
    return {
      query: ''
    };
  },
  methods: {
    search() {
      this.$emit('search', this.query);
    }
  }
};
</script>