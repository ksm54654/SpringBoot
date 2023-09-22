package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberVO;
import edu.pnu.service.MemberService;

@RestController
public class MemberController {

	private MemberService memberService;
	
	public MemberController() {
		memberService = new MemberService();
	}
	
	@GetMapping("/members")
	public List<MemberVO> getMembers() {
		return memberService.getMembers();
	}
	
	@GetMapping("/member/{id}") // key값을 줄 필요없이 바로 값을 넣어주면 된다. (PathVariable이기 때문)
	public MemberVO getMember(@PathVariable Integer id) { 
		return memberService.getMember(id);	
	}
	
	@PostMapping("/member")
	public int addMember(MemberVO member) {
		return memberService.addMember(member);
	}
	
	@PutMapping("/member")
	public int updateMember(MemberVO member) {
		return memberService.updateMember(member);
	}
	
	@DeleteMapping("/member/{id}")
	public int deleteMember(@PathVariable Integer id) {
		return memberService.deleteMember(id);
	}
}
