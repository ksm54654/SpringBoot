package edu.pnu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public Member getMember(Member member) {
		Optional<Member> findMember = memberRepo.findById(member.getUsername());
		if(findMember.isPresent())
			return findMember.get();
		else return null;
	}
	
	public void joinMember(Member member) {
		
		Member n_member = new Member();
		 
		n_member.setUsername(member.getUsername());
		n_member.setName(member.getName());
		n_member.setPassword(encoder.encode(member.getPassword()));
		n_member.setRole("ROLE_MEMBER");
		n_member.setEnabled(true);
		memberRepo.save(n_member);
	}
}
