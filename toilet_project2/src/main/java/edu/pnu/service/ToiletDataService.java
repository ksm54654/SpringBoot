package edu.pnu.service;

import java.util.List;

import edu.pnu.domain.ToiletInfo;

public interface ToiletDataService {

	List<ToiletInfo> getToiletList();

	void insertBoard(ToiletInfo board);

//	ToiletData getBoard(ToiletData board);
//
//	void updateBoard(ToiletData board);
//
//	void deleteBoard(ToiletData board);

}