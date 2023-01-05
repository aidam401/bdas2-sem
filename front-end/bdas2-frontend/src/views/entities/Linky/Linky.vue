<template>
  <WrapperList :allowed-header-items="allowedHeaderItems" :entity-service="service" :delete-service="service" :title="title" :detail-id-key="detailIdKey"/>
</template>

<script>
import WrapperList from "@/components/WrapperList.vue";
import LinkaService from "@/_services/linka.service";

export default {
  name: "Linky",
  components: {WrapperList},
  data () {
    return {
      title: 'Linky',
      detailIdKey: 'ID_LINKA',
      allowedHeaderItems: [
          'NAZEV_LINKA'
      ],
      service: LinkaService
    }
  }
}
</script>