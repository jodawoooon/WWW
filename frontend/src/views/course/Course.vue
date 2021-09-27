<template>
  <div>
    <Header :showArrow="true" message="우리 동네 산책로" id="navBar" />
    <div class="default">
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
