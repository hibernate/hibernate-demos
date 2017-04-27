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
import com.example.ogm.core.user.Celebrity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class JpaProxyCelebrityRepositoryIntegrationTest {

	@Autowired
	private JpaProxyCelebrityRepository repository;

	@Test
	public void testNativeQuery() {
		Celebrity martin = new Celebrity( "Martin Fowler" );
		martin = repository.save( martin );

		Celebrity jujimufu = new Celebrity( "Jujimufu" );
		jujimufu = repository.save( jujimufu );

		List<Celebrity> martinResults = repository.byName( martin.getName() );
		assertThat( martinResults ).containsOnly( martin );

		List<Celebrity> jujimufuResults = repository.byName( jujimufu.getName() );
		assertThat( jujimufuResults ).containsExactly( jujimufu );
	}

	@After
	public void after() {
		repository.deleteAll();
	}
}
