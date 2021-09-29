<template>
  <div style="padding-bottom: 90px">
    <Header :showArrow="false" message="나의 산책 분석" id="navBar" />

    <div style="background-color: #49ab76; color: #ffffff">
      <div id="space"></div>

      <p style="text-align: left; margin-left: 25px">
        <b>{{ userName }}</b>
        님이 <br />
        <span style="color: #ee684a; font-weight: 700">WWW</span>와 함께
        <span style="color: #ee684a; font-weight: 700">걸은</span> 시간 ⏱
      </p>

      <br />

      <p
        class="font-weight-black"
        style="font-size: 35px; text-align: left; margin-left: 25px"
      >
        <b>{{ totalTime }} 🏃‍♀️🏃‍♂️</b>
      </p>

      <br /><br />
    </div>
    <v-card>
      <v-tabs centered fixed-tabs slider-color="red">
        <v-tab
          v-on:click="init('week')"
          style="font-size: 20px; color: gray; font-weight: bold"
          >주간</v-tab
        >
        <v-tab
          v-on:click="init('month')"
          style="font-size: 20px; color: gray; font-weight: bold"
          >월간</v-tab
        >
      </v-tabs>
    </v-card>
    <br /><br />

    <div style="font-size: 20px">
      <div v-if="curType == 'week'">
        <b>이번주🚩</b><br />{{ prevDay }} ~ {{ curDay }}
      </div>
      <div v-if="curType == 'month'">
        <b>이번달🚩</b><br />{{ prevDay }} ~ {{ curDay }}
      </div>
    </div>
    <br />

    <div class="data">
      <div v-if="curType == 'week'">
        <b style="float: left; margin-left: 35px">주간 누적</b>
        <span style="float: right; margin-right: 35px">{{ sumTimeText }}</span>
        <br />
      </div>

      <div v-if="curType == 'month'">
        <b style="float: left; margin-left: 35px">월간 누적</b>
        <span style="float: right; margin-right: 35px">{{ sumTimeText }}</span>
        <br />
      </div>

      <div>
        <b style="float: left; margin-left: 35px">하루 평균</b>
        <span style="float: right; margin-right: 35px">{{ avgTimeText }}</span>
        <br />
      </div>
    </div>
    <br />
    <div>
      총 소모 칼로리 :
      <b style="font-size: 25px"> {{ userData.sumCalorie }} </b>kcal
      <br />
    </div>
    <div style="font-size: 20px">
      <br />
      <img
        v-if="timeDiff < 0"
        src="https://cdn-icons-png.flaticon.com/512/599/599426.png"
        alt=""
        width="80px"
      />
      <img
        v-if="timeDiff >= 0"
        src="https://cdn-icons-png.flaticon.com/512/983/983079.png"
        alt=""
        width="80px"
      />
      <br /><br />

      <div v-if="curType == 'week'">
        <b>이번 주</b> 는 <b>저번 주</b>보다 하루 평균
      </div>
      <div v-if="curType == 'month'">
        <b>이번 달</b>은 <b>저번 달</b> 보다 하루 평균
      </div>
      <span class="font-weight-black">{{ timeDiffText }}</span>
      <span v-if="timeDiff < 0"> 만큼 덜 걸었어요</span>
      <span v-if="timeDiff >= 0"> 만큼 더 걸었어요</span>
      <div style="font-size: 10pt; margin-top: 3px" v-if="timeDiff < 0">
        내일은 더 걸어봐요💪
      </div>
      <div style="font-size: 10pt; margin-top: 3px" v-if="timeDiff >= 0">
        참 잘했어요🤩👏
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/common/Header";
import userApi from "@/api/user.js";

export default {
  name: "Walk",
  components: {
    Header,
  },
  data() {
    return {
      userId: this.$store.getters.getLoginUserInfo.userId,
      curType: "",
      userName: this.$store.getters.getLoginUserInfo.nickname,
      userInfo: [],
      totalTime: 0,
      userData: [],
      timeDiff: 0,
      prevDay: "",
      curDay: "",

      timeDiffText: "",
      avgTimeText: "",
      sumTimeText: "",
    };
  },
  mounted() {
    this.$store.commit("SET_IS_NOT_INDEX");
  },
  created() {
    // this.userId = "test"; // for test
    this.curType = "week";
    this.init(this.curType);
  },
  methods: {
    init(type) {
      this.curType = type;

      let today = new Date();
      this.curDay = this.getDateStr(today);

      if (type == "week") {
        today.setDate(today.getDate() - today.getDay() + 1);
        this.prevDay = this.getDateStr(today);
      } else {
        today.setDate(1);
        this.prevDay = this.getDateStr(today);
      }
      this.getUserInfo(this.userId);
      this.getTotalTime(this.userId);
      this.getUserData(this.userId, type);
    },

    async getUserInfo(userId) {
      let data = {
        type: "mypage",
        userId: userId,
      };
      this.userInfo = await userApi.getWalkData(data, {});
    },

    async getTotalTime(userId) {
      let data = {
        type: "totaltime",
        userId: userId,
      };
      let totalTime = await userApi.getWalkData(data, {});

      if (totalTime.time == 0) {
        this.totalTime = "0시간 0분 0초";
      } else {
        this.totalTime =
          parseInt(totalTime.time / 3600) +
          "시간 " +
          parseInt((totalTime.time % 3600) / 60) +
          "분 " +
          parseInt((totalTime.time % 3600) % 60) +
          "초";
      }
      console.log(totalTime);
      console.log(parseInt((totalTime.time % 3600) % 60));
      console.log(this.totalTime);
    },

    async getUserData(userId, returnType) {
      let data = {
        type: "time",
        userId: userId,
        returnType: returnType,
      };
      this.userData = await userApi.getWalkData(data, {});

      this.timeDiff = this.userData.avgTime - this.userData.prevAvgTime;
      if (this.timeDiff < 0) this.timeDiff *= -1;
      this.timeDiffText =
        parseInt(this.timeDiff / 3600) +
        "시간 " +
        parseInt((this.timeDiff % 3600) / 60) +
        "분 " +
        parseInt((this.timeDiff % 3600) % 60) +
        "초";

      this.sumTimeText =
        parseInt(this.userData.sumTime / 3600) +
        "시간 " +
        parseInt((this.userData.sumTime % 3600) / 60) +
        "분 " +
        parseInt((this.userData.sumTime % 3600) % 60) +
        "초";

      this.avgTimeText =
        parseInt(this.userData.avgTime / 3600) +
        "시간 " +
        parseInt((this.userData.avgTime % 3600) / 60) +
        "분 " +
        parseInt((this.userData.avgTime % 3600) % 60) +
        "초";

      this.timeDiff = this.userData.avgTime - this.userData.prevAvgTime;
    },

    getDateStr(myDate) {
      var year = myDate.getFullYear();
      var month = myDate.getMonth() + 1;
      var day = myDate.getDate();

      month = month < 10 ? "0" + String(month) : month;
      day = day < 10 ? "0" + String(day) : day;

      return year + "/" + month + "/" + day;
    },
  },
};
</script>

<style scoped>
#space {
  height: 80px;
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

.data {
  font-size: 25px;
}
.data div {
  margin: 10px 0 0 10px;
}
</style>
