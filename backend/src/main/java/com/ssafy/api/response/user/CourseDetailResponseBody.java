package com.ssafy.api.response.user;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Data;

import java.util.List;

@Data
public class CourseDetailResponseBody extends BaseResponseBody {
    int courseId;
    String courseFlagName;
    String courseName;
    String route;
    String level;
    String address;
    String detail;
    String option;
    String toilet;
    String convStore;
    String time;
    int timeInt;
    double courseLength;    // KM 단위 코스 길이
    double latitude;
    double longitude;
    int likes;  // 코스의 좋아요 개수
    boolean myLike; // 로그인 사용자의 좋아요 여부 (true, false)
    double score;  // 코스의 평균 점수 : 0~5를 별 5개로 표시
    Double myScore;    // 로그인 사용자의 리뷰 점수 : 0~10을 별 5개로 표시
    List<CafeBody> cafeList; // 코스 주변 카페 리스트
    List<ConvBody> convList; // 코스 주변 편의점 리스트
}
