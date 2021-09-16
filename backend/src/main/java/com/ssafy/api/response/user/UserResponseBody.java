package com.ssafy.api.response.user;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Data;

@Data
public class UserResponseBody extends BaseResponseBody {
    String nickName;
    double sumDistance;
    int weeklySumTime;
    int dailyAvgTime;
    int sumCalorie;
}
