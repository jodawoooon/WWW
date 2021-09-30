<template>
  <div></div>
</template>

<style></style>

<script>
import axios from "@/utils/axios.js";

export default {
  name: "Signup",
  created() {
    this.create();
  },
  data() {
    return {
      code: "",
      tokens: {
        accessToken: "",
        accessTokenExpire: "",
        refreshToken: "",
        refreshTokenExpire: "",
      },
      userInfo: {
        userId: "",
        nickname: "",
      },
    };
  },
  methods: {
    create() {
      this.code = this.$route.query.code;
      this.getToken();
    },
    login() {
      axios.post("/kakao/login", this.tokens).then((result) => {
        console.log(result);
        this.userInfo.userId = result.data.user.userId;
        this.userInfo.name = result.data.user.name;
        this.$store.commit("SET_USER_INFO", {
          userId: result.data.user.userId,
          name: result.data.user.name,
        });
        console.log(this.$store.state.loginUserInfo);
        axios
          .get("/info/present/" + this.userInfo.userId)
          .then((result) => {
            console.log(result);
            this.$router.push({ name: "Main" });
            console.log(this.$store.state.loginUserInfo);
          })
          .catch((err) => {
            console.log(err);
            this.$router.push({ name: "Signup" });
            console.log(this.$store.state.loginUserInfo);
          });
      });
    },
    getToken() {
      axios.get("/kakao/oauth?code=" + this.code).then((result) => {
        console.log(result);
        this.tokens.accessToken = result.data.user.accessToken;
        this.tokens.accessTokenExpire = result.data.user.accessTokenExpire;
        this.tokens.refreshToken = result.data.user.refreshToken;
        this.tokens.refreshTokenExpire = result.data.user.refreshTokenExpire;

        this.login();
      });
    },
  },
};
</script>
