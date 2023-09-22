package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
public class MemberInitialize {
	@Autowired
	MemberRepository memRepo;

	@Autowired
	private PasswordEncoder encoder;

	@Test
	public void testDataInsert() {
		memRepo.save(Member.builder()
				.username("member")
				.name("둘리")
				.password(encoder.encode("abcd"))
				.role("ROLE_MEMBER")
				.enabled(true).build());
		
		memRepo.save(Member.builder()
				.username("manager")
				.name("도우너")
				.password(encoder.encode("abcd"))
				.role("ROLE_MANAGER")
				.enabled(true).build());
//		memRepo.save(Member.builder()
//				.username("admin")
//				.password(encoder.encode("abcd"))
//				.role("ROLE_ADMIN")
//				.enabled(true).build());
	}
}
