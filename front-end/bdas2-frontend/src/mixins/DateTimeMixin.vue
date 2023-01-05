<script>
export default {
  name: "DateTimeMixin",
  methods: {
    extract (value, index) {
      return value.split(' ')[index]
    },
    timeOnly (value) {
      return value.split(':').slice(0, 2).join(':') + 'h'
    },
    dateOnly (value) {
      const date = new Date(value)
      const year = date.getFullYear()
      const month = (date.getMonth() + 1).toString().padStart(2, '0')
      const day = date.getDate().toString().padStart(2, '0')
      return `${year}-${month}-${day}`
    }
  }
}
</script>