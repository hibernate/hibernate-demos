package org.hibernate.demo.message.post.it;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.service.MessageService;
import org.hibernate.demo.message.post.util.DeploymentUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.slf4j.Logger;

@RunWith(Arquillian.class)
public class MessageCreateDeleteIT {

	public static final String USERNAME = "myuser";

	@Deployment
	public static WebArchive create() {
		return DeploymentUtil.create();
	}

	@Inject
	private MessageService messageService;

	@Inject
	private Logger log;

	private List<Long> messageIds = new ArrayList<>();

	@Test
	public void test() throws Exception {

		// add 10 messages
		for (int i=0; i<10; i++) {
			Message message = new Message( USERNAME, "Message content: " + i );
			messageService.addMessage( message );
			messageIds.add( message.getId() );
		}

		List<Message> loadedMessages = messageService.findMessagesByUser( USERNAME );
		log.info( "Loaded messages: {}", loadedMessages );

		assertEquals( 10, loadedMessages.size() );

		// remove the 5 odd messages [0,2,4,6,8]
		for (int i=0; i<5; i++) {
			messageService.deleteMessage( messageIds.remove( i ) );
		}

		loadedMessages = messageService.findMessagesByUser( USERNAME );
		log.info( "Loaded messages: {}", loadedMessages );

		assertEquals( 5, loadedMessages.size() );

		// add other 10 messages
		for (int i=0; i<10; i++) {
			Message message = new Message( USERNAME, "Message content: " + i );
			messageService.addMessage( message );
			messageIds.add( message.getId() );
		}

		loadedMessages = messageService.findMessagesByUser( USERNAME );
		log.info( "Loaded messages: {}", loadedMessages );

		assertEquals( 15, loadedMessages.size() );

		// remove the first 10 messages
		for (int i=0; i<10; i++) {
			messageService.deleteMessage( messageIds.remove( 0 ) );
		}

		loadedMessages = messageService.findMessagesByUser( USERNAME );
		log.info( "Loaded messages: {}", loadedMessages );

		assertEquals( 5, loadedMessages.size() );
		assertEquals( 5, messageIds.size() );

		// clean up
		for (Long id : messageIds) {
			messageService.deleteMessage( id );
		}
		messageIds.clear();

	}

	@Test
	public void test_cleanUpTheBoard() throws Exception {

		// add 3 messages
		for (int i=0; i<3; i++) {
			Message message = new Message( USERNAME, "---Message content: " + i + "---" );
			messageService.addMessage( message );
			messageIds.add( message.getId() );
		}

		// clean up the board
		for (Long id : messageIds) {
			messageService.deleteMessage( id );
		}
		messageIds.clear();

		// re-add 3 messages
		for (int i=0; i<3; i++) {
			Message message = new Message( USERNAME, "---Message content: " + i + "---" );
			messageService.addMessage( message );
			messageIds.add( message.getId() );
		}

		List<Message> loadedMessages = messageService.findMessagesByUser( USERNAME );
		assertEquals( 3, loadedMessages.size() );

		// clean up
		for (Long id : messageIds) {
			messageService.deleteMessage( id );
		}
		messageIds.clear();

	}

}
