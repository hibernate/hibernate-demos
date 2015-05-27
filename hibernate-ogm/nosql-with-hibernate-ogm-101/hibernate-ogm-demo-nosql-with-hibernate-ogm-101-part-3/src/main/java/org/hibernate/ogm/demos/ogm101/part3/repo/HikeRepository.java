/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.demos.ogm101.part3.repo;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.ogm.demos.ogm101.part3.model.Hike;

/**
 * {@link Hike} CRUD operations.
 *
 * @author Gunnar Morling
 */
@ApplicationScoped
public class HikeRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public Hike create(Hike hike) {
		if ( hike.getOrganizer() != null ) {
			hike.getOrganizer().getOrganizedHikes().add( hike );
		}

		entityManager.persist( hike );

		return hike;
	}

	public Hike get(String id) {
		return entityManager.find( Hike.class, id );
	}

	public List<Hike> getAll() {
		return entityManager.createQuery( "FROM Hike h", Hike.class ).getResultList();
	}

	public void remove(Hike hike) {
		entityManager.remove( hike );
		hike.getOrganizer().getOrganizedHikes().remove( hike );
	}
}
