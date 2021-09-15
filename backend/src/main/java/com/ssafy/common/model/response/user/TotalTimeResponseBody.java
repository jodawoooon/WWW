package com.ssafy.common.model.response.user;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalTimeResponseBody extends BaseResponseBody {

    int time;

    public TotalTimeResponseBody(){

    }
    public TotalTimeResponseBody(Integer statusCode, String message, int time){
        super(statusCode, message);
        this.time=time;
    }

    public static TotalTimeResponseBody of(Integer statusCode, String message, int time) {
        TotalTimeResponseBody body = new TotalTimeResponseBody();
        body.setMessage(message);
        body.setStatusCode(statusCode);
        body.setTime(time);
        return body;
    }
}
