package edu.pnu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import edu.pnu.config.auth.JWTAuthorizationFilter;
import edu.pnu.config.filter.JWTAuthenticationFilter;
import edu.pnu.persistence.MemberRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private MemberRepository memberRepository;
		
//	@Bean
//	public PasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
	@Autowired // AuthenticationManager를 얻기 위함
	private AuthenticationConfiguration authConfig;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()); 
		http.cors(cors -> cors.disable());
		
		http.authorizeHttpRequests(security -> {
			security.requestMatchers("/member/**").authenticated()
					.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().permitAll();
		});
		
		http.formLogin(frmLogin -> frmLogin.disable()); // form을 이용한 로그인을 사용하지 않겠다는 설정
		// 시큐리티 세션을 만들지 않겠다고 설정
		http.sessionManagement(ssmg -> ssmg.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		// memberRepository 추가해줘야함(new 한다고 해놓고 안넣으면 오류... ) 
		http.addFilter(new JWTAuthenticationFilter(authConfig.getAuthenticationManager(), memberRepository));
		http.addFilter(new JWTAuthorizationFilter(authConfig.getAuthenticationManager(), memberRepository));
		
		return http.build();
	}
}
