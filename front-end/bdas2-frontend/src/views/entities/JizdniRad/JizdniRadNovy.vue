<template>
  <div class="main-wrapper">
    <MainHeader title="Nový jízdní řád"/>
    <form>
      <div class="mb-3">
        <label for="nazev" class="form-label">Název</label>
        <input v-model="entityModel.NAZEV_JIZDNI_RAD" type="text" class="form-control" id="nazev">
      </div>
      <div class="mb-3">
        <label for="zacatek" class="form-label">Zahájení platnosti</label>
        <input v-model="entityModel.DATUM_ZAHAJENI_PLATNOSTI" type="date" class="form-control" id="zacatek">
      </div>
      <div class="mb-3">
        <label for="konec" class="form-label">Ukončení platnosti</label>
        <input v-model="entityModel.DATUM_KONCE_PLATNOSTI" type="date" class="form-control" id="konec">
      </div>
      <button :disabled="isSomewthingWrong" @click="handlePridat" class="btn btn-primary">Přidat</button>
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
      }
    };
  },
  methods: {
    handlePridat() {
      event.preventDefault();
      const data = {...this.entityModel};
      JizdniradyService.createEntity(data).then((resp) => {
        this.goToTheDetailFromAdd(resp?.data);
      });
    },
  },
  computed: {
    isSomewthingWrong () {
      // TODO validation
      return false;
    }
  }
}
</script>