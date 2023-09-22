package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.ToiletInfo;
import edu.pnu.persistence.ToiletRepository;

@Service
public class ToiletDataServiceImpl implements ToiletDataService {

	@Autowired
	private ToiletRepository toiletRepo;
	
	@Override
	public List<ToiletInfo> getToiletList() {
		return toiletRepo.findAll();
	}
	

	
//	@Override
//	public ToiletData getBoard(ToiletData board) {
//		
//		ToiletData b = boardRepo.findById(board.()).get();
//		
//		b.setCnt(b.getCnt() + 1);
//		
//		return boardRepo.save(b);
//	}
	
	@Override
	public void insertBoard(ToiletInfo board) {
		toiletRepo.save(board);
	}
}
