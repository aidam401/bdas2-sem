<template>
<WrapperList :entity-service="service" :title="title"/>
</template>

<script>
import MainHeader from "@/components/MainHeader.vue";
import UserService from "@/_services/user.service";
import TableList from "@/components/TableList.vue";
import WrapperList from "@/components/WrapperList.vue";

export default {
  name: "Uzivatele",
  components: {WrapperList, TableList, MainHeader},
  data () {
    return {
      title: 'Uživatelé',
      allowedHeaderItems: [],
      service: UserService
    }
  }
}
</script>

<style scoped>

</style>