package com.ssafy.api.controller;


import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.DongCodeGetRes;
import com.ssafy.api.response.GugunCodeGetRes;
import com.ssafy.api.response.SidoCodeGetRes;
import com.ssafy.api.service.InfoService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Dong;
import com.ssafy.db.entity.Gugun;
import com.ssafy.db.entity.Sido;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin(origins={"*"}, maxAge=6000)
@RestController
@RequestMapping("/api/info")
public class InfoController {
    @Autowired
    InfoService infoService;

    // 가입이 안되어 있을 경우 추가 정보를 받아와 회원가입 완료
    @PostMapping("/register")
    @ApiOperation(value = "회원 가입", notes = "유저 회원가입")
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원 가입에 성공했습니다"),
            @ApiResponse(code = 409, message = "회원 가입에 실패했습니다")
    })
    public ResponseEntity<String> signUp(HttpServletResponse response, @ApiIgnore @RequestHeader("Authorization") String accessToken, @RequestBody @ApiParam(value="회원 가입 정보", required=true) UserRegisterPostReq userRegistPostReq)
    {
        infoService.register(userRegistPostReq, accessToken, response);

        return ResponseEntity.ok("회원가입이 완료 되었습니다.");
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

