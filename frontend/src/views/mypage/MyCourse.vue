<template>
  <div id="main">
    
    <Header :showArrow="false" message="우리 동네 산책로" id="navBar" />
    <div id="space"></div>
    
    <v-card >
      <v-tabs centered fixed-tabs slider-color="red">
        <v-tab v-on:click="getRecentCourse(userId)" style="font-size:20px;color:gray;font-weight:bold;">최근 코스</v-tab>
        <v-tab v-on:click="getWishCourse(userId)" style="font-size:20px;color:gray;font-weight:bold;">관심 코스</v-tab>
      </v-tabs>
    </v-card>

    <div>관심 코스 :<br>{{wishCourse}}</div>
    <br>
    <br>
    <div>최근 코스 :<br>{{recentCourse}}</div>

  </div>
</template>

<script>
import Header from "@/components/common/Header";
import("@/assets/style/Main.css");
import myCourseApi from "@/api/mycourse.js";

export default {
    name: "MyCourse",
    components: {
        Header,
    },
    data(){
        return{
            userId: "", 
            recentCourse:[],
            wishCourse:[],
        };
    },
    created(){
        this.userId="test"; // for test
        this.getWishCourse(this.userId);
        this.getRecentCourse(this.userId);
    },
    methods:{
        async getWishCourse(userId){
            let data = {
                type: "wish",
                userId: userId,
            };
            this.wishCourse = await myCourseApi.getCourseData(data, {});
            console.log(this.wishCourse);
        },
        async getRecentCourse(userId){
            let data = {
                type: "recent",
                userId: userId,
            };
            this.recentCourse = await myCourseApi.getCourseData(data, {});
            console.log(this.recentCourse);
        },

    },
}
</script>

<style>
#space {
  height: 50px;
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
  background: #FFFFFF;
}

</style>