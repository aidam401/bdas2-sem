<template>
  <div class="main-wrapper">
    <MainHeader title="Nový uživatel"/>
    <form>
      <div v-if="error" class="alert alert-danger">{{error}}</div>
      <div class="mb-3">
        <label for="profile-login" class="form-label">Login</label>
        <input v-model="userModel.LOGIN" type="text" class="form-control" id="profile-login" :class="{ 'is-invalid': submitted && !userModel.LOGIN }">
        <div v-show="submitted && !userModel.LOGIN" class="invalid-feedback">Login je povinný</div>
      </div>
      <div class="mb-3">
        <label for="profile-password" class="form-label">Heslo</label>
        <input v-model="userModel.HESLO" type="text" class="form-control" id="profile-password" :class="{ 'is-invalid': submitted && !userModel.HESLO }">
        <div v-show="submitted && !userModel.HESLO" class="invalid-feedback">Heslo je povinné</div>
      </div>
      <div class="mb-3">
        <searchable-select-box v-if="roleItems" label="Role" :options="roleItems" @changeSelection="handleChangeRole" :class="{ 'is-invalid': submitted && !userModel.ID_ROLE }" />
          <div v-show="submitted && !userModel.ID_ROLE" class="invalid-feedback">Zvolte roli</div>
      </div>
      <button @click="handlePridat" class="btn btn-primary">Pridat</button>
    </form>
  </div>
</template>

<script>
import RoleService from "@/_services/role.service";
import SearchableSelectBox from "@/components/SearchableSelectBox.vue";
import MainHeader from "@/components/MainHeader.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import UserService from "@/_services/user.service";
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";

export default {
  name: "UzivatelNovy",
  mixins: [RouterDetailMixin, ObjectUtilityMixin],
  components: {SearchableSelectBox, MainHeader},
  data () {
    return {
      userModel: {
        LOGIN: '',
        HESLO: '',
        ID_ROLE: 0
      },
      roleItems: [],
      submitted: false,
      error: ''
    }
  },
  created() {
    RoleService.getAll().then((resp) => {
      if (resp?.data) {
        this.roleItems = this.getItemsWithValueKey(resp.data, 'ID_ROLE', 'NAZEV_ROLE');
      }
    })
  },
  methods: {
    handlePridat () {
      event.preventDefault()
      this.submitted = true;
      if (this.isOk) {
        UserService.createEntity(this.userModel).then((resp) => {
          this.goToTheDetailFromAdd(resp?.data);
        }).catch(() => this.error = 'Operace se nezdařila. Něco se pokazilo');
      } else {
        this.error = 'Doplňte potřebné údaje';
      }
    },
    handleChangeRole (id, name) {
      this.userModel.ID_ROLE = id;
    }
  },
  computed: {
    isOk () {
      return !!this.userModel.LOGIN &&
          !!this.userModel.HESLO &&
          this.userModel.ID_ROLE;
    }
  }
}
</script>

<style scoped>

</style>