<template>
  <WrapperList :entity-service="service" :title="title" :detail-id-key="detailIdKey"/>
</template>

<script>
import WrapperList from "@/components/WrapperList.vue";
import LinkyViewService from "@/_services/view_services/linky.view.service";

export default {
  name: "Linky",
  components: {WrapperList},
  data () {
    return {
      title: 'Linky',
      detailIdKey: 'ID_LINKA',
      allowedHeaderItems: [],
      service: LinkyViewService
    }
  }
}
</script>