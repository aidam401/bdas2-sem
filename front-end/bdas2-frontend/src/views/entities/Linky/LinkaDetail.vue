<template>
  <div class="main-wrapper">
    <MainHeader title="Detail linky"/>
    <div class="mb-3">
      <label for="zastavka" class="form-label">Název linky</label>
      <input v-model="linkaModel.NAZEV_LINKA" type="text" class="form-control" id="zastavka">
    </div>
    <SelectBoxAddButton :title="'Přidat zastávku'" :options="optionsZastavky" @addItem="handleAddZastavka"/>
    <DragAndDropList :items="zastavkyLinky" @deleteItem="handleDeleteZastavka"/>
    <button :disabled="areDataSame" @click="handlePridat" class="btn btn-primary">Upravit</button>
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
  name: "LinkaDetail",
  components: {SelectBoxAddButton, MainHeader,DragAndDropList},
  mixins: [ObjectUtilityMixin, RouterDetailMixin],
  data () {
    return {
      linkaModel: {
        NAZEV_LINKA: ''
      },
      linka: null,
      optionsZastavky: [],
      zastavkyLinky: [],
      previousZastavky: []
    }
  },
  created () {
    this.init();
  },
  methods: {
    init () {
      LinkaService.getById(this.getIdDetail).then((resp) => {
        if (resp?.data) {
          this.linkaModel = resp.data[0];
          this.linka = {...this.linkaModel};
        }
      });
      ZastavkaService.getAll().then((resp) => {
        if (resp?.data) {
          this.optionsZastavky = this.getItemsWithValueKey(resp.data, 'ID_ZASTAVKA', 'NAZEV_ZASTAVKA');
          this.refreshZastavky();
        }
      });
      LinkyViewService.getLinkaDetail(this.getIdDetail).then((resp) => {
        if (resp?.data) {
          this.zastavkyLinky = this.getItemsWithValueKey(
              this.sortObjectsArrayByObjectKey(resp.data, 'PORADI_ZASTAVKY'),
              'ID_ZASTAVKA', 'NAZEV_ZASTAVKA');
          this.previousZastavky = [...this.zastavkyLinky];
          this.refreshZastavky();
        }
      });
    },
    refreshZastavky () {
      this.optionsZastavky = this.difference(this.optionsZastavky, this.zastavkyLinky);
    },
    handleAddZastavka (item) {
      this.zastavkyLinky.push(item);
      this.refreshZastavky();
    },
    handlePridat () {
      LinkaService.updateEntity(this.getIdDetail, this.linkaModel, 'ID_LINKA').then((resp) => {
        if (resp.data) {
          this.optionsZastavky.forEach((zastavka) => {
            if (zastavka.ID_ZASTAVKA_LINKA) {
              ZastavkalinkaService.deleteEntity(zastavka.ID_ZASTAVKA_LINKA);
            }
          });
          this.zastavkyLinky.forEach((zastavka, index) => {
            if (zastavka.ID_ZASTAVKA_LINKA) {
              ZastavkalinkaService.updateEntity(zastavka.ID_ZASTAVKA_LINKA, {
                'ID_LINKA' : this.getIdDetail,
                'ID_ZASTAVKA' : zastavka.ID_ZASTAVKA,
                'PORADI_ZASTAVKY' : index + 1
              });
            } else {
              ZastavkalinkaService.createEntity({
                'ID_LINKA' : this.getIdDetail,
                'ID_ZASTAVKA' : zastavka.ID_ZASTAVKA,
                'PORADI_ZASTAVKY' : index + 1
              });
            }
          })
        }
      }).then((e) => {
        setTimeout(() => { this.init(); }, 1000);
      }).catch((e) => console.log('Nastala chyba při vytváření záznamu LINKA', e));
    },
    handleDeleteZastavka (item) {
      this.optionsZastavky.push(item);
    }
  },
  computed: {
    areDataSame () {
      return this.areObjectsEqual(this.linkaModel, this.linka) && this.areObjectsArrayEqual(this.zastavkyLinky, this.previousZastavky);
    }
  }
}
</script>