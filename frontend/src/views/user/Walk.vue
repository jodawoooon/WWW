<template>
    <div>
        
        <ComponentNav title="나의 산책 분석"></ComponentNav>
        <div id="space"></div>
        <div><strong>{{userInfo.nickName}}</strong>님이<br>
        xx와 함께 걸은 시간</div>
        <div>{{totalTime}}</div>
        <br>
        <v-btn elevation="2">ee</v-btn>
        <v-btn elevation="2">ee</v-btn>
        <form action="">
            <button type="button">asdf</button>
        </form>

        <div v-if="curType=='week'">이번주<br>{{prevDay}} ~ {{curDay}}</div>
        <div v-if="curType=='month'">이번달<br>{{prevDay}} ~ {{curDay}}</div>
        <br>

        <div v-if="curType=='week'">주간 누적 : {{userData.sumTime}}</div>
        <div v-if="curType=='month'">월간 누적 : {{userData.sumTime}}</div>
        
        <div>하루 평균 : {{userData.avgTime}}</div>
        <div>총 소모 칼로리 : {{userData.sumCalorie}}</div>

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
        this.init();
        this.getUserInfo(this.userId);
        this.getTotalTime(this.userId);
        this.getUserData(this.userId, this.curType);
    },
    methods:{
        init(){
            this.userId = "test"; // for test

            this.curType="week";
            let today = new Date();
            this.curDay = this.getDateStr(today);

            if(this.curType == "week"){
                today.setDate(today.getDate() - today.getDay() +1);
                this.prevDay = this.getDateStr(today);
            }
            else{
                today.setDate(1);
                this.prevDay = this.getDateStr(today);
            }
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
            this.totalTime = parseInt(totalTime.time/3600) + "시간 "
            +parseInt( (totalTime.time%3600)/60 ) +"분 " + parseInt( (totalTime.time%3600)%60 ) + "초";
            
        },

        async getUserData(userId, returnType){
            let data={
                type : "time",
                userId : userId,
                returnType : returnType,
            }
            this.userData = await userApi.getWalkData(data,{});

            this.timeDiff = this.userData.avgTime - this.userData.prevAvgTime;
            this.timeDiffStr = parseInt(this.timeDiff/3600) + "시간 " +
            parseInt( (this.timeDiff%3600)/60 ) + "분 " +
            parseInt( (this.timeDiff%3600)%60 ) + "초";
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
</style>