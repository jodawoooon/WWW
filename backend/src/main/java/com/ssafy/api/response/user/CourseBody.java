package com.ssafy.api.response.user;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Data;

@Data
public class CourseBody{
    int courseId;
    String courseName;
    String address;
    double courseCnt;
    double courseLength;
}
