<template>
  <WrapperList :allowed-header-items="allowedHeaderItems" :entity-service="service" :delete-service="service" :title="title" :detail-id-key="detailIdKey"/>
</template>

<script>
import WrapperList from "@/components/WrapperList.vue";
import PozicezamestnanceService from "@/_services/pozicezamestnance.service";

export default {
  name: "PoziceZamestnance",
  components: {WrapperList},
  data () {
    return {
      title: 'Pozice zaměstnance',
      detailIdKey: 'ID_POZICE_ZAMESTNANCE',
      allowedHeaderItems: [
          'NAZEV_POZICE_ZAMESTNANCE'
      ],
      service: PozicezamestnanceService
    }
  }
}
</script>