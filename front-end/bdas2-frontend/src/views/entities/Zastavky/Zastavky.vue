<template>
  <WrapperList :allowed-header-items="allowedHeaderItems" :entity-service="service" :delete-service="service" :title="title" :detail-id-key="detailIdKey"/>
</template>

<script>
import WrapperList from "@/components/WrapperList.vue";
import ZastavkaService from "@/_services/zastavka.service";

export default {
  name: "Zastavky",
  components: {WrapperList},
  data () {
    return {
      title: 'Zastávky',
      detailIdKey: 'ID_ZASTAVKA',
      allowedHeaderItems: [
          'NAZEV_ZASTAVKA'
      ],
      service: ZastavkaService
    }
  }
}
</script>