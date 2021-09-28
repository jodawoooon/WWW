<template>
    <div id="main">
        <ComponentNav title="나의 산책 분석"></ComponentNav>

        <div id="space"></div>
        내코스

        <br>
    </div>
</template>

<script>
import ComponentNav from "@/components/common/ComponentNav";
//import myCourseApi from "@/api/mycourse.js";

// /mypage/recent?userId=?
// /mypage/wish?userId=?
export default {
    name: "MyCourse",
    components: {
        ComponentNav,
    },
    data(){
        return{
            userId: "",

        };
    },
    created(){
        this.userId="test";
    },
    methods:{

    },
}
</script>

<style>
#space {
  height: 80px;
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
  background: #cccccc;
}

</style>