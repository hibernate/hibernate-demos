package org.hibernate.demo.message.post.core.service.procedure;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;

@Singleton
public class RemoteProcedureRegister {

	@Inject
	private EntityManager em;

	protected boolean registered = false;

	// a javax.ejb.Singleton should be already synchronized
	public void registerMarshaller() {
		if ( registered ) {
			return;
		}

		StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery( "RegisterMarshallersTask" );
		storedProcedureQuery.execute();
		registered = true;
	}
}
