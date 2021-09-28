<template>
  <div>
    <Header :showArrow="true" back="/course" message="산책 기록" id="navBar" />

    <div>
      <div id="map" class="map"></div>
    </div>

    <div class="box">
      <div class="content-top">
        <h3 style="font-weight: 700; margin-bottom: 8px">
          {{ this.course.title }}
        </h3>
        <el-divider style="padding: 0" />
        <p>00:00:00</p>
        <span>0kcal</span><span>0km</span>
      </div>
      <el-row
        style="
          padding-top: 10px;
          margin-bottom: 10px;
          display: flex;
          justify-content: center;
        "
      >
        <el-button type="danger">STOP</el-button></el-row
      >
    </div>
  </div>
</template>

<script>
import Header from "@/components/common/Header";

import("@/assets/style/Main.css");

export default {
  name: "CourseDetail",
  components: {
    Header,
  },
  data() {
    return {
      activeName: "first",
      course: this.$store.getters.getCourseDetail,
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=779f3000dd215fa0e783546831836eca&autoload=false";
      document.head.appendChild(script);
    }
  },
  methods: {
    clickStar() {
      if (this.course.isBookmarked) {
        //axios 북마크 삭제
      } else {
        //axios 북마크 등록
      }

      this.course.isBookmarked = !this.course.isBookmarked;
    },
    initMap() {
      console.log(this.course.lat);
      var container = document.getElementById("map");
      var options = {
        center: new kakao.maps.LatLng(this.course.lat, this.course.lng),
        level: 5,
      };

      this.map = new kakao.maps.Map(container, options);

      // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
      var mapTypeControl = new kakao.maps.MapTypeControl();

      // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
      // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
      this.map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

      // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      var zoomControl = new kakao.maps.ZoomControl();
      this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
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
      console.log(data);
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
  height: 180px;
  margin-top: -150px;
  z-index: 1;
  position: relative;

  box-shadow: 2px 2px 7px 5px #c5c5c5;
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
