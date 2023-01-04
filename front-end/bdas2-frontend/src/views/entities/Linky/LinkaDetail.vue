<template>
  <div class="main-wrapper">
    <MainHeader title="Detail linky"/>
    <div class="mb-3">
      <label for="zastavka" class="form-label">Název linky</label>
      <input v-model="linkaModel.NAZEV_LINKA" type="text" class="form-control" id="zastavka">
    </div>
    <SelectBoxAddButton :title="'Přidat zastávku'" :options="optionsZastavky" @addItem="handleAddZastavka"/>
    <DragAndDropList :items="zastavkyLinky" @deleteItem="handleDeleteZastavka"/>
    <button :disabled="areDataSame" @click="handlePridat" class="btn btn-primary">Přidat</button>
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
      linkaModel: null,
      linka: null,
      optionsZastavky: [],
      zastavkyLinky: [],
      previousZastavky: [],
      deletedZastavky: [],
      addedZastavky: [],
      zaznamyZastavkaLinka: []
    }
  },
  created () {
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
        this.zaznamyZastavkaLinka = resp.data;
        this.zastavkyLinky = this.getItemsWithValueKey(
            this.sortObjectsArrayByObjectKey(resp.data, 'PORADI_ZASTAVKY'),
            'ID_ZASTAVKA', 'NAZEV_ZASTAVKA');
        this.previousZastavky = [...this.zastavkyLinky];
        this.refreshZastavky();
      }
    });
  },
  methods: {
    refreshZastavky () {
      this.optionsZastavky = this.difference(this.optionsZastavky, this.zastavkyLinky);
    },
    handleAddZastavka (item) {
      this.zastavkyLinky.push(item);
      if (!this.previousZastavky.includes(item)) {
        if (this.deletedZastavky.includes(item)) {
          this.deletedZastavky.filter(ob => !this.areObjectsEqual(ob, item));
        }
        this.addedZastavky.push(item);
      }
      this.refreshZastavky();
    },
    handlePridat () {
      var profileId;
      LinkaService.updateEntity(this.linkaModel).then((resp) => {
        profileId = resp.data;
        if (resp.data) {
          this.deletedZastavky?.forEach((zastavka) => {
            this.zaznamyZastavkaLinka?.forEach((zaznam) => {
              if (zaznam.ID_ZASTAVKA === zastavka.value) {
                ZastavkalinkaService.deleteEntity(zaznam.ID_ZASTAVKA_LINKA).catch((e) => { console.log('Nepodařilo se smazat záznam', zaznam) });
              }
            });
          });

          this.difference(this.zastavkyLinky, this.addedZastavky)?.forEach((zastavka, index) => {
            const poradi = index + 1;
            ZastavkalinkaService.updateEntity({
              'ID_LINKA' : resp.data,
              'ID_ZASTAVKA' : zastavka.value,
              'PORADI_ZASTAVKY' : poradi
            }).catch((e) => console.log('Nastala chyba při vytváření záznamu ZASTAVKA_LINKA'))
          });

          this.addedZastavky?.forEach((zastavky, index) => {
            const poradi = index + 1;
            ZastavkalinkaService.createEntity({
              'ID_LINKA' : resp.data,
              'ID_ZASTAVKA' : zastavka.value,
              'PORADI_ZASTAVKY' : poradi
            }).catch((e) => console.log('Nastala chyba při vytváření záznamu ZASTAVKA_LINKA'))
          });
        }
      }).catch((e) => console.log('Nastala chyba při vytváření záznamu LINKA'));
    },
    handleDeleteZastavka (item) {
      if (this.previousZastavky.includes(item)) {
        if (this.addedZastavky.includes(item)) {
          this.addedZastavky.filter(ob => !this.areObjectsEqual(ob, item));
        }
        this.deletedZastavky.push(item);
      }
      this.optionsZastavky.push(item);
    }
  },
  computed: {
    areDataSame () {
      console.log(this.linka, this.linkaModel)
      console.log(this.areObjectsEqual(this.linkaModel, this.linka), this.areObjectsArrayEqual(this.zastavkyLinky, this.previousZastavky));
      return this.areObjectsEqual(this.linkaModel, this.linka) && this.areObjectsArrayEqual(this.zastavkyLinky, this.previousZastavky);
    }
  }
}
</script>