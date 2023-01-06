<template>
  <div class="main-wrapper">
    <MainHeader title="Nový spoj"/>
    <form>
      <div v-if="error" class="alert alert-danger">{{error}}</div>
      <div class="mb-3">
        <label for="nazev" class="form-label">Název</label>
        <input v-model="spojModel.NAZEV_SPOJ" type="text" class="form-control" id="nazev" :class="{ 'is-invalid': submitted && !spojModel.NAZEV_SPOJ }">
        <div v-show="submitted && !spojModel.NAZEV_SPOJ" class="invalid-feedback">Název je povinný</div>
      </div>
      <select-box @input="updateSelectedJROption" label="Vyberte jízdní řád" :options="radyOptions" :class="{ 'is-invalid': submitted && !jizdniRad }" />
      <div v-show="submitted && !jizdniRad" class="invalid-feedback">Zvolte jízdní řád</div>
      <select-box @input="updateSelectedOption" label="Vyberte linku" :options="linkyOptions" :class="{ 'is-invalid': submitted && !spojModel.ID_LINKA }" />
      <div v-show="submitted && !spojModel.ID_LINKA" class="invalid-feedback">Zvolte linku, kterou bude spoj popisovat</div>
      <double-time-form-group v-for="(zastavka, index) in zastavky" :key="index" :item="zastavka" :class="{ 'is-invalid': submitted && !doesEveryStopHaveTimes }" />
      <div v-show="submitted && !doesEveryStopHaveTimes" class="invalid-feedback">Nastavte časy u všech zastávek</div>
      <button @click="handlePridat" class="btn btn-primary">Přidat</button>
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
      zastavky: [],
      submitted: false,
      error: ''
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
          this.zastavky = this.sortObjectsArrayByObjectKey(resp.data, 'PORADI_ZASTAVKY');
        }
      }).catch((e) => console.log("Něco se pokazilo"));
    },
    handlePridat () {
      event.preventDefault();
      this.submitted = true;
      if (this.isOK) {
        const dataSpoj = {...this.spojModel};
        const zastavky = [...this.zastavky];
        SpojService.createEntity(dataSpoj).then((resp) => {
          if (resp?.data) {
            zastavky.forEach(zastavka => {
              ZaznamjizdnihoraduService.createEntity({
                ID_SPOJ: resp.data,
                ID_JIZDNI_RAD: this.jizdniRad,
                ID_ZASTAVKA_LINKA: zastavka.ID_ZASTAVKA_LINKA,
                PRAVIDELNY_PRIJEZD: zastavka.PRAVIDELNY_PRIJEZD,
                PRAVIDELNY_ODJEZD: zastavka.PRAVIDELNY_ODJEZD
              }).catch(() => this.error = 'Operace se nezdařila. Něco se pokazilo');
            })
          }
          this.goToTheDetailFromAdd(resp.data);
        })
      } else {
        this.error = 'Doplňte potřebné údaje';
      }
    }
  },
  computed: {
    isOK () {
      return !!this.spojModel.NAZEV_SPOJ && this.spojModel.ID_LINKA && this.jizdniRad && this.doesEveryStopHaveTimes;
    },
    doesEveryStopHaveTimes () {
      for (const key in this.zastavky) {
        if (!this.zastavky[key].PRAVIDELNY_PRIJEZD || !this.zastavky[key].PRAVIDELNY_ODJEZD) {
          return false;
        }
      }
      return true;
    },
  },
}
</script>