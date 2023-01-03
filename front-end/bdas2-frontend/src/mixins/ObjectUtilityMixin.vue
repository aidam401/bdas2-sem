<script>
export default {
  name: "ObjectUtilityMixin",
  methods: {
    areObjectsEqual(object1, object2) {
      if (!(object1 && object2))
        return false
      const keys1 = Object.keys(object1);
      const keys2 = Object.keys(object2);
      if (keys1.length !== keys2.length) {
        return false;
      }
      for (let key of keys1) {
        if (object1[key] !== object2[key]) {
          return false;
        }
      }
      return true;
    },
    isDefinedNonEmptyArray(array) {
      return array !== undefined && Array.isArray(array) && array.length > 0;
    },
    getDataForSelectBox(items, valueKey, textKey) {
      var dataArray = []
      items.forEach((item) => {
        dataArray.push(this.getValueForSelectBox(item, valueKey, textKey));
      })

      return dataArray;
    },

    getValueForSelectBox(item, valueKey, textKey) {
      if (item !== null) {
        return { value: item[valueKey], text: item[textKey] };
      } else {
        return {};
      }
    }
  }

}
</script>