package com.ssafy.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@ApiModel("MainReq")
public class MainReq {
    //@ApiModelProperty(name = "userId", example = "jihyeonzzang")
    String userId;
    //@ApiModelProperty(name = "courseId", example = "ABCDEFG1234567")
    String courseId;

    String distance;
    String time;
    String calorie;
    String date;
}
