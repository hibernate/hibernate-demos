package org.hibernate.demo.message.post.core.event;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

@ApplicationScoped
public class BoardSessionHolder {

	// if a pod dies, the browser will create a new session on another node,
	// so we don't need to clustering session items
	private Map<Session, String> sessionMap = new ConcurrentHashMap<>();

	public void addSession(Session session, String user) {
		sessionMap.put( session, user );
	}

	public void removeSession(Session session) {
		sessionMap.remove( session );
	}

	public Set<Session> findByUser(String username) {
		return sessionMap.entrySet().stream()
				.filter( entry -> username.equals( entry.getValue() ) )
				.map( map -> map.getKey() )
				.collect( Collectors.toSet() );
	}
}
