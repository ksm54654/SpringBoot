package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import edu.pnu.domain.ToiletData;

@Repository
public interface ToiletRepository extends JpaRepository<ToiletData, Long>, JpaSpecificationExecutor<ToiletData> {
    List<ToiletData> findByCityAndCountyAndDisabledToiletAndNappyToilet(
            String city, String county, String disabled_toilet, String nappy_toilet
    );
}

