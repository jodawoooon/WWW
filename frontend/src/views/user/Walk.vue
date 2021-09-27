<template>
    <div id="main">
        
        <ComponentNav title="나의 산책 분석"></ComponentNav>
        <div id="space"></div>
        
        <p style="text-align:left;margin-left:25px;">
            <b>{{userInfo.nickName}}</b>님이
            <br>(서비스 이름)와 함께 걸은 시간
        </p>
        <p class="font-weight-bold" style="font-size:35px;text-align:left;margin-left:25px;">{{totalTime}}</p>

        <br>
        <v-card>
            <v-tabs centered fixed-tabs
          slider-color="red">
                <v-tab v-on:click="init('week')">이번주</v-tab>
                <v-tab v-on:click="init('month')">이번달</v-tab>
            </v-tabs>
        </v-card>
        <br><br>

        <div v-if="curType=='week'"><b>이번주</b><br>{{prevDay}} ~ {{curDay}}</div>
        <div v-if="curType=='month'"><b>이번달</b><br>{{prevDay}} ~ {{curDay}}</div>
        <br>
        <div v-if="curType=='week'">
            <span style="float:left;margin-left:35px;">주간 누적</span>
            <span style="float:right;margin-right:35px;">{{userData.sumTime}}</span>
        </div>

        <div v-if="curType=='month'">
            <span style="float:left;margin-left:35px;">월간 누적</span>
            <span style="float:right;margin-right:35px;">{{userData.sumTime}}</span>
        </div>
        
        <div>하루 평균 : {{ Math.round(userData.avgTime) }}</div>
        <div>총 소모 칼로리 : {{ userData.sumCalorie }}</div>

        <br>
        <div v-if="curType=='week'">이번주 일일 평균 산책 시간은<br>저번주 평균 산책 시간보다</div>
        <div v-if="curType=='month'">이번달 일일 평균 산책 시간은<br>저번달 평균 산책 시간보다</div>
        <div v-if="timeDiff<0">{{timeDiffStr}} 만큼 덜 걸었습니다</div>
        <div v-if="timeDiff>=0">{{timeDiffStr}} 만큼 더 걸었습니다</div>

        <div id="space"></div>
        <div>userInfo:{{userInfo}}</div><br>
        <div>totalTime:{{totalTime}}</div><br>
        <div>userData:{{userData}}</div>
    </div>

</template>

<script>
import ComponentNav from "@/components/common/ComponentNav";
import userApi from "@/api/user.js"

export default {
    name: 'Walk',
    components: {
        ComponentNav
    },
    data() {
        return{
            userId : "",
            curType :"",
            userInfo :[],  
            totalTime:0,
            userData :[],
            timeDiff : 0,
            timeDiffStr : "",
            prevDay:"",
            curDay:"",
        }
    },
    created(){
        this.userId = "test"; // for test
        this.curType = "week"
        this.init(this.curType);
    },
    methods:{
        init(type){
            this.curType=type;

            let today = new Date();
            this.curDay = this.getDateStr(today);

            if(type == "week"){
                today.setDate(today.getDate() - today.getDay() +1);
                this.prevDay = this.getDateStr(today);
            }
            else{
                today.setDate(1);
                this.prevDay = this.getDateStr(today);
                }
            this.getUserInfo(this.userId);
            this.getTotalTime(this.userId);
            this.getUserData(this.userId, type);
        },

        async getUserInfo(userId) {
            let data = {
                type : "mypage",
                userId: userId,
            }
            this.userInfo = await userApi.getWalkData(data,{});

        },

        async getTotalTime(userId){
            let data={
                type : "totaltime",
                userId : userId,
            }
            let totalTime = await userApi.getWalkData(data,{});
            this.totalTime = parseInt(totalTime.time/3600)
            + "시간 " +
            parseInt( (totalTime.time%3600)/60 )
            + "분 " +
            parseInt( (totalTime.time%3600)%60 )
            + "초";
            
        },

        async getUserData(userId, returnType){
            let data={
                type : "time",
                userId : userId,
                returnType : returnType,
            }
            this.userData = await userApi.getWalkData(data,{});
            
            this.timeDiff = this.userData.avgTime - this.userData.prevAvgTime;
            if(this.timeDiff<0)this.timeDiff*=-1;
            this.timeDiffStr = parseInt(this.timeDiff/3600)
            + "시간 " +
            parseInt( (this.timeDiff%3600)/60 )
            + "분 " +
            parseInt( (this.timeDiff%3600)%60 )
            + "초";
        },

        getDateStr(myDate){
            var year = myDate.getFullYear();
            var month = (myDate.getMonth() + 1);
            var day = myDate.getDate();
            
            month = (month < 10) ? "0" + String(month) : month;
            day = (day < 10) ? "0" + String(day) : day;
            
            return  (year + '/' + month + '/' + day );
        },
    },
}
</script>

<style scoped>
#space{
    height:120px;
}

#main {
  width: 100%;
  max-width: 425px;
  top:0;
  left: 0;
  right: 0;
  margin: auto;
  position: fixed;
  bottom: 0;
  background: #CCCCCC;
}
</style>
