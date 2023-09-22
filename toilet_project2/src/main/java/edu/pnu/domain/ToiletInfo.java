package edu.pnu.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ToiletInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int 번호;
	private String 화장실명;
	private String 구분;		// 화장실 구분 
	private String 소재지도로명주소;		// 도로명주소
	private String 소재지지번주소;		// 지번주소
	
	private int 남성용대변기수;
	private int 남성용소변기수;
	private int 남성용장애인용대변기수;
	private int 남성용장애인용소변기수;
	private int 남성용어린이용대변기수;
	private int 남성용어린이용소변기수;
	private int 여성용대변기수;
	private int 여성용장애인용대변기수;
	private int 여성용어린이용대변기수;

	private String 관리기관명;	
	private String 전화번호;
	private String 설치연월;
	private String WGS84위도;	// 위도
	private String WGS84경도;	// 경도
	
	private String 화장실소유구분;
	private String 화장실설치장소유형;
	private String 오물처리방식;
	private String 비상벨설치여부;
	private String 비상벨설치장소;
	
	private String 화장실입구CCTV설치유무;
	private String 기저귀교환대유무;
	private String 기저귀교환대장소;
	private String 리모델링연월;
	private Date 데이터기준일자;
	
	private String 개방시간;		// 개방시간
			// 기저귀 교환대 유무
	
	
}
