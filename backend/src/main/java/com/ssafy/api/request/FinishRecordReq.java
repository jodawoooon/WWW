package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("MainReq")
public class FinishRecordReq {
    @ApiModelProperty(name = "userId", example = "jihyeonzzang")
    String userId;
    @ApiModelProperty(name = "courseId", example = "ABCDEFG1234567")
    int courseId;
    @ApiModelProperty(name = "distance", example = "2.03")//km
    double distance;
    @ApiModelProperty(name = "time", example = "01-12-30")//미정
    int time;
    @ApiModelProperty(name = "calorie", example = "300")//프론트에서 계산해서 주거나 거리랑 시간만 보낼시 백에서 계산 예정
    int calorie;
}
