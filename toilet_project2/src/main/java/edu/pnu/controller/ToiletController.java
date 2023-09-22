package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.ToiletInfo;
import edu.pnu.service.ToiletDataService;

@RestController
public class ToiletController {

	
	@Autowired
	private ToiletDataService ToiletService;
	
//	@CrossOrigin(origins = "http://10.125.121.189:3000")
	@GetMapping("/toilets/data")
	public List<ToiletInfo> getToiletList() {
		
		List<ToiletInfo> toiletList = ToiletService.getToiletList();
		
		return toiletList;
	}
	

	
}
