package org.hibernate.demo.message.post.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import javax.inject.Inject;

import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.repo.MessageRepo;
import org.hibernate.demo.message.post.core.service.MessageService;
import org.hibernate.demo.message.post.core.service.exception.ResourceNotFoundException;
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

/**
 * @author Fabio Massimo Ercoli
 */
@RunWith(Arquillian.class)
public class MessageFullTextSearchIT {

	private static final Message[] MESSAGES = {

			// Ah Sunflower - Poem by William Blake - 1794 MESSAGES[ 0 - 3 ]
			new Message( "wblake", "I was angry with my #friend: I told my wrath, my wrath did end. I was angry with my foe: I told it not, my wrath did grow." ),
			new Message( "wblake", "And I watered it in fears, Night and morning with my #tears; And I sunned it with smiles, And with soft deceitful wiles." ),
			new Message( "wblake", "And it grew both day and night, Till it bore an apple bright. And my foe beheld it shine. And he knew that it was mine," ),
			new Message( "wblake", "And into my garden stole When the night had veiled the pole; In the morning glad I seeMy foe outstretched beneath the tree. " ),

			// A song - Poem by William Blake - 1789 MESSAGES[ 4 - 7 ]
			new Message( "wblake", "Sweet dreams, form a shade O'er my lovely infant's head! Sweet dreams of pleasant streams By happy, silent, moony beams!" ),
			new Message( "wblake", "Sweet Sleep, with soft down Weave thy brows an infant crown Sweet Sleep, #angel mild, Hover o'er my happy child!" ),
			new Message( "wblake", "Sweet smiles, in the night Hover over my delight! Sweet smiles, mother's smile, All the livelong night beguile." ),
			new Message( "wblake", "Sweet moans, dovelike sighs, Chase not slumber from thine eyes! Sweet moan, sweeter smile, All the dovelike moans beguile." ),
			// MESSAGES[ 8 - 11 ]
			new Message( "wblake", "Sleep, sleep, happy child! All creation slept and smiled. Sleep, sleep, happy sleep, While o'er thee doth mother weep." ),
			new Message( "wblake", "Sweet babe, in thy face Holy image I can trace; Sweet babe, once like thee Thy Maker lay, and wept for me:" ),
			new Message( "wblake", "Wept for me, for thee, for all, When He was an infant small. Thou His image ever see, Heavenly face that smiles on thee!" ),
			new Message( "wblake", "Smiles on thee, on me, on all, Who became an infant small; Infant smiles are his own smiles; Heaven and earth to peace beguiles." )
	};

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
	private MessageService service;

	@Inject
	private MessageRepo repo;

	@Inject
	private Logger log;

	@Before
	public void before() {
		for ( Message message : MESSAGES ) {
			service.addMessage( message );
		}
	}

	@After
	public void after() throws ResourceNotFoundException {
		for ( Message message : MESSAGES ) {
			service.deleteMessage( message.getId() );
		}
	}

	@Test
	public void fullTextSearchOnPoems() {
		List occurrences = repo.findByTerm( "Sweet", 0, 10 );

		log.info( "Full Text Search result --> {}", occurrences );
		assertEquals( 5, occurrences.size() );
		assertTrue( occurrences.contains( MESSAGES[4] ) );
		assertTrue( occurrences.contains( MESSAGES[5] ) );
		assertTrue( occurrences.contains( MESSAGES[6] ) );
		assertTrue( occurrences.contains( MESSAGES[7] ) );
		assertTrue( occurrences.contains( MESSAGES[9] ) );
	}
}
