<template>
  <div class="main-wrapper">
    <MainHeader title="Detail vozidla"/>
    <form>
      <div class="mb-3">
        <label for="entity" class="form-label">Název</label>
        <input v-model="entityModel.NAZEV_VOZIDLO" type="text" class="form-control" id="entity">
      </div>
      <div class="mb-3">
        <label for="spz" class="form-label">SPZ</label>
        <input v-model="entityModel.SPZ" type="text" class="form-control" id="spz">
      </div>
      <div class="mb-3">
        <label for="rychlost" class="form-label">Maximálni rychlost</label>
        <input v-model="entityModel.MAX_RYCHLOST" type="number" class="form-control" id="rychlost">
      </div>
      <select-box @input="updateSelectedOption" label="Typ vozidla" :options="typyVozidel" :selected="entityModel.DISCR"/>
      <div v-if="entityModel.DISCR === 'AUTOBUS'" class="mb-3">
        <label for="kapacita" class="form-label">Kapacita nádrže</label>
        <input v-model="entityVozidloModel.KAPACITA_NADRZE" type="number" class="form-control" id="kapacita">
      </div>
      <div v-if="entityModel.DISCR === 'TROLEJBUS'" class="mb-3">
        <label for="spotreba" class="form-label">Elektrická spotřeba</label>
        <input v-model="entityVozidloModel.ELEKTRICKA_SPOTREBA" type="number" class="form-control" id="spotreba">
      </div>
      <button :disabled="areDataSame" @click="handleUpravit" class="btn btn-primary">Přidat</button>
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
  name: "VozidloDetail",
  mixins: [RouterDetailMixin, ObjectUtilityMixin],
  components: {SelectBox, SearchableSelectBox, MainHeader},
  data() {
    return {
      entity: {},
      entityModel: {},
      typyVozidel: [
        {value: 'AUTOBUS', text: 'Autobus' },
        {value: 'TROLEJBUS', text: 'Trolejbus' }
      ],
      entityVozidlo: {},
      entityVozidloModel: {}
    };
  },
  created() {
    this.init();
  },
  methods: {
    init () {
      VozidloService.getById(this.getIdDetail).then((resp) => {
        if (resp?.data) {
          this.entity = resp.data[0];
          this.entityModel = {...this.entity};
          switch (this.entityModel.DISCR) {
            case "AUTOBUS":
              AutobusService.getById(this.getIdDetail).then((resp) => {
                if (resp?.data) {
                  this.entityVozidlo = resp?.data[0];
                  this.entityVozidlo.ELEKTRICKA_SPOTREBA = null;
                  this.entityVozidloModel = {...this.entityVozidlo};
                }
              }).catch((e) => console.log(e));
              break;
            case "TROLEJBUS":
              TrolejbusService.getById( this.getIdDetail).then((resp) => {
                if (resp?.data) {
                  this.entityVozidlo = resp?.data[0];
                  this.entityVozidlo.KAPACITA_NADRZE = null;
                  this.entityVozidloModel = {...this.entityVozidlo};
                }
              }).catch((e) => console.log(e));
              break;
            default:
              break;
          }
        }
      });
    },
    updateSelectedOption (e) {
      this.entityModel.DISCR = e.target.value;
      switch (this.entityModel.DISCR) {
        case "AUTOBUS":
          this.entityVozidloModel.ELEKTRICKA_SPOTREBA = null;
          break;
        case "TROLEJBUS":
          this.entityVozidloModel.KAPACITA_NADRZE = null;
          break;
        default:
          break;
      }
    },
    handleUpravit () {
      event.preventDefault();
      const dataEntityModel = {...this.entityModel};
      const dataEntity = {...this.entityModel};
      const dataEntityVozidloModel = {...this.entityVozidloModel};

      VozidloService.updateEntity(this.getIdDetail, dataEntityModel, 'ID_VOZIDLO').then((resp) => {
        if (dataEntity.DISCR !== dataEntityModel.DISCR) {
          switch (dataEntity.DISCR) {
            case "AUTOBUS":
              AutobusService.deleteEntity(this.getIdDetail).catch((e) => console.log(e));
              delete dataEntityVozidloModel.ELEKTRICKA_SPOTREBA
              TrolejbusService.createEntity( this.entityVozidloModel).catch((e) => console.log(e));
              break;
            case "TROLEJBUS":
              TrolejbusService.deleteEntity( this.getIdDetail ).catch((e) => console.log(e));
              delete dataEntityVozidloModel.KAPACITA_NADRZE
              AutobusService.createEntity(dataEntityVozidloModel).catch((e) => console.log(e));
              break;
            default:
              break;
          }
        } else {
          switch (dataEntityModel.DISCR) {
            case "AUTOBUS":
              delete dataEntityVozidloModel.ELEKTRICKA_SPOTREBA
              AutobusService.updateEntity(this.getIdDetail, dataEntityVozidloModel, 'ID_VOZIDLO').catch((e) => console.log(e));
              break;
            case "TROLEJBUS":
              delete dataEntityVozidloModel.KAPACITA_NADRZE
              TrolejbusService.updateEntity( this.getIdDetail, dataEntityVozidloModel, 'ID_VOZIDLO').catch((e) => console.log(e));
              break;
            default:
              break;
          }
        }
      }).then(() => {this.init()}).catch((e) => console.log(e));
    }
  },
  computed: {
    areDataSame () {
      return this.areObjectsEqual(this.entity, this.entityModel) && this.areObjectsEqual(this.entityVozidlo, this.entityVozidloModel)
    },
    isSomewthingWrong() {
      // TODO
      return false;
    }
  }
}
</script>