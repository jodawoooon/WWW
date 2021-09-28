<template>
  <div class="register">
      <div class="register-content">
        <div style="top-margin: 50px">
          <p>기본 정보를 입력하고 <br> <span class="title">WWW</span>를 이용해보세요🏃‍♀️🏃‍♂️</p></div>
      </div>
      <div class="form-content" style="margin-right:30px; margin-left:30px;">
        <el-form :model="form" ref="form" label-position=top; class="demo-ruleForm">
          <el-form-item
            label="이름"
            prop="name"
            :rules="[
              { required: true, message: '이름을 입력해주세요.'},
            ]"
            style="width:100%"
          >
            <el-input v-model="form.name" placeholder="실명을 기재해주세요" ></el-input>
          </el-form-item>
          <el-button type="primary" @click="submitForm('form')" size="medium" style="">완료</el-button>
        </el-form>
      </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
 export default {
    data() {
      return {
        form: {
          name: ''
        },
        userInfo:{
          userId:'',
          nickname:'',
          name:'',
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!' + this.form.name);
            this.register(this.form);
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      register(data){
        this.userInfo.userId = this.$store.state.loginUserInfo.userId;
        this.userInfo.nickname = this.$store.state.loginUserInfo.nickname;
        this.userInfo.name = data.name;

        console.log(this.$store.state.loginUserInfo.userId)

        axios
          .post("/info/register", this.userInfo)
          .then((result)=>{
              console.log(result);
              alert("회원가입 완료")
              this.$router.push({name: "Main"});
          })
          .catch((err)=>{
            console.log(err);
          })
      }
    }
  }
</script>

<style scoped>
.register-content{
    text-align: left;
    margin : 30% 0% 5% 10%;
}
.register-content h2{
  font-weight: bold;
}
.title{
    font-weight: bold;
}

.register{
  margin : auto;
}
.el-button{
  margin-top : 20px;
  background-color:#EE684A;
  width: 100px;
}
.el-button:hover{
  background-color: #EE684A;
  border-color: #EE684A;

}
.form-content{
  margin-top:100px;
  text-align: center;
}
</style>