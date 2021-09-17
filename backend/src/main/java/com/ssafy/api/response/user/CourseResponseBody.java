package com.ssafy.api.response.user;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseResponseBody extends BaseResponseBody {
    List<CourseBody> courseList;

    public CourseResponseBody(){
        super();
        courseList=new ArrayList<>();
    }
}
