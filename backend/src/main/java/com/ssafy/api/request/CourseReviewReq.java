package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CourseReviewReq")
public class CourseReviewReq {
    int courseId;

    String userId;

    @Schema(description = "리뷰 점수", defaultValue = "0", example = "0", minimum = "0", maximum = "5")
    double score;
}
