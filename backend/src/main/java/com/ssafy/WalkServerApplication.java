package com.ssafy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.TimeZone;

@EnableJpaAuditing
@CrossOrigin(origins = "*")
@SpringBootApplication
public class WalkServerApplication {

	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
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
