package com.ssafy.api.controller;

import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.KakaoAPI;
import com.ssafy.api.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.HashMap;


@CrossOrigin(origins={"*"}, maxAge=6000)
@RestController
@RequestMapping("/api/kakao")
public class KakaoController {
    @Autowired
    private KakaoAPI kakaoAPI;
    @Autowired
    UserService userService;

    @RequestMapping(value="/login")
    public ResponseEntity<UserLoginPostRes> login(@RequestParam("code") String code, HttpSession session) {
        String access_Token = kakaoAPI.getAccessToken(code);
        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(access_Token);

        System.out.println("login Controller : " + userInfo);

        return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", userInfo));
    }

    @ApiOperation(value = "카카오 계정 로그아웃", notes = "Kakao 인증서버에 accessToken을 넘겨주어 로그인된 카카오 계정을 로그아웃 시킨다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping(value = "/logout")
    public ResponseEntity<String> logout(@CookieValue(value = "accessToken", required = false) Cookie access_Token) {
        if (access_Token == null)
            return ResponseEntity.ok("토큰이 유효하지 않습니다.");
        System.out.println("logout accessToken : " + access_Token.getValue());
        kakaoAPI.Logout(access_Token.getValue());
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}

