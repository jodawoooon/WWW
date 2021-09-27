<template>
    <div>
        <div class="login-content">
            <div style="top-margin: 50px"><h2>환영합니다! 🙌</h2>
            <p>나의 산책 친구, <br> <span class="title">WWW</span> 와 함께 걸어보세요!</p></div>
        </div>

        <KakaoLogin
            class="kakao"
            api-key="779f3000dd215fa0e783546831836eca"
            image="kakao_login_btn_medium"
            :on-success=onSuccess
            :on-failure=onFailure
            />
    </div>


</template>

<script>
import KakaoLogin from 'vue-kakao-login'
import("@/assets/style/Main.css");

let onSuccess = (data) => {
  console.log(data)
  console.log("Login success")
}
let onFailure = (data) => {
  console.log(data)
  console.log("Login failure")
}

export default {
    name: 'Login',
    components: {
        KakaoLogin
    },
    methods: {
        onSuccess,
        onFailure
    }
}
</script>

<style scoped>
#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
#navBar {
  position: fixed;
  width: 100%;
  max-width: 425px;
  left: 0;
  right: 0;
  margin: 0 auto;
  height: 60px;
  z-index: 3;
}
.kakao{
    margin-top:3%;
}
.login-content{
    text-align: left;
}
.title{
    font-weight: 900;
}
</style>