<template>
  <div class="main-wrapper">
    <MainHeader title="Nový spoj"/>
    <form>
      <div class="mb-3">
        <label for="nazev" class="form-label">Název</label>
        <input v-model="spojModel.NAZEV_SPOJ" type="text" class="form-control" id="nazev">
      </div>
      <select-box @input="updateSelectedJROption" label="Vyberte jízdní řád" :options="radyOptions"/>
      <select-box @input="updateSelectedOption" label="Vyberte linku" :options="linkyOptions"/>
      <double-time-form-group v-for="(zastavka, index) in zastavky" :key="index" :item="zastavka"/>
      <button :disabled="isSomewthingWrong" @click="handlePridat" class="btn btn-primary">Přidat</button>
    </form>
  </div>
</template>

<script>
import DoubleTimeFormGroup from "@/components/DoubleTimeFormGroup.vue";
import MainHeader from "@/components/MainHeader.vue";
import LinkaService from "@/_services/linka.service";
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import SelectBox from "@/components/SelectBox.vue";
import LinkyViewService from "@/_services/view_services/linky.view.service";
import SpojService from "@/_services/spoj.service";
import ZaznamjizdnihoraduService from "@/_services/zaznamjizdnihoradu.service";
import JizdniradyService from "@/_services/jizdnirady.service";

export default {
  name: "SpojNovy",
  mixins: [RouterDetailMixin, ObjectUtilityMixin],
  components: {DoubleTimeFormGroup, MainHeader, SelectBox},
  data() {
    return {
      linkyOptions: [],
      radyOptions: [],
      spojModel: {
        NAZEV_SPOJ: '',
        ID_LINKA: null
      },
      jizdniRad: null,
      zastavky: []
    }
  },
  created () {
    LinkaService.getAll().then((resp) => {
      if (resp?.data) {
        this.linkyOptions = this.getItemsWithValueKey(resp.data, 'ID_LINKA', 'NAZEV_LINKA')
      }
    });
    JizdniradyService.getAll().then((resp) => {
      if (resp?.data) {
        this.radyOptions = this.getItemsWithValueKey(resp.data, 'ID_JIZDNI_RAD', 'NAZEV_JIZDNI_RAD')
      }
    });
  },
  methods: {
    updateSelectedOption (e) {
      this.spojModel.ID_LINKA = e.target.value;
      this.fetchZastavky(e.target.value);
    },
    updateSelectedJROption (e) {
      this.jizdniRad = e.target.value;
    },
    fetchZastavky(id_linka) {
      LinkyViewService.getLinkaDetail(id_linka).then((resp) => {
        if (resp?.data) {
          this.zastavky = this.getItemsWithValueKey(
              this.sortObjectsArrayByObjectKey(resp.data, 'PORADI_ZASTAVKY'),
              'ID_ZASTAVKA_LINKA', 'NAZEV_ZASTAVKA');
        }
      }).catch((e) => console.log("Něco se pokazilo"));
    },
    handlePridat () {
      event.preventDefault();
      const dataSpoj = {...this.spojModel};
      const zastavky = [...this.zastavky];
      SpojService.createEntity(dataSpoj).then((resp) => {
        if (resp?.data) {
          zastavky.forEach(zastavka => {
            ZaznamjizdnihoraduService.createEntity({
              ID_SPOJ: resp.data,
              ID_JIZDNI_RAD: this.jizdniRad,
              ID_ZASTAVKA_LINKA: zastavka.value,
              PRAVIDELNY_PRIJEZD: zastavka.PRIJEZD,
              PRAVIDELNY_ODJEZD: zastavka.ODJEZD
            }).catch((resp) => {console.log('Něco se pokazilo')});
          })
        }
      })
    }
  },
  computed: {
    isSomewthingWrong () {
      //TODO validation
      return false
    }
  }
}
</script>