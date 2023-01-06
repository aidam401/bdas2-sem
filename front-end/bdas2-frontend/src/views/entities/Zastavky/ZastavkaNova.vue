<template>
  <div class="main-wrapper">
    <MainHeader title="Nová zastávka"/>
    <form>
      <div v-if="error" class="alert alert-danger">{{error}}</div>
      <div class="mb-3">
        <label for="zastavka" class="form-label">Název</label>
        <input v-model="zastavkaModel.NAZEV_ZASTAVKA" type="text" class="form-control" id="zastavka" :class="{ 'is-invalid': submitted && !zastavkaModel.NAZEV_ZASTAVKA }">
        <div v-show="submitted && !zastavkaModel.NAZEV_ZASTAVKA" class="invalid-feedback">Název je povinný</div>
      </div>
      <button @click="handlePridat" class="btn btn-primary">Přidat</button>
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
        ZastavkaService.createEntity(this.zastavkaModel).then((resp) => {
          this.goToTheDetailFromAdd(resp.data);
        }).catch(() => this.error = 'Operace se nezdařila. Něco se pokazilo');
      } else {
        this.error = 'Doplňte potřebné údaje';
      }
    }
  },
  computed: {
    isOk () {
      return !!this.zastavkaModel.NAZEV_ZASTAVKA;
    }
  }
}
</script>