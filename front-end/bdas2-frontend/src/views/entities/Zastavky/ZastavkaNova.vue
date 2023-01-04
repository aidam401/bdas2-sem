<template>
  <div class="main-wrapper">
    <MainHeader title="Nová zastávka"/>
    <form>
      <div class="mb-3">
        <label for="zastavka" class="form-label">Název</label>
        <input v-model="zastavkaModel.NAZEV_ZASTAVKA" type="text" class="form-control" id="zastavka">
      </div>
      <button :disabled="isSomewthingWrong" @click="handlePridat" class="btn btn-primary">Přidat</button>
    </form>
  </div>
</template>

<script>
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ZastavkaService from "@/_services/zastavka.service";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import MainHeader from "@/components/MainHeader.vue";

export default {
  name: "ZastavkaNova",
  mixins: [RouterDetailMixin, ObjectUtilityMixin],
  components: {MainHeader},
  data() {
    return {
      zastavkaModel: {
        NAZEV_ZASTAVKA: ''
      }
    };
  },
  methods: {
    handlePridat() {
      event.preventDefault()
      ZastavkaService.createEntity(this.zastavkaModel).then((resp) => {
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