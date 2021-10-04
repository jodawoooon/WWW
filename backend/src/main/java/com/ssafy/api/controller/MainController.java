package com.ssafy.api.controller;

import com.ssafy.api.request.FinishRecordReq;
import com.ssafy.api.response.main.GetRankRes;
import com.ssafy.api.response.main.GetRecommendListRes;
import com.ssafy.api.response.main.TodayWalkTimeRes;
import com.ssafy.api.service.MainService;
import com.ssafy.common.model.response.BaseResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/main")
public class MainController {

    @Autowired
    MainService mainService;

    //순위 정보 받아오기
    @GetMapping("/rank")
    public ResponseEntity<GetRankRes> getRank(){
        GetRankRes getRankRes = mainService.getRank();

        if(getRankRes!=null) return ResponseEntity.ok(GetRankRes.of(200,"Success",getRankRes.getRanking()));
        return ResponseEntity.status(401).body(GetRankRes.of(401,"Failed",null));
    }

    //오늘 걸은 시간
    @GetMapping("/todaywalk/{userId}/{date}")
    public ResponseEntity<TodayWalkTimeRes> getTodayWalk(@RequestBody @PathVariable("userId") String userId, @PathVariable("date") String date){
        TodayWalkTimeRes todayWalkTimeRes = mainService.getTodayWalk(userId, date);

        if(todayWalkTimeRes != null) return ResponseEntity.ok(TodayWalkTimeRes.of(200,"Success",todayWalkTimeRes.getSecond()));
        return ResponseEntity.status(401).body(TodayWalkTimeRes.of(401,"Failed", -1));
    }

    //산책 기록 저장
    @PostMapping("/finishrecord")
    public ResponseEntity<BaseResponseBody> finishRecord(@RequestBody FinishRecordReq finishRecordReq){
        String userId = finishRecordReq.getUserId();
        int courseId = finishRecordReq.getCourseId();
        double distance = finishRecordReq.getDistance();
        int time = finishRecordReq.getTime();
        int calorie = finishRecordReq.getCalorie();

        boolean resbody = mainService.finishRecord(userId,courseId,distance,time,calorie);

        if(resbody) return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
        return ResponseEntity.status(401).body(BaseResponseBody.of(401,"Failed"));
    }

    //오늘의 추천 코스>알고리즘 결정 필요
    @GetMapping("/today/{dong}")
    public ResponseEntity<GetRecommendListRes> getRecommendList(@RequestBody @PathVariable("dong") String dong){
        GetRecommendListRes getRecommendListRes = mainService.getRecommendList(dong);

        if(getRecommendListRes!=null) return ResponseEntity.ok(GetRecommendListRes.of(200, "Success",getRecommendListRes.getRecommendList()));
        return ResponseEntity.status(401).body(GetRecommendListRes.of(401,"Failed",null));
    }
}
