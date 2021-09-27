<template>
  <div>
    <Header id="navBar" />
    <div class="main-greeting">
      <div style="top-margin: 50px">test</div>
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
</style>
