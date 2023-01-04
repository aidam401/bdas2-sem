<template>
  <WrapperList :entity-service="service" :delete-service="deleteService" :title="title" :detail-id-key="detailIdKey"/>
</template>

<script>
import WrapperList from "@/components/WrapperList.vue";
import VozidloViewService from "@/_services/view_services/vozidlo.view.service";
import VozidloService from "@/_services/vozidlo.service";

export default {
  name: "Vozidlo",
  components: {WrapperList},
  data () {
    return {
      title: 'Vozidla',
      detailIdKey: 'ID_VOZIDLO',
      allowedHeaderItems: [],
      service: VozidloViewService,
      deleteService: VozidloService,
    }
  }
}
</script>