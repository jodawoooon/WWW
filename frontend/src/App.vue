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
import axios from "axios";

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
        return;
      }

      // get position
      navigator.geolocation.watchPosition(
        (pos) => {
          this.lat = pos.coords.latitude;
          this.lng = pos.coords.longitude;

          axios
            .get(
              "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=" +
                this.lng +
                "&y=" +
                this.lat,
              {
                headers: {
                  Authorization: "KakaoAK bacd72f58ac01490602415c683ad8c05",
                },
              }
            )
            .then((response) => {
              this.dong = response.data.documents[0].region_3depth_name;
              this.$store.commit("SET_USER_LOCATION", {
                lat: this.lat,
                lng: this.lng,
                dong: this.dong,
              });
              this.$store.commit("SET_IS_AGREE");
              router.push("/main");
            });
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
  created() {
    this.geofind();
  },
  mounted() {
    this.geofind();
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
