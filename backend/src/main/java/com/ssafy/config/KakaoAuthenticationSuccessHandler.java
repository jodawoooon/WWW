package com.ssafy.config;

import com.ssafy.common.model.response.KakaoLoginUser;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Component
public class KakaoAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final String USER_NO = "USER_NO";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private final HttpSession httpSession;

    private final ObjectMapper objectMapper;

    private final UserRepository userRepository;

    private final OAuth2ClientContext oAuth2ClientContext;

    public KakaoAuthenticationSuccessHandler(
            HttpSession httpSession,
            @Qualifier("thirdPartyMapper") ObjectMapper objectMapper,
            UserRepository userRepository,
            OAuth2ClientContext oAuth2ClientContext) {
        this.httpSession = httpSession;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.oAuth2ClientContext = oAuth2ClientContext;
    }

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        KakaoLoginUser kakaoLoginUser = convertThirdPartyLoginUser((OAuth2Authentication) authentication);
//
//        // FIXME email 이 nullable 하기 때문에 다른 고유 값이 필요
//        User user = getUser(convertThirdPartyLoginUser((OAuth2Authentication) authentication).getKakaoAccount().getEmail()))
//                .orElseGet(() -> User.convert(kakaoLoginUser));
//        user.updateLastAccessAtNow();
//
//        userRepository.save(user);
//        httpSession.setAttribute(USER_NO, user.getUserId());
//
//        authentication = new UsernamePasswordAuthenticationToken(user.getUserId(), null, Collections.emptyList());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        response.sendRedirect(getRefererUrl());
//    }

    @Transactional
    public Optional<User> getUser(String userId) {
        return userRepository.findUserByUserId(userId);
    }

    private KakaoLoginUser convertThirdPartyLoginUser(OAuth2Authentication oAuth2Authentication) {
        return objectMapper.convertValue(oAuth2Authentication.getUserAuthentication().getDetails(), KakaoLoginUser.class);
    }

    private String getRefererUrl() {
        try {
            return httpSession.getAttribute("referer").toString();
        } catch (Exception e) {
            // store 서비스 메인 페이지로 변경 필요
            return "/api/v1/me";
        }
    }
}
