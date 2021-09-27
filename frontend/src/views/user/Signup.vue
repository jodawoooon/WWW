<template>
  <el-form
    ref="ruleForm"
    :model="ruleForm"
    :rules="rules"
    label-width="120px"
    class="demo-ruleForm"
  >
    <el-form-item label="Activity name" prop="name">
      <el-input v-model="ruleForm.name"></el-input>
    </el-form-item>
    <el-form-item label="Activity zone" prop="region">
      <el-select v-model="ruleForm.region" placeholder="Activity zone">
        <el-option label="Zone one" value="shanghai"></el-option>
        <el-option label="Zone two" value="beijing"></el-option>
      </el-select>
      <el-select v-model="ruleForm.region" placeholder="Activity zone">
        <el-option label="Zone one" value="shanghai"></el-option>
        <el-option label="Zone two" value="beijing"></el-option>
      </el-select>
      <el-select v-model="ruleForm.region" placeholder="Activity zone">
        <el-option label="Zone one" value="shanghai"></el-option>
        <el-option label="Zone two" value="beijing"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')"
        >완료</el-button
      >
    </el-form-item>
  </el-form>
</template>

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
      ruleForm: {
        name: '',
        city:'',
        gu:'',
        dong: ''
      },
      rules: {
        name: [
          {
            required: true,
            message: 'Please input Your name',
            trigger: 'blur',
          },
          {
            min: 1,
            max: 10,
            message: '이름은 필수 입력값입니다.',
            trigger: 'blur',
          },
        ],
        city : [
          {
            required: true,
            message: 'Please select City',
            trigger: 'change',
          },
        ],
      },
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
      .get("/login?code=" + this.code)
      .then((res)=>{
        console.log(res);
      })
      
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!')
          axios
            .post("/register", {
              userid: this.userId,
              name: this.userName,
              email: this.userEmail,
              userpw: this.userPassword,
              tel: this.userTel,
              address: this.userAddress,
              userclsf: this.userClsf,
            })
            .then(({ data }) => {
              console.log("RegisterVue: data : ");
              console.log(this.userClsf);
              console.log(data);

              this.$alertify.success("회원가입을 축하합니다!");
              this.$router.push("/login");
            })
            .catch((error) => {
              console.log("RegisterVue: error : ");
              console.log(error);
              if (error.response.status == "404") {
                this.$alertify.error("Opps!! 서버에 문제가 발생했습니다.");
                
              }
            });
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
  },
}
</script>