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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberController {
	
	private MemberService memberService;

	public MemberController() {
		System.out.println("MemberController가 생성되었습니다.");
		log.info("MemberController가 생성되었습니다.");
		
		memberService = new MemberService();
	}
	
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
