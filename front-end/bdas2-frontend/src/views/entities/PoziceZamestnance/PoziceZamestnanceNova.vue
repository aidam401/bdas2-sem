<template>
  <div class="main-wrapper">
    <MainHeader title="Nová pozice"/>
    <form>
      <div class="mb-3">
        <label for="entity" class="form-label">Název</label>
        <input v-model="entity.NAZEV_POZICE_ZAMESTNANCE" type="text" class="form-control" id="entity">
      </div>
      <button :disabled="isSomewthingWrong" @click="handlePridat" class="btn btn-primary">Přidat</button>
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
      }
    };
  },
  methods: {
    handlePridat() {
      event.preventDefault()
      PozicezamestnanceService.createEntity(this.entity).then((resp) => {
        this.goToTheDetailFromAdd(resp.data);
      })
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