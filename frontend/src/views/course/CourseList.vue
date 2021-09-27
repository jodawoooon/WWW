<template>
  <div>
    <!-- 여기서 코스 리스트 가져와서 CourseCard에 props로 넘겨주기-->
    <div v-for="(course,idx) in courseList" v-bind:key="idx">
      <CourseCard
        :title="course.courseName"
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
import {requestPost} from "@/api/request.js";
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
    };
  },
  watch: {
    "filter" : { 
      handler() {
        this.resetData();
        this.readCourseList();
      },
      deep: true
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
      requestPost("http://localhost:8080/api/course/", this.courseReq, {})
        .then((res) => {
          this.courseList = res.courseList;
          this.hasNextPage = res.hasNextPage;
          this.page = res.page;
          // console.log(this.courseList);
        });
    },
  },
};
</script>

<style scoped></style>
