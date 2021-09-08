package com.ssafy.walkServer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WalkServerApplicationTests {

	static {
		System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/application-db.yml");
	}

	@Test
	void contextLoads() {
	}

}
