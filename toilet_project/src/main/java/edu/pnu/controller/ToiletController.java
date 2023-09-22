package edu.pnu.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.pnu.domain.ToiletData;
import edu.pnu.service.ToiletDataService;

@RestController
public class ToiletController {

	
	@Autowired
	private ToiletDataService ToiletService;
	
	@GetMapping("/toilets") // 데이터 다 불러오기
	public List<ToiletData> getToiletList() {
		
		List<ToiletData> toiletList = ToiletService.getToiletList();
		
		return toiletList;
	}
	
	@GetMapping("/toilets/{id}") // 데이터 조회하기
	public ToiletData getToilet(@PathVariable Long id) {
		
		ToiletData toilet = ToiletService.getToilet(id);
		
		return toilet;
	}
	
//	@GetMapping("/toilets/{city}/{county}/{nappy_toilet}") // 선택해서 데이터 조회해주는 거 추가해야함 (p261참조)
//	public List<ToiletData> getCityToiletList (@PathVariable String city, @PathVariable String county,
//			@PathVariable String nappy_toilet) {
//		
//		List<ToiletData> toiletList = ToiletService.getCityToiletList(city, county, nappy_toilet);
//		
//		return toiletList;
//	}
	
	@GetMapping("/filter")
	public ResponseEntity<List<ToiletData>> filterToilets(
	        @RequestParam Optional<String> city,
	        @RequestParam Optional<String> county,
	        @RequestParam Optional<String> disabledToilet,
	        @RequestParam Optional<String> nappyToilet) {
	    
	    List<ToiletData> filteredToilets = ToiletService.filterToilets(city.orElse(null), county.orElse(null), disabledToilet.orElse(null), nappyToilet.orElse(null));
	    
	    return ResponseEntity.ok(filteredToilets);
	}
	
	
//	// 데이터 추가
//	@PostMapping("/toilets")
//	public void insertToilet(@RequestBody ToiletData toilet) {
//		System.out.println(toilet);
//		ToiletService.insertToilet(toilet);
//	}
	
//	@PostMapping("/toilets")
//	public String insertToiletWithImage(@RequestBody ToiletData toiletData) {
//		byte[] imageBytes = Base64.getDecoder().decode(toiletData.getImage());
//
//        ToiletData newToiletData = new ToiletData();
//
//        newToiletData.setImage(imageBytes);
//
//  
//
//        return "Toilet data and image uploaded successfully.";
//	}
	
	@Value("${spring.servlet.multipart.location}")
	private String location;
	
//	@PostMapping("/toilets")
//	public ResponseEntity<?> insertToiletWithImage(@RequestParam("toilet") ToiletData toilet) {
//		System.out.println(toilet);
//		ToiletService.insertToilet(toilet);
//		return ResponseEntity.ok("업로드되었습니다.");
//	}
	
	
//	@PostMapping("/toilets")
//	public ResponseEntity<?> insertToiletWithImage(@RequestParam("keyName") MultipartFile file) {
//	
//		
//		if (file.isEmpty()) {
//			return ResponseEntity.ok("File is Empty!");
//		}
//		try {
//			// File의 메소드를 이용하는 방법
//			file.transferTo(new File(location + file.getOriginalFilename()));
//			
//			// FileOutputStream 객체를 이용하는 방법
//			//FileOutputStream fos = new FileOutputStream(location + file.getOriginalFilename());
//			//fos.write(file.getBytes());
//			//fos.close();
//		} catch(Exception e) {
//			return ResponseEntity.ok("Exception: " + e.getMessage());
//		}		
//		return ResponseEntity.ok("업로드되었습니다.");
//	}
		
	// 데이터 추가
	@PostMapping(value = "/toilets")
	public ResponseEntity<?> insertToiletWithImage(@RequestBody ToiletData toilet,
			@RequestParam(name = "image",required = false) MultipartFile file){

		System.out.println("start");
		System.out.println(toilet);
		if (toilet != null) {
			ToiletService.insertToilet(toilet);
		}
		
		if (file != null) {
			try {
				file.transferTo(new File(location + file.getOriginalFilename()));
			} catch (Exception e) {
				return ResponseEntity.ok("Exception: " + e.getMessage());
			}
		}
		return ResponseEntity.ok("Sucessful");
		
		
//		ObjectMapper objectMapper = new ObjectMapper();
//        ToiletData toiletData = objectMapper.readValue(toilet, ToiletData.class);
//        
//        ToiletService.insertToiletWithImage(toiletData, file);
	}
	
	@PutMapping("/toilets") // 데이터 업데이트이나... 안씀 . 
	public ToiletData updateToilet(@RequestBody ToiletData toilet) {
		
		return ToiletService.updateToilet(toilet);
	}
		
	// 데이터 조회 + 값 수정 
	@PutMapping("/toilets/{id}")
	public void getupdateToilet(@PathVariable Long id , @RequestBody ToiletData toilet) {
		System.out.println(toilet);
		ToiletData gettoilet = ToiletService.getToilet(id);
		if (gettoilet.getId() == toilet.getId()) {
			ToiletService.updateToilet(toilet);
		}
	}
	
	// 데이터 삭제
	@DeleteMapping("/toilets/{id}")
	public void deleteToilet(@PathVariable Long id) {
		System.out.println(id);
		ToiletService.deleteToilet(id);
		
	}
	
}

