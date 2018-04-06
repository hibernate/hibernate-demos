package org.hibernate.demo.message.post.it;

import static org.junit.Assert.assertEquals;

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

@RunWith(Arquillian.class)
public class MessageOrderIT {

	public static final String USERNAME = "keepInOrder";

	@Deployment
	public static WebArchive create() {
		return DeploymentUtil.create();
	}

	@Inject
	private MessageService messageService;

	@Test
	public void test() {
		messageService.addMessage( new Message( USERNAME, "Message #1" ) );
		messageService.addMessage( new Message( USERNAME, "Message #2" ) );
		messageService.addMessage( new Message( USERNAME, "Message #3" ) );

		List<Message> loadedMessages = messageService.findMessagesByUser( USERNAME );

		assertEquals( "Message #3", loadedMessages.get( 0 ).getBody() );
		assertEquals( "Message #2", loadedMessages.get( 1 ).getBody() );
		assertEquals( "Message #1", loadedMessages.get( 2 ).getBody() );

	}

}
