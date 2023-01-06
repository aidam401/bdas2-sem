<template>
  <div class="main-wrapper">
    <MainHeader title="Nový jízdní řád"/>
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
      <button @click="handlePridat" class="btn btn-primary">Přidat</button>
    </form>
  </div>
</template>

<script>
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import MainHeader from "@/components/MainHeader.vue";
import JizdniradyService from "@/_services/jizdnirady.service";

export default {
  name: "JizdniRadNovy",
  mixins: [RouterDetailMixin, ObjectUtilityMixin],
  components: {MainHeader},
  data() {
    return {
      entityModel: {
        NAZEV_JIZDNI_RAD: '',
        DATUM_KONCE_PLATNOSTI: '',
        DATUM_ZAHAJENI_PLATNOSTI: '',
      },
      submitted: false,
      error: ''
    };
  },
  methods: {
    handlePridat() {
      event.preventDefault()
      this.submitted = true;
      const data = {...this.entityModel};
      if (this.isOk) {
        JizdniradyService.createEntity(data).then((resp) => {
          this.goToTheDetailFromAdd(resp?.data);
        }).catch(() => this.error = 'Operace se nezdařila. Něco se pokazilo');
      } else {
        this.error = 'Doplňte potřebné údaje';
      }
    },
  },
  computed: {
    isOk () {
      return !!this.entityModel.NAZEV_JIZDNI_RAD &&
          this.entityModel.DATUM_KONCE_PLATNOSTI &&
          this.entityModel.DATUM_ZAHAJENI_PLATNOSTI;
    }
  }
}
</script>