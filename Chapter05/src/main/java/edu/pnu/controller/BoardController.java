package edu.pnu.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Board;
import edu.pnu.persistence.BoardRepository;

@RestController
public class BoardController {

	@Autowired
	BoardRepository boardRepo;
	
	@GetMapping("/getBoardList")
	public List<Board> getBoardList() {
		return boardRepo.findAll();
	}
		
	@PostMapping("/board")		// Create
	public Board insertBoard(Board board) {
		if (board.getCreateDate() == null)
			board.setCreateDate(new Date());
		return boardRepo.save(board);
	}
	
	@PostMapping("/boardjson")	// Create
	public Board insertJsonBoard(@RequestBody Board board) {
		if (board.getCreateDate() == null)
			board.setCreateDate(new Date());
		return boardRepo.save(board);
	}
	
	@GetMapping("/board")		// Read
	public Board getBoard(Long id) {
		return boardRepo.findById(id).get();
	}
	
	@PutMapping("/board")		// Update
	public Board updateBoard(Board board) {
		return boardRepo.save(board);
	}
	
	@DeleteMapping("/board")	// delete
	public String deleteBoard(Long id) {
		boardRepo.deleteById(id);
		return String.format("seq가 %d인 데이터가 삭제되었습니다.", id);
	}
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@GetMapping("/findpage")
	public List<Board> findPage(Integer pagenum, Integer size) {
		Pageable paging = PageRequest.of(pagenum, size);
		Page<Board> page = boardRepo.findAll(paging);
		return page.getContent();
	}
	
	@GetMapping("/findByTitleLike")
	public List<Board> findByTitleLike(String kw) {
		Pageable page = PageRequest.of(0, 5);
		return boardRepo.findByTitleLike("%"+kw+"%", null);
	}
}
