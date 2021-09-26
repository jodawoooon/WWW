package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CourseReviewReq")
public class CourseReviewReq {
    int courseId;

    String userId;
    
    int score;
}
