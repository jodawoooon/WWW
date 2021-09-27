<template>
  <div>
    <nav id="nav">
      <div id="title">{{ $props.message }}</div>
      <div></div>
    </nav>
  </div>
</template>

<script>
// import {mapGetters, mapState} from "vuex";

export default {
  name: "Header",
  props: ["message"],
};
</script>

<style scoped>
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
</style>
