<template>
  <div class="main-wrapper">
    <MainHeader title="Nový zaměstnanec"/>
    <form>
      <div class="mb-3">
        <label for="jmeno" class="form-label">Jméno</label>
        <input v-model="entityModel.JMENO" type="text" class="form-control" id="jmeno">
      </div>
      <div class="mb-3">
        <label for="prijmeni" class="form-label">Příjmení</label>
        <input v-model="entityModel.PRIJMENI" type="text" class="form-control" id="prijmeni">
      </div>
      <div class="mb-3">
        <label for="datum" class="form-label">Narozen</label>
        <input v-model="entityModel.DATUM_NAROZENI" type="date" class="form-control" id="datum">
      </div>
      <select-box @input="updateSelectedOption" label="Pozice" :options="poziceOptions" />
      <button :disabled="isSomewthingWrong" @click="handlePridat" class="btn btn-primary">Přidat</button>
    </form>
  </div>
</template>

<script>
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import MainHeader from "@/components/MainHeader.vue";
import SelectBox from "@/components/SelectBox.vue";
import PozicezamestnanceService from "@/_services/pozicezamestnance.service";
import ZamestnanecService from "@/_services/zamestnanec.service";

export default {
  name: "ZamestnanecNovy",
  mixins: [RouterDetailMixin, ObjectUtilityMixin],
  components: {SelectBox, MainHeader},
  data() {
    return {
      entityModel: {
        JMENO: '',
        PRIJMENI: '',
        DATUM_NAROZENI: '',
        ID_POZICE: 0,
      },
      poziceOptions: [],
    };
  },
  created() {
    PozicezamestnanceService.getAll().then((resp) => {
      if (resp?.data) {
        this.poziceOptions = this.getItemsWithValueKey(resp.data, 'ID_POZICE_ZAMESTNANCE', 'NAZEV_POZICE_ZAMESTNANCE');
      }
    })
  },
  methods: {
    handlePridat() {
      event.preventDefault();
      const data = {...this.entityModel};
      ZamestnanecService.createEntity(data).then((resp) => {
        this.goToTheDetailFromAdd(resp?.data);
      });
    },
    updateSelectedOption (e) {
      this.entityModel.ID_POZICE = e.target.value;
    }
  },
  computed: {
    isSomewthingWrong() {
      // TODO
      return false;
    }
  }
}
</script>