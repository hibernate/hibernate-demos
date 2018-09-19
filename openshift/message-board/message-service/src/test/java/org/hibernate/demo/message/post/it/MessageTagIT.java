package org.hibernate.demo.message.post.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;
import javax.inject.Inject;

import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.entity.Tag;
import org.hibernate.demo.message.post.core.repo.MessageRepo;
import org.hibernate.demo.message.post.core.service.MessageService;
import org.hibernate.demo.message.post.util.DeploymentUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

@RunWith(Arquillian.class)
public class MessageTagIT {

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

	@Inject
	private MessageService messageService;

	@Inject
	private MessageRepo messageRepo;

	@Test
	public void createMessagesWithTags() throws Exception {
		messages[0] = new Message( "fabio", "Nice! I'm happy to be here. Thank you guys! #Hello" );
		messages[1] = new Message( "fabio", "What's your favourite framework for #MicroServices?" );
		messages[2] = new Message( "john", "#Hello. There are a lot of #MicroServices here." );

		for ( int i = 0; i < messages.length; i++ ) {
			messageService.addMessage( messages[i] );
		}

		List<Message> johnMessages = messageService.findMessagesByUser( "john" );
		assertEquals( 1, johnMessages.size() );

		Set<Tag> tags = johnMessages.get( 0 ).getTags();
		assertEquals( 2, tags.size() );
		assertTrue( tags.contains( new Tag( "#Hello" ) ) );
		assertTrue( tags.contains( new Tag( "#MicroServices" ) ) );

		List<Message> helloMessages = messageRepo.findMessagesByTag( "#Hello", 0, 10 );
		assertEquals( 2, helloMessages.size() );
		assertTrue( messages[0].equals( helloMessages.get( 0 ) ) || messages[2].equals( helloMessages.get( 0 ) ) );
		assertTrue( messages[0].equals( helloMessages.get( 1 ) ) || messages[2].equals( helloMessages.get( 1 ) ) );
	}
}
