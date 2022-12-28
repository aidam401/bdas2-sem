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
        { name: 'Linky' , to: 'Linka', icon: 'fa-link' }
      ];
    },
    navigationAdminItems() {
      return [
        { name: 'Řidiči' , to: 'Ridic', icon: 'fa-id-card'},
        { name: 'Trolejbusy', to: 'Trolejbus', icon: 'fa-train-tram'},
        { name: 'Autobusy', to: 'Autobus', icon: 'fa-bus'},
        { name: 'Uživatelé', to: 'Uzivatel', icon: 'fa-users'},
        { name: 'Uživatelské role', to: 'Uzivatelske_role', icon: 'fa-user-shield'}
      ];
    },
    navigationLoggedInItems() {
      return [
        { name: 'Adresy' , to: 'Adresa', icon: 'fa-map-location-dot'},
        { name: 'Zastávky' , to: 'Zastavka', icon: 'fa-location-crosshairs'},
        { name: 'Spoje' , to: 'Spoj', icon: 'fa-circle-nodes'},
        { name: 'Časy zastávek' , to: 'Casy_zastavek', icon: 'fa-clock'},
        { name: 'Záznamy jízdních řádů' , to: 'Zaznam_jizdniho_radu', icon: 'fa-timeline'},
        { name: 'Jizdní řády' , to: 'Jizdni_rad', icon: 'fa-table-list'},
        { name: 'Typy jízdních řádů' , to: 'Typ_jizdniho_radu', icon: 'fa-table'},
        { name: 'Aktuální jízdy' , to: 'Aktualni_jizda', icon: 'fa-road'},
      ];
    },
    allNavigationItems() {
      return [
        { name: 'Domovská stránka' , to: 'Home', icon: 'fa-home' },
        { name: 'Linky' , to: 'Linka', icon: 'fa-link' },
        { name: 'Adresy' , to: 'Adresa', icon: 'fa-map-location-dot'},
        { name: 'Zastávky' , to: 'Zastavka', icon: 'fa-location-crosshairs'},
        { name: 'Spoje' , to: 'Spoj', icon: 'fa-circle-nodes'},
        { name: 'Časy zastávek' , to: 'Casy_zastavek', icon: 'fa-clock'},
        { name: 'Záznamy jízdních řádů' , to: 'Zaznam_jizdniho_radu', icon: 'fa-timeline'},
        { name: 'Jizdní řády' , to: 'Jizdni_rad', icon: 'fa-table-list'},
        { name: 'Typy jízdních řádů' , to: 'Typ_jizdniho_radu', icon: 'fa-table'},
        { name: 'Aktuální jízdy' , to: 'Aktualni_jizda', icon: 'fa-road'},
        { name: 'Řidiči' , to: 'Ridic', icon: 'fa-id-card'},
        { name: 'Trolejbusy', to: 'Trolejbus', icon: 'fa-train-tram'},
        { name: 'Autobusy', to: 'Autobus', icon: 'fa-bus'},
        { name: 'Uživatelé', to: 'Uzivatel', icon: 'fa-users'},
        { name: 'Uživatelské role', to: 'Uzivatelske_role', icon: 'fa-user-shield'}
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