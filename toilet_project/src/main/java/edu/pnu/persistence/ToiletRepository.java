package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ToiletData;

public interface ToiletRepository extends JpaRepository<ToiletData, Long> {

	List<ToiletData> findByCityAndCounty(String city, String county);

//	List<ToiletData> findByCityAndCountyAndNappy_toilet(String city, String county, String nappy_toilet);

}
