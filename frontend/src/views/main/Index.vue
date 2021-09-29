<template>
  <div>
    <div
      class="default"
      style="
        padding-top: 20%;
        margin-bottom: 10px;
        display: flex;
        justify-content: center;
      "
    >
      <img src="../../assets/test.png" width="250px" />
    </div>
    <el-dialog :visible.sync="dialogVisible" width="70%" center>
      <span>위치 정보 수집에 동의하셔야 서비스 이용이 가능합니다 🏃‍♀️🏃‍♂️</span>
      <span slot="footer" class="dialog-footer" style="padding-top: 0px">
        <el-button @click="dialogVisible = false">닫기</el-button>
      </span>
    </el-dialog>
    <el-row
      style="
        margin-top: 140px;

        display: flex;
        justify-content: center;
      "
    >
      <el-button
        type="danger"
        style="border: 4px solid #49ab76; width: 80%; background-color: #49ab76"
        @click="goMain()"
        >시작하기</el-button
      ></el-row
    >
    <span style="font-size: 9pt" v-if="!this.$store.getters.getIsAgree"
      >위치 수집에 동의하셔야 서비스 이용이 가능합니다</span
    >
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router/index.js";

import("@/assets/style/Main.css");

export default {
  name: "Main",
  components: {},
  data() {
    return {
      lat: "",
      lng: "",
      dialogVisible: false,
      dong: "",
      isAgree: false,
    };
  },
  mounted() {
    this.$store.commit("SET_IS_INDEX");
  },
  methods: {
    goMain() {
      this.geofind();
    },
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
          this.textContent = err.message;
        }
      );
    },
  },
};
</script>

<style scoped>
.main-top {
  height: 200px;
}

.main-box {
  margin-bottom: 15px;
  height: 90px;
  background: #f6f6f6;
  border-radius: 20px;
}
</style>
