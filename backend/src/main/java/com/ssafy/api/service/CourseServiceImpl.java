package com.ssafy.api.service;

import com.ssafy.api.response.CourseDataGetRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("courseService")
public class CourseServiceImpl implements CourseService {
    @Override
    public List<CourseDataGetRes> getCourseList() {
        return null;
    }
}
