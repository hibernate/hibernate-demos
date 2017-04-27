package com.example.ogm.data.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.ogm.core.user.Celebrity;
import com.example.ogm.core.user.CelebrityRepository;

@Repository
public class JpaProxyCelebrityRepository implements CelebrityRepository {

	@Autowired
	private CelebrityJpaRepository jpaRepository;

	public Celebrity save(Celebrity newUser) {
		CelebrityJpaEntity newEntity = new CelebrityJpaEntity( newUser );
		CelebrityJpaEntity savedEntity = jpaRepository.save( newEntity );
		Celebrity savedUser = savedEntity.toCelebrity();
		return savedUser;
	}

	public List<Celebrity> all() {
		List<CelebrityJpaEntity> entities = jpaRepository.findAll();
		List<Celebrity> users = new ArrayList<Celebrity>( entities.size() );
		for ( CelebrityJpaEntity entity : entities ) {
			Celebrity user = entity.toCelebrity();
			users.add( user );
		}
		return users;
	}

	public List<Celebrity> byName(String name) {
		List<CelebrityJpaEntity> entities = jpaRepository.findByName( name );
		List<Celebrity> users = new ArrayList<Celebrity>( entities.size() );
		for ( CelebrityJpaEntity entity : entities ) {
			Celebrity user = entity.toCelebrity();
			users.add( user );
		}
		return users;
	}

	public void deleteAll() {
		jpaRepository.deleteAll();
	}

}
