<template>
  <div>
    <!-- 여기서 코스 리스트 가져와서 CourseCard에 props로 넘겨주기-->
    <div v-if="!showNearby">
      <span style="font-weight: 700">{{ filter.dong }}</span> 일대의 산책로 코스입니다.
    </div>
    <div v-if="showNearby">
      <span style="font-weight: 700">{{ filter.dong }}</span>에 위치한 산책로 코스가 없습니다.<br>
      현재 위치로부터 10km내 코스까지 포함된 결과입니다.<br><br>
    </div>
    <div v-for="(course, idx) in courseList" v-bind:key="idx">
      <CourseCard
        :title="course.courseFlagName"
        :name="course.courseName"
        :courseId="course.courseId"
        :address="course.address"
        :km="course.courseLength"
        :min="course.time"
        :kcal="100"
        :isBookmarked="course.myLike"
      />
    </div>
  </div>
</template>

<script>
import { requestPost } from "@/api/request.js";
import CourseCard from "@/views/course/CourseCard";

export default {
  name: "CourseList",
  props: ["filter"],
  components: {
    CourseCard,
  },
  data() {
    return {
      courseReq: {
        userId: this.filter.userId,
        page: 0,
        size: 10,
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
        this.readCourseList();
      },
      deep: true,
    },
  },
  created() {
    this.readCourseList();
  },
  methods: {
    nextPage() {
      this.courseReq.page++;
      this.readCourseList();
    },
    resetData() {
      this.showNearby = false;
      this.courseList = [];
      this.courseReq.userId = this.filter.userId;
      this.courseReq.page = 0;
      this.courseReq.size = 10;
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
    readCourseList() {
      requestPost(
        "/api/course/",
        this.courseReq,
        {}
      ).then((res) => {
        this.courseList = res.courseList;
        this.hasNextPage = res.hasNextPage;
        this.page = res.page;
        console.log(this.courseList);
        // 동 이름으로 검색된 코스가 없을 경우 반경 10KM 이내 조건으로 다시 검색
        if (this.courseReq.dong !== "" && this.courseList.length == 0) {
          this.courseReq.dong = "";
          console.log("재실행");
          this.showNearby = true;
          this.readCourseList();
        }
      });
    },
  },
};
</script>

<style scoped></style>
