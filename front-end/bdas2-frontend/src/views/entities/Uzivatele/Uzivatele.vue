<template>
<WrapperList :entity-service="service" :delete-service="service" :title="title" :detail-id-key="detailIdKey"/>
</template>

<script>
import WrapperList from "@/components/WrapperList.vue";
import UzivatelViewService from "@/_services/view_services/uzivatel.view.service";

export default {
  name: "Uzivatele",
  components: {WrapperList},
  data () {
    return {
      title: 'Uživatelé',
      detailIdKey: 'ID_UZIVATEL',
      allowedHeaderItems: [],
      service: UzivatelViewService
    }
  }
}
</script>