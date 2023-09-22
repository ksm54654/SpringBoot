package edu.pnu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	BoardUserDetailsService boardUserDetailsService;

	// 암호화 라이브러리 
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		// Security에서 주는 기본 설정을 하나도 안쓰겠다고 선언 -> 로그인창 같은거도 이제 안뜸 !! 
		security.authorizeHttpRequests(auth -> {
//			auth.requestMatchers("/").permitAll();
//			auth.requestMatchers("/member/**").authenticated();
			
			auth.requestMatchers("/member/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")  // Any
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll();
		});
		
		security.csrf(csrf->csrf.disable());	
		
		security.formLogin(form-> {
			form.loginPage("/login")
				.defaultSuccessUrl("/loginSuccess", true);
		});
		
		security.exceptionHandling(ex->ex.accessDeniedPage("/accessDenied"));
		security.logout(log->{
			log.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/login");
		});
		
		security.userDetailsService(boardUserDetailsService);
		
		return security.build();
	}
	
	@Autowired
	public void authenicate(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("member")
			.password("{noop}abcd")
			.roles("MEMBER");
		
		auth.inMemoryAuthentication()
		.withUser("manager")
		.password("{noop}abcd")
		.roles("MANAGER");
		
		auth.inMemoryAuthentication()
		.withUser("admin")
		.password("{noop}abcd")
		.roles("ADMIN");
	}
}
