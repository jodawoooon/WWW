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

    <div>
      <div v-if="!isRecent">
        <div
          v-for="(course, idx) in this.wishCourse.courseList"
          v-bind:key="idx"
        >
          <el-dialog
            :visible.sync="dialogVisible"
            width="70%"
            center
            :show-close="false"
          >
            <div style="text-align: center">
              <star-rating
                :increment="0.5"
                v-model="rating"
                @current-rating="setRating"
                :show-rating="false"
                :star-size="40"
              >
              </star-rating>
            </div>
            <div
              style="
                font-size: 15pt;
                text-align: center;
                font-weight: 700;
                padding-top: 20px;
              "
            >
              자유공원 - 갈산둘레길
            </div>
            <div
              style="
                font-size: 9pt;
                text-align: center;

                padding-top: 10px;
              "
            >
              걸어보시니 어떠셨나요? <br />솔직한 별점을 남겨주세요! <br />
            </div>
            <div slot="footer" class="dialog-footer" style="padding-top: 0px">
              <el-button
                type="danger"
                style="
                  border: 4px solid #49ab76;
                  width: 80%;
                  background-color: #49ab76;
                  border-radius: 30px;

                  padding-top: 10px;
                  padding-bottom: 10px;
                "
                @click="sendReview()"
                >✨ 제출하기 ✨</el-button
              ><br />
              <el-button
                type="danger"
                style="
                  border: 4px solid #ffffff;
                  width: 80%;
                  background-color: #ffffff;
                  color: #49ab76;
                  border-radius: 30px;
                  margin-top: 10px;
                  padding-top: 10px;
                  padding-bottom: 10px;
                "
                @click="dialogVisible = false"
                >🙅‍♂️ 다음에 할게요 🙅‍♀️</el-button
              >
            </div>
          </el-dialog>
          <div @click="clickReview(course.courseId)">
            <CourseCard
              :title="course.courseFlagName"
              :name="course.courseName"
              :courseId="course.courseId"
              :address="course.address"
              :km="course.courseLength"
              :min="course.time"
              :kcal="Math.round(course.timeInt * 60 * 0.06 * 10) / 10"
              :lat="course.latitude"
              :lng="course.longitude"
              :score="course.score"
              :detail="course.detail"
              :isBookmarked="course.myLike"
            />
          </div>
        </div>
      </div>
      <div v-if="isRecent">
        <div
          v-for="(course, idx) in this.recentCourse.courseList"
          v-bind:key="idx"
        >
          <div>
            <CourseCard
              :title="course.courseFlagName"
              :name="course.courseName"
              :courseId="course.courseId"
              :address="course.address"
              :km="course.courseLength"
              :min="timeText(course.time)"
              :kcal="course.calorie"
              :lat="course.latitude"
              :lng="course.longitude"
              :score="course.score"
              :detail="course.detail"
              :isBookmarked="course.myLike"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/common/Header";
import("@/assets/style/Main.css");
import myCourseApi from "@/api/mycourse.js";
import CourseCard from "@/views/mypage/ReviewCard";
import StarRating from "vue-star-rating";
//import router from "@/router/index.js";

export default {
  name: "MyCourse",
  components: {
    Header,
    CourseCard,
    StarRating,
  },
  data() {
    return {
      curID: "",
      dialogVisible: true,
      rating: 0,
      isRecent: true,
      userId: this.$store.getter.getLoginUserInfo.userId,
      recentCourse: [],
      wishCourse: [],
    };
  },
  mounted() {
    this.$store.commit("SET_PREV_PAGE", "/user/mycourse");
    // this.getWishCourse(this.userId);
    this.getRecentCourse(this.userId);
    this.$store.commit("SET_IS_NOT_INDEX");
    console.log(this.userId);
  },
  created() {
    // this.userId = "test"; // for test

    //this.getWishCourse(this.userId);
    this.getRecentCourse(this.userId);

    // if(this.userId == ""){
    //   alert("로그인 이후 이용해주세요");
    //   router.push("/main");
    // }
  },
  methods: {
    async sendReview() {
      let data = {
        courseId: this.curId,
        score: this.rating,
        type: "wish",
        userId: this.userId,
      };
      await myCourseApi.getCourseData(data, {});
      this.rating = 0;
      this.dialogVisible = false;
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
