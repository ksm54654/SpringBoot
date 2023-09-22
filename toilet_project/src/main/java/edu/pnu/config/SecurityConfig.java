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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import edu.pnu.config.filter.JWTAuthorizationFilter;
import edu.pnu.persistence.MemberRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MemberRepository memRepo;
    
    @Autowired // AuthenticationManager를 얻기 위함
	private AuthenticationConfiguration authConfig;
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    	return authConfig.getAuthenticationManager();
    }
    
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
//		http.addFilter(corFilter());
		
		http.authorizeHttpRequests(security -> {
			security
			.requestMatchers("/toilets/**").authenticated()
//					.requestMatchers("/toilets/delete").hasRole("MANAGER")
					.anyRequest().permitAll();
			// toilets~는 로그인해야 접근 가능, 나머지는 안해도 접근가능.
		});
		
		http.formLogin(frmLogin -> frmLogin.disable()); // form을 이용한 로그인을 사용하지 않겠다는 설정
		// 시큐리티 세션을 만들지 않겠다고 설정
		http.sessionManagement(ssmg -> ssmg.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		// memberRepository 추가해줘야함(new 한다고 해놓고 안넣으면 오류... // 아직 코드 작성 안해서 오류난거.)
//		http.addFilter(new JWTAuthenticationFilter(authConfig.getAuthenticationManager(), memberRepository));
		http.addFilterBefore(new JWTAuthorizationFilter(authConfig.getAuthenticationManager(), memRepo), BasicAuthenticationFilter.class);
		// 여기서 jwt설정을 해줘야 토큰을 인증하고 수행해서 로그인이 된다!! 
		
		return http.build();
	}
}
