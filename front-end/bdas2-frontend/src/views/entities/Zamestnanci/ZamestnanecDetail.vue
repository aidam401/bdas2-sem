<template>
  <div class="main-wrapper">
    <MainHeader title="Detail zaměstnance"/>
    <form>
      <div v-if="error" class="alert alert-danger">{{error}}</div>
      <div class="mb-3">
        <label for="jmeno" class="form-label">Jméno</label>
        <input v-model="entityModel.JMENO" type="text" class="form-control" id="jmeno" :class="{ 'is-invalid': submitted && !entityModel.JMENO }">
        <div v-show="submitted && !entityModel.JMENO" class="invalid-feedback">Jméno je povinné</div>
      </div>
      <div class="mb-3">
        <label for="prijmeni" class="form-label">Příjmení</label>
        <input v-model="entityModel.PRIJMENI" type="text" class="form-control" id="prijmeni" :class="{ 'is-invalid': submitted && !entityModel.PRIJMENI }">
        <div v-show="submitted && !entityModel.PRIJMENI" class="invalid-feedback">Příjmení je povinné</div>
      </div>
      <div class="mb-3">
        <label for="datum" class="form-label">Narozen</label>
        <input v-model="entityModel.DATUM_NAROZENI" type="date" class="form-control" id="datum" :class="{ 'is-invalid': submitted && !entityModel.DATUM_NAROZENI }">
        <div v-show="submitted && !entityModel.DATUM_NAROZENI" class="invalid-feedback">Zadejte datum narození</div>
      </div>
      <select-box @input="updateSelectedOption" label="Pozice" :options="poziceOptions" :selected="entityModel.ID_POZICE_ZAMESTNANCE" :class="{ 'is-invalid': submitted && !entityModel.ID_POZICE_ZAMESTNANCE }" />
      <div v-show="submitted && !entityModel.ID_POZICE_ZAMESTNANCE" class="invalid-feedback">Zvolte pozici</div>
      <button :disabled="areDataSame" @click="handleUpravit" class="btn btn-primary">Upravit</button>
    </form>
  </div>
</template>

<script>
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import SelectBox from "@/components/SelectBox.vue";
import MainHeader from "@/components/MainHeader.vue";
import PozicezamestnanceService from "@/_services/pozicezamestnance.service";
import ZamestnanecService from "@/_services/zamestnanec.service";
import DateTimeMixin from "@/mixins/DateTimeMixin.vue";

export default {
  name: "ZamestnanecDetail",
  mixins: [RouterDetailMixin, ObjectUtilityMixin, DateTimeMixin],
  components: {SelectBox, MainHeader},
  data() {
    return {
      entityModel: {
        JMENO: '',
        PRIJMENI: '',
        DATUM_NAROZENI: '',
        ID_POZICE_ZAMESTNANCE: 0,
      },
      entity: null,
      poziceOptions: [],
      submitted: false,
      error: ''
    };
  },
  created() {
    PozicezamestnanceService.getAll().then((resp) => {
      if (resp?.data) {
        this.poziceOptions = this.getItemsWithValueKey(resp.data, 'ID_POZICE_ZAMESTNANCE', 'NAZEV_POZICE_ZAMESTNANCE');
      }
    });
    ZamestnanecService.getById(this.getIdDetail).then((resp) => {
      if (resp?.data) {
        this.entityModel = resp.data[0];
        this.entityModel.DATUM_NAROZENI = this.dateOnly(this.entityModel.DATUM_NAROZENI);
        this.entity = {...this.entityModel};
      }
    });
  },
  methods: {
    handleUpravit () {
      event.preventDefault();
      this.submitted = true;
      if (this.isOk) {
        const dataEntity = {...this.entityModel};
        ZamestnanecService.updateEntity(this.getIdDetail, dataEntity, 'ID_ZAMESTNANEC').then((resp) => {
          this.entity = {...this.entityModel}
        }).catch(() => this.error = 'Update se nezdařil. Něco se pokazilo');
      }else {
        this.error = 'Doplňte potřebné údaje';
      }
    },
    updateSelectedOption (e) {
      this.entityModel.ID_POZICE_ZAMESTNANCE = e.target.value;
    }
  },
  computed: {
    areDataSame () {
      return this.areObjectsEqual(this.entity, this.entityModel);
    },
    isOk () {
      return !!this.entityModel.JMENO &&
          !!this.entityModel.PRIJMENI &&
          this.entityModel.DATUM_NAROZENI &&
          this.entityModel.ID_POZICE_ZAMESTNANCE;
    }
  }
}
</script>