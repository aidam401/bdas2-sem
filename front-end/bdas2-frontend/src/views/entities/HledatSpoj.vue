<template>
  <main-header :title="'Vyhledat spoj'"/>
  <searchable-select-box :options="spojeOptions" @changeSelection="handleChangedSpoj"/>
  <casy-zastavek :items="zastavkySpoje"/>
</template>

<script>
import MainHeader from "@/components/MainHeader.vue";
import SpojService from "@/_services/spoj.service";
import SearchableSelectBox from "@/components/SearchableSelectBox.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import CasyZastavek from "@/components/CasyZastavek.vue";
export default {
  name: "HledatSpoj",
  components: {CasyZastavek, SearchableSelectBox, MainHeader},
  mixins: [ObjectUtilityMixin],
  data() {
    return {
      spojeOptions: [],
      zastavkySpoje: []
    }
  },
  created() {
    SpojService.getAll().then((resp) => {
      if (resp?.data) {
        this.spojeOptions = this.getItemsWithValueKey(resp.data, 'ID_SPOJ', 'NAZEV_SPOJ')
      }
    })
  },
  methods: {
    refreshZastavky (id) {
      SpojService.getZastavkySpoje(id).then((resp) => {
        if (resp?.data) {
          this.zastavkySpoje = resp.data;
        }
      })
    },
    handleChangedSpoj (value, text) {
      this.refreshZastavky(value);
    }
  }
}
</script>

<style scoped>

</style>