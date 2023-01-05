<template>
  <WrapperList :allowed-header-items="allowedHeaderItems" :entity-service="service" :delete-service="service" :title="title" :detail-id-key="detailIdKey"/>
</template>

<script>
import WrapperList from "@/components/WrapperList.vue";
import RoleService from "@/_services/role.service";

export default {
  name: "Role",
  components: {WrapperList},
  data () {
    return {
      title: 'Role',
      detailIdKey: 'ID_ROLE',
      allowedHeaderItems: [
         'NAZEV_ROLE'
      ],
      service: RoleService
    }
  }
}
</script>