<template>
  <div class="main-wrapper">
    <h1 class="display-4 mb-3">Vítejte na naší dopravní aplikaci!</h1>
    <p class="lead">
      Jako host můžete použít naši vyhledávací funkci, abyste našli požadovanou trasu. Stačí vyhledat nebo vybrat linku z nabídky a my Vám ukážeme jednotlivé zastávky.
    </p>
    <p class="lead">
      Pokud si založíte účet, budete moci dělat ještě více. Nejenže si můžete vyhledat zastávky linek, ale také si můžete prohlédnout časy zastávek odjezdu a příjezdu pro každé spojení a to z pohodlí svého domova.
    </p>
    <p class="lead" v-if="!isUserLogged">
      Tak proč čekat? <router-link class="btn btn-primary" :to="{name: 'Register'}">Zaregistrujte se nyní</router-link><br> a využijte všechny možnosti, které naše aplikace nabízí. Doufáme, že si ji budete užívat!
    </p>
    <Login v-if="!isUserLogged"></Login>
  </div>
</template>

<script>
import Login from "@/views/Login.vue";
import UserMixin from "@/mixins/UserMixin.vue";
import MainHeader from "@/components/MainHeader.vue";
import DragAndDropList from "@/components/DragAndDropList.vue";
import Pagination from "@/components/Pagination.vue";

export default {
  name: "Home",
  mixins: [UserMixin],
  components: {Pagination, DragAndDropList, MainHeader, Login},
  data() {
    return {
      message: "Domovská stránka"
    }
  }
}
</script>