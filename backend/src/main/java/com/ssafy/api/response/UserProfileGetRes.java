package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserProfileGetResponse")
public class UserProfileGetRes extends BaseResponseBody {

    @ApiModelProperty
    User user;

    public static UserProfileGetRes of(Integer statusCode, String message, User user){
        UserProfileGetRes res = new UserProfileGetRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setUser(user);
        return res;
    }


}
