<template>
  <div class="main-wrapper">
    <MainHeader title="Detail spoje"/>
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
import LinkaService from "@/_services/linka.service";
import JizdniradyService from "@/_services/jizdnirady.service";
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import DoubleTimeFormGroup from "@/components/DoubleTimeFormGroup.vue";
import MainHeader from "@/components/MainHeader.vue";
import SelectBox from "@/components/SelectBox.vue";

export default {
  name: "SpojDetail",
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
      spoj: {},
      jizdniRad: null,
      prevJizdniRad: null,
      zastavky: [],
      prevZastavky: [],
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
}
</script>