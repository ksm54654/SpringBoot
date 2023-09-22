//package edu.pnu.config;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class SecurityConfig {
//
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf(csrf -> csrf.disable()); 
//		http.cors(cors -> cors.disable());
//		
//		http.formLogin(frmLogin -> {
//			frmLogin.loginPage("/user/login").defaultSuccessUrl();
//		});
//		return 
//	}
//}

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired // AuthenticationManager를 얻기 위함
	private AuthenticationConfiguration authConfig;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();	// 비밀번호 암호화
    }
    
//    @Bean
//    public CorsFilter corFilter() {
//    	CorsConfiguration cors = new CorsConfiguration();
//    	cors.addAllowedOrigin("http://10.125.121.189:3000");
//    	cors.addAllowedMethod("*");
//    	cors.addAllowedHeader("*");
//    	cors.setAllowCredentials(true);
//    	
//    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    	source.registerCorsConfiguration("/**", cors);
//    	return new CorsFilter(source);
//    }

    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()); 

		
		http.authorizeHttpRequests(security -> {
			security  // 주소 수정...? .requestMatchers("/toilets/**").authenticated()
					.anyRequest().permitAll();	
			// 로그인 페이지는 모두 접근 가능... 그 이후부터는 인증 필요... 를 구현하고 싶었음 (될지...ㅜㅜ)
		});
		
		http.formLogin(frmLogin -> frmLogin.disable()); // form을 이용한 로그인을 사용하지 않겠다는 설정
		// 시큐리티 세션을 만들지 않겠다고 설정
		http.sessionManagement(ssmg -> ssmg.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
	
		return http.build();
	}
}
