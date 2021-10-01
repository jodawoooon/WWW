<template>
  <div class="card" @click="goDetail()">
    <el-row style="display: flex; align-items: center">
      <el-col :span="20" style="text-align: left">
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
          {{ roundKm }}km | {{ $props.min }} | {{ $props.kcal }}kcal
        </p>
      </el-col>
      <el-col :span="4" style="text-align: center">
        <i
          v-if="this.$store.getters.getLoginUserInfo.userId"
          @click.stop="clickStar()"
          :class="[isLiked ? 'el-icon-star-on' : 'el-icon-star-off']"
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
  data() {
    return {
      userId: this.$store.getters.getLoginUserInfo.userId,
      isLiked: this.$props.isBookmarked,
      roundKm: Math.round(this.$props.km * 100) / 100,
    };
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
    isWish: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    // 산책로 세부 정보를 가져오기
    goDetail() {
      console.log(this.$props.courseId);
      console.log(this.$props.lat);
      console.log(this.$store.getters.getLoginUserInfo.userId);
      axios
        .get("/api/course/", {
          params: {
            courseId: this.$props.courseId,
            userId: this.$store.getters.getLoginUserInfo.userId,
          },
        })
        .then((res) => {
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
            isBookmarked: res.data.myLike,
          });
          console.log(this.$props.courseId + " " + this.$props.address);
        });
      router.push("/course/detail");
    },
    clickStar() {
      if (this.isLiked) {
        this.deleteLike();
      } else {
        this.createLike();
      }
    },
    createLike() {
      const req = {
        courseId: this.$props.courseId,
        userId: this.$store.getters.getLoginUserInfo.userId,
      };
      axios.post("/api/course/like", req, {}).then(() => {
        this.isLiked = !this.isLiked;
      });
    },
    deleteLike() {
      const req = {
        courseId: this.$props.courseId,
        userId: this.$store.getters.getLoginUserInfo.userId,
      };
      axios
        .delete("/api/course/like", {
          data: req,
        })
        .then(() => {
          if (this.$props.isWish) {
            console.log("refresh WishCourse");
            this.$emit(
              "refresh-wish-course",
              this.$store.getters.getLoginUserInfo.userId
            );
          } else {
            this.isLiked = !this.isLiked;
          }
        });
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
