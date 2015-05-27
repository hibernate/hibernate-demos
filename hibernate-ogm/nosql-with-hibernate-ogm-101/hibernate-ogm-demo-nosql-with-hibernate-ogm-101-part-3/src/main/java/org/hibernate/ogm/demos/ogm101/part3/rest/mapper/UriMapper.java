/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.demos.ogm101.part3.rest.mapper;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.hibernate.ogm.demos.ogm101.part3.model.Hike;
import org.hibernate.ogm.demos.ogm101.part3.model.Person;
import org.hibernate.ogm.demos.ogm101.part3.rest.resource.Hikes;
import org.hibernate.ogm.demos.ogm101.part3.rest.resource.Persons;
import org.mapstruct.TargetType;

/**
 * @author Gunnar Morling
 *
 */
@ApplicationScoped
public class UriMapper {

	@Inject
	private UriInfo uriInfo;

	@PersistenceContext
	private EntityManager entityManager;

	public URI toUri(Person person) {
		return UriBuilder.fromUri( uriInfo.getBaseUri() ).path( Persons.class ).path( "/{id}" ).build( person.getId() );
	}

	public URI toUri(Hike hike) {
		return UriBuilder.fromUri( uriInfo.getBaseUri() ).path( Hikes.class ).path( "/{id}" ).build( hike.getId() );
	}

	public <T> T load(URI uri, @TargetType Class<T> entityType) {
		String id = toId( uri );
		return entityManager.find( entityType, id );
	}

	public String toId(URI uri) {
		String path = uri.getPath();
		return path.substring( path.lastIndexOf( "/" ) + 1 );
	}
}
