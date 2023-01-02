<template>
  <div class="main-wrapper">
    <MainHeader title="Detail zastávky"/>
    <form v-if="zastavkaModel">
      <div class="mb-3">
        <label for="zastavka" class="form-label">Název</label>
        <input v-model="zastavkaModel.NAZEV_ZASTAVKA" type="text" class="form-control" id="zastavka">
      </div>
      <button :disabled="isZastavkaChanged" @click="handleUpravit" class="btn btn-primary">Upravit</button>
    </form>
  </div>
</template>

<script>
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ZastavkaService from "@/_services/zastavka.service";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import MainHeader from "@/components/MainHeader.vue";

export default {
  name: "ZastavkaDetail",
  mixins: [RouterDetailMixin, ObjectUtilityMixin],
  components: {MainHeader},
  data() {
    return {
      zastavka: null,
      zastavkaModel: null
    };
  },
  created() {
    ZastavkaService.getById(this.getIdDetail).then((resp) => {
      if (resp?.data) {
        this.zastavka = resp.data[0];
        this.zastavkaModel = {...this.zastavka}
      }
    });
  },
  computed: {
    isZastavkaChanged() {
      return this.areObjectsEqual(this.zastavka, this.zastavkaModel);
    }
  },
  methods: {
    handleUpravit() {
      event.preventDefault()
      ZastavkaService.updateEntity(this.getIdDetail, this.zastavkaModel, 'ID_ZASTAVKA').then((resp) => {
        if (resp.data) {
          console.log("update proběhl v pořádku!");
        }
      }).catch(() => {
        console.log("Něco se pokazilo");
      })
    }
  }
}
</script>