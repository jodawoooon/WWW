//package com.ssafy.common.Filter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.ssafy.api.service.KakaoAPI;
//import com.ssafy.api.service.RedisService;
//import com.ssafy.common.Util.CookieUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.HashMap;
//
//
//public class TokenFilter extends OncePerRequestFilter {
//
//    @Autowired
//    KakaoAPI kakaoAPI;
//
//    @Autowired
//    RedisService redisService;
//
//    @Autowired
//    CookieUtil cookieUtil;
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        System.out.println("Filter on===============");
//
//        Cookie accessTokenCookie = cookieUtil.getCookie(request,"accessToken");
//        String accessToken = accessTokenCookie.getValue();
//        System.out.println("deFilter : " + accessToken);
//        //String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(headers);
//        ResponseEntity<String> tokenResponse = null;
//
//        try {
//            tokenResponse = restTemplate.exchange(
//                    "https://kapi.kakao.com/v1/user/access_token_info",
//                    HttpMethod.GET,
//                    tokenRequest,
//                    String.class
//            );
//        }
//        // accessToken이 만료되어 401 Error 발생
//        catch (HttpClientErrorException e) {
//            Cookie refreshToken = cookieUtil.getCookie(request, "refreshToken");
//            // 새로운 토큰 갱신
//            HashMap<String, Object> Token = kakaoAPI.renewAccessToken(refreshToken.getValue());
//
//            String newAceessToken = (String) Token.get("accessToken");
//            Long newAceessTokenExpire = Long.parseLong((String) Token.get("accessTokenExpire"));
//            // 새로운 accessToken에 대한 cookie값 설정
//            Cookie newAccessTokenCookie = cookieUtil.createCookie("accessToken", newAceessTokenExpire, newAceessToken);
//            response.addCookie(newAccessTokenCookie);
//
//            // refreshToken의 유효기간이 1개월 미만인 경우 refreshToken또한 갱신
//            if (Token.get("refreshToken") != null && Token.get("refreshTokenExpire") != null) {
//                String newRefreshToken = (String) Token.get("refreshToken");
//                Long newRefreshTokenExpire = Long.parseLong((String) Token.get("refreshTokenExpire"));
//                String userId = redisService.getData(refreshToken.getValue());
//                // redis에 저장된 refreshToken이 만료되지 않은 경우
//                if (userId != null) {
//                    // 유효기간이 1개월 미만인 refreshToken 삭제 후 새로운 refreshToken 발급
//                    redisService.deleteData(refreshToken.getValue());
//                }
//                // redis에 새로운 refreshToken값 저장
//                redisService.setDataExpire(newRefreshToken, userId, newRefreshTokenExpire);
//                // 새로운 refreshToken에 대한 cookie값 설정
//                Cookie refreshTokenCookie = cookieUtil.createCookie("refreshToken", newRefreshTokenExpire, newRefreshToken);
//                response.addCookie(refreshTokenCookie);
//            }
//
//        }
//        filterChain.doFilter(request,response);
//    }
//
//}
