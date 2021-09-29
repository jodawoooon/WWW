<template>
  <div class="card" @click="goDetail()">
    <el-row style="display: flex; align-items: center">
      <el-col :span="20">
        <p
          class="title"
          style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap"
        >
          {{ $props.title }}
          <span v-if="$props.title !== $props.name">- {{ $props.name }}</span>
        </p>
        <p class="content">
          <i class="el-icon-location" style="color: #ee684a" />{{
            $props.address
          }}
        </p>
        <p class="content">
          {{ $props.km }}km | {{ $props.min }} | {{ $props.kcal }}kcal
        </p>
      </el-col>
      <el-col :span="4" style="text-align: center">
        <i
          :class="[
            $props.isBookmarked ? 'el-icon-star-on' : 'el-icon-star-off',
          ]"
          style="font-size: 25pt; color: #ee684a"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import router from "@/router/index.js";
import axios from "axios";

export default {
  name: "CourseCard",
  dat() {
    return{
      userId : this.$store.getters.getLoginUserInfo.userId
    }
  },
  props: {
    title: {
      type: String,
      default: "title",
    },
    name: {
      type: String,
      defalut: "name",
    },
    courseId: {
      type: Number,
      default: 0,
    },
    address: {
      type: String,
      default: "address",
    },
    km: {
      type: Number,
      default: 0,
    },
    min: {
      type: String,
      default: "min",
    },
    kcal: {
      type: Number,
      default: 0,
    },
    lat: {
      type: String,
      default: "37.4265296",
    },
    lng: {
      type: String,
      default: "126.986664",
    },
    detail: {
      type: String,
      default: "test",
    },
    isBookmarked: {
      type: Boolean,
      default: false,
    },

  },
  methods: {
    // 산책로 세부 정보를 가져오기
    goDetail() {
      console.log(this.$props.courseId);
      console.log(this.$props.lat);

      console.log(this.$store.getLoginUserInfo.userId);
      axios.get("/api/course/",{
        params :{
          courseId : this.$props.courseId,
          userId : this.$store.getLoginUserInfo.userId
        }
      }).then((res) =>{
        console.log(res);
        this.$store.commit("SET_CUR_COURSE", {
          id: this.$props.courseId,
          title:
            this.$props.title != this.$props.name
              ? this.$props.title + "-" + this.$props.name
              : this.$props.title,
          address: this.$props.address,
          lat: this.$props.lat,
          lng: this.$props.lng,
          score: this.$props.score,
          distance: this.$props.km,
          time: this.$props.min,
          kcal: this.$props.kcal,
          detail: this.$props.detail,
          cafe: res.data.cafeList,
          conv: res.data.convList,
        });
        console.log(this.$props.courseId+" "+this.$props.address);
      })

      router.push("/course/detail");
    },
  },
};
</script>

<style scoped>
.card {
  margin-bottom: 30px;
  height: 90px;
  background: #f6f6f6;
  border-radius: 20px;
  padding: 15px;
}

.title {
  font-size: 15pt;
  font-weight: 600;
}

.content {
  font-size: 11pt;
  color: #6f7789;
}
</style>
