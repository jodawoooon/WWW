<template>
  <div class="card">
    <el-row>
      <p
        class="title"
        style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap"
      >
        {{ $props.name }}
      </p>
      <p class="content">
        <i class="el-icon-location" style="color: #ee684a" />{{
          $props.address
        }}
      </p>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "ConvCard",
  props: {
    name: {
      type: String,
      default: "name",
    },
    address: {
      type: String,
      default: "address",
    },
  },
  methods: {},
};
</script>

<style scoped>
.card {
  margin-bottom: 10px;
  height: 60px;
  background: #f6f6f6;
  border-radius: 20px;
  padding: 13px;
}

.title {
  font-size: 13pt;
  font-weight: 600;
}

.content {
  font-size: 11pt;
  color: #6f7789;
}
</style>
