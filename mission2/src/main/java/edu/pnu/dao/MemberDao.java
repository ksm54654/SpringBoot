package edu.pnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.pnu.domain.Member;

public class MemberDao {

	private String driver = "org.h2.Driver";
	private String url = "jdbc:h2:tcp://localhost/~/mission2";
	private String username = "scott";
	private String password = "tiger";
	
	private Connection con;
	
	public MemberDao() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public Member getMember(Long id) {
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery(String.format("select * from Member where id = %d", id));
			
			rs.next();
			Member m = Member.builder()
							.id(rs.getLong("id"))
							.pass(rs.getString("pass"))
							.name(rs.getString("name"))
							.regidate(rs.getDate("regidate"))
							.build();

			rs.close();
			stmt.close();
			
			return m;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Member> getMembers() {
		List<Member> list = new ArrayList<>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(String.format("select * from Member order by id"));
			
			while(rs.next()) {
				list.add(Member.builder()
						.id(rs.getLong("id"))
						.pass(rs.getString("pass"))
						.name(rs.getString("name"))
						.regidate(rs.getDate("regidate"))
						.build());
			}
			rs.close();
			stmt.close();
			
			return list;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int insertMember(Member member) {
		try {
			Statement stmt = con.createStatement();
			int ret = stmt.executeUpdate(String.format("insert into member(pass, name) values ('%s', '%s')", 
					member.getPass(), member.getName()));
					
			stmt.close();
			return ret;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateMember(Member member) {
		try {
			Statement stmt = con.createStatement();
			int ret = stmt.executeUpdate(String.format("update member set pass='%s', name='%s' where id = %d ", 
					member.getPass(), member.getName(), member.getId()));
					
			stmt.close();
			return ret;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int deleteMember(Long id) {
		try {
			Statement stmt = con.createStatement();
			int ret = stmt.executeUpdate(String.format("delete member where id = %d", id));
					
			stmt.close();
			return ret;

		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
