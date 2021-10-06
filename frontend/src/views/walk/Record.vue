<template>
  <div>
    <Header :showArrow="true" back="/main" message="산책 기록" id="navBar" />
    <div>
      <div id="map" class="map"></div>
    </div>

    <div class="box">
      <div class="content-top">
        <h3 style="font-weight: 700; margin-bottom: 2px">
          🏁 {{ this.course.title }}
        </h3>
        <p style="font-weight: 400; font-size: 9pt; margin-bottom: 6px">
          🌎 현위치 : {{ this.address }}
        </p>
        <el-divider />

        <el-row style="text-align: center">
          <el-col :span="8" class="myRecord">
            <div id="run_desc distance">거리</div>
            <span id="acc_dis" style="font-weight: 700">
              {{ accumulated_distance.toFixed(2) }}km
            </span>
          </el-col>
          <el-col :span="8" class="myRecord">
            <div id="run_desc speed">속도</div>
            <span id="acc_time" style="font-weight: 700"
              >{{ speed.toFixed(2) }}m/s</span
            >
          </el-col>
          <el-col :span="8" class="myRecord">
            <div id="run_desc time">시간</div>
            <span id="time" style="font-weight: 700">{{ clock }}</span>
          </el-col>
        </el-row>

        <div class="btn_container">
          <div v-if="!running">
            <section
              class="bottom-bar"
              style="
                margin-top: 10px;
                margin-bottom: 10px;
                padding-top: 10px;
                margin-bottom: 10px;
                display: flex;
                justify-content: center;
              "
            >
              <div v-if="!isPause">
                <el-button
                  type="danger"
                  @click="startLocationUpdates"
                  class="btn round btn btn-dark btn-icon rounded-circle m-1"
                >
                  START
                </el-button>
              </div>
              <div v-if="isPause">
                <el-button
                  type="danger"
                  @click="watchLocationUpdates"
                  class="btn round btn btn-dark btn-icon rounded-circle m-1"
                >
                  START
                </el-button>
                <el-button
                  type="warning"
                  @click="endLocationUpdates"
                  class="
                    btn
                    round
                    btn btn-secondary btn-icon
                    rounded-circle
                    m-1
                  "
                >
                  STOP
                </el-button>
              </div>
            </section>
          </div>
          <div v-if="running">
            <section
              class="bottom-bar"
              style="
                margin-top: 10px;
                margin-bottom: 10px;
                padding-top: 10px;
                margin-bottom: 10px;
                display: flex;
                justify-content: center;
              "
            >
              <el-button
                type="button"
                @click="stopLocationUpdates"
                class="btn round btn btn-info btn-icon rounded-circle m-1"
              >
                PAUSE
              </el-button>

              <el-button
                type="warning"
                @click="endLocationUpdates"
                class="btn round btn btn-secondary btn-icon rounded-circle m-1"
              >
                STOP
              </el-button>
            </section>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/common/Header";
import axios from "axios";
import https from "@/utils/axios.js";
import router from "@/router/index.js";
import Swal from "sweetalert2";

import("@/assets/style/Main.css");

