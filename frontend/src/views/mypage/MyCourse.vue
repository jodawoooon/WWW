<template>
  <div id="main">
    <Header :showArrow="false" message="우리 동네 산책로" id="navBar" />
    <div id="space"></div>

    <v-card>
      <v-tabs centered fixed-tabs slider-color="red">
        <v-tab
          v-on:click="getRecentCourse(userId)"
          style="font-size: 20px; color: gray; font-weight: bold"
          >최근 코스</v-tab
        >
        <v-tab
          v-on:click="getWishCourse(userId)"
          style="font-size: 20px; color: gray; font-weight: bold"
          >관심 코스</v-tab
        >
      </v-tabs>
    </v-card>

    <div v-if="!isRecent">
      <div v-for="(course, idx) in wishCourse.courseList" v-bind:key="idx">
        <CourseCard
          :name="course.courseName"
          :courseId="course.id"
          :address="course.address"
        />
      </div>
    </div>
    <div v-if="isRecent">
      <div v-for="(course, idx) in recentCourse.courseList" v-bind:key="idx">
        <CourseCard
          :name="course.courseName"
          :courseId="course.id"
          :address="course.address"
        />
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/common/Header";
import("@/assets/style/Main.css");
import myCourseApi from "@/api/mycourse.js";
import CourseCard from "@/views/course/CourseCard";

export default {
  name: "MyCourse",
  components: {
    Header,
    CourseCard,
  },
  data() {
    return {
      isRecent: false,
      userId: "",
      recentCourse: [],
      wishCourse: [],
    };
  },
  created() {
    this.userId = "test"; // for test
    this.getWishCourse(this.userId);
    this.getRecentCourse(this.userId);
  },
  methods: {
    async getWishCourse(userId) {
      let data = {
        type: "wish",
        userId: userId,
      };
      this.wishCourse = await myCourseApi.getCourseData(data, {});
      this.isRecent = false;
      console.log(this.wishCourse);
    },
    async getRecentCourse(userId) {
      let data = {
        type: "recent",
        userId: userId,
      };
      this.isRecent = true;
      this.recentCourse = await myCourseApi.getCourseData(data, {});
      console.log(this.recentCourse);
    },
  },
};
</script>

<style>
#space {
  height: 50px;
}

#main {
  width: 100%;
  max-width: 425px;
  top: 0;
  left: 0;
  right: 0;
  margin: auto;
  position: fixed;
  bottom: 0;
  background: #ffffff;
}
</style>
