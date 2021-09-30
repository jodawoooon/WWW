<template>
  <div>
    <Header :showArrow="false" message="Home" id="navBar" />
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
              <span>{{ temp }}°C</span>
              <p>최고 {{ max_temp }}°C</p>
              <p>최저 {{ min_temp }}°C</p>
            </div>
            <div
              class="dong_status"
              style="background-color: rgb(87, 180, 130, 30%)"
            >
              <p>미세먼지 농도</p>
              <span>{{ dust_grade }}</span>
              <p>{{ dust }}㎍/㎥</p>
            </div>
            <div
              class="dong_status"
              style="background-color: rgb(238, 104, 74, 30%)"
            >
              <p>신규 확진자</p>
              <span>{{ local_corona }}명</span>
              <p>전국 : {{ corona_cnt }}명</p>
            </div>
          </div>
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
      do: "",
      userName: this.$store.getters.getUserName,
      userInfo: [],

      temp: "",
      min_temp: "",
      max_temp: "",

      dust: "",
      dust_grade: "",

      corona_cnt: "",
      local_corona: "",
    };
  },
  computed: {
    getDo() {
      return this.$store.state.location.do;
    },
    // getCovid() {
    //   return this.$store.state.covid.corona_cnt;
    // },
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
              this.do = response.data.documents[0].region_1depth_name.replace(
                "도",
                "" 
              );
              this.getMicroDust();
              console.log(this.dust)
              // console.log("abcc")
              console.log(this.dong)
              console.log(this.do)
              this.$store.commit("SET_USER_LOCATION", {
                lat: this.lat,
                lng: this.lng,
                dong: this.dong,
                do: this.do,
              });
              // this.getWeather(this.lat,this.lng);
              // await this.getMicroDust(this.do);
              // await this.getCoronaStatus();
            });
        },
        (err) => {
          this.textContent = err.message;
        }
      );
    },
    getWeather() {
      console.log("a")
      axios
        .get(
          "http://api.openweathermap.org/data/2.5/weather?lat=" +
            this.$store.state.location.lat +
            "&lon=" +
            this.$store.state.location.lng +
            "&appid=51f278e92de05bac589367d013849016"
        )
        .then((response) => {
          const temp = response.data.main.temp - 273.15;
          const minTemp = response.data.main.temp_min - 273.15;
          const maxTemp = response.data.main.temp_max - 273.15;
          this.temp = temp.toFixed(1);
          this.min_temp = minTemp.toFixed(1);
          this.max_temp = maxTemp.toFixed(1);
        });
    },
    getMicroDust() {
      console.log("b")
      // console.log(this.do)
      // console.log(this.$store.state.location.do)
      axios
        .get(
          "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?sidoName=" +
            this.$store.state.location.do +
            "&returnType=json&serviceKey=BzaLot6kNeh07KbZkzyJuXRdK5iGd0RvcK540gVbI%2F0aJy%2FlA0wHtckzM6t986i4LUkYJogx%2BeEktaXqnCbBzw%3D%3D"
        )
        .then((response) => {
          this.dust = response.data.response.body.items[0].pm10Value;
          const grade = response.data.response.body.items[0].pm10Grade;
          if (grade == 1) this.dust_grade = "좋음";
          else if (grade == 2) this.dust_grade = "보통";
          else if (grade == 3) this.dust_grade = "나쁨";
          else if (grade == 4) this.dust_grade = "매우 나쁨";
        });
    },
    getCoronaStatus() {
      console.log("c")
      axios
        .get(
          "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey=BzaLot6kNeh07KbZkzyJuXRdK5iGd0RvcK540gVbI%2F0aJy%2FlA0wHtckzM6t986i4LUkYJogx%2BeEktaXqnCbBzw%3D%3D"
        )
        .then((response) => {
          const cStatus = response.data.response.body.items.item;
          for (var i = 0; i < cStatus.length; i++) {
            if (cStatus[i].gubun == this.do) {
              // console.log(cStatus[i].gubun);
              // console.log(this.do);
              this.corona_cnt = cStatus[i].defCnt;
              this.local_corona = cStatus[i].localOccCnt;
            }
          }
          this.$store.commit("SET_COVID", {
            corona_cnt: this.corona_cnt,
            local_corona: this.local_corona,
          });
        });
    },
    // getTodayWalk(userId,date){
    //   let data = {
    //     type: "todaywalk",
    //     userId: userId,
    //     date: date,
    //   };
    //   this.userInfo = await mainApi.getWalkDate(data,{});
    // },
  },
  created() {
    this.geofind();
    this.getWeather();
    this.getMicroDust();
    this.getCoronaStatus();
  },
  mounted() {
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