export default {
  name: "Record",
  components: {
    Header,
  },
  data() {
    return {
      current: { lat: 0, lng: 0 },
      previous: { lat: 0, lng: 0 },
      address: "",
      watchPositionId: null,
      map: null,
      accumulated_distance: 0,
      accumulated_time: 0,
      speed: 0,
      checkOneKm: 0,
      checkSecond: 0,
      avgSpeed: 0,
      linePath: [],
      poly: null,
      encoded_polyline: "",
      cur_marker: null,
      startTime: "",
      endTime: "",
      gugun: [],
      currentCity: "",
      thumbnail: "",
      tempRecords: [],
      stringTempRecords: [],

      //스톱워치
      clock: "00:00:00",
      timeBegan: null,
      timeStopped: null,
      stoppedDuration: 0,
      started: null,
      running: false,
      isPause: false,

      activeName: "first",
      course: this.$store.getters.getCourseDetail,
    };
  },
  mounted() {
    this.$store.commit("SET_IS_NOT_INDEX");
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=%VUE_APP_NEWS_KEY%&autoload=false";
      document.head.appendChild(script);
      this.resetLocations();
      this.accumulated_distance = 0;
      this.accumulated_time = 0;
      this.checkSecond = 0;
      this.checkOneKm = 0;
    }
  },
  methods: {
    savePosition() {
      var speed = 0;
      if (this.checkOneKm <= 0 || this.checkSecond <= 0) {
        speed = 0.001;
      } else {
        speed = this.speed + 0.001;
      }

      let tempRecord = {
        accDistance: this.accumulated_distance + 0.001,
        accTime: this.accumulated_time,
        speed: speed,
      };

      this.tempRecords.push(tempRecord);

      let stringTempRecord = {
        accDistance: (this.accumulated_distance + 0.001).toString(),
        accTime: this.accumulated_time.toString(),
        speed: speed.toString(),
      };
      this.stringTempRecords.push(stringTempRecord);

      https
        .post("/main/finishrecord", {
          userId: this.$store.getters.getLoginUserInfo.userId,
          courseId: this.course.id,
          distance: this.accumulated_distance,
          time: this.accumulated_time,
          calorie: Math.round(this.accumulated_time * 60 * 0.06 * 10) / 10,
        })
        .then((response) => {
          console.log(response);
        });
    },
    endLocationUpdates() {
      this.stopLocationUpdates();
      Swal.fire({
        width: 250,
        titleSize: 10,
        title: "산책 기록이 저장되었습니다 📬",
      });
      this.speed = (this.accumulated_distance * 1000) / this.accumulated_time;

      this.savePosition();
      this.isPause = false;
      this.running = false;
      this.stoppedDuration = 0;
      this.timeBegan = null;
      this.timeStopped = null;
      this.clock = "00:00:00";
      this.checkSecond = 0;
      this.checkOneKm = 0;
      this.endTime = new Date();
      this.endTime = this.$moment(this.endTime).format("YYYY-MM-DDTHH:mm:ss");
      router.push("/main");
    },
    stopLocationUpdates() {
      this.isPause = true;
      this.running = false;
      this.timeStopped = new Date();
      clearInterval(this.started);

      navigator.geolocation.clearWatch(this.watchPositionId);
      this.drawLines();
    },
    // encode_polyline(latLng, poly) {
    computeDistance(startCoords, destCoords) {
      var startLatRads = this.degreesToRadians(startCoords.lat);
      var startLongRads = this.degreesToRadians(startCoords.lng);
      var destLatRads = this.degreesToRadians(destCoords.lat);
      var destLongRads = this.degreesToRadians(destCoords.lng);

      var Radius = 6371; //지구의 반경(km)
      var distance =
        Math.acos(
          Math.sin(startLatRads) * Math.sin(destLatRads) +
            Math.cos(startLatRads) *
              Math.cos(destLatRads) *
              Math.cos(startLongRads - destLongRads)
        ) * Radius;

      return distance;
    },
    degreesToRadians(degrees) {
      var radians = (degrees * Math.PI) / 180;
      return radians;
    },
    encode_polyline(poly) {
      var path = poly.getPath();
      this.encoded_polyline = kakao.maps.geometry.encoding.encodePath(path);
    },
    drawLines() {
      this.poly = new kakao.maps.Polyline({
        // path: runningPathCoordinates,
        path: this.linePath,
        geodesic: true,
        strokeColor: "#ff0000",
        strokeOpacity: 1.0,
        strokeWeight: 2,
        map: this.map,
      });

      this.poly.setMap(this.map);
    },
    zeroPrefix(num, digit) {
      var zero = "";
      for (var i = 0; i < digit; i++) {
        zero += "0";
      }
      return (zero + num).slice(-digit);
    },
    clockRunning() {
      var currentTime = new Date();
      var timeElapsed = new Date(
        currentTime - this.timeBegan - this.stoppedDuration
      );

      var hour = timeElapsed.getUTCHours();
      var min = timeElapsed.getUTCMinutes();
      var sec = timeElapsed.getUTCSeconds();

      this.clock =
        this.zeroPrefix(hour, 2) +
        ":" +
        this.zeroPrefix(min, 2) +
        ":" +
        this.zeroPrefix(sec, 2);

      var realTime = (
        (currentTime - this.timeBegan - this.stoppedDuration) /
        1000
      ).toFixed(0);
      // var time_now = ((currentTime- this.timeBegan-this.stoppedDuration)/1000).toFixed(0)

      // this.accumulated_time += 1;
      // this.checkSecond += 1;
      this.accumulated_time = realTime;
      this.checkSecond = realTime;
    },
    resetLocations() {
      this.endTime = "";
      this.clock = "00:00:00";
      this.timeBegan = null;
      this.timeStopped = null;
      this.stoppedDuration = 0;
      this.started = null;
      this.checkSecond = 0;
      this.checkOneKm = 0;
      this.current.lat = 0;
      this.current.lng = 0;
      this.previous.lat = 0;
      this.previous.lng = 0;
    },
    convertToTime(origin) {
      var time = "";
      time += parseInt(origin / 60) + "'";
      time += (origin % 60) + '"';
      return time;
    },
    startLocationUpdates() {
      this.startTime = new Date();
      this.startTime = this.$moment(this.startTime).format(
        "YYYY-MM-DDTHH:mm:ss"
      );
      this.watchLocationUpdates();
    },
    initMap() {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.current.lat = position.coords.latitude;
          this.current.lng = position.coords.longitude;
          this.$store.commit("SET_IS_AGREE");
          axios
            .get(
              "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=" +
                this.current.lng +
                "&y=" +
                this.current.lat,
              {
                headers: {
                  Authorization: "KakaoAK bacd72f58ac01490602415c683ad8c05",
                },
              }
            )
            .then((response) => {
              var dong = response.data.documents[0].region_3depth_name;
              this.$store.commit("SET_USER_LOCATION", {
                lat: this.current.lat,
                lng: this.current.lng,
                dong: dong,
              });
              this.address = response.data.documents[0].address_name;
            });

          var container = document.getElementById("map");
          var options = {
            center: new kakao.maps.LatLng(this.current.lat, this.current.lng),
            level: 5,
          };

          this.map = new kakao.maps.Map(container, options);

          //Map 현재위치 마커
          var runningMarkerSrc = require("@/assets/location.png");
          var runningMarkerSize = new kakao.maps.Size(30, 30);

          // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
          var markerImage = new kakao.maps.MarkerImage(
            runningMarkerSrc,
            runningMarkerSize
          );

          var runningMarkerPosition = new kakao.maps.LatLng(
            this.current.lat,
            this.current.lng
          );

          var marker = new kakao.maps.Marker({
            position: runningMarkerPosition,
            image: markerImage, // 마커이미지 설정
          });

          marker.setMap(this.map);

          this.marker = marker;
        },
        (err) => {
          console.log(err);
          this.$store.commit("SET_IS_NOT_AGREE");
          router.push("/index");
        }
      );
    },

    watchLocationUpdates() {
      if (this.running) return;

      if (this.timeBegan === null) {
        this.resetLocations();
        this.timeBegan = new Date();
      }

      if (this.timeStopped !== null) {
        this.stoppedDuration += new Date() - this.timeStopped;
      }

      this.started = setInterval(this.clockRunning, 1000);
      this.running = true;
      this.isPause = false;

      //Map 시작
      var map = this.map;
      var marker = this.marker;

      this.watchPositionId = navigator.geolocation.watchPosition(
        (position) => {
          this.current.lat = position.coords.latitude;
          this.current.lng = position.coords.longitude;
          var now = new kakao.maps.LatLng(
            position.coords.latitude,
            position.coords.longitude
          );
          this.$store.commit("SET_IS_AGREE");
          axios
            .get(
              "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=" +
                this.current.lng +
                "&y=" +
                this.current.lat,
              {
                headers: {
                  Authorization: "KakaoAK bacd72f58ac01490602415c683ad8c05",
                },
              }
            )
            .then((response) => {
              this.address = response.data.documents[0].address_name;
            });
          map.setCenter(now);
          marker.setPosition(now);
          if (this.previous.lat == 0) {
            this.previous.lat = this.current.lat;
            this.previous.lng = this.current.lng;

            //런닝 시작
            var currentLatLng = new kakao.maps.LatLng(
              this.current.lat,
              this.current.lng
            );
            this.linePath.push(currentLatLng);
          } else {
            var distance = this.computeDistance(this.previous, this.current);
            var threshold = 0.001;
            this.previous.lat = this.current.lat;
            this.previous.lng = this.current.lng;

            if (distance > threshold) {
              // 일정속도 이상
              this.accumulated_distance += distance;
              this.checkOneKm += distance;

              this.linePath.push(
                new kakao.maps.LatLng(this.current.lat, this.current.lng)
              );
              this.speed = (this.checkOneKm * 1000) / this.checkSecond;

              this.drawLines();
            }
            if (this.checkOneKm >= 1) {
              //1km 도달
              this.savePosition();
              this.checkOneKm -= 1;
              this.checkSecond = 0;
            }
          }
        },
        (error) => {
          console.log(error.message);
          this.$store.commit("SET_IS_NOT_AGREE");
          router.push("/index");
        },
        {
          timeout: 5000,
          maximumAge: 0,
          enableHighAccuracy: true,
          distanceFilter: 40,
        }
      );
      this.map = map;
      this.cur_marker = marker;
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    moveMap(data) {
      //
      // 이동할 위도 경도 위치를 생성합니다
      var moveLatLon = new kakao.maps.LatLng(data.lat, data.lng);

      // 지도 중심을 이동 시킵니다
      this.map.setCenter(moveLatLon);
    },
  },
};
</script>

<style scoped>
.box {
  text-align: left;
  padding: 20px;
  border-radius: 30px 30px 0px 0px;
  background: #ffffff;
  height: 222.5px;
  margin-top: -197px;
  z-index: 1;
  position: relative;
}

.bookmark {
  cursor: pointer;
  z-index: 3;
  margin-top: -490px;
  margin-left: 10px;
  position: fixed;
  font-size: 20pt;
  color: #ee684a;
  background-color: #ffffff83;
  border-radius: 50%;
  padding: 4px;
}

.content-top {
  margin-bottom: 20px;
}

.mini-desc {
  font-size: 9.5pt;
  padding-left: 10px;
  padding-right: 10px;
  color: #6f7789;
}
.map {
  margin-top: 58px;

  height: 500px;
}
</style>
