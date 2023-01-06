<template>
  <div class="sidebar">
    <ul class="navigation">
      <div class="branding">
        <router-link :to="{name: 'Home'}">
          <img src="/bus.ico" alt=""/>
        </router-link>
      </div>
      <li v-for="navigationItem in navigationItems">
        <router-link class="link" :to="{name: navigationItem.to}">
          <i :class="['fas', navigationItem.icon]"/>
          <span>{{ navigationItem.name }}</span>
        </router-link>
      </li>
      <div class="bottom-nav">
        <li v-if="isUserLogged">
          <router-link :to="{name: 'Profile'}">
            <i class="fas fa-user-pen" />
            <span>Profil</span>
          </router-link>
        </li>
        <li v-if="!isUserLogged">
          <router-link :to="{name: 'Login'}">
            <i class="fas fa-right-to-bracket" />
            <span>Přihlásit se</span>
          </router-link>
        </li>
        <li v-if="isUserLogged">
          <a href="#" @click="logout">
            <i class="fas fa-right-from-bracket" />
            <span>Odhlásit se</span>
          </a>
        </li>
      </div>
    </ul>
</div>
</template>

<script>
import UserMixin from "@/mixins/UserMixin.vue";

export default {
  name: "Navigation",
  mixins: [UserMixin],
  data() {
    return {
      mobile: false,
      mobileNav: null,
      windowWidth: null
    };
  },
  created() {
    window.addEventListener('resize', this.checkScreen);
    this.checkScreen();
  },
  methods: {
    checkScreen() {
      this.windowWidth = window.innerWidth;
      if (this.windowWidth <= 750) {
        this.mobile = true;
      } else {
        this.mobile = false;
        this.mobileNav = false;
      }
    }
  },
  computed : {
    navigationPublicItems() {
      return [
        { name: 'Domovská stránka' , to: 'Home', icon: 'fa-home' },
        { name: 'Vyhledat linku' , to: 'LinkaSearch', icon: 'fa-magnifying-glass-location' },
      ];
    },
    navigationLoggedInItems() {
      return [
        { name: 'Vyhledat spoj' , to: 'SpojSearch', icon: 'fa-magnifying-glass' },
      ];
    },
    allNavigationItems() {
      return [
        { name: 'Domovská stránka' , to: 'Home', icon: 'fa-home' },
        { name: 'Pozice zaměstnance' , to: 'Pozice', icon: 'fa-briefcase'},
        { name: 'Zastávky' , to: 'Zastavka', icon: 'fa-location-crosshairs'},
        { name: 'Linky' , to: 'Linka', icon: 'fa-map-location-dot'},
        { name: 'Vozidla', to: 'Vozidlo', icon: 'fa-bus'},
        { name: 'Zaměstnanci' , to: 'Zamestnanec', icon: 'fa-id-card'},
        { name: 'Jizdní řády' , to: 'JizdniRad', icon: 'fa-table-list'},
        { name: 'Spoje' , to: 'Spoj', icon: 'fa-circle-nodes'},
        { name: 'Role uživatele' , to: 'Role', icon: 'fa-shield-halved'},
        { name: 'Uživatelé', to: 'Uzivatel', icon: 'fa-users'},
        { name: 'Vyhledat spoj' , to: 'SpojSearch', icon: 'fa-magnifying-glass' },
        { name: 'Vyhledat linku' , to: 'LinkaSearch', icon: 'fa-magnifying-glass-location' },
      ];
    },
    navigationItems() {
      if (this.isAdmin) {
        return this.allNavigationItems;
      }
      if (this.isUserLogged) {
        return this.navigationPublicItems.concat(this.navigationLoggedInItems);
      }
      return this.navigationPublicItems;
    }
  }
}
</script>

<style scoped>
</style>