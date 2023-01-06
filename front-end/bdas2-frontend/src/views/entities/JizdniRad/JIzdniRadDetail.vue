<template>
  <div class="main-wrapper">
    <MainHeader title="Detail jízdního řádu"/>
    <form>
      <div v-if="error" class="alert alert-danger">{{error}}</div>
      <div class="mb-3">
        <label for="nazev" class="form-label">Název</label>
        <input v-model="entityModel.NAZEV_JIZDNI_RAD" type="text" class="form-control" id="nazev" :class="{ 'is-invalid': submitted && !entityModel.NAZEV_JIZDNI_RAD }">
        <div v-show="submitted && !entityModel.NAZEV_JIZDNI_RAD" class="invalid-feedback">Název je povinný</div>
      </div>
      <div class="mb-3">
        <label for="zacatek" class="form-label">Zahájení platnosti</label>
        <input v-model="entityModel.DATUM_ZAHAJENI_PLATNOSTI" type="date" class="form-control" id="zacatek" :class="{ 'is-invalid': submitted && !entityModel.DATUM_ZAHAJENI_PLATNOSTI }">
        <div v-show="submitted && !entityModel.DATUM_ZAHAJENI_PLATNOSTI" class="invalid-feedback">Vyberte datum</div>
      </div>
      <div class="mb-3">
        <label for="konec" class="form-label">Ukončení platnosti</label>
        <input v-model="entityModel.DATUM_KONCE_PLATNOSTI" type="date" class="form-control" id="konec" :class="{ 'is-invalid': submitted && !entityModel.DATUM_KONCE_PLATNOSTI }">
        <div v-show="submitted && !entityModel.DATUM_KONCE_PLATNOSTI" class="invalid-feedback">Vyberte datum</div>
      </div>
      <button :disabled="!areDataChanged" @click="handleUpravit" class="btn btn-primary">Upravit</button>
    </form>
  </div>
</template>

<script>
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import MainHeader from "@/components/MainHeader.vue";
import JizdniradyService from "@/_services/jizdnirady.service";
import DateTimeMixin from "@/mixins/DateTimeMixin.vue";

export default {
  name: "JIzdniRadDetail",
  mixins: [RouterDetailMixin, ObjectUtilityMixin, DateTimeMixin],
  components: {MainHeader},
  data() {
    return {
      entityModel: {
        NAZEV_JIZDNI_RAD: '',
        DATUM_KONCE_PLATNOSTI: '',
        DATUM_ZAHAJENI_PLATNOSTI: '',
      },
      entity: null,
      submitted: false,
      error: ''
    };
  },
  created() {
    JizdniradyService.getById(this.getIdDetail).then((resp) => {
      if (resp?.data) {
        this.entityModel = {...resp.data[0]};
        this.entityModel.DATUM_KONCE_PLATNOSTI = this.dateOnly(this.entityModel.DATUM_KONCE_PLATNOSTI);
        this.entityModel.DATUM_ZAHAJENI_PLATNOSTI = this.dateOnly(this.entityModel.DATUM_ZAHAJENI_PLATNOSTI);
        this.entity = {...this.entityModel}
      }
    }).catch((e) => console.log("Nepodařilo se načíst Jízdní řád"))
  },
  methods: {
    handleUpravit () {
      event.preventDefault()
      this.submitted = true;
      const data = {...this.entityModel}
      if (this.isOk) {
        JizdniradyService.updateEntity(this.getIdDetail, data, 'ID_JIZDNI_RAD').then((resp) => {
          if (resp?.data) {
            this.entity = {...this.entityModel}
          }
        }).catch((e) => this.error = 'Update se nepovedl. Něco se pokazilo');
      } else {
        this.error = 'Doplňte potřebné údaje';
      }
    }
  },
  computed: {
    areDataChanged () {
      return !this.areObjectsEqual(this.entity, this.entityModel);
    },
    isOk () {
      return !!this.entityModel.NAZEV_JIZDNI_RAD &&
          this.entityModel.DATUM_KONCE_PLATNOSTI &&
          this.entityModel.DATUM_ZAHAJENI_PLATNOSTI;
    }
  }
}
</script>

<style scoped>

</style>