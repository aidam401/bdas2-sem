<template>
  <div class="main-wrapper">
    <MainHeader title="Detail pozice"/>
    <form v-if="entityModel">
      <div class="mb-3">
        <label for="entity" class="form-label">Název</label>
        <input v-model="entityModel.NAZEV_POZICE_ZAMESTNANCE" type="text" class="form-control" id="entity">
      </div>
      <button :disabled="!isChanged" @click="handleUpravit" class="btn btn-primary">Upravit</button>
    </form>
  </div>
</template>

<script>
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import MainHeader from "@/components/MainHeader.vue";
import PozicezamestnanceService from "@/_services/pozicezamestnance.service";

export default {
  name: "PoziceZamestnanceDetail",
  mixins: [RouterDetailMixin, ObjectUtilityMixin],
  components: {MainHeader},
  data() {
    return {
      entity: null,
      entityModel: null
    };
  },
  created() {
    PozicezamestnanceService.getById(this.getIdDetail).then((resp) => {
      if (resp?.data) {
        this.entity = resp.data[0];
        this.entityModel = {...this.entity}
      }
    });
  },
  computed: {
    isChanged() {
      return !this.areObjectsEqual(this.entity, this.entityModel);
    }
  },
  methods: {
    handleUpravit() {
      event.preventDefault()
      PozicezamestnanceService.updateEntity(this.getIdDetail, this.entityModel, 'ID_POZICE_ZAMESTNANCE').then((resp) => {
        if (resp.data) {
          console.log("update proběhl v pořádku!");
          this.$router.go(-1);
        }
      }).catch(() => {
        console.log("Něco se pokazilo");
      })
    }
  }
}
</script>