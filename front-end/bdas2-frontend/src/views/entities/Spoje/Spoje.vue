<template>
  <WrapperList :allowed-header-items="allowedHeaderItems" :entity-service="service" :delete-service="service" :title="title" :detail-id-key="detailIdKey"/>
</template>

<script>
import WrapperList from "@/components/WrapperList.vue";
import SpojService from "@/_services/spoj.service";

export default {
  name: "Spoje",
  components: {WrapperList},
  data () {
    return {
      title: 'Spoje',
      detailIdKey: 'ID_SPOJ',
      allowedHeaderItems: [
          'NAZEV_SPOJ'
      ],
      service: SpojService,
    }
  }
}
</script>