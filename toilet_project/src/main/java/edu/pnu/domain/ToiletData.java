package edu.pnu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ToiletData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String toilet_name;
	
	// 주소
	private String city;	// 광역시
	private String county;	// 시군구
	private String load_address;
	private String num_address;

	private String open_time;	// 개방시간
	private String disabled_toilet;	// 장애인 화장실 유무
	private String nappy_toilet;	// 기저귀 교환대 유무
	
	
	// 상세정보
	private String division;	// 구분
	private String latitude;	// 위도
	private String longitude;	// 경도
	
	private String management_name;	// 관리기관명
	private String tellnumber;		// 전화번호
	private String establishment_date;	// 설치연월
	private String trash_processing;	// 오물처리방식
	private String emergency_bell;		// 비상벨 설치 유무
	private String cctv;			// CCTV설치 유무
	private String data_date;		// 데이터 기준일자
	

	private String image; // 이미지
	private String imageName;
	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "image_id")
//	private Images images;
	
}
