<script>
export default {
  name: "RouterDetailMixin",
  computed: {
    getIdDetail () {
      return this.$route.params.id ;
    }
  }
}
</script>

<style scoped>

</style>