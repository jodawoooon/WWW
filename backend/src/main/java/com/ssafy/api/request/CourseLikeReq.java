package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CourseLikeReq")
public class CourseLikeReq {
    int courseId;

    String userId;
}
