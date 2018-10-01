/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.post.core.repo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.demo.message.post.core.entity.Message;

import org.slf4j.Logger;

/**
 * @author Fabio Massimo Ercoli
 */
@ApplicationScoped
public class MessageRepo {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;

	public MessageRepo() {
	}

	public MessageRepo(EntityManager em) {
		this.em = em;
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void add(@Valid Message post) {
		em.persist( post );
	}

	public Message findById(Long id) {
		return em.find( Message.class, id );
	}

	public void remove(Message message) {
		em.remove( message );
	}

	public List<Long> findMessageIdsByTag(String tag, int pageNumber, int pageSize) {
		List result = em.unwrap( Session.class ).createNativeQuery( "select Message_id from HibernateOGMGenerated.Message_Tag mt where mt.tags_name = '" + tag + "'" )
				.setFirstResult( pageNumber * pageSize )
				.setMaxResults( pageSize )
				.list();

		log.debug( "tag : {} - pageNumber : {} - pageSize : {} - ids : {}", tag, pageNumber, pageSize, result );
		return result;
	}

	public List<Message> findMessagesByIds(List<Long> ids) {
		List resultList = em.createQuery( "from Message where id in (:ids) order by moment desc" )
				.setParameter( "ids", ids )
				.getResultList();

		log.debug( "messages : {}", resultList );
		return resultList;
	}

	public List<Message> findMessagesByTag(String tag, int pageNumber, int pageSize) {
		List<Long> messageIdsByTag = findMessageIdsByTag( tag, pageNumber, pageSize );
		return (messageIdsByTag.isEmpty()) ?
			new ArrayList<>() : findMessagesByIds( messageIdsByTag );
	}

	public List<Message> findMessageByTime(Date start, Date end) {
		// end has to be after start
		if ( !end.after( start ) ) {
			return new ArrayList<>();
		}

		return em.createQuery( "from Message where moment BETWEEN :start and :end order by moment desc" )
			.setParameter( "start", start )
			.setParameter( "end", end )
			.getResultList();
	}

	public List<Message> findByTerm(String term, int pageNumber, int pageSize) {
		Query query = em.createNativeQuery( "from HibernateOGMGenerated.Message m where m.body:'" + term + "'", Message.class )
				.setMaxResults( pageSize )
				.setFirstResult( pageNumber * pageSize );

		return query.getResultList();
	}
}
