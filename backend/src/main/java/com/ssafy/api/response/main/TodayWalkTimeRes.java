package com.ssafy.api.response.main;

import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("MainRes")
public class TodayWalkTimeRes extends BaseResponseBody {
    @ApiModelProperty(name = "초", example = "55")
    int second;

    public static TodayWalkTimeRes of(Integer statusCode, String message, int hour, int minute, int second){
        TodayWalkTimeRes body = new TodayWalkTimeRes();
        body.setStatusCode(statusCode);
        body.setMessage(message);
        body.setSecond(second);
        return body;
    }
}
