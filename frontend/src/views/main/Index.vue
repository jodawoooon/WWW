<template>
  <div>
    <div
      class="default"
      style="
        padding-top: 20%;
        margin-bottom: 10px;
        display: flex;
        justify-content: center;
      "
    >
      <img src="../../assets/test.png" width="250px" />
    </div>
    <el-row
      style="
        margin-top: 140px;

        display: flex;
        justify-content: center;
      "
    >
      <el-button
        type="danger"
        style="border: 4px solid #49ab76; width: 80%; background-color: #49ab76"
        @click="goMain()"
        >시작하기</el-button
      ></el-row
    >
    <span style="font-size: 9pt"
      >위치 수집에 동의하셔야 서비스 이용이 가능합니다</span
    >
  </div>
</template>

<script>
import router from "@/router/index.js";
import Swal from "sweetalert2";

import("@/assets/style/Main.css");

export default {
  name: "Main",
  components: {},
  data() {
    return {
      dialogVisible: false,
      dong: "",
      isAgree: false,
    };
  },
  mounted() {
    this.$store.commit("SET_IS_INDEX");
  },
  methods: {
    goMain() {
      this.geofind();
    },
    geofind() {
      if (!("geolocation" in navigator)) {
        return;
      }

      // get position
      navigator.geolocation.getCurrentPosition(
        () => {
          this.$store.commit("SET_IS_AGREE");
          router.push("/main");
        },
        (err) => {
          Swal.fire({
            width: 250,
            titleSize: 10,
            title:
              "위치 정보 수집을 허용하셔야 <br/> 서비스 이용이 가능합니다🏃‍♂️",
          });
          this.$store.commit("SET_IS_NOT_AGREE");
          this.textContent = err.message;
        }
      );
    },
  },
};
</script>

<style scoped>
.main-top {
  height: 200px;
}

.main-box {
  margin-bottom: 15px;
  height: 90px;
  background: #f6f6f6;
  border-radius: 20px;
}
</style>
