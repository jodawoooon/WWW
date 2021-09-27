<template>
  <div>
    <!-- 여기서 코스 리스트 가져와서 CourseCard에 props로 넘겨주기-->
    <CourseCard
      title="무슨무슨무슨"
      address="어쩌구저쩌구"
      :km="5"
      :min="120"
      :kcal="100"
      :isBookmarked="true"
    />
  </div>
</template>

<script>
import CourseCard from "@/views/course/CourseCard";

export default {
  name: "CourseList",
  props: ["dong"],
  components: {
    CourseCard,
  },
};
</script>

<style scoped></style>
