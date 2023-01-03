<template>
  <div class="main-wrapper">
    <MainHeader title="Nová linka"/>
    <div class="mb-3">
      <label for="zastavka" class="form-label">Název linky</label>
      <input v-model="linkaModel.NAZEV_LINKA" type="text" class="form-control" id="zastavka">
    </div>
    <SelectBoxAddButton :title="'Přidat zastávku'" :options="optionsZastavky" @addItem="handleAddZastavka"/>
    <DragAndDropList :items="zastavkyLinky"/>
    <button :disabled="false" @click="handlePridat" class="btn btn-primary">Přidat</button>
  </div>
</template>

<script>
import DragAndDropList from "@/components/DragAndDropList.vue";
import MainHeader from "@/components/MainHeader.vue";
import SelectBoxAddButton from "@/components/SelectBoxAddButton.vue";
import ZastavkaService from "@/_services/zastavka.service";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import LinkyViewService from "@/_services/view_services/linky.view.service";
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ZastavkalinkaService from "@/_services/zastavkalinka.service";
import LinkaService from "@/_services/linka.service";
export default {
  name: "LinkaNova",
  components: {SelectBoxAddButton, MainHeader,DragAndDropList},
  mixins: [ObjectUtilityMixin, RouterDetailMixin],
  data () {
    return {
      linkaModel: {
        NAZEV_LINKA: ''
      },
      optionsZastavky: [],
      zastavkyLinky: [],
    }
  },
  created () {
    ZastavkaService.getAll().then((resp) => {
      if (resp?.data) {
        this.optionsZastavky = this.getItemsWithValueKey(resp.data, 'ID_ZASTAVKA', 'NAZEV_ZASTAVKA');
        this.refreshZastavky();
      }
    });
    /*
    LinkyViewService.getLinkaDetail(this.getIdDetail).then((resp) => {
      if (resp?.data) {
        this.zastavkyLinky = this.getItemsWithValueKey(resp.data, 'ID_ZASTAVKA', 'NAZEV_ZASTAVKA');
        this.refreshZastavky();
      }
    });*/
  },
  methods: {
    refreshZastavky () {
      this.optionsZastavky = this.difference(this.optionsZastavky, this.zastavkyLinky);
    },
    handleAddZastavka (item) {
      this.zastavkyLinky.push(item);
      this.refreshZastavky();
    },
    handlePridat () {
      var profileId;
      LinkaService.createEntity(this.linkaModel).then((resp) => {
        profileId = resp.data;
        if (resp.data) {
          this.zastavkyLinky?.forEach((zastavka, index) => {
            const poradi = index + 1;
            console.log(poradi);
            ZastavkalinkaService.createEntity({
              'ID_LINKA' : resp.data,
              'ID_ZASTAVKA' : zastavka.value,
              'PORADI_ZASTAVKY' : poradi
            }).catch((e) => console.log('Nastala chyba při vytváření záznamu ZASTAVKA_LINKA'))
          });
        }
      }).catch((e) => console.log('Nastala chyba při vytváření záznamu LINKA'));

    }
  },
  computed: {

  }
}
</script>