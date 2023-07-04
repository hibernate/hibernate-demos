package org.hibernate.demos.outboxpolling.dto.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.ws.rs.NotFoundException;
import org.mapstruct.TargetType;

@ApplicationScoped
public class LongIdMapper {

	@PersistenceContext
	EntityManager entityManager;

	public <T extends PanacheEntity> T resolve(Long id, @TargetType Class<T> entityClass) {
		if ( id == null ) {
			return null;
		}
		T entity = Panache.getEntityManager( entityClass ).find( entityClass, id );
		if ( entity == null ) {
			throw new NotFoundException( "No entity of type '" + entityClass + "' with ID " + id );
		}
		return entity;
	}

	public Long toLong(PanacheEntity entity) {
		return entity != null ? entity.id : null;
	}

}
