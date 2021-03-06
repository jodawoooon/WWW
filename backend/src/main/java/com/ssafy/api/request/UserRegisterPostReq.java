package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserRegisterPostReq")
public class UserRegisterPostReq {
    String userId;

    String name;

    String nickname;

    String city;

    String gu;

    String dong;

    String refreshToken;

    String refreshTokenExpire;

}
