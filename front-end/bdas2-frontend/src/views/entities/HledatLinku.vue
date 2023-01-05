<template>
  <main-header :title="'Vyhledat linku'"/>
  <searchable-select-box :options="linkyOptions" @changeSelection="handleChangedLinka"/>
  <drag-and-drop-list :draggable="false" :items="zastavkyLinky" :deletable="false"/>
</template>

<script>
import LinkaService from "@/_services/linka.service";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import LinkyViewService from "@/_services/view_services/linky.view.service";
import DragAndDropList from "@/components/DragAndDropList.vue";
import SearchableSelectBox from "@/components/SearchableSelectBox.vue";
import MainHeader from "@/components/MainHeader.vue";

export default {
  name: "HledatLinku",
  components: {DragAndDropList, SearchableSelectBox, MainHeader},
  mixins: [ObjectUtilityMixin],
  data() {
    return {
      linkyOptions: [],
      zastavkyLinky: []
    }
  },
  created() {
    LinkaService.getAll().then((resp) => {
      if (resp?.data) {
        this.linkyOptions = this.getItemsWithValueKey(resp.data, 'ID_LINKA', 'NAZEV_LINKA');
      }
    });
  },
  methods: {
    refreshZastavky (id) {
      LinkyViewService.getLinkaDetail(id).then((resp) => {
        if (resp?.data) {
          this.zastavkyLinky = this.getItemsWithValueKey(this.sortObjectsArrayByObjectKey(resp.data, 'PORADI_ZASTAVKY'), 'ID_ZASTAVKA', 'NAZEV_ZASTAVKA');
        }
      })
    },
    handleChangedLinka (value, text) {
      this.refreshZastavky(value);
    }
  }
}
</script>

<style scoped>

</style>