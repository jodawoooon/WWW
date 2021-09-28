<template>
  <div>
    <Header :showArrow="false" message="Home" id="navBar" />
    <div class="default">
      <div class="main-top">
        <div style="margin-top: 20px">
          <span style="font-weight: 700">{{ userName }}님!</span>
          산책하기 좋은 날이네요 🌞
          <div>{{ dong }}</div>
        </div>
      </div>
      <el-divider></el-divider>
      <div>
        <p style="font-weight: 700">오늘의 추천 코스 👍</p>
        <div class="main-box"></div>
      </div>
      <div>
        <p style="font-weight: 700">이번주 걷기왕 👑</p>
        <div class="main-box"></div>
      </div>
      <div>
        <p style="font-weight: 700">오늘의 건강 뉴스 📰</p>
        <div class="main-box"></div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

axios.defaults.baseURL = "https://dapi.kakao.com/";
axios.defaults.headers.post["Content-Type"] = "application/json;charset=utf-8";
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";

import Header from "@/components/common/Header";
import("@/assets/style/Main.css");

export default {
  name: "Main",
  components: {
    Header,
  },
  data() {
    return {
      lat: "",
      lng: "",

      dong: "",
      userName: this.$store.getters.getUserName,
    };
  },
  methods: {
    geofind() {
      if (!("geolocation" in navigator)) {
        this.textContent = "Geolocation is not available.";
        return;
      }
      this.textContent = "Locating...";

      // get position
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          this.lat = pos.coords.latitude;
          this.lng = pos.coords.longitude;

          axios
            .get(
              "/v2/local/geo/coord2regioncode.json?x=" +
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
            });
        },
        (err) => {
          this.textContent = err.message;
        }
      );
    },
  },
  created() {
    this.geofind();
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
