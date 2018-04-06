/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.post.it;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.service.MessageService;
import org.hibernate.demo.message.post.core.service.exception.ResourceNotFoundException;
import org.hibernate.demo.message.post.util.DeploymentUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.slf4j.Logger;

/**
 * @author Fabio Massimo Ercoli
 */
@RunWith(Arquillian.class)
public class MessageLoadIT {

	public static final String USERNAME = "fabio";
	public static final String USERNAME_2 = "andrea";

	@Deployment
	public static WebArchive create() {
		return DeploymentUtil.create();
	}

	@Inject
	private MessageService testSubject;

	@Inject
	private Logger log;

	public Message[] messages = new Message[6];

	@Before
	public void before() {

		messages[0] = new Message( USERNAME, "Here I am!" );
		messages[1] = new Message( USERNAME, "Here I am! II" );
		messages[2] = new Message( USERNAME, "Here I am! III" );

		messages[3] = new Message( USERNAME_2, "Hello!" );
		messages[4] = new Message( USERNAME_2, "Hello! II" );
		messages[5] = new Message( USERNAME_2, "Hello! III" );

		for (int i=0; i<messages.length; i++) {
			testSubject.addMessage( messages[i] );
		}
	}

	@After
	public void after() {
		Arrays.stream(messages).forEach( message -> {
			try {
				testSubject.deleteMessage( message.getId() );
			}
			catch (ResourceNotFoundException e) {
				e.printStackTrace();
			}
		} );
	}

	@Test
	public void test() {

		List<Message> fabioMessages = null;
		List<Message> andreaMessages = null;

		fabioMessages = testSubject.findMessagesByUser( USERNAME );
		andreaMessages = testSubject.findMessagesByUser( USERNAME_2 );

		// order is not important!
		for ( int i=0; i<3; i++ ) {
			assertTrue( "Fabio's board does not contain post: " + messages[ i ], fabioMessages.contains( messages[ i ] ) );
		}

		// order is not important!
		for ( int i=3; i<6; i++ ) {
			assertTrue( "Andrea's board does not contain post: " + messages[ i ], andreaMessages.contains( messages[ i ] ) );
		}

	}

}
