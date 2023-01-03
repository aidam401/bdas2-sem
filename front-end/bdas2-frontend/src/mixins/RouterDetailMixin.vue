<script>
import PozicezamestnanceService from "@/_services/pozicezamestnance.service";

export default {
  name: "RouterDetailMixin",
  computed: {
    getIdDetail () {
      return this.$route.params.id ;
    },
  },
  methods: {
    goToTheDetailFromAdd (id) {
      if (id) {
        const detailPath = this.$route.path.replace(/\/[^/]*$/, "/" + id.toString());
        this.$router.push(detailPath);
      }
    }
  }
}
</script>

<style scoped>

</style>