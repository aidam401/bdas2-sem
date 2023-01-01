<template>
  <div>
    <ul class="list-group">
      <li class="list-group-item" v-for="(item, index) in items" :key="index"
          @dragstart="dragStart(index)"
          @dragover="dragOver(index)"
          @dragend="dragEnd(index)"
          :class="{ 'dragging': index === draggingIndex }"
          draggable="true">{{ item }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "DragAndDropList",
  data() {
    return {
      items: ['Item 1', 'Item 2', 'Item 3'],
      draggingIndex: null,
    };
  },
  methods: {
    dragStart(index) {
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
      this.draggingIndex = null;
    },
  },
};
</script>

<style>
.dragging {
  background-color: #f8f9fa;
}
</style>