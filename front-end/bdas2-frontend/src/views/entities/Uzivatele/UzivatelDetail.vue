<template>
  <div class="main-wrapper">
    <MainHeader title="Detail uživatele" :title-suffix="this.getProfileTitleSuffix"/>
    <form v-if="userModel">
      <img :src="getUserFile" width="200" height="200" class="rounded mx-auto d-block" alt="...">

      <div class="mb-3">
        <label for="profile-login" class="form-label">Login</label>
        <input v-model="userModel.LOGIN" type="text" class="form-control" id="profile-login">
      </div>
      <div class="mb-3">
        <label for="profile-password" class="form-label">Heslo</label>
        <input v-model="userModel.HESLO" type="text" class="form-control" id="profile-password">
      </div>
      <div class="mb-3">
        <select-box @input="handleChangeRole" label="Role" :options="this.roleItems" :selected="this.userModel.ID_ROLE"/>
      </div>
      <div class="mb-3">
        <label for="file" class="form-label">Profilový obrázek</label>
        <input id="file" @change="onFileChange" type="file" accept=".png, .jpg, .jpeg" class="form-control">
      </div>
      <button :disabled="isUserSame" @click="handleUpravit" class="btn btn-primary">Upravit</button>
    </form>
  </div>
</template>

<script>
import RouterDetailMixin from "@/mixins/RouterDetailMixin.vue";
import UserMixin from "@/mixins/UserMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import MainHeader from "@/components/MainHeader.vue";
import UzivatelViewService from "@/_services/view_services/uzivatel.view.service";
import SearchableSelectBox from "@/components/SearchableSelectBox.vue";
import RoleService from "@/_services/role.service";
import UserService from "@/_services/user.service";
import SelectBox from "@/components/SelectBox.vue";

export default {
  name: "UzivatelDetail",
  mixins: [UserMixin, ObjectUtilityMixin, RouterDetailMixin],
  components: {SelectBox, SearchableSelectBox, MainHeader},
  created () {
    UserService.getById(this.getIdDetail).then((resp) => {
      if (this.isDefinedNonEmptyArray(resp.data)) {
        this.nonChangedUser =  {...resp.data[0]};
        this.userModel = {...this.nonChangedUser};
      }
    });
    RoleService.getAll().then((resp) => {
      if (resp?.data) {
        this.roleItems = this.getItemsWithValueKey(resp.data, 'ID_ROLE', 'NAZEV_ROLE');
      }
    })
  },
  data () {
    return {
      profilePic: null,
      userModel: null,
      nonChangedUser: null,
      roleItems: [],
    }
  },
  methods: {
    handleUpravit () {
      event.preventDefault();
      UserService.updateEntity(this.getIdDetail, this.userModel, 'ID_UZIVATEL').then(() => {
        this.nonChangedUser = {...this.userModel}
      });
    },
    onFileChange (e) {
      const [file] = e.target.files;
      if (file) {
        const reader = new FileReader();
        reader.readAsArrayBuffer(file);
        reader.onload = (event) => {
          this.userModel.NAZEV_SOUBOR = file?.name;
          this.userModel.TYP_SOUBOR = file?.type;
          this.userModel.DATA_SOUBOR = new Blob([reader.result], { type: file.type });
        };
      } else {
        if (this.nonChangedUser?.ID_SOUBOR) {
          this.userModel.DATA_SOUBOR = this.nonChangedUser?.DATA_SOUBOR ;
          this.userModel.TYP_SOUBOR = this.nonChangedUser?.TYP_SOUBOR;
          this.userModel.NAZEV_SOUBOR = this.nonChangedUser?.NAZEV_SOUBOR;
        } else {
          delete this.userModel.DATA_SOUBOR;
          delete this.userModel.TYP_SOUBOR;
          delete this.userModel.NAZEV_SOUBOR;
        }
      }
    },
    handleChangeRole (e) {
      this.userModel.ID_ROLE = e.target.value;
    }
  },
  computed: {
    isUserSame () {
      return this.areObjectsEqual(this.nonChangedUser, this.userModel);
    },
    getUserFile () {
      return this.userModel?.DATA_SOUBOR ? URL.createObjectURL(this.userModel.DATA_SOUBOR) : this.getUniversalUserImagePath;
    },
    getProfileTitleSuffix () {
      return String(this.userModel?.LOGIN);
    }
  }
}
</script>