package com.ssafy.api.response.user;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Data;

@Data
public class CourseBody{
    int courseId;
    String courseFlagName;
    String courseName;
    String address;
    double courseCnt;
    double courseLength;    // KM 단위 코스 길이
    String time;
    int timeInt;    // 분 단위 int형 시간
    double latitude;
    double longitude;
    int likes;  // 코스의 좋아요 개수
    boolean myLike; // 로그인 사용자의 좋아요 여부 (true, false)
    double score;  // 코스의 평균 점수 : 0~5를 별 5개로 표시
    Double myScore;
    Double geoDistance; // 사용자와 산책로 떨어진 거리 KM 단위
    int calorie;
}
