<template>
  <div></div>
</template>

<style></style>

<script>
import axios from "@/utils/axios.js";
import VueCookies from 'vue-cookies';

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
        name: "",
      },
    };
  },
  methods: {
    create() {
      this.code = this.$route.query.code;
      this.getToken();
    },
    login(){
      axios
        .post("/kakao/login", this.tokens)
        .then((result)=>{
            this.userInfo.userId = result.data.user.userId;
            this.userInfo.name = result.data.user.name;
            console.log(result)
            this.$store.commit("SET_USER_INFO", {userId: result.data.user.userId, name : result.data.user.name});
            this.$store.commit("SET_IS_LOGIN", {
              isLogin : true,
              isLogout : false
            });
            axios
                .get("/info/present/" + this.userInfo.userId)
                .then((result)=>{
                    console.log(result);
                    VueCookies.set("accessToken", this.tokens.accessToken)
                    console.log(this.$store.state.loginUserInfo);
                    VueCookies.set("userId",this.$store.state.loginUserInfo.userId)
                    axios
                    .get("/info/" + this.userInfo.userId)
                    .then((res) =>{
                      console.log(res);
                      this.$store.state.loginUserInfo.nickname = res.data.user.nickname;
                      this.$store.state.loginUserInfo.sido = res.data.user.city;
                      this.$store.state.loginUserInfo.gugun = res.data.user.gu;
                      this.$store.state.loginUserInfo.dong = res.data.user.dong;
                    })
                    this.$router.push({name: "Main"});
                    console.log(this.$store.state.loginUserInfo);
                })
                .catch((err)=>{
                    console.log(err);
                    this.$router.push({name: "Signup"});
                    console.log(this.$store.state.loginUserInfo);
                })
                
        })
    },
    getToken() {
      axios.get("/kakao/oauth?code=" + this.code).then((result) => {
        console.log(result);
        this.tokens.accessToken = result.data.user.accessToken;
        this.tokens.accessTokenExpire = result.data.user.accessTokenExpire;
        this.tokens.refreshToken = result.data.user.refreshToken;
        this.tokens.refreshTokenExpire = result.data.user.refreshTokenExpire;
        console.log(this.tokens)
        this.$store.commit("SET_USER_TOKEN", {
          accessToken : result.data.user.accessToken,
          accessTokenExpire : result.data.user.accessTokenExpire,
          refreshToken : result.data.user.refreshToken,
          refreshTokenExpire : result.data.user.refreshTokenExpire,
        });
        console.log(2);

        this.login();
      });
    },
  },
};
</script>
