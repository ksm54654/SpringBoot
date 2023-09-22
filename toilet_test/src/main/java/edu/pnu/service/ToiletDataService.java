package edu.pnu.service;

import java.util.List;

import edu.pnu.domain.FilterCriteria;
import edu.pnu.domain.ToiletData;

public interface ToiletDataService {

	List<ToiletData> getToiletList();

	void insertToilet(ToiletData toilet);

	ToiletData updateToilet(ToiletData toilet);

	void deleteToilet(Long id);

	ToiletData getToilet(Long id);

	ToiletData getupdateToilet(Long id, ToiletData toilet);
	
	// ---- 옵션 처리 ----
	List<ToiletData> filterToilets(String city, String county, String disabledToilet, String nappyToilet);

}