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
          <i class="el-icon-user-solid"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <el-button @click="login()" size="mini" v-if="isLogoutGetters"
              >로그인</el-button
            ></el-dropdown-item
          >
          <el-dropdown-item>
            <el-button @click="logout()" size="mini" v-if="isLoginGetters"
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
import axios from "@/utils/axios.js";
import VueCookies from 'vue-cookies';

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

      axios
        .get("/kakao/logout")
        .then((result)=>{
          VueCookies.remove("userId")
          VueCookies.remove("accessToken")
          console.log(result)
          this.$store.commit("SET_USER_INFO", {
            userId: "",
            name : "",
          });
          this.$store.commit("SET_MORE_USER_INFO", {
            nickname: "",
            sido : "",
            gugun : "",
            dong: ""
          });
          this.$store.commit("SET_IS_LOGIN", {
            isLogin : false,
            isLogout :true
          });
          this.$store.commit("SET_USER_TOKEN", {
            accessToken : "",
            refreshToken : "",
            accessTokenExpire : "",
            refreshTokenExpire : ""
          });
        })
      router.push("/index");
    },
    goBack(back) {
      router.push(back);
    },
  },
  computed:{
    isLoginGetters(){
      return this.$store.getters.getterLoginInfo;
    },
    isLogoutGetters(){
      return this.$store.getters.getterLogoutInfo;
    }
  }
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
.logo {
  margin-top: 10%;
  height: 200px;
}
</style>
