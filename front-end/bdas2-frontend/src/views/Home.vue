<template>
  <div class="main-wrapper">
    <MainHeader title="Domovská stránka" />
    <p class="lead">
      <span>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cumque explicabo in quae tempora tenetur, vero? Ab accusamus aliquid asperiores consectetur, corporis dignissimos, ea earum hic itaque natus velit veniam voluptates?</span><span>Ad autem culpa cum cumque doloremque est facere, fugit labore laborum magni minima pariatur sint, soluta vel veniam veritatis, voluptatum! Consectetur eaque minus molestiae nulla omnis porro recusandae reprehenderit rerum.</span>
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