<template>
  <div class="main-wrapper">
    <MainHeader :title="title"/>
    <TableList v-if="items" :items="items" :allowedHeaderItems="headerItems" />
    <Pagination @pageChanged="handlePageChanged" />
  </div>
</template>

<script>
import TableList from "@/components/TableList.vue";
import MainHeader from "@/components/MainHeader.vue";
import BaseEntityService from "@/_services/base.entity.service";

export default {
  name: "WrapperList",
  components: {TableList, MainHeader},
  data () {
    return {
      items: [],
      headerItems: []
    };
  },
  props: {
    title: String,
    allowedHeaderItems: Array,
    entityService: BaseEntityService
  },
  methods: {
    handlePageChanged (value) {
      this.entityService.getAll()
      console.log("Emitováno na hodnotu", value);
    }
  },
  created () {
    this.entityService.getAll(1, 5).then( resp => {
      this.items = resp.data;
      if (Array.isArray(this.allowedHeaderItems) && this.allowedHeaderItems.length) {
        this.headerItems = this.allowedHeaderItems;
      } else {
        this.headerItems = this.items ? Object.keys(this.items[0]) : [];
      }

    });
  }
}
</script>

<style scoped>

</style>