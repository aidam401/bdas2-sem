<template>
  <WrapperList :entity-service="service" :delete-service="service" :title="title" :detail-id-key="detailIdKey" :allowed-header-items="allowedHeaderItems"/>
</template>

<script>
import WrapperList from "@/components/WrapperList.vue";
import JizdniradyService from "@/_services/jizdnirady.service";

export default {
  name: "JizdniRady",
  components: {WrapperList},
  data () {
    return {
      title: 'Jízdní řády',
      detailIdKey: 'ID_JIZDNI_RAD',
      allowedHeaderItems: [
          'NAZEV_JIZDNI_RAD',
          'DATUM_ZAHAJENI_PLATNOSTI',
          'DATUM_KONCE_PLATNOSTI'
      ],
      service: JizdniradyService
    }
  }
}
</script>