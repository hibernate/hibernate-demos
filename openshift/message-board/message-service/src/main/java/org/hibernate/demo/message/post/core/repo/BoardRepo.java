package org.hibernate.demo.message.post.core.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.demo.message.post.core.entity.Board;

import org.slf4j.Logger;

@ApplicationScoped
public class BoardRepo {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;

	public Board find(String username) {
		return em.find( Board.class, username );
	}

	public void add(@Valid Board board) {
		em.persist( board );
	}

	public void update(@Valid Board board) {
		em.merge( board );
	}

	public void delete(Board board) {
		em.remove( board );
	}

	public Map<Long, Integer> findBoardMessageJoinItems(String username) {
		List<Object[]> result = em.unwrap( Session.class ).createNativeQuery(
				"select messages_id, order from HibernateOGMGenerated.Board_Message bm where bm.Board_username = '" + username + "'" )
				.list();

		log.info( "BoardMessage join items for username[{}]:", username );

		Map<Long, Integer> ordinalResultMap = new HashMap<>();
		for ( Object[] row : result ) {
			log.info( "Message {} - Ordinal {}", row[0], row[1] );
			ordinalResultMap.put( (Long)row[0], (Integer) row[1] );
		}

		return ordinalResultMap;
	}
}
