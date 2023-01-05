<template>
  <div class="drag-and-drop">
    <ul class="list-group">
      <li class="list-group-item" v-for="(item, index) in items" :key="index"
          @dragstart="dragStart(index)"
          @dragover="dragOver(index)"
          @dragend="dragEnd(index)"
          :class="{ 'dragging': index === draggingIndex }"
          :draggable="draggable">{{ item.text }}
        <button v-if="deletable" type="button"  class="btn-close" @click="deleteItem(index)" aria-label="Close"></button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "DragAndDropList",
  props: {
    items: Array,
    draggable: {
      type: Boolean,
      default: true
    },
    deletable: {
      type: Boolean,
      default: true
    }
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
        this.$emit('listChanged', this.items);
      }
      this.originalIndex = null;
      this.draggingIndex = null;
    },
    deleteItem(index) {
      const item = this.items[index];
      this.items.splice(index, 1);
      this.$emit('deleteItem', item);
    }
  },
};
</script>