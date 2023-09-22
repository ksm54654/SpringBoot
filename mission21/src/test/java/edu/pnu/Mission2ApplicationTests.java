package edu.pnu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;

@SpringBootTest
class Mission2ApplicationTests {

	@Autowired
	MemberService memberService;
	
	@Autowired
	DataSource dataSource;
	
	@Test
	public void dataSourceTest() throws SQLException {
		Statement st = dataSource.getConnection().createStatement();
		ResultSet rs = st.executeQuery("select * from Member");
		while(rs.next()) {
			
		}
	}

	@Test
	@Order(1)
	public void contextload1() {
		List<Member> list = memberService.getMembers();
		for (Member m : list) {
			System.out.println(m);
		}
	}

	@Test
	@Order(2)
	public void contextload2() {
		System.out.println("=".repeat(40));
		Member m = memberService.getMember(1L);
		System.out.println(m);
		System.out.println("=".repeat(40));
	}

	@Test
	@Order(3)
	public void contextload3() {
		System.out.println("=".repeat(40));
		System.out.println(memberService.insertMember(
				new Member(6L, "pass6", "name6", new Date())
				));
		System.out.println("-".repeat(40));
		List<Member> list = memberService.getMembers();
		for (Member m : list) {
			System.out.println(m);
		}
		System.out.println("=".repeat(40));
	}
}
