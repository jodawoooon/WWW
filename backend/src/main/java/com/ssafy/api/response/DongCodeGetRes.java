package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Dong;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 동 코드 리스트를 리턴하는 Response
 */
@Getter
@Setter
@ApiModel("DongCodeGetRes")
public class DongCodeGetRes extends BaseResponseBody {

    @ApiModelProperty
    List<Dong> dongList;

    public static DongCodeGetRes of(Integer statusCode, String message, List<Dong> dongList) {
        DongCodeGetRes res = new DongCodeGetRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setDongList(dongList);
        return res;
    }
}

