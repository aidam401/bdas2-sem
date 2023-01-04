<template>
  <div class="main-wrapper">
    <MainHeader title="Profil" :title-suffix="this.getProfileTitleSuffix"/>
    <form>
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
        <label for="file" class="form-label">Profilový obrázek</label>
        <input id="file" @change="onFileChange" type="file" accept=".png, .jpg, .jpeg" class="form-control">
      </div>
      <button :disabled="isUserChanged" @click="handleUpravit" class="btn btn-primary">Upravit</button>
    </form>
  </div>
</template>

<script>
import UserMixin from "@/mixins/UserMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";
import MainHeader from "@/components/MainHeader.vue";
import UzivatelViewService from "@/_services/view_services/uzivatel.view.service";

export default {
  name: "Profile.vue",
  mixins: [UserMixin, ObjectUtilityMixin],
  components: {MainHeader},
  created () {
    this.userModel = {...this.getLoggedUser};
  },
  data () {
    return {
      profilePic: null,
      userModel: null
    }
  },
  methods: {
    handleUpravit () {
      event.preventDefault();
      delete this.userModel.authData;
      UzivatelViewService.updateEntity(this.userModel?.ID_UZIVATEL, this.userModel, 'ID_UZIVATEL');
    },
    onFileChange (e) {
      const [file] = e.target.files;
      if (file) {
        const reader = new FileReader();
        reader.readAsArrayBuffer(file);
        reader.onload = () => {
          this.userModel.NAZEV_SOUBOR = file?.name;
          this.userModel.TYP_SOUBOR = file?.type;
          this.userModel.DATA_SOUBOR = new Blob([reader.result], { type: file.type });
        };
      } else {
        if (this.getLoggedUser?.ID_SOUBOR) {
          this.userModel.DATA_SOUBOR = this.getLoggedUser?.DATA_SOUBOR ;
          this.userModel.TYP_SOUBOR = this.getLoggedUser?.TYP_SOUBOR;
          this.userModel.NAZEV_SOUBOR = this.getLoggedUser?.NAZEV_SOUBOR;
        } else {
          delete this.userModel.DATA_SOUBOR;
          delete this.userModel.TYP_SOUBOR;
          delete this.userModel.NAZEV_SOUBOR;
        }
      }
    }
  },
  computed: {
    isUserChanged () {
      return this.areObjectsEqual(this.getLoggedUser, this.userModel);
    },
    getUserFile () {
      return this.userModel?.DATA_SOUBOR ? URL.createObjectURL(this.userModel.DATA_SOUBOR) : this.getUniversalUserImagePath
    },
    getProfileTitleSuffix () {
      return this.getRoleName + " " + this.userModel.LOGIN;
    }
  }
}
</script>