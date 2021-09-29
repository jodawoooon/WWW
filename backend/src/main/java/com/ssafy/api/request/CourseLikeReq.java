package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CourseLikeReq")
public class CourseLikeReq {
    @Schema(description = "코스 아이디", defaultValue = "0", example = "0")
    int courseId;

    @Schema(description = "회원 아이디", defaultValue = " ", example = " ")
    String userId;
}
