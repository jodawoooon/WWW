<template>
  <div id="app">
    <div id="app-wrap">
      <navBar id="navBar" />
      <router-view />
      <Footer id="footer" />
    </div>
  </div>
</template>

<script>
import navBar from "@/components/common/NavBar";
import Footer from "@/components/common/Footer";

export default {
  name: "App",

  components: {
    navBar,
    Footer,
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
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
  height: 60px;
  z-index: 3;
}
#footer {
  height: 50px;
  width: 100%;
  max-width: 425px;
  left: 0;
  right: 0;
  margin: 0 auto;
  position: fixed;
  bottom: 0;
  background: #e1eedd;
}
</style>
