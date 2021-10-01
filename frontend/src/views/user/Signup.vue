<template>
  <div class="register">
      <div class="register-content">

          <p>기본 정보를 입력하고 <br> <span class="title">WWW</span>를 이용해보세요🏃‍♀️🏃‍♂️</p>

      </div>
      <div class="form-content" style="margin-right:30px; margin-left:30px;">
        <el-form :model="form" ref="form" label-position=top; class="demo-ruleForm">
          <el-form-item
            label="닉네임"
            prop="nickname"
            :rules="[
              { required: true, message: '내용을 입력해주세요.'},
            ]"
            style="width:100%"
          >
            <el-input v-model="form.nickname" placeholder="닉네임을 기재해주세요" ></el-input>
          </el-form-item>
            <el-row>
                <el-col :span="24">
                  <el-row>
                    <el-col :span="8">
                      <el-form-item
                        label="시/도"
                        prop="sido"
                        :rules="[
                          { required: true, message: '시/도를 선택해주세요.'},
                        ]"
                        style="width:100%"
                      >
                        <el-select v-model="form.sido" placeholder="시/도" @change="getGugunList(form.sido)">
                          <el-option
                            v-for="(sido, idx) in sidoList"
                            :key="idx"
                            :label="sido.name"
                            :value="sido.id">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item
                      label="구/군"
                      :rules="[
                          { required: true, message: '구/군을 선택해주세요.'},
                        ]"
                        prop="gugun"
                        style="width:100%"
                      >
                        <el-select v-model="form.gugun" placeholder="구/군" @change="getDongList(form.gugun)">
                          <el-option
                            v-for="(gugun, idx) in gugunList"
                            :key="idx"
                            :label="gugun.name"
                            :value="gugun.id">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item
                      label="동"
                      :rules="[
                          { required: true, message: '동을 선택해주세요.'},
                        ]"
                        prop="dong"
                        style="width:100%"
                      >
                        <el-select v-model="form.dong" placeholder="동" >
                          <el-option
                            v-for="(dong, idx) in dongList"
                            :key="idx"
                            :label="dong.name"
                            :value="dong.id">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
            </el-row>
          <el-button type="primary" @click="submitForm('form')" size="medium" style="">완료</el-button>
        </el-form>
        
      </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
import VueCookies from 'vue-cookies';

 export default {
    data() {
      return {
        form: {
          nickname: '',
          sido: '',
          gugun: '',
          dong : '',
        },
        userInfo:{
          userId:'',
          nickname:'',
          name:'',
          sido:'',
          gugun:'',
          dong:'',
          refreshToken:'',
          refreshTokenExpire:''
        },
        sidoList:[],
        gugunList:[],
        dongList:[],
      };
    },
    methods: {
      getSidoList(){
        axios
        .get("/info/sido")
        .then((res) => {
          console.log(res.data);
          //this.$store.commit("SET_SIDO_LIST", res.data);
          this.sidoList = res.data.sidoList;
        })
      },
      getGugunList(sidoCode){
        axios
        .get("/info/gugun/" + sidoCode)
        .then((res) =>{
          console.log(res)
          //this.$store.commit("SET_GUGUN_LIST",res.data);
          this.gugunList = res.data.gugunList;
          this.form.gugun = ''
        })
      },
      getDongList(gugunCode){
        axios
        .get("/info/dong/" + gugunCode)
        .then((res) =>{
          //this.$store.commit("SET_DONG_LIST",res.data);
          this.dongList = res.data.dongList;
          this.form.dong = ''
        })
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.register(this.form);
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      register(data){
        this.userInfo.userId = this.$store.state.loginUserInfo.userId;
        this.userInfo.name = this.$store.state.loginUserInfo.name;
        this.userInfo.nickname = data.nickname;
        this.userInfo.city = data.sido;
        this.userInfo.gu = data.gugun;
        this.userInfo.dong = data.dong;
        this.userInfo.refreshToken = this.$store.state.tokens.refreshToken;
        this.userInfo.refreshTokenExpire = this.$store.state.tokens.refreshTokenExpire;

        axios
          .post("/info/register", this.userInfo)
          .then((result)=>{
              VueCookies.set("accessToken", this.$store.state.tokens.accessToken, this.$store.state.tokens.accessTokenExpire)
              VueCookies.set("userId",this.$store.state.loginUserInfo.userId)
              this.$store.commit("SET_IS_LOGIN", {
                isLogin : true,
                isLogout : false
              });
              console.log(result);
              alert("회원가입 완료")
              this.$router.push({name: "Main"});
          })
          .catch((err)=>{
            console.log(err);
          })
      }
    },
    created(){
      this.getSidoList();
    }
  }
</script>

<style scoped>
.register-content{
    text-align: left;
    margin : 50px 0px 10px 30px;
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
  margin-top : 50px;
  background-color:#EE684A;
  width: 100px;
}
.el-button:hover{
  background-color: #EE684A;
  border-color: #EE684A;

}
.form-content{
  margin-top:50px;
  text-align: center;
}
.el-select{
  margin-left:3px;
  margin-right:3px;
}

</style>