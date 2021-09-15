package com.ssafy.common.model.response.user;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserResponseBody extends BaseResponseBody {
    String nickName;
    double sumDistance;
    int weeklySumTime;
    int dailyAvgTime;
    int sumCalorie;
}
