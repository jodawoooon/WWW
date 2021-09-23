package com.ssafy.api.service;

import com.ssafy.api.request.CourseReq;
import com.ssafy.api.response.CourseDataGetRes;
import com.ssafy.common.model.response.BaseResponseBody;

import java.util.List;


public interface CourseService {
    public BaseResponseBody getCourseList(CourseReq courseReq);
}
