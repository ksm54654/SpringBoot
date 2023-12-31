package edu.pnu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberVO;
import edu.pnu.service.MemberService;

// @Slf4j
@RestController
public class MemberController {

	private MemberService memberService;
	
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	
	public MemberController() {
		memberService = new MemberService();
	}
	
	@GetMapping("/members")
	public List<MemberVO> getMembers() {
		log.info("getMembers()");
		return memberService.getMembers();
	}

	// localhost:8080/member/1
	@GetMapping("/member/{id}") // 데이터 가져올때 사용하는 방식. .. ? 
	public MemberVO getMember(@PathVariable Integer id) {
		log.info("getMember()");
		return memberService.getMember(id);
	}
	
	// localhost:8080/member?id=1
	@PostMapping("/member")
	public MemberVO addMember(MemberVO member) {
		log.info("addMember()");
		return memberService.addMember(member);
	}

	@PutMapping("/member")
	public MemberVO updateMember(MemberVO member) {
		log.info("updateMember()");
		return memberService.updateMember(member);
	}
	
	@DeleteMapping("/member/{id}")
	public MemberVO deleteMember(@PathVariable Integer id) {
		log.info("deleteMember()");
		return memberService.deleteMember(id);
	}
}
