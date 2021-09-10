package com.ssafy.api.service;

import com.ssafy.api.response.CourseDataGetRes;

import java.util.List;


public interface CourseService {
    List<CourseDataGetRes> getCourseList();
}
