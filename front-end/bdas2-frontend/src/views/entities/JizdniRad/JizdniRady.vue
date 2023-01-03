<template>
  <WrapperList :entity-service="service" :title="title" :detail-id-key="detailIdKey"/>
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
      allowedHeaderItems: [],
      service: JizdniradyService
    }
  }
}
</script>