<template>
  <WrapperList :entity-service="service" :delete-service="deleteService" :title="title" :detail-id-key="detailIdKey"/>
</template>

<script>
import WrapperList from "@/components/WrapperList.vue";
import ZamestnanecViewService from "@/_services/view_services/zamestnanec.view.service";
import ZamestnanecService from "@/_services/zamestnanec.service";

export default {
  name: "Zamestnanci",
  components: {WrapperList},
  data () {
    return {
      title: 'Zaměstnanci',
      detailIdKey: 'ID_ZAMESTNANEC',
      allowedHeaderItems: [],
      service: ZamestnanecViewService,
      deleteService: ZamestnanecService
    }
  }
}
</script>