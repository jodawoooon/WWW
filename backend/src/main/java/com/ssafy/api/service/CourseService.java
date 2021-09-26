package com.ssafy.api.service;

import com.ssafy.api.request.CourseLikeReq;
import com.ssafy.api.request.CourseReq;
import com.ssafy.api.response.CourseDataGetRes;
import com.ssafy.common.model.response.BaseResponseBody;

import java.util.List;


public interface CourseService {
    // 코스 목록 조회
    public BaseResponseBody getCourseList(CourseReq courseReq);

    // 코스 상세정보 조회
    public BaseResponseBody getCourseDetail(int courseId, String userId);

    // 코스 좋아요 등록
    public BaseResponseBody postCourseLike(CourseLikeReq courseLikeReq);

    // 코스 좋아요 삭제
    public BaseResponseBody deleteCourseLike(CourseLikeReq courseLikeReq);
}
