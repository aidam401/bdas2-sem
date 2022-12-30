<template>
  <div>
    <h2> Profil <small class="text-muted">{{this.getProfileTitleSuffix}}</small> </h2>
    <form>
      <img :src="getUserFile" width="200" height="200" class="rounded mx-auto d-block" alt="...">

      <div class="mb-3">
        <label for="profile-login" class="form-label">Login</label>
        <input v-model="userModel.username" type="text" class="form-control" id="profile-login">
      </div>
      <div class="mb-3">
        <label for="profile-password" class="form-label">Heslo</label>
        <input v-model="userModel.password" type="text" class="form-control" id="profile-password">
      </div>
      <div class="mb-3">
        <label for="file" class="form-label">Profilový obrázek</label>
        <input id="file" @change="onFileChange" type="file" accept=".png, .jpg, .jpeg" class="form-control">
      </div>
      <button :disabled="isUserChanged" type="submit" class="btn btn-primary">Upravit</button>
    </form>
  </div>
</template>

<script>
import UserMixin from "@/mixins/UserMixin.vue";
import ObjectUtilityMixin from "@/mixins/ObjectUtilityMixin.vue";

export default {
  name: "Profile.vue",
  mixins: [UserMixin, ObjectUtilityMixin],
  created () {
    this.userModel = {...this.getLoggedUser};
  },
  data () {
    return {
      profilePic: null,
      userModel: {
        username: '',
        password: '',
        file: null,
        role: ''
      }
    }
  },
  methods: {
    handleUpravit () {
      // TODO from base.entity.service.js
    },
    onFileChange (e) {
      console.log(e.target.files);
      const [file] = e.target.files;
      if (file) {
        this.userModel.file = file;
      } else {
        this.userModel.file = this.getLoggedUser?.file;
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
      return this.getRoleName + " " + this.userModel.username;
    }
  }
}
</script>