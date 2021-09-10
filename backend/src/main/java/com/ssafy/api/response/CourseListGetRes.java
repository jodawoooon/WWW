package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("CourseListGetRes")
public class CourseListGetRes extends BaseResponseBody {

    @ApiModelProperty
    List<CourseDataGetRes> courseList;

    @ApiModelProperty
    int size;


    public static CourseListGetRes of(Integer statusCode, String message, List<CourseDataGetRes> courseList, Integer size) {
        CourseListGetRes res = new CourseListGetRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setCourseList(courseList);
        res.setSize(size);
        return res;
    }
}
