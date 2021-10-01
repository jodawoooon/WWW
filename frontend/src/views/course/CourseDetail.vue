<template>
  <div>
    
    <Header
      :showArrow="true"
      :back="prevPage"
      message="산책로 상세 정보"
      id="navBar"
    />

    <div>
      <div id="map" class="map"></div>
      <div class="bookmark" 
        v-if="this.$store.getters.getLoginUserInfo.userId"
        @click="clickStar()"
        >
        <i
          :class="[
            this.course.isBookmarked ? 'el-icon-star-on' : 'el-icon-star-off',
          ]"
        />
      </div>
    </div>

    <div class="box">
      <div class="content-top">
        <h3 style="font-weight: 700; margin-bottom: 8px">
          🏁 {{ this.course.title }}
        </h3>
        <p class="small-desc">
          <i
            class="el-icon-location"
            style="color: #ee684a; margin-right: 5px; margin-bottom: 8px"
          />{{ this.course.address }}
        </p>
        <p class="small-desc">
          {{ this.courseDetail.courseLength }}km | {{ this.courseDetail.time }} |
          {{ Math.round((this.courseDetail.timeInt * 60) * 0.06 * 10) / 10 }}kcal
        </p>
      </div>
      <div class="content-middle">
        <el-tabs v-model="activeName">
          <el-tab-pane label="산책로 소개" name="first">
            <div class="mini-desc">{{ this.courseDetail.detail }}</div>
          </el-tab-pane>
          <el-tab-pane label="주변 편의시설" name="second">
            <el-tabs :tab-position="tabPosition" style="height: 200px">
              <el-tab-pane label="편의점">
                <div class="mini-desc" style="margin-bottom: 10px">
                  산책로 주변 편의점은 {{ this.course.conv.length }}개 입니다.
                </div>
                <div v-for="(card, idx) in this.course.conv" :key="idx">
                  <div  @click="moveMap(card)">
                    <ConvCard
                      :name="card.name"
                      :address="card.address"                 
                    />
                  </div>
                  

                </div>
              </el-tab-pane>
              <el-tab-pane label="카페">
                <div class="mini-desc" style="margin-bottom: 10px">
                  산책로 주변 카페는 {{ this.course.cafe.length }}개 입니다.
                </div>
                <div v-for="(card, idx) in this.course.cafe" :key="idx">
                  <div  @click="moveMap(card)">
                    <ConvCard
                      :name="card.name"
                      :address="card.address"
                      :latitude="card.latitude"
                      :longitude="card.longitude"
                      @click="moveMap(card)"
                    />
                  </div>
                </div>
              </el-tab-pane>
            </el-tabs>
          </el-tab-pane>
        </el-tabs>
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
  </div>
</template>

<script>
import Header from "@/components/common/Header";
import ConvCard from "@/views/course/ConvCard";
import router from "@/router/index.js";
import axios from "axios";
import courseApi from "@/api/course.js";

import("@/assets/style/Main.css");

export default {
  name: "CourseDetail",
  components: {
    Header,
    ConvCard,
  },
  data() {
    return {
      tabPosition: "left",
      activeName: "first",
      course: this.$store.getters.getCourseDetail,
      prevPage:this.$store.getters.getPrevPage,
      courseDetail: "",
      userId: this.$store.getters.getLoginUserInfo.userId,
    };
  },
  async mounted() {
    
    await this.getCourseDetail(this.userId, this.course.id);
    
    this.$store.commit("SET_IS_NOT_INDEX");
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
    console.log(this.prevPage);

    

  },
  created(){
    this.course = this.$store.getters.getCourseDetail;
  },
  methods: {
    clickStar() {
      if (this.course.isBookmarked) {
        this.deleteLike();
      } else {
        this.createLike();
      }
    },
    createLike() {
      const req =  {
        courseId: this.course.id,
        userId: this.$store.getters.getLoginUserInfo.userId,
      };
      axios.post("/api/course/like", req, {}).then(() => {
        this.course.isBookmarked = !this.course.isBookmarked;
        this.$store.commit("SET_CUR_COURSE_LIKE", {
            isBookmarked: this.course.isBookmarked,
        });
      });
    },
    deleteLike() {
      const req =  {
        courseId: this.course.id,
        userId: this.$store.getters.getLoginUserInfo.userId,
      };
      axios.delete("/api/course/like", {
        data: req,
      }).then(() => {
        this.course.isBookmarked = !this.course.isBookmarked;
        this.$store.commit("SET_CUR_COURSE_LIKE", {
            isBookmarked: this.course.isBookmarked,
        });
      });
    },
    initMap() {

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
      var moveLatLon = new kakao.maps.LatLng(data.latitude, data.longitude);

      // 지도 중심을 이동 시킵니다
      this.map.setCenter(moveLatLon);
      console.log(moveLatLon);
      //Map 현재위치 마커
      var convMarkerSrc = require("@/assets/location.png");
      var convMarkerSize = new kakao.maps.Size(30, 30);

      // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
      var markerImage = new kakao.maps.MarkerImage(
        convMarkerSrc,
        convMarkerSize
      );

      var convMarkerPosition = new kakao.maps.LatLng(
        data.latitude,
        data.longitude
      );

      console.log(convMarkerPosition);

      if(this.marker!=null){
        this.marker.setPosition(convMarkerPosition);
      }else{
        var marker = new kakao.maps.Marker({
              position: convMarkerPosition,
              image: markerImage, // 마커이미지 설정
        });
        marker.setMap(this.map);

        this.marker = marker;
      }

   

      // var marker = new kakao.maps.Marker({
      //   map: this.map,
      //   title: "현재위치",
      //   position: runningMarkerPosition,
      //   icon: runningMarker,
      // });


    },
    startWalk() {
      router.push("/record");
    },

    async getCourseDetail(userId, courseId){
      let data = {
        type: "",
        userId: userId,
        courseId: courseId,
      };
      await courseApi.getCourseData(data, {}).then((response)=>{
        this.courseDetail=response;
        console.log(response);
      });
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
  min-height: 220px;
  margin-top: -200px;
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

.content-middle {
  margin-bottom: 60px;
}
.content-top {
  margin-bottom: 20px;
}

.small-desc {
  font-size: 11pt;
  color: #6f7789;
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
