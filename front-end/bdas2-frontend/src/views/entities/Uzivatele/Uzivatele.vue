<template>
<WrapperList :allowed-header-items="allowedHeaderItems" :entity-service="service" :delete-service="deleteService" :title="title" :detail-id-key="detailIdKey"/>
</template>

<script>
import WrapperList from "@/components/WrapperList.vue";
import UzivatelViewService from "@/_services/view_services/uzivatel.view.service";
import userService from "@/_services/user.service";

export default {
  name: "Uzivatele",
  components: {WrapperList},
  data () {
    return {
      title: 'Uživatelé',
      detailIdKey: 'ID_UZIVATEL',
      allowedHeaderItems: [
          'NAZEV_ROLE',
          'LOGIN',
          'NAZEV_SOUBOR',
          'TYP_SOUBOR'
      ],
      service: UzivatelViewService,
      deleteService: userService
    }
  }
}
</script>