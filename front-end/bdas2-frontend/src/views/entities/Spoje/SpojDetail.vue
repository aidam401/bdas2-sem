<template>
  <div class="main-wrapper">
    <MainHeader title="Detail spoje"/>
    <form>
      <div class="mb-3">
        <label for="nazev" class="form-label">Název</label>
        <input v-model="spojModel.NAZEV_SPOJ" type="text" class="form-control" id="nazev">
      </div>
      <select-box @input="updateSelectedJROption" label="Vyberte jízdní řád" :options="radyOptions" :selected="spojModel.ID_JIZDNI_RAD"/>
      <select-box @input="updateSelectedOption" label="Vyberte linku" :options="linkyOptions" :selected="spojModel.ID_LINKA"/>
      <double-time-form-group v-for="(zastavka, index) in zastavky" :key="index" :item="zastavka"/>
      <button :disabled="areDataSame" @click="handleUpravit" class="btn btn-primary">Upravit</button>
    </form>
  </div>
</template>
<script>
import LinkaService from "@/_services/linka.service";
import JizdniradyService from "@/_services/jizdnirady.service";
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import DoubleTimeFormGroup from "@/components/DoubleTimeFormGroup.vue";
import MainHeader from "@/components/MainHeader.vue";
import SelectBox from "@/components/SelectBox.vue";
import SpojService from "@/_services/spoj.service";
import LinkyViewService from "@/_services/view_services/linky.view.service";
import ZaznamjizdnihoraduService from "@/_services/zaznamjizdnihoradu.service";
import DateTimeMixin from "@/mixins/DateTimeMixin.vue";

export default {
  name: "SpojDetail",
  mixins: [RouterDetailMixin, ObjectUtilityMixin, DateTimeMixin],
  components: {DoubleTimeFormGroup, MainHeader, SelectBox},
  data() {
    return {
      linkyOptions: [],
      radyOptions: [],
      spojModel: {
        NAZEV_SPOJ: '',
        ID_LINKA: null,
        ID_JIZDNI_RAD: null
      },
      spoj: {},
      jizdniRad: null,
      prevJizdniRad: null,
      zastavky: [],
      prevZastavky: [],
    }
  },
  created () {
    this.init();
  },
  methods: {
    init () {
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
      SpojService.getSpoj(this.getIdDetail).then((resp) => {
        if (resp?.data) {
          this.spoj = {...resp.data[0]};
          this.spojModel = {...this.spoj};
        }
      });
      this.initZastavky();
    },
    updateSelectedOption (e) {
      this.spojModel.ID_LINKA = e.target.value;
      if (this.spojModel.ID_LINKA === this.spoj.ID_LINKA) {
        this.initZastavky();
      } else {
        this.fetchZastavky(e.target.value);
      }
    },
    updateSelectedJROption (e) {
      this.spojModel.ID_JIZDNI_RAD = e.target.value;
    },
    initZastavky() {
      SpojService.getZastavkySpoje(this.getIdDetail).then((resp) => {
        if (resp?.data) {
          this.zastavky = JSON.parse(JSON.stringify(resp.data));
          this.zastavky.forEach((zastavka) => {
            zastavka.PRAVIDELNY_PRIJEZD = this.timeOnly(this.extract(zastavka.PRAVIDELNY_PRIJEZD, 1));
            zastavka.PRAVIDELNY_ODJEZD = this.timeOnly(this.extract(zastavka.PRAVIDELNY_ODJEZD, 1));
          })
          this.prevZastavky = JSON.parse(JSON.stringify(this.zastavky));
        }
      }).catch((e) => console.log("Něco se pokazilo"));
    },
    fetchZastavky(id_linka) {
      LinkyViewService.getLinkaDetail(id_linka).then((resp) => {
        if (resp?.data) {
          this.zastavky = this.sortObjectsArrayByObjectKey(resp.data, 'PORADI_ZASTAVKY');
        }
      }).catch((e) => console.log("Něco se pokazilo"));
    },
    handleUpravit () {
      event.preventDefault();
      if (!this.areObjectsEqual(this.spoj, this.spojModel)) {
        SpojService.updateEntity(this.getIdDetail, {
          NAZEV_SPOJ: this.spojModel.NAZEV_SPOJ,
          ID_LINKA: this.spojModel.ID_LINKA
        });
      }
      if (this.spoj.ID_LINKA === this.spojModel.ID_LINKA) {
        this.prevZastavky.forEach((zaznam) => {
          ZaznamjizdnihoraduService.deleteEntity(zaznam.ID_ZAZNAM_JIZDNIHO_RADU).catch((e) => {console.log("něco se pokazilo")})
        });
        this.zastavky.forEach((zaznam) => {
          ZaznamjizdnihoraduService.createEntity({
            ID_SPOJ: this.getIdDetail,
            ID_JIZDNI_RAD: this.spojModel.ID_JIZDNI_RAD,
            ID_ZASTAVKA_LINKA: zaznam.ID_ZASTAVKA_LINKA,
            PRAVIDELNY_PRIJEZD: zaznam.PRAVIDELNY_PRIJEZD,
            PRAVIDELNY_ODJEZD: zaznam.PRAVIDELNY_ODJEZD
          });
        })
      } else if (this.spoj.ID_JIZDNI_RAD !== this.spojModel.ID_JIZDNI_RAD
          || !this.areObjectsArrayEqual(this.zastavky, this.prevZastavky)) {
        this.zastavky.forEach((zaznam) => {
          ZaznamjizdnihoraduService.updateEntity(zaznam.ID_ZAZNAM_JIZDNIHO_RADU, {
            ID_SPOJ: this.getIdDetail,
            ID_JIZDNI_RAD: this.spojModel.ID_JIZDNI_RAD,
            ID_ZASTAVKA_LINKA: zaznam.ID_ZASTAVKA_LINKA,
            PRAVIDELNY_PRIJEZD: zaznam.PRAVIDELNY_PRIJEZD,
            PRAVIDELNY_ODJEZD: zaznam.PRAVIDELNY_ODJEZD
          }, 'ID_ZAZNAM_JIZDNIHO_RADU')
        });
        this.init();
      }
    }
  },
  computed: {
    areDataSame () {
      console.log("are data same?", this.prevZastavky, this.zastavky);
      return this.areObjectsEqual(this.spoj, this.spojModel) && this.areProxyObjectsArrayEqual(this.prevZastavky, this.zastavky);
    }
  }
}
</script>