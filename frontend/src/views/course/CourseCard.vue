<template>
  <div class="card">
    <p
      class="title"
      style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap"
    >
      {{ $props.title }}
    </p>
    <p class="content">
      <i class="el-icon-location" style="color: #ee684a" />{{ $props.address }}
    </p>
    <p class="content">
      {{ $props.km }}km | {{ $props.min }}분 | {{ $props.kcal }}Kcal
    </p>
  </div>
</template>

<script>
export default {
  name: "CourseCard",
  props: {
    title: {
      type: String,
      default: "title",
    },
    address: {
      type: String,
      default: "address",
    },
    km: {
      type: Number,
      default: 0,
    },
    min: {
      type: Number,
      default: 0,
    },
    kcal: {
      type: Number,
      default: 0,
    },
  },
};
</script>

<style scoped>
.card {
  margin-bottom: 30px;
  height: 90px;
  background: #f6f6f6;
  border-radius: 20px;
  padding: 15px;
}

.title {
  font-size: 15pt;
  font-weight: 600;
}

.content {
  font-size: 11pt;
  color: #6f7789;
}
</style>
