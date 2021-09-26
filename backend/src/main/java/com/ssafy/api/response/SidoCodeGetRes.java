package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;

import com.ssafy.db.entity.Sido;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 시/도 코드 리스트를 리턴하는 Response
 */
@Getter
@Setter
@ApiModel("SidoCodeGetRes")
public class SidoCodeGetRes extends BaseResponseBody {

    @ApiModelProperty
    List<Sido> sidoList;

    public static SidoCodeGetRes of(Integer statusCode, String message, List<Sido> sidoList) {
        SidoCodeGetRes res = new SidoCodeGetRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setSidoList(sidoList);
        return res;
    }
}

