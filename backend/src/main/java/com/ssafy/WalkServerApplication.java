package com.ssafy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.Charset;

@CrossOrigin(origins = "*")
@SpringBootApplication
public class WalkServerApplication {

	static {
		System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/application-db.yml");
	}

	public static void main(String[] args) {
		SpringApplication.run(WalkServerApplication.class, args);
	}
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}

	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}
}
