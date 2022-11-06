<template>
  <div class="sidebar">
    <ul  class="navigation">
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
    </ul>

    <!--
  <div class="icon">
    <i @click="toggleMobileNav" v-show="mobile" class="far fa-bars" :class="{'icon-active': mobileNav}"></i>
  </div>
  <transition name="mobile-nav">
    <ul v-show="mobileNav" class="dropdown-nav">

      <li>
        <router-link class="link" :to="{name: 'Home'}">
          <i class="fas fa-home"/>
          <span>Domovská stránka</span>
        </router-link>
      </li>
      <li v-for="navigationItem in navigationItems">
        <router-link class="link" :to="{name: navigationItem.to}">
          <i class="fas fa-home"/>
          <span>{{ navigationItem.name }}</span>
        </router-link>
      </li>
    </ul>
  </transition> -->
</div>
</template>

<script>
export default {
  name: "Navigation",
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
    toggleMobileNav() {
      this.mobileNav = !this.mobileNav;
      },
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
    navigationItems() {
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
    }
  }
}
</script>

<style scoped>
</style>