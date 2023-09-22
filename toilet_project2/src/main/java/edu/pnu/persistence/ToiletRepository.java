package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ToiletInfo;

public interface ToiletRepository extends JpaRepository<ToiletInfo, Long> {

}
