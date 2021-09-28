<template>
  <div>
    <div class="default"></div>
  </div>
</template>

<script>
import axios from "axios";

import("@/assets/style/Main.css");

export default {
  name: "Main",
  components: {},
  data() {
    return {
      lat: "",
      lng: "",

      dong: "",
      userName: this.$store.getters.getUserName,
    };
  },
  methods: {
    geofind() {
      if (!("geolocation" in navigator)) {
        this.textContent = "Geolocation is not available.";
        return;
      }
      this.textContent = "Locating...";

      // get position
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          this.lat = pos.coords.latitude;
          this.lng = pos.coords.longitude;

          axios
            .get(
              "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=" +
                this.lng +
                "&y=" +
                this.lat,
              {
                headers: {
                  Authorization: "KakaoAK bacd72f58ac01490602415c683ad8c05",
                },
              }
            )
            .then((response) => {
              this.dong = response.data.documents[0].region_3depth_name;
              this.$store.commit("SET_USER_LOCATION", {
                lat: this.lat,
                lng: this.lng,
                dong: this.dong,
              });
            });
        },
        (err) => {
          this.textContent = err.message;
        }
      );
    },
  },
  created() {
    this.geofind();
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
