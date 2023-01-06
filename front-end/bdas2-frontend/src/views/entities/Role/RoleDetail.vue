<template>
  <div class="main-wrapper">
    <MainHeader title="Detail role"/>
    <form v-if="entityModel">
      <div class="mb-3">
        <label for="entity" class="form-label">Název</label>
        <input v-model="entityModel.NAZEV_ROLE" type="text" class="form-control" id="entity">
      </div>
      <button :disabled="!isChanged" @click="handleUpravit" class="btn btn-primary">Upravit</button>
    </form>
  </div>
</template>

<script>
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import MainHeader from "@/components/MainHeader.vue";
import RoleService from "@/_services/role.service";

export default {
  name: "RoleDetail",
  mixins: [RouterDetailMixin, ObjectUtilityMixin],
  components: {MainHeader},
  data() {
    return {
      entity: null,
      entityModel: null
    };
  },
  created() {
    RoleService.getById(this.getIdDetail).then((resp) => {
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
      RoleService.updateEntity(this.getIdDetail, this.entityModel, 'ID_ROLE').then((resp) => {
        if (resp.data) {
          this.entity = {...this.entityModel}
        }
      }).catch(() => {
        console.log("Něco se pokazilo");
      })
    }
  }
}
</script>