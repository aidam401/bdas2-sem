<template>
  <div class="drag-and-drop">
    <ul class="list-group">
      <li class="list-group-item" v-for="(item, index) in items" :key="index"
          @dragstart="dragStart(index)"
          @dragover="dragOver(index)"
          @dragend="dragEnd(index)"
          :class="{ 'dragging': index === draggingIndex }"
          draggable="true">{{ item.text }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "DragAndDropList",
  props: {
    items: Array,
  },
  data() {
    return {
      originalIndex: null,
      draggingIndex: null,
    };
  },
  methods: {
    dragStart(index) {
      this.originalIndex = index;
      this.draggingIndex = index;
    },
    dragOver(index) {
      if (this.draggingIndex === index) {
        return;
      }
      this.items.splice(index, 0, this.items.splice(this.draggingIndex, 1)[0]);
      this.draggingIndex = index;
    },
    dragEnd(index) {
      if (this.originalIndex !== this.draggingIndex) {
        //this.$emit('listChanged', this.items);
      }
      this.originalIndex = null;
      this.draggingIndex = null;
    },
  },
};
</script>