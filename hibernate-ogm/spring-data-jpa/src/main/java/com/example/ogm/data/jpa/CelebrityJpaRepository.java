package com.example.ogm.data.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
public interface CelebrityJpaRepository extends JpaRepository<CelebrityJpaEntity, Long> {

	@Query("select c from CelebrityJpaEntity c where c.name = :name")
	List<CelebrityJpaEntity> findByName(@Param("name") String name);

	@Override
	@Modifying
	@Query(value = "MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n, r", nativeQuery = true)
	void deleteAll();
}
