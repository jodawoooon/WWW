<template>
  <div>
    <Header :showArrow="false" message="WWW" id="navBar" />
    <div class="default">
      <div class="main-top">
        <div style="margin-top: 20px">
          <span style="font-weight: 700">{{ userName }}님!</span>
          산책하기 좋은 날이네요 🌞
          <div>{{ dong }}</div>
          <div style="display: flex; justify-content: space-around">
            <div
              class="dong_status"
              style="background-color: rgb(72, 146, 241, 30%)"
            >
              <p>오늘의 날씨</p>
              <!-- <span>{{ weather.main.temp }}</span> -->
              <p>최고온도/최저온도</p>
            </div>
            <div
              class="dong_status"
              style="background-color: rgb(87, 180, 130, 30%)"
            >
              <p>미세먼지 지수</p>
              <span>좋음</span>
              <p>미세먼지 양</p>
            </div>
            <div
              class="dong_status"
              style="background-color: rgb(238, 104, 74, 30%)"
            >
              <p>신규 확진자</p>
              <span>21명</span>
              <p>전국 : 1800명 어쩌구</p>
            </div>
          </div>
        </div>
        <div>
          <el-row
            style="
              padding-top: 10px;
              margin-bottom: 10px;
              display: flex;
              justify-content: center;
            "
          >
            <el-button type="danger" @click="startWalk()"
              >START</el-button
            ></el-row
          >
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
import router from "@/router/index.js";

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
      userName: this.$store.getters.getLoginUserInfo.nickname,
      api_key: "51f278e92de05bac589367d013849016",
      url_base: "https://api.openweathermap.org/data/2.5/",
      query: "",
      weather: {},
    };
  },
  mounted() {
    this.$store.commit("SET_IS_NOT_INDEX");
  },
  methods: {
    startWalk() {
      this.$store.commit("SET_MAIN_TO_START");
      router.push("/record");
    },
    geofind() {
      if (!("geolocation" in navigator)) {
        this.textContent = "Geolocation is not available.";
        alert(this.textContent);
        return;
      }
      this.textContent = "Locating...";

      // get position
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          this.lat = pos.coords.latitude;
          this.lng = pos.coords.longitude;
          this.$store.commit("SET_IS_AGREE");
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
            });
        },
        (err) => {
          console.log(err);
          this.$store.commit("SET_IS_NOT_AGREE");
          router.push("/index");
        }
      );
    },
    fetchWeather() {
      if (this.dong != null) {
        let fetchUrl = `${this.url_base}weather?q=${this.dong}&units=metric&APPID=${this.api_key}`;
        fetch(fetchUrl)
          .then((res) => {
            console.log(res);
            return res.json();
          })
          .then((results) => {
            return this.setResult(results);
          });
      }
    },
    setResult(results) {
      this.weather = results;
    },
  },
  created() {
    this.$store.commit("SET_CUR_PAGE", "Main");
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
