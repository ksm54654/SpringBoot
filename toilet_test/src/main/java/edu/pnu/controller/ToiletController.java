package edu.pnu.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	
	
	// 데이터 추가
	@PostMapping("/toilets")
	
	
	public void insertToilet(@RequestBody ToiletData toilet) {
		System.out.println(toilet);
		ToiletService.insertToilet(toilet);
	}
	
//	@PutMapping("/toilets") // 데이터 업데이트이나... 안씀 . 
//	public ToiletData updateToilet(@RequestBody ToiletData toilet) {
//		
//		return ToiletService.updateToilet(toilet);
//	}
	
//	 데이터 조회 + 값 수정 
//	@PutMapping("/toilets/{id}")
//	public void getupdateToilet(@PathVariable Long id, @RequestBody ToiletData toilet) {
//		System.out.println(toilet);
//		ToiletData gettoilet = ToiletService.getToilet(id);
//		if (gettoilet.getId() == toilet.getId()) {
//			ToiletService.updateToilet(toilet);
//		}
//	}
	
	
	
	@Value("${spring.servlet.multipart.location}")
	private String location;
	
	// 데이터 수정
	@PutMapping(value = "/toilets/{id}")
	public ResponseEntity<?> insertToiletWithImage(@PathVariable Long id, @RequestBody ToiletData toilet,
			@RequestParam(name = "image", required = false) MultipartFile file) {

		System.out.println("start");
		System.out.println(toilet);
		ToiletData gettoilet = ToiletService.getToilet(id);
		if (gettoilet == null) {
			System.out.println("id error");
			return ResponseEntity.notFound().build();
		}

		if (toilet != null) {
			ToiletService.updateToilet(toilet);
		}

		if (file != null && !file.isEmpty()) {
	        try {
	            String fileName = id + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
	            String filePath = location + fileName;

	            File targetFile = new File(filePath);
	            if (!targetFile.getParentFile().exists()) {
	                targetFile.getParentFile().mkdirs(); // 디렉토리 생성
	            }

	            file.transferTo(targetFile);
	            
	            gettoilet.setImageName(fileName);
	            gettoilet.setImage(filePath);
	            ToiletService.updateToilet(gettoilet); // 이미지 정보를 업데이트
	            
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Exception: " + e.getMessage());
	        }
	    }
		return ResponseEntity.ok("Sucessful");

	}
	
	
	@GetMapping("/display/{id}") // 내 로컬의 이미지를 표시하기
	public ResponseEntity<byte[]> getImage(@PathVariable Long id) {

		ToiletData toilet = ToiletService.getToilet(id);
		System.out.println(toilet);
		String file = "C:/temp/uploads/" + toilet.getImageName();
//		  String file = "C:/temp/uploads/" + "source_04.jpg";
		System.out.println(file);

		Path imagePath = Paths.get(file);

		if (!Files.exists(imagePath)) {
			return ResponseEntity.notFound().build();
		}

		byte[] imageBytes = null;
		try {
			imageBytes = Files.readAllBytes(imagePath);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG); // 이미지 타입에 맞게 설정

		return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

	}
	   
//	// 이미지만 데이터베이스 추가--------------------------------------
//	@PostMapping("/toilets")
//	public ResponseEntity<?> insertToiletWithImage(@RequestParam("image") MultipartFile file) {
//
//		if (file.isEmpty()) {
//			System.out.println("image error");
//			return ResponseEntity.ok("File is Empty!");
//		}
//		try {
//			
//			// 새로운 데이터 생성 및 저장
//	        ToiletData newToilet = new ToiletData();
//	        ToiletService.insertToilet(newToilet);
//
//	        // 새로 생성된 ID를 가져와서 이미지 정보 업데이트
//	        Long newToiletId = (long) newToilet.getId();
//	        String fileName = newToiletId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
//			String filePath = location + fileName;
//	        newToilet.setImageName(fileName);
//	        newToilet.setImage(filePath);
//
//	        ToiletService.updateToilet(newToilet); // 이미지 정보를 업데이트
//
//	        // 이미지를 서버에 저장
//	        file.transferTo(new File(filePath));
//
//		} catch (Exception e) {
//			return ResponseEntity.ok("Exception: " + e.getMessage());
//		}
//		return ResponseEntity.ok("Sucessful.");
//	}
	
	
	
