<template>
    <div>
        <nav id="nav">
            <div id="logo">
                <router-link to="/main">
                <img src=""  alt=""/>WWW
                </router-link>
            </div>
        </nav>   
    </div>
</template>

<script>
// import {mapGetters, mapState} from "vuex";

export default {
    name: "navBar",
}
</script>

<style scoped>
#nav {
  background-color: white;
  top: 0;
  padding-left: 10px;
  padding-right: 10px;
  position: fixed;
  width: 100%;
  max-width: 425px;
  /*width: 100vw;*/
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-bottom: 5px;
  border-bottom: 1px solid #e4e6e9;
  box-shadow: 0 -5px 15px -8px #292929;
}

#logo {
  margin-top: 5px;
}

#logo img {
  height: 40px;
}
</style>