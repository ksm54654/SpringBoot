package edu.pnu.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.pnu.domain.ToiletData;

public interface ToiletDataService {

	List<ToiletData> getToiletList();

	void insertToilet(ToiletData toilet);

	ToiletData updateToilet(ToiletData toilet);

	void deleteToilet(Long id);

	ToiletData getToilet(Long id);

	ToiletData getupdateToilet(Long id, ToiletData toilet);

	List<ToiletData> getCityToiletList(String city, String county);

	String insertToiletWithImage(ToiletData toilet);
	
	void insertToiletWithImage(ToiletData toilet, MultipartFile imageFile);
	
	List<ToiletData> filterToilets(String city, String county, String disabledToilet, String nappyToilet);

//	void insertToiletWithImage(ToiletData toiletData, MultipartFile file);

}