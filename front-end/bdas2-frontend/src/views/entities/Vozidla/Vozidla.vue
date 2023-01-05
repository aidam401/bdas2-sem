<template>
  <WrapperList :allowed-header-items="allowedHeaderItems" :entity-service="service" :delete-service="service" :title="title" :detail-id-key="detailIdKey"/>
</template>

<script>
import WrapperList from "@/components/WrapperList.vue";
import VozidloService from "@/_services/vozidlo.service";

export default {
  name: "Vozidlo",
  components: {WrapperList},
  data () {
    return {
      title: 'Vozidla',
      detailIdKey: 'ID_VOZIDLO',
      allowedHeaderItems: [
          'NAZEV_VOZIDLO',
          'DISCR',
          'SPZ',
          'MAX_RYCHLOST'
      ],
      service: VozidloService,
    }
  }
}
</script>