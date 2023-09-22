package edu.pnu.config.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	// 생성자 -> 파라미터
	private final AuthenticationManager authenticationManager;
	private final MemberRepository memRepo;
	
	@Override // 로그인 인증 시도
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp)
			throws AuthenticationException {
		
		// req의 Body에 Json으로 담겨오는 usernamem/ password를 읽어서 member 객체로 받아옴. 
		ObjectMapper om = new ObjectMapper();
		try {
			Member member = om.readValue(req.getInputStream(), Member.class);
			
			Optional<Member> option = memRepo.findById(member.getUsername());
			if (!option.isPresent()) {
				log.info("Not Authenticated : Not found user!");
				return null;
			}
			
			Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
			Authentication auth = authenticationManager.authenticate(authToken);
			log.info("attemptAuthentication : [" + member.getUsername() + "]");
			
			return auth;
			
		} catch (Exception e) {
			log.info("Not Authenticated : " + e.getMessage());
		}
		// 
		return null;
	}
	
	@Override // 로그인 인증이 완료된 후
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp,
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		
		User user = (User)authResult.getPrincipal();
		log.info("successfulAuthentication : " + user.toString());
		
		String jwtToken = JWT.create()
						.withClaim("username", user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
						.sign(Algorithm.HMAC256("edu.pnu.jwtkey"));
		resp.addHeader("Authorization", "Bearer" + jwtToken);
		chain.doFilter(req, resp);
	}

}
