<template>
  <div class="main-header">
    <h2> {{this.title}} <small class="text-muted">{{this.titleSuffix}}</small> </h2>
  </div>
</template>

<script>
export default {
  name: "MainHeader",
  props:  {
    title: String,
    titleSuffix: String,
  }
}
</script>