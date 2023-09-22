package edu.pnu;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Board;
import edu.pnu.persistence.BoardRepository;

@SpringBootTest
class QueryMethodTest {

	@Autowired
	private BoardRepository boardRepo;
	
//	@Test
	public void testFindByTitleContaining() {
		List<Board> boardList = boardRepo.findByTitleContaining("1");
		
		System.out.println("title에 \"1\"이 포함되는 데이터 출력");
		for(Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
	
//	@Test
	public void testFindByTitleContainingAndCntGreaterThan() {
		List<Board> boardList = boardRepo.findByTitleContainingAndCntGreaterThan("1", 50L);
		
		System.out.println("title에 \"1\"이 포함되면서 cnt가 50보다 큰 데이터 출력");
		for(Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
	
//	@Test
	public void testFindByCntGreaterThanEqualAndCntLessThanEqualOrderBySeqAsc() {
		List<Board> boardList = boardRepo.findByCntGreaterThanEqualAndCntLessThanEqualOrderBySeqAsc(10L, 50L);
		
		System.out.println("cnt가 10~50사이인 데이터를 seq오름차순으로 출력");
		for(Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
	
	@Test
	public void testFindByTitleContainingOrContentContainingOrderBySeqDesc() {
		List<Board> boardList = boardRepo.findByTitleContainingOrContentContainingOrderBySeqDesc("10", "2");
		
		System.out.println("title에 \"1\"이 포함되거나 content에 \"2\"가 포함되는 데이터를 seq 내림차순으로 출력");
		for(Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
	
}
