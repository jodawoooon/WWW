package com.ssafy.api.controller;


import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.DongCodeGetRes;
import com.ssafy.api.response.GugunCodeGetRes;
import com.ssafy.api.response.SidoCodeGetRes;
import com.ssafy.api.service.InfoService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Dong;
import com.ssafy.db.entity.Gugun;
import com.ssafy.db.entity.Sido;
import com.ssafy.db.entity.User;
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

    @Autowired
    UserService userService;

    // 가입이 안되어 있을 경우 추가 정보를 받아와 회원가입 완료
    @PostMapping("/register")
    @ApiOperation(value = "회원 가입", notes = "유저 회원가입")
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원 가입에 성공했습니다"),
            @ApiResponse(code = 409, message = "회원 가입에 실패했습니다")
    })
    public ResponseEntity<String> signUp(@RequestBody @ApiParam(value="회원 가입 정보", required=true) UserRegisterPostReq userRegistPostReq)
    {
        infoService.register(userRegistPostReq);

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
        System.out.println(sido);
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

    @GetMapping("/present/{userId}")
    @ApiOperation(value = "존재하는 회원 확인", notes = "해당 userID를 사용하는 사용자가 있는지 확인한다.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> isPresentUser(@PathVariable String userId) {
        User user = userService.getUserId(userId);
        if(user == null) {
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "사용할 수 있는 ID입니다."));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "이미 존재하는 사용자 ID입니다."));
    }

}

