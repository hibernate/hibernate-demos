package org.hibernate.demo.message.post.it;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;

import org.hibernate.demo.message.post.core.entity.Board;
import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.repo.BoardRepo;
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
public class MessageBoardPositionT {

	private static final String USERNAME = "davide";

	@Deployment
	public static WebArchive doDeploy() {
		return DeploymentUtil.wildFly();
	}

	@Deployment(name = "infinispan", testable = false)
	@TargetsContainer("infinispan")
	public static JavaArchive getInfinispanDeployment() {
		return DeploymentUtil.infinispan();
	}

	@Inject
	private MessageService messageService;

	@Inject
	private BoardRepo boardRepo;

	@Test
	public void tryStartInfinispanRemoteWithTask() throws Exception {
		Message message1 = new Message( USERNAME, "Hi #hibernate-ogm!" );
		Message message2 = new Message( USERNAME, "Hi #infinispan!" );
		Message message3 = new Message( USERNAME, "Hi #hibernate-orm!" );
		Message message4 = new Message( USERNAME, "Hi #openshift!" );

		messageService.addMessage( message1 );
		messageService.addMessage( message2 );
		messageService.addMessage( message3 );

		Map<Long, Integer> ordinals = boardRepo.findBoardMessageJoinItems( USERNAME );
		// message 1 -> position 0
		assertEquals( new Integer( 0 ), ordinals.get( message1.getId() ) );
		// message 2 -> position 1
		assertEquals( new Integer( 1 ), ordinals.get( message2.getId() ) );
		// message 3 -> position 2
		assertEquals( new Integer( 2 ), ordinals.get( message3.getId() ) );

		// potential next value -> 3
		Board board = boardRepo.find( USERNAME );
		assertEquals( new Integer( 3 ), board.getNext() );

		// remove message 2
		messageService.deleteMessage( message2.getId() );

		ordinals = boardRepo.findBoardMessageJoinItems( USERNAME );
		// message 1 -> position 0
		assertEquals( new Integer( 0 ), ordinals.get( message1.getId() ) );
		// message 2 -> does not exist anymore
		assertEquals( null, ordinals.get( message2.getId() ) );
		// message 3 -> position 1
		assertEquals( new Integer( 1 ), ordinals.get( message3.getId() ) );

		// potential next value -> 2
		board = boardRepo.find( USERNAME );
		assertEquals( new Integer( 2 ), board.getNext() );

		messageService.addMessage( message4 );

		ordinals = boardRepo.findBoardMessageJoinItems( USERNAME );
		// message 1 -> position 0
		assertEquals( new Integer( 0 ), ordinals.get( message1.getId() ) );
		// message 2 -> does not exist anymore
		assertEquals( null, ordinals.get( message2.getId() ) );
		// message 3 -> position 1
		assertEquals( new Integer( 1 ), ordinals.get( message3.getId() ) );
		// message 4 -> position 2
		assertEquals( new Integer( 2 ), ordinals.get( message4.getId() ) );

		// potential next value -> 3
		board = boardRepo.find( USERNAME );
		assertEquals( new Integer( 3 ), board.getNext() );

		List<Message> davide = messageService.findMessagesByUser( USERNAME );
		assertEquals( 3, davide.size() );
	}
}
