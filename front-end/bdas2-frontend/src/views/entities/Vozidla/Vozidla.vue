<template>
  <WrapperList :entity-service="service" :delete-service="deleteService" :title="title" :detail-id-key="detailIdKey"/>
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
      allowedHeaderItems: [],
      service: VozidloService,
      deleteService: VozidloService,
    }
  }
}
</script>