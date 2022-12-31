<script>
export default {
  name: "UserMixin",
  methods: {
    logout () {
      this.$store.dispatch("auth/logout");
    }
  },
  computed: {
    isUserLogged () {
      return this.$store.getters["auth/isLoggedIn"];
    },
    isAdmin () {
      return this.$store.getters["auth/isAdmin"];
    },
    getLoggedUser () {
      return this.$store.getters["auth/loggedUser"];
    },
    getUniversalUserImagePath () {
      return "/universalUserImage.jpg";
    },
    getRoleName () {
      switch (Number(this.getLoggedUser?.ROLE)) {
        case 1:
          return "Administrátor";
        case 2:
          return "Uživatel";
        default:
          return "";
      }
    }
  }
}
</script>