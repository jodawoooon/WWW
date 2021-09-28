<template>
  <div>
    <Header message="Home" id="navBar" />
    <div class="default">
      <div class="main-top">
        <div style="margin-top: 20px">
          <span style="font-weight: 700">{{ userName }}님!</span>
          산책하기 좋은 날이네요 🌞
          <div></div>
        </div>
      </div>
      <el-divider></el-divider>
      <div class="main-content">
        <p style="font-weight: 700">오늘의 추천 코스 👍</p>
        <div class="main-box"></div>
      </div>
      <div class="main-content">
        <p style="font-weight: 700">이번주 걷기왕 👑</p>
        <div class="main-box"></div>
      </div>
      <div class="main-content">
        <p style="font-weight: 700">오늘의 건강 뉴스 📰</p>
        <div class="main-box"></div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/common/Header";
import("@/assets/style/Main.css");

export default {
  name: "Main",
  components: {
    Header,
  },
};
</script>

<style scoped>
.main-top {
  padding-left: 4%;
  padding-right: 4%;
  height: 200px;
  text-align: left;
}

.main-content {
  padding-left: 4%;
  padding-right: 4%;
  text-align: left;
}

.main-box {
  padding-left: 4%;
  padding-right: 4%;
  margin-bottom: 30px;
  height: 70px;
  background: #f6f6f6;
  border-radius: 20px;
}

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

  z-index: 3;
}
</style>
