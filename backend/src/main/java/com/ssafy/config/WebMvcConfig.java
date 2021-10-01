package com.ssafy.config;


import com.ssafy.api.service.KakaoAPI;
import com.ssafy.common.Interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

import javax.servlet.Filter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    AuthInterceptor authInterceptor;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // configuration.addAllowedOrigin("*");
        configuration.addAllowedOriginPattern("*"); // CORS 해결을 위한 설정 (고정)
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        // configuration.addExposedHeader(JwtTokenUtil.HEADER_STRING);
        configuration.setAllowCredentials(true); // CORS 해결을 위한 설정 (고정)
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Value("C:\\Users\\multicampus\\Desktop\\ssafy\\공통프로젝트\\Doglive\\profile_imgs\\")
    private String profileUploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/profile_imgs/**")
                .addResourceLocations("file:///" + profileUploadFolder)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());

        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/WEB-INF/resources/");

        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        /*
         *
         * Front-end에서 참조하는 URL을 /dist로 매핑
         *
         */
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/dist/css/");
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/dist/fonts/");
        registry.addResourceHandler("/icons/**")
                .addResourceLocations("classpath:/dist/icons/");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/dist/img/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/dist/js/");
    }

    public Filter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setIncludeHeaders(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }

    @Bean
    public FilterRegistrationBean loggingFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(requestLoggingFilter());
        registration.addUrlPatterns("/api/*");
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/users/**")
                .addPathPatterns("/api/course/**")
                .addPathPatterns("/api/review/**");
    }

}

