<template>
  <div>
    <Header :showArrow="false" message="우리 동네 산책로" id="navBar" />

    <div class="default">
      <div class="course-top">
        <el-row>
          <el-button
            type="primary"
            style="
              height: 10px;
              padding-left: 0px;
              padding-right: 0px;
              padding-bottom: 0px;
            "
          >
            거리순
          </el-button>
          <el-button
            type="primary"
            style="
              color: #727272;
              font-weight: 500;
              height: 10px;
              padding-left: 5px;
              padding-right: 5px;
              padding-bottom: 0px;
            "
          >
            |
          </el-button>
          <el-button
            type="primary"
            style="
              height: 10px;
              padding-left: 0px;
              padding-right: 0px;
              padding-bottom: 0px;
            "
          >
            인기순
          </el-button>
          <el-button
            type="primary"
            @click="showFilter = !showFilter"
            style="
              float: right;
              height: 10px;
              padding-left: 0px;
              padding-right: 0px;
              padding-bottom: 0px;
            "
          >
            {{ showFilter ? " 필터 OFF " : " 필터 ON " }}
            <i class="el-icon-s-operation" style="font-size: 13pt"></i>
          </el-button>
        </el-row>
      </div>
      <div
        v-show="showFilter"
        style="
          margin-bottom: 20px;
          margin-left: 10px;
          margin-right: 10px;
          padding-left: 15px;
          padding-right: 15px;
        "
      >
        <el-divider />
        <div class="block" style="margin-bottom: 20px">
          <h5>거리</h5>
          <el-slider
            v-model="filterDist"
            :min="3"
            :max="15"
            :step="3"
            :marks="kms"
            :show-tooltip="false"
          >
          </el-slider>
        </div>
        <div class="block" style="margin-bottom: 10px">
          <h5>시간</h5>
          <el-slider
            v-model="filterTime"
            :marks="times"
            :min="30"
            :max="180"
            :step="30"
            :show-tooltip="false"
          >
          </el-slider>
        </div>

        <el-row
          style="
            padding-top: 10px;
            margin-bottom: 10px;
            display: flex;
            justify-content: center;
          "
        >
          <el-button type="danger" size="small">검색</el-button
          ><el-button type="danger" size="small" @click="resetData"
            >초기화</el-button
          ></el-row
        >
        <el-divider />
      </div>
      <span style="font-weight: 700">{{ dong }}</span> 일대의 산책로 코스입니다.

      <div class="course-content">
        <CourseList :dong="dong" />
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/common/Header";
import CourseList from "@/views/course/CourseList";
import("@/assets/style/Main.css");

export default {
  name: "Course",
  components: {
    Header,
    CourseList,
  },
  data() {
    return {
      times: {
        30: "30분",
        60: "60분",
        90: "90분",
        120: "120분",
        150: "150분",
        180: "⬆",
      },
      kms: {
        3: "3km",
        6: "6km",
        9: "9km",
        12: "12km",
        15: "⬆ ",
      },
      filterTime: 180,
      filterDist: 15,
      filterKcal: 0,
      showFilter: false,
      dong: this.$store.getters.getDong,
    };
  },
  methods: {
    resetData() {
      this.filterTime = 180;
      this.filterDist = 15;
    },
  },
};
</script>

<style scoped>
.course-top {
  margin-bottom: 10px;
}

.course-content {
  margin-top: 20px;
  margin-bottom: 20px;
}

.el-button--primary:focus {
  border: 0px;
  font-weight: 700;
  color: #49ab76;
  background-color: #ffffff;
}

.el-button--primary:hover {
  border: 0px;
  font-weight: 700;
  color: #49ab76;
  background-color: #ffffff;
}

.el-button--primary {
  border: 0px;
  color: #6f7789;
  background-color: #ffffff;
}

.el-button--danger {
  border: 1px solid #dcdfe6;
  color: #6f7789;
  background-color: #ffffff;
}

.el-button--primary:active {
  border: 0px;
  font-weight: 700;
  color: #49ab76;
  background-color: #ffffff;
}
</style>
