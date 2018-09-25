package org.hibernate.demo.message.post.core.event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.Session;

import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.repo.BoardRepo;
import org.hibernate.ogm.datastore.infinispanremote.impl.protostream.ProtostreamId;

import org.infinispan.client.hotrod.annotation.ClientCacheEntryCreated;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryModified;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryRemoved;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.event.ClientCacheEntryCreatedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryModifiedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryRemovedEvent;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

@ClientListener
@Stateless
@Asynchronous
public class BoardEventListener {

	@Inject
	private Logger log;

	@Inject
	private BoardSessionHolder sessions;

	@Inject
	private BoardRepo boards;

	@Inject
	private ObjectMapper jackson;

	@ClientCacheEntryCreated
	public void boardCreated(ClientCacheEntryCreatedEvent e) {
		log.info( "Board created event {}", e );
		notifyOpenBoards( (ProtostreamId) e.getKey(), false );
	}

	@ClientCacheEntryModified
	public void boardModified(ClientCacheEntryModifiedEvent e) {
		log.info( "Board modified event: {}", e );
		notifyOpenBoards( (ProtostreamId) e.getKey(), false );
	}

	@ClientCacheEntryRemoved
	public void boardRemoved(ClientCacheEntryRemovedEvent e) {
		log.info( "Board deleted event: {}", e );
		notifyOpenBoards( (ProtostreamId) e.getKey(), true );
	}

	private void notifyOpenBoards(ProtostreamId key, boolean removed) {
		String username = (String) key.columnValues[0];
		Set<Session> sessions = this.sessions.findByUser( username );
		if ( sessions.isEmpty() ) {
			log.warn( "No listening session, on board {}", username );
			return;
		}

		List<Message> messagesByUser = ( removed ) ? new ArrayList<>() : boards.find( username ).getMessages();
		try {
			String json = jackson.writer().writeValueAsString( messagesByUser );
			log.info( "Sending message to {} webSockets listening on board ({}). Payload: {}.", sessions.size(), username, json );
			for ( Session session : sessions ) {
				if ( session.isOpen() ) {
					session.getBasicRemote().sendText( json );
				}
				else {
					sessions.remove( session );
				}
			}
		}
		catch (IOException e) {
			throw new RuntimeException( "Error notifying Board WebSockets", e );
		}
	}
}
