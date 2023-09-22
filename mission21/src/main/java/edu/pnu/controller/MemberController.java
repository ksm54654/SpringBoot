package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor // 4번 - 롬복 어노테이션
public class MemberController {
	
	// 1번 - 필드에 설정하는 방법
//	@Autowired
	private MemberService memberService;
	
	// 2번 - 생성자에서 설정하는 방법
//	@Autowired
//	public MemberController(MemberService memberService) {
//		this.memberService = memberService;
//	}
	
	// 3번 - Setter를 이용한 방법
//	private void setmemberService(MemberService memberService) {
//		this.memberService = memberService;
//	}
	
	
	// localhost:8080/member/1
	@GetMapping("/member/{id}")
	public Member getMember(@PathVariable Long id) {
		return memberService.getMember(id);
	}
	
	@GetMapping("/member") // 모든 멤버 데이터를 리턴
	public List<Member> getMembers() {	
		return memberService.getMembers();
	}
	
	@PostMapping("/member") // 새로운 멤버 입력
	public int insertMember(Member member) {
		return memberService.insertMember(member);
	}
	
	@PutMapping("/member") // 멤버 이름, 암호 수정
	public int updateMember(Member member) {
		return memberService.updateMember(member);
	}
	
	@DeleteMapping("/member") // 멤버 삭제
	public int deleteMember(Long id) {
		return memberService.deleteMember(id);
	}
}
