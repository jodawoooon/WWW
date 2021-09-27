<template>
  <v-app id="app">
    <div id="app-wrap">
      <router-view />
      <Footer id="footer" />
    </div>
<<<<<<< HEAD
  </div>
=======
    
  </v-app>
>>>>>>> feat-14/userAPI
</template>

<script>
import Footer from "@/components/common/Footer";

export default {
  name: "App",

  components: {
    Footer,
  },
};
</script>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#footer {
  height: 55px;
  width: 100%;
  max-width: 425px;
  left: 0;
  right: 0;
  margin: 0 auto;
  position: fixed;
  bottom: 0;
  background: #49ab76;
}
</style>
