package edu.pnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

	public TestConfig() {
		System.out.println("=".repeat(50));
		System.out.println("TestConfig가 생성되었습니다.");
		System.out.println("=".repeat(50));
	}
	
	@Bean
	public TestBean testBean() {
		return new TestBean();
	}
}

class TestBean {
	public TestBean() {
		System.out.println("=".repeat(50));
		System.out.println("TestConfig가 생성되었습니다.");
		System.out.println("=".repeat(50));
	}
}