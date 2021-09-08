package com.ssafy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalkServerApplication {

	static {
		System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/application-db.yml");
	}

	public static void main(String[] args) {
		SpringApplication.run(WalkServerApplication.class, args);
	}

}
