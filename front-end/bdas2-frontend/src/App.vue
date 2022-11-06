<template>
  <div id="app" class="wrapper">
    <Navigation/>
    <div class="main_content">
      <router-view/>
    </div>
  </div>
</template>

<script>
import Navigation from "@/components/Navigation.vue";
export default {
  name: "App",
  components: {Navigation}
}
</script>

<style scoped>
body {
  margin: 0;
  padding: 0;
}
.main_content {
  background-color: #7b95ff;
}
</style>
