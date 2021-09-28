package com.ssafy.api.response.main;

import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRankRes extends BaseResponseBody {
    @ApiModelProperty(name = "순위정보",example = "[jihyeon,down,haeon]")
    String[] ranking;
//    @ApiModelProperty(name = "1등",example = "jihyeon")
//    String firstUser;
//    @ApiModelProperty(name = "2등",example = "down")
//    String secondUser;
//    @ApiModelProperty(name = "3등",example = "haeon")
//    String thirdUser;

    public static GetRankRes of(Integer statusCode,String message, String[] ranking){//String firstUser, String secondUser, String thirdUser
        GetRankRes body = new GetRankRes();
        body.setStatusCode(statusCode);
        body.setMessage(message);
        body.setRanking(ranking);
//        body.setFirstUser(firstUser);
//        body.setSecondUser(secondUser);
//        body.setThirdUser(thirdUser);
        return body;
    }

}
