<template>
  <div id="app">
    <div id="app-wrap">
      <router-view />
      <el-dialog :visible.sync="dialogVisible" width="70%" center>
        <span>위치 정보 수집에 동의하셔야 서비스 이용이 가능합니다 🏃‍♀️🏃‍♂️</span>
        <span slot="footer" class="dialog-footer" style="padding-top: 0px">
          <el-button @click="dialogVisible = false">닫기</el-button>
        </span>
      </el-dialog>
      <Footer id="footer" v-if="!getIsIndex" />
    </div>
  </div>
</template>

<script>
import Footer from "@/components/common/Footer";
import { mapGetters } from "vuex";
import router from "@/router/index.js";

export default {
  name: "App",

  components: {
    Footer,
  },
  data() {
    return {
      dialogVisible: false,
      isIndex: false,
    };
  },
  methods: {
    geofind() {
      if (!("geolocation" in navigator)) {
        console.log("TEST");
        return;
      }
      // get position
      navigator.geolocation.watchPosition(
        (pos) => {
          this.lat = pos.coords.latitude;
          this.lng = pos.coords.longitude;
        },
        (err) => {
          console.log("fail");
          this.dialogVisible = true;
          this.$store.commit("SET_IS_NOT_AGREE");
          router.push("/index");
          this.textContent = err.message;
        }
      );
    },
  },
  computed: {
    ...mapGetters(["getIsIndex"]),
    requireCurPage: function () {
      console.log(this.$store.getters.getIsIndex);
      return this.$store.getters.getIsIndex;
    },
  },
  watch: {
    getIsIndex() {
      this.isIndex = this.getIsIndex;
    },
    $route() {
      //this.geofind();
    },
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
  max-width: 425px;
  left: 0;
  margin: 0 auto;
  right: 0;
}

.app {
  height: 640px;
}
#footer {
  height: 55px;
  width: 100%;
  max-width: 425px;
  left: 0;
  right: 0;
  margin: 0 auto;
  z-index: 3;
  position: fixed;
  bottom: 0;
  background: #49ab76;
}
</style>
