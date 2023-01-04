<template>
  <div class="main-wrapper">
    <MainHeader :title="title"/>
    <Filters @search="handleSearch"/>
    <TableList v-if="items" :items="items" :allowedHeaderItems="headerItems" :detailIdKey="detailIdKey" :allowed-deletion="this.isAdmin" @deleteItem="handleDeleteItem"/>
    <Pagination v-if="itemsCount !== 0" :items-count="itemsCount" @pageChanged="handlePageChanged" />
  </div>
</template>

<script>
import TableList from "@/components/TableList.vue";
import MainHeader from "@/components/MainHeader.vue";
import BaseEntityService from "@/_services/base.entity.service";
import Pagination from "@/components/Pagination.vue";
import Filters from "@/components/Filters.vue";
import UserMixin from "@/mixins/UserMixin.vue";

export default {
  name: "WrapperList",
  components: {Filters, TableList, MainHeader, Pagination},
  mixins: [UserMixin],
  data () {
    return {
      items: [],
      headerItems: [],
      itemsCount: 0,
      search: '',
      page: 1,
      perPage: 15
    };
  },
  props: {
    title: String,
    detailIdKey: String,
    allowedHeaderItems: Array,
    entityService: BaseEntityService,
    deleteService: BaseEntityService,
  },
  created () {
    this.refreshItems(15, 0).then( () => {
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
      this.page = page;
      this.perPage = perPage;
      let limit = perPage;
      let offset = perPage * (page - 1);
      this.refreshItems(limit, offset);
    },
    refreshItems(limit, offset) {
      return this.entityService.getCount().then( resp => {
        this.itemsCount = resp.data;
      }).then(() => {
        return this.entityService.getAll(limit, offset, this.search).then(resp => {
          this.items = resp.data;
        })
      });
    },
    handleDeleteItem (id) {
      return this.deleteService.deleteEntity(id).then( (resp) => {
        let limit = this.perPage;
        let offset = this.perPage * (this.page - 1);
        this.refreshItems(limit, offset);
      })
    },
    handleSearch (searchQuery) {
      this.search = searchQuery;
      let limit = this.perPage;
      let offset = this.perPage * (this.page - 1);
      this.refreshItems(limit, offset);
    }
  }
}
</script>