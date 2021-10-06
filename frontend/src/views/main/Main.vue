e
<template>
  <div>
    <Header :showArrow="false" message="WWW" id="navBar" />
    <div class="default">
      <div class="main-top">
        <div style="margin-top: 20px">
          <span v-if="isLoginGetters">
            <span style="font-weight: 700">{{ getName }}님!</span> 환영합니다
            오늘도 화이팅🙌</span
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
                  <span style="font-size: 9pt; font-weight: 500"
                    >🌈 {{ today.split("-")[0] }}년 {{ today.split("-")[1] }}월
                    {{ today.split("-")[2] }}일 🌈</span
                  >

                  <div style="height: 60px; overflow: auto">
                    <div v-for="(weather, idx) in weatherList" v-bind:key="idx">
                      <div style="line-height: 3px">
                        <img
                          style="
                            width: 25px;
                            margin-right: 5px;
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
                </el-col>
              </el-row>
            </div>
          </div>

          <div
            style="text-align: center; margin-top: 20px"
            v-if="isLoginGetters"
          >
            <p style="font-size: 9pt">⏱ 오늘 걸은 시간</p>
            <div style="font-size: 20pt; margin-top: 5px">
              <strong>{{ h }}</strong
              >시간 <strong>{{ m }}</strong
              >분 <strong>{{ s }}</strong
              >초
            </div>
            <el-row
              style="margin-top: 10px; display: flex; justify-content: center"
            >
              <el-button type="danger" @click="startWalk()"
                >산책하기</el-button
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
      <!-- -->
      <div v-if="recommendList.length != 0">
        <p style="font-weight: 700">👍 {{ dong }} 인기 코스</p>
        <div
          class="main-box"
          style="display: flex; justify-content: space-between; padding: 0 25px"
        >
          <div class="bestCourse">
            <div style="font-weight: 600; font-size: 15pt">
              {{ recommendList[1] }}
            </div>
            <div class="detail-color" style="margin: 3px 0">
              <i class="el-icon-location icon-color" />
              {{ recommendList[0] }}
            </div>
            <div class="detail-color">
              {{ recommendList[2] }} | {{ recommendList[3] }}
            </div>
          </div>
          <div
            class="detail-color"
            style="text-align: center; display: flex; align-items: center"
          >
            <i class="el-icon-star-on icon-color" style="font-size: 18pt" />
            {{ recommendList[4] }}
          </div>
        </div>
      </div>
      <div>
        <p style="font-weight: 700">🏆 이번주 걷기왕</p>
        <div
          class="main-box"
          style="
            display: flex;
            flex-direction: column;
            justify-content: space-around;
          "
        >
          <div style="text-align: center; font-weight: bold">
            🥇 {{ ranking.ranking[0] }}
          </div>
          <div style="display: flex; justify-content: space-around">
            <div style="font-weight: bold">🥈 {{ ranking.ranking[1] }}</div>
            <div style="font-weight: bold">🥉 {{ ranking.ranking[2] }}</div>
          </div>
        </div>
      </div>

      <div style="margin-top: 24px">
        <p style="font-weight: 700">📰 건강 뉴스</p>
        <div class="main-box" id="news" style="padding: 10px">
          <el-row>
            <div
              style="
                font-size: 11pt;
                font-weight: 600;
                overflow: hidden;
                padding: 4px;
                height: 30px;
                white-space: nowrap;
                text-overflow: ellipsis;
              "
            >
              {{ newsTitle }}
            </div>
          </el-row>
          <hr style="opacity: 0.1" />
          <el-row style="display: flex; align-items: center">
            <el-col
              :span="19"
              style="
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                padding: 1px;
                font-size: 8pt;
                margin-top: 10px;
              "
            >
              {{ newsContent }}
            </el-col>
            <el-col :span="5">
              <el-button
                @click="newsScript()"
                type="danger"
                style="border-radius: 14px; margin-top: 10px; font-size: 8pt"
                size="mini"
              >
                더보기</el-button
              >
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router/index.js";
import mainApi from "@/api/main.js";

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
      today: "",
      weatherList: [],
      dong: "",
      si: "",
      sigu: "",
      temp: "",
      min_temp: "",
      max_temp: "",

      userName: this.$store.getters.getLoginUserInfo.nickname,

      recommendList: [],
      h: "00",
      m: "00",
      s: "00",
      ranking: [],
      newsUrl: "",
      newsTitle: "",
      newsContent: "",
    };
  },
  mounted() {
    this.$store.commit("SET_IS_NOT_INDEX");
    this.getTodayWalk();
    console.log(process.env);
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
              this.sigu =
                response.data.documents[0].region_2depth_name.split(" ")[0];
              // this.getRecommendData();
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

    async getWeather() {
      await axios
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
    async getRecommendData() {
      let data = {
        type: "today",
        sigu: this.sigu,
      };
      console.log(this.sigu);
      this.recommendList = await mainApi.getRecommendData(data, {});
      console.log("adfasdfasd");
      console.log(this.recommendList.recommendList);
    },
    async getRankData() {
      let data = {
        type: "rank",
      };
      this.ranking = await mainApi.getRankData(data, {});
    },
    async getTodayWalk() {
      if (this.$store.getters.getLoginUserInfo.nickname != "") {
        var today = new Date();
        var year = today.getFullYear();
        var month = ("0" + (today.getMonth() + 1)).slice(-2);
        var day = ("0" + today.getDate()).slice(-2);
        var dateString = year + "-" + month + "-" + day;
        let data = {
          type: "todaywalk",
          userName: this.$store.getters.getLoginUserInfo.nickname,
          date: dateString,
        };
        const today_walk_time = await mainApi.getTodayWalk(data, {});
        this.h = parseInt(today_walk_time.second / 3600);
        this.m = parseInt((today_walk_time.second % 3600) / 60);
        this.s = today_walk_time.second % 60;
        console.log(today_walk_time.second, this.h, this.m, this.s);
      }
    },
    getHealthNews() {
      console.log("들어옴?");
      axios
        .get(
          "https://dapi.kakao.com/v2/search/web?query=" +
            encodeURI("걷기 운동 신체 활동 기자 기사 코로나 일보"),
          {
            headers: {
              Authorization: "KakaoAK bacd72f58ac01490602415c683ad8c05",
            },
          }
        )
        .then((response) => {
          let item = response.data.documents[0];
          console.log(item.title);
          console.log(item.contents);
          item.title = item.title.replace(/(<([^>]+)>)/gi, " ");
          item.title = item.title.replaceAll("&quot", "");
          item.title = item.title.replaceAll("&lt", "");
          item.title = item.title.replaceAll("&#39", "");
          item.title = item.title.replaceAll("&gt", "");
          item.title = item.title.replaceAll("&amp", "");
          item.title = item.title.replaceAll("...", "");
          item.title = item.title.replaceAll(";", " ");
          // if (item.title.length > 20) {
          //   item.title = item.title.substring(0, 20) + "...";
          // }
          this.newsTitle = item.title;
          item.contents = item.contents.replaceAll("&quot", "");
          item.contents = item.contents.replace(/(<([^>]+)>)/gi, " ");
          item.contents = item.contents.replaceAll("&lt", " ");
          item.contents = item.contents.replaceAll("&#39", " ");
          item.contents = item.contents.replaceAll(";", " ");
          this.newsContent = item.contents;
          this.newsUrl = item.url;
          // let line1 = item.description.substring(0,20);

          // if (item.contents.length > 20) {
          //   this.content[0] = item.contents.substring(0, 20);
          //   if (item.description.length > 30) {
          //     this.content[1] = item.contents.substring(20, 30) + "...";
          //   }
          // }

          // item.description = item.description.substring(0,15)+"\n"+item.description.substring(15);
          // 정규식 표현으로 태그 제거
          // console.log(item.link);
          // console.log(item.title);
          // console.log(this.content[0]);
          // console.log(this.content[1]);
          // console.log(item.contents);
        });
    },
    newsScript() {
      window.open(this.newsUrl, "_blank");
    },
  },
  created() {
    this.$store.commit("SET_CUR_PAGE", "Main");
    this.geofind();
    this.getWeather();
    this.getForecast();
    this.getRankData();

    this.getTodayWalk();
    this.getHealthNews();
  },
  computed: {
    isLoginGetters() {
      return this.$store.getters.getterLoginInfo;
    },
    isLogoutGetters() {
      return this.$store.getters.getterLogoutInfo;
    },
    getName() {
      this.getTodayWalk();
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

.detail-color {
  font-size: 11pt;
  color: #6f7789;
}

.icon-color {
  color: #ee684a;
}

.introimg {
  margin-top: 10px;
  width: 120px;
}
::-webkit-scrollbar {
  width: 8px;
}
::-webkit-scrollbar-thumb {
  background-color: #ffffff7a;
  border-radius: 10px;
  background-clip: padding-box;
  border: 2px solid transparent;
}
::-webkit-scrollbar-track {
  background-color: rgba(255, 255, 255, 0);
  border-radius: 10px;
}
</style>
