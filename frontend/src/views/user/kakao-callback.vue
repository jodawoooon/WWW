<template>
  <div></div>
</template>

<style></style>

<script>
import axios from "axios";

export default {
  name: 'Signup',
  created(){
    this.create();
  },
  data() {
    return {
      code : '',
    }
  },
  methods: {
    create(){
      this.code = this.$route.query.code;
      this.getToken();
    },
    login(){


    },
    getToken(){
      axios
      .get("/kakao/login?code=" + this.code)
      .then((res)=>{
        console.log(res);
      })
      
    },
  }
}
</script>


