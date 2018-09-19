package org.hibernate.demo.message.post.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.inject.Inject;

import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.repo.MessageRepo;
import org.hibernate.demo.message.post.core.service.MessageService;
import org.hibernate.demo.message.post.util.DeploymentUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.slf4j.Logger;

@RunWith(Arquillian.class)
public class MessageDateIT {

	@Deployment
	public static WebArchive doDeploy() {
		return DeploymentUtil.wildFly();
	}

	@Deployment(name = "infinispan", testable = false)
	@TargetsContainer("infinispan")
	public static JavaArchive getInfinispanDeployment() {
		return DeploymentUtil.infinispan();
	}

	private Message[] messages = new Message[3];
	private Date[] moments = new Date[3];

	@Inject
	private MessageService messageService;

	@Inject
	private MessageRepo messageRepo;

	@Inject
	private Logger log;

	@Before
	public void insertMessagesWithDates() {
		moments[0] = getDate( 1991, Calendar.FEBRUARY, 1 );
		moments[1] = getDate( 1990, Calendar.NOVEMBER, 21 );
		moments[2] = getDate( 1991, Calendar.NOVEMBER, 21 );

		messages[0] = new Message( "mario", "Hello! How are you?" );
		messages[0].setMoment( moments[0] );
		messages[1] = new Message( "luigi", "Hello! Where are you from?" );
		messages[1].setMoment( moments[1] );
		messages[2] = new Message( "peach", "Hello! How old are you?" );
		messages[2].setMoment( moments[2] );

		messageService.addMessage( messages[0] );
		messageService.addMessage( messages[1] );
		messageService.addMessage( messages[2] );
	}

	@Test
	public void verifyMessageDate() throws Exception {
		List<Message> mario = messageService.findMessagesByUser( "mario" );
		assertEquals( 1, mario.size() );
		assertEquals( moments[0], mario.get( 0 ).getMoment() );

		List<Message> luigi = messageService.findMessagesByUser( "luigi" );
		assertEquals( 1, luigi.size() );
		assertEquals( moments[1], luigi.get( 0 ).getMoment() );

		List<Message> peach = messageService.findMessagesByUser( "peach" );
		assertEquals( 1, peach.size() );
		assertEquals( moments[2], peach.get( 0 ).getMoment() );
	}

	@Test
	public void testSearchByDate() {
		List<Message> aug1990mar1991 = messageRepo.findMessageByTime( getDate( 1990, Calendar.AUGUST, 1 ), getDate( 1991, Calendar.MARCH, 1 ) );
		if ( aug1990mar1991.size() != 2 ) {
			log.warn( "Wrong message list. Expected 2. Actual {}", aug1990mar1991 );
			fail();
		}

		List<Message> genMar1991 = messageRepo.findMessageByTime( getDate( 1991, Calendar.JANUARY, 1 ), getDate( 1991, Calendar.MARCH, 1 ) );
		assertEquals( 1, genMar1991.size() );
	}

	@After
	public void removeMessages() throws Exception {
		messageService.deleteMessage( messages[0].getId() );
		messageService.deleteMessage( messages[1].getId() );
		messageService.deleteMessage( messages[2].getId() );
	}

	private Date getDate(int year, int month, int date) {
		Calendar cal = Calendar.getInstance( TimeZone.getDefault() );
		cal.set( year, month, date );
		return cal.getTime();
	}
}
