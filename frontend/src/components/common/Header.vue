<template>
  <div>
    <nav id="nav">
      <span style="float: left; line-height: 57px; padding-left: 20px">
        <i class="el-icon-back" v-if="showArrow == true"></i>
        <i
          class="el-icon-back"
          v-if="showArrow == false"
          style="color: #ffffff"
        ></i>
      </span>
      <span id="title">{{ $props.message }}</span>
      <el-dropdown style="float: right; line-height: 57px; padding-right: 30px">
        <span class="el-dropdown-link">
          <i class="el-icon-more"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>로그인</el-dropdown-item>
          <el-dropdown-item>로그아웃</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </nav>
  </div>
</template>

<script>
// import {mapGetters, mapState} from "vuex";

export default {
  name: "Header",
  props: {
    message: String,
    showArrow: Boolean,
  },
};
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
}
.el-icon-arrow-down {
  font-size: 12px;
}
#nav {
  background-color: white;
  top: 0;
  padding-top: 5px;
  padding-left: 1%;
  padding-right: 1%;
  position: fixed;

  width: 100%;

  max-width: 425px;
  /*width: 100vw;*/
  height: 57px;
  line-height: 57px;
}

#title {
  font-weight: 700;
}
</style>
