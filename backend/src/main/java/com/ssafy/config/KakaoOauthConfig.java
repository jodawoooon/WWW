package com.ssafy.config;

import lombok.extern.slf4j.Slf4j;


import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import javax.servlet.Filter;


@Slf4j
@Configuration
@PropertySource("classpath:api-config.properties")
@EnableOAuth2Client
public class KakaoOauthConfig {

    private OAuth2ClientContext oAuth2ClientContext;

    private final KakaoAuthenticationSuccessHandler kakaoAuthenticationSuccessHandler;

    public KakaoOauthConfig(OAuth2ClientContext oAuth2ClientContext, KakaoAuthenticationSuccessHandler kakaoAuthenticationSuccessHandler) {
        this.oAuth2ClientContext = oAuth2ClientContext;
        this.kakaoAuthenticationSuccessHandler = kakaoAuthenticationSuccessHandler;
    }

    // 카카오 로그인 연동
    @Bean
    public Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/api/v1/oauth");
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(kakaoClient(), oAuth2ClientContext);
        filter.setRestTemplate(oAuth2RestTemplate);
        filter.setTokenServices(new UserInfoTokenServices(kakaoResource().getUserInfoUri(), kakaoClient().getClientId()));
        filter.setAuthenticationSuccessHandler(kakaoAuthenticationSuccessHandler);
        return filter;
    }

    @Bean
    @ConfigurationProperties("kakao.client")
    public OAuth2ProtectedResourceDetails kakaoClient() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("kakao.resource")
    public ResourceServerProperties kakaoResource() {
        return new ResourceServerProperties();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
}
