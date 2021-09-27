<template>
  <div>
    <Header :showArrow="true" message="우리 동네 산책로" id="navBar" />
  </div>
</template>

<script>
import Header from "@/components/common/Header";

import("@/assets/style/Main.css");

export default {
  name: "CourseDetail",
  components: {
    Header,
  },
  data() {
    return {};
  },
};
</script>

<style scoped></style>
