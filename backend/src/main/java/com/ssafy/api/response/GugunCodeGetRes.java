package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Gugun;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 구/군 코드 리스트를 리턴하는 Response
 */
@Getter
@Setter
@ApiModel("GugunCodeGetRes")
public class GugunCodeGetRes extends BaseResponseBody {

    @ApiModelProperty
    List<Gugun> gugunList;

    public static GugunCodeGetRes of(Integer statusCode, String message, List<Gugun> gugunList) {
        GugunCodeGetRes res = new GugunCodeGetRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setGugunList(gugunList);
        return res;
    }
}
