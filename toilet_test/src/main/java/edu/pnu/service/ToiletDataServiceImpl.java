package edu.pnu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import edu.pnu.domain.ToiletData;
import edu.pnu.persistence.ToiletRepository;

@Service
public class ToiletDataServiceImpl implements ToiletDataService {

	@Autowired
	private ToiletRepository toiletRepo;
	
	@Override
	public List<ToiletData> getToiletList() {
		return toiletRepo.findAll();
	}
	
	@Override
	public ToiletData getToilet(Long id) {
		Optional<ToiletData> option = toiletRepo.findById(id);
		if(option.isPresent()) return option.get();
		return null;
		
	}
	
	@Override
	public void insertToilet(ToiletData toilet) {
		toiletRepo.save(toilet);
	}
	
	@Override
	public ToiletData updateToilet(ToiletData toilet) {
		
		ToiletData findToilet = toiletRepo.findById((long) toilet.getId()).get();
		
		findToilet.setToilet_name(toilet.getToilet_name());
		findToilet.setCity(toilet.getCity());
		findToilet.setCounty(toilet.getCounty());
		findToilet.setLoad_address(toilet.getLoad_address());
		findToilet.setNum_address(toilet.getNum_address());
		findToilet.setOpen_time(toilet.getOpen_time());
		findToilet.setDisabledToilet(toilet.getDisabledToilet());
		findToilet.setNappyToilet(toilet.getNappyToilet());
		findToilet.setImage(toilet.getImage());
		findToilet.setImageName(toilet.getImageName());
		
		return toiletRepo.save(findToilet);
	}
	
	@Override
	public ToiletData getupdateToilet(Long id, ToiletData toilet) {
		Optional<ToiletData> option = toiletRepo.findById(id);	
		
		if(option.isPresent()) {
			ToiletData existingToilet = option.get();
			
			existingToilet.setToilet_name(toilet.getToilet_name());
			existingToilet.setCity(toilet.getCity());
			existingToilet.setCounty(toilet.getCounty());
			existingToilet.setLoad_address(toilet.getLoad_address());
			existingToilet.setNum_address(toilet.getNum_address());
			existingToilet.setOpen_time(toilet.getOpen_time());
			existingToilet.setDisabledToilet(toilet.getDisabledToilet());
			existingToilet.setNappyToilet(toilet.getNappyToilet());
			
			return toiletRepo.save(existingToilet);
		}
		else {
			return null;
		}
	}
	
	@Override
	public void deleteToilet(Long id) {
		Optional<ToiletData> option = toiletRepo.findById(id);	
		
		if(option.isPresent()) {
			ToiletData toilet = option.get();
			toiletRepo.delete(toilet);
		}
		
	}
	
	// ---- 옵션 처리 ----
	// 시도1.
//    @Override
//    public List<ToiletData> filterToilets(String city, String county, String disabledToilet, String nappyToilet) {
//        // 필터링 조건에 따라 데이터 조회
//        List<ToiletData> filteredToilets = toiletRepo.findByCityAndCountyAndDisabledToiletAndNappyToilet(
//            city, county, disabledToilet, nappyToilet
//        );
//
//        return filteredToilets;
//    }

	// 시도2.
//    @Override
//    public List<ToiletData> filterToilets(String city, String county, String disabledToilet, String nappyToilet) {
//        Specification<ToiletData> finalSpecification = (root, query, builder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            // 각각의 조건에 대한 Predicate 추가
//            if (city != null) {
//                predicates.add(builder.equal(root.get("city"), city));
//            }
//            if (county != null) {
//                predicates.add(builder.equal(root.get("county"), county));
//            }
//            if (disabledToilet != null) {
//                predicates.add(builder.equal(root.get("disabledToilet"), disabledToilet));
//            }
//            if (nappyToilet != null) {
//                predicates.add(builder.equal(root.get("nappyToilet"), nappyToilet));
//            }
//
//            return builder.and(predicates.toArray(new Predicate[0]));
//        };
//
//        return toiletRepo.findAll(finalSpecification);
//    }

	@Override
	public List<ToiletData> filterToilets(String city, String county, String disabledToilet, String nappyToilet) {
	    if (city == null && county == null && disabledToilet == null && nappyToilet == null) {
	        return toiletRepo.findAll(); // 모든 데이터 조회
	    }

	    List<Specification<ToiletData>> specifications = new ArrayList<>();
	    
	    // 도시 옵션 선택 시 해당하는 Specification 추가
	    if (city != null && !city.isEmpty()) {
	        specifications.add((root, query, criteriaBuilder) ->
	                criteriaBuilder.equal(root.get("city"), city));
	    }

	    // 구/군 옵션 선택 시 해당하는 Specification 추가
	    if (county != null && !county.isEmpty()) {
	        specifications.add((root, query, criteriaBuilder) ->
	                criteriaBuilder.equal(root.get("county"), county));
	    }

	    // 장애인 관련 옵션 선택 시 해당하는 Specification 추가
	    if (disabledToilet != null && !disabledToilet.isEmpty()) {
	        specifications.add((root, query, criteriaBuilder) ->
	                criteriaBuilder.equal(root.get("disabledToilet"), disabledToilet));
	    }

	    // 기저귀 관련 옵션 선택 시 해당하는 Specification 추가
	    if (nappyToilet != null && !nappyToilet.isEmpty()) {
	        specifications.add((root, query, criteriaBuilder) ->
	                criteriaBuilder.equal(root.get("nappyToilet"), nappyToilet));
	    }

	    // 모든 조건을 AND로 결합
	    Specification<ToiletData> finalSpecification = specifications.stream()
	            .reduce(Specification::and)
	            .orElse(null);

	    // Specification이 null이 아닌 경우에만 필터링하여 조회
	    if (finalSpecification != null) {
	        return toiletRepo.findAll(finalSpecification);
	    } else {
	        return new ArrayList<>(); // 조건이 없는 경우 빈 리스트 반환
	    }
	}



	
	
	
}
