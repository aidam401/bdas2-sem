<template>
  <WrapperList :entity-service="service" :title="title" :detail-id-key="detailIdKey"/>
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
      allowedHeaderItems: [],
      service: ZastavkaService
    }
  }
}
</script>