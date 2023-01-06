<template>
  <div class="main-wrapper">
    <MainHeader title="Nové vozidlo"/>
    <form>
      <div v-if="error" class="alert alert-danger">{{error}}</div>
      <div class="mb-3">
        <label for="entity" class="form-label">Název</label>
        <input v-model="entity.NAZEV_VOZIDLO" type="text" class="form-control" id="entity" :class="{ 'is-invalid': submitted && !entity.NAZEV_VOZIDLO }">
        <div v-show="submitted && !entity.NAZEV_VOZIDLO" class="invalid-feedback">Název je povinný</div>
      </div>
      <div class="mb-3">
        <label for="spz" class="form-label">SPZ</label>
        <input v-model="entity.SPZ" type="text" class="form-control" id="spz" :class="{ 'is-invalid': submitted && !entity.SPZ }">
        <div v-show="submitted && !entity.SPZ" class="invalid-feedback">SPZ je povinná</div>
      </div>
      <div class="mb-3">
        <label for="rychlost" class="form-label">Maximálni rychlost</label>
        <input v-model="entity.MAX_RYCHLOST" type="number" class="form-control" id="rychlost" :class="{ 'is-invalid': submitted && !entity.MAX_RYCHLOST }">
        <div v-show="submitted && !entity.MAX_RYCHLOST" class="invalid-feedback">Zadejte max. rychlost</div>
      </div>
      <select-box @input="updateSelectedOption" label="Typ vozidla" :options="typyVozidel" :selected="entity.DISCR"/>
      <div v-if="entity.DISCR === 'AUTOBUS'" class="mb-3">
        <label for="kapacita" class="form-label">Kapacita nádrže</label>
        <input v-model="KAPACITA_NADRZE" type="number" class="form-control" id="kapacita" :class="{ 'is-invalid': submitted && !KAPACITA_NADRZE }">
        <div v-show="submitted && !KAPACITA_NADRZE" class="invalid-feedback">Zadejte kapacitu nádrže</div>
      </div>
      <div v-if="entity.DISCR === 'TROLEJBUS'" class="mb-3">
        <label for="spotreba" class="form-label">Elektrická spotřeba</label>
        <input v-model="ELEKTRICKA_SPOTREBA" type="number" class="form-control" id="spotreba" :class="{ 'is-invalid': submitted && !ELEKTRICKA_SPOTREBA }">
        <div v-show="submitted && !ELEKTRICKA_SPOTREBA" class="invalid-feedback">Zadejte elektrickou spotebu</div>
      </div>
      <button @click="handlePridat" class="btn btn-primary">Přidat</button>
    </form>
  </div>
</template>

<script>
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import MainHeader from "@/components/MainHeader.vue";
import SearchableSelectBox from "@/components/SearchableSelectBox.vue";
import SelectBox from "@/components/SelectBox.vue";
import VozidloService from "@/_services/vozidlo.service";
import AutobusService from "@/_services/autobus.service";
import TrolejbusService from "@/_services/trolejbus.service";

export default {
  name: "VozidloNove",
  mixins: [RouterDetailMixin, ObjectUtilityMixin],
  components: {SelectBox, SearchableSelectBox, MainHeader},
  data() {
    return {
      entity: {
        NAZEV_VOZIDLO: '',
        SPZ: '',
        MAX_RYCHLOST: 0,
        DISCR: 'AUTOBUS',
      },
      typyVozidel: [
        {value: 'AUTOBUS', text: 'Autobus' },
        {value: 'TROLEJBUS', text: 'Trolejbus' }
      ],
      KAPACITA_NADRZE: 0,
      ELEKTRICKA_SPOTREBA: 0,
      submitted: false,
      error: ''
    };
  },
  methods: {
    updateSelectedOption (e) {
      this.entity.DISCR = e.target.value;
    },
    handlePridat() {
      event.preventDefault();
      const data = {...this.entity}
      this.submitted = true;
      if (this.isOk) {
        VozidloService.createEntity(data).then((resp) => {
          if (resp?.data) {
            switch (data.DISCR) {
              case "AUTOBUS":
                AutobusService.createEntity({
                  'ID_VOZIDLO' : resp?.data,
                  'KAPACITA_NADRZE' : this.KAPACITA_NADRZE
                }).then(() => {
                  this.goToTheDetailFromAdd(resp.data);
                }).catch((e) => console.log(e));
                break;
              case "TROLEJBUS":
                TrolejbusService.createEntity( {
                  'ID_VOZIDLO' : resp?.data,
                  'ELEKTRICKA_SPOTREBA' : this.ELEKTRICKA_SPOTREBA
                }).then(() => {
                  this.goToTheDetailFromAdd(resp.data);
                }).catch((e) => console.log(e));
                break;
              default:
                break;
            }
          }
        }).catch((e) => this.error = 'Operace se nezdařila. Něco se pokazilo');
      } else {
        this.error = 'Doplňte potřebné údaje';
      }
    }
  },
  computed: {
    isOk() {
      if (this.entity.DISCR === "AUTOBUS" && !this.KAPACITA_NADRZE) {
        return false
      }
      if (this.entity.DISCR === "TROLEJBUS" && !this.ELEKTRICKA_SPOTREBA) {
        return false
      }
      return !!this.entity.NAZEV_VOZIDLO && !!this.entity.SPZ && this.entity.DISCR && this.entity.MAX_RYCHLOST;
    }
  }
}
</script>