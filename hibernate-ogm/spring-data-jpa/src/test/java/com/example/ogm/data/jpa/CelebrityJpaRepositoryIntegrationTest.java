package com.example.ogm.data.jpa;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.ogm.config.ApplicationConfiguration;
import com.example.ogm.data.jpa.CelebrityJpaEntity;
import com.example.ogm.data.jpa.CelebrityJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CelebrityJpaRepositoryIntegrationTest {

	@Autowired
	private CelebrityJpaRepository	repository;

	@Test
	public void sampleTestCase() {
		CelebrityJpaEntity king = new CelebrityJpaEntity("King of Random");
		king = repository.save(king);

		CelebrityJpaEntity vihart = new CelebrityJpaEntity("Vihart");
		vihart = repository.save(vihart);

		List<CelebrityJpaEntity> daveResults = repository.findByName( king.getName() );
		assertThat( daveResults ).containsExactly( king );

		List<CelebrityJpaEntity> vihartResults = repository.findByName( vihart.getName() );
		assertThat( vihartResults ).containsExactly( vihart );
	}

	@After
	public void after() {
		repository.deleteAll();
	}
}
