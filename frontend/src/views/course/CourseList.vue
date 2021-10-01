<template>
  <div>
    <!-- 여기서 코스 리스트 가져와서 CourseCard에 props로 넘겨주기-->
    <div v-if="!showNearby">
      <span style="font-weight: 700">{{ filter.dong }}</span> 일대의 산책로
      코스입니다.
    </div>
    <div v-if="showNearby">
      <span style="font-weight: 700">{{ filter.dong }}</span
      >에 위치한 산책로 코스가 없습니다.<br />
      현재 위치로부터 10km내 코스까지 포함된 결과입니다.<br /><br />
    </div>
    <div v-for="(course, idx) in courseList" v-bind:key="idx">
      <CourseCard
        :title="course.courseFlagName"
        :name="course.courseName"
        :courseId="course.courseId"
        :address="course.address"
        :km="course.courseLength"
        :min="course.time"
        :kcal="Math.round((course.timeInt * 60) * 0.06 * 10) / 10"
        :lat="course.latitude.toString()"
        :lng="course.longitude.toString()"
        :score="course.score"
        :detail="course.detail"
        :isBookmarked="course.myLike"
        :cafeList="course.cafeList"
        :convList="course.convList"
      />
    </div>
    <infinite-loading
      @infinite="readCourseList"
      ref="infiniteLoading"
      spinner="waveDots"
    >
      <div slot="no-results"></div>
      <div slot="no-more"></div>
    </infinite-loading>
  </div>
</template>

<script>
import InfiniteLoading from "vue-infinite-loading";
import CourseCard from "@/views/course/CourseCard";
import axios from "axios";

export default {
  name: "CourseList",
  props: ["filter"],
  components: {
    CourseCard,
    InfiniteLoading,
  },
  data() {
    return {
      courseReq: {
        userId: this.$store.getters.getLoginUserInfo.userId,
        page: 0,
        size: 5,
        sort: this.filter.sort,
        criteria: this.filter.criteria,
        minTime: this.filter.minTime,
        maxTime: this.filter.maxTime,
        minDistance: this.filter.minDistance,
        maxDistance: this.filter.maxDistance,
        dong: this.filter.dong,
        longitude: this.filter.longitude,
        latitude: this.filter.latitude,
      },
      hasNextPage: true,
      courseList: [],
      showNearby: false, // 동 검색에 실패할 경우 반경 10KM내 조건으로 재검색
    };
  },
  watch: {
    filter: {
      handler() {
        this.resetData();
        if (this.$refs.infiniteLoading) {
          this.$refs.infiniteLoading.stateChanger.reset();
        }
      },
      deep: true,
    },
  },
  methods: {
    resetData() {
      this.showNearby = false;
      this.courseList = [];
      this.courseReq.userId = this.$store.getters.getLoginUserInfo.userId;
      this.courseReq.page = 0;
      this.courseReq.sort = this.filter.sort;
      this.courseReq.criteria = this.filter.criteria;
      this.courseReq.minTime = this.filter.minTime;
      this.courseReq.maxTime = this.filter.maxTime;
      this.courseReq.minDistance = this.filter.minDistance;
      this.courseReq.maxDistance = this.filter.maxDistance;
      this.courseReq.dong = this.filter.dong;
      this.courseReq.longitude = this.filter.longitude;
      this.courseReq.latitude = this.filter.latitude;
    },
    readCourseList($state) {
      setTimeout(() => {
        axios.post("/api/course/", this.courseReq, {}).then((res) => {
          const data = res.data;
          this.courseList = this.courseList.concat(data.courseList);
          $state.loaded();
          if (this.courseReq.dong !== "" && this.courseList.length == 0) {
            this.courseReq.page = 0;
            this.showNearby = true;
            this.courseReq.dong = "";
            $state.complete();
            if (this.$refs.infiniteLoading) {
              this.$refs.infiniteLoading.stateChanger.reset();
            }
            return;
          }
          this.hasNextPage = data.hasNextPage;
          this.courseReq.page = data.page + 1;
          if (!this.hasNextPage) {
            $state.complete();
          }
        });
      }, 1000);
    },
  },
};
</script>

<style scoped></style>
