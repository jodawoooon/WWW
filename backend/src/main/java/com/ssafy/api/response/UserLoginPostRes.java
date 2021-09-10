package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@ApiModel("UserLoginPostResponse")
public class UserLoginPostRes extends BaseResponseBody {
    @ApiModelProperty
    HashMap<String,Object> user;

    public static UserLoginPostRes of(Integer statusCode, String message, HashMap<String,Object> user) {
        UserLoginPostRes res = new UserLoginPostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setUser(user);
        return res;
    }

}

