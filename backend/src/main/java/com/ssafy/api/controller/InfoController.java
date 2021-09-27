package com.ssafy.api.controller;


import com.ssafy.api.response.DongCodeGetRes;
import com.ssafy.api.response.GugunCodeGetRes;
import com.ssafy.api.response.SidoCodeGetRes;
import com.ssafy.api.service.InfoService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Dong;
import com.ssafy.db.entity.Gugun;
import com.ssafy.db.entity.Sido;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins={"*"}, maxAge=6000)
@RestController
@RequestMapping("/api/info")
public class InfoController {
    @Autowired
    UserService userService;

    @Autowired
    InfoService infoService;

    //회원가입 시 필요한 회원정보 받아오기
    @PostMapping("/signup")
    public ResponseEntity<BaseResponseBody> getMoreUserInfo(){
        return ResponseEntity.status(401).body(BaseResponseBody.of(401,"Failed"));
    }

    @GetMapping("/sido")
    @ApiOperation(value = "시/도 코드 리스트", notes = "시/도 코드 정보를 가져온다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<SidoCodeGetRes> findSidoList(){

        List<Sido> sidoList = infoService.getSidoList();

        return ResponseEntity.ok(SidoCodeGetRes.of(200, "Success", sidoList));
    }

    @GetMapping("/gugun/{sido}")
    @ApiOperation(value = "구/군 코드 리스트", notes = "구/군 코드 정보를 가져온다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<GugunCodeGetRes> findGugunList(@PathVariable("sido") Long sido){

        List<Gugun> gugunList = infoService.getGugunListBySido(sido);
        return ResponseEntity.ok(GugunCodeGetRes.of(200, "Success",gugunList));
    }

    @GetMapping("/dong/{gugun}")
    @ApiOperation(value = "동 코드 리스트", notes = "동 코드 정보를 가져온다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<DongCodeGetRes> findDongList(@PathVariable("gugun") Long gugun){
        List<Dong> dongList = infoService.getDongListByGugun(gugun);
        return ResponseEntity.ok(DongCodeGetRes.of(200, "Success",dongList));
    }

}

