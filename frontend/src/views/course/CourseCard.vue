<template>
  <div class="card" @click="goDetail()">
    <el-row style="display: flex; align-items: center">
      <el-col :span="20">
        <p
          class="title"
          style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap"
        >
          {{ $props.title }} <span v-if="$props.title !== $props.name">- {{ $props.name }}</span>
        </p>
        <p class="content">
          <i class="el-icon-location" style="color: #ee684a" />{{
            $props.address
          }}
        </p>
        <p class="content">
          {{ $props.km }}km | {{ $props.min }} | {{ $props.kcal }}kcal
        </p>
      </el-col>
      <el-col :span="4" style="text-align: center">
        <i
          :class="[
            $props.isBookmarked ? 'el-icon-star-on' : 'el-icon-star-off',
          ]"
          style="font-size: 25pt; color: #ee684a"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import router from "@/router/index.js";

export default {
  name: "CourseCard",
  props: {
    title: {
      type: String,
      default: "title",
    },
    name: {
      type: String,
      defalut: "name",
    },
    courseId: {
      type: Number,
      default: 0,
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
      type: String,
      default: "min",
    },
    kcal: {
      type: Number,
      default: 0,
    },
    isBookmarked: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    goDetail() {
      router.push("/course/detail");
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
