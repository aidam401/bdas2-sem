<template>
  <div class="main-wrapper">
    <MainHeader title="Detail jízdního řádu"/>
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
      <button :disabled="!areDataChanged" @click="handleUpravit" class="btn btn-primary">Upravit</button>
    </form>
  </div>
</template>

<script>
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import MainHeader from "@/components/MainHeader.vue";
import JizdniradyService from "@/_services/jizdnirady.service";

export default {
  name: "JIzdniRadDetail",
  mixins: [RouterDetailMixin, ObjectUtilityMixin],
  components: {MainHeader},
  data() {
    return {
      entityModel: {
        NAZEV_JIZDNI_RAD: '',
        DATUM_KONCE_PLATNOSTI: '',
        DATUM_ZAHAJENI_PLATNOSTI: '',
      },
      entity: null,
    };
  },
  created() {
    JizdniradyService.getById(this.getIdDetail).then((resp) => {
      if (resp?.data) {
        this.entityModel = resp.data[0];
        this.entity = {...this.entityModel}
      }
    }).catch((e) => console.log("Nepodařilo se načíst Jízdní řád"))
  },
  methods: {
    handleUpravit () {
      event.preventDefault()
      const data = {...this.entityModel}
      JizdniradyService.updateEntity(this.getIdDetail, data, 'ID_JIZDNI_RAD').then((resp) => {
        if (resp?.data) {
          console.log("Update ok");
        }
      }).catch((e) => console.log("Update se pokazil", e));
    }
  },
  computed: {
    areDataChanged () {
      return !this.areObjectsEqual(this.entity, this.entityModel);
    }
  }
}
</script>

<style scoped>

</style>