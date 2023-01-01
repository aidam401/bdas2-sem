<template>
  <div class="main-wrapper">
    <MainHeader :title="title"/>
    <TableList v-if="items" :items="items" :allowedHeaderItems="headerItems" :detailIdKey="detailIdKey"/>
    <Pagination v-if="itemsCount !== 0" :items-count="itemsCount" @pageChanged="handlePageChanged" />
  </div>
</template>

<script>
import TableList from "@/components/TableList.vue";
import MainHeader from "@/components/MainHeader.vue";
import BaseEntityService from "@/_services/base.entity.service";
import Pagination from "@/components/Pagination.vue";

export default {
  name: "WrapperList",
  components: {TableList, MainHeader, Pagination},
  data () {
    return {
      items: [],
      headerItems: [],
      itemsCount: 0
    };
  },
  props: {
    title: String,
    detailIdKey: String,
    allowedHeaderItems: Array,
    entityService: BaseEntityService
  },
  created () {
    this.refreshItems(5, 0).then( () => {
      this.initHeaders();
    });
  },
  methods: {
    initHeaders () {
      if (Array.isArray(this.allowedHeaderItems) && this.allowedHeaderItems.length) {
        this.headerItems = this.allowedHeaderItems;
      } else {
        this.headerItems = this.items ? Object.keys(this.items[0]) : [];
      }
    },
    handlePageChanged (page, perPage) {
      let limit = perPage;
      let offset = perPage * (page - 1);
      this.refreshItems(limit, offset);
    },
    refreshItems(limit, offset) {
      return this.entityService.getCount().then( resp => {
        this.itemsCount = resp.data;
      }).then(() => {
        return this.entityService.getAll(limit, offset).then(resp => {
          this.items = resp.data;
        })
      });
    }
  }
}
</script>