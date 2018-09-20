package org.hibernate.demo.message.post.core.service.procedure;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@Stateless
public class StoredProcedureService {

	@Inject
	private RemoteProcedureRegister register;

	@Inject
	private EntityManager em;

	public Integer updateBoardWithMessage(Long messageId) {
		register.registerMarshaller();

		StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery( "UpdateUserBoardTask" );
		storedProcedureQuery.registerStoredProcedureParameter( "messageId", Long.class, ParameterMode.IN );
		storedProcedureQuery.setParameter( "messageId", messageId );

		return (Integer) storedProcedureQuery.getSingleResult();
	}
}
