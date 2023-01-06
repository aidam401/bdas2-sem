<template>
  <div class="main-wrapper">
    <MainHeader title="Nová pozice"/>
    <form>
      <div v-if="error" class="alert alert-danger">{{error}}</div>
      <div class="mb-3">
        <label for="entity" class="form-label">Název</label>
        <input v-model="entity.NAZEV_POZICE_ZAMESTNANCE" type="text" class="form-control" id="entity" :class="{ 'is-invalid': submitted && !entity.NAZEV_POZICE_ZAMESTNANCE }">
        <div v-show="submitted && !entity.NAZEV_POZICE_ZAMESTNANCE" class="invalid-feedback">Název je povinný</div>
      </div>
      <button @click="handlePridat" class="btn btn-primary">Přidat</button>
    </form>
  </div>
</template>

<script>
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import MainHeader from "@/components/MainHeader.vue";
import PozicezamestnanceService from "@/_services/pozicezamestnance.service";

export default {
  name: "PoziceZamestnanceNova",
  mixins: [RouterDetailMixin, ObjectUtilityMixin],
  components: {MainHeader},
  data() {
    return {
      entity: {
        NAZEV_POZICE_ZAMESTNANCE: ''
      },
      submitted: false,
      error: ''
    };
  },
  methods: {
    handlePridat() {
      event.preventDefault()
      this.submitted = true;
      if (this.isOk) {
        PozicezamestnanceService.createEntity(this.entity).then((resp) => {
          this.goToTheDetailFromAdd(resp.data);
        }).catch((e) => this.error = 'Operace se nepodařila. Něco se pokazilo');
      } else {
        this.error = 'Doplňte potřebné údaje';
      }
    }
  },
  computed: {
    isOk() {
      return !!this.entity.NAZEV_POZICE_ZAMESTNANCE;
    }
  }
}
</script>