package com.ssafy.common.Interceptor;

import com.ssafy.api.service.KakaoAPI;
import com.ssafy.api.service.RedisService;
import com.ssafy.common.Util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private KakaoAPI kakaoAPI;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String uri = request.getRequestURI();
        String accessToken = null;
        String refreshToken = null;

        // 현재 로그인 된 상태인지 체크
        if(cookies == null || cookies.length == 0){
            throw new AuthorizationServiceException("로그인이 필요합니다!");
        }

        for(Cookie cookie : cookies){
            if(cookie.getName().equals("accessToken"))
                accessToken = cookie.getValue();
            else if(cookie.getName().equals("refreshToken"))
                refreshToken = cookie.getValue();
        }

        // 현재 로그인 된 상태인지 체크
        if(accessToken == null || refreshToken == null) {
            throw new AuthorizationServiceException("로그인이 필요합니다!");
        }

        KakaoAPI kakaoAPI = new KakaoAPI();
        int responseCode = kakaoAPI.checkAccessToken(accessToken, refreshToken); // 갱신 여부 체크
        System.out.println("WebMvcConfig-ResponseCode : " + responseCode + " " + accessToken);
        // accessToken이 만료되어 401 Error 발생
        if(responseCode == 401){
            Cookie refreshTokenCookie = cookieUtil.getCookie(request, "refreshToken");
            // 새로운 토큰 갱신
            HashMap<String, Object> Token = kakaoAPI.renewAccessToken(refreshTokenCookie.getValue());

            String newAceessToken = (String) Token.get("accessToken");
            Long newAceessTokenExpire = Long.parseLong((String) Token.get("accessTokenExpire"));
            // 새로운 accessToken에 대한 cookie값 설정
            Cookie newAccessTokenCookie = cookieUtil.createCookie("accessToken", newAceessTokenExpire, newAceessToken);
            response.addCookie(newAccessTokenCookie);

            // refreshToken의 유효기간이 1개월 미만인 경우 refreshToken또한 갱신
            if (Token.get("refreshToken") != null && Token.get("refreshTokenExpire") != null) {
                String newRefreshToken = (String) Token.get("refreshToken");
                Long newRefreshTokenExpire = Long.parseLong((String) Token.get("refreshTokenExpire"));
                String userId = redisService.getData(refreshTokenCookie.getValue());
                // redis에 저장된 refreshToken이 만료되지 않은 경우
                if (userId != null) {
                    // 유효기간이 1개월 미만인 refreshToken 삭제 후 새로운 refreshToken 발급
                    redisService.deleteData(refreshTokenCookie.getValue());
                }
                // redis에 새로운 refreshToken값 저장
                redisService.setDataExpire(newRefreshToken, userId, newRefreshTokenExpire);
                // 새로운 refreshToken에 대한 cookie값 설정
                Cookie newRefreshTokenCookie = cookieUtil.createCookie("refreshToken", newRefreshTokenExpire, newRefreshToken);
                response.addCookie(newRefreshTokenCookie);
            }
        }

        if(responseCode == 400){
            throw new AuthorizationServiceException("잘못된 접근입니다!");
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {

    }
}
