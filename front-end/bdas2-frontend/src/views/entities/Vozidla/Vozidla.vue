<template>
  <WrapperList :entity-service="service" :title="title" :detail-id-key="detailIdKey"/>
</template>

<script>
import WrapperList from "@/components/WrapperList.vue";
import VozidloViewService from "@/_services/view_services/vozidlo.view.service";

export default {
  name: "Linky",
  components: {WrapperList},
  data () {
    return {
      title: 'Linky',
      detailIdKey: 'ID_VOZIDLO',
      allowedHeaderItems: [],
      service: VozidloViewService
    }
  }
}
</script>