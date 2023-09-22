package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pnu.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	List<Board> findByTitleContaining(String title);
	
	List<Board> findByTitleContainingAndCntGreaterThan(String title, Long cnt);
	
	List<Board> findByCntGreaterThanEqualAndCntLessThanEqualOrderBySeqAsc(Long cnt1, Long cnt2);
	
	List<Board> findByTitleContainingOrContentContainingOrderBySeqDesc(String title, String content);
	
	
	
	List<Board> findByTitleLike(String title, Pageable paging);
	Page<Board> findAll(Pageable paging);
	
	@Query("Select b from Board b where b.title like %?1% order by b.seq DESC")
	List<Board> queryAnnotationTest1(String searchKeyword);
	
	@Query("Select b from Board b where b.title like %:searchKeyword% order by b.seq DESC")
	List<Board> queryAnnotationTest2(String searchKeyword);
	
	@Query("Select b.seq, b.title, b.writer, b.createDate from Board b"
			+ " Where b.title like %:searchKeyword% order by b.seq DESC")
	List<Object[]> queryAnnotationTest3(String searchKeyword);
	
	@Query(value="select seq, title, writer, create_date "
			+"from board where title like '%'||:sk||'%' "
			+"order by seq desc", nativeQuery=true)
	List<Object[]> queryAnnotationTest4(@Param("sk") String searchKeyword);
	
	@Query("select b from Board b order by b.seq asc")
//	@Query("select b from Board b")
	List<Board> queryAnnotationTest5(Pageable paging);
}
