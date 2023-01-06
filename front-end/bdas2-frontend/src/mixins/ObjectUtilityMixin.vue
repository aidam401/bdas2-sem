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
    areObjectsArrayEqual(array1, array2) {
      if (array1?.length !== array2?.length) {
        return false;
      }
      for (let i = 0; i < array1.length; i++) {
        if (!this.areObjectsEqual(array1[i], array2[i])) {
          return false
        }
      }
      return true;
    },
    areProxyObjectsArrayEqual(array1, array2) {
      for (var key in array1) {
        const array1String = JSON.stringify(array1[key]);
        const array2String = JSON.stringify(array2[key]);
        if (array1String !== array2String) {
          return false
        }
      }
      return true;
    },
    isDefinedNonEmptyArray(array) {
      return array !== undefined && Array.isArray(array) && array.length > 0;
    },
    getItemsWithValueKey(items, valueKey, textKey) {
      var dataArray = []
      items.forEach((item) => {
        dataArray.push(this.getItemWithValueKey(item, valueKey, textKey));
      })

      return dataArray;
    },

    getItemWithValueKey(item, valueKey, textKey) {
      if (item !== null) {
        let newItem = {...item};
        newItem.value = item[valueKey];
        newItem.text = item[textKey];
        return newItem;
      } else {
        return {};
      }
    },
    difference(array1, array2) {
      return array1?.filter((item) =>  {
        for (let i = 0; i < array2?.length; i++) {
          if (array2[i].value === item.value) {
            return false;
          }
        }
        return true;
      });
    },
    sortObjectsArrayByObjectKey(array, key) {
      return array.sort((a, b) => a[key] - b[key]);
    }
  }

}
</script>