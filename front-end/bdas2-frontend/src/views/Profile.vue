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
import UserService from "@/_services/user.service";
import MainHeader from "@/components/MainHeader.vue";

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
      UserService.updateEntity(this.userModel);
    },
    onFileChange (e) {
      console.log(e.target.files);
      const [file] = e.target.files;
      if (file) {
        this.userModel.file = file;
      } else {
        if (this.getLoggedUser.file) {
          this.userModel.file = this.getLoggedUser?.file ;
        } else {
          delete this.userModel.file
          // TODO smazat všechny části file - typ, data atd.
        }
      }
    }
  },
  computed: {
    isUserChanged () {
      return this.areObjectsEqual(this.getLoggedUser, this.userModel);
    },
    getUserFile () {
      return this.userModel?.file ? URL.createObjectURL(this.userModel.file) : this.getUniversalUserImagePath
    },
    getProfileTitleSuffix () {
      return this.getRoleName + " " + this.userModel.LOGIN;
    }
  }
}
</script>