<template>
  <div class="main-wrapper">
    <MainHeader title="Nová linka"/>
    <div v-if="error" class="alert alert-danger">{{error}}</div>
    <div class="mb-3">
      <label for="zastavka" class="form-label">Název linky</label>
      <input v-model="linkaModel.NAZEV_LINKA" type="text" class="form-control" id="zastavka" :class="{ 'is-invalid': submitted && !this.linkaModel.NAZEV_LINKA }">
      <div v-show="submitted && !linkaModel.NAZEV_LINKA" class="invalid-feedback">Název je povinný</div>
    </div>
    <SelectBoxAddButton :title="'Přidat zastávku'" :options="optionsZastavky" @addItem="handleAddZastavka" :class="{ 'is-invalid': submitted && this.zastavkyLinky.length < 2 }"/>
    <div v-show="submitted && this.zastavkyLinky.length < 2 " class="invalid-feedback">Přidejte alespoň dvě zastávky do linky</div>
    <DragAndDropList :items="zastavkyLinky"/>
    <button @click="handlePridat" class="btn btn-primary">Přidat</button>
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
      submitted: false,
      error: ''
    }
  },
  created () {
    ZastavkaService.getAll().then((resp) => {
      if (resp?.data) {
        this.optionsZastavky = this.getItemsWithValueKey(resp.data, 'ID_ZASTAVKA', 'NAZEV_ZASTAVKA');
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
      this.refreshZastavky();
    },
    handlePridat () {
      this.submitted = true;
      if (this.isOk) {
        LinkaService.createEntity(this.linkaModel).then((resp) => {
          if (resp.data) {
            this.zastavkyLinky?.forEach((zastavka, index) => {
              const poradi = index + 1;
              ZastavkalinkaService.createEntity({
                'ID_LINKA' : resp.data,
                'ID_ZASTAVKA' : zastavka.value,
                'PORADI_ZASTAVKY' : poradi
              }).catch((e) => this.error = 'Nastala chyba při vytváření záznamu ZASTAVKA_LINKA')
            });
          }
          this.goToTheDetailFromAdd(resp.data);
        }).catch((e) => this.error = 'Nastala chyba při vytváření záznamu LINKA');
      } else {
        this.error = 'Doplňte potřebné údaje';
      }
    }
  },
  computed: {
    isOk () {
      return !!this.linkaModel.NAZEV_LINKA && this.zastavkyLinky.length >= 2;
    }
  }
}
</script>