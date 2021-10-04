<template>
  <div>
    <Header :showArrow="false" message="WWW" id="navBar" />
    <div class="default">
      <div class="main-top">
        <div style="margin-top: 20px">
          <span v-if="isLoginGetters">
            <span style="font-weight: 700">{{ getName }}님!</span>
            {{ mention[Math.floor(Math.random() * 4)] }}</span
          >
          <span v-if="isLogoutGetters">
            <span style="font-weight: 700">WWW</span>에 오신 것을 환영해요!
            🏃‍♂️</span
          >
          <div style="text-align: center; margin-top: 14px; font-size: 10pt">
            <i class="el-icon-location" style="color: #ee684a" />
            {{ si }} {{ dong }}
          </div>
          <div style="display: flex; justify-content: center">
            <div
              class="dong_status"
              style="background-color: rgb(72, 146, 241, 30%)"
            >
              <el-row>
                <el-col :span="12">
                  <div
                    style="font-size: 18pt; font-weight: 700; margin-top: 7px"
                  >
                    <img
                      :src="icon"
                      style="width: 40px; vertical-align: middle"
                    />

                    {{ temp }}°C
                  </div>

                  <p style="margin-top: 7px; font-size: 9pt">
                    최고 {{ max_temp }}°C / 최저 {{ min_temp }}°C
                  </p>
                </el-col>
                <el-col :span="12">
                  <span style="font-size: 9pt; font-weight: 700"
                    >🌈 {{ today }} 날씨 🌈</span
                  >
                  <div style="height: 60px; overflow: auto; margin-top: 2px">
                    <div v-for="(weather, idx) in weatherList" v-bind:key="idx">
                      <div>
                        <div style="line-height: 3px">
                          <img
                            style="
                              width: 25px;
                              margin-right: 2px;
                              vertical-align: middle;
                            "
                            :src="`https://openweathermap.org/img/w/${weather.weather[0].icon}.png`"
                          />
                          <span style="font-size: 2px; margin-right: 3px"
                            >{{ weather.dt_txt.split(" ")[0].split("-")[1] }}-{{
                              weather.dt_txt.split(" ")[0].split("-")[2]
                            }}</span
                          >
                          <span style="font-size: 8pt"
                            ><strong>{{
                              weather.dt_txt.split(" ")[1].split(":")[0]
                            }}</strong
                            >시
                            <strong style="font-size: 10pt; margin-left: 2px"
                              >{{
                                (weather.main.temp - 273.15).toFixed(1)
                              }}°C</strong
                            ></span
                          >
                        </div>
                      </div>
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>

          <div
            style="text-align: center; margin-top: 20px"
            v-if="isLoginGetters"
          >
            <p style="font-size: 9pt">⏱ 오늘 걸은 시간 ⏱</p>
            <div style="font-size: 20pt; margin-top: 5px">
              <strong>00</strong>시간 <strong>00</strong>분
            </div>
            <el-row
              style="margin-top: 10px; display: flex; justify-content: center"
            >
              <el-button type="danger" @click="startWalk()"
                >START</el-button
              ></el-row
            >
          </div>
          <div
            style="text-align: center; margin-top: 20px"
            v-if="isLogoutGetters"
          >
            <div>
              <el-row
                style="
                  padding-top: 10px;
                  margin-bottom: 10px;
                  display: flex;
                  justify-content: center;
                "
              >
                <el-button type="danger" @click="clickLogin()">Login</el-button>
              </el-row>
              <div
                style="text-align: center; font-size: 10pt; margin-top: 10px"
              >
                <p>
                  로그인 후 WWW의 산책활동 분석을 통한 <br />
                  맞춤형 산책 코스 등 특별한 기능을 사용해보세요!
                </p>
              </div>
            </div>
          </div>

          <div></div>
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

<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.15.5/xlsx.full.min.js"></script>
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
      mention: [
        "환영합니다 오늘도 화이팅🙌 ",
        "산책 할 준비 되셨나요? 🏃‍♂️",
        "오늘도 좋은 하루 🥰",
        "만나서 반가워요 🙋‍♀️",
      ],
      lat: "",
      lng: "",
      icon: [0, 0],
      weatherCode: "",
      icon: "",
      today: "",
      weatherList: [],
      dong: "",
      si: "",
      do: "",
      temp: "",
      min_temp: "",
      max_temp: "",

      dust: "",
      dust_grade: "",

      corona_cnt: "",
      local_corona: "",

      userName: this.$store.getters.getLoginUserInfo.nickname,
    };
  },
  mounted() {
    this.$store.commit("SET_IS_NOT_INDEX");
  },
  methods: {
    clickLogin() {
      router.push("/login");
    },
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
              this.si = response.data.documents[0].region_2depth_name;
              this.do = response.data.documents[0].region_1depth_name.replace(
                "도",
                ""
              );
              this.$store.commit("SET_USER_LOCATION", {
                lat: this.lat,
                lng: this.lng,
                dong: this.dong,
                do: this.do,
              });
              // this.getMicroDust();
              // this.getCoronaStatus();
            });
        },
        (err) => {
          console.log(err);
          this.$store.commit("SET_IS_NOT_AGREE");
          router.push("/index");
        }
      );
    },
    getForecast() {
      axios
        .get(
          "https://api.openweathermap.org/data/2.5/forecast?lat=" +
            this.$store.state.location.lat +
            "&lon=" +
            this.$store.state.location.lng +
            "&appid=51f278e92de05bac589367d013849016"
        )
        .then((response) => {
          console.log(response);
          this.today = response.data.list[0].dt_txt.split(" ")[0];
          this.weatherList = response.data.list;
        });
    },
    getWeather() {
      axios
        .get(
          "https://api.openweathermap.org/data/2.5/weather?lat=" +
            this.$store.state.location.lat +
            "&lon=" +
            this.$store.state.location.lng +
            "&appid=51f278e92de05bac589367d013849016"
        )
        .then((response) => {
          console.log(response);
          const temp = response.data.main.temp - 273.15;
          const minTemp = response.data.main.temp_min - 273.15;
          const maxTemp = response.data.main.temp_max - 273.15;
          this.weatherCode = response.data.weather[0].id % 100;
          this.icon =
            "https://openweathermap.org/img/w/" +
            response.data.weather[0].icon +
            ".png";
          this.temp = temp.toFixed(1);
          this.min_temp = minTemp.toFixed(1);
          this.max_temp = maxTemp.toFixed(1);
        });
    },
  },
  created() {
    this.$store.commit("SET_CUR_PAGE", "Main");
    this.geofind();
    this.getWeather();
    this.getForecast();
  },
  computed: {
    isLoginGetters() {
      return this.$store.getters.getterLoginInfo;
    },
    isLogoutGetters() {
      return this.$store.getters.getterLogoutInfo;
    },
    getName() {
      return this.$store.getters.getLoginUserInfo.nickname;
    },
  },
};
</script>

<style scoped>
.main-top {
  height: 280px;
}

.main-box {
  margin-top: 7px;
  margin-bottom: 15px;
  height: 90px;
  background: #f6f6f6;
  border-radius: 20px;
}
.introimg {
  margin-top: 10px;
  width: 120px;
}
</style>
