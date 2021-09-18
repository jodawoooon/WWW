package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CourseReq")
public class CourseReq {
    String userId;

    int page;

    int size;

    @ApiModelProperty(name = "sort", example = "'distance', 'likes'")
    String sort;

    int minTime;

    int maxTime;

    int minDistnace;

    int maxDistance;

    String dong;

    Double longtitude;

    Double latitude;
}
