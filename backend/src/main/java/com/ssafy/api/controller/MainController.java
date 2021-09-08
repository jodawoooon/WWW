package com.ssafy.api.controller;

import com.ssafy.api.request.MainReq;
import com.ssafy.common.model.response.BaseResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/main")
public class MainController {

    //순위 정보 받아오기
    @GetMapping("/rank")
    public ResponseEntity<BaseResponseBody> getRank(){
        return ResponseEntity.status(401).body(BaseResponseBody.of(401,"Failed"));
    }

    //오늘 걸은 시간
    @GetMapping("/todaywalk/{userId}/{date}")
    public ResponseEntity<BaseResponseBody> getTodayWalk(@RequestBody @PathVariable("userId") String userId, @PathVariable("date") Date date){
        return ResponseEntity.status(401).body(BaseResponseBody.of(401,"Failed"));
    }

    //메인에서 start 눌렀을 때 시간 카운트


    //최근 코스 등록 (user가 시작하려는 코스를 등록함(start 버튼 눌렀을때)
    @PostMapping("/startrecord")
    public ResponseEntity<BaseResponseBody> startRecord(@RequestBody MainReq mainReq){
        String userId;
        String courseId;

        if(true) return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
        return ResponseEntity.status(401).body(BaseResponseBody.of(401,"Failed"));
    }

    //산책 기록 저장
    @PostMapping("/finishrecord")
    public ResponseEntity<BaseResponseBody> finishRecord(@RequestBody MainReq mainReq){
        String userId;
        String courseId;
        String distance;
        String time;
        String calorie;
        String date;

        if(true) return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
        return ResponseEntity.status(401).body(BaseResponseBody.of(401,"Failed"));
    }

    //오늘의 추천 코스>알고리즘 결정 필요
    @GetMapping("/today/{dong}")
    public ResponseEntity<BaseResponseBody> getRecommendList(@RequestBody @PathVariable("dong") String dong){

        if(true) return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
        return ResponseEntity.status(401).body(BaseResponseBody.of(401,"Failed"));
    }
}
