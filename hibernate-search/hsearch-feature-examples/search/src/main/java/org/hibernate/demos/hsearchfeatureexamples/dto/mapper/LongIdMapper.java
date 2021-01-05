package org.hibernate.demos.hsearchfeatureexamples.dto.mapper;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.mapstruct.TargetType;

@ApplicationScoped
public class LongIdMapper {

	@PersistenceContext
	EntityManager entityManager;

	public <T extends PanacheEntity> T resolve(Long id, @TargetType Class<T> entityClass) {
		return id != null ? Panache.getEntityManager( entityClass ).find( entityClass, id ) : null;
	}

	public Long toLong(PanacheEntity entity) {
		return entity != null ? entity.id : null;
	}

}
