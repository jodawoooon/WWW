<template>
  <div>
    <nav id="nav">
      <span style="float: left; line-height: 57px; padding-left: 20px">
        <i
          class="el-icon-back"
          v-if="showArrow == true"
          @click="goBack($props.back)"
        ></i>
        <i
          class="el-icon-back"
          v-if="showArrow == false"
          style="color: #ffffff"
        ></i>
      </span>
      <span v-if="$props.message != 'WWW'" id="title">{{
        $props.message
      }}</span>
      <span style="font-size: 16pt" v-if="$props.message == 'WWW'" id="title">
        <span style="color: #737373; font-weight: 800">W</span>
        <span style="color: #49ab76; font-weight: 800">W</span>
        <span style="color: #737373; font-weight: 800">W</span>
      </span>
      <el-dropdown style="float: right; line-height: 57px; padding-right: 30px">
        <span class="el-dropdown-link">
          <i class="el-icon-more"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <el-button @click="login()" size="mini"
              >로그인</el-button
            ></el-dropdown-item
          >
          <el-dropdown-item>
            <el-button @click="logout()" size="mini"
              >로그아웃</el-button
            ></el-dropdown-item
          >
        </el-dropdown-menu>
      </el-dropdown>
    </nav>
  </div>
</template>

<script>
// import {mapGetters, mapState} from "vuex";

import router from "@/router/index.js";

export default {
  name: "Header",
  props: {
    message: String,
    showArrow: Boolean,
    back: String,
  },
  data() {
    return {
      userInfo: this.$store.getters.getLoginUserInfo,
    };
  },
  methods: {
    login() {
      router.push("/login");
    },
    logout() {
      router.push("/main");
    },
    goBack(back) {
      router.push(back);
    },
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

.el-button {
  border: 0px;
}
</style>