//	// 두개 같이 ... 
//	@PostMapping("/toilets")
//	public ResponseEntity<?> insertToiletAll(
//			@RequestBody ToiletData toilet, 
//			@RequestParam(name = "image", required = false) MultipartFile file) {
//
//		ToiletService.insertToilet(toilet);
//		
//		Long id = (long) toilet.getId();
//		System.out.println(id);
//		System.out.println(file);
//		
////		if (file.isEmpty()) {
////			System.out.println("image error");
////			return ResponseEntity.ok("File is Empty!");
////		}
//		try {
//			System.out.println("start");
////			Long id = (long) 3;
//			ToiletData gettoilet = ToiletService.getToilet(id);
//			System.out.println(gettoilet);
//			String fileName = id + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
//			String filePath = location + fileName;
//
//			// File의 메소드를 이용하는 방법
//			file.transferTo(new File(filePath));
//			
//			gettoilet.setImageName(fileName);
//			gettoilet.setImage(filePath);
//            ToiletService.updateToilet(gettoilet); // 이미지 정보를 업데이트
//            
//		} catch (Exception e) {
//			return ResponseEntity.ok("Exception: " + e.getMessage());
//		}
//		return ResponseEntity.ok("Sucessful.");
//	}
	

//	@PostMapping(value = "/toilets", consumes = "multipart/form-data")
//	public ResponseEntity<?> insertToiletWithImage(@RequestBody ToiletData toilet,
//			@RequestParam(name = "file", required = false) MultipartFile file) {
//
//		System.out.println("start");
//		System.out.println(toilet);
//		System.out.println("file" + file);
//		
//		if (toilet == null) {
//			System.out.println("id error");
//			return ResponseEntity.notFound().build();
//		}
//
//		if (toilet != null) {
//			ToiletService.insertToilet(toilet);
//			System.out.println("text insert");
//		}
//
//		if (file != null && !file.isEmpty()) {
//	        try {
//	        	System.out.println("image test");
//	            String fileName = toilet.getId() + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
//	            String filePath = location + fileName;
//
//	            File targetFile = new File(filePath);
//	            if (!targetFile.getParentFile().exists()) {
//	                targetFile.getParentFile().mkdirs(); // 디렉토리 생성
//	            }
//
//	            file.transferTo(targetFile);
//	            
//	            toilet.setImageName(fileName);
//	            toilet.setImage(filePath);
//	            ToiletService.updateToilet(toilet); // 이미지 정보를 업데이트
//	            
//	        } catch (Exception e) {
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                    .body("Exception: " + e.getMessage());
//	        }
//		}
//	    
//		return ResponseEntity.ok("Sucessful");
//
//	}
	
	
	// 데이터 삭제
	@DeleteMapping("/toilets/{id}")
	public void deleteToilet(@PathVariable Long id) {
		System.out.println(id);
		ToiletService.deleteToilet(id);
		
	}
	
	// ---- 옵션 처리 ---- 
//    @GetMapping("/filter")
//    public ResponseEntity<List<ToiletData>> filterToilets(
//            @RequestParam(required = false) String city,
//            @RequestParam(required = false) String county,
//            @RequestParam(required = false) String disabledToilet,
//            @RequestParam(required = false) String nappyToilet) {
//        
//        List<ToiletData> filteredToilets = ToiletService.filterToilets(city, county, disabledToilet, nappyToilet);
//        
//        return ResponseEntity.ok(filteredToilets);
//    }
	
	@GetMapping("/filter")
	public ResponseEntity<List<ToiletData>> filterToilets(
	        @RequestParam Optional<String> city,
	        @RequestParam Optional<String> county,
	        @RequestParam Optional<String> disabledToilet,
	        @RequestParam Optional<String> nappyToilet) {
	    
	    List<ToiletData> filteredToilets = ToiletService.filterToilets(city.orElse(null), county.orElse(null), disabledToilet.orElse(null), nappyToilet.orElse(null));
	    
	    return ResponseEntity.ok(filteredToilets);
	}

	
	
	
}

