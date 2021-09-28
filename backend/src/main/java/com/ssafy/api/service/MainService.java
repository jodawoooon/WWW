package com.ssafy.api.service;

import com.ssafy.api.response.main.GetRankRes;
import com.ssafy.api.response.main.GetRecommendListRes;
import com.ssafy.api.response.main.TodayWalkTimeRes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface MainService {

    public GetRankRes getRank();

    public TodayWalkTimeRes getTodayWalk(String userId, LocalDateTime date);

//    public boolean startRecord();

    public boolean finishRecord(String userId, int courseId, double distance, int time, int calorie);

    public GetRecommendListRes getRecommendList(String dong);
}
