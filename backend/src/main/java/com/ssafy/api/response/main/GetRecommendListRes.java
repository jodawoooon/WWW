package com.ssafy.api.response.main;

import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRecommendListRes extends BaseResponseBody {
    @ApiModelProperty(name = "추천 리스트", example = "[[],[]]")
    int[] recommendList;

    public static GetRecommendListRes of(Integer statusCode, String message, int[] recommendList){
        GetRecommendListRes resbody = new GetRecommendListRes();
        resbody.setStatusCode(statusCode);
        resbody.setMessage(message);
        resbody.setRecommendList(recommendList);
        return resbody;
    }
}
