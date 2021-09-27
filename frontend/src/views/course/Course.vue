<template>
  <div>
    <Header :showArrow="false" message="우리 동네 산책로" id="navBar" />

    <div class="default">
      <div>
        <el-row>
          <button
            style="
              float: left;
              color: #727272;

              padding-left: 0px;
              padding-right: 0px;
              padding-bottom: 0px;
            "
          >
            거리순
          </button>
          <div
            style="
              float: left;
              color: #727272;
              font-weight: 500;

              padding-left: 10px;
              padding-right: 10px;
              padding-bottom: 0px;
            "
          >
            |
          </div>
          <button
            style="
              float: left;

              color: #727272;
              padding-left: 0px;
              padding-right: 0px;
              padding-bottom: 0px;
            "
          >
            인기순
          </button>
        </el-row>
      </div>
      <span style="font-weight: 700">{{ dong }}</span> 일대의 산책로 코스입니다.
    </div>
  </div>
</template>

<script>
import Header from "@/components/common/Header";
import("@/assets/style/Main.css");

export default {
  name: "Course",
  components: {
    Header,
  },
  data() {
    return {
      dong: this.$store.getters.getDong,
    };
  },
};
</script>
<style scoped>
#button {
  border-width: 0px;
}
</style>
