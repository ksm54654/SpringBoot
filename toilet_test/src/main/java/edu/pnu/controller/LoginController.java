package edu.pnu.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.service.MemberService;

@RestController
public class LoginController {

	@Autowired
	private MemberRepository memRepo;
	
	@Autowired 
	private MemberService memSe;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody Member member) {

		Optional<Member> option = memRepo.findById(member.getUsername());
		System.out.println(member);
		if (!option.isPresent()) { // 아이디 가져와서 없으면 not found
			return ResponseEntity.ok("Not found user!");
		}

		Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		// username이나 password가 틀리면 예외를 발생시킴.

		User user = (User) auth.getPrincipal();

		String jwtToken = JWT.create()
				.withClaim("username", user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.sign(Algorithm.HMAC256("edu.pnu.jwtkey")); // 다른 key 해도됨.

		System.out.println(user);
		System.out.println(ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken).build());
		
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken).build();

	}

	@GetMapping("/member")
	public ResponseEntity<?> member() {
		return ResponseEntity.ok("member");
	}

//	@GetMapping("/join")
//	public void join() {}

	@PostMapping("/join")
	public ResponseEntity<?> joinMember(@RequestBody Member member) {
		System.out.println(member);

		Optional<Member> option = memRepo.findById(member.getUsername());
		if (option.isPresent()) { // 회원가입시 동일한 아이디가 이미 있을 경우 
		
			return ResponseEntity.ok("Not");
		}
		else {
			memSe.joinMember(member);

			return ResponseEntity.ok("OK.");
		}
		
		
	}

	// 로그인 연장
//	@

//	@GetMapping("/user/logout") 
//	public ResponseEntity<String> userLogout(){
//		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        SecurityContextHolder.clearContext();
//
//        return new ResponseEntity<>("Logout successful", HttpStatus.OK); 
//	}

//	@PostMapping("user/join")
//	public ResponseEntity<?> userJoin(){
//		return ResponseEntity.
//	}

	/*
	 * @GetMapping("/user/logout") public
	 */

//	@GetMapping("/user/login")
//	public void loginView() {
//		
//	}

//	@PostMapping("/user/login")
//	public String login(Member member, Model model) {
//		Member findMember = memberService.getMember(member);
//		
//		if(findMember != null
//				&& findMember.getPassword().equals(member.getPassword())) {
//			model.addAttribute("member", findMember);
//			return "Main";
//		}
//		else {
//			return "redirect:Login";
//		}
//		
//	}
//	
//	@GetMapping("/user/logout")
//	public String logout(SessionStatus status) {
//		status.setComplete();
//		return "redirect:Login";
//	}
//	
//	@GetMapping("/user/new")
//	public void usernew() {
//		
//	}
//	
//	@PostMapping("/user/new")
//	public String usernewProc(Member member) {
//		Member member1 = new Member();
//		member1.setId("");
//		member1.setName("");
//		member1.setPassword("");
////		memberRepo.save(member1);
//		return "Login";
//	}

}
