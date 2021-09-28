package com.ssafy.api.response.user;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Data;

@Data
public class TimeResponseBody extends BaseResponseBody {
    int sumTime;
    double avgTime;
    int prevSumTime;
    double prevAvgTime;
    int sumCalorie;
    double avgCalorie;
    double sumDistance;
    double avgDistance;
}
