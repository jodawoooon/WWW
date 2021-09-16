package com.ssafy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import javax.servlet.Filter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Filter ssoFilter;

    public SecurityConfig(Filter ssoFilter) {
        this.ssoFilter = ssoFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 로그인이 필요한 url 정의
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/api/kakao").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().csrf().disable()
                .addFilterBefore(ssoFilter, BasicAuthenticationFilter.class); // OAuthConfig에서 생성한 ssoFilter 추가
    }
}
