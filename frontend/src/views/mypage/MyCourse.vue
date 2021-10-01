<template>
  <div id="main">
    <Header :showArrow="false" message="나의 산책 목록" id="navBar" />
    <div style="postion: fixed">
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
    </div>

    <div style="margin: 10px; text-algin: left; margin-top: 20px">
      <div v-if="!isRecent">
        <div v-for="(course, idx) in wishCourse.courseList" v-bind:key="idx">
          <div>
            <CourseCard
              @refresh-wish-course="getWishCourse"
              :isWish="true"
              :title="course.courseFlagName"
              :name="course.courseName"
              :courseId="course.courseId"
              :address="course.address"
              :km="course.courseLength"
              :min="course.time"
              :kcal="Math.round(course.timeInt * 60 * 0.06 * 10) / 10"
              :lat="course.latitude.toString()"
              :lng="course.longitude.toString()"
              :score="course.score"
              :detail="course.detail"
              :isBookmarked="true"
            />
          </div>
        </div>
      </div>
      <div v-if="isRecent">
        <div v-for="(course, idx) in recentCourse.courseList" v-bind:key="idx">
          <div>
            <ReviewCard
              @refresh-recent-course="getRecentCourse"
              :title="course.courseFlagName"
              :name="course.courseName"
              :courseId="course.courseId"
              :address="course.address"
              :km="course.courseLength.toFixed(2)"
              :min="timeText(course.time)"
              :kcal="Math.round(course.time * 60 * 0.06 * 10)"
              :lat="course.latitude.toString()"
              :lng="course.longitude.toString()"
              :myScore="course.myScore"
              :detail="course.detail"
              :isBookmarked="course.myLike"
            />
          </div>
        </div>
      </div>
    </div>
    <div id="space"></div>
  </div>
</template>

<script>
import Header from "@/components/common/Header";
import("@/assets/style/Main.css");
import myCourseApi from "@/api/mycourse.js";
import ReviewCard from "@/views/mypage/ReviewCard";
import CourseCard from "@/views/course/CourseCard";

//import router from "@/router/index.js";
import axios from "@/utils/axios.js";

export default {
  name: "MyCourse",
  components: {
    Header,
    ReviewCard,
    CourseCard,
  },
  data() {
    return {
      isRecent: true,
      curID: "",
      userId: this.$store.getters.getLoginUserInfo.userId,
      recentCourse: [],
      wishCourse: [],
    };
  },
  mounted() {
    this.$store.commit("SET_PREV_PAGE", "/user/mycourse");
    this.getRecentCourse(this.userId);
    this.$store.commit("SET_IS_NOT_INDEX");
    console.log(this.userId);
  },
  created() {
    this.getRecentCourse(this.userId);
  },
  methods: {
    sendReview(id) {
      axios
        .post("/review/", {
          courseId: id,
          score: this.rating,
          userId: this.userId,
        })
        .then((response) => {
          this.rating = 1;
          this.dialogVisible = false;
          console.log(response);
        });
    },
    clickReview(id) {
      this.curID = id;
      this.dialogVisible = true;
    },
    setRating(rating) {
      console.log(rating);
    },

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
    timeText(time) {
      var t = parseInt(time);
      var text = "";
      if (t >= 3600) text += parseInt(t / 3600) + "시간 ";
      if (t >= 60) text += parseInt((t % 3600) / 60) + "분 ";
      text += parseInt((t % 3600) % 60) + "초";
      return text;
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
  bottom: 0;
  background: #ffffff;
}
</style>
