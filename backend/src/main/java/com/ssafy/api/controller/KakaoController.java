package com.ssafy.api.controller;

import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.KakaoAPI;
import com.ssafy.api.service.UserService;
import com.ssafy.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping(value = "/oauth")
    public ResponseEntity<String> kakaoConnect(){
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=" + "1e31c0b3e807829e950f0236c26efec6");
        url.append("&redirect_uri=http://localhost:8080/kakao/login");
        url.append("&response_type=code");
        System.out.println(url.toString());
        return new ResponseEntity<String>(url.toString(), HttpStatus.OK);
    }

    @RequestMapping(value="/login")
    public ResponseEntity<UserLoginPostRes> login(@RequestParam("code") String code, HttpSession session) {
        HashMap<String,Object> Token = kakaoAPI.getAccessToken(code);

        System.out.println(Token.toString());

        String access_Token = (String) Token.get("accessToken");
        String refresh_Token = (String) Token.get("refreshToken");

        System.out.println("code : " + code);
        // 사용자의 정보를 <string, 객체> 로 생성
        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(access_Token, refresh_Token); //front 전송 용 유저 Info Hash Map
        HashMap<String, Object> userProfile = kakaoAPI.getUserProfile(access_Token, refresh_Token); //create 용 유저 Profile Hash Map

        System.out.println("login Controller : " + userInfo);

        HashMap<String, Object> userObject = new HashMap<String, Object>();
        userObject.put("Token",Token);
        userObject.put("userInfo",userInfo);

        System.out.println("-------------------");
        System.out.println(userObject);
        System.out.println("-------------------");

        // 카카오가 보낸 정보에서 id를 가져온다.
        String id = (String) userInfo.get("userid");
        System.out.println(id);
        User user = userService.getUserId(id);
        // 회원가입이 되어있는 경우
        if(user!=null){
            System.out.println("login success!");
            return ResponseEntity.ok(UserLoginPostRes.of(200,"Success", userObject));
        }
        // 클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
//                session.setAttribute("userId", userInfo.get("email"));
//                session.setAttribute("access_Token", userObject);
        }
        userService.createUser(access_Token, refresh_Token, userProfile);
        return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", userObject));

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

