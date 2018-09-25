package org.hibernate.demo.message.post.core.event;

import javax.inject.Inject;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;

@ServerEndpoint("/board")
public class BoardWebSocket {

	@Inject
	private BoardSessionHolder sessions;

	@Inject
	private Logger log;

	@OnOpen
	public void open(Session session, EndpointConfig config) {
		log.info( "Add new session {} with config ", session, config);
	}

	@OnMessage
	public void message(String user, Session session) {
		log.info( "Add new session {} receiving message user {}", session, user );
		// session becomes active only when we have the name of the owner of the board to listen to
		sessions.addSession( session, user );
	}

	@OnClose
	public void close(Session session) {
		log.info( "Remove session {}", session );
		sessions.removeSession( session );
	}

	@OnError
	public void error(Throwable error) {
		log.error( "Error on websocket client", error );
	}
}
