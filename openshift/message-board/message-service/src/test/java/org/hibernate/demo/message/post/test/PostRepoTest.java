/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.post.test;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;
import static org.junit.Assert.assertNotNull;

import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.entity.Tag;
import org.hibernate.demo.message.post.core.repo.MessageRepo;
import org.hibernate.demo.message.test.BaseEntityManagerFunctionalTestCase;

import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fabio Massimo Ercoli
 */
public class PostRepoTest extends BaseEntityManagerFunctionalTestCase {

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class[] { Message.class, Tag.class };
	}

	@Test
	public void createUserTest() {

		Message post = new Message( "mirko", "Here I am!" );
		post.addTag( "music" );

		doInJPA( this::entityManagerFactory, entityManager -> {
			MessageRepo repo = new MessageRepo( entityManager );
			repo.add( post );

			Long id = post.getId();
			assertNotNull( id );
		} );

		doInJPA( this::entityManagerFactory, entityManager -> {
			MessageRepo repo = new MessageRepo( entityManager );
			Message message = repo.findById( post.getId() );

			assertNotNull( message );
		} );
	}

}
